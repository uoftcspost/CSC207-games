package uoft.csc207.gameapplication.Games.MazeGame;

import android.graphics.Canvas;
import android.graphics.Color;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Callable;

import uoft.csc207.gameapplication.Games.GameDriver;
import uoft.csc207.gameapplication.Games.MazeGame.Controller.MazeController;
import uoft.csc207.gameapplication.Games.MazeGame.Controller.SwipeController;
import uoft.csc207.gameapplication.Games.MazeGame.Controller.TapController;
import uoft.csc207.gameapplication.Games.MazeGame.GameLogic.MazeGame;
import uoft.csc207.gameapplication.Games.MazeGame.Presenter.MazePresenter;

import static java.lang.Thread.sleep;

public class MazeGameDriver extends GameDriver {
    private String controllerType = "Tap";

    private MazeGame mazeGame;
    private MazeController mazeController;
    private MazePresenter mazePresenter;
    /**
     * cursors initial x and y position when pressed down on the screen
     */
    private int xInit;
    private int yInit;

    /**
     * creates the MazeGameDriver that accesses the MazeGame
     *
     */
    public MazeGameDriver() {
        mazeGame = new MazeGame();
    }

    /**
     * sets the init x and y position
     *
     * @param x coordinate of press
     * @param y coordinate of press
     */
    public void touchStart(float x, float y) {
        executeCommand(mazeController.touchStart(x, y));
    }

    /**
     * calls the MazeGame movement methods
     * @param x where the x position of the cursor when moved
     * @param y where the y position of the cursor when moved
     */
    public void touchMove(float x, float y) {
        executeCommand(mazeController.touchMove(x, y));
    }

    public void touchUp() {
        executeCommand(mazeController.touchUp());
    }

    private void executeCommand(int control) {
        switch (control) {
            case 1:
                mazeGame.moveUp();
                break;
            case 2:
                mazeGame.moveRight();
                break;
            case 3:
                mazeGame.moveDown();
                break;
            case 4:
                mazeGame.moveLeft();
                break;
            default:
                break;
        }
    }

    @Override
    public void timeUpdate() {
        mazeGame.update();
    }

    /**
     * draws onto the canvas
     *
     * @param canvas the place where the picture will be drawn on
     */
    public void draw(Canvas canvas) {
        newCanvas.save();
        newCanvas.drawColor(Color.WHITE);
        newCanvas.scale(1.01f, 1.01f);

        Character[][] mazeArrayRepresentation = mazeGame.getMaze();
        mazePresenter.drawMap(newCanvas, mazeArrayRepresentation, mazeGame.getCharacterPos());

        canvas.drawBitmap(bitmap, 0, 0, null);

        newCanvas.restore();
    }

    /**
     * @return whether the game is over or not
     */
    public boolean getGameIsOver() {
        return mazeGame.getGameIsOver();
    }

    /**
     *
     * @return the total points accumulated in the mazegame
     */
    public int getPoints() {
        return mazeGame.getPoints();
    }

    @Override
    public void init() {
        if (controllerType.equalsIgnoreCase("tap")) {
            mazeController = new TapController(screenWidth, screenHeight);
        }
        else if (controllerType.equalsIgnoreCase("swipe")) {
            mazeController = new SwipeController();
        }
        else {
            mazeController = new TapController(screenWidth, screenHeight);
        }
        mazePresenter = new MazePresenter(screenWidth, screenHeight);
    }

    @Override
    public void setConfigurations(JSONObject configurations) {
        super.setConfigurations(configurations);

        try {
            controllerType = configurations.getString("controls");
        } catch (JSONException e) {

        }
    }
}
