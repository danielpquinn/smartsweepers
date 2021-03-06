package danquinndesign.com.smartsweepers.views;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import danquinndesign.com.smartsweepers.objects.SweepersScene;

/**
 * Handles drawing loop
 */
public class SweepersThread extends Thread implements Runnable {
    private static final String TAG = "SweepersThread";

    /** Shoot for 60 frames per second */
    private final static int FRAME_DURATION = 1000 / 60;

    /** Surface holder from surface view */
    private SurfaceHolder mSurfaceHolder;

    /** Scene responsible for drawing screen */
    private SweepersScene mSweepersScene;

    /** Allows us to start or stop this thread from outside */
    private boolean mRunning = false;

    public SweepersThread(SurfaceHolder surfaceHolder, SweepersScene sweepersScene) {
        mSurfaceHolder = surfaceHolder;
        mSweepersScene = sweepersScene;
    }

    /** Update thread state */

    public void setRunning(boolean running) {
        mRunning = running;
    }

    /** Main drawing loop */

    @Override
    public void run() {
        Canvas canvas;
        long beginTime;
        long timeDiff;
        int sleepTime;

        while(mRunning) {

            // Set up loop

            canvas = null;
            beginTime = System.currentTimeMillis();

            // Attempt to draw on canvas

            try {
                canvas = mSurfaceHolder.lockCanvas();

                synchronized (mSurfaceHolder) {
                    mSweepersScene.update();
                    mSweepersScene.draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            // Figure out how much time this loop took, sleep until next frame should run

            timeDiff = System.currentTimeMillis() - beginTime;
            sleepTime = (int)(FRAME_DURATION - timeDiff);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}
