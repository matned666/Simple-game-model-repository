package eu.mrndesign.matned.client.model.game.object.element.obj;

import static eu.mrndesign.matned.client.controller.Constants.PANEL_HEIGHT_INT;
import static eu.mrndesign.matned.client.controller.Constants.PANEL_WIDTH_INT;

import java.util.Arrays;
import java.util.List;

import eu.mrndesign.matned.client.model.game.object.CanvasModel;
import eu.mrndesign.matned.client.model.game.object.GameElement;
import eu.mrndesign.matned.client.model.game.object.GameElementType;
import eu.mrndesign.matned.client.model.game.object.element.Hero;
import eu.mrndesign.matned.client.model.tools.Bounds2D;
import eu.mrndesign.matned.client.model.tools.Point2D;
import eu.mrndesign.matned.client.model.tools.Vector2D;

public class StarShip extends Hero {

    public static GameElement hero(CanvasModel canvasModel) {
        return new StarShip(canvasModel);
    }

    private boolean launched;

    public StarShip(CanvasModel canvasModel) {
        super("StarShip", 5,  new Bounds2D(new Vector2D(0, 100),50, 80, new Point2D(PANEL_WIDTH_INT / 2, PANEL_HEIGHT_INT / 2)), canvasModel, 40, 5);
    }

    @Override
    public List<String> frames() {
        return Arrays.asList("img/starship.png", "img/starship-fly.png");
    }

    @Override
    public void refresh() {

    }

    @Override
    public void mouseMove(int x, int y) {
        launched = false;
        rotate(x, y);
    }

    @Override
    public void action(int x, int y) {
        if (getBounds().getCenter().distanceFrom(x, y) > getBounds().getHeight() / 2) {
            launched = true;
            moveTo(x, y);
        }
    }

    @Override
    public GameElementType getType() {
        return GameElementType.HERO;
    }

    @Override
    public boolean isToRemove() {
        return false;
    }

    public int actualImageIndex(){
        if (launched) {
            return 1;
        }
        return 0;
    }


}
