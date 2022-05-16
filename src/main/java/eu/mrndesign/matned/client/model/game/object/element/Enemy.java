package eu.mrndesign.matned.client.model.game.object.element;

import eu.mrndesign.matned.client.model.game.object.CanvasModel;
import eu.mrndesign.matned.client.model.game.object.GameElement;
import eu.mrndesign.matned.client.model.tools.Bounds2D;

public abstract class Enemy extends GameElement {

    public Enemy(String name, double speed, Bounds2D bounds, CanvasModel canvasModel, int hP, int hit) {
        super(name, speed, bounds, canvasModel, hP, hit);
    }

    public Enemy(String name, double speed, GameElement referenceElement, CanvasModel canvasModel, int hP, int hit) {
        super(name, speed, referenceElement, canvasModel, hP, hit);
    }

    public Enemy(String name, double speed, Bounds2D bounds, GameElement referenceElement, CanvasModel canvasModel, int hit, int hP) {
        super(name, speed, bounds, referenceElement, canvasModel, hit, hP);
    }
}
