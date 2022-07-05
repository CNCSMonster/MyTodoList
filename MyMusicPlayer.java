import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.security.auth.Subject;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.UIDefaults.ProxyLazyValue;

/*
 * 一个用来播放音乐的工具类,同一时间只能支持播放一种音乐
 * 
 * 
 * 
 */

public class MyMusicPlayer {

    //音频播放用的源数据行

    //音频播放用的字节数组
    private static byte[] bytes;

    //音频字节数组的信息格式
    private static AudioFormat af;

    //播放时每过一段时间写入音频的信息长度


    //总共字节数组需要分成的部分数


    
    public MyMusicPlayer(){

    }

    /*
     * ps：该方法来自于复制粘贴
     */
    public void playa() throws LineUnavailableException, UnsupportedAudioFileException, IOException{
        File file = new File("resource\\闹钟铃声.wav");
        //2、定义一个AudioInputStream用于接收输入的音频数据
        AudioInputStream am;
        //3、使用AudioSystem来获取音频的音频输入流(处理（抛出）异常)
        am = AudioSystem.getAudioInputStream(file);
        //4、使用AudioFormat来获取AudioInputStream的格式
        AudioFormat af = am.getFormat();
        //5、一个源数据行
        SourceDataLine sd ;
        //6、获取受数据行支持的音频格式DataLine.info
        //DataLine.Info dl = new DataLine.Info(SourceDataLine.class, af);
        //7、获取与上面类型相匹配的行 写到源数据行里 二选一
        sd = AudioSystem.getSourceDataLine(af);//便捷写法
        //sd = (SourceDataLine) AudioSystem.getLine(dl);
        //8、打开具有指定格式的行，这样可以使行获得资源并进行操作
        sd.open();
        //9、允许某个数据行执行数据i/o
        sd.start();
        //10、写数据
        int sumByteRead = 0; //读取的总字节
        byte[] b=am.readAllBytes();
        // byte [] b = new byte[320];//设置字节数组大小
        //11、从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
        while (true) {//-1代表没有 不等于-1时就无限读取
            if(sumByteRead >= 0 ) {//13、读取了之后将数据写入混频器,开始播放
                sd.write(b, 0, b.length);
            }
            if(sumByteRead>0) break;
        }
        //关闭
        sd.drain();
        sd.close();
    }


    //设置要播放的音乐文件，更新使用的数据行,里面是要播放的音频文件的名字
    public static void setMusic(String musicFileName){
        if(!Files.isReadable(Path.of(musicFileName))) return;
        File file=new File(musicFileName);
        AudioInputStream audioInputStream=null;
        try {
            audioInputStream=AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        af=audioInputStream.getFormat();
        try {
            bytes=audioInputStream.readAllBytes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //TODO 完成用于循环播放的每次写入字节数量，与总共要写入字节数量

    }


    //播放音乐
    public static void play(){
        

    }

    //播放音乐






    public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        // new MyMusicPlayer().play();
        setMusic("ggb");
    }

}
