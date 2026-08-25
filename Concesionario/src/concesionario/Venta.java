package concesionario;

import java.time.LocalDate;

/**
 * Representa la venta financiada de un vehículo: quién lo compra,
 * quién hizo la venta, en cuántas cuotas mensuales y cuántas de
 * esas cuotas ya se han pagado.
 */
public class Venta {

    private Cliente cliente;
    private Vehiculo vehiculo;
    private Vendedor vendedor;
    private LocalDate fechaVenta;
    private double precioTotal;
    private int numeroCuotas;
    private double valorCuota;
    private int cuotasPagadas;
    private LocalDate fechaUltimoPago;

    public Venta(Cliente cliente, Vehiculo vehiculo, Vendedor vendedor,
                 LocalDate fechaVenta, int numeroCuotas) {

        if (!vehiculo.estaDisponible()) {
            throw new IllegalStateException("El vehículo no está disponible para la venta.");
        }
        if (numeroCuotas <= 0) {
            throw new IllegalArgumentException("El número de cuotas debe ser mayor a cero.");
        }

        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.vendedor = vendedor;
        this.fechaVenta = fechaVenta;
        this.precioTotal = vehiculo.getPrecio();
        this.numeroCuotas = numeroCuotas;
        this.valorCuota = precioTotal / numeroCuotas;
        this.cuotasPagadas = 0;
        this.fechaUltimoPago = null;

        vehiculo.marcarVendido();
    }

    public void registrarPago(LocalDate fecha) {
        if (estaCompletamentePagada()) {
            throw new IllegalStateException("Esta venta ya fue pagada en su totalidad.");
        }
        if (fecha.isBefore(fechaVenta)) {
            throw new IllegalArgumentException("La fecha de pago no puede ser anterior a la fecha de venta.");
        }
        this.cuotasPagadas++;
        this.fechaUltimoPago = fecha;
    }

    public boolean estaCompletamentePagada() {
        return cuotasPagadas >= numeroCuotas;
    }

    public int getCuotasPendientes() {
        return numeroCuotas - cuotasPagadas;
    }

    public double getSaldoPendiente() {
        return getCuotasPendientes() * valorCuota;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public int getNumeroCuotas() {
        return numeroCuotas;
    }

    public double getValorCuota() {
        return valorCuota;
    }

    public int getCuotasPagadas() {
        return cuotasPagadas;
    }

    public LocalDate getFechaUltimoPago() {
        return fechaUltimoPago;
    }
}
