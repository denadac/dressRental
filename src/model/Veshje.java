package model;
import java.io.Serializable;

public abstract class Veshje implements Serializable {
    protected int veshjaId;
    protected String emri;
    protected String lloji;
    protected String madhesia;
    protected double cmimiQirasePerDite;
    protected boolean eshteEDisponueshme = true;
    private static final long serialVersionUID = 1L; // Ensures compatibility across versions


    public Veshje(int veshjaId, String emri, String lloji, String madhesia, double cmimiQirasePerDite) {
        this.veshjaId = veshjaId;
        this.emri = emri;
        this.lloji = lloji;
        this.madhesia = madhesia;
        this.cmimiQirasePerDite = cmimiQirasePerDite;
    }



    public void merreMeQira() {
        this.eshteEDisponueshme = false;
    }


    public void ktheVeshjen() {
        this.eshteEDisponueshme = true;
    }


    public boolean eshteEDisponueshme() {
        return eshteEDisponueshme;
    }


    public String getEmri() {
        return emri;
    }

    public double getCmimiQirasePerDite() {
        return cmimiQirasePerDite;
    }

}

