package service;

import java.util.*;
import model.Veshje;
import model.Klienti;

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
}
