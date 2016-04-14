package danquinndesign.com.smartsweepers.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * A single sweeper
 */
public class Sweeper {

    /** Sweeper position */
    private float mX;
    private float mY;

    /** Sweeper velocity */
    private float mVX;
    private float mVY;

    /** Dimensions of sweeper */
    private float mWidth;
    private float mHeight;

    /** Fitness increments each time a mine is encountered */
    private int mFitness;

    /** Paint styles */
    private Paint mPaint;

    public Sweeper() {
        mX = 0;
        mY = 0;
        mVX = 0;
        mVY = 0;
        mFitness = 0;
        mWidth = 30;
        mHeight = 30;
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#03A9F4"));
    }

    /** Move sweeper */

    public void move(float x, float y) {
        mX = x;
        mY = y;
    }

    /** Draw sweeper onto canvas */

    public void draw(Canvas canvas) {
        canvas.drawCircle(mX + mWidth / 2, mY + mHeight / 2, mWidth / 2, mPaint);
    }

    /** Bump up fitness level when sweeper finds a mine */

    public void findMine() {
        mFitness += 1;
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

    public void setWidth(float width) {
        mWidth = width;
    }

    public void setHeight(float height) {
        mHeight= height;
    }

    public float getVX() {
        return mVX;
    }

    public float getVY() {
        return mVY;
    }
}
