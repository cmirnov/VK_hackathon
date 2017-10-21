package org.artoolkit.ar6.artracking;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button run = (Button) findViewById(R.id.run);
        run.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                runAR();
            }
        });
        Button map = (Button) findViewById(R.id.map_button);
        map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                runMap();
            }
        });

        Button test = (Button) findViewById(R.id.buttonTest);
        Button next = (Button) findViewById(R.id.buttonTestNext);
        tv = (TextView) findViewById(R.id.textTest);

        test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Handler.getColor("Rembrant");
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Handler.getColor("Rembrantg");
            }
        });
    }

    private void runAR() {
        startActivity(new Intent(MainActivity.this, ARTrackingActivity.class));
    }

    private void runMap() {
        startActivity(new Intent(MainActivity.this, map.class));
    }

    public static void setText(String txt){
        tv.setText(tv.getText() + "\n"+ txt);
    }
}
