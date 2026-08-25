package biblioteca;

public abstract class MaterialBiblioteca {

    private String codigo;
    private String titulo;
    private boolean prestado;

    public MaterialBiblioteca(String codigo, String titulo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.prestado = false;
    }

    public abstract boolean puedePrestarse();

    public boolean estaDisponible() {
        return !prestado && puedePrestarse();
    }

    public void marcarPrestado() {
        prestado = true;
    }

    public void marcarDevuelto() {
        prestado = false;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isPrestado() {
        return prestado;
    }
}