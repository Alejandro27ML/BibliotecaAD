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
            System.out.println("10. Mostrar todos los libros de un autor.");
            System.out.println("11. Mostrar todos los préstamos de un usuario.");
            System.out.println("12. Mostrar cantidad de libros de cada autor.");
            System.out.println("13. Top libros más populares.");
            System.out.println("0. Salir.");
            System.out.print("Elige una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("ID del autor: ");
                    int idAutor = sc.nextInt(); sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombreAutor = sc.nextLine();
                    System.out.print("Apellido: ");
                    String apellidoAutor = sc.nextLine();

                    autorDAO.insertarAutor(new Autor(idAutor, nombreAutor, apellidoAutor));
                    break;

                case 2:
                    autorDAO.consultarAutores().forEach(System.out::println);
                    break;

                case 3:
                    System.out.print("ID del libro: ");
                    int idLibro = sc.nextInt(); sc.nextLine();
                    System.out.print("Nombre del libro: ");
                    String nombreLibro = sc.nextLine();
                    System.out.print("ID del autor: ");
                    int idAutorLibro = sc.nextInt();

                    libroDAO.insertarLibro(new Libro(idLibro, nombreLibro, idAutorLibro));
                    break;

                case 4:
                    libroDAO.consultarLibros().forEach(System.out::println);
                    break;

                case 5:
                    System.out.print("ID del usuario: ");
                    int idUsuario = sc.nextInt(); sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombreUsuario = sc.nextLine();
                    System.out.print("Apellido: ");
                    String apellidoUsuario = sc.nextLine();

                    usuarioDAO.insertarUsuario(new Usuario(idUsuario, nombreUsuario, apellidoUsuario));
                    break;

                case 6:
                    usuarioDAO.consultarUsuarios().forEach(System.out::println);
                    break;

                case 7:
                    System.out.print("ID del usuario a eliminar: ");
                    int idUsuarioEliminar =sc.nextInt(); sc.nextLine();

                    usuarioDAO.eliminarUsuario(idUsuarioEliminar);
                    break;

                case 8:
                    System.out.print("ID del préstamo: ");
                    int idPrestamo = sc.nextInt();
                    System.out.print("ID del usuario: ");
                    int idU = sc.nextInt();
                    System.out.print("ID del libro: ");
                    int idL = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Fecha préstamo (YYYY-MM-DD): ");
                    LocalDate fechaP = LocalDate.parse(sc.nextLine());

                    prestamoDAO.insertarPrestamo(new Prestamo(idPrestamo, idU, idL, fechaP, null));
                    break;

                case 9:
                    prestamoDAO.consultarPrestamos().forEach(System.out::println);
                    break;

                case 10:
                    System.out.print("ID del autor: ");
                    int idAutorConsulta = sc.nextInt();
                    libroDAO.consultarLibrosPorAutor(idAutorConsulta).forEach(System.out::println);
                    break;

                case 11:
                    System.out.print("ID del usuario: ");
                    int idUsuarioConsulta = sc.nextInt();
                    prestamoDAO.consultarPrestamosPorUsuario(idUsuarioConsulta).forEach(System.out::println);
                    break;

                case 12:
                    autorDAO.contarLibrosPorAutor();
                    break;

                case 13:
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
}

