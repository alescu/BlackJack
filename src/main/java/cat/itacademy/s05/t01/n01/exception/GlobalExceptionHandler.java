package cat.itacademy.s05.t01.n01.exception;

import cat.itacademy.s05.t01.n01.model.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException; // Aquesta és l'excepció de WebFlux per mètode no permès
import reactor.core.publisher.Mono; // Per retornar un Mono en un context reactiu

@ControllerAdvice
public class GlobalExceptionHandler {

    // Gestiona l'excepció MethodNotAllowedException per a WebFlux
    @ExceptionHandler(MethodNotAllowedException.class)
    public Mono<ResponseEntity<CustomError>> handleMethodNotAllowedException(MethodNotAllowedException ex) {
        String message = "El mètode HTTP " + ex.getHttpMethod() + " no està permès per a aquesta URL.";
        CustomError errorResposta = new CustomError(HttpStatus.METHOD_NOT_ALLOWED.value(), message);
        return Mono.just(new ResponseEntity<>(errorResposta, HttpStatus.METHOD_NOT_ALLOWED));
    }

    // Pots afegir més @ExceptionHandler per a altres tipus d'excepcions
    // Per exemple, per a RuntimeException genèriques:
    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<CustomError>> handleRuntimeException(RuntimeException ex) {
        String message = "Error intern del servidor: " + ex.getMessage();
        CustomError errorResposta = new CustomError(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
        return Mono.just(new ResponseEntity<>(errorResposta, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}