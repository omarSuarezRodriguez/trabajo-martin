package biblioteca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {

    private List<MaterialBiblioteca> materiales;
    private List<Usuario> usuarios;
    private List<Prestamo> prestamos;

    public Biblioteca() {

        materiales = new ArrayList<>();
        usuarios = new ArrayList<>();
        prestamos = new ArrayList<>();
    }

    // =============================
    // REGISTRAR
    // =============================

    public void agregarMaterial(MaterialBiblioteca material) {
        materiales.add(material);
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    // =============================
    // BUSCAR
    // =============================

    public Usuario buscarUsuario(String identificacion) {

        for (Usuario usuario : usuarios) {

            if (usuario.getIdentificacion()
                    .equalsIgnoreCase(identificacion)) {

                return usuario;
            }
        }

        return null;
    }

    public MaterialBiblioteca buscarMaterial(String codigo) {

        for (MaterialBiblioteca material : materiales) {

            if (material.getCodigo()
                    .equalsIgnoreCase(codigo)) {

                return material;
            }
        }

        return null;
    }

    public Prestamo buscarPrestamoActivo(String codigoMaterial) {

        for (Prestamo prestamo : prestamos) {

            if (prestamo.estaActivo()
                    && prestamo.getMaterial()
                            .getCodigo()
                            .equalsIgnoreCase(codigoMaterial)) {

                return prestamo;
            }
        }

        return null;
    }

    // =============================
    // PRÉSTAMOS
    // =============================

    public Prestamo realizarPrestamo(
            Usuario usuario,
            MaterialBiblioteca material,
            LocalDate fecha) {

        Prestamo prestamo =
                new Prestamo(
                        usuario,
                        material,
                        fecha);

        prestamos.add(prestamo);

        return prestamo;
    }

    // =============================
    // LISTAS
    // =============================

    public List<MaterialBiblioteca> getMateriales() {
        return materiales;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
}