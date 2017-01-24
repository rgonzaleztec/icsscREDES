package cr.ac.itcr.tecweather.model;

/**
 * Created by usuario on 22/12/2016.
 */
public class Weather {

    private float altitud;
    private float latitud;
    private float presion;
    private float tempGrados;
    private float tempFarenheit;

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    private float humedad;
    private float luminozidad;
    private float dirVientoAngulo;
    private float velocidadVientomph;
    private long fecha;

    public float getAltitud() {
        return altitud;
    }

    public void setAltitud(float altitud) {
        this.altitud = altitud;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getPresion() {
        return presion;
    }

    public void setPresion(float presion) {
        this.presion = presion;
    }

    public float getTempGrados() {
        return tempGrados;
    }

    public void setTempGrados(float tempGrados) {
        this.tempGrados = tempGrados;
    }

    public float getTempFarenheit() {
        return tempFarenheit;
    }

    public void setTempFarenheit(float tempFarenheit) {
        this.tempFarenheit = tempFarenheit;
    }

    public float getHumedad() {
        return humedad;
    }

    public void setHumedad(float humedad) {
        this.humedad = humedad;
    }

    public float getLuminozidad() {
        return luminozidad;
    }

    public void setLuminozidad(float luminozidad) {
        this.luminozidad = luminozidad;
    }

    public float getDirVientoAngulo() {
        return dirVientoAngulo;
    }

    public void setDirVientoAngulo(float dirVientoAngulo) {
        this.dirVientoAngulo = dirVientoAngulo;
    }

    public float getVelocidadVientomph() {
        return velocidadVientomph;
    }

    public void setVelocidadVientomph(float velocidadVientomph) {
        this.velocidadVientomph = velocidadVientomph;
    }

}
