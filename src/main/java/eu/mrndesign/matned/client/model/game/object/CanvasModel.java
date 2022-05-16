package eu.mrndesign.matned.client.model.game.object;

import com.google.gwt.event.dom.client.KeyCodes;
import eu.mrndesign.matned.client.model.Model;
import eu.mrndesign.matned.client.model.game.object.element.obj.DesertBackground;
import eu.mrndesign.matned.client.model.tools.Bounds2D;
import eu.mrndesign.matned.client.model.tools.Point2D;
import eu.mrndesign.matned.client.model.tools.Vector2D;

import java.util.List;
import java.util.Map;

public class CanvasModel extends Bounds2D {

	private final GameElement background = new DesertBackground();

	private final Model model;
	private final Game game;

	public CanvasModel(double width, double height, Model model) {
		super(new Vector2D(0,1), width, height, new Point2D(0, 0));
		this.model = model;
		this.game = new Game(this);
	}

	public Map<String, GameElement> getMapIdToGameElement() {
		return game.getMapIdToGameElement();
	}

	public void mouseDownEvent(int x, int y) {
		game.getMapIdToGameElement().values().forEach(gameElement -> gameElement.action(x, y));
	}

	public void mouseMoveEvent(int x, int y) {
		game.getMapIdToGameElement().values().forEach(gameElement -> gameElement.mouseMove(x, y));
	}

	public String getBackgroundImage() {
		return background.frames().get(0);
	}

	public void canvasRefresh() {
		game.refresh();
	}

	public List<String> getRemovedGameElements() {
		return game.getRemovedGameElements();
	}

	public void onKeyPressed(int keyCode) {
		if (keyCode == KeyCodes.KEY_SPACE) {
			game.fire();
		}
	}

	public void clearRemoved() {
		game.clearRemoved();
	}

}
