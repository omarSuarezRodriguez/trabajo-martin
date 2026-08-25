package biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    private static final Scanner teclado
            = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            System.out.println("No se soporta UTF-8");
        }
        Biblioteca biblioteca
                = new Biblioteca();

        int opcion;

        do {

            mostrarMenu();

            opcion
                    = leerEntero(
                            "Seleccione una opción: ");

            System.out.println();

            switch (opcion) {

                case 1:
                    registrarUsuario(biblioteca);
                    break;

                case 2:
                    registrarLibro(biblioteca);
                    break;

                case 3:
                    registrarRevista(biblioteca);
                    break;

                case 4:
                    mostrarMateriales(biblioteca);
                    break;

                case 5:
                    mostrarUsuarios(biblioteca);
                    break;

                case 6:
                    realizarPrestamo(biblioteca);
                    break;

                case 7:
                    devolverMaterial(biblioteca);
                    break;

                case 8:
                    mostrarPrestamos(biblioteca);
                    break;

                case 0:
                    System.out.println(
                            "Finalizando sistema...");
                    break;

                default:
                    System.out.println(
                            "Opción no válida.");
            }

            System.out.println();

        } while (opcion != 0);
    }

    // ==========================================
    // MENÚ
    // ==========================================
    private static void mostrarMenu() {

        System.out.println(
                "=================================");

        System.out.println(
                "     SISTEMA DE BIBLIOTECA");

        System.out.println(
                "=================================");

        System.out.println("1. Registrar usuario");
        System.out.println("2. Registrar libro");
        System.out.println("3. Registrar revista");
        System.out.println("4. Ver materiales");
        System.out.println("5. Ver usuarios");
        System.out.println("6. Realizar préstamo");
        System.out.println("7. Devolver material");
        System.out.println("8. Ver préstamos");
        System.out.println("0. Salir");

        System.out.println(
                "=================================");
    }

    // ==========================================
    // REGISTRAR USUARIO
    // ==========================================
    private static void registrarUsuario(
            Biblioteca biblioteca) {

        System.out.println(
                "--- REGISTRAR USUARIO ---");

        System.out.print(
                "Identificación: ");

        String identificacion
                = teclado.nextLine();

        if (biblioteca.buscarUsuario(
                identificacion) != null) {

            System.out.println(
                    "El usuario ya existe.");

            return;
        }

        System.out.print(
                "Nombre: ");

        String nombre
                = teclado.nextLine();

        Usuario usuario
                = new Usuario(
                        identificacion,
                        nombre);

        biblioteca.registrarUsuario(
                usuario);

        System.out.println(
                "Usuario registrado correctamente.");
    }

    // ==========================================
    // REGISTRAR LIBRO
    // ==========================================
    private static void registrarLibro(
            Biblioteca biblioteca) {

        System.out.println(
                "--- REGISTRAR LIBRO ---");

        System.out.print(
                "Código: ");

        String codigo
                = teclado.nextLine();

        if (biblioteca.buscarMaterial(
                codigo) != null) {

            System.out.println(
                    "Ya existe un material con ese código.");

            return;
        }

        System.out.print(
                "Título: ");

        String titulo
                = teclado.nextLine();

        System.out.print(
                "¿Es libro de consulta? (S/N): ");

        String respuesta
                = teclado.nextLine();

        boolean consulta
                = respuesta.equalsIgnoreCase("S");

        Libro libro
                = new Libro(
                        codigo,
                        titulo,
                        consulta);

        biblioteca.agregarMaterial(
                libro);

        System.out.println(
                "Libro registrado correctamente.");
    }

    // ==========================================
    // REGISTRAR REVISTA
    // ==========================================
    private static void registrarRevista(
            Biblioteca biblioteca) {

        System.out.println(
                "--- REGISTRAR REVISTA ---");

        System.out.print(
                "Código: ");

        String codigo
                = teclado.nextLine();

        if (biblioteca.buscarMaterial(
                codigo) != null) {

            System.out.println(
                    "Ya existe un material con ese código.");

            return;
        }

        System.out.print(
                "Título: ");

        String titulo
                = teclado.nextLine();

        Revista revista
                = new Revista(
                        codigo,
                        titulo);

        biblioteca.agregarMaterial(
                revista);

        System.out.println(
                "Revista registrada correctamente.");
    }

    // ==========================================
    // MOSTRAR MATERIALES
    // ==========================================
    private static void mostrarMateriales(
            Biblioteca biblioteca) {

        System.out.println(
                "--- MATERIALES ---");

        if (biblioteca
                .getMateriales()
                .isEmpty()) {

            System.out.println(
                    "No existen materiales registrados.");

            return;
        }

        for (MaterialBiblioteca material
                : biblioteca.getMateriales()) {

            String tipo;

            if (material instanceof Libro) {
                tipo = "Libro";
            } else {
                tipo = "Revista";
            }

            String estado;

            if (!material.puedePrestarse()) {

                estado = "SOLO CONSULTA";

            } else if (material.isPrestado()) {

                estado = "PRESTADO";

            } else {

                estado = "DISPONIBLE";
            }

            System.out.println(
                    "Código: "
                    + material.getCodigo());

            System.out.println(
                    "Título: "
                    + material.getTitulo());

            System.out.println(
                    "Tipo: "
                    + tipo);

            System.out.println(
                    "Estado: "
                    + estado);

            System.out.println(
                    "------------------------");
        }
    }

    // ==========================================
    // MOSTRAR USUARIOS
    // ==========================================
    private static void mostrarUsuarios(
            Biblioteca biblioteca) {

        System.out.println(
                "--- USUARIOS ---");

        if (biblioteca
                .getUsuarios()
                .isEmpty()) {

            System.out.println(
                    "No existen usuarios registrados.");

            return;
        }

        for (Usuario usuario
                : biblioteca.getUsuarios()) {

            System.out.println(
                    "Identificación: "
                    + usuario.getIdentificacion());

            System.out.println(
                    "Nombre: "
                    + usuario.getNombre());

            if (usuario.getSancionadoHasta()
                    != null) {

                System.out.println(
                        "Sancionado hasta: "
                        + usuario.getSancionadoHasta());

            } else {

                System.out.println(
                        "Sin sanciones.");
            }

            System.out.println(
                    "------------------------");
        }
    }

    // ==========================================
    // REALIZAR PRÉSTAMO
    // ==========================================
    private static void realizarPrestamo(
            Biblioteca biblioteca) {

        System.out.println(
                "--- REALIZAR PRÉSTAMO ---");

        System.out.print(
                "Identificación del usuario: ");

        String identificacion
                = teclado.nextLine();

        Usuario usuario
                = biblioteca.buscarUsuario(
                        identificacion);

        if (usuario == null) {

            System.out.println(
                    "Usuario no encontrado.");

            return;
        }

        System.out.print(
                "Código del material: ");

        String codigo
                = teclado.nextLine();

        MaterialBiblioteca material
                = biblioteca.buscarMaterial(
                        codigo);

        if (material == null) {

            System.out.println(
                    "Material no encontrado.");

            return;
        }

        LocalDate fecha
                = leerFecha(
                        "Fecha del préstamo (AAAA-MM-DD): ");

        try {

            Prestamo prestamo
                    = biblioteca.realizarPrestamo(
                            usuario,
                            material,
                            fecha);

            System.out.println();
            System.out.println(
                    "PRÉSTAMO REALIZADO");

            System.out.println(
                    "Usuario: "
                    + usuario.getNombre());

            System.out.println(
                    "Material: "
                    + material.getTitulo());

            System.out.println(
                    "Fecha préstamo: "
                    + fecha);

            System.out.println(
                    "Fecha límite: "
                    + prestamo.getFechaLimite());

        } catch (IllegalStateException e) {

            System.out.println(
                    "No fue posible realizar el préstamo.");

            System.out.println(
                    "Motivo: "
                    + e.getMessage());
        }
    }

    // ==========================================
    // DEVOLVER
    // ==========================================
    private static void devolverMaterial(
            Biblioteca biblioteca) {

        System.out.println(
                "--- DEVOLVER MATERIAL ---");

        System.out.print(
                "Código del material: ");

        String codigo
                = teclado.nextLine();

        Prestamo prestamo
                = biblioteca.buscarPrestamoActivo(
                        codigo);

        if (prestamo == null) {

            System.out.println(
                    "No existe un préstamo activo "
                    + "para ese material.");

            return;
        }

        LocalDate fechaDevolucion
                = leerFecha(
                        "Fecha de devolución (AAAA-MM-DD): ");

        boolean retrasado
                = fechaDevolucion.isAfter(
                        prestamo.getFechaLimite());

        try {

            prestamo.devolver(
                    fechaDevolucion);

            System.out.println(
                    "Material devuelto correctamente.");

            if (retrasado) {

                System.out.println(
                        "DEVOLUCIÓN FUERA DE PLAZO.");

                System.out.println(
                        "Usuario sancionado hasta: "
                        + prestamo
                                .getUsuario()
                                .getSancionadoHasta());

            } else {

                System.out.println(
                        "Devolución realizada a tiempo.");
            }

        } catch (IllegalArgumentException
                | IllegalStateException e) {

            System.out.println(
                    "ERROR: "
                    + e.getMessage());
        }
    }

    // ==========================================
    // MOSTRAR PRÉSTAMOS
    // ==========================================
    private static void mostrarPrestamos(
            Biblioteca biblioteca) {

        System.out.println(
                "--- HISTORIAL DE PRÉSTAMOS ---");

        if (biblioteca
                .getPrestamos()
                .isEmpty()) {

            System.out.println(
                    "No existen préstamos.");

            return;
        }

        for (Prestamo prestamo
                : biblioteca.getPrestamos()) {

            System.out.println(
                    "Usuario: "
                    + prestamo
                            .getUsuario()
                            .getNombre());

            System.out.println(
                    "Material: "
                    + prestamo
                            .getMaterial()
                            .getTitulo());

            System.out.println(
                    "Fecha préstamo: "
                    + prestamo
                            .getFechaPrestamo());

            System.out.println(
                    "Fecha límite: "
                    + prestamo
                            .getFechaLimite());

            if (prestamo.estaActivo()) {

                System.out.println(
                        "Estado: ACTIVO");

            } else {

                System.out.println(
                        "Fecha devolución: "
                        + prestamo
                                .getFechaDevolucion());

                System.out.println(
                        "Estado: DEVUELTO");
            }

            System.out.println(
                    "------------------------");
        }
    }

    // ==========================================
    // LEER ENTERO
    // ==========================================
    private static int leerEntero(
            String mensaje) {

        while (true) {

            try {

                System.out.print(
                        mensaje);

                return Integer.parseInt(
                        teclado.nextLine());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Debe ingresar un número.");
            }
        }
    }

    // ==========================================
    // LEER FECHA
    // ==========================================
    private static LocalDate leerFecha(
            String mensaje) {

        while (true) {

            try {

                System.out.print(
                        mensaje);

                String texto
                        = teclado.nextLine();

                return LocalDate.parse(
                        texto);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Fecha incorrecta.");

                System.out.println(
                        "Use el formato AAAA-MM-DD.");
            }
        }
    }
}
