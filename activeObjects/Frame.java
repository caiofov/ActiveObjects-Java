package activeObjects;

public class Frame {
    public String info;
    String kind = null;
    public boolean last = false;
    public int code;

    public Frame(String info, String kind, int code){
        this.setInfo(info);
        this.kind = kind;
        this.code = code;
    }
    public Frame(String info, String kind, int code, boolean last){
        this(info, kind, code);
        this.last = last;
    }
    public Frame(String kind, int code){
        this.kind = kind;
        this.code = code;
    }
    public void setInfo (String info){
        this.info = info;
    }
    public int getCode() {
        return code;
    }
    public String getInfo() {
        return info;
    }
    public String getKind(){
        return kind;
    }
    public Boolean isLast() {
        return last;
    }
}
