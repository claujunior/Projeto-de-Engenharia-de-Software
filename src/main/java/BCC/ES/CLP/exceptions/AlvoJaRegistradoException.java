package BCC.ES.CLP.exceptions;

public class AlvoJaRegistradoException extends RuntimeException{
    
    public AlvoJaRegistradoException() {
        super("Alvo já registrado");
    }

    public AlvoJaRegistradoException(String message) {
        super(message);
    }
}
