package eu.mrndesign.matned.client.model.game.object.element.obj;

import java.util.Collections;
import java.util.List;

import eu.mrndesign.matned.client.model.game.object.CanvasModel;
import eu.mrndesign.matned.client.model.game.object.GameElement;
import eu.mrndesign.matned.client.model.game.object.GameElementType;
import eu.mrndesign.matned.client.model.game.object.element.Landscape;
import eu.mrndesign.matned.client.model.tools.Bounds2D;
import eu.mrndesign.matned.client.model.tools.Point2D;
import eu.mrndesign.matned.client.model.tools.Vector2D;

import static eu.mrndesign.matned.client.controller.Constants.PANEL_HEIGHT_INT;
import static eu.mrndesign.matned.client.controller.Constants.PANEL_WIDTH_INT;

public class BreakBear extends Landscape {

    private static final int HEIGHT = 300;
    private static final int WIDTH = 400;

    public BreakBear(CanvasModel canvasModel) {
        super("Break bear", 2, new Bounds2D(new Vector2D(0,-1), WIDTH, HEIGHT, new Point2D(PANEL_WIDTH_INT/2, PANEL_HEIGHT_INT-HEIGHT/2)), canvasModel, 1, 0);
    }

    @Override
    public List<String> frames() {
        return Collections.singletonList("img/break-bear.png");
    }

    @Override
    public void refresh() {

    }

    @Override
    public void mouseMove(int x, int y) {

    }

    @Override
    public void action(int x, int y) {
    }

    @Override
    public GameElementType getType() {
        return GameElementType.PICTURE;
    }

    @Override
    public boolean isToRemove() {
        return toRemove;
    }
}
