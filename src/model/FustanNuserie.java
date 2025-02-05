package model;
import java.io.Serializable;


public class FustanNuserie extends Veshje implements Serializable {
    private static final long serialVersionUID = 1L; // Ensures version compatibility
    private String materiali;


    public FustanNuserie(int veshjaId, String emri, String madhesia, double cmimiQirasePerDite, String materiali) {
        super(veshjaId, emri, "Fustan Nuserie", madhesia, cmimiQirasePerDite);
        this.materiali = materiali;
    }
}
