package model;

public class Libro {

    private int id;
    private String nombre;
    private int idAutor;

    public Libro(int id, String nombre, int idAutor) {
        this.id = id;
        this.nombre = nombre;
        this.idAutor = idAutor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    @Override
    public String toString() {
        return id + " - " + nombre + " (Autor ID: " + idAutor + ")";
    }
}

