import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class View extends JPanel implements KeyListener {
    
    private final World world = new World();
    
    public View() {
    
        world.addFace(-2.5, 1.45, 0.25,  Face.ORIENTATION.BOTTOM, true);

        Level.generate(world, -5, -2.5, -5);

        world.generateAllPatches();

        for (int i = 0; i < 16; i++) {
            System.out.println("radiosity pass " + i + " ...");
            world.processRadiosity();
        }

        world.bakeRadiosityTextures();

        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.BLACK);
        g2d.clearRect(0, 0, getWidth(), getHeight());
        g2d.translate(0, getHeight());
        g2d.scale(1, -1);

    }

    // key handler 

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
