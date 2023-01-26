package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 * 常量类，用于提供常量
 * 所有程序中用到的主要常量保存在这个接口中
 */
public class Parameter {

    public static final int x=100;
    public static int y=100;
    public static int width=500;
    public static int height=400;
    static String musica="resource\\bird.wav";
    static String musicb="resource\\stronger.wav";

    //从一个music_setting文件中读取音乐信息
    static {
        File f=new File("music_path.txt");
        FileInputStream fin;
        try {
            fin = new FileInputStream(f);
            Scanner sfin=new Scanner(fin);
            musica=sfin.nextLine();
            sfin.close();
            try {
                fin.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }
    //事实上接口中定义的量默认是也只能是public static 的量
    //如果缺省访问权限修饰符和静态修饰符的话，默认当做public static 的变量处理

}
