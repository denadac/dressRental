package model;
import java.io.Serializable;


public class Kostum extends Veshje implements Serializable {
    private static final long serialVersionUID = 1L; // Ensures version compatibility
    private String ngjyra;

    public Kostum(int veshjaId, String emri, String madhesia, double cmimiQirasePerDite, String ngjyra) {
        super(veshjaId, emri, "Kostum", madhesia, cmimiQirasePerDite);
        this.ngjyra = ngjyra;
    }
}
