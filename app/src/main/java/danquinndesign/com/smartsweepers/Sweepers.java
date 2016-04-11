package danquinndesign.com.smartsweepers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import danquinndesign.com.smartsweepers.utils.GeneticAlgorithm;

public class Sweepers extends AppCompatActivity {
    private static final String TAG = "AppCompatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweepers);
    }
}
