package concesionario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Concesionario {

    private List<Vehiculo> vehiculos;
    private List<Cliente> clientes;
    private List<Vendedor> vendedores;
    private List<Venta> ventas;

    public Concesionario() {
        this.vehiculos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.vendedores = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void registrarVendedor(Vendedor vendedor) {
        vendedores.add(vendedor);
    }

    public Cliente buscarCliente(String cedula) {
        for (Cliente c : clientes) {
            if (c.getCedula().equalsIgnoreCase(cedula)) {
                return c;
            }
        }
        return null;
    }

    public Vendedor buscarVendedor(String cedula) {
        for (Vendedor v : vendedores) {
            if (v.getCedula().equalsIgnoreCase(cedula)) {
                return v;
            }
        }
        return null;
    }

    public Vehiculo buscarVehiculo(String placa) {
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }

    public List<Vehiculo> vehiculosDisponibles() {
        List<Vehiculo> disponibles = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            if (v.estaDisponible()) {
                disponibles.add(v);
            }
        }
        return disponibles;
    }

    public List<Vehiculo> vehiculosVendidos() {
        List<Vehiculo> vendidos = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            if (v.isVendido()) {
                vendidos.add(v);
            }
        }
        return vendidos;
    }

    public Venta buscarVentaPorPlaca(String placa) {
        for (Venta venta : ventas) {
            if (venta.getVehiculo().getPlaca().equalsIgnoreCase(placa)) {
                return venta;
            }
        }
        return null;
    }

    public Venta realizarVenta(Cliente cliente, Vehiculo vehiculo, Vendedor vendedor,
                                LocalDate fecha, int numeroCuotas) {
        Venta venta = new Venta(cliente, vehiculo, vendedor, fecha, numeroCuotas);
        ventas.add(venta);
        return venta;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    public List<Venta> getVentas() {
        return ventas;
    }
}