package eu.mrndesign.matned.client.model.game.object;

import eu.mrndesign.matned.client.model.game.object.element.obj.Putin;
import eu.mrndesign.matned.client.model.game.object.element.obj.Rock;

public class GameElementsFactory {
    
    public static GameElement getEnemy(GameElement referenceElement, CanvasModel canvasModel, int hp, int hit) {
        int random = (int) (Math.random() * 10);
        if (random > 7) {
            return Putin.enemy(referenceElement, canvasModel, hp, hit);
        }
        return Rock.enemy(referenceElement, canvasModel, hp, hit);


    }

    


}
