package activeObjects;

public class SenderSW extends Thread{
    Frame packet = new Frame(5, "data"); //pacote que será enviado
    int packetCount = 0;
    Boolean wait = false;
    ReceiverSW receiver;
    PhysicalLayer physicalLayer;
    
    // private BlockingDeque<Runnable> dispatchQueue = new LinkedBlockingDeque<Runnable>();
    
    public SenderSW(PhysicalLayer physicalLayer){
        this.physicalLayer = physicalLayer;
    }
    public SenderSW(ReceiverSW receiver, Frame packet){
        this.setReceiver(receiver);
        this.fromNetworkLayer(packet);
    }
    public void fromNetworkLayer(Frame packet) { //sets the packet
        this.packet = packet;
    }
    public void setReceiver(ReceiverSW receiver) {
        this.receiver = receiver;
    }

    private void toPhysicalLayer() { //envia o quadro para a camada física
        
        int content = 1;
        this.packet.setInfo(this.packet.getInfo()-1);
        this.packetCount += 1;
        
        Frame senderPacket = new Frame(content, "data"); //quadro que será enviado
        senderPacket.code = this.packetCount; //insere o código no quadro
        
        if(this.packet.getInfo() <= 0){senderPacket.last = true; } //verifica se é o último quadro a ser enviado
        
        System.out.println("Sender: Enviando...| Código: "+ senderPacket.code);
        this.physicalLayer.setContent(senderPacket); //envia para a camada física

    }

    @Override
    public void run(){
        this.toPhysicalLayer();
        while(this.packet.getInfo() > 0){
            Frame content = this.physicalLayer.getContent(); //recebe o conteudo da camada física
            if(!(content == null) && content.getKind() == "ack" && content.code == packetCount){ //se o pacote for de confirmação e for do quadro enviado por último
                //envia um novo quadro para a camada física
                System.out.println("Sender: Quadro de confirmação recebido | Código: "+ content.code);
                this.toPhysicalLayer();
                
            }
            else{ //caso contrário, deverá esperar por um quadro de confirmação
                System.out.println("Sender: Esperando...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    // private void sendPacket(){
    //     System.out.println("Enviado");
    //     this.packet -= 1;
    //     this.receiver.receivePacket();
    // }
    // public void receiveWaitSignal(){
    //     this.wait = false;
    // }
}   
