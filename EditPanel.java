import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class EditPanel extends AbstractFunctionPanel{

    //记录当前要选中的任务
    Mission mission=null;


    public EditPanel(int width, int height,MissionPane missionPane) {
        super(width, height,missionPane);
        jButtons[0].setText("修改");
        jButtons[1].setText("还原");
        jButtons[2].setText("退出");
        if(missionPane!=null) mission=missionPane.getCurMission();
        mission=new Mission(111,"dd","busdsds");
        actionB();
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
            //如果输入的修改信息正确
            if(mission!=null){
                mission.setText(title);
                mission.setContent(content);
                mission.setLimitTime(limitTime);
            }  
            afterEdit();
        } catch (Exception e) {
            System.out.println("修改任务信息失败");
        }
    }

    @Override
    public void actionB() {
        if(mission==null) return;
        jTextFields[0].setText(mission.getText());
        //把时间从秒转化为时:分:秒的形式
        int h=0;
        int m=0;
        int s=0;
        int lh=0;
        int lm=0;
        int ls=0;
        h=mission.getRestTime()/3600;
        m=mission.getRestTime()/60%60;
        s=mission.getRestTime()%60;
        lh = mission.getLimitTime() / 3600;
        lm = mission.getLimitTime() / 60 % 60;
        ls = mission.getLimitTime() % 60;
        jTextFields[1].setText(h+":"+m+":"+s+" / "+lh+":"+lm+":"+ls);
        jTextArea.setText(mission.getContent());
        
    }

    //修改成功后处理
    public void afterEdit(){
        //TODO 修改成功后处理，比如提示弹窗，比如退出当前界面
        JOptionPane.showMessageDialog(this, "修改成功！");

        notice(observer);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame jFrame=new JFrame();
            jFrame.setBounds(100,100,Main.width,Main.height);
            jFrame.setVisible(true);
            jFrame.setLayout(null);
            AbstractFunctionPanel abstractFunctionPanel=new EditPanel(Main.width,Main.height,null);
            jFrame.add(abstractFunctionPanel);
        });
    }
    
}
