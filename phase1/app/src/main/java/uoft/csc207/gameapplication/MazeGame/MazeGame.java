package uoft.csc207.gameapplication.MazeGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public class MazeGame {
    private MazeGenerator mazeGenerator;

    public Character[][] maze;

    private int xCharacter;
    private int yCharacter;
    private int xEndPos = 0;
    private int yEndPos = 0;

    private int currentLevel;

    private boolean gameIsOver;

    private Paint wallPaint = new Paint();
    private Paint endPaint = new Paint();
    private Paint startPaint = new Paint();

    private long startTime;
    private int points = 0;
    private int currentLevelPoints = 2000;

    public MazeGame() {
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStyle(Paint.Style.FILL);
        wallPaint.setStrokeWidth(1);
        endPaint.setColor(Color.RED);
        endPaint.setStyle(Paint.Style.FILL);
        endPaint.setStrokeWidth(1);
        startPaint.setColor(Color.GREEN);
        startPaint.setStyle(Paint.Style.FILL);
        startPaint.setStrokeWidth(1);

        mazeGenerator = new MazeGenerator(7, 13);

        xCharacter = mazeGenerator.getStartingPoint()[0];
        yCharacter = mazeGenerator.getStartingPoint()[1];

        xEndPos = mazeGenerator.getEndPoint()[0];
        yEndPos = mazeGenerator.getEndPoint()[1];

        maze = mazeGenerator.getMaze();

        currentLevel = 1;
        gameIsOver = false;
        startTime = System.currentTimeMillis();
    }

    void moveDown() {

        if (!maze[xCharacter][yCharacter + 1].equals('W')) {
            maze[xCharacter][yCharacter + 1] = maze[xCharacter][yCharacter];
            maze[xCharacter][yCharacter] = 'P';
            yCharacter += 1;
            checkEndpointReached();
        }
    }

    void moveUp() {
        if (!maze[xCharacter][yCharacter - 1].equals('W')) {
            maze[xCharacter][yCharacter - 1] = maze[xCharacter][yCharacter];
            maze[xCharacter][yCharacter] = 'P';
            yCharacter -= 1;
            checkEndpointReached();
        }
    }

    void moveLeft() {
        if (!maze[xCharacter - 1][yCharacter].equals('W')) {
            maze[xCharacter - 1][yCharacter] = maze[xCharacter][yCharacter];
            maze[xCharacter][yCharacter] = 'P';
            xCharacter -= 1;
            checkEndpointReached();
        }
    }

    void moveRight() {
        if (!maze[xCharacter + 1][yCharacter].equals('W')) {
            maze[xCharacter + 1][yCharacter] = maze[xCharacter][yCharacter];
            maze[xCharacter][yCharacter] = 'P';
            xCharacter += 1;
            checkEndpointReached();
        }
    }

    boolean getGameIsOver() {
        return gameIsOver;
    }

    int getPoints() {
        if (currentLevelPoints > 0) {
            return points + currentLevelPoints;
        }
        else {
            return points;
        }
    }

    private void checkEndpointReached() {
        if (xCharacter == xEndPos && yCharacter == yEndPos) {
            xEndPos = -1;
            yEndPos = -1;
            calculatePoints();
            if (currentLevel == 1) {
                gameIsOver = true;
                // temporarily print game is over
            }
            else {
                currentLevel += 1;
                mazeGenerator.newMaze();

                maze = mazeGenerator.getMaze();
                xCharacter = mazeGenerator.getStartingPoint()[0];
                yCharacter = mazeGenerator.getStartingPoint()[1];
                xEndPos = mazeGenerator.getEndPoint()[0];
                yEndPos = mazeGenerator.getEndPoint()[1];
            }
        }
    }
    void draw(Canvas canvas, int screenWidth, int screenHeight) {
        int blockWidth = screenWidth / maze.length;
        int blockHeight = screenHeight / maze[0].length;

        long timeTaken = System.currentTimeMillis() - startTime;
        currentLevelPoints = (int)(2000 - (timeTaken / 1000 * 60));

        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[0].length; y++) {
                Rect rect = new Rect(x * blockWidth, y*blockHeight,
                        (x + 1) * blockWidth, (y + 1)  * blockHeight);
                if (maze[x][y].equals('W')) {
                    canvas.drawRect(rect, wallPaint);
                }
                else if (maze[x][y].equals('E')) {
                    canvas.drawRect(rect, endPaint);
                }
                else if (maze[x][y].equals('S')) {
                    canvas.drawRect(rect, startPaint);
                }
            }
        }
    }

    private void calculatePoints() {
        long timeTaken = System.currentTimeMillis() - startTime;
        currentLevelPoints = (int)(2000 - (timeTaken / 1000 * 60));
        if (currentLevelPoints > 0) {
            points += currentLevelPoints;
        }
        startTime = System.currentTimeMillis();
    }
}
