package danquinndesign.com.smartsweepers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import danquinndesign.com.smartsweepers.views.SweepersView;

public class Sweepers extends AppCompatActivity {
    private static final String TAG = "Sweepers";

    /** View references */
    private SweepersView mSweepersView;
    private TextView mTextGeneration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.sweepers);

        mSweepersView = (SweepersView)findViewById(R.id.sweepers_view);
        mTextGeneration = (TextView)findViewById(R.id.text_generation);
        mTextGeneration.setText("Generation 0");
    }

    /** Reset */

    public void onResetClick(View view) {
        mSweepersView.surfaceDestroyed(mSweepersView.getHolder());
        mSweepersView.surfaceCreated(mSweepersView.getHolder());
    }

    /** Create next generation of sweepers */

    public void onNextGenerationClick(View view) {
        mSweepersView.getSweepersScene().nextGeneration();
        mTextGeneration.setText("Generation " + mSweepersView.getSweepersScene().getGeneration());
    }
}
