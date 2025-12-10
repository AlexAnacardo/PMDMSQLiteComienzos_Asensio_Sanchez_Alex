package es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.Modelo.Persona;

public class PersonaDAO {

    private DBHelper dbHelper;

    public PersonaDAO(Context context) {
        this.dbHelper = DBHelper.getInstance(context);
    }

    public long insertarPersona(Persona p) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PersonaContract.PersonaEntry.COLUMN_NOMBRE, p.getNombre());
        values.put(PersonaContract.PersonaEntry.COLUMN_APELLIDOS, p.getApellidos());
        values.put(PersonaContract.PersonaEntry.COLUMN_EDAD, p.getEdad());
        values.put(PersonaContract.PersonaEntry.COLUMN_TELEFONO, p.getTelefono());

        long resultado = db.insert(PersonaContract.PersonaEntry.TABLE_NAME, null, values);

        db.close();
        return resultado;
    }

    public ArrayList<Persona> listarPersonas(){
        ArrayList<Persona> lista = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                PersonaContract.PersonaEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(PersonaContract.PersonaEntry._ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(PersonaContract.PersonaEntry.COLUMN_NOMBRE));
                String apellidos = cursor.getString(cursor.getColumnIndexOrThrow(PersonaContract.PersonaEntry.COLUMN_APELLIDOS));
                int edad = cursor.getInt(cursor.getColumnIndexOrThrow(PersonaContract.PersonaEntry.COLUMN_EDAD));
                String telefono = cursor.getString(cursor.getColumnIndexOrThrow(PersonaContract.PersonaEntry.COLUMN_TELEFONO));

                Persona p = new Persona(nombre, apellidos, edad, telefono);
                p.setId(id);
                lista.add(p);

            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return lista;
    }

    public long eliminarAlumno(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long resultado = db.delete(PersonaContract.PersonaEntry.TABLE_NAME, "_id = ?",  new String[]{ String.valueOf(id) });

        db.close();
        return resultado;
    }

    public long actualizarPersona(int id, String nombre, String apellidos, int edad, String telefono){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PersonaContract.PersonaEntry.COLUMN_NOMBRE, nombre);
        values.put(PersonaContract.PersonaEntry.COLUMN_APELLIDOS, apellidos);
        values.put(PersonaContract.PersonaEntry.COLUMN_EDAD, edad);
        values.put(PersonaContract.PersonaEntry.COLUMN_TELEFONO, telefono);


        long resultado = db.update(
                PersonaContract.PersonaEntry.TABLE_NAME,
                values,
                "_id = ?",
                new String[]{ String.valueOf(id) }
        );

        db.close();
        return resultado;
    }

    public ArrayList<Persona> buscarPersonas(int edadMin, int edadMax, String texto, String filtroNombre) {
        ArrayList<Persona> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = PersonaContract.PersonaEntry.COLUMN_EDAD + " BETWEEN ? AND ?";
        ArrayList<String> selectionArgsList = new ArrayList<>();
        selectionArgsList.add(String.valueOf(edadMin));
        selectionArgsList.add(String.valueOf(edadMax));

        if (texto != null && !texto.isEmpty()) {
            switch (filtroNombre) {
                case "empiezaPor":
                    selection += " AND " + PersonaContract.PersonaEntry.COLUMN_NOMBRE + " LIKE ?";
                    selectionArgsList.add(texto + "%");
                    break;
                case "contiene":
                    selection += " AND " + PersonaContract.PersonaEntry.COLUMN_NOMBRE + " LIKE ?";
                    selectionArgsList.add("%" + texto + "%");
                    break;
                case "buscar":
                    selection += " AND " + PersonaContract.PersonaEntry.COLUMN_NOMBRE + " = ?";
                    selectionArgsList.add(texto);
                    break;
            }
        }

        String[] selectionArgs = selectionArgsList.toArray(new String[0]);

        Cursor cursor = db.query(
                PersonaContract.PersonaEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(PersonaContract.PersonaEntry._ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(PersonaContract.PersonaEntry.COLUMN_NOMBRE));
                String apellidos = cursor.getString(cursor.getColumnIndexOrThrow(PersonaContract.PersonaEntry.COLUMN_APELLIDOS));
                int edad = cursor.getInt(cursor.getColumnIndexOrThrow(PersonaContract.PersonaEntry.COLUMN_EDAD));
                String telefono = cursor.getString(cursor.getColumnIndexOrThrow(PersonaContract.PersonaEntry.COLUMN_TELEFONO));

                Persona p = new Persona(nombre, apellidos, edad, telefono);
                p.setId(id);
                lista.add(p);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return lista;
    }

}
