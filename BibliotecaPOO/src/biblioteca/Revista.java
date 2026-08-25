package biblioteca;

public class Revista extends MaterialBiblioteca {

    public Revista(String codigo, String titulo) {

        super(codigo, titulo);
    }

    @Override
    public boolean puedePrestarse() {

        return true;
    }
}