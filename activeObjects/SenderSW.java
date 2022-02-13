package activeObjects;

public class SenderSW extends Thread{
    Frame frame = new Frame(5, "data"); //pacote que será enviado
    int frameCount = 0;
    Boolean wait = false;
    ReceiverSW receiver;
    PhysicalLayer physicalLayer;
    
    // private BlockingDeque<Runnable> dispatchQueue = new LinkedBlockingDeque<Runnable>();
    
    public SenderSW(PhysicalLayer physicalLayer){
        this.physicalLayer = physicalLayer;
    }
    public SenderSW(ReceiverSW receiver, Frame frame){
        this.setReceiver(receiver);
        this.fromNetworkLayer(frame);
    }
    public void fromNetworkLayer(Frame frame) { //sets the frame
        this.frame = frame;
    }
    public void setReceiver(ReceiverSW receiver) {
        this.receiver = receiver;
    }

    private void toPhysicalLayer() { //envia o quadro para a camada física
        
        int content = 1;
        this.frame.setInfo(this.frame.getInfo()-1);
        this.frameCount += 1;
        
        Frame senderframe = new Frame(content, "data"); //quadro que será enviado
        senderframe.code = this.frameCount; //insere o código no quadro
        
        if(this.frame.getInfo() <= 0){senderframe.last = true; } //verifica se é o último quadro a ser enviado
        
        System.out.println("Sender: Enviando...| Código: "+ senderframe.code);
        this.physicalLayer.setFrame(senderframe); //envia para a camada física

    }

    @Override
    public void run(){
        this.toPhysicalLayer();
        while(this.frame.getInfo() > 0){
            Frame frame = this.physicalLayer.getFrame(); //recebe o conteudo da camada física
            if(!(frame == null) && frame.getKind() == "ack" && frame.code == frameCount){ //se o pacote for de confirmação e for do quadro enviado por último
                //envia um novo quadro para a camada física
                System.out.println("Sender: Quadro de confirmação recebido | Código: "+ frame.code);
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
}   
