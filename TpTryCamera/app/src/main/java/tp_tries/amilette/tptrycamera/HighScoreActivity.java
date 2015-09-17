package tp_tries.amilette.tptrycamera;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
        if(highScores.size() > 0)
            findViewById(R.id.tv_msgNoHighscore).setVisibility(View.GONE);

        //Ajoutez un message s'il y a aucun highScore
        highScoreAdapter = new HighScoreAdapter(ctx, R.layout.highscore_layout, highScores);
        ((ListView)findViewById(R.id.lv_highScore)).setAdapter(highScoreAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscore, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clearHighscore) {
            HighScoreManager.clear(ctx);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
