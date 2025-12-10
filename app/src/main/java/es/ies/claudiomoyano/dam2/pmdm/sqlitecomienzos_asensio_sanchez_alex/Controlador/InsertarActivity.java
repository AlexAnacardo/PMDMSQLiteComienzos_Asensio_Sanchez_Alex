package es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.Controlador;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.Modelo.Persona;
import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.R;
import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.db.PersonaDAO;

public class InsertarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insertar);

        PersonaDAO personaDAO = new PersonaDAO(this);

        Button botonAniadir = findViewById(R.id.botonAniadir);
        EditText nombre = findViewById(R.id.etNombre);
        EditText apellidos = findViewById(R.id.etApellidos);
        EditText edad = findViewById(R.id.etEdad);

        botonAniadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Persona p = new Persona(nombre.getText().toString(), apellidos.getText().toString(), Integer.parseInt(edad.getText().toString()));

                if(personaDAO.insertarPersona(p)!=-1){
                    Toast.makeText(v.getContext(), "Insertado", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(v.getContext(), "Error al insertar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
