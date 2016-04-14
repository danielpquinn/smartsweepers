package danquinndesign.com.smartsweepers.objects;

import android.util.Log;

import java.util.ArrayList;

/**
 * Used in collision detection
 */
public class MineQuadTree {
    private static final String TAG = "MineQuadTree";

    /** Mines in this tree */
    private ArrayList<Mine> mMines;

    /** Quadrant dimensions */
    private float mX;
    private float mY;
    private float mWidth;
    private float mHeight;

    /** Child quadrants */
    private MineQuadTree mNW;
    private MineQuadTree mNE;
    private MineQuadTree mSE;
    private MineQuadTree mSW;

    public MineQuadTree(float x, float y, float width, float height, ArrayList<Mine> mines) {

        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;
        mMines = mines;

        ArrayList<Mine> minesNW = new ArrayList();
        ArrayList<Mine> minesNE = new ArrayList();
        ArrayList<Mine> minesSW = new ArrayList();
        ArrayList<Mine> minesSE = new ArrayList();

        if (mines.size() < 10 || samePositions(mines)) {
            return;
        }

        float quadWidth = mWidth / 2;
        float quadHeight = mHeight / 2;

        // Add mines to appropriate quadrants

        for (Mine mine: mines) {
            if (mine.getX() - x < quadWidth) {
                if (mine.getY() - y < quadHeight) {
                    minesNW.add(mine);
                } else {
                    minesSW.add(mine);
                }
            } else {
                if (mine.getY() - y < quadHeight) {
                    minesNE.add(mine);
                } else {
                    minesSE.add(mine);
                }
            }
        }

        // Check for mines in each quadrant, create new quad tree if they exist

        if (minesNW.size() > 0) {
            mNW = new MineQuadTree(mX, mY, quadWidth, quadHeight, minesNW);
        }
        if (minesNE.size() > 0) {
            mNE = new MineQuadTree(mX + quadWidth, mY, quadWidth, quadHeight, minesNE);
        }
        if (minesSE.size() > 0) {
            mSE = new MineQuadTree(mX + quadWidth, mY + quadHeight, quadWidth, quadHeight, minesSE);
        }
        if (minesSW.size() > 0) {
            mSW = new MineQuadTree(mX, mY + quadHeight, quadWidth, quadHeight, minesSW);
        }
    }

    /** Check to see if mines are in exactly the same position */

    private static boolean samePositions(ArrayList<Mine> mines) {
        boolean same = true;
        float startX;
        float startY;

        if (mines.size() < 1) { return true; }

        startX = mines.get(0).getX();
        startY = mines.get(0).getY();

        for (Mine mine: mines) {
            if (mine.getX() != startX || mine.getY() != startY) {
                same = false;
            }
        }

        return same;
    }

    /** Getters and setters. Are these necessary? */

    public ArrayList<Mine> getMines() {
        return mMines;
    }

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

    public MineQuadTree getNW() {
        return mNW;
    }

    public MineQuadTree getNE() {
        return mNE;
    }

    public MineQuadTree getSE() {
        return mSE;
    }

    public MineQuadTree getSW() {
        return mSW;
    }
}
