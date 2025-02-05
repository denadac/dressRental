package model;
import java.io.Serializable;

public class FustanEventi extends Veshje  implements Serializable{
    private static final long serialVersionUID = 1L; // Ensures version compatibility
    private String dizajni;

    public FustanEventi(int veshjaId, String emri, String madhesia, double cmimiQirasePerDite, String dizajni) {
        super(veshjaId, emri, "Fustan Eventi", madhesia, cmimiQirasePerDite);
        this.dizajni = dizajni;
    }
}
