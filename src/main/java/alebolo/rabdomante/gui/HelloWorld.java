package alebolo.rabdomante.gui;


import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HelloWorld {
    File inputFile;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HelloWorld().showGui());
    }

    private void showGui() {
        JFrame frame = new JFrame("Rabdomante");
        JPanel outerPanel = new JPanel(new GridLayout(1, 2));
        JLabel label = new JLabel("You need to generate the template first, then fill it with your spreadsheet software, then load it here.");

        JButton selectFile = new JButton("Save template");

        selectFile.addActionListener(e -> {
            JFileChooser save = new JFileChooser();
            int res = save.showSaveDialog(outerPanel);
            if (res == JFileChooser.APPROVE_OPTION) {
                inputFile = save.getSelectedFile();
            }
        });


        outerPanel.add(label);
        outerPanel.add(selectFile);
        frame.add(outerPanel);
        frame.pack();
        frame.setVisible(true);
    }

}
