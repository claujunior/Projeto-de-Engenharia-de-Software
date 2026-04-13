package BCC.ES.CLP.excepitons;

public class PortasFechadas extends RuntimeException{
    public PortasFechadas() {
        super("Nenhuma porta aberta nesse Ip");
    }

    public PortasFechadas(String message) {
        super(message);
    }
}
