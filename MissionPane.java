import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
/*
 * 用来存放任务的滚动面板
 */
class MissionPane extends JScrollPane implements Observer{
    
    private int missionWidth;
    private int missionheight;
    
    List<Mission> missions=new ArrayList<>();
    private Mission curMission=null;


    JPanel jPanel=new JPanel();
    
    

    public MissionPane(int width,int height){
        super();
        missionWidth=width/7*5;
        missionheight=height/13*2;
        jPanel.setPreferredSize(new Dimension(width,height));
        jPanel.setLayout(null);
        setViewportView(jPanel);
        setBounds(0,0,width,height);

    }

    public MissionPane(int x,int y,int width,int height){
        this(width, height);
        setLocation(x,y);
    }

    public void addMission(Mission mission){
        int num=missions.size();
        mission.setBounds(missionWidth/5,missionheight/2+missionheight/2*3*num,missionWidth,missionheight);
        mission.setMissionPane(this);
        if(num>=4){
            jPanel.setPreferredSize(new Dimension(getWidth(),missionheight/2*3*(num+1)));
            missions.add(num,mission);
        }
        else{
            missions.add(num,mission);
        }
        jPanel.add(mission);
        jPanel.repaint();
        this.repaint();
    }

    @Override
    public void update(Object message) {
        if(message==null) return;
        if(!(message instanceof Mission)) return;
        //如果是再次点击已经选中的任务
        if(message==curMission){
            curMission.setSelected(false);
        }
        //如果之前没有任务被选中
        else if(curMission==null){
            curMission=(Mission)message;
            curMission.setSelected(true);
        }
        //如果发生点击的是新任务
        else{
            curMission.setSelected(false);
            curMission=(Mission)message;
            curMission.setSelected(true);
        }
    }

    





    public static void main(String[] args) throws InterruptedException {
        JFrame jFrame=new JFrame();
        jFrame.setBounds(200,200,500,500);
        jFrame.setLayout(null);
        MissionPane missionPane=new MissionPane(100,100,300, 300);
        missionPane.addMission(new Mission());
        missionPane.addMission(new Mission());
        jFrame.add(missionPane);
        jFrame.setVisible(true);
        for(int i=0;i<30;i++){
            Thread.sleep(2000);
            missionPane.addMission(new Mission());
        }
    }


}