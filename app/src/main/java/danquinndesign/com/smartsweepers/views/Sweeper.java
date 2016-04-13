package danquinndesign.com.smartsweepers.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * A single sweeper
 */
public class Sweeper {
    private static final String TAG = "Sweeper";

    /** Sweeper position */
    private float mX;
    private float mY;

    /** Sweeper velocity */
    private float mVX;
    private float mVY;

    /** Dimensions of sweeper */
    private float mWidth;
    private float mHeight;

    /** Paint styles */
    private Paint mPaint;

    public Sweeper() {
        mX = 0;
        mY = 0;
        mVX = 0;
        mVY = 0;
        mWidth = 30;
        mHeight = 30;
        mPaint = new Paint();
        mPaint.setARGB(255, 255, 255, 255);
    }

    /** Move sweeper */

    public void move(float x, float y) {
        mX = x;
        mY = y;
    }

    /** Draw sweeper onto canvas */

    public void draw(Canvas canvas) {
        canvas.drawRect(mX, mY, mX + mWidth, mY + mHeight, mPaint);
    }

    /** Getters and setters */

    public float getX() {
        return mX;
    }

    public float getY() {
        return mY;
    }

    public void setVelocity(float x, float y) {
        mVX = x;
        mVY = y;
    }

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }

    public float getVX() {
        return mVX;
    }

    public float getVY() {
        return mVY;
    }
}
