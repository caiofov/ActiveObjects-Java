package activeObjects;

public class Frame {
    public int info;
    String kind = null;
    public Boolean last = false;
    public int code;

    public Frame(int info, String kind){
        this.setInfo(info);
        this.kind = kind;
    }
    public void setInfo (int info){
        this.info = info;
    }
    public int getInfo() {
        return info;
    }
    public String getKind(){
        return kind;
    }
}
