package com.example.iot_lab6_20182693;

public class MovimientoLinea1 {
    private String idTarjeta;
    private String fecha;
    private String estacionEntrada;
    private String estacionSalida;
    private String tiempoViaje;

    public MovimientoLinea1() {}  // Necesario para Firestore

    public MovimientoLinea1(String idTarjeta, String fecha, String estacionEntrada, String estacionSalida, String tiempoViaje) {
        this.idTarjeta = idTarjeta;
        this.fecha = fecha;
        this.estacionEntrada = estacionEntrada;
        this.estacionSalida = estacionSalida;
        this.tiempoViaje = tiempoViaje;
    }

    public String getIdTarjeta() { return idTarjeta; }
    public String getFecha() { return fecha; }
    public String getEstacionEntrada() { return estacionEntrada; }
    public String getEstacionSalida() { return estacionSalida; }
    public String getTiempoViaje() { return tiempoViaje; }
}
