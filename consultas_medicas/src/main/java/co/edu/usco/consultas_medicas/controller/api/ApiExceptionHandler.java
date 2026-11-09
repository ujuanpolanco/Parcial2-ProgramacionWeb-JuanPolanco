package co.edu.usco.consultas_medicas.controller.api;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.edu.usco.consultas_medicas.dto.MensajeResponse;
import co.edu.usco.consultas_medicas.service.AccesoDenegadoException;

/**
 * Convierte las excepciones de los servicios en respuestas JSON con el código
 * HTTP adecuado. Solo aplica a los controladores REST.
 *
 * @author Juan Manuel Polanco
 */
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<MensajeResponse> datosInvalidos(IllegalArgumentException e) {
		return respuesta(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<MensajeResponse> jsonInvalido(HttpMessageNotReadableException e) {
		return respuesta(HttpStatus.BAD_REQUEST, "El JSON no es válido. Las horas deben ir en formato HH:mm.");
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<MensajeResponse> noEncontrado(NoSuchElementException e) {
		return respuesta(HttpStatus.NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(AccesoDenegadoException.class)
	public ResponseEntity<MensajeResponse> accesoDenegado(AccesoDenegadoException e) {
		return respuesta(HttpStatus.FORBIDDEN, e.getMessage());
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<MensajeResponse> conflicto(IllegalStateException e) {
		return respuesta(HttpStatus.CONFLICT, e.getMessage());
	}

	private ResponseEntity<MensajeResponse> respuesta(HttpStatus estado, String mensaje) {
		return ResponseEntity.status(estado).body(new MensajeResponse(estado.value(), mensaje));
	}
}
