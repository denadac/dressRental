package service;

import java.util.*;
import model.Veshje;
import model.Klienti;

public class SherbimiQirase {
    private List<Veshje> veshjet;
    private List<Klienti> klientet;

    public SherbimiQirase() {
        // Load existing Veshje from file on startup
        this.veshjet = MenaxhuesiSkedareve.lexoVeshje();
        this.klientet = new ArrayList<>();
    }

    // Adds a new Veshje and saves it to file
    public void shtoVeshje(Veshje veshje) {
        veshjet.add(veshje);
        MenaxhuesiSkedareve.ruajVeshje(veshjet);
        System.out.println("✅ Veshja u shtua dhe u ruajt!");
    }

    // Adds a new Klienti
    public void shtoKlient(Klienti klient) {
        klientet.add(klient);
    }

    // Returns available Veshje
    public List<Veshje> shikoVeshjetDisponueshme() {
        List<Veshje> teDisponueshme = new ArrayList<>();
        for (Veshje v : veshjet) {
            if (v.eshteEDisponueshme()) {
                teDisponueshme.add(v);
            }
        }
        return teDisponueshme;
    }

    // Returns all Veshje (not just available ones)
    public List<Veshje> getTeGjithaVeshjet() {
        return new ArrayList<>(veshjet);
    }
}
