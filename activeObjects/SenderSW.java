package activeObjects;

import java.util.ArrayList;

public class SenderSW extends Thread{
    ArrayList<Frame> frames = new ArrayList<Frame>(); //quadros que serão enviados
    int lastFrameCode;
    Boolean wait = false;
    ReceiverSW receiver;
    PhysicalLayer physicalLayer;
    
     
    public SenderSW(PhysicalLayer physicalLayer){
        this.physicalLayer = physicalLayer;
    }
    public SenderSW(ReceiverSW receiver, ArrayList<Frame> frames){
        this.setReceiver(receiver);
        this.fromNetworkLayer(frames);
    }
    public void fromNetworkLayer(ArrayList<Frame> frames) { //sets the frame
        this.frames = frames;
    }
    public void setReceiver(ReceiverSW receiver) {
        this.receiver = receiver;
    }

    private void toPhysicalLayer() { //envia o quadro para a camada física
        
        Frame buffer = this.frames.get(0); //quadro que será enviado
        this.frames.remove(buffer);
        this.lastFrameCode = buffer.getCode(); //insere o código no quadro
        
        if(this.frames.size() <= 0){buffer.last = true; } //verifica se é o último quadro a ser enviado
        
        System.out.println("Sender: Enviando... -> Código: "+ buffer.getCode() + " | Mensagem: "+ buffer.getInfo());
        this.physicalLayer.setFrame(buffer); //envia para a camada física

    }

    @Override
    public void run(){
        this.toPhysicalLayer();
        while(this.frames.size() > 0){
            Frame frame = this.physicalLayer.getFrame(); //recebe o conteudo da camada física
            if(!(frame == null) && frame.getKind() == "ack" && frame.getCode() == lastFrameCode){ //se o pacote for de confirmação e for do quadro enviado por último
                //envia um novo quadro para a camada física
                System.out.println("Sender: Quadro de confirmação recebido -> Código: "+ frame.code);
                this.toPhysicalLayer();
            }
            else{ //caso contrário, deverá esperar por um quadro de confirmação
                System.out.println("Sender: Esperando...");
                try {
                    Thread.sleep(1000); //sleep para não ocorrer de insistir várias vezes em uma mesma situação -> evita a repetição de ações
                } catch (InterruptedException e) {
                }
            }
        }
    }
}   
