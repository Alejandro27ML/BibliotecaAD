import DAO.AutorDAO;
import DAO.LibroDAO;
import DAO.UsuarioDAO;
import DAO.PrestamoDAO;
import model.Autor;
import model.Libro;
import model.Usuario;
import model.Prestamo;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AutorDAO autorDAO = new AutorDAO();
        LibroDAO libroDAO = new LibroDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PrestamoDAO prestamoDAO = new PrestamoDAO();

        int opcion;

        //Creo un menú con un Switch para elegir entre las opciones, cada opción
        //cargará los métodos que necesite.
        do {
            System.out.println("\n-----{ BIBLIOTECA }-----");
            System.out.println("1. Insertar autor.");
            System.out.println("2. Listar autores.");
            System.out.println("3. Insertar libro.");
            System.out.println("4. Listar libros.");
            System.out.println("5. Insertar usuario.");
            System.out.println("6. Listar usuarios.");
            System.out.println("7. Eliminar usuario.");
            System.out.println("8. Insertar préstamo.");
            System.out.println("9. Listar préstamos.");
            System.out.println("10. Eliminar préstamo.");
            System.out.println("11. Mostrar todos los libros de un autor.");
            System.out.println("12. Mostrar todos los préstamos de un usuario.");
            System.out.println("13. Mostrar cantidad de libros de cada autor.");
            System.out.println("14. Top libros más populares.");
            System.out.println("0. Salir.");
            System.out.print("Elige una opción: ");

            opcion = comprobarOpcion(sc, 0, 14);

            switch (opcion) {

                case 1:
                    int idAutor = comprobarIDNuevo(sc, "ID del autor: ", "autor", autorDAO);
                    String nombreAutor = comprobarTexto(sc, "Nombre: ");
                    String apellidoAutor = comprobarTexto(sc, "Apellido: ");

                    autorDAO.insertarAutor(new Autor(idAutor, nombreAutor, apellidoAutor));
                    break;

                case 2:
                    autorDAO.consultarAutores().forEach(System.out::println);
                    break;

                case 3:
                    int idLibro = comprobarIDNuevo(sc, "ID del libro: ", "libro", libroDAO);
                    String nombreLibro = comprobarTexto(sc, "Nombre del libro: ");
                    int idAutorLibro = comprobarID(sc, "ID del autor: ", "autor", autorDAO);

                    libroDAO.insertarLibro(new Libro(idLibro, nombreLibro, idAutorLibro));
                    break;

                case 4:
                    libroDAO.consultarLibros().forEach(System.out::println);
                    break;

                case 5:
                    int idUsuario = comprobarIDNuevo(sc, "ID del usuario: ", "usuario", usuarioDAO);
                    String nombreUsuario = comprobarTexto(sc, "Nombre: ");
                    String apellidoUsuario = comprobarTexto(sc, "Apellido: ");

                    usuarioDAO.insertarUsuario(new Usuario(idUsuario, nombreUsuario, apellidoUsuario));
                    break;

                case 6:
                    usuarioDAO.consultarUsuarios().forEach(System.out::println);
                    break;

                case 7:
                    int idUsuarioEliminar = comprobarID(sc, "ID del usuario a eliminar: ", "usuario", usuarioDAO);
                    usuarioDAO.eliminarUsuario(idUsuarioEliminar);
                    break;

                case 8:
                    int idPrestamo = comprobarIDNuevo(sc, "ID del préstamo: ", "prestamo", prestamoDAO);
                    int idU = comprobarID(sc, "ID del usuario: ", "usuario", usuarioDAO);
                    int idL = comprobarID(sc, "ID del libro: ", "libro", libroDAO);
                    System.out.print("Fecha préstamo (YYYY-MM-DD): ");
                    LocalDate fechaP = LocalDate.parse(sc.nextLine());

                    prestamoDAO.insertarPrestamo(new Prestamo(idPrestamo, idU, idL, fechaP, null));
                    break;

                case 9:
                    prestamoDAO.consultarPrestamos().forEach(System.out::println);
                    break;

                case 10:
                    int idPrestamoEliminar = comprobarID(sc, "ID del préstamo a eliminar: ", "prestamo", prestamoDAO);
                    prestamoDAO.eliminarPrestamo(idPrestamoEliminar);
                    break;

                case 11:
                    int idAutorConsulta = comprobarID(sc, "ID del autor: ", "autor", autorDAO);
                    libroDAO.consultarLibrosPorAutor(idAutorConsulta).forEach(System.out::println);
                    break;

                case 12:
                    int idUsuarioConsulta = comprobarID(sc, "ID del usuario: ", "usuario", usuarioDAO);
                    prestamoDAO.consultarPrestamosPorUsuario(idUsuarioConsulta).forEach(System.out::println);
                    break;

                case 13:
                    autorDAO.contarLibrosPorAutor();
                    break;

                case 14:
                    System.out.println("\n--- Libros más prestados ---");
                    List<String> ranking = libroDAO.obtenerLibrosMasPrestados();

                    if (ranking.isEmpty()) {
                        System.out.println("No hay préstamos registrados.");
                    } else {
                        ranking.forEach(System.out::println);
                    }
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        sc.close();
    }

    //No sabia donde meter estos métodos, así que los voy a poner en el Main, no se si es correcto.

    //Comprobar que en el menú se indica una opción válida:
    public static int comprobarOpcion(Scanner sc, int min, int max) {
        while (true) {

            if (!sc.hasNextInt()) {
                System.out.println("Debes introducir un número.");
                sc.nextLine();
                continue;
            }

            int opcion = sc.nextInt();
            sc.nextLine();

            if (opcion < min || opcion > max) {
                System.out.println("Opción fuera de rango. Intentalo de nuevo.");
                continue;
            }

            return opcion;
        }
    }

    //Aquí creo y valido los formatos que tendrá cada id.
    public static boolean validarFormatoID(int id, String tipo) {
        switch (tipo) {
            case "autor": return id >= 100 && id <= 999;
            case "libro": return id >= 1 && id <= 9;
            case "usuario": return id >= 10 && id <= 99;
            case "prestamo": return id >= 1000 && id <= 9999;
            default: return false;
        }
    }

    //Comprobar que si un ID existe en la BBDD para evitar que cierre el programa al introducir un ID no Válido.
    //El objeto Object nos permite comprobar las clases que hemos creado.
    //Object dao -> nos permitirá comprobar las clases DAO para asegurarse que el id introducido está dentro
    //de su respectiva tabla, por ejemplo un id de autor está dentro de la tabla autores y no en libros.
    public static int comprobarID(Scanner sc, String mensaje, String tipo, Object dao) {
        int id;

        while (true) {
            System.out.print(mensaje);
            id = sc.nextInt();
            sc.nextLine();

            if (!validarFormatoID(id, tipo)) {
                System.out.println("Formato de ID incorrecto para " + tipo + ".");
                continue;
            }

            boolean existe = false;

            //Aquí es donde comprobamos su el id existe en las tablas:
            switch (tipo) {
                case "autor": existe = ((AutorDAO) dao).buscarPorId(id) != null; break;
                case "usuario": existe = ((UsuarioDAO) dao).buscarPorId(id) != null; break;
                case "libro": existe = ((LibroDAO) dao).buscarPorId(id) != null; break;
                case "prestamo": existe = ((PrestamoDAO) dao).buscarPorId(id) != null; break;
            }

            if (existe) return id;

            System.out.println("Ese " + tipo + " no existe. Intenta de nuevo.");
        }
    }

    //Comprobar si un ID ya está registrado para no registrar el mismo ID.
    public static int comprobarIDNuevo(Scanner sc, String mensaje, String tipo, Object dao) {
        int id;

        while (true) {
            System.out.print(mensaje);
            id = sc.nextInt();
            sc.nextLine();

            if (!validarFormatoID(id, tipo)) {
                System.out.println("Formato de ID incorrecto para " + tipo + ".");
                continue;
            }

            boolean existe = false;

            switch (tipo) {
                case "autor": existe = ((AutorDAO) dao).buscarPorId(id) != null; break;
                case "usuario": existe = ((UsuarioDAO) dao).buscarPorId(id) != null; break;
                case "libro": existe = ((LibroDAO) dao).buscarPorId(id) != null; break;
                case "prestamo": existe = ((PrestamoDAO) dao).buscarPorId(id) != null; break;
            }

            if (!existe) return id;

            System.out.println("Ese ID ya existe. Introduce uno diferente.");
        }
    }

    //Comprobar si el téxto introducido es una cadena vacia para evitar errores.
    //en algunos casos este metodo no será necesario gracias al de valicadión de formatos de ID.
    public static String comprobarTexto(Scanner sc, String mensaje) {
        String texto;

        while (true) {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();

            if (!texto.isEmpty()) return texto;

            System.out.println("El texto no puede estar vacío. Intenta de nuevo.");
        }
    }

}


