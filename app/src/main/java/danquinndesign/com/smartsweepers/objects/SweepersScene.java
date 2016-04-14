package danquinndesign.com.smartsweepers.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

import danquinndesign.com.smartsweepers.objects.Sweeper;

/**
 * Manages scene objects and does main render loop
 */
public class SweepersScene {
    private static final String TAG = "SweepersScene";

    private static final int NUM_SWEEPERS = 10;
    private static final int NUM_MINES= 10;
    private static final int BG_COLOR = Color.parseColor("#607D8B");

    /** List of sweepers */
    private ArrayList<Sweeper> mSweepers;

    /** List of mines */
    private ArrayList<Mine> mMines;

    /** Mine quad tree used for collision detection */
    private MineQuadTree mMineQuadTree;

    /** Scene dimensions */
    private int mWidth;
    private int mHeight;

    public SweepersScene() {
        mSweepers = new ArrayList();
        mMines = new ArrayList();

        for (int i = 0; i < NUM_MINES; i += 1) {
            Mine mine = new Mine();
            mMines.add(mine);
        }

        for (int i = 0; i < NUM_SWEEPERS; i += 1) {
            Sweeper sweeper = new Sweeper();
            sweeper.setVelocity((float)Math.random() * 10, (float)Math.random() * 10);
            mSweepers.add(sweeper);
            sweeper.move((int)(Math.random() * 100), (int)(Math.random() * 100));
        }
    }

    /** Update positions of sweepers */

    public void update() {
        for (Sweeper sweeper: mSweepers) {
            if (sweeper.getX() + sweeper.getWidth() > mWidth) {
                sweeper.setVelocity(sweeper.getVX() * -1, sweeper.getVY());
            } else if (sweeper.getY() + sweeper.getHeight() > mHeight) {
                sweeper.setVelocity(sweeper.getVX(), sweeper.getVY() * -1);
            } else if (sweeper.getX() < 0) {
                sweeper.setVelocity(sweeper.getVX() * -1, sweeper.getVY());
            } else if (sweeper.getY() < 0) {
                sweeper.setVelocity(sweeper.getVX(), sweeper.getVY() * -1);
            }
            sweeper.move(sweeper.getX() + sweeper.getVX(), sweeper.getY() + sweeper.getVY());
            checkCollision(sweeper, mMineQuadTree);
        }
    }

    /** Detect collisions between sweeper and mine */

    private void checkCollision(Sweeper sweeper, MineQuadTree quadTree) {

        // drill into overlapping quad trees

        if (sweeper.getX() < quadTree.getX() + quadTree.getWidth() / 2) {
            if (sweeper.getY() < quadTree.getY() + quadTree.getHeight() / 2) {
                if (quadTree.getNW() != null) {
                    checkCollision(sweeper, quadTree.getNW());
                    return;
                }
            } else {
                if (quadTree.getSW() != null) {
                    checkCollision(sweeper, quadTree.getSW());
                    return;
                }
            }
        } else {
            if (sweeper.getY() < quadTree.getY() + quadTree.getHeight() / 2) {
                if (quadTree.getNE() != null) {
                    checkCollision(sweeper, quadTree.getNE());
                    return;
                }
            } else {
                if (quadTree.getSE() != null) {
                    checkCollision(sweeper, quadTree.getSE());
                    return;
                }
            }
        }

        for (Mine mine: quadTree.getMines()) {
            if (mine.getX() < sweeper.getX() + sweeper.getWidth() && mine.getX() + mine.getWidth() > sweeper.getX()) {
                if (mine.getY() < sweeper.getY() + sweeper.getHeight() && mine.getY() + mine.getHeight() > sweeper.getY()) {
                    sweeper.setWidth(sweeper.getWidth() + 5);
                    sweeper.setHeight(sweeper.getHeight() + 5);
                    mine.move((float)Math.random() * mWidth, (float)Math.random() * mHeight);
                    mMineQuadTree = new MineQuadTree(0, 0, mWidth, mHeight, mMines);
                }
            }
        }
    }

    /** Draw sweepers onto canvas */

    public void draw(Canvas canvas) {
        canvas.drawColor(BG_COLOR);
        for (Mine mine: mMines) {
            mine.draw(canvas);
        }
        for (Sweeper sweeper: mSweepers) {
            sweeper.draw(canvas);
        }
    }

    /** Set dimensions */

    public void setDimensions(int width, int height) {
        mWidth = width;
        mHeight = height;
        for (Mine mine: mMines) {
            mine.move((float)Math.random() * mWidth, (float)Math.random() * mHeight);
        }
        mMineQuadTree = new MineQuadTree(0, 0, mWidth, mHeight, mMines);
    }
}
