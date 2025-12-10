package es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.Modelo.Persona;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "personas.db";
    public static final int DB_VERSION = 1;

    private static DBHelper instance;

    public DBHelper(Context context){
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context){
        if(instance==null){
            instance = new DBHelper(context);
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table "+ PersonaContract.PersonaEntry.TABLE_NAME+" ("+
                PersonaContract.PersonaEntry._ID+" integer primary key autoincrement, "+
                PersonaContract.PersonaEntry.COLUMN_NOMBRE+" text, "+
                PersonaContract.PersonaEntry.COLUMN_APELLIDOS+" text, "+
                PersonaContract.PersonaEntry.COLUMN_EDAD+" integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists persona");
        onCreate(db);
    }
}
