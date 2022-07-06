package src;
/*
 * 观察者模式中的发布者接口，将由任务条实现
 */
public interface Publisher {

    //通知发布者某种信息
    public void notice(Observer observer);

    //添加观察者/信息接受者
    public void addObserver(Observer observer);

}
