package src;



/*
 * 常量类，用于提供常量
 * 所有程序中用到的主要常量保存在这个接口中
 */
public interface Parameter {

    public static final int x=100;
    public static int y=100;
    public static int width=500;
    int height=400;
    String musica="resource\\bird.wav";
    String musicb="resource\\stronger.wav";

    //事实上接口中定义的量默认是也只能是public static 的量
    //如果缺省访问权限修饰符和静态修饰符的话，默认当做public static 的变量处理

}
