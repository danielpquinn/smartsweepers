package danquinndesign.com.smartsweepers.views;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;

/**
 * Manages scene objects and does main render loop
 */
public class SweepersScene {

    /** List of sweepers */
    private ArrayList<Sweeper> mSweepers;

    /** Scene dimensions */
    private int mWidth;
    private int mHeight;

    public SweepersScene() {
        mSweepers = new ArrayList();

        for (int i = 0; i < 10; i += 1) {
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
        }
    }

    /** Draw sweepers onto canvas */

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for (Sweeper sweeper: mSweepers) {
            sweeper.draw(canvas);
        }
    }

    /** Set dimensions */

    public void setDimensions(int width, int height) {
        mWidth = width;
        mHeight = height;
    }
}
