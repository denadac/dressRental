package model;

import java.io.Serializable;

public class Klienti implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String emri;
    private String kontakt;
    private boolean activeRental;
    private int rentedVeshjeId; // ID of the rented Veshje if applicable

    public Klienti(int id, String emri, String kontakt) {
        this.id = id;
        this.emri = emri;
        this.kontakt = kontakt;
        this.activeRental = false;
        this.rentedVeshjeId = -1; // Default to -1 indicating no active rental
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getEmri() {
        return emri;
    }

    public String getKontakt() {
        return kontakt;
    }

    public boolean hasActiveRental() {
        return activeRental;
    }

    public int getRentedVeshjeId() {
        return rentedVeshjeId;
    }

    // Setters
    public void setActiveRental(boolean activeRental, int rentedVeshjeId) {
        this.activeRental = activeRental;
        this.rentedVeshjeId = rentedVeshjeId;
    }

    public void clearRental() {
        this.activeRental = false;
        this.rentedVeshjeId = -1;
    }
}
