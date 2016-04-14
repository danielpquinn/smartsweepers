package danquinndesign.com.smartsweepers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import danquinndesign.com.smartsweepers.views.SweepersView;

public class Sweepers extends AppCompatActivity {
    private static final String TAG = "Sweepers";

    /** Sweepers view reference */
    private SweepersView mSweepersView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        // SweepersView view = new SweepersView(this);
        // setContentView(view);

        setContentView(R.layout.sweepers);

        mSweepersView = (SweepersView)findViewById(R.id.sweepers_view);
    }

    /** Create next generation of sweepers */

    public void onNextGenerationClick(View view) {
        mSweepersView.nextGeneration();
    }
}
