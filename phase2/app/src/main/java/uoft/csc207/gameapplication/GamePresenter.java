package uoft.csc207.gameapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * Presents the game to the player, by drawing to canvas.
 */
public abstract class GamePresenter {
    private int screenWidth, screenHeight;

    private Bitmap bitmap;
    private Canvas bitCanvas;

    public void init(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        bitCanvas = new Canvas(bitmap);
    }

    public abstract void draw(Canvas canvas);

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public Bitmap getBitmap(){return bitmap;}

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.bitCanvas = new Canvas(bitmap);
    }

    public Canvas getBitCanvas() {
        return bitCanvas;
    }

}