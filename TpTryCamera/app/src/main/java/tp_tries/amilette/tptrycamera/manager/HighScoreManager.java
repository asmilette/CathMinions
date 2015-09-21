package tp_tries.amilette.tptrycamera.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tp_tries.amilette.tptrycamera.entite.HighScore;
import tp_tries.amilette.tptrycamera.service.BDConnection;
import tp_tries.amilette.tptrycamera.service.GestionBD;

/**
 * Created by dpelleti on 2015-09-10.

 Le système contiendra au maximum 10 Pointages.
  Si < 10 dans la DB alors on ajoute
  Si = 10 dans la DB alors on update
  Si > 10 Il ne faut pas que sa arrive
 */
public class HighScoreManager {

    //Permet d'ajouter un pointage
    public static HighScore add(Context ctx, HighScore highScore) {
        SQLiteDatabase db = BDConnection.getBD(ctx);
        ContentValues cv = new ContentValues();
        cv.put(GestionBD.HIGH_SCORE__SCORE, highScore.getScore());
        cv.put(GestionBD.HIGH_SCORE__NAME, highScore.getName());
        highScore.setId((int) db.insert(GestionBD.HIGH_SCORE__TABLE__NAME, null, cv));
        db.close();
        return highScore;
    }

    //Permet de mettre à jour un pointage (Au lieu de supprimer, et ensuite ajouter)
    public static boolean update(Context ctx, HighScore highScore) {
        SQLiteDatabase db = BDConnection.getBD(ctx);
        ContentValues cv = new ContentValues();
        cv.put(GestionBD.HIGH_SCORE__SCORE, highScore.getScore());
        cv.put(GestionBD.HIGH_SCORE__NAME, highScore.getName());
        String whereClause = GestionBD.HIGH_SCORE__ID + " = ?";
        String[] whrereArgs = {String.valueOf(highScore.getId())};
        boolean isSucces = db.update(GestionBD.HIGH_SCORE__TABLE__NAME, cv, whereClause, whrereArgs) > 0;
        return isSucces;
    }

    //Retourne une liste de de pointage trié par ordre croissant
    public static List<HighScore> getAll(Context ctx) {
        ArrayList<HighScore> highScores = new ArrayList<>();
        SQLiteDatabase db = BDConnection.getBD(ctx);
        Cursor c = db.query(GestionBD.HIGH_SCORE__TABLE__NAME
                , new String[] { GestionBD.HIGH_SCORE__ID, GestionBD.HIGH_SCORE__SCORE, GestionBD.HIGH_SCORE__NAME}
                , null, null, null, null, GestionBD.HIGH_SCORE__ID + " DESC");
        while(c.moveToNext()) {
            highScores.add(new HighScore(
                c.getInt(0)
                , c.getInt(1)
                , c.getString(2))
            );
        }
        db.close();
        return highScores;
    }

    public static boolean clear(Context ctx) {
        SQLiteDatabase db = BDConnection.getBD(ctx);
        boolean isSucces = db.delete(GestionBD.HIGH_SCORE__TABLE__NAME, null, null) > 0;
        db.close();
        return isSucces;
    }
}
