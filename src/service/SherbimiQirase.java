package service;

import java.util.*;
import model.Veshje;
import model.Klienti;
import model.TransaksioniQirase;
import java.time.LocalDate;

public class SherbimiQirase {
    private List<Veshje> veshjet;
    private List<Klienti> klientet;

    public SherbimiQirase() {
        this.veshjet = MenaxhuesiSkedareve.lexoVeshje();
        this.klientet = MenaxhuesiSkedareve.lexoKlientet();
    }

    public void shtoVeshje(Veshje veshje) {
        veshjet.add(veshje);
        MenaxhuesiSkedareve.ruajVeshje(veshjet);
        System.out.println("✅ Veshja u shtua dhe u ruajt!");
    }

    public void shtoKlient(Klienti klient) {
        klientet.add(klient);
        MenaxhuesiSkedareve.ruajKlientet(klientet);
        System.out.println("✅ Klienti u shtua dhe u ruajt!");
    }

    public List<Veshje> shikoVeshjetDisponueshme() {
        List<Veshje> teDisponueshme = new ArrayList<>();
        for (Veshje v : veshjet) {
            if (v.eshteEDisponueshme()) {
                teDisponueshme.add(v);
            }
        }
        return teDisponueshme;
    }

    public List<Veshje> getTeGjithaVeshjet() {
        return new ArrayList<>(veshjet);
    }

    public List<Klienti> getTeGjitheKlientet() {
        return new ArrayList<>(klientet);
    }

    public Klienti getKlientById(int klientId) {
        for (Klienti klient : klientet) {
            if (klient.getId() == klientId) {
                return klient;
            }
        }
        return null;
    }

    public Veshje getVeshjeById(int veshjeId) {
        for (Veshje veshje : veshjet) {
            if (veshje.getVeshjaId() == veshjeId) {
                return veshje;
            }
        }
        return null;
    }

    public void shtoQira(int klientId, int veshjeId) {
        Klienti klient = getKlientById(klientId);
        Veshje veshje = getVeshjeById(veshjeId);
        if (klient != null && veshje != null && veshje.eshteEDisponueshme()) {
            // Assuming you calculate the start and end date here
            LocalDate startDate = LocalDate.now();  // Start date is the current date
            LocalDate endDate = startDate.plusDays(5);  // Example: 5 days rental period

            // Create a new TransaksioniQirase object
            TransaksioniQirase transaction = new TransaksioniQirase(1, veshje, klient, startDate, endDate);

            // Set the transaction for the client
            klient.setActiveRental(true, veshjeId);
            klient.setActiveRentalTransaction(transaction);  // Set the rental transaction

            // Mark the veshja as rented
            veshje.merreMeQira();
        }
    }

    public void paguajQira(int klientId) {
        // Handle payment logic here
    }

    public void mbyllQira(int klientId) {
        Klienti klient = getKlientById(klientId);
        if (klient != null && klient.hasActiveRental()) {
            Veshje veshje = getVeshjeById(klient.getRentedVeshjeId());
            if (veshje != null) {
                veshje.ktheVeshjen();
            }
            klient.clearRental();
        }
    }

    public int calculateRentalDays(int klientId) {
        // Assuming method returns mock rental period for now
        return 5;
    }

    public LocalDate getRentalStartDate(int klientId) {
        Klienti klient = getKlientById(klientId);
        if (klient != null && klient.hasActiveRental()) {
            TransaksioniQirase transaction = klient.getActiveRentalTransaction();
            return transaction != null ? transaction.getDataFillimit() : null;
        }
        return null;
    }

    public LocalDate getRentalEndDate(int klientId) {
        Klienti klient = getKlientById(klientId);
        if (klient != null && klient.hasActiveRental()) {
            TransaksioniQirase transaction = klient.getActiveRentalTransaction();
            return transaction != null ? transaction.getDataMbarimit() : null;
        }
        return null;
    }

    public void updateVeshje(Veshje updatedVeshje) {
        for (int i = 0; i < veshjet.size(); i++) {
            if (veshjet.get(i).getVeshjaId() == updatedVeshje.getVeshjaId()) {
                veshjet.set(i, updatedVeshje);
                break;
            }
        }
        MenaxhuesiSkedareve.ruajVeshje(veshjet); // Save the updated list to the file
    }

    public void fshiVeshje(int veshjaId) {
        veshjet.removeIf(veshje -> veshje.getVeshjaId() == veshjaId);
        MenaxhuesiSkedareve.ruajVeshje(veshjet); // Save the updated list to the file
    }

    public void shtoQira(int klientId, int veshjeId, LocalDate dataFillimit, LocalDate dataMbarimit, boolean paguar) {
        Klienti klient = getKlientById(klientId);
        Veshje veshje = getVeshjeById(veshjeId);
        if (klient != null && veshje != null && veshje.eshteEDisponueshme()) {
            TransaksioniQirase transaction = new TransaksioniQirase(1, veshje, klient, dataFillimit, dataMbarimit);
            klient.setActiveRental(true, veshjeId);
            klient.setActiveRentalTransaction(transaction);
            veshje.merreMeQira();

            // If the rental is paid, handle payment logic here
            if (paguar) {
                paguajQira(klientId);
            }
        }
    }

}
