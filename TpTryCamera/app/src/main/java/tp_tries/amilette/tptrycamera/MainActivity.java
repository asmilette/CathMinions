package tp_tries.amilette.tptrycamera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import tp_tries.amilette.tptrycamera.services.MyService;

public class MainActivity extends AppCompatActivity {


    Button boutonJouer;
    Button bouttonHelp;
    Button bouttonScore;
    Button bouttonQuitter;
    Button bouttonOptions;

    Intent demarreService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boutonJouer = (Button) findViewById(R.id.jouer);
        bouttonHelp = (Button) findViewById(R.id.aide);
        bouttonScore = (Button) findViewById(R.id.score);
        bouttonOptions = (Button) findViewById(R.id.options);
        bouttonQuitter = (Button) findViewById(R.id.sortir);

        //*********On click des boutons*********
        boutonJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);

            }
        });

        bouttonScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HighScoreActivity.class);
                startActivity(intent);
            }
        });

        bouttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        bouttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OptionActivity.class);
                startActivity(intent);
            }
        });

        bouttonQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //android.os.Process.killProcess(android.os.Process.myPid());
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //********Service music ouverture*******
        demarreService = new Intent(MainActivity.this, MyService.class);
        startService(demarreService);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(demarreService);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
