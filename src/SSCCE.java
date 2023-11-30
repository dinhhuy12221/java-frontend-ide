import java.awt.*;
import java.util.*;
import javax.swing.*;

public class SSCCE extends JPanel
{
    public SSCCE()
    {
        JPanel blue = new JPanel();
        // JPanel blue1 = new JPanel();
        // blue1.setOpaque(true);
        blue.setBackground( Color.BLUE );
        blue.setPreferredSize( new Dimension(500, 100) );
        blue.setLayout(null);
        // blue1.setLayout(new BoxLayout(blue1, BoxLayout.LINE_AXIS));
        // blue.setBounds(20,20,100,100);
        JButton button = new JButton("Hello");
        button.setBounds(50,50,50,50);
        // button.setVisible(true);

        // button.setLocation(50, 50);
        // button.setMinimumSize(new Dimension(100,30));
        // blue1.add(button);
        blue.add(button);
        
        Box box = Box.createHorizontalBox();
        
        JPanel green = new JPanel();
        green.setBackground( Color.GREEN );
        green.setPreferredSize( new Dimension(50, 50) );
        green.setMaximumSize( green.getPreferredSize() );
        box.add( green );
        
        JPanel cyan = new JPanel();
        JButton bt1 = new JButton();
        bt1.setLocation(50,50);
        cyan.setBackground( Color.CYAN );
        cyan.setPreferredSize( new Dimension(50, 100) );
        cyan.setMinimumSize( cyan.getPreferredSize() );
        cyan.add(bt1);
        box.add( cyan );
        
        setLayout( new BorderLayout() );
        add(blue, BorderLayout.NORTH);
        add(box, BorderLayout.CENTER);
        blue.revalidate();
        blue.repaint();
    }

    private static void createAndShowGUI()
    {
        JFrame frame = new JFrame("SSCCE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SSCCE());
        frame.setLocationByPlatform( true );
        frame.pack();
        frame.setVisible( true );
        frame.revalidate();
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }
}