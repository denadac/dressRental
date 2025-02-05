package model;
import java.io.Serializable;

public class FustanEventi extends Veshje  implements Serializable{
    private static final long serialVersionUID = 1L; // Ensures version compatibility
    private String gjatesia;

    public FustanEventi(int veshjaId, String emri, String madhesia, double cmimiQirasePerDite, String gjatesia) {
        super(veshjaId, emri, "Fustan Eventi", madhesia, cmimiQirasePerDite);
        this.gjatesia = gjatesia;
    }
}
