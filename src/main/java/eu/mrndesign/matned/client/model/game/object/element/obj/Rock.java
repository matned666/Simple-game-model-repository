package eu.mrndesign.matned.client.model.game.object.element.obj;

import eu.mrndesign.matned.client.model.game.object.CanvasModel;
import eu.mrndesign.matned.client.model.game.object.GameElement;
import eu.mrndesign.matned.client.model.game.object.GameElementType;
import eu.mrndesign.matned.client.model.game.object.element.Enemy;
import eu.mrndesign.matned.client.model.tools.Bounds2D;
import eu.mrndesign.matned.client.model.tools.Math2D;
import eu.mrndesign.matned.client.model.tools.Point2D;
import eu.mrndesign.matned.client.model.tools.Vector2D;

import java.util.Collections;
import java.util.List;

import static eu.mrndesign.matned.client.controller.Constants.PANEL_HEIGHT_INT;
import static eu.mrndesign.matned.client.controller.Constants.PANEL_WIDTH_INT;

public class Rock extends Enemy {

    public static GameElement enemy(GameElement referenceElement, CanvasModel canvasModel, int hp, int hit) {
        return new Rock(Math2D.randomInt(1, 6), referenceElement, canvasModel, hp, hit);
    }

    public Rock(double speed, GameElement referenceElement, CanvasModel canvasModel, int hp, int hit) {
        super("Rock", speed, referenceElement, canvasModel, hp, hit);
        Point2D point2D = Point2D.randomPointOnEdge(30, PANEL_WIDTH_INT, PANEL_HEIGHT_INT);
        Vector2D v = new Vector2D(point2D, Point2D.zero());
        bounds = new Bounds2D(v, 30, 40, point2D);
    }

    @Override
    public List<String> frames() {
        return Collections.singletonList("img/rock.png");
    }

    @Override
    public void refresh() {
        if (!isInBounds(referenceElement)) {
            Point2D referencedElementCenter = referenceElement.getBounds().getCenter();
            setVector(new Vector2D(getBounds().getCenter(), referencedElementCenter));
            moveTo(referencedElementCenter.getX(), referencedElementCenter.getY());
        }
    }

    @Override
    public void mouseMove(int x, int y) {

    }

    @Override
    public void action(int x, int y) {

    }

    @Override
    public GameElementType getType() {
        return GameElementType.ENEMY;
    }

    @Override
    public boolean isToRemove() {
        return toRemove;
    }
}
