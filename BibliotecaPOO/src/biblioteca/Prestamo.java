package biblioteca;

import java.time.LocalDate;

public class Prestamo {

    private Usuario usuario;
    private MaterialBiblioteca material;

    private LocalDate fechaPrestamo;
    private LocalDate fechaLimite;
    private LocalDate fechaDevolucion;

    public Prestamo(
            Usuario usuario,
            MaterialBiblioteca material,
            LocalDate fechaPrestamo) {

        if (usuario.estaSancionado(fechaPrestamo)) {
            throw new IllegalStateException(
                    "El usuario se encuentra sancionado.");
        }

        if (!material.estaDisponible()) {
            throw new IllegalStateException(
                    "El material no está disponible para préstamo.");
        }

        this.usuario = usuario;
        this.material = material;
        this.fechaPrestamo = fechaPrestamo;

        // El préstamo puede durar máximo 7 días
        this.fechaLimite = fechaPrestamo.plusDays(7);

        material.marcarPrestado();
    }

    public void devolver(LocalDate fechaDevolucion) {

        if (this.fechaDevolucion != null) {
            throw new IllegalStateException(
                    "Este préstamo ya fue devuelto.");
        }

        if (fechaDevolucion.isBefore(fechaPrestamo)) {
            throw new IllegalArgumentException(
                    "La fecha de devolución no puede ser anterior al préstamo.");
        }

        this.fechaDevolucion = fechaDevolucion;

        material.marcarDevuelto();

        // Si devuelve después de la fecha límite
        if (fechaDevolucion.isAfter(fechaLimite)) {
            usuario.sancionar(fechaDevolucion);
        }
    }

    public boolean estaActivo() {
        return fechaDevolucion == null;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public MaterialBiblioteca getMaterial() {
        return material;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }
}