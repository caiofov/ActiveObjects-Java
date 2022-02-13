package activeObjects;

public class ActiveObject {
    Integer message;
    ActiveObject other;
    
    public ActiveObject(){
        this.message = 0;
    }
    public ActiveObject(ActiveObject other){
        setOther(other);
        this.message = 0;
    }
    public void setOther(ActiveObject other) {
        this.other = other;
    }
    public void ball(){
        if(this.message == 10){
            System.out.println("Parou");
        }
        else{
            this.message+=1;
            this.other.ball();
        }
        
    }
}
