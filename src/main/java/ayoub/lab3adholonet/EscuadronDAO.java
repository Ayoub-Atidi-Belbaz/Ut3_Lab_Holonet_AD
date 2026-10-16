package ayoub.lab3adholonet;

import java.util.List;

/**
 *
 * @author Ayoub Atidi Belbaz
 */
public interface EscuadronDAO {

    // DDL para crear la tabla escuadrones_imperiales si no existe
    void inicializarTabla();

    // DML para insertar un nuevo escuadron usando PreparedStatement
    void insertar(EscuadronDTO escuadron);

    // Consulta DQL para obtener todos los registros
    List<EscuadronDTO> obtenerTodos();

    // Consulta DQL parametrizada para buscar por sector base evitando inyeccion SQL
    List<EscuadronDTO> obtenerPorSectorBase(String sectorBase);

    // DML para actualizar el numero de cazas de un escuadron
    void actualizarNumeroCazas(String idEscuadron, int nuevoNumeroCazas);

    // DML para eliminar un escuadron por su identificador
    void eliminar(String idEscuadron);
}
