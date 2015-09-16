package com.example.dpelleti.minionattack;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
}
