package com.example.dtchyman.example;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {


    Button boutonJouer;
    Button bouttonHelp;
    Button bouttonScore;
    Button bouttonQuitter;
    Button bouttonOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boutonJouer = (Button) findViewById(R.id.jouer);
        bouttonHelp = (Button) findViewById(R.id.aide);
        bouttonScore = (Button) findViewById(R.id.score);
        bouttonOptions = (Button) findViewById(R.id.options);
        bouttonQuitter = (Button) findViewById(R.id.sortir);

        bouttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, help.class);
                startActivity(intent);
            }
        });

        bouttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, option.class);
                startActivity(intent);
            }
        });

        bouttonQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }
}
