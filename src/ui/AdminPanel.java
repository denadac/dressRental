package ui;

import service.SherbimiQirase;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JPanel {
    private SherbimiQirase sherbimi;
    private JTextField emriField, cmimiField, materialiField, numriPjeseveField, ngjyraField, gjatesiaField, veshjaIdField;
    private JComboBox<String> madhesiaDropdown, veshjeTypeDropdown;
    private JButton shtoVeshjeBtn, shikoVeshjetBtn, shtoKlientBtn, shikoKlientetBtn;
    private JPanel dynamicFieldsPanel, veshjetPanel, klientPanel;

    public AdminPanel(SherbimiQirase sherbimi) {
        this.sherbimi = sherbimi;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));  // Increase the row count to 9 to add new field

        JLabel emriLabel = new JLabel("Emri:");
        emriField = new JTextField(15);

        JLabel cmimiLabel = new JLabel("Cmimi:");
        cmimiField = new JTextField(10);

        JLabel madhesiaLabel = new JLabel("Madhësia:");
        String[] madhesit = {"S", "M", "L", "XL"};
        madhesiaDropdown = new JComboBox<>(madhesit);

        JLabel veshjeTypeLabel = new JLabel("Lloji i Veshjes:");
        String[] veshjeTypes = {"Fustan Nuserie", "Fustan Eventi", "Kostum"};
        veshjeTypeDropdown = new JComboBox<>(veshjeTypes);

        JLabel veshjaIdLabel = new JLabel("Veshja ID:");
        veshjaIdField = new JTextField(10); // New field for veshjaId

        dynamicFieldsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        materialiField = new JTextField(10);
        numriPjeseveField = new JTextField(10);
        ngjyraField = new JTextField(10);
        gjatesiaField = new JTextField(10);
        dynamicFieldsPanel.setVisible(false);

        shtoVeshjeBtn = new JButton("Shto Veshje");
        shikoVeshjetBtn = new JButton("Shiko Veshjet");
        shtoKlientBtn = new JButton("Shto Klient");
        shikoKlientetBtn = new JButton("Shiko Klientet");

        veshjeTypeDropdown.addActionListener(e -> updateDynamicFields());
        shtoVeshjeBtn.addActionListener(e -> shtoVeshje());
        shikoVeshjetBtn.addActionListener(e -> shfaqVeshjet());
        shtoKlientBtn.addActionListener(e -> shtoKlient());
        shikoKlientetBtn.addActionListener(e -> shfaqKlientet());

        formPanel.add(emriLabel);
        formPanel.add(emriField);
        formPanel.add(cmimiLabel);
        formPanel.add(cmimiField);
        formPanel.add(madhesiaLabel);
        formPanel.add(madhesiaDropdown);
        formPanel.add(veshjeTypeLabel);
        formPanel.add(veshjeTypeDropdown);
        formPanel.add(veshjaIdLabel);  // Add the new label for Veshja ID
        formPanel.add(veshjaIdField);  // Add the new text field for Veshja ID
        formPanel.add(dynamicFieldsPanel);
        formPanel.add(shtoVeshjeBtn);
        formPanel.add(shikoVeshjetBtn);
        formPanel.add(shtoKlientBtn);
        formPanel.add(shikoKlientetBtn);

        add(formPanel, BorderLayout.NORTH);
        veshjetPanel = new JPanel(new BorderLayout());
        klientPanel = new JPanel(new BorderLayout());
        add(veshjetPanel, BorderLayout.CENTER);
        add(klientPanel, BorderLayout.SOUTH);
    }

    private void updateDynamicFields() {
        dynamicFieldsPanel.removeAll();
        String selectedType = (String) veshjeTypeDropdown.getSelectedItem();

        if ("Fustan Nuserie".equals(selectedType)) {
            dynamicFieldsPanel.add(new JLabel("Materiali:"));
            dynamicFieldsPanel.add(materialiField);
        } else if ("Fustan Eventi".equals(selectedType)) {
            dynamicFieldsPanel.add(new JLabel("Gjatësia:"));
            dynamicFieldsPanel.add(gjatesiaField);
        } else if ("Kostum".equals(selectedType)) {
            dynamicFieldsPanel.add(new JLabel("Ngjyra:"));
            dynamicFieldsPanel.add(ngjyraField);
        }

        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
        dynamicFieldsPanel.setVisible(true);
    }

    private void shtoVeshje() {
        try {
            String emri = emriField.getText().trim();
            if (emri.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Emri nuk mund të jetë bosh!", "Gabim", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String cmimiText = cmimiField.getText().trim();
            if (cmimiText.isEmpty() || !cmimiText.matches("\\d+(\\.\\d+)?")) {
                JOptionPane.showMessageDialog(this, "Cmimi duhet të jetë numër!", "Gabim", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double cmimi = Double.parseDouble(cmimiText);

            String veshjaIdText = veshjaIdField.getText().trim();
            if (veshjaIdText.isEmpty() || !veshjaIdText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Veshja ID duhet të jetë numër!", "Gabim", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int veshjaId = Integer.parseInt(veshjaIdText);

            String madhesia = (String) madhesiaDropdown.getSelectedItem();
            String selectedType = (String) veshjeTypeDropdown.getSelectedItem();
            Veshje veshje = null;

            if ("Fustan Nuserie".equals(selectedType)) {
                String materiali = materialiField.getText().trim();
                veshje = new FustanNuserie(veshjaId, emri, madhesia, cmimi, materiali);
            } else if ("Fustan Eventi".equals(selectedType)) {
                String gjatesia = gjatesiaField.getText().trim();
                veshje = new FustanEventi(veshjaId, emri, madhesia, cmimi, gjatesia);
            } else if ("Kostum".equals(selectedType)) {
                String ngjyra = ngjyraField.getText().trim();
                veshje = new Kostum(veshjaId, emri, madhesia, cmimi, ngjyra);
            }

            if (veshje != null) {
                sherbimi.shtoVeshje(veshje);
                JOptionPane.showMessageDialog(this, "Veshja u shtua me sukses!");
                emriField.setText("");
                cmimiField.setText("");
                veshjaIdField.setText(""); // Clear the veshjaId field
                materialiField.setText("");
                ngjyraField.setText("");
                gjatesiaField.setText("");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cmimi duhet të jetë numër dhe Veshja ID duhet të jetë numër!", "Gabim", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void shfaqVeshjet() {
        List<Veshje> veshjet = sherbimi.getTeGjithaVeshjet();
        StringBuilder sb = new StringBuilder();
        for (Veshje v : veshjet) {
            sb.append(v.toString()).append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        veshjetPanel.removeAll();
        veshjetPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        veshjetPanel.revalidate();
        veshjetPanel.repaint();
    }

    private void shtoKlient() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Shkruani ID e klientit:"));
            String emri = JOptionPane.showInputDialog(this, "Shkruani emrin e klientit:");
            String kontakt = JOptionPane.showInputDialog(this, "Shkruani kontaktin e klientit:");

            // Create a checkbox for activeRental
            JCheckBox activeRentalCheckBox = new JCheckBox("Aktive Qiraja?");
            int option = JOptionPane.showConfirmDialog(this, activeRentalCheckBox, "Aktive Qiraja", JOptionPane.OK_CANCEL_OPTION);

            boolean activeRental = false;
            int rentedVeshjeId = -1;

            // If the user selects "OK" and the checkbox is checked, we add rentedVeshjeId
            if (option == JOptionPane.OK_OPTION && activeRentalCheckBox.isSelected()) {
                activeRental = true;
                String rentedVeshjeIdInput = JOptionPane.showInputDialog(this, "Shkruani ID e Veshjes së Qiruar:");
                rentedVeshjeId = Integer.parseInt(rentedVeshjeIdInput.trim());
            }

            if (emri != null && !emri.trim().isEmpty() && kontakt != null && !kontakt.trim().isEmpty()) {
                Klienti klient = new Klienti(id, emri.trim(), kontakt.trim());
                klient.setActiveRental(activeRental, rentedVeshjeId); // Set active rental and rented Veshje ID
                sherbimi.shtoKlient(klient);
                JOptionPane.showMessageDialog(this, "Klienti u shtua me sukses!");
            } else {
                JOptionPane.showMessageDialog(this, "Të gjitha fushat janë të detyrueshme!", "Gabim", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID duhet të jetë numër!", "Gabim", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void shfaqKlientet() {
        List<Klienti> klientet = sherbimi.getTeGjitheKlientet();
        StringBuilder sb = new StringBuilder();
        for (Klienti k : klientet) {
            sb.append("ID: ").append(k.getId())
                    .append(", Emri: ").append(k.getEmri())
                    .append(", Kontakt: ").append(k.getKontakt())
                    .append(", Aktive Qiraja: ").append(k.hasActiveRental() ? "Po" : "Jo");
            if (k.hasActiveRental()) {
                sb.append(", Veshje ID: ").append(k.getRentedVeshjeId());
            }
            sb.append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        klientPanel.removeAll();
        klientPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        klientPanel.revalidate();
        klientPanel.repaint();
    }
}
