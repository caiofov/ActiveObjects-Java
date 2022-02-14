
import javax.swing.plaf.synth.SynthRootPaneUI;

import activeObjects.*;

public class Main {
	public static void main(String[] args) throws InterruptedException {
        StopAndWait protocol = new StopAndWait();
        protocol.sendMessage("Oi, tudo bem?");
        System.out.println("Acabou");
        System.out.println("Mensagem recebida: " + protocol.receivedMessage());
    }

}