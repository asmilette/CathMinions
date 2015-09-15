package com.example.dpelleti.minionattack;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.dpelleti.minionattack.entities.HighScore;
import com.example.dpelleti.minionattack.entities.HighScoreDialog;
import com.example.dpelleti.minionattack.entities.Minion;
import com.example.dpelleti.minionattack.threads.MoveMinion;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Minion> minions = new ArrayList<>();
    FrameLayout main_layout;
    Handler handler;
    Context ctx;
    HighScore highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_main);
        main_layout = (FrameLayout)findViewById(R.id.main_layout);
        handler = new Handler();
       /* ((Button)findViewById(R.id.btn_pause)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = handler.obtainMessage();
                message.obj = new String("KILL");
                handler.dispatchMessage(message);
            }
        });*/
        Random rand = new Random();
        minions.add(new Minion(this, rand, 1));
        minions.add(new Minion(this, rand, 2));
        minions.add(new Minion(this, rand, 5));

        for(Minion m : minions)
            main_layout.addView(m);

        MoveMinion moveMinion = new MoveMinion(this, handler, minions);
        handler.post(moveMinion);

        int score = rand.nextInt(100);
        HighScoreDialog dialog = new HighScoreDialog(ctx, score);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                highScore = ((HighScoreDialog) dialog).getHighScore();
                System.out.println(highScore.toString());
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
