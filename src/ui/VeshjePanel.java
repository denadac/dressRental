package ui;

import service.SherbimiQirase;
import model.Veshje;
import model.FustanNuserie;
import model.FustanEventi;
import model.Kostum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

public class VeshjePanel extends JPanel {
    private SherbimiQirase sherbimi;
    private JTable veshjeTable;
    private JTextField searchField;
    private JButton searchButton;
    private JButton updateButton;

    public VeshjePanel(SherbimiQirase sherbimi) {
        this.sherbimi = sherbimi;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchField = new JTextField(20);
        searchButton = new JButton("Kërko");
        updateButton = new JButton("Përditëso Tabelën");

        searchButton.addActionListener(this::searchVeshje);
        updateButton.addActionListener(e -> loadVeshje(sherbimi.getTeGjithaVeshjet()));

        topPanel.add(new JLabel("Kërko (Emri ose ID):"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(updateButton);

        add(topPanel, BorderLayout.NORTH);

        veshjeTable = new JTable();
        add(new JScrollPane(veshjeTable), BorderLayout.CENTER);

        loadVeshje(sherbimi.getTeGjithaVeshjet());
    }

    private void loadVeshje(List<Veshje> veshjet) {
        String[] columnNames = {"ID", "Emri", "Çmimi", "Ngjyra", "Materiali", "Gjatësia", "E Disponueshme", "Modifiko", "Fshi"};
        Object[][] data = new Object[veshjet.size()][9];

        for (int i = 0; i < veshjet.size(); i++) {
            Veshje veshje = veshjet.get(i);
            data[i][0] = veshje.getVeshjaId();
            data[i][1] = veshje.getEmri();
            data[i][2] = veshje.getCmimiQirasePerDite();
            data[i][3] = veshje instanceof Kostum ? ((Kostum) veshje).getNgjyra() : "";
            data[i][4] = veshje instanceof FustanNuserie ? ((FustanNuserie) veshje).getMateriali() : "";
            data[i][5] = veshje instanceof FustanEventi ? ((FustanEventi) veshje).getGjatesia() : "";
            data[i][6] = veshje.eshteEDisponueshme() ? "Po" : "Jo";
            data[i][7] = "Modifiko";
            data[i][8] = "Fshi";
        }

        veshjeTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column == 7 || column == 8;
            }
        });

        veshjeTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
        veshjeTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), row -> modifyVeshje(row)));
        veshjeTable.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer());
        veshjeTable.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox(), this::deleteVeshje));
    }

    private void searchVeshje(ActionEvent e) {
        String query = searchField.getText().trim().toLowerCase();
        List<Veshje> filteredVeshje = sherbimi.getTeGjithaVeshjet().stream()
                .filter(v -> v.getEmri().toLowerCase().contains(query) || String.valueOf(v.getVeshjaId()).contains(query))
                .collect(Collectors.toList());
        loadVeshje(filteredVeshje);
    }

    private void modifyVeshje(int row) {
        int veshjaId = (int) veshjeTable.getValueAt(row, 0);
        Veshje veshje = sherbimi.getVeshjeById(veshjaId); // Assumes you have a getVeshjeById method in SherbimiQirase

        if (veshje != null) {
            String newEmri = JOptionPane.showInputDialog(this, "Ndrysho Emrin", veshje.getEmri());
            double newCmimi = Double.parseDouble(JOptionPane.showInputDialog(this, "Ndrysho Çmimin", veshje.getCmimiQirasePerDite()));
            boolean eDisponueshme = JOptionPane.showConfirmDialog(this, "A është e disponueshme?", "Disponueshmëria", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

            if (veshje instanceof FustanNuserie) {
                String newMateriali = JOptionPane.showInputDialog(this, "Ndrysho Materialin", ((FustanNuserie) veshje).getMateriali());
                ((FustanNuserie) veshje).setMateriali(newMateriali);
            } else if (veshje instanceof FustanEventi) {
                String newGjatesia = JOptionPane.showInputDialog(this, "Ndrysho Gjatësinë", ((FustanEventi) veshje).getGjatesia());
                ((FustanEventi) veshje).setGjatesia(newGjatesia);
            } else if (veshje instanceof Kostum) {
                String newNgjyra = JOptionPane.showInputDialog(this, "Ndrysho Ngjyrën", ((Kostum) veshje).getNgjyra());
                ((Kostum) veshje).setNgjyra(newNgjyra);
            }

            veshje.setEmri(newEmri);
            veshje.setCmimiQirasePerDite(newCmimi);
            veshje.setEDisponueshme(eDisponueshme);

            sherbimi.updateVeshje(veshje); // Assumes you have an updateVeshje method in SherbimiQirase
            loadVeshje(sherbimi.getTeGjithaVeshjet());
        }
    }

    private void deleteVeshje(int row) {
        if (row >= 0) { // Ensure the row index is valid
            int veshjaId = (int) veshjeTable.getValueAt(row, 0);
            sherbimi.fshiVeshje(veshjaId);
            loadVeshje(sherbimi.getTeGjithaVeshjet());
        }
    }
}
