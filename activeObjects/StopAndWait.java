package activeObjects;

public class StopAndWait { //protocolo de enlace de dados Stop and Wait
    SenderSW sender;
    ReceiverSW receiver;
    PhysicalLayer physicalLayer;
    //atenção ao definir os atributos: sender e receiver precisam ter o mesmo physicalLayer
    public StopAndWait(){
        setPhysicalLayer(new PhysicalLayer());
        setReceiver(new ReceiverSW(physicalLayer));
        setSender(new SenderSW(physicalLayer));
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
    public void sendMessage() throws InterruptedException{
        this.sender.start();
        this.receiver.start();
        //programa só irá continuar depois que as duas threads acabarem
        this.sender.join();
        this.receiver.join();
    }
}
