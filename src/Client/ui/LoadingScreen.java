package Client.ui;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;


public class LoadingScreen extends JLabel{

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800,800);
        frame.setBackground(Color.blue);
        JLayeredPane layeredPane = new JLayeredPane();
        LoadingScreen ls = new LoadingScreen();
        layeredPane.setOpaque(true);
        layeredPane.setBackground(new Color(255,250,255,60));
        layeredPane.add(ls, 0, 0);
        layeredPane.setLayout(null);
        layeredPane.setVisible(true);
        frame.add(layeredPane);
        frame.setVisible(true);
        // ls.setVisible(true);
        // frame.add(ls);
        // frame.setVisible(true);
    }

    public LoadingScreen() {
        // JLabel label = new JLabel();
        // ImageIcon imageIcon = new ImageIcon(new ImageIcon(".\\src\\Client\\ui\\logo\\Pulse.gif").getImage()
        // .getScaledInstance(100, 100, Image.SCALE_REPLICATE));
        // label.setIcon(imageIcon);
        // label.setSize( 80, 80);

        // setLayout(new GridBagLayout());
        // setOpaque(true);
        // setBackground(new Color(255,250,255,60));
        // // setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        // setBounds(0,0,800,800);
        // add(label);

        ImageIcon imageIcon = new ImageIcon(new ImageIcon(".\\src\\Client\\ui\\logo\\Pulse.gif").getImage()
        .getScaledInstance(80, 80, Image.SCALE_REPLICATE));
        setIcon(imageIcon);
        setBounds(490,180,80,80);
        setVisible(false);
    }
}
