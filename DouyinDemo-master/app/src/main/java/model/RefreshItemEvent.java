package model;

//用于通知其他界面控件数据刷新
public class RefreshItemEvent {
    private int position;
    public RefreshItemEvent(int position){
        this.position=position;
    }
    public int getPosition(){
        return position;
    }
}
