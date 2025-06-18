package com.example.iot_lab6_20182693;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Linea1Fragment extends Fragment {

    private EditText etIdTarjeta, etFecha, etEntrada, etSalida, etTiempo;
    private Button btnGuardar;
    private TextView tvMovimientos;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linea1, container, false);

        etIdTarjeta = view.findViewById(R.id.etIdTarjeta);
        etFecha = view.findViewById(R.id.etFecha);
        etEntrada = view.findViewById(R.id.etEntrada);
        etSalida = view.findViewById(R.id.etSalida);
        etTiempo = view.findViewById(R.id.etTiempo);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        tvMovimientos = view.findViewById(R.id.tvMovimientos);

        db = FirebaseFirestore.getInstance();

        btnGuardar.setOnClickListener(v -> guardarMovimiento());
        obtenerMovimientos();

        return view;
    }

    private void guardarMovimiento() {
        String id = etIdTarjeta.getText().toString();
        String fecha = etFecha.getText().toString();
        String entrada = etEntrada.getText().toString();
        String salida = etSalida.getText().toString();
        String tiempo = etTiempo.getText().toString();

        MovimientoLinea1 mov = new MovimientoLinea1(id, fecha, entrada, salida, tiempo);

        db.collection("movimientos_linea1")
                .add(mov)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getActivity(), "Movimiento guardado", Toast.LENGTH_SHORT).show();
                    obtenerMovimientos();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void obtenerMovimientos() {
        db.collection("movimientos_linea1")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    StringBuilder builder = new StringBuilder();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        MovimientoLinea1 m = doc.toObject(MovimientoLinea1.class);
                        builder.append("ID: ").append(m.getIdTarjeta()).append("\n")
                                .append("Fecha: ").append(m.getFecha()).append("\n")
                                .append("Entrada: ").append(m.getEstacionEntrada()).append("\n")
                                .append("Salida: ").append(m.getEstacionSalida()).append("\n")
                                .append("Tiempo: ").append(m.getTiempoViaje()).append(" min\n\n");
                    }
                    tvMovimientos.setText(builder.toString());
                });
    }
}
