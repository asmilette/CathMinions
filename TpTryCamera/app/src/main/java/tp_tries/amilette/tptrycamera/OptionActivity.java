package tp_tries.amilette.tptrycamera;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class OptionActivity extends AppCompatActivity {

    Button couleur;
    Button quitter;
    Button jouer;
    Context ctx;
    LinearLayout vue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        ctx = this;
        couleur = (Button) findViewById(R.id.choixCouleur);
        quitter = (Button) findViewById(R.id.quitter);
        jouer = (Button) findViewById(R.id.jouer);
        vue = (LinearLayout) findViewById(R.id.Vue);

        couleur.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);

                   alertDialog.setTitle("Change color");

                   alertDialog.setMessage("Choisissez votre couleur!!!");

                   alertDialog.setPositiveButton("Cyan", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           vue.setBackgroundColor(Color.CYAN);
                       }
                   });

                   alertDialog.setNegativeButton("Red", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           vue.setBackgroundColor(Color.RED);
                       }
                   });

                   AlertDialog alertDialog1 = alertDialog.create();

                   alertDialog1.show();
               }
         });
    }
}


