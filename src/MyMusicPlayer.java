package src;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/*

* 一个用来播放音乐的工具类,同一时间只能支持播放一种音乐

* 

* 

* 

*/

public class MyMusicPlayer {
    
    private static Clip clip;

    

   
    // 设置要播放的音乐文件，更新使用的数据行,里面是要播放的音频文件的名字
    public static void setMusic(String musicFileName) {
        if (!Files.isReadable(Path.of(musicFileName))) {
            System.out.println("no such music");
            return;
        }
        File file = new File(musicFileName);
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
            clip=AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            
            e.printStackTrace();
        }
    }

    // 开启一个线程播放音乐
    public static void play() {
        if(clip==null) return;
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // 结束播放音乐线程
    public static void stop() {
        clip.stop();
    }


    // 播放音乐
    static void playMusic(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }





    public static void main(String[] args)
            throws LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException {
        // // new MyMusicPlayer().play();
        // setMusic(Parameter.musica);
        // play();
        // // playMusic(Parameter.musica);
        // // Thread.sleep(20000);
        // // stop();
        // Scanner in=new Scanner(System.in);
        // while(true){
        //     String s=in.nextLine();
        //     if(s.equals("end")){
        //         stop();
        //         break;
        //     }
        // }

        // System.out.println("stop");
        // String s=in.nextLine();
    }

}