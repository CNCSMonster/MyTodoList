import java.awt.Container;
import src.Parameter;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import src.DAO;
import src.MyTodoList;

public class Main extends JFrame{

    public Main(){

        setBounds(0,0,Parameter.width,Parameter.height);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyTodoList mtl=new MyTodoList(Parameter.width,Parameter.height);
        setContentPane((Container) mtl);
        setVisible(true);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                mtl.getMissionPane().store();
            }
        });;
    }

    //重写主界面的退出事件，每次退出后都会把任务写入文件
    


    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new Main();
        });
        
    }
}
