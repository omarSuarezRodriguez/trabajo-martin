package concesionario;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("No se soporta UTF-8");
        }

        Concesionario concesionario = new Concesionario();
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarCliente(concesionario);
                    break;
                case 2:
                    registrarVendedor(concesionario);
                    break;
                case 3:
                    registrarAutomovil(concesionario);
                    break;
                case 4:
                    registrarMotocicleta(concesionario);
                    break;
                case 5:
                    mostrarVehiculosDisponibles(concesionario);
                    break;
                case 6:
                    mostrarVehiculosVendidos(concesionario);
                    break;
                case 7:
                    realizarVenta(concesionario);
                    break;
                case 8:
                    registrarPago(concesionario);
                    break;
                case 9:
                    mostrarVentas(concesionario);
                    break;
                case 0:
                    System.out.println("Finalizando sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("=================================");
        System.out.println("     SISTEMA DE CONCESIONARIO");
        System.out.println("=================================");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Registrar vendedor");
        System.out.println("3. Registrar automóvil en inventario");
        System.out.println("4. Registrar motocicleta en inventario");
        System.out.println("5. Ver vehículos disponibles");
        System.out.println("6. Ver vehículos vendidos");
        System.out.println("7. Realizar venta financiada");
        System.out.println("8. Registrar pago de cuota");
        System.out.println("9. Ver historial de ventas");
        System.out.println("0. Salir");
    }

    private static void registrarCliente(Concesionario concesionario) {
        System.out.println("--- REGISTRAR CLIENTE ---");
        System.out.print("Cédula: ");
        String cedula = teclado.nextLine();

        if (concesionario.buscarCliente(cedula) != null) {
            System.out.println("El cliente ya existe.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        concesionario.registrarCliente(new Cliente(cedula, nombre));
        System.out.println("Cliente registrado correctamente.");
    }

    private static void registrarVendedor(Concesionario concesionario) {
        System.out.println("--- REGISTRAR VENDEDOR ---");
        System.out.print("Cédula: ");
        String cedula = teclado.nextLine();

        if (concesionario.buscarVendedor(cedula) != null) {
            System.out.println("El vendedor ya existe.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        concesionario.registrarVendedor(new Vendedor(cedula, nombre));
        System.out.println("Vendedor registrado correctamente.");
    }

    private static void registrarAutomovil(Concesionario concesionario) {
        System.out.println("--- REGISTRAR AUTOMÓVIL ---");
        System.out.print("Placa: ");
        String placa = teclado.nextLine();

        if (concesionario.buscarVehiculo(placa) != null) {
            System.out.println("Ya existe un vehículo con esa placa.");
            return;
        }

        System.out.print("Modelo: ");
        String modelo = teclado.nextLine();
        double precio = leerDecimal("Precio: ");
        System.out.print("¿Es vehículo de exhibición? (S/N): ");
        String respuesta = teclado.nextLine();
        boolean exhibicion = respuesta.equalsIgnoreCase("S");

        concesionario.agregarVehiculo(new Automovil(placa, modelo, precio, exhibicion));
        System.out.println("Automóvil registrado correctamente.");
    }

    private static void registrarMotocicleta(Concesionario concesionario) {
        System.out.println("--- REGISTRAR MOTOCICLETA ---");
        System.out.print("Placa: ");
        String placa = teclado.nextLine();

        if (concesionario.buscarVehiculo(placa) != null) {
            System.out.println("Ya existe un vehículo con esa placa.");
            return;
        }

        System.out.print("Modelo: ");
        String modelo = teclado.nextLine();
        double precio = leerDecimal("Precio: ");

        concesionario.agregarVehiculo(new Motocicleta(placa, modelo, precio));
        System.out.println("Motocicleta registrada correctamente.");
    }

    private static void mostrarVehiculosDisponibles(Concesionario concesionario) {
        System.out.println("--- VEHÍCULOS DISPONIBLES ---");
        if (concesionario.vehiculosDisponibles().isEmpty()) {
            System.out.println("No hay vehículos disponibles para la venta.");
            return;
        }

        for (Vehiculo v : concesionario.vehiculosDisponibles()) {
            String tipo = (v instanceof Automovil) ? "Automóvil" : "Motocicleta";
            System.out.println("Placa: " + v.getPlaca());
            System.out.println("Modelo: " + v.getModelo());
            System.out.println("Tipo: " + tipo);
            System.out.println("Precio: " + v.getPrecio());
            System.out.println("------------------------");
        }
    }

    private static void mostrarVehiculosVendidos(Concesionario concesionario) {
        System.out.println("--- VEHÍCULOS VENDIDOS ---");
        if (concesionario.vehiculosVendidos().isEmpty()) {
            System.out.println("Aún no se ha vendido ningún vehículo.");
            return;
        }

        for (Vehiculo v : concesionario.vehiculosVendidos()) {
            String tipo = (v instanceof Automovil) ? "Automóvil" : "Motocicleta";
            System.out.println("Placa: " + v.getPlaca());
            System.out.println("Modelo: " + v.getModelo());
            System.out.println("Tipo: " + tipo);

            Venta venta = concesionario.buscarVentaPorPlaca(v.getPlaca());
            if (venta != null) {
                System.out.println("Comprado por: " + venta.getCliente().getNombre());
                System.out.println("Vendido por: " + venta.getVendedor().getNombre());
                System.out.println("Fecha de venta: " + venta.getFechaVenta());
            }
            System.out.println("------------------------");
        }
    }

    private static void realizarVenta(Concesionario concesionario) {
        System.out.println("--- REALIZAR VENTA FINANCIADA ---");
        System.out.print("Cédula del cliente: ");
        String cedulaCliente = teclado.nextLine();
        Cliente cliente = concesionario.buscarCliente(cedulaCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.print("Cédula del vendedor: ");
        String cedulaVendedor = teclado.nextLine();
        Vendedor vendedor = concesionario.buscarVendedor(cedulaVendedor);
        if (vendedor == null) {
            System.out.println("Vendedor no encontrado.");
            return;
        }

        System.out.print("Placa del vehículo: ");
        String placa = teclado.nextLine();
        Vehiculo vehiculo = concesionario.buscarVehiculo(placa);
        if (vehiculo == null) {
            System.out.println("Vehículo no encontrado.");
            return;
        }

        LocalDate fecha = leerFecha("Fecha de la venta (AAAA-MM-DD): ");
        int numeroCuotas = leerEntero("Número de cuotas mensuales: ");

        try {
            Venta venta = concesionario.realizarVenta(cliente, vehiculo, vendedor, fecha, numeroCuotas);
            System.out.println("--- VENTA REALIZADA ---");
            System.out.println("Cliente: " + cliente.getNombre());
            System.out.println("Vendedor: " + vendedor.getNombre());
            System.out.println("Vehículo: " + vehiculo.getModelo());
            System.out.println("Precio total: " + venta.getPrecioTotal());
            System.out.println("Cuotas: " + venta.getNumeroCuotas());
            System.out.println("Valor de cada cuota: " + venta.getValorCuota());
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("No fue posible realizar la venta.");
            System.out.println("Motivo: " + e.getMessage());
        }
    }

    private static void registrarPago(Concesionario concesionario) {
        System.out.println("--- REGISTRAR PAGO DE CUOTA ---");
        System.out.print("Placa del vehículo: ");
        String placa = teclado.nextLine();

        Venta venta = concesionario.buscarVentaPorPlaca(placa);
        if (venta == null) {
            System.out.println("Ese vehículo no tiene una venta financiada registrada.");
            return;
        }
        if (venta.estaCompletamentePagada()) {
            System.out.println("Esta venta ya está completamente pagada.");
            return;
        }

        LocalDate fecha = leerFecha("Fecha del pago (AAAA-MM-DD): ");

        try {
            venta.registrarPago(fecha);
            System.out.println("Pago registrado correctamente.");
            System.out.println("Cuotas pagadas: " + venta.getCuotasPagadas() + "/" + venta.getNumeroCuotas());
            System.out.println("Saldo pendiente: " + venta.getSaldoPendiente());
            if (venta.estaCompletamentePagada()) {
                System.out.println("La venta ha quedado completamente pagada.");
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void mostrarVentas(Concesionario concesionario) {
        System.out.println("--- HISTORIAL DE VENTAS ---");
        if (concesionario.getVentas().isEmpty()) {
            System.out.println("No existen ventas registradas.");
            return;
        }

        for (Venta venta : concesionario.getVentas()) {
            System.out.println("Cliente: " + venta.getCliente().getNombre());
            System.out.println("Vendedor: " + venta.getVendedor().getNombre());
            System.out.println("Vehículo: " + venta.getVehiculo().getModelo());
            System.out.println("Fecha de venta: " + venta.getFechaVenta());
            System.out.println("Precio total: " + venta.getPrecioTotal());
            System.out.println("Cuotas: " + venta.getCuotasPagadas() + "/" + venta.getNumeroCuotas());
            System.out.println("Saldo pendiente: " + venta.getSaldoPendiente());
            System.out.println("Estado: " + (venta.estaCompletamentePagada() ? "PAGADA" : "EN CUOTAS"));
            System.out.println("------------------------");
        }
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        String texto = teclado.nextLine();
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número.");
            return -1;
        }
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = teclado.nextLine();
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un valor numérico.");
            }
        }
    }

    private static LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = teclado.nextLine();
            try {
                return LocalDate.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha incorrecta. Use el formato AAAA-MM-DD.");
            }
        }
    }
}