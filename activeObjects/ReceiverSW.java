package activeObjects;

import java.util.ArrayList;

public class ReceiverSW extends Thread{
    ArrayList<Frame> frames = new ArrayList<Frame>(); //quadros que foram recebidos
    PhysicalLayer physicalLayer;
    String receivedMessage = "";
    
    public ReceiverSW(PhysicalLayer physicalLayer){
        this.physicalLayer = physicalLayer;
    }

    public boolean fromPhysicalLayer(Frame frame){ //recebe o pacote e envia o quadro de confirmação para a camada física
        System.out.println("Receiver: Quadro recebido -> Código: "+ frame.getCode() + " | Mensagem: "+frame.getInfo());
        
        this.frames.add(frame); //adiciona o quadro recebido à arraylist de quadros
        
        Frame ackFrame = new Frame("ack", frame.getCode()); //cria o quadro de confirmação
    
        System.out.println("Receiver: Enviando quadro de confirmação -> Código: "+ ackFrame.getCode());
        
        this.physicalLayer.setFrame(ackFrame);
        
        return frame.isLast(); //retornar se é o último quadro a ser lido
    }

    @Override
    public void run(){
        while(true){
            System.out.println("Receiver: Lendo camada física");
            Frame content = this.physicalLayer.getFrame(); //recebe o conteudo da camada física
            
            if(content == null || content.getKind() == "ack"){ //se o conteúdo for nulo ou o quadro for de confirmação
                System.out.println("Receiver: Nada encontrado. Esperando..."); //deverá esperar por um novo quadro
                try {
                    Thread.sleep(1000); //o sleep evita que essa mesma ação seja executada várias vezes. Dá tempo para o sender enviar a sua mensagem
                } catch (InterruptedException e) {
                }
                continue;
            }
            
            else{ //caso contrário, irá receber o quadro
                boolean isLast = this.fromPhysicalLayer(content); //recebe o quadro e retorna se o quadro foi o último a ser recebido ou não
                
                if(isLast){ //se o quadro for o último, deverá encerrar a thread
                    System.out.println("Receiver: Último quadro lido");
                    this.generateMessage(); //tranformar os quadros de volta em string
                    break;
                }
            } 
        }
    }

    private void generateMessage(){ //transaforma os quadros de volta em string
        for(int i = 0; i < this.frames.size(); i++){
            this.receivedMessage += this.frames.get(i).getInfo() + " ";
        }
    }

    public String getMessage(){
        return this.receivedMessage;
    }
}
