import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            View view = new View();
            JFrame frame = new JFrame("Baking Global Illumination / Radiosity - Test #1");
            frame.getContentPane().add(view);
            view.setPreferredSize(new Dimension(800, 600));
            view.setBackground(Color.WHITE);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            view.requestFocus();
        });
    }

}