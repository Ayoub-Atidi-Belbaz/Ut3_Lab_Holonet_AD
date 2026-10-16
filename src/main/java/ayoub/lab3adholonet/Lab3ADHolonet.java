package ayoub.lab3adholonet;

import java.util.List;

/**
 *
 * @author Ayoub Atidi Belbaz
 */
public class Lab3ADHolonet {

    public static void main(String[] args) {
        System.out.println("=== Alianza Rebelde: Sistema de Inteligencia Tactica HoloNet ===");

        // Instanciamos el DAO mediante la interfaz desacoplada
        EscuadronDAO dao = new EscuadronDAOImplementacion();

        // Inicializamos la tabla si no existe
        dao.inicializarTabla();

        // Insertamos escuadrones imperiales de prueba
        System.out.println("\n--- Registro de nuevos escuadrones (DML Insert) ---");
        EscuadronDTO esc1 = new EscuadronDTO("ESC-01", "Escuadron Sombra", "Borde Exterior - Endor", 24);
        EscuadronDTO esc2 = new EscuadronDTO("ESC-02", "Escuadron Calavera", "Nucleo - Coruscant", 12);
        EscuadronDTO esc3 = new EscuadronDTO("ESC-03", "Inferno Squad", "Borde Exterior - Endor", 8);

        dao.insertar(esc1);
        dao.insertar(esc2);
        dao.insertar(esc3);

        // Consultamos y mostramos todos los registros obtenidos con el cursor
        System.out.println("\n--- Lista de todos los escuadrones registrados (DQL Select) ---");
        List<EscuadronDTO> todos = dao.obtenerTodos();
        for (EscuadronDTO esc : todos) {
            System.out.println(esc);
        }

        // Actualizamos el numero de cazas de un escuadron especifico
        System.out.println("\n--- Actualizacion de capacidad de combate (DML Update) ---");
        dao.actualizarNumeroCazas("ESC-01", 36);

        // Demostracion de consulta parametrizada protegida contra SQL Injection
        System.out.println("\n--- Busqueda filtrada por sector base: Borde Exterior - Endor ---");
        List<EscuadronDTO> filtrados = dao.obtenerPorSectorBase("Borde Exterior - Endor");
        for (EscuadronDTO esc : filtrados) {
            System.out.println(esc);
        }

        // Eliminamos un escuadron neutralizado de la base de datos
        System.out.println("\n--- Eliminacion de escuadron neutralizado (DML Delete) ---");
        dao.eliminar("ESC-03");

        // Mostramos el estado final consolidado de los escuadrones
        System.out.println("\n--- Censo final consolidado ---");
        List<EscuadronDTO> censoFinal = dao.obtenerTodos();
        for (EscuadronDTO esc : censoFinal) {
            System.out.println(esc);
        }

        System.out.println("\n=== Fin de la transmision HoloNet ===");
    }
}
