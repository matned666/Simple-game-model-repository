package eu.mrndesign.matned.client.model.game.object.element.obj;

import eu.mrndesign.matned.client.controller.TimeWrapper;
import eu.mrndesign.matned.client.model.game.object.CanvasModel;
import eu.mrndesign.matned.client.model.game.object.GameElement;
import eu.mrndesign.matned.client.model.game.object.GameElementType;
import eu.mrndesign.matned.client.model.game.object.element.Landscape;
import eu.mrndesign.matned.client.model.tools.Bounds2D;
import eu.mrndesign.matned.client.model.tools.Point2D;
import eu.mrndesign.matned.client.model.tools.Vector2D;

import java.util.Arrays;
import java.util.List;

import static eu.mrndesign.matned.client.controller.Constants.ANIMATION_FRAME_RATE;

public class Blow extends Landscape {

    public static GameElement blow20x30(Vector2D vector, Bounds2D bounds, CanvasModel canvasModel) {
        Vector2D v = new Vector2D(vector);
        Bounds2D bounds2D = new Bounds2D(v, 20,30, new Point2D(bounds.getCenter()));
        return new Blow(bounds2D, canvasModel);
    }



    private List<String> frames;

    public Blow(Bounds2D bounds, CanvasModel canvasModel) {
        super("Blow", 0, bounds, null, canvasModel, 1, 1);
        frames = Arrays.asList("img/blow1.png", "img/blow2.png", "img/blow3.png", "img/blow4.png", "img/blow5.png", "img/blow6.png");
    }

    @Override
    public List<String> frames() {
        return frames;
    }

    @Override
    public void refresh() {
        move();
    }

    @Override
    public void mouseMove(int x, int y) {

    }

    @Override
    public void action(int x, int y) {

    }

    @Override
    public GameElementType getType() {
        return GameElementType.BLOW;
    }

    @Override
    public boolean isToRemove() {
        return (TimeWrapper.getInstance().getFrameNo() - startFrame)/ ANIMATION_FRAME_RATE> frames.size() || toRemove;
    }

    public boolean isAnimation(){
        return true;
    }





}
