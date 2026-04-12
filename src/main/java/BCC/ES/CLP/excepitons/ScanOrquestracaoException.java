package BCC.ES.CLP.excepitons;

public class ScanOrquestracaoException extends RuntimeException {

    public ScanOrquestracaoException() {
        super("Falha crítica ao executar o scan");
    }

    public ScanOrquestracaoException(String message) {
        super(message);
    }

    public ScanOrquestracaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
