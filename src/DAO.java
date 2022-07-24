package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public interface DAO {
    //待完成的任务的路径
    public static final String missionFolderPath="missions";
    //已经完成的任务的存放路径
    public static final String finishedMissionFolderPath="finishedMissions";
    //月计划路径
    public static final String monthTarget="target";

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
    
    //追加写入已经完成的任务,每次完成一个任务都更新这一项内容
    public static void addFinishedMissionToday(Mission mission){
        //判断对应文件夹是否存在，如果不存在，则创建一个
        if(!Files.isDirectory(Path.of(finishedMissionFolderPath))){
            try {
                Files.createDirectories(Path.of(finishedMissionFolderPath));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("创建文件夹失败");
                e.printStackTrace();
            }
        }
        try{
            String fileName=finishedMissionFolderPath+"/"+getDateString()+".txt";
            File file=new File(fileName);
            if(!file.isFile()) file.createNewFile();
            //先读取文件中的全部字符串
            List<String> strArr=Files.readAllLines(file.toPath());
            //如果是第一次写入文件
            if(strArr.size()<2){
                BufferedWriter bfw=Files.newBufferedWriter(file.toPath());
                int limitTime=mission.getLimitTime();
                String time=limitTime/3600+":"+limitTime/60%60+":"+limitTime%60;
                bfw.write("total num:1\ntotal time:"+time+"\n1.\n");
                System.out.println("total num:1\ntotal time:"+time+"\n1.\n");
                bfw.write(missionToJsonStr(mission));
                bfw.close();
                return;
            }
            
            //第一行 total num:
            //第二行 total time:
            //后面开始放任务，
            int num=Integer.parseInt(strArr.get(0).substring(10));
            String time=strArr.get(1).substring(11);
            String[] timeArr=time.split(":");
            int hour=Integer.parseInt(timeArr[0]);
            int min=Integer.parseInt(timeArr[1]);
            int second=Integer.parseInt(timeArr[2]);
            //更新完成任务总数
            num+=1;
            //通过任务更新完成任务总时间
            int newTime=hour*3600+min*60+second+mission.getLimitTime();
            hour=newTime/3600;
            min=newTime/60%60;
            second=newTime%60;
            time="total time:"+hour+":"+min+":"+second;
            strArr.set(0, "total num:"+num);
            strArr.set(1, time);
            BufferedWriter bfw=Files.newBufferedWriter(file.toPath());
            for(String string:strArr){
                bfw.write(string+"\n");
            }
            bfw.write(num+".\n");
            bfw.write(missionToJsonStr(mission));
            bfw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("input wrong");
            e.printStackTrace();
        }
    }

    

    //转化成json格式的字符串,用来写入已经完成的任务
    public static String missionToJsonStr(Mission mission){
        //TODO 把内容分割成一个字符串列表转成字符串
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("{\n");
        //先加入标题
        stringBuilder.append("\"title\": \""+mission.getText()+"\",\n");
        stringBuilder.append("\"limitTime\": "+mission.getRestTime()+",\n");
        stringBuilder.append("\"content\":\n");
        stringBuilder.append("\t[\n");
        String[] sa=mission.getContent().split("\n");
        for(String string:sa){
            stringBuilder.append("\t\""+string+"\"");
            if(string!=sa[sa.length-1]) stringBuilder.append(",\n");
            else stringBuilder.append("\n");
        }
        stringBuilder.append("\t]\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    // //通过json格式的字符串转化为Mission类型
    // public static Mission jsonStrToMission(String jsonString){
    //     //首先,从里面读取标题
    //     //然后从里面读取时间
    //     //再然后从里面读取任务内容
    //     int index=jsonString.indexOf("\"title\":");
    //     int end=jsonString.indexOf("\n",index);
    //     String title=jsonString.substring(index+8, end).strip();

    //     //读取到标题后读取任务上限时间
    //     index=jsonString.indexOf("\"limitTime\":");
    //     end=jsonString.indexOf("\n",index);
        

    //     index=jsonString.indexOf("[");
    //     end=jsonString.indexOf("]");

    //     return null;
    // }


}
