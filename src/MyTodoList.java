package src;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Container;
import java.awt.event.*;
import java.awt.*;




public class MyTodoList extends JPanel implements Observer{

    //按钮组件,从上到下1,2,3,4
    JButton[] jButtons=new JButton[4];

    //记录横纵坐标基本单位
    private int d;
    private int h;

    //基本任务滚动面板
    private MissionPane missionPane;

    //切换界面用的布局
    CardLayout card;

    //基本功能界面
    // private AbstractFunctionPanel addPanel;
    private AbstractFunctionPanel addPanel;
    private AbstractFunctionPanel editPanel;
    private AbstractFunctionPanel timePanel;
    private JPanel mainPanel;

    

    //创建按钮,以及设置按钮事件
    public void creButtons(){
        for(int i=0;i<4;i++){
            if(jButtons[i]==null) jButtons[i]=new JButton();
            jButtons[i].setBounds(8*d,h+i*3*h,2*d,2*h);
        }
        jButtons[0].setText("增加任务");
        jButtons[1].setText("进行任务");
        jButtons[2].setText("编辑任务");
        jButtons[3].setText("删除任务");
        jButtons[0].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(MyTodoList.this,addPanel.getName());
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
                card.show(MyTodoList.this, timePanel.getName());
                timePanel.init();
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
                card.show(MyTodoList.this, editPanel.getName());
                editPanel.init();
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

    //创建滚动任务面板以及功能面板
    private void creFunctionPanels(){
        int width=getWidth();
        int height=getHeight();
        missionPane=new MissionPane(0,0,7*d,13*h);
        missionPane.addMissions(DAO.loadMissionsToday());

        addPanel=new AddPanel(0,0,width, height, missionPane);
        editPanel=new EditPanel(0,0,width, height, missionPane);
        timePanel=new TimePanel(0,0,width, height, missionPane);
        addPanel.addObserver(this);
        editPanel.addObserver(this);
        timePanel.addObserver(this);
        addPanel.setName("addPanel");
        editPanel.setName("editPanel");
        timePanel.setName("timePanel");
    }

    public MissionPane getMissionPane() {
        return missionPane;
    }


    //创建主面板
    private void creMainPanel(){
        mainPanel=new JPanel();
        mainPanel.setBounds(0,0,getWidth(),getHeight());
        mainPanel.setLayout(null);
        mainPanel.setName("mainPanel");
        for(JButton jButton:jButtons){
            if(jButton!=null) mainPanel.add(jButton);
        }
        if(missionPane!=null) mainPanel.add(missionPane);
    }

    public MyTodoList(){
        this(Parameter.width,Parameter.height);
    }

    public MyTodoList(int width,int height){
        setBounds(0,0,width,height);
        card=new CardLayout();
        setLayout(card);
        d=width/11;
        h=height/13;
        // 构造任务滚动板实例以及各个功能面板的实例
        creButtons();
        creFunctionPanels();
        creMainPanel();
        if(mainPanel!=null) add(mainPanel.getName(),mainPanel);
        if(addPanel!=null) add(addPanel.getName(),addPanel);
        if(editPanel!=null) add(editPanel.getName(),editPanel);
        if(timePanel!=null) add(timePanel.getName(),timePanel);
        card.show(MyTodoList.this, mainPanel.getName());
    }





    //接受到功能界面的通知后返回主界面
    @Override
    public void update(Object message) {
        //切换到主面板
        card.show(MyTodoList.this, mainPanel.getName());
    }

   





    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame jFrame=new JFrame();
            jFrame.setBounds(0,0,Parameter.width,Parameter.height);
            jFrame.setLayout(null);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            MyTodoList mtl=new MyTodoList(Parameter.width,Parameter.height);
            jFrame.setContentPane((Container) mtl);
            jFrame.setVisible(true);
        });
    }



}
