
import activeObjects.*;

public class Main {
	public static void main(String[] args) throws InterruptedException {
        StopAndWait protocol = new StopAndWait();
        protocol.sendMessage("Oi, tudo bem?"); //escreva aqui a mensagem a ser enviada
        System.out.println("Mensagem recebida: " + protocol.receivedMessage());
    }
}