package es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.Modelo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import es.ies.claudiomoyano.dam2.pmdm.sqlitecomienzos_asensio_sanchez_alex.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorPersona extends RecyclerView.Adapter<AdaptadorPersona.PersonaViewHolder>{
        private List<Persona> listaPersonas;
        private final RecyclerPersonaInterface recyclerPersonaInterface;
        public AdaptadorPersona(List<Persona> listaPersonas, RecyclerPersonaInterface recyclerPersonaInterface) {
            this.listaPersonas = listaPersonas;
            this.recyclerPersonaInterface = recyclerPersonaInterface;
        }

        @NonNull
        @Override
        public PersonaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater from = LayoutInflater.from(parent.getContext());
            View inflate = from.inflate(R.layout.item_persona, parent, false);
            return new PersonaViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull PersonaViewHolder holder, int position) {
            Persona persona = this.listaPersonas.get(position);

            holder.nombre.setText(persona.getNombre());
            holder.apellidos.setText(persona.getApellidos());
            holder.edad.setText(String.valueOf(persona.getEdad()));
        }

        @Override
        public int getItemCount() {
            return this.listaPersonas.size();
        }

        public class PersonaViewHolder extends RecyclerView.ViewHolder {
            public TextView nombre;
            public TextView apellidos;
            public TextView edad;

            public PersonaViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre = itemView.findViewById(R.id.nombrePersona);
                apellidos = itemView.findViewById(R.id.apellidosPersona);
                edad = itemView.findViewById(R.id.edadPersona);

                // Click normal
                itemView.setOnClickListener(view -> {
                    if (recyclerPersonaInterface != null) {
                        int pos = getAbsoluteAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerPersonaInterface.onItemClick(pos);
                        }
                    }
                });

                // Long click - Menú contextual
                itemView.setOnLongClickListener(view -> {
                    if (recyclerPersonaInterface != null) {
                        int pos = getAbsoluteAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerPersonaInterface.onItemLongClick(pos);  // Aquí se pasará la posición
                        }
                    }
                    return true;  // Indicamos que manejamos el evento
                });
            }
        }
}
