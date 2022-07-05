
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;




public class MyTodoList extends JPanel implements Observer{

    //按钮组件,从上到下1,2,3,4
    JButton[] jButtons=new JButton[4];

    //记录横纵坐标基本单位
    private int d;
    private int h;

    //基本任务滚动面板
    private MissionPane missionPane;

    //基本功能界面
    private AbstractFunctionPanel addPanel;
    private AbstractFunctionPanel editPanel;
    private AbstractFunctionPanel timePanel;

    

    //放置按钮,以及设置按钮事件
    public void initButtons(){
        for(int i=0;i<4;i++){
            if(jButtons[i]==null) jButtons[i]=new JButton();
            jButtons[i].setBounds(8*d,h+i*3*h,2*d,2*h);
            //设置按钮事件
            jButtons[i].addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    // MyTodoList.this.removeAll();
                    
                }
            });
            add(jButtons[i]);
        }

    }



    public MyTodoList(){
        setBounds(0,0,Main.width,Main.height);
        setLayout(null);
        d=Main.width/11;
        h=Main.height/13;
        initButtons();
        //TODO 构造任务滚动板实例以及各个功能面板的实例
        missionPane=new MissionPane(0,0,7*d,13*h);
        

    }





    @Override
    public void repaint() {
        super.repaint();

    }


    
    @Override
    public void update(Object message) {
        
        
    }





    public static void main(String[] args) {
        JFrame jFrame=new JFrame();
        jFrame.setBounds(0,0,Main.width,Main.height);
        jFrame.setLayout(null);
        jFrame.add(new MyTodoList());
        jFrame.setVisible(true);
    }



}
