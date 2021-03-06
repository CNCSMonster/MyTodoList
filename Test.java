import java.io.*;
import java.util.ArrayList;
import java.util.List;

import src.DAO;
import src.Mission;

/*
 * 该类用来对一些代码进行测试
 * 
 */

public class Test {

    //测试DAO功能
    public static void testDAO(){
        List<Mission> missions=new ArrayList<>();
        missions.add(new Mission());
        missions.add(new Mission(100,"任务名","任务内容:\ncs\ncontent"));
        DAO.storeMissionsToday(missions);
        missions=DAO.loadMissionsToday();
        System.out.println(missions);
    }
   
    public static void main(String[] args) throws IOException {
        // System.out.println(DAO.missionToJsonStr(new Mission(11,"dd","content\ntttt")));
        DAO.addFinishedMissionToday(new Mission() );
    }
}
