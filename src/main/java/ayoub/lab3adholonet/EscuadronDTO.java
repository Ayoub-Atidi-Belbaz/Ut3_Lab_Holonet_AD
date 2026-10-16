package ayoub.lab3adholonet;

/**
 *
 * @author Ayoub Atidi Belbaz
 */
public class EscuadronDTO {

    private String idEscuadron;
    private String nombre;
    private String sectorBase;
    private int numeroCazas;

    public EscuadronDTO() {
    }

    public EscuadronDTO(String idEscuadron, String nombre, String sectorBase, int numeroCazas) {
        this.idEscuadron = idEscuadron;
        this.nombre = nombre;
        this.sectorBase = sectorBase;
        this.numeroCazas = numeroCazas;
    }

    public String getIdEscuadron() {
        return idEscuadron;
    }

    public void setIdEscuadron(String idEscuadron) {
        this.idEscuadron = idEscuadron;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSectorBase() {
        return sectorBase;
    }

    public void setSectorBase(String sectorBase) {
        this.sectorBase = sectorBase;
    }

    public int getNumeroCazas() {
        return numeroCazas;
    }

    public void setNumeroCazas(int numeroCazas) {
        this.numeroCazas = numeroCazas;
    }

    @Override
    public String toString() {
        return "Escuadron ID: " + idEscuadron + " | Nombre: " + nombre + " | Sector Base: " + sectorBase + " | Cazas: " + numeroCazas;
    }
}
