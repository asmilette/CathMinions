package tp_tries.amilette.tptrycamera.entite;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import tp_tries.amilette.tptrycamera.R;


/**
 * Created by dpelleti on 2015-09-15.
 */
public class HighScoreDialog extends Dialog {

    public interface PositiveListener{
        void onPositiveClick(String name);
    }

    public interface NegativeListener {
        void onNegativeClick();
    }

    private Context ctx;
    private EditText ed_highScore_dialog_name;
    private PositiveListener positiveListener;
    private NegativeListener negativeListener;
    private Dialog me;

    public HighScoreDialog(Context ctx, int score) {
        super(ctx);
        this.ctx = ctx;
        me = this;

        LayoutInflater li = LayoutInflater.from(ctx);
        LinearLayout ll = (LinearLayout)li.inflate(R.layout.high_score_dialog, null);

        setContentView(ll);

        setTitle(R.string.addHighScore);

        ((TextView)ll.findViewById(R.id.tv_highScore_dialog_score)).setText(String.valueOf(score));

        ed_highScore_dialog_name = (EditText)ll.findViewById(R.id.ed_highScore_dialog_name);

        ((Button)ll.findViewById(R.id.btn_highScore_dialog_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(positiveListener != null)
                    positiveListener.onPositiveClick(ed_highScore_dialog_name.getText().toString());
                dismiss();
            }
        });

        ((Button)ll.findViewById(R.id.btn_highScore_dialog_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(negativeListener != null)
                    negativeListener.onNegativeClick();
                dismiss();
            }
        });

        setCanceledOnTouchOutside(false);
    }

    public void setPositiveListener(PositiveListener positiveListener) {
        this.positiveListener = positiveListener;
    }

    public void setNegativeListener(NegativeListener negativeListener) {
        this.negativeListener = negativeListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
