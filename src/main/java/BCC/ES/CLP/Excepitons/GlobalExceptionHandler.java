package BCC.ES.CLP.Excepitons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlvoNaoEncontradoException.class)
    public ResponseEntity<String> handleAlvoNaoEncontrado(AlvoNaoEncontradoException ex) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
    }
    
    @ExceptionHandler(AlvoJaRegistradoException.class)
    public ResponseEntity<String> handleAlvoJaRegistrado(AlvoJaRegistradoException ex) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
    }
}
