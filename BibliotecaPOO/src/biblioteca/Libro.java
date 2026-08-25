package biblioteca;

public class Libro extends MaterialBiblioteca {

    private boolean consulta;

    public Libro(String codigo, String titulo, boolean consulta) {

        super(codigo, titulo);

        this.consulta = consulta;
    }

    @Override
    public boolean puedePrestarse() {

        return !consulta;
    }

    public boolean isConsulta() {

        return consulta;
    }
}