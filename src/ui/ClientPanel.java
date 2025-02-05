package ui;

import model.*;
import service.SherbimiQirase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

public class ClientPanel extends JPanel {
    private SherbimiQirase sherbimi;
    private JTable veshjeTable;
    private DefaultTableModel tableModel;

    public ClientPanel(SherbimiQirase sherbimi) {
        this.sherbimi = sherbimi;
        setLayout(new BorderLayout());

        // Create buttons
        JButton shikoVeshjeBtn = new JButton("Shiko Veshjet Disponueshme");
        shikoVeshjeBtn.addActionListener(this::showVeshjetDetails);

        JButton updateButton = new JButton("Përditëso Tabelën");
        updateButton.addActionListener(e -> loadVeshje(sherbimi.getTeGjithaVeshjet()));

        // Create top panel for the buttons
        JPanel topPanel = new JPanel();
        topPanel.add(shikoVeshjeBtn);
        topPanel.add(updateButton);  // Add the update button
        add(topPanel, BorderLayout.NORTH);

        // Create the table for Veshje list
        veshjeTable = new JTable();
        tableModel = new DefaultTableModel(new Object[]{
                "ID", "Emri", "Çmimi", "Ngjyra", "Materiali", "Gjatësia", "E Disponueshme", "Rezervo"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Only the "Rezervo" button is editable
            }
        };

        veshjeTable.setModel(tableModel);
        veshjeTable.getColumn("Rezervo").setCellRenderer(new ButtonRenderer());
        veshjeTable.getColumn("Rezervo").setCellEditor(new ButtonEditor(new JCheckBox(), this::rezervoVeshje));

        add(new JScrollPane(veshjeTable), BorderLayout.CENTER);

        // Initially populate the table
        loadVeshje(sherbimi.getTeGjithaVeshjet());
    }

    private void loadVeshje(List<Veshje> veshjet) {
        tableModel.setRowCount(0); // Clear the table
        for (Veshje veshje : veshjet) {
            tableModel.addRow(new Object[]{
                    veshje.getVeshjaId(),
                    veshje.getEmri(),
                    veshje.getCmimiQirasePerDite(),
                    veshje instanceof Kostum ? ((Kostum) veshje).getNgjyra() : "",
                    veshje instanceof FustanNuserie ? ((FustanNuserie) veshje).getMateriali() : "",
                    veshje instanceof FustanEventi ? ((FustanEventi) veshje).getGjatesia() : "",
                    veshje.eshteEDisponueshme() ? "Po" : "Jo",
                    "Rezervo"
            });
        }
    }

    private void showVeshjetDetails(ActionEvent e) {
        StringBuilder builder = new StringBuilder();
        List<Veshje> veshjet = sherbimi.getTeGjithaVeshjet();
        for (Veshje v : veshjet) {
            builder.append(v.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(this, builder.toString(), "Veshjet Disponueshme", JOptionPane.INFORMATION_MESSAGE);
    }

    private void rezervoVeshje(int row) {
        int veshjeId = (int) veshjeTable.getValueAt(row, 0);
        Veshje veshje = sherbimi.getVeshjeById(veshjeId);

        if (veshje != null && veshje.eshteEDisponueshme()) {
            String klientIdStr = JOptionPane.showInputDialog(this, "Jep ID e Klientit:");
            int klientId = Integer.parseInt(klientIdStr);
            Klienti klient = sherbimi.getKlientById(klientId);

            if (klient != null && !klient.hasActiveRental()) {
                String dataFillimitStr = JOptionPane.showInputDialog(this, "Jep Datën e Fillimit (yyyy-MM-dd):");
                String dataMbarimitStr = JOptionPane.showInputDialog(this, "Jep Datën e Mbarimit (yyyy-MM-dd):");
                String paguarStr = JOptionPane.showInputDialog(this, "Paguar (po/jo):");

                LocalDate dataFillimit = LocalDate.parse(dataFillimitStr, DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate dataMbarimit = LocalDate.parse(dataMbarimitStr, DateTimeFormatter.ISO_LOCAL_DATE);
                boolean paguar = "po".equalsIgnoreCase(paguarStr);

                // Rezervo veshjen dhe përditëso statusin
                veshje.merreMeQira();
                klient.setActiveRental(true, veshjeId);
                sherbimi.shtoQira(klientId, veshjeId, dataFillimit, dataMbarimit, paguar); // Assumes shtoQira method is updated to include dates and payment status

                loadVeshje(sherbimi.getTeGjithaVeshjet());
                JOptionPane.showMessageDialog(this, "Veshja u rezervua me sukses.");
            } else {
                JOptionPane.showMessageDialog(this, "Klienti ka qira aktive ose nuk ekziston.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veshja nuk është e disponueshme.");
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
        private int row;
        private Consumer<Integer> action;

        public ButtonEditor(JCheckBox checkBox, Consumer<Integer> action) {
            super(checkBox);
            this.action = action;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                action.accept(row);
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
