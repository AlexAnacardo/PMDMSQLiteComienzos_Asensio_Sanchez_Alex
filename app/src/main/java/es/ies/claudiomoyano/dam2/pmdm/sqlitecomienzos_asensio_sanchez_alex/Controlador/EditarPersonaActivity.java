package es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.Controlador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.R;
import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.db.PersonaDAO;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EditarPersonaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PersonaDAO personaDAO = new PersonaDAO(this);

        setContentView(R.layout.activity_editar);

        Intent intentPadre = getIntent();

        int posicion = intentPadre.getIntExtra("posicion", 0);
        int id = intentPadre.getIntExtra("id", 0);

        String nombre = intentPadre.getStringExtra("nombre");
        String apellidos = intentPadre.getStringExtra("apellidos");
        int edad = intentPadre.getIntExtra("edad", 0);


        EditText etNombre = findViewById(R.id.etNombre);
        EditText etApellidos = findViewById(R.id.etApellidos);
        EditText etEdad = findViewById(R.id.etEdad);



        etNombre.setText(nombre);
        etApellidos.setText(apellidos);
        etEdad.setText(String.valueOf(edad));


        Button botonAceptar = findViewById(R.id.botonEditar);

        botonAceptar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                personaDAO.actualizarPersona(id, etNombre.getText().toString(), etApellidos.getText().toString(), Integer.parseInt(etEdad.getText().toString()));

                Intent intent = new Intent();

                intent.putExtra("posicion", posicion);
                intent.putExtra("nuevoNombre", etNombre.getText().toString());
                intent.putExtra("nuevoApellidos", etApellidos.getText().toString());
                intent.putExtra("nuevoEdad", etEdad.getText().toString());

                setResult(RESULT_OK, intent);

                finish();

            }
        });
    }

}
