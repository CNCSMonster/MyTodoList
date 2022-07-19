package src;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;

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


    private Observer observer;
    

    public Mission(int limitTime,int x,int y,int height,int width,String title,String content){
        setBounds(x,y,width,height);
        int thickOfborder=height/10>2?height/10:4;
        setBorder(BorderFactory.createLineBorder(Color.gray, thickOfborder));
        addMouseListener(new SwapIfChosen());
        this.limitTime=limitTime;
        restTime=limitTime;
        setText(title);
        setContent(content);
    }

    public Mission(int width,int height){
        this(0,0,width,height);
    }

    public Mission(int x,int y,int width,int height){
        setBounds(x,y,width,height);
        setBorder(BorderFactory.createLineBorder(Color.gray, height/10));
        addMouseListener(new SwapIfChosen());
        limitTime=60;
        restTime=limitTime;
        setText("空标题");
    }

    public Mission(int width,int height,String title,String content){
        this(60,0,0,width, height,title,content);
    }

    public Mission(int limitTime,int x,int y,int height,int width){
        this(limitTime, x, y, height, width,"空标题","空内容");
    }

    public Mission(int limitTime,String title,String content){
        this(limitTime, 1, 1, title, content);
    }


    public Mission(int limitTime,int height ,int width,String title,String content){
        this(limitTime, 0, 0, height, width,title,content);
    }

    


    public Mission(){
        this(60,0,0,100,100);
    }




    public void setLimitTime(int limitTime) {
        this.limitTime = limitTime;
        restTime=limitTime;
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
                    Thread.sleep(1000);
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
            super.mouseClicked(e);
            if(observer!=null) notice(observer);
            repaint();
        }
    }

    

    //通知方法，该通知方法会在点击事件中被调用，以通知滚动任务面板
    @Override
    public void notice(Observer observer) {
        // 通知
        if(observer==null) return;
        observer.update(this);
    }


    //该程序中，一个任务只需要通知一个滚动任务面板，也就是只需要保有一个观察者的引用
    @Override
    public void addObserver(Observer observer) {
        this.observer=observer;
    }

    @Override
    public String toString() {
        return "任务名:"+getText()+"\n任务总时间:"+getLimitTime()+"\n任务剩余时间:"+getRestTime()+"\n任务内容:"+getContent();
    }

    //把一个String转化为Mission
    public static Mission valueOf(String string){
        String[] sa=string.split("\n");
        String missionName=sa[0].substring(4);  //下标4对应实际序号5，从第五个字符开始截取到结尾
        String limitTime=sa[1].substring(6);   //任务总时间
        String restTime=sa[2].substring(7);
        String contentString=sa[3].substring(5);
        for(int i=4;i<sa.length;i++){
            contentString+='\n'+sa[i];
        }
        int lTime=Integer.parseInt(limitTime);
        int rTime=Integer.parseInt(restTime);
        Mission out=new Mission(lTime,missionName,contentString);
        out.setRestTime(rTime);
        return out;
    }

}
