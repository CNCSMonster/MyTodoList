import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
/*
 * @author
 * 任务类型
 */

public class Mission extends JButton implements Publisher{
    private int limitTime;  //时间上限
    private int restTime;   //剩余时间
    private Thread thread;
    //用本身的text当做把标题，
    //定义内容,content存储任务具体内容描述
    private String content="无内容";
    private MissionPane missionPane;
    

    //计算时间上限，还有倒计时


    public Mission(int width,int height){
        setSize(width,height);
        setBorder(BorderFactory.createLineBorder(Color.gray, height/10));
        addMouseListener(new SwapIfChosen());
        limitTime=60;
        restTime=limitTime;
        setText("空标题");
    }

    public Mission(int x,int y,int width,int height){
        setBounds(x,y,width,height);
        setBorder(BorderFactory.createLineBorder(Color.gray, height/10));
        addMouseListener(new SwapIfChosen());
        limitTime=60;
        restTime=limitTime;
        setText("空标题");
    }

    public Mission(int width,int height,String text,String content){
        this(width, height);
        setText(text);
        setContent(content);
    }

    public Mission(int limitTime,int x,int y,int height,int width){
        
        setBounds(x,y,width,height);
        setBorder(BorderFactory.createLineBorder(Color.gray, height/10));
        addMouseListener(new SwapIfChosen());
        limitTime=60;
        restTime=limitTime;
        setText("空标题");
    }

    public Mission(int limitTime,int x,int y,int height,int width,String title,String content){
        this(limitTime, x, y, height, width);
        setText(title);
        setContent(content);
    }


    public Mission(int limitTime,int height ,int width,String title,String content){
        this(limitTime, 0, 0, height, width);
        setText(title);
        setContent(content);
    }

    


    public Mission(){
        this(60,0,0,100,100);
    }




    public void setLimitTime(int limitTime) {
        this.limitTime = limitTime;
    }

    public int getLimitTime() {
        return limitTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }


    //开始倒计时
    public void startCountDown(){
        //如果还在进行，则保持不动
        if(thread!=null&&thread.isAlive()) return;
        thread=new Thread(()->{
            while(true){
                try {
                    thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;  //在睡眠异常处理中加入break退出循环，这样外面可以通过interrupt方法退出循环
                }
                synchronized(Mission.this){
                    setRestTime(getRestTime()-1);
                    if(getRestTime()==0) break;
                }
            }
        });
        thread.start();

    }

    //暂停倒计时
    public void pauseCounttDown(){
        if(thread==null) return;
        if(thread.isAlive()){   //如果计时线程没有停止，停止
            thread.interrupt();
        }
    }



    @Override
    protected void paintBorder(Graphics g) {
        if(isSelected())  super.paintBorder(g);
    }

    class SwapIfChosen extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            super.mouseClicked(e);
            if(missionPane!=null) notice(missionPane);
            repaint();
        }
    }

    
    public void setMissionPane(MissionPane missionPane) {
        this.missionPane = missionPane;
    }


    //通知方法，该通知方法会在点击事件中被调用，以通知滚动任务面板
    @Override
    public void notice(Observer observer) {
        // 通知
        if(observer==null) return;
        observer.update(this);
    }




    public static void main(String[] args) throws InterruptedException {
        JFrame jFrame=new JFrame();
        jFrame.setBounds(300,300,300,300);
        Mission mission=new Mission(100, 100);
        JPanel jPanel=new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(0,0,300,300);
        jPanel.add(mission);
        jFrame.add(jPanel);
        jFrame.setVisible(true);
        mission.setRestTime(10);
        mission.startCountDown();
        
    }



    
}
