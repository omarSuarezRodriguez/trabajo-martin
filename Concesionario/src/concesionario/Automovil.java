package concesionario;

public class Automovil extends Vehiculo {

    private boolean exhibicion;

    public Automovil(String placa, String modelo, double precio, boolean exhibicion) {
        super(placa, modelo, precio);
        this.exhibicion = exhibicion;
    }

    @Override
    public boolean puedeVenderse() {
        // Un automóvil de exhibición no está a la venta mientras dure la muestra.
        return !exhibicion;
    }

    public boolean isExhibicion() {
        return exhibicion;
    }
}
