package tp_tries.amilette.tptrycamera;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import tp_tries.amilette.tptrycamera.adapter.HighScoreAdapter;
import tp_tries.amilette.tptrycamera.entite.HighScore;
import tp_tries.amilette.tptrycamera.manager.HighScoreManager;

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

        //Ajoutez un message s'il y a aucun highScore
        highScoreAdapter = new HighScoreAdapter(ctx, R.layout.highscore_layout, highScores);
        ((ListView)findViewById(R.id.lv_highScore)).setAdapter(highScoreAdapter);
    }
}
