import activeObjects.*;

public class Main {
	public static void main(String[] args) {
        ActiveObject sender = new ActiveObject();
        ActiveObject receiver = new ActiveObject(sender);
        sender.setOther(receiver);
        sender.ball();
        // System.out.println("oi");
    }

}