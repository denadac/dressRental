package service;

import model.Klienti;
import model.Veshje;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MenaxhuesiSkedareve {
    private static final String FILE_PATH = "data/veshjet.dat";
    private static final String FILE_PATH2 = "data/klientet.dat";

    // Saves the list of Veshje to a file
    public static void ruajVeshje(List<Veshje> veshjet) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs(); // Ensure "data" directory exists

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(veshjet);
            System.out.println("✅ Veshjet u ruajtën me sukses në skedar!");
        } catch (IOException e) {
            System.err.println("❌ Gabim gjatë ruajtjes së veshjeve: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Reads the list of Veshje from a file
    public static List<Veshje> lexoVeshje() {
        File file = new File(FILE_PATH);

        // If file doesn't exist, return an empty list
        if (!file.exists()) {
            System.out.println("⚠️ Skedari nuk ekziston, kthehet listë bosh.");
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Veshje>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Gabim gjatë leximit të veshjeve: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Saves the list of Klient to a file
    public static void ruajKlientet(List<Klienti> Klienti) {
        File file = new File(FILE_PATH2);
        file.getParentFile().mkdirs(); // Ensure "data" directory exists

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(Klienti);
            System.out.println("✅ Klientet u ruajtën me sukses në skedar!");
        } catch (IOException e) {
            System.err.println("❌ Gabim gjatë ruajtjes së klienteve: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Reads the list of Klient from a file
    public static List<Klienti> lexoKlientet() {
        File file = new File(FILE_PATH2);

        // If file doesn't exist, return an empty list
        if (!file.exists()) {
            System.out.println("⚠️ Skedari nuk ekziston, kthehet listë bosh.");
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Klienti>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Gabim gjatë leximit të klienteve: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
