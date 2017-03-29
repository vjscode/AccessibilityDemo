package demo.accessibility.customview.accessibilitydemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import demo.accessibility.customview.accessibilitydemo.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationViewClickListener {

    @NonNull private TextView colorView;
    @NonNull private int[] color = {Color.GREEN, Color.YELLOW};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colorView = (TextView) findViewById(R.id.colorView);
        colorView.setBackgroundColor(color[0]);
        ((NavigationView) findViewById(R.id.navigation_view)).setNavigationViewClickListener(this);
    }

    @Override
    public void navigationClicked(@IntRange(from=0,to=1) int option) {
        colorView.setBackgroundColor(color[option]);
    }
}
