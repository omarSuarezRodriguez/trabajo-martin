package concesionario;

/**
 * Empleado del concesionario que gestiona una venta.
 * Se modela aparte de Cliente porque representa un rol distinto:
 * quien realiza la venta, no quien la compra.
 */
public class Vendedor {

    private String cedula;
    private String nombre;

    public Vendedor(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }
}
