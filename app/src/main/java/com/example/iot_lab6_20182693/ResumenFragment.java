package com.example.iot_lab6_20182693;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ResumenFragment extends Fragment {

    private FirebaseFirestore db;
    private BarChart barChart;
    private PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen, container, false);

        barChart = view.findViewById(R.id.barChart);
        pieChart = view.findViewById(R.id.pieChart);
        db = FirebaseFirestore.getInstance();

        cargarGraficos();

        return view;
    }

    private void cargarGraficos() {
        HashMap<String, Integer> viajesPorMesLinea1 = new HashMap<>();
        HashMap<String, Integer> viajesPorMesLimaPass = new HashMap<>();

        db.collection("movimientos_linea1")
                .get()
                .addOnSuccessListener(linea1Docs -> {
                    for (QueryDocumentSnapshot doc : linea1Docs) {
                        String fecha = doc.getString("fecha");
                        String mes = obtenerMes(fecha);
                        viajesPorMesLinea1.put(mes, viajesPorMesLinea1.getOrDefault(mes, 0) + 1);
                    }

                    db.collection("movimientos_limapass")
                            .get()
                            .addOnSuccessListener(limapassDocs -> {
                                for (QueryDocumentSnapshot doc : limapassDocs) {
                                    String fecha = doc.getString("fecha");
                                    String mes = obtenerMes(fecha);
                                    viajesPorMesLimaPass.put(mes, viajesPorMesLimaPass.getOrDefault(mes, 0) + 1);
                                }

                                mostrarGraficoBarras(viajesPorMesLinea1, viajesPorMesLimaPass);
                                mostrarGraficoTorta(viajesPorMesLinea1, viajesPorMesLimaPass);
                            });
                });
    }

    private String obtenerMes(String fecha) {
        // Asume formato dd/MM/yyyy
        if (fecha != null && fecha.length() >= 7) {
            return fecha.substring(3, 10); // "MM/yyyy"
        }
        return "N/A";
    }

    private void mostrarGraficoBarras(HashMap<String, Integer> linea1, HashMap<String, Integer> limapass) {
        ArrayList<BarEntry> entriesLinea1 = new ArrayList<>();
        ArrayList<BarEntry> entriesLimaPass = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        int index = 0;
        for (String mes : linea1.keySet()) {
            entriesLinea1.add(new BarEntry(index, linea1.get(mes)));
            entriesLimaPass.add(new BarEntry(index, limapass.getOrDefault(mes, 0)));
            labels.add(mes);
            index++;
        }

        BarDataSet set1 = new BarDataSet(entriesLinea1, "Línea 1");
        set1.setColor(Color.BLUE);
        BarDataSet set2 = new BarDataSet(entriesLimaPass, "Lima Pass");
        set2.setColor(Color.GREEN);

        BarData data = new BarData(set1, set2);
        data.setBarWidth(0.4f);

        barChart.setData(data);
        barChart.groupBars(0f, 0.2f, 0.02f);
        barChart.getDescription().setText("Viajes por mes");
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int i = (int) value;
                return i >= 0 && i < labels.size() ? labels.get(i) : "";
            }
        });
        barChart.invalidate();
    }

    private void mostrarGraficoTorta(HashMap<String, Integer> linea1, HashMap<String, Integer> limapass) {
        int totalLinea1 = linea1.values().stream().mapToInt(Integer::intValue).sum();
        int totalLimaPass = limapass.values().stream().mapToInt(Integer::intValue).sum();

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(totalLinea1, "Línea 1"));
        entries.add(new PieEntry(totalLimaPass, "Lima Pass"));

        PieDataSet dataSet = new PieDataSet(entries, "Uso de tarjetas");
        dataSet.setColors(new int[]{Color.BLUE, Color.GREEN});
        PieData pieData = new PieData(dataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setText("Distribución de viajes");
        pieChart.invalidate();
    }
}
