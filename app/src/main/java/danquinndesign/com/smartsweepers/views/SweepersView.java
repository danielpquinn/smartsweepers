package danquinndesign.com.smartsweepers.views;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Displays sweepers and targets
 */
public class SweepersView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "SweepersView";

    /** Sweepers scene instance */
    private SweepersScene mSweepersScene;

    /** Drawing thread */
    private SweepersThread mSweepersThread;

    public SweepersView(Context context) {
        super(context);
        mSweepersScene = new SweepersScene();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSweepersThread = new SweepersThread(getHolder(), this, mSweepersScene);
        mSweepersThread.setRunning(true);
        mSweepersThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        mSweepersScene.setDimensions(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSweepersThread.setRunning(false);
        boolean retry = true;
        while (retry) {
            try {
                mSweepersThread.join();
                retry = false;
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}
