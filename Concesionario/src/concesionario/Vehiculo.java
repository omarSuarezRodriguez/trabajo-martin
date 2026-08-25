package concesionario;

/**
 * Vehículo del inventario del concesionario. Es abstracta porque
 * un "vehículo" genérico no se instancia: siempre es un Automóvil
 * o una Motocicleta, y cada tipo decide su propia elegibilidad
 * para la venta.
 */
public abstract class Vehiculo {

    private String placa;
    private String modelo;
    private double precio;
    private boolean vendido;

    public Vehiculo(String placa, String modelo, double precio) {
        this.placa = placa;
        this.modelo = modelo;
        this.precio = precio;
        this.vendido = false;
    }

    public abstract boolean puedeVenderse();

    public boolean estaDisponible() {
        return !vendido && puedeVenderse();
    }

    public void marcarVendido() {
        this.vendido = true;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isVendido() {
        return vendido;
    }
}
