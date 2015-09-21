package tp.android.catchminion.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dpelleti on 2015-09-10.
 */
public class GestionBD extends SQLiteOpenHelper {
    public static String HIGH_SCORE__TABLE__NAME = "tb_high_score";
    public static String HIGH_SCORE__ID = "id";
    public static String HIGH_SCORE__SCORE = "score";
    public static String HIGH_SCORE__NAME = "name";
    public static String CREATE_TABLE = "CREATE TABLE " + HIGH_SCORE__TABLE__NAME
            + "(" + HIGH_SCORE__ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HIGH_SCORE__SCORE + " INTEGER, "
            + HIGH_SCORE__NAME + " TEXT);";

    private static String DROP_TABLE = "DROP TABLE IF EXISTS "+ HIGH_SCORE__TABLE__NAME;

    public GestionBD(Context ctx, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(ctx, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
