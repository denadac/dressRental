package model;

import java.io.Serializable;

public abstract class Veshje implements Serializable {
    protected int veshjaId;
    protected String emri;
    protected String lloji;
    protected String madhesia;
    protected double cmimiQirasePerDite;
    protected boolean eshteEDisponueshme = true;
    private static final long serialVersionUID = 1L;

    public Veshje(int veshjaId, String emri, String lloji, String madhesia, double cmimiQirasePerDite) {
        this.veshjaId = veshjaId;
        this.emri = emri;
        this.lloji = lloji;
        this.madhesia = madhesia;
        this.cmimiQirasePerDite = cmimiQirasePerDite;
    }

    // Metodat Getter

    public String getEmri() {
        return emri;
    }
    public String getLloji() {
        return lloji;
    }
    public void setLloji(String newLloji) {
        madhesia = newLloji;
    }

    public double getCmimiQirasePerDite() {
        return cmimiQirasePerDite;
    }

    public int getVeshjaId() {
        return veshjaId;
    }

    // metodat Setter

    public void setEmri(String newEmri) {
        emri = newEmri;
    }

    public void setCmimiQirasePerDite(double newCmimi) {
        cmimiQirasePerDite = newCmimi;
    }

    public void setEDisponueshme(boolean eDisponueshme) {
        eshteEDisponueshme = eDisponueshme;
    }

    public Object getMadhesia() {
        return madhesia;
    }

    public void setMadhesia(String newMadhesia) {
        lloji = newMadhesia;
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

    @Override
    public String toString() {
        return "ID: " + veshjaId + ", Emri: " + emri + ", Lloji: " + lloji + ", Madhësia: " + madhesia + ", Çmimi: " + cmimiQirasePerDite + "€/dita" +
                ", Disponueshmëria: " + (eshteEDisponueshme ? "Po" : "Jo");
    }
}