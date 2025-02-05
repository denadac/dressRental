package ui;

import model.Klienti;
import model.Veshje;
import service.SherbimiQirase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ClientPanel extends JPanel {
    private SherbimiQirase sherbimi;

    public ClientPanel(SherbimiQirase sherbimi) {
        this.sherbimi = sherbimi;
        setLayout(new BorderLayout());

        // Create buttons
        JButton shikoVeshjeBtn = new JButton("Shiko Veshjet Disponueshme");
        shikoVeshjeBtn.addActionListener(this::showVeshjetDetails);

        JButton perditesoKlientetBtn = new JButton("Përditëso Klientët");
        perditesoKlientetBtn.addActionListener(this::updateClientTable);

        // Create top panel for the buttons
        JPanel topPanel = new JPanel();
        topPanel.add(shikoVeshjeBtn);
        topPanel.add(perditesoKlientetBtn);  // Add the update button next to the existing one
        add(topPanel, BorderLayout.NORTH);

        // Create the table for Klient list
        JTable klientTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{
                "Klient ID", "Emri", "Kontakt", "Ka Qira Aktive", "Shiko Detajet", "Shto Qera", "Paguaj Qera", "Mbyll Qerane"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 4; // Only buttons are editable
            }
        };

        klientTable.setModel(tableModel);
        klientTable.getColumn("Shiko Detajet").setCellRenderer(new ButtonRenderer());
        klientTable.getColumn("Shiko Detajet").setCellEditor(new ButtonEditor(new JButton("Shiko Detajet")));
        klientTable.getColumn("Shto Qera").setCellRenderer(new ButtonRenderer());
        klientTable.getColumn("Shto Qera").setCellEditor(new ButtonEditor(new JButton("Shto Qera")));
        klientTable.getColumn("Paguaj Qera").setCellRenderer(new ButtonRenderer());
        klientTable.getColumn("Paguaj Qera").setCellEditor(new ButtonEditor(new JButton("Paguaj Qera")));
        klientTable.getColumn("Mbyll Qerane").setCellRenderer(new ButtonRenderer());
        klientTable.getColumn("Mbyll Qerane").setCellEditor(new ButtonEditor(new JButton("Mbyll Qerane")));

        add(new JScrollPane(klientTable), BorderLayout.CENTER);

        // Initially populate the table
        populateKlientTable(tableModel);
    }

    private void showVeshjetDetails(ActionEvent e) {
        StringBuilder builder = new StringBuilder();
        List<Veshje> veshjet = sherbimi.getTeGjithaVeshjet();
        for (Veshje v : veshjet) {
            builder.append(v.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(this, builder.toString(), "Veshjet Disponueshme", JOptionPane.INFORMATION_MESSAGE);
    }

    // New method to update the client table
    private void updateClientTable(ActionEvent e) {
        DefaultTableModel tableModel = (DefaultTableModel) ((JTable) ((JScrollPane) getComponent(1)).getViewport().getView()).getModel();
        tableModel.setRowCount(0); // Clear the current table
        populateKlientTable(tableModel); // Re-populate with the latest client data
    }

    private void populateKlientTable(DefaultTableModel tableModel) {
        List<Klienti> klientList = sherbimi.getTeGjitheKlientet();
        for (Klienti klient : klientList) {
            boolean kaQiraAktive = klient.hasActiveRental();
            tableModel.addRow(new Object[]{
                    klient.getId(),
                    klient.getEmri(),
                    klient.getKontakt(),
                    kaQiraAktive ? "Po" : "Jo",
                    "Shiko Detajet",
                    "Shto Qera",
                    "Paguaj Qera",
                    "Mbyll Qerane"
            });
        }
    }

    private void shikoDetajetQira(int klientId) {
        Klienti klient = sherbimi.getKlientById(klientId);
        if (klient.hasActiveRental()) {
            Veshje veshja = sherbimi.getVeshjeById(klient.getRentedVeshjeId());
            String message = String.format("Klienti ka qira aktive:\nVeshje: %s\nData e fillimit: %s\nData e mbarimit: %s",
                    veshja.toString(), sherbimi.getRentalStartDate(klientId), sherbimi.getRentalEndDate(klientId));
            JOptionPane.showMessageDialog(this, message);
        } else {
            JOptionPane.showMessageDialog(this, "Klienti nuk ka qira aktive.");
        }
    }

    private void shtoQera(int klientId) {
        Klienti klient = sherbimi.getKlientById(klientId);
        if (!klient.hasActiveRental()) {
            String veshjeIdStr = JOptionPane.showInputDialog("Jep ID e Veshjes për qira:");
            int veshjeId = Integer.parseInt(veshjeIdStr);
            Veshje veshja = sherbimi.getVeshjeById(veshjeId);
            if (veshja != null && veshja.eshteEDisponueshme()) {
                sherbimi.shtoQira(klientId, veshjeId);
                JOptionPane.showMessageDialog(this, "Qira u shtua me sukses.");
            } else {
                JOptionPane.showMessageDialog(this, "Veshja nuk është e disponueshme.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Klienti ka qira aktive dhe nuk mund të shtohet një e re.");
        }
    }

    private void paguajQera(int klientId) {
        Klienti klient = sherbimi.getKlientById(klientId);
        if (klient.hasActiveRental()) {
            Veshje veshja = sherbimi.getVeshjeById(klient.getRentedVeshjeId());
            double rentalPrice = veshja.getCmimiQirasePerDite();
            int days = sherbimi.calculateRentalDays(klientId);
            double totalAmount = rentalPrice * days;
            int confirm = JOptionPane.showConfirmDialog(this,
                    String.format("ID e qirasë: %d\nTotali për pagesë: %.2f €\nDëshironi të paguani?", klient.getRentedVeshjeId(), totalAmount),
                    "Paguaj Qira", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                sherbimi.paguajQira(klientId);
                JOptionPane.showMessageDialog(this, "Pagesa u krye me sukses.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Klienti nuk ka qira aktive.");
        }
    }

    private void mbyllQerane(int klientId) {
        Klienti klient = sherbimi.getKlientById(klientId);
        if (klient.hasActiveRental()) {
            sherbimi.mbyllQira(klientId);
            JOptionPane.showMessageDialog(this, "Qira u mbyll me sukses.");
        } else {
            JOptionPane.showMessageDialog(this, "Klienti nuk ka qira aktive.");
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JButton button) {
            super(new JCheckBox());
            this.button = button;
            this.button.setOpaque(true);
            this.button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                JOptionPane.showMessageDialog(button, label + " clicked");
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
