package concesionario;

public class Motocicleta extends Vehiculo {

    public Motocicleta(String placa, String modelo, double precio) {
        super(placa, modelo, precio);
    }

    @Override
    public boolean puedeVenderse() {
        // A diferencia de los automóviles, ninguna motocicleta
        // se reserva para exhibición: todas están a la venta.
        return true;
    }
}
