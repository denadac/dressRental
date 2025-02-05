package ui;

import service.SherbimiQirase;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        SherbimiQirase sherbimi = new SherbimiQirase();
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Administrator", new AdminPanel(sherbimi));
        tabbedPane.addTab("Veshjet", new VeshjePanel(sherbimi));
        tabbedPane.addTab("Klienti", new ClientPanel(sherbimi));


        add(tabbedPane);
        setTitle("Sistemi i Qirasë së Veshjeve");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
