package eu.mrndesign.matned.client.view.screencontent.game;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import eu.mrndesign.matned.client.controller.Constants;
import eu.mrndesign.matned.client.controller.Controller;
import eu.mrndesign.matned.client.controller.TimeWrapper;
import eu.mrndesign.matned.client.model.game.object.GameElement;
import eu.mrndesign.matned.client.model.tools.Vector2D;
import eu.mrndesign.matned.client.view.screencontent.drawer.GameObjView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.mrndesign.matned.client.controller.Constants.*;

public class DrawingCanvas extends AbsolutePanel {

    private final Canvas drawingCanvas;

    private final Context2d drawingCanvasContext;
    private final Controller controller;

    private final Map<String, GameObjView> mapIdToGameObjects = new HashMap<>();
    private final Label degreeLabel;
    private final Label mousePosLabel;
    private final Label mouseActionPosLabel;

    private boolean dragging = false;

    private double actualAngle;

    public DrawingCanvas(Controller controller) {
        this.controller = controller;
        drawingCanvas = Canvas.createIfSupported();
        Image background = new Image(controller.getActiveBackGroundImage());
        add(background, 0, 0);
        getElement().setClassName("drawingArea");
        setWidth(PANEL_WIDTH);
        setHeight(Constants.PANEL_HEIGHT);
        drawingCanvasContext = drawingCanvas.getContext2d();
        createDrawingCanvas();
        initGameObjects();
        mousePosLabel = new Label();
        mouseActionPosLabel = new Label();
        degreeLabel = new Label();
        mousePosLabel.setStyleName("infoLabel");
        mouseActionPosLabel.setStyleName("infoLabel");
        degreeLabel.setStyleName("infoLabel");
        add(mousePosLabel, PANEL_WIDTH_INT / 4, 0);
        add(mouseActionPosLabel, PANEL_WIDTH_INT * 2 / 4, 0);
        add(degreeLabel, PANEL_WIDTH_INT * 3 / 4, 0);
        initListeners();
        setTimer();
        TimeWrapper.getInstance().runTimer();

    }

    private void initListeners() {
        drawingCanvas.addMouseMoveHandler(event -> {
            if (dragging) {
                controller.onCanvasMouseDown(event.getX(), event.getY());
                mouseActionPosLabel.setText("mouseDown->x:" + event.getX() + ",y:" + event.getY());
            }
            controller.onCanvasMouseMove(event.getX(), event.getY());
            mousePosLabel.setText("mouseMove->x:" + event.getX() + ",y:" + event.getY());
            degreeLabel.setText("angle->" + actualAngle +"deg");
        });
        drawingCanvas.addMouseDownHandler(e -> dragging = true);
        drawingCanvas.addMouseUpHandler(e -> dragging = false);
    }

    private void initGameObjects() {
        List<GameElement> allValues = controller.getGameElement();
        manageGameObjectsMap(allValues, new ArrayList<>());
    }

    public void createDrawingCanvas() {
        drawingCanvas.setWidth(PANEL_WIDTH);
        drawingCanvas.setCoordinateSpaceWidth(PANEL_WIDTH_INT);
        drawingCanvas.setHeight(PANEL_HEIGHT);
        drawingCanvas.setCoordinateSpaceHeight(PANEL_HEIGHT_INT);
        CssColor color = CssColor.make("rgba(" + 255 + ", " + 255 + "," + 255 + ", " + 0 + ")");
        drawingCanvasContext.setFillStyle(color);
        add(drawingCanvas, 0, 0);
    }

    private void setTimer() {
        TimeWrapper timeWrapper = TimeWrapper.getInstance();
        timeWrapper.setTimer(new Timer() {
            @Override
            public void run() {
                refreshDrawingCanvas();
            }
        });
    }

    public void refreshDrawingCanvas() {
        drawingCanvasContext.clearRect(0, 0, PANEL_WIDTH_INT, PANEL_HEIGHT_INT);
        if (!controller.gameObjectsStateIsActual(mapIdToGameObjects.keySet())) {
            List<GameElement> newValues = controller.getNewValues(mapIdToGameObjects.keySet());
            List<String> removedKeys = controller.getRemovedKeys(mapIdToGameObjects.keySet());
            manageGameObjectsMap(newValues, removedKeys);
        }
        addAllMappedToCanvas();
        TimeWrapper.getInstance().nextFrame();
    }

    private void addAllMappedToCanvas() {
        mapIdToGameObjects.values().forEach(value -> {
            actualAngle = Math.toRadians(value.getRotationValue());
            double rx = value.getCenterX();
            double ry = value.getCenterY();
            ImageElement img = ImageElement.as(new Image(value.getImageUrl()).getElement());
            drawImageElement(value, rx, ry, img);
        });
    }

    private void drawImageElement(GameObjView value, double rx, double ry, ImageElement img) {
        drawingCanvasContext.translate(rx, ry);
        drawingCanvasContext.rotate(actualAngle);
        drawingCanvasContext.drawImage(img, -value.getWidth()/2, -value.getHeight()/2, value.getWidth(), value.getHeight());
        drawingCanvasContext.rotate(-actualAngle);
        drawingCanvasContext.translate(-rx, -ry);
    }

    private void manageGameObjectsMap(List<GameElement> newValues, List<String> removedKeys) {
        if (removedKeys.size() > 0) {
            removeGameObjects(removedKeys);
        }
        if (newValues.size() > 0) {
            addNewGameObjects(newValues);
        }
    }

    private void removeGameObjects(List<String> removedKeys) {
        removedKeys.forEach(mapIdToGameObjects::remove);
    }

    private void addNewGameObjects(List<GameElement> newValues) {
        newValues.forEach(gameElement -> {
            GameObjView obj = new GameObjView(gameElement);
            mapIdToGameObjects.put(obj.getId(), obj);
        });
    }

}