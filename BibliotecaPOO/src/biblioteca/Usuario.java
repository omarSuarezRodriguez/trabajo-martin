package biblioteca;

import java.time.LocalDate;

public class Usuario {

    private String identificacion;
    private String nombre;
    private LocalDate sancionadoHasta;

    public Usuario(String identificacion, String nombre) {

        this.identificacion = identificacion;
        this.nombre = nombre;
        this.sancionadoHasta = null;
    }

    public boolean estaSancionado(LocalDate fecha) {

        if (sancionadoHasta == null) {

            return false;
        }

        return fecha.isBefore(sancionadoHasta);
    }

    public void sancionar(LocalDate fecha) {

        sancionadoHasta = fecha.plusDays(7);
    }

    public String getIdentificacion() {

        return identificacion;
    }

    public String getNombre() {

        return nombre;
    }

    public LocalDate getSancionadoHasta() {

        return sancionadoHasta;
    }
}