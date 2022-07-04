import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.lang.reflect.WildcardType;
import java.time.chrono.HijrahEra;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.naming.Context;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.MouseInputAdapter;

class Main extends JFrame{

    static int x=100;
    static int y=100;
    static int width=500;
    static int height=400;


    public Main(){
        setBounds(x,y,width,height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        add(new MyTodoList());
        getContentPane();

    }


    public static void main(String[] args) {
       
        
    }
}
