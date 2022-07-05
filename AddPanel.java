import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/*
 * 增加任务面板
 */
public class AddPanel extends AbstractFunctionPanel{



    public AddPanel(int width, int height,MissionPane missionPane) {
        this(0,0,width,height,missionPane);
    }

    public AddPanel(int x, int y, int width, int height, MissionPane missionPane) {
        super(x,y,width, height, missionPane);
        jButtons[0].setText("增加");
        jButtons[1].setText("清空");
        jButtons[2].setText("退出");
    }

	@Override
    public void actionA() {

        try {
            //对增加进行输入检查
            String title=jTextFields[0].getText();
            if(title.length()==0){
                JOptionPane.showMessageDialog(this,"任务名不能为空！");
                throw new Exception("任务名为空");
            }
            //任务内容为空
            String content=jTextArea.getText();
            //时间输入格式允许输入分钟，所以这里需要对时间进行处理
            //对输入的时间进行翻译
            String sa[]=jTextFields[1].getText().split(":");
            int h=0;  //时
            int m=0;  //分
            int s=0;  //秒
            try {
                if(sa.length==1){
                    s=Integer.parseInt(sa[0]);
                }
                else if(sa.length==2){
                    s=Integer.parseInt(sa[1]);
                    m=Integer.parseInt(sa[0]);
                }
                else if(sa.length==3){
                    s=Integer.parseInt(sa[2]);
                    m=Integer.parseInt(sa[1]);
                    h=Integer.parseInt(sa[0]);
                }
                else{
                    throw new Exception();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "时间输入格式错误，请按照\"小时:分钟:秒\"的格式输入,如1:20:30(表示1小时20分30秒) ");
                throw new Exception();
            }
            int limitTime=h*3600+m*60+s;
            Mission mission=new Mission(limitTime,title,content);
            if(missionPane!=null) missionPane.addMission(mission);
            System.out.println(mission);    //用于测试的语句
        } catch (Exception e) {
            System.out.println("添加任务失败");
        }
    }

    @Override
    public void actionB() {
        for(JTextField jTextField:jTextFields){
            if(jTextField!=null) jTextField.setText("");
        }
        jTextArea.setText("");
    }
    
    //增加后操作
    public void afterAdd(){
        JOptionPane.showMessageDialog(this, "增加成功!");
        //然后退出界面
        notice(observer);
    }

    

    @Override
    public void paintComponents(Graphics g) {
        if(jScrollPane!=null) jScrollPane.paint(g);
        for(JButton jButton:jButtons){
            if(jButton!=null) jButton.paint(g);
        }
        for(JLabel jLabel:jLabels){
            if(jLabel!=null) jLabel.paint(g);
        }
        for(JTextField jTextField:jTextFields){
            if(jTextField!=null) jTextField.paint(g);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame jFrame=new JFrame();
            jFrame.setBounds(100,100,Main.width,Main.height);
            jFrame.setVisible(true);
            jFrame.setLayout(null);
            AbstractFunctionPanel abstractFunctionPanel=new AddPanel(Main.width,Main.height,null);
            JPanel jPanel=new JPanel();
            jPanel.setBounds(0,0,Main.width,Main.height);
            jPanel.setLayout(null);
            jPanel.add(abstractFunctionPanel);
            jFrame.add(jPanel);

            
        });
    }





}
