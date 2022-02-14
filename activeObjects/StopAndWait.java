package activeObjects;

import java.util.ArrayList;

public class StopAndWait { //protocolo de enlace de dados Stop and Wait
    SenderSW sender;
    ReceiverSW receiver;
    PhysicalLayer physicalLayer;
    
    //atenção ao definir os atributos: sender e receiver precisam ter o mesmo physicalLayer
    public StopAndWait(){
        this.setPhysicalLayer(new PhysicalLayer());
        this.setReceiver(new ReceiverSW(physicalLayer));
        this.setSender(new SenderSW(physicalLayer));
    }

    public void setPhysicalLayer(PhysicalLayer physicalLayer) {
        this.physicalLayer = physicalLayer;
    }

    public void setSender(SenderSW sender) {
        this.sender = sender;
    }

    public void setReceiver(ReceiverSW receiver) {
        this.receiver = receiver;
    }

    public void sendMessage(String message) throws InterruptedException{
        this.setSenderMessage(message);
        System.out.println("Iniciando a transmissão de dados . . . ");
        //inica as threads dos dois objetos ativos
        this.sender.start();
        this.receiver.start();
        //programa só irá continuar depois que as duas threads acabarem
        this.sender.join();
        this.receiver.join();
    }

    private void setSenderMessage(String message){
        String[] splitedMessage = message.split(" ");
        ArrayList<Frame> frames = new ArrayList<Frame>();
        int code = 0;
        for (String m : splitedMessage) {
            frames.add(new Frame(m, "data", code));
            code ++;
        }
        this.sender.fromNetworkLayer(frames);
    }

    public String receivedMessage(){
        return this.receiver.getMessage();
    }
}
