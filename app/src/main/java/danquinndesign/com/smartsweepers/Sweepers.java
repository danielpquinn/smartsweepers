package danquinndesign.com.smartsweepers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import danquinndesign.com.smartsweepers.views.SweepersView;

public class Sweepers extends AppCompatActivity {
    private static final String TAG = "Sweepers";

    /** Run next generation on ui thread */

    final Runnable mNextGenerationRunnable = new Runnable() {
        public void run() {
            nextGeneration();
        }
    };

    /** New generation timer */

    private class GenerationTimerTask extends TimerTask {
        public void run() {
            runOnUiThread(mNextGenerationRunnable);
        }
    }

    /** Seekbar callback */

    private int mGeneration = 0;
    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            float slope = (float)progress / 100 * 2;
            mSweepersView.getSweepersScene().setSigmoidSlopes(slope);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };
    private Timer mGenerationTimer = new Timer();

    private SweepersView mSweepersView;
    private TextView mTextGeneration;
    private SeekBar mSeekBarSlope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.sweepers);

        mSweepersView = (SweepersView)findViewById(R.id.sweepers_view);
        mTextGeneration = (TextView)findViewById(R.id.text_generation);
        mSeekBarSlope = (SeekBar)findViewById(R.id.seekbar_slope);

        mSeekBarSlope.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mTextGeneration.setText("Generation " + (mGeneration + 1));
        mGenerationTimer.scheduleAtFixedRate(new GenerationTimerTask(), 0, 20000);
    }

    /** Reset */

    public void onResetClick(View view) {
        mSweepersView.stopThread();
        mSweepersView.createScene();
        mGeneration = 0;
        mTextGeneration.setText("Generation " + (mGeneration + 1));
        mGenerationTimer.cancel();
        mGenerationTimer = new Timer();
        mGenerationTimer.scheduleAtFixedRate(new GenerationTimerTask(), 0, 20000);
    }

    /** Create next generation of sweepers */

    public void onNextGenerationClick(View view) {
        mGenerationTimer.cancel();
        mGenerationTimer = new Timer();
        mGenerationTimer.scheduleAtFixedRate(new GenerationTimerTask(), 0, 20000);
        nextGeneration();
    }

    /** Go to next generation */

    private void nextGeneration() {
        if (mSweepersView.getSweepersScene() == null) { return; }
        mSweepersView.getSweepersScene().nextGeneration();
        mGeneration += 1;
        mTextGeneration.setText("Generation " + (mGeneration + 1));
    }
}
