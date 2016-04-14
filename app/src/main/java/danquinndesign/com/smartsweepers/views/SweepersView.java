package danquinndesign.com.smartsweepers.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import danquinndesign.com.smartsweepers.objects.SweepersScene;

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
        init();
    }

    public SweepersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SweepersView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /** Genreate next generation */

    private void init() {
        mSweepersScene = new SweepersScene();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSweepersThread = new SweepersThread(getHolder(), mSweepersScene);
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

    /** Getters and setters */

    public SweepersScene getSweepersScene() {
        return mSweepersScene;
    }
}
