package src;
/*
 * 观察者模式中的观察者接口，将由滚动面板实现
 */
public interface Observer {
    
    //根据接受到的发布者发布的信息作出某种行为,
    public void update(Object message);

}
