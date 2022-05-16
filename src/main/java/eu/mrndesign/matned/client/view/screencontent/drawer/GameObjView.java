package eu.mrndesign.matned.client.view.screencontent.drawer;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import eu.mrndesign.matned.client.controller.TimeWrapper;
import eu.mrndesign.matned.client.model.game.object.GameElement;

import java.util.ArrayList;
import java.util.List;

import static eu.mrndesign.matned.client.controller.Constants.ANIMATION_FRAME_RATE;

public class GameObjView extends Image {

    private final GameElement gameElement;
    private final String id;
    private long frameNo = 0;
    private final List<ImageElement> frames = new ArrayList<>();

    public GameObjView(GameElement gameElement) {
        this.gameElement = gameElement;
        this.id = gameElement.getId();
        setStyleName("gameObject");
        getElement().setDraggable("DRAGGABLE_FALSE");
        List<String> frames = gameElement.frames();
        setUrl(frames.get(0));
        frames.forEach(url -> this.frames.add(ImageElement.as(new Image(url).getElement())));
    }

    public GameElement getGameElement() {
        return gameElement;
    }

    public String getId() {
        return id;
    }

    private ImageElement animationRun() {
        int frame = (int) (TimeWrapper.getInstance().getFrameNo() - frameNo) - 1;
        if (frame >= frames.size()*ANIMATION_FRAME_RATE) {
            frameNo = TimeWrapper.getInstance().getFrameNo();
            frame = (int) (TimeWrapper.getInstance().getFrameNo() - frameNo) - 1;
        }
        return  frames.get(frame/ANIMATION_FRAME_RATE);
    }

    public double getRotationValue() {
        return gameElement.getAngle();
    }

    public double getCenterX() {
        return gameElement.getBounds().getCenter().getX();
    }

    public double getCenterY() {
        return gameElement.getBounds().getCenter().getY();
    }

    public ImageElement getImage() {
        if (gameElement.isAnimation()) {
            return animationRun();
        } else {
            return frames.get(gameElement.actualImageIndex());
        }
    }
}
