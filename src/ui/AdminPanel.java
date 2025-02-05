package ui;

import service.SherbimiQirase;
import model.FustanNuserie;
import model.Veshje;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {
    private SherbimiQirase sherbimi;
    private JTextField emriField;
    private JTextField cmimiField;
    private JButton shtoVeshjeBtn;

    public AdminPanel(SherbimiQirase sherbimi) {
        this.sherbimi = sherbimi;
        setLayout(new GridLayout(3, 2, 10, 10)); // Grid layout with spacing

        // Create UI Components
        JLabel emriLabel = new JLabel("Emri:");
        emriField = new JTextField(15);

        JLabel cmimiLabel = new JLabel("Cmimi:");
        cmimiField = new JTextField(10);

        shtoVeshjeBtn = new JButton("Shto Veshje");

        // Button Click Event
        shtoVeshjeBtn.addActionListener(e -> shtoVeshje());

        // Add Components to Panel
        add(emriLabel);
        add(emriField);
        add(cmimiLabel);
        add(cmimiField);
        add(shtoVeshjeBtn);
    }

    private void shtoVeshje() {
        try {
            String emri = emriField.getText().trim();
            double cmimi = Double.parseDouble(cmimiField.getText().trim());

            if (emri.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Emri nuk mund të jetë bosh!", "Gabim", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a new Veshje with input values
            Veshje veshje = new FustanNuserie(1, emri, "M", cmimi, "Saten");
            sherbimi.shtoVeshje(veshje);

            JOptionPane.showMessageDialog(this, "Veshja u shtua me sukses!");

            // Clear input fields after adding
            emriField.setText("");
            cmimiField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cmimi duhet të jetë një numër!", "Gabim", JOptionPane.ERROR_MESSAGE);
        }
    }
}
