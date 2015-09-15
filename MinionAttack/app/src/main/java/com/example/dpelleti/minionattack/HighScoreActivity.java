package com.example.dpelleti.minionattack;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.dpelleti.minionattack.adapter.HighScoreAdapter;
import com.example.dpelleti.minionattack.entities.HighScore;
import com.example.dpelleti.minionattack.managers.HighScoreManager;

import java.util.List;

public class HighScoreActivity extends AppCompatActivity {
    List<HighScore> highScores;
    Context ctx;
    HighScoreAdapter highScoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        ctx = this;
        highScores = HighScoreManager.getAll(ctx);
        highScoreAdapter = new HighScoreAdapter(ctx, R.layout.highscore_layout, highScores);
        ((ListView)findViewById(R.id.lv_highScore)).setAdapter(highScoreAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_score, menu);
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
