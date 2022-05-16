package eu.mrndesign.matned.client.model.game.object.element.obj;

import eu.mrndesign.matned.client.model.game.object.GameElement;
import eu.mrndesign.matned.client.model.game.object.GameElementType;
import eu.mrndesign.matned.client.model.game.object.element.Landscape;

import java.util.Collections;
import java.util.List;

public class DesertBackground extends Landscape {


    public DesertBackground() {
        super("Background", 0, null, null, null, 1, 0);
    }

    @Override
    public List<String> frames() {
        return Collections.singletonList("img/desert-background.jpg");
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
        return GameElementType.BACKGROUND;
    }

    @Override
    public boolean isToRemove() {
        return false;
    }
}
