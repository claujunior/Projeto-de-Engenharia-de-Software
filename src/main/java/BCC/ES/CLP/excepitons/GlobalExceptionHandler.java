package BCC.ES.CLP.excepitons;

import org.springframework.dao.DataIntegrityViolationException;
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
            .status(HttpStatus.CONFLICT)
            .body(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body("Conflito/violação de integridade no banco (duplicado ou referência inválida).");
    }

    @ExceptionHandler(ScanOrquestracaoException.class)
    public ResponseEntity<String> handleScanOrquestracao(ScanOrquestracaoException ex) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ex.getMessage());
    }
}
