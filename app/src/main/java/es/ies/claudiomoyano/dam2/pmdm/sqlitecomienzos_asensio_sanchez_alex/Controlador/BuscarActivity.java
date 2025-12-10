package es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.Controlador;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.Modelo.Persona;
import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.R;
import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.db.PersonaDAO;

public class BuscarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar);


        SeekBar seekBarEdadMin = findViewById(R.id.seekBarEdadMin);
        SeekBar seekBarEdadMax = findViewById(R.id.seekBarEdadMax);
        TextView tvEdadMin = findViewById(R.id.tvEdadMin);
        TextView tvEdadMax = findViewById(R.id.tvEdadMax);
        EditText etNombre = findViewById(R.id.etNombre);
        RadioGroup rgFiltroNombre = findViewById(R.id.rgFiltroNombre);
        Button botonBuscar = findViewById(R.id.botonBuscar);

        botonBuscar.setOnClickListener(v -> {
            int edadMin = seekBarEdadMin.getProgress();
            int edadMax = seekBarEdadMax.getProgress();
            String nombre = etNombre.getText().toString();
            int selectedId = rgFiltroNombre.getCheckedRadioButtonId();

            String filtroNombre = "";
            if (selectedId == R.id.rbEmpiezaPor) filtroNombre = "empiezaPor";
            else if (selectedId == R.id.rbContiene) filtroNombre = "contiene";
            else if (selectedId == R.id.rbBuscar) filtroNombre = "buscar";

            Intent intentListar = new Intent(this, MostrarActivity.class);

            intentListar.putExtra("busqueda", true);
            intentListar.putExtra("edadMinima", edadMin);
            intentListar.putExtra("edadMaxima", edadMax);
            intentListar.putExtra("nombre", nombre);
            intentListar.putExtra("filtro", filtroNombre);

            startActivity(intentListar);

        });


        // SeekBar Edad Min
        seekBarEdadMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvEdadMin.setText("Edad mínima: " + progress);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // SeekBar Edad Max
        seekBarEdadMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvEdadMax.setText("Edad máxima: " + progress);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
