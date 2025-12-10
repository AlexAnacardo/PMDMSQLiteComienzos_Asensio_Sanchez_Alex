package es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.db;

import android.provider.BaseColumns;

public final class PersonaContract {
    private PersonaContract(){}

    public static class PersonaEntry implements BaseColumns {
        public static final String TABLE_NAME = "persona";

        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_APELLIDOS = "apellidos";
        public static final String COLUMN_EDAD = "edad";
    }
}
