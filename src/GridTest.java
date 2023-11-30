import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;


public class GridTest extends JPanel{

    private boolean changeAllowed = false;
    //keep reference to cyan for the height dimension
    final JPanel cyan = new JPanel();

    public GridTest(){
        cyan.setPreferredSize(new Dimension(600, 300));//provide sizing hint
    }

    private Dimension getCustomDimensions(){
        if ( changeAllowed ){
            return new Dimension((int)(super.getParent().getBounds().width * 0.3), cyan.getBounds().height);
        }else{
            return new Dimension(200, cyan.getBounds().height);
        }
    }
    @Override
    public Dimension getMaximumSize(){
        return getCustomDimensions();
    }
    @Override
    public Dimension getMinimumSize(){
        return getCustomDimensions();
    }
    @Override
    public Dimension getPreferredSize(){
        return getCustomDimensions();
    }

    public static void main(String[] args) throws Exception{
        SwingUtilities.invokeAndWait(new Runnable(){

            @Override
            public void run() {
                final int MINIMUM = 600;
                JFrame frame = new JFrame();
                frame.add(new JToolBar(), BorderLayout.NORTH);
                final JPanel blue = new JPanel();

                final GridTest green = new GridTest();
                green.setBackground(Color.green);
                green.setOpaque(true);

                green.cyan.setBackground(Color.cyan);
                green.cyan.setOpaque(true);
                blue.setOpaque(true);
                blue.setBackground(Color.blue);
                blue.setPreferredSize(new Dimension(900, 300));//hint at size
                blue.setMinimumSize(new Dimension(100, 200));//hint at size
                //Nest Box Layouts
                Box top = Box.createHorizontalBox();
                top.add(blue);
                Box bottom = Box.createHorizontalBox();
                bottom.add(green);
                bottom.add(green.cyan);
                Box vert = Box.createVerticalBox();
                vert.add(top);
                vert.add(bottom);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(vert);
                //listen for resizes
                blue.addComponentListener(new ComponentAdapter(){

                    @Override
                    public void componentResized(ComponentEvent e) {
                        if ( blue.getBounds().width < MINIMUM ){//set flag
                            green.changeAllowed = true;
                        }else{
                            green.changeAllowed = false;
                        }
                    }
                });

                frame.setSize(800, 600);
                frame.setPreferredSize(new Dimension(300, 400));
                frame.setVisible(true);

            }

        });
    }
}