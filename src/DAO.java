package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

public interface DAO {
    public static final String missionFolderPath="missions";
    
    private static String getDateString(){
        Date date=new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("dd_MM_yyyy");
        return formatter.format(date);
    }

    
    //从文件中加载今日任务
    public static List<Mission> loadMissionsToday(){
        List<Mission> out=new ArrayList<>();
        try {
            List<String> strings=Files.readAllLines(Path.of(missionFolderPath+"/"+getDateString()+".txt"));
            String curMission=null;
            for(int i=0;i<strings.size();i++){
                String string=strings.get(i);
                if(string.startsWith("任务名:")){
                    if(curMission!=null) out.add(Mission.valueOf(curMission));
                    curMission=string;
                }
                else curMission+="\n"+string;
            }
            try {
                out.add(Mission.valueOf(curMission));
            } catch (Exception e) {
                System.out.println("从文件中读取的最后一个人任务信息格式错误");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("文件读取错误");
            e.printStackTrace();
        }
        return out;
    }

    //往今日对应的文件中保存今日任务
    public static void storeMissionsToday(List<Mission> missions){
        //判断对应文件夹是否存在，如果不存在，则创建一个
        if(!Files.isDirectory(Path.of(missionFolderPath))){
            try {
                Files.createDirectories(Path.of(missionFolderPath));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("创建文件夹失败");
                e.printStackTrace();
            }
        }
        try{
            Path path=Paths.get(missionFolderPath+"/"+getDateString()+".txt");
            BufferedWriter bfw=Files.newBufferedWriter(path);
            for(Mission mission:missions){
                if(mission!=missions.get(missions.size()-1))
                    bfw.write(mission.toString()+"\n");
                else bfw.write(mission.toString());
            }
            bfw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    



}
