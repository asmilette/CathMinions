package com.example.dpelleti.minionattack.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dpelleti on 2015-09-10.
 */
public class BDConnection {
    private static SQLiteDatabase db;
    private static int version = 1;
    private static String DBName = "catchMinion";

    public static SQLiteDatabase getBD(Context ctx) {
        SQLiteDatabase db = null;
        db = new GestionBD(ctx, DBName, null, version).getWritableDatabase();
        return db;
    }
}
