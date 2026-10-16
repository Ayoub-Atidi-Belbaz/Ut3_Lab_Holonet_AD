package ayoub.lab3adholonet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ayoub Atidi Belbaz
 */
public class EscuadronDAOImplementacion  implements EscuadronDAO {

    // Parametros de conexion centralizados para PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/imperio_db";
    private static final String USER = "postgres";
    private static final String PASS = "admin";

    // Metodo privado para obtener una conexion activa con la base de datos
    private Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Inicializamos la tabla escuadrones_imperiales mediante sentencia DDL
    @Override
    public void inicializarTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS escuadrones_imperiales ("
                + "id_escuadron VARCHAR(50) PRIMARY KEY, "
                + "nombre VARCHAR(100) NOT NULL, "
                + "sector_base VARCHAR(100) NOT NULL, "
                + "numero_cazas INT NOT NULL"   
                + ");";

        try (Connection con = obtenerConexion();
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Tabla escuadrones_imperiales inicializada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al inicializar la tabla: " + e.getMessage());
        }
    }

    // Inserta un nuevo registro de escuadron con PreparedStatement anti-inyeccion SQL
    @Override
    public void insertar(EscuadronDTO escuadron) {
        String sql = "INSERT INTO escuadrones_imperiales (id_escuadron, nombre, sector_base, numero_cazas) "
                + "VALUES (?, ?, ?, ?);";

        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignamos los parametros posicionales
            ps.setString(1, escuadron.getIdEscuadron());
            ps.setString(2, escuadron.getNombre());
            ps.setString(3, escuadron.getSectorBase());
            ps.setInt(4, escuadron.getNumeroCazas());

            ps.executeUpdate();
            System.out.println("Escuadron insertado correctamente: " + escuadron.getIdEscuadron());

        } catch (SQLException e) {
            System.err.println("Error al insertar escuadron " + escuadron.getIdEscuadron() + ": " + e.getMessage());
        }
    }

    // Consulta todos los escuadrones y recorre el cursor ResultSet
    @Override
    public List<EscuadronDTO> obtenerTodos() {
        List<EscuadronDTO> lista = new ArrayList<>();
        String sql = "SELECT id_escuadron, nombre, sector_base, numero_cazas FROM escuadrones_imperiales ORDER BY id_escuadron ASC;";

        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Extraemos los registros con el cursor
            while (rs.next()) {
                String id = rs.getString("id_escuadron");
                String nombre = rs.getString("nombre");
                String sector = rs.getString("sector_base");
                int cazas = rs.getInt("numero_cazas");

                EscuadronDTO escuadron = new EscuadronDTO(id, nombre, sector, cazas);
                lista.add(escuadron);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los escuadrones: " + e.getMessage());
        }

        return lista;
    }

    // Consulta parametrizada filtrando por sector base con seguridad PreparedStatement
    @Override
    public List<EscuadronDTO> obtenerPorSectorBase(String sectorBase) {
        List<EscuadronDTO> lista = new ArrayList<>();
        String sql = "SELECT id_escuadron, nombre, sector_base, numero_cazas FROM escuadrones_imperiales "
                + "WHERE sector_base = ? ORDER BY id_escuadron ASC;";

        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sectorBase);

            try (ResultSet rs = ps.executeQuery()) {
                // Iteramos por las tuplas coincidentes
                while (rs.next()) {
                    String id = rs.getString("id_escuadron");
                    String nombre = rs.getString("nombre");
                    String sector = rs.getString("sector_base");
                    int cazas = rs.getInt("numero_cazas");

                    EscuadronDTO escuadron = new EscuadronDTO(id, nombre, sector, cazas);
                    lista.add(escuadron);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener escuadrones por sector base: " + e.getMessage());
        }

        return lista;
    }

    // Actualiza la cantidad de cazas asignados a un escuadron especifico
    @Override
    public void actualizarNumeroCazas(String idEscuadron, int nuevoNumeroCazas) {
        String sql = "UPDATE escuadrones_imperiales SET numero_cazas = ? WHERE id_escuadron = ?;";

        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevoNumeroCazas);
            ps.setString(2, idEscuadron);

                  int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Numero de cazas actualizado correctamente para el escuadron: " + idEscuadron);
            } else {
                System.out.println("No se encontro el escuadron con ID: " + idEscuadron);
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar numero de cazas del escuadron " + idEscuadron + ": " + e.getMessage());
        }
    }

    // Elimina un escuadron de la base de datos segun su clave primaria
    @Override
    public void eliminar(String idEscuadron) {
        String sql = "DELETE FROM escuadrones_imperiales WHERE id_escuadron = ?;";

        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, idEscuadron);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Escuadron eliminado correctamente: " + idEscuadron);
            } else {
                System.out.println("No se encontro el escuadron con ID: " + idEscuadron);
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar escuadron " + idEscuadron + ": " + e.getMessage());
        }
    }
}
