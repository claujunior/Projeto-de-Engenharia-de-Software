package BCC.ES.CLP.exceptions;

public class UsuarioJaExistente extends RuntimeException{
    public UsuarioJaExistente(String message){
        super(message);
    }
}
