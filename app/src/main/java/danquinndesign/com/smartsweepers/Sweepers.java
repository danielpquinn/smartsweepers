package danquinndesign.com.smartsweepers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import danquinndesign.com.smartsweepers.views.SweepersView;

public class Sweepers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        SweepersView view = new SweepersView(this);
        setContentView(view);
    }
}
