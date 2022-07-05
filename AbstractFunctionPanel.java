import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.*;



/*
 * 在抽象功能面板中完成界面布局，在具体功能面板类中实现具体功能
 */

public abstract class AbstractFunctionPanel extends JPanel implements Publisher{
    
    //按钮组件,从上到下1,2,3,4
    JButton[] jButtons=new JButton[3];

    //记录横纵坐标基本单位
    private int d;
    private int h;


    //显示区域
    protected JLabel[] jLabels=new JLabel[3];
    protected JTextField[] jTextFields=new JTextField[2];
    protected JTextArea jTextArea;
    protected Observer observer;
    protected MissionPane missionPane;

    

    


    //初始化左边区域组件
    public void initLeftArea(){
        //加上滚动文本区域作为具体任务内容的输入和显示区域
        jTextArea=new JTextArea();
        JScrollPane jScrollPane=new JScrollPane(jTextArea);
        jScrollPane.setBounds(d,6*h,6*d,6*h);
        add(jScrollPane);

        //加上文本输入与现实标签
        for(int i=0;i<jLabels.length;i++){
            jLabels[i]=new JLabel();
            jLabels[i].setBounds(d,h+2*i*h,d,h);
            add(jLabels[i]);
        }
        jLabels[0].setText("名称");
        jLabels[1].setText("时间");
        jLabels[2].setText("内容");
        //加入用于输入和显示的文本行
        for(int i=0;i<jTextFields.length;i++){
            jTextFields[i]=new JTextField();
            jTextFields[i].setBounds(2*d,h+2*i*h,4*d,h);
            add(jTextFields[i]);
        }


    }


    

    //放置按钮,以及设置按钮事件
    public void initButtons(){
        for(int i=0;i<jButtons.length;i++){
            if(jButtons[i]==null) jButtons[i]=new JButton();
            jButtons[i].setBounds(8*d,h+i*3*h,2*d,2*h);
            jButtons[i].setFont(new Font("name",Font.BOLD,25));
            add(jButtons[i]);
        }
        jButtons[0].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                actionA();
            }
        });
        jButtons[1].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                actionB();
            }
        });
        jButtons[2].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                actionC();
            }
        });
        jButtons[2].setText("退出");
        
        
    }


    private AbstractFunctionPanel(int width,int height){
        setBounds(0,0,Main.width,Main.height);
        setLayout(null);
        d=Main.width/11;
        h=Main.height/13;
        initButtons();
        initLeftArea();

    }

    public AbstractFunctionPanel(int width,int height,MissionPane missionPane){
        this(width,height);
        this.missionPane=missionPane;
    }




    public abstract void actionA();

    public abstract void actionB();

    public void actionC(){
        notice(observer);
    }


    @Override
    public void notice(Observer observer) {
        if(observer!=null) observer.update(this);

    }
    @Override
    public void addObserver(Observer observer) {
        this.observer=observer;
    }

   public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame jFrame=new JFrame();
            jFrame.setBounds(100,100,Main.width,Main.height);
            jFrame.setVisible(true);
            jFrame.setLayout(null);
            AbstractFunctionPanel abstractFunctionPanel=new AbstractFunctionPanel(Main.width,Main.height) {
                
                @Override
                public void actionA() {
                    System.out.println("do");
                    
                }

                @Override
                public void actionB() {
                    System.out.println("it");
                    
                }
            };
            jFrame.add(abstractFunctionPanel);
        });
   }



}
