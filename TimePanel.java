import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/*
 * 任务计时面板
 */
public class TimePanel extends AbstractFunctionPanel{

    Thread timeThread;
    Mission mission;

    public TimePanel(int width, int height,MissionPane missionPane) {
        this(0, 0, width, height, missionPane);
    }

    public TimePanel(int x, int y, int width, int height, MissionPane missionPane) {
        super(width, height,missionPane);
        jButtons[0].setText("开始");
        jButtons[1].setText("暂停");
        jButtons[2].setText("退出");
        //显示区域不允许外部改动
        jTextArea.setEditable(false);
        for(JTextField jTextField:jTextFields){
            if(jTextField!=null) jTextField.setEditable(false);
        }
        if(missionPane!=null) mission=missionPane.getCurMission();
        // mission=new Mission();      //用来测试用的语句
        if(mission!=null){
            jTextFields[0].setText(mission.getText());
            jTextArea.setText(mission.getContent());
        }
        refreshTimeFiled();
        setLocation(x,y);
    }

    @Override
    public void actionA() {
        if(mission==null){
            System.out.println("缺少任务");
            return;
        }
        mission.startCountDown();   //任务开始倒计时
        //开始个线程，让时间标签一直显示任务的内容
        timeThread=new Thread(()->{
            while(true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
                synchronized(jTextFields[1]){
                    refreshTimeFiled();
                    if(mission.getRestTime()==0){
                        timeEnd();
                        break;
                    }
                }
            }

        });
        timeThread.start();
    }

    //刷新时间文本行显示
    public void refreshTimeFiled() {
        if(mission==null) return;
        int h = 0;
        int m = 0;
        int s = 0;
        int lh = 0;
        int lm = 0;
        int ls = 0;
        h = mission.getRestTime() / 3600;
        m = mission.getRestTime() / 60 % 60;
        s = mission.getRestTime() % 60;
        lh = mission.getLimitTime() / 3600;
        lm = mission.getLimitTime() / 60 % 60;
        ls = mission.getLimitTime() % 60;
        jTextFields[1].setText(h + ":" + m + ":" + s + " / " + lh + ":" + lm + ":" + ls);

    }


    //暂停计时
    @Override
    public void actionB() {
        if(mission==null) return;
        //结束任务计时线程
        mission.pauseCounttDown();
        //如果从未开始计时，则退出方法
        if(timeThread==null) return;
        //如果计时已经结束，线程已经死亡，则退出方法
        if(!timeThread.isAlive()) return;
        //否则
        //结束显示刷新线程
        timeThread.interrupt();
    }


    //重写退出方法，退出的时候暂停计时
    @Override
    public void actionC() {
        super.actionC();
        //退出任务的同时会结束计时线程
        actionB();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame jFrame=new JFrame();
            jFrame.setBounds(100,100,Main.width,Main.height);
            jFrame.setVisible(true);
            jFrame.setLayout(null);
            AbstractFunctionPanel abstractFunctionPanel=new TimePanel(Main.width,Main.height,null);
            jFrame.add(abstractFunctionPanel);
        });
    }

    //计时结束事件
    public void timeEnd(){
        //TODO 弹窗提示，播放音乐，

        //通知上级容器切换窗口
        notice(observer);
    }

    
}
