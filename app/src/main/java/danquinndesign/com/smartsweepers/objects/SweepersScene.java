package danquinndesign.com.smartsweepers.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Manages scene objects and does main render loop
 */
public class SweepersScene {
    private static final String TAG = "SweepersScene";

    private static final int NUM_SWEEPERS = 10;
    private static final int NUM_MINES= 50;
    private static final int BG_COLOR = Color.parseColor("#00ABB2");

    /** List of sweepers */
    private ArrayList<Sweeper> mSweepers;

    /** List of mines */
    private ArrayList<Mine> mMines;

    /** Mine quad tree used for collision detection */
    private MineQuadTree mMineQuadTree;

    /** Scene dimensions */
    private int mWidth;
    private int mHeight;

    /** Current generation */
    private int mGeneration;

    public SweepersScene() {
        mSweepers = new ArrayList();
        mMines = new ArrayList();
        mGeneration = 0;

        for (int i = 0; i < NUM_MINES; i += 1) {
            Mine mine = new Mine();
            mMines.add(mine);
        }

        for (int i = 0; i < NUM_SWEEPERS; i += 1) {
            Sweeper sweeper = new Sweeper();
            mSweepers.add(sweeper);
            sweeper.move((int)(Math.random() * mWidth), (int)(Math.random() * mHeight));
        }
    }

    /** Update positions of sweepers */

    public void update() {
        for (Sweeper sweeper: mSweepers) {
            if (sweeper.getX() < 0) { sweeper.move(mWidth, sweeper.getY()); }
            if (sweeper.getX() > mWidth) { sweeper.move(0, sweeper.getY()); }
            if (sweeper.getY() < 0) { sweeper.move(sweeper.getX(), mHeight); }
            if (sweeper.getY() > mHeight) { sweeper.move(sweeper.getX(), 0); }
            compareObjects(sweeper, mMineQuadTree);
        }
    }

    /** Detect collisions between sweeper and mine */

    private void compareObjects(Sweeper sweeper, MineQuadTree quadTree) {

        if (quadTree == null) {
            Log.d(TAG, "Quad tree is null :(");
            return;
        }

        // drill into overlapping quad trees

        if (sweeper.getX() < quadTree.getX() + quadTree.getWidth() / 2) {
            if (sweeper.getY() < quadTree.getY() + quadTree.getHeight() / 2) {
                if (quadTree.getNW() != null) {
                    compareObjects(sweeper, quadTree.getNW());
                    return;
                }
            } else {
                if (quadTree.getSW() != null) {
                    compareObjects(sweeper, quadTree.getSW());
                    return;
                }
            }
        } else {
            if (sweeper.getY() < quadTree.getY() + quadTree.getHeight() / 2) {
                if (quadTree.getNE() != null) {
                    compareObjects(sweeper, quadTree.getNE());
                    return;
                }
            } else {
                if (quadTree.getSE() != null) {
                    compareObjects(sweeper, quadTree.getSE());
                    return;
                }
            }
        }

        for (Mine mine: quadTree.getMines()) {
            if (mine.getX() < sweeper.getX() + sweeper.getWidth() && mine.getX() + mine.getWidth() > sweeper.getX()) {
                if (mine.getY() < sweeper.getY() + sweeper.getHeight() && mine.getY() + mine.getHeight() > sweeper.getY()) {
                    sweeper.findMine();
                    mine.move((float)Math.random() * mWidth, (float)Math.random() * mHeight);
                    mMineQuadTree = new MineQuadTree(0, 0, mWidth, mHeight, mMines);
                }
            }
        }

        sweeper.react(quadTree.getMines(), mWidth, mHeight);
    }

    /** Create the next generation */

    public void nextGeneration() {

        ArrayList<Sweeper> newSweepers = new ArrayList();

        // Sort sweepers by fitness

        Comparator byFitness = new Comparator<Sweeper>() {
            @Override
            public int compare(Sweeper s1, Sweeper s2) {
            return s2.getFitness() - s1.getFitness();
            }
        };

        Collections.sort(mSweepers, byFitness);

        // Create new new individuals from fittest members of population

        for (int i = 0; i < mSweepers.size() - 2; i += 1) {
            Sweeper s1 = mSweepers.get((int)Math.floor(Math.random() * mSweepers.size() * Math.random()));
            Sweeper s2 = mSweepers.get((int)Math.floor(Math.random() * mSweepers.size() * Math.random()));
            Sweeper newSweeper = combineSweepers(s1, s2);
            newSweeper.move((float)Math.random() * mWidth, (float)Math.random() * mHeight);
            newSweepers.add(newSweeper);
        }

        // Keep top performing sweepers from each generation

        newSweepers.add(mSweepers.get(0));
        newSweepers.add(mSweepers.get(1));

        // Mutate sweeper's weights randomly to introduce new behavior

        for (Sweeper sweeper: mSweepers) {
            sweeper.mutateWeights();
        }

        mSweepers = newSweepers;

        mGeneration += 1;
    }

    /** Combine two sweepers */

    private static Sweeper combineSweepers(Sweeper s1, Sweeper s2) {
        Sweeper newSweeper = new Sweeper();

        // Create a new set of weights that is a combination of s1 and s2 weights

        ArrayList<Float> s1Weights = s1.getNeuralNet().getWeights();
        ArrayList<Float> s2Weights = s2.getNeuralNet().getWeights();

        int crossover = (int)Math.floor(Math.random() * s1Weights.size());

        ArrayList<Float> newWeights = new ArrayList();

        for (int j = 0; j < s1Weights.size(); j += 1) {
            newWeights.add(j < crossover ? s1Weights.get(j) : s2Weights.get(j));
        }

        newSweeper.getNeuralNet().setWeights(newWeights);

        return newSweeper;
    }

    /** Draw sweepers onto canvas */

    public void draw(Canvas canvas) {
        canvas.drawColor(BG_COLOR);
        for (Mine mine: mMines) { mine.draw(canvas); }
        for (Sweeper sweeper: mSweepers) { sweeper.draw(canvas); }
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

    /** Getters and setters */

    public int getGeneration() {
        return mGeneration;
    }
}
