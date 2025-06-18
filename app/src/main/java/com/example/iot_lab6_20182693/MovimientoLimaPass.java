package com.example.iot_lab6_20182693;

public class MovimientoLimaPass {
    private String idTarjeta;
    private String fecha;
    private String paraderoEntrada;
    private String paraderoSalida;
    private String tiempoViaje;

    public MovimientoLimaPass() {}

    public MovimientoLimaPass(String idTarjeta, String fecha, String paraderoEntrada, String paraderoSalida, String tiempoViaje) {
        this.idTarjeta = idTarjeta;
        this.fecha = fecha;
        this.paraderoEntrada = paraderoEntrada;
        this.paraderoSalida = paraderoSalida;
        this.tiempoViaje = tiempoViaje;
    }

    public String getIdTarjeta() { return idTarjeta; }
    public String getFecha() { return fecha; }
    public String getParaderoEntrada() { return paraderoEntrada; }
    public String getParaderoSalida() { return paraderoSalida; }
    public String getTiempoViaje() { return tiempoViaje; }
}
