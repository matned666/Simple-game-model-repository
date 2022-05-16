package eu.mrndesign.matned.client.view.screencontent.game;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import eu.mrndesign.matned.client.controller.Constants;
import eu.mrndesign.matned.client.controller.Controller;
import eu.mrndesign.matned.client.controller.TimeWrapper;
import eu.mrndesign.matned.client.model.game.object.GameElement;
import eu.mrndesign.matned.client.model.game.object.element.obj.BreakBear;
import eu.mrndesign.matned.client.model.tools.Log;
import eu.mrndesign.matned.client.view.screencontent.drawer.GameObjView;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static eu.mrndesign.matned.client.controller.Constants.*;

public class DrawingCanvas extends AbsolutePanel {

    protected final Logger logger;

    private final Canvas drawingCanvas;

    private final Context2d drawingCanvasContext;
    private final Controller controller;

    private final Map<String, GameObjView> mapIdToGameObjects = new HashMap<>();
    private final Label mousePosLabel;
    private final Label additionalLabel;

    private int actualX;
    private int actualY;

    private boolean dragging;
    private GameObjView breakBear;

    private final Subject<Boolean> pauseSubject = PublishSubject.create();

    public DrawingCanvas(Controller controller) {
        this.controller = controller;
        controller.setDrawingCanvas(this);
        logger = Logger.getLogger("DrawingCanvas:");
        drawingCanvas = Canvas.createIfSupported();
        Image background = new Image(controller.getActiveBackGroundImage());
        background.setHeight(PANEL_HEIGHT);
        background.setWidth(PANEL_WIDTH);
        add(background, 0, 0);
        getElement().setClassName("drawingArea");
        setWidth(PANEL_WIDTH);
        setHeight(Constants.PANEL_HEIGHT);
        drawingCanvasContext = drawingCanvas.getContext2d();
        drawingCanvas.setStyleName("gameObject");
        createDrawingCanvas();
        addGameObjects();
        mousePosLabel = new Label();
        Label mouseActionPosLabel = new Label();
        additionalLabel = new Label();
        mousePosLabel.setStyleName("infoLabel");
        mouseActionPosLabel.setStyleName("infoLabel");
        additionalLabel.setStyleName("infoLabel");
        add(mousePosLabel, PANEL_WIDTH_INT / 4, 0);
        add(mouseActionPosLabel, PANEL_WIDTH_INT * 2 / 4, 0);
        add(additionalLabel, PANEL_WIDTH_INT * 3 / 4, 0);
        initListeners();
        initPauseImage();
        setTimer();
    }

    private void initListeners() {
        drawingCanvas.addMouseMoveHandler(event -> {
            controller.onCanvasMouseMove(event.getX(), event.getY());
            mousePosLabel.setText("mouseMove->x:" + event.getX() + ",y:" + event.getY());
            actualX = event.getX();
            actualY = event.getY();
        });
        drawingCanvas.addMouseDownHandler(e -> dragging=true);
        drawingCanvas.addMouseUpHandler(e -> dragging=false);
        drawingCanvas.addMouseOutHandler(e -> dragging=false);
        drawingCanvas.addMouseOverHandler(e -> dragging=false);
        drawingCanvas.addKeyDownHandler(this::onKeyDown);
        pauseSubject.map(pause -> {
            boolean b = TimeWrapper.getInstance().startStop();
            breakAction(b);
            return b;
        }).subscribe();
    }

    private void initPauseImage() {
        breakBear = new GameObjView(new BreakBear(null));
    }

    private void onKeyDown(KeyDownEvent event) {
        switch (event.getNativeKeyCode()) {
            case KeyCodes.KEY_P:
                Log.log("sdsd");
                pauseSubject.onNext(true);
            default:
                controller.onKeyPressed(event.getNativeKeyCode());
        }
    }

    public void breakAction(boolean pause) {
        if (pause) {
            drawImageElement(breakBear);
        }
    }

    private void addGameObjects() {
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
        List<String> removedKeys = controller.getRemovedKeys(mapIdToGameObjects.keySet());
        controller.onCanvasRefresh();
        if (dragging) {
            controller.onCanvasMouseDown(actualX, actualY);
        }
        additionalLabel.setText("timer->" + TimeWrapper.getInstance().getFrameNo());
        drawingCanvasContext.clearRect(0, 0, PANEL_WIDTH_INT, PANEL_HEIGHT_INT);
        if (!controller.gameObjectsStateIsActual(mapIdToGameObjects.keySet())) {
            List<GameElement> newValues = controller.getNewValues(mapIdToGameObjects.keySet());
            manageGameObjectsMap(newValues, removedKeys);
        }
        addAllMappedToCanvas();
        TimeWrapper.getInstance().nextFrame();
    }

    private void addAllMappedToCanvas() {
        mapIdToGameObjects.values().forEach(this::drawImageElement);
    }

    private void drawImageElement(GameObjView value) {
        double actualAngle = Math.toRadians(value.getRotationValue());
        double rx = value.getCenterX();
        double ry = value.getCenterY();
        ImageElement img = value.getImage();
        drawingCanvasContext.translate(rx, ry);
        drawingCanvasContext.rotate(actualAngle);
        drawingCanvasContext.drawImage(img, -value.getWidth() / 2, -value.getHeight() / 2, value.getWidth(), value.getHeight());
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
