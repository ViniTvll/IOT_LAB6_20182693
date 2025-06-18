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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LimaPassFragment extends Fragment {

    private EditText etIdTarjetaPass, etFechaPass, etParaderoEntrada, etParaderoSalida, etTiempoPass;
    private Button btnGuardarPass;
    private TextView tvMovimientosPass;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lima_pass, container, false);

        etIdTarjetaPass = view.findViewById(R.id.etIdTarjetaPass);
        etFechaPass = view.findViewById(R.id.etFechaPass);
        etParaderoEntrada = view.findViewById(R.id.etParaderoEntrada);
        etParaderoSalida = view.findViewById(R.id.etParaderoSalida);
        etTiempoPass = view.findViewById(R.id.etTiempoPass);
        btnGuardarPass = view.findViewById(R.id.btnGuardarPass);
        tvMovimientosPass = view.findViewById(R.id.tvMovimientosPass);

        db = FirebaseFirestore.getInstance();

        btnGuardarPass.setOnClickListener(v -> guardarMovimiento());
        obtenerMovimientos();

        return view;
    }

    private void guardarMovimiento() {
        String id = etIdTarjetaPass.getText().toString();
        String fecha = etFechaPass.getText().toString();
        String entrada = etParaderoEntrada.getText().toString();
        String salida = etParaderoSalida.getText().toString();
        String tiempo = etTiempoPass.getText().toString();

        MovimientoLimaPass mov = new MovimientoLimaPass(id, fecha, entrada, salida, tiempo);

        db.collection("movimientos_limapass")
                .add(mov)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getActivity(), "Movimiento guardado", Toast.LENGTH_SHORT).show();
                    obtenerMovimientos();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void obtenerMovimientos() {
        db.collection("movimientos_limapass")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    StringBuilder builder = new StringBuilder();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        MovimientoLimaPass m = doc.toObject(MovimientoLimaPass.class);
                        builder.append("ID: ").append(m.getIdTarjeta()).append("\n")
                                .append("Fecha: ").append(m.getFecha()).append("\n")
                                .append("Entrada: ").append(m.getParaderoEntrada()).append("\n")
                                .append("Salida: ").append(m.getParaderoSalida()).append("\n")
                                .append("Tiempo: ").append(m.getTiempoViaje()).append(" min\n\n");
                    }
                    tvMovimientosPass.setText(builder.toString());
                });
    }
}
