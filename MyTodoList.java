
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Container;
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
    // private AbstractFunctionPanel addPanel;
    public AbstractFunctionPanel addPanel;
    private AbstractFunctionPanel editPanel;
    private AbstractFunctionPanel timePanel;

    

    //放置按钮,以及设置按钮事件
    public void initButtons(){
        for(int i=0;i<4;i++){
            if(jButtons[i]==null) jButtons[i]=new JButton();
            jButtons[i].setBounds(8*d,h+i*3*h,2*d,2*h);
            add(jButtons[i]);
        }
        jButtons[0].setText("增加任务");
        jButtons[1].setText("进行任务");
        jButtons[2].setText("编辑任务");
        jButtons[3].setText("删除任务");
        jButtons[0].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MyTodoList.this.removeAll();
                MyTodoList.this.add(addPanel);
                MyTodoList.this.repaint();
            }
        });
        jButtons[1].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                //如果没有选中的任务，不能使用计时功能
                if(missionPane.getCurMission()==null){
                    JOptionPane.showMessageDialog(MyTodoList.this, "请先选中任务");
                    return;
                }
                MyTodoList.this.removeAll();
                MyTodoList.this.add(timePanel);
                MyTodoList.this.repaint();
            }
        });
        jButtons[2].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //如果没有选中的任务，不能使用编辑功能
                if(missionPane.getCurMission()==null){
                    JOptionPane.showMessageDialog(MyTodoList.this, "请先选中任务");
                    return;
                }
                MyTodoList.this.removeAll();
                MyTodoList.this.add(editPanel);
                MyTodoList.this.repaint();
            }
        });
        jButtons[3].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //如果没有选中任务，则没有效果
                if(missionPane.getCurMission()==null){
                    JOptionPane.showMessageDialog(MyTodoList.this, "请先选中任务");
                    return;
                }
                //直接删除当前选中任务
                missionPane.removeMission(missionPane.getCurMission());
            }
        });

    }



    public MyTodoList(){
        this(Main.width,Main.height);
    }

    public MyTodoList(int width,int height){
        setBounds(0,0,width,height);
        setLayout(null);
        d=width/11;
        h=height/13;
        // 构造任务滚动板实例以及各个功能面板的实例
        missionPane=new MissionPane(0,0,7*d,13*h);
        add(missionPane);
        initButtons();
        addPanel=new AddPanel(0,0,width, height, missionPane);
        editPanel=new EditPanel(0,0,width, height, missionPane);
        timePanel=new TimePanel(0,0,width, height, missionPane);
        addPanel.addObserver(this);
        editPanel.addObserver(this);
        timePanel.addObserver(this);

        //测试语句
        // removeAll();
        // add(addPanel);
        // repaint();


    }





    @Override
    public void repaint() {
        super.repaint();

    }


    //接受到功能界面的通知后返回主界面
    @Override
    public void update(Object message) {
        if(message==null) return;
        this.removeAll();
        for(JButton jButton:jButtons){
            add(jButton);
        }
        add(missionPane);
        repaint();
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame jFrame=new JFrame();
            jFrame.setBounds(0,0,Main.width,Main.height);
            jFrame.setLayout(null);
            MyTodoList mtl=new MyTodoList(Main.width,Main.height);
            jFrame.setContentPane((Container) mtl);
            jFrame.setVisible(true);
        });
    }



}
