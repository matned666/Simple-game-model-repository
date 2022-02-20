package eu.mrndesign.matned.client.model.game.object;

import com.google.gwt.event.dom.client.KeyCodes;
import eu.mrndesign.matned.client.model.game.object.element.DesertBackground;
import eu.mrndesign.matned.client.model.tools.Bounds2D;
import eu.mrndesign.matned.client.model.tools.Point2D;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CanvasModel extends Bounds2D {

    private Logger logger = Logger.getLogger("Controller:");

    protected final String id = "DrawingArea-" + System.currentTimeMillis();

    private final Map<String, GameElement> mapIdToGameElement = new HashMap<>();
    private final List<String> removedGameElements = new LinkedList<>();
    private final GameElement background = new DesertBackground();

    private final GameModel gameModel;
    private GameElement hero;

    public CanvasModel(double width, double height, GameModel gameModel) {
        super(width, height, new Point2D(0,0));
        this.gameModel = gameModel;
        addHero();
        addNewEnemy();
    }

    public void add(GameElement gameElement) {
        mapIdToGameElement.put(gameElement.getId(), gameElement);
    }

    public void remove(String id) {
        removedGameElements.add(id);
    }

    public Map<String, GameElement> getMapIdToGameElement() {
        return mapIdToGameElement;
    }

    public void mouseDownEvent(int x, int y) {
        mapIdToGameElement.values().forEach(gameElement -> gameElement.action(x,y));
    }

    public void mouseMoveEvent(int x, int y) {
        mapIdToGameElement.values().forEach(gameElement -> gameElement.mouseMove(x,y));
    }

    public String getBackgroundImage() {
        return background.getUrl();
    }


    public void canvasRefresh() {
        mapIdToGameElement.values().forEach(GameElement::refresh);
    }

    public List<String> getRemovedGameElements() {
        return removedGameElements;
    }

    public boolean isInBounds(GameElement element){
        return isIn(element.bounds);
    }

    public void onKeyPressed(int keyCode) {
        if (keyCode == KeyCodes.KEY_SPACE) {
            fireBullet();
        }
    }

    private void addHero() {
        hero = GameElementsFactory.hero(this);
        add(hero);
    }

    public void addNewEnemy() {
        GameElement rock1 = GameElementsFactory.enemy(1, hero, this);
        add(rock1);
    }

    private void fireBullet() {
        GameElement bullet = GameElementsFactory.bullet(hero.getVector(), hero.getBounds(), this);
        add(bullet);
        logger.log(Level.SEVERE, "set new bullet");
    }
}
