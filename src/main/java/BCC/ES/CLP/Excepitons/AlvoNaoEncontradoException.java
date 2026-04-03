package BCC.ES.CLP.Excepitons;

public class AlvoNaoEncontradoException extends RuntimeException {

    public AlvoNaoEncontradoException() {
        super("Alvo não encontrado");
    }

    public AlvoNaoEncontradoException(String message) {
        super(message);
    }
}