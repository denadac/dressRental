package ui;

import service.SherbimiQirase;
import model.Veshje;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ClientPanel extends JPanel {
    private SherbimiQirase sherbimi;


    public ClientPanel(SherbimiQirase sherbimi) {
        this.sherbimi = sherbimi;
        JButton shikoVeshjeBtn = new JButton("Shiko Veshjet Disponueshme");

        shikoVeshjeBtn.addActionListener((ActionEvent e) -> {
            StringBuilder builder = new StringBuilder();
            for (Veshje v : sherbimi.shikoVeshjetDisponueshme()) {
                builder.append(v.getEmri()).append(" - ").append(v.getCmimiQirasePerDite()).append("€/dita\n");
            }
            JOptionPane.showMessageDialog(this, builder.toString());
        });

        add(shikoVeshjeBtn);
    }
}