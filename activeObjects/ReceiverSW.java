package activeObjects;

public class ReceiverSW extends Thread{
    Frame packet = new Frame(0, "data");
    PhysicalLayer physicalLayer;
    
    public ReceiverSW(PhysicalLayer physicalLayer){
        this.physicalLayer = physicalLayer;
    }

    public boolean receivePacket(Frame packet){ //recebe o pacote e envia o quadro de confirmação para a camada física
        System.out.println("Receiver: Quadro recebido | Código: "+ packet.code);
        this.packet.info += packet.info;
        
        Frame ackFrame = new Frame(-111, "ack");
        ackFrame.code = packet.code; //seta o código do quadro de confirmação igual ao quadro recebido
        System.out.println("Receiver: Enviando quadro de confirmação | Código: "+ ackFrame.code);
        this.physicalLayer.setContent(ackFrame);
        
        return packet.last; //retornar se é o último quadro a ser lido
    }

    @Override
    public void run(){
        while(true){
            System.out.println("Receiver: Lendo camada física");
            Frame content = this.physicalLayer.getContent(); //recebe o conteudo da camada física
            if(content == null || content.kind == "ack"){ //se o conteúdo for nulo ou o quadro for de confirmação
                System.out.println("Receiver: Esperando..."); //deverá esperar por um novo quadro
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                continue;
            }
            else{ //caso contrário, irá receber o quadro
                Boolean isLast = this.receivePacket(content);
                if(isLast){ //se o quadro for o último, deverá encerrar a thread
                    System.out.println("Receiver: Último pacote lido");
                    break;
                }
            } 
        }
    }
}
