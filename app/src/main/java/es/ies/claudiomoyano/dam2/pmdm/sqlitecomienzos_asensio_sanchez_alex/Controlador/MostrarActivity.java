package es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.Controlador;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.Modelo.AdaptadorPersona;
import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.Modelo.Persona;
import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.Modelo.RecyclerPersonaInterface;
import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.R;
import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.db.PersonaDAO;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class MostrarActivity extends AppCompatActivity implements RecyclerPersonaInterface {

    PersonaDAO personaDAO;
    ArrayList<Persona> listaPersonas;
    AdaptadorPersona adaptadorPersona;

    private int personaSeleccionada = -1;

    ActivityResultLauncher<Intent> intentResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);


        personaDAO = new PersonaDAO(this);

        listaPersonas = personaDAO.listarPersonas();

        adaptadorPersona = new AdaptadorPersona(listaPersonas, this);

        RecyclerView rvPersonas = findViewById(R.id.rvPersonas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvPersonas.setLayoutManager(linearLayoutManager);

        rvPersonas.setAdapter(adaptadorPersona);
        registerForContextMenu(rvPersonas);


        intentResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == RESULT_OK){
                            if(o.getData() != null && o.getData().getExtras() != null){

                                Intent valoresEditados = o.getData();

                                int posicion = valoresEditados.getIntExtra("posicion", 0);

                                Persona personaEditada = listaPersonas.get(posicion);

                                personaEditada.setNombre(valoresEditados.getStringExtra("nuevoNombre"));
                                personaEditada.setApellidos(valoresEditados.getStringExtra("nuevoApellidos"));
                                personaEditada.setEdad(Integer.parseInt(valoresEditados.getStringExtra("nuevoEdad")));
                                personaEditada.setTelefono(valoresEditados.getStringExtra("nuevoTelefono"));

                                adaptadorPersona.notifyItemChanged(posicion);
                            }
                        }
                    }
                }
        );
    }

    @Override
    public void onItemClick(int posicion) {

    }

    @Override
    public void onItemLongClick(int posicion) {
        personaSeleccionada = posicion;

        // Abrimos el menú contextual manualmente
        openContextMenu(findViewById(R.id.rvPersonas));
    }

    /*MENU CONTEXTUAL*/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final int id = item.getItemId();

        int posicion = personaSeleccionada;

        if(id == R.id.context_editar) {

            Intent intentEditar = new Intent(this, EditarPersonaActivity.class);
            Persona persona = listaPersonas.get(posicion);

            intentEditar.putExtra("posicion", posicion);
            intentEditar.putExtra("id", persona.getId());
            intentEditar.putExtra("nombre", persona.getNombre());
            intentEditar.putExtra("apellidos", persona.getApellidos());
            intentEditar.putExtra("edad", persona.getEdad());
            intentEditar.putExtra("telefono", persona.getTelefono());

            intentResult.launch(intentEditar);

        }else if(id == R.id.context_eliminar) {

            personaDAO.eliminarAlumno(listaPersonas.get(posicion).getId());
            listaPersonas.remove(posicion);
            adaptadorPersona.notifyItemRemoved(posicion);

            Toast.makeText(getApplicationContext(), "Persona eliminada", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }
}
