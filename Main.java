
import javax.swing.JFrame;


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
