package com.example.dpelleti.minionattack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dpelleti.minionattack.R;
import com.example.dpelleti.minionattack.entities.HighScore;

import java.util.List;

/**
 * Created by dpelleti on 2015-09-10.
 */
public class HighScoreAdapter extends ArrayAdapter<HighScore> {
    Context ctx;
    int resource;
    public HighScoreAdapter(Context ctx, int resource, List<HighScore> highScores) {
        super(ctx, 0);
        this.ctx = ctx;
        this.resource = resource;
        for(HighScore highScore : highScores) {
            this.add(highScore);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HighScore highScore = getItem(position);

        if(convertView == null) {
            LayoutInflater li = LayoutInflater.from(ctx);
            convertView = li.inflate(resource, null);
        }

        ((TextView)convertView.findViewById(R.id.tv_score)).setText(String.valueOf(highScore.getScore()));
        ((TextView)convertView.findViewById(R.id.tv_name)).setText(highScore.getName());

        return convertView;
    }
}
