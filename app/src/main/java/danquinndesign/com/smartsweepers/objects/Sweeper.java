package danquinndesign.com.smartsweepers.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

import danquinndesign.com.smartsweepers.neuralnet.NeuralNet;

/**
 * A single sweeper
 */
public class Sweeper {
    private static final String TAG = "Sweeper";

    /** Brain! */
    private NeuralNet mNeuralNet;

    /** Sweeper position */
    private float mX;
    private float mY;

    /** Rotation, controls direction of movement */
    private float mRotation;

    /** How fast the sweeper moves */
    private float mSpeed;

    /** Dimensions of sweeper */
    private float mWidth;
    private float mHeight;

    /** Fitness increments each time a mine is encountered */
    private int mFitness;

    /** Paint styles */
    private Paint mPaint;

    public Sweeper() {

        /*
         * Two inputs are x and y distance to nearest mine.
         * Output is number between -1 and 1 which determines rotation.
         */

        mNeuralNet = new NeuralNet(3, 1, 2, 3);
        mNeuralNet.setSigmoidSlope(0.5f);

        mRotation = 0;
        mSpeed = 4;
        mX = 0;
        mY = 0;
        mFitness = 0;
        mWidth = 30;
        mHeight = 30;
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#29F6FF"));
    }

    /** Update direction based on distance from nearest mine */

    public void react(ArrayList<Mine> mines) {
        double minDist = 1000000;
        Mine closestMine = mines.get(0);

        for (Mine mine: mines) {
            float distX = mX - mine.getX();
            float distY = mY - mine.getY();
            double dist = Math.sqrt(distX * distX + distY * distY);
            if (dist < minDist) {
                minDist = dist;
                closestMine = mine;
            }
        }

        float[] inputs = { mRotation, mX - closestMine.getX(), mY - closestMine.getY() };
        float[] outputs = mNeuralNet.getOutput(inputs);

        mRotation = outputs[0] * 360;

        float dirX = (float)Math.cos(Math.toRadians((double)mRotation));
        float dirY = (float)Math.sin(Math.toRadians((double)mRotation));

        // Normalize direction vector

        float dirLength = (float)Math.sqrt(dirX * dirX + dirY * dirY);

        dirX = dirX / dirLength;
        dirY = dirY / dirLength;

        move(mX + dirX * mSpeed, mY + dirY * mSpeed);
    }

    /** Move sweeper */

    public void move(float x, float y) {
        mX = x;
        mY = y;
    }

    /** Mutate weights in neural net at random */

    public void mutateWeights() {
        ArrayList<Float> weights = mNeuralNet.getWeights();
        ArrayList<Float> newWeights = new ArrayList();

        for (Float weight: weights) {
            newWeights.add(Math.random() < 0.1 ? (float)Math.random() : weight);
        }

        mNeuralNet.setWeights(newWeights);
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

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }

    public int getFitness() {
        return mFitness;
    }

    public NeuralNet getNeuralNet() {
        return mNeuralNet;
    }
}
