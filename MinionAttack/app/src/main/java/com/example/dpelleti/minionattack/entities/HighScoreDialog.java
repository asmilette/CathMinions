package com.example.dpelleti.minionattack.entities;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dpelleti.minionattack.R;

/**
 * Created by dpelleti on 2015-09-15.
 */
public class HighScoreDialog extends Dialog {
    Context ctx;
    HighScore highScore = null;
    EditText ed_highScore_dialog_name;

    public HighScoreDialog(Context ctx, final int score) {
        super(ctx);
        this.ctx = ctx;

        LayoutInflater li = LayoutInflater.from(ctx);
        LinearLayout ll = (LinearLayout)li.inflate(R.layout.high_score_dialog, null);

        setContentView(ll);

        setTitle(R.string.addHighScore);

        ((TextView)ll.findViewById(R.id.tv_highScore_dialog_score)).setText(String.valueOf(score));
        ed_highScore_dialog_name = (EditText)ll.findViewById(R.id.ed_highScore_dialog_name);

        ((Button)ll.findViewById(R.id.btn_highScore_dialog_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highScore = new HighScore(score, ed_highScore_dialog_name.getText().toString());
                dismiss();
            }
        });

        ((Button)ll.findViewById(R.id.btn_highScore_dialog_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public HighScore getHighScore() {
        return highScore;
    }
}
