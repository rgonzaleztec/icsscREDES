/**
 * Created by Jimmy on 28/04/2016.
 */
package com.TecnologicoDeCostaRica.RedesestimoteIlb;
public class ScanDTO {
    String nombre;
    String hora;
    String fecha;
    boolean in_out;

    public ScanDTO(String nombre, String hora, String fecha, boolean in_out) {
        this.nombre = nombre;
        this.hora = hora;
        this.fecha = fecha;
        this.in_out = in_out;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isIn_out() {
        return in_out;
    }

    public void setIn_out(boolean in_out) {
        this.in_out = in_out;
    }
}
