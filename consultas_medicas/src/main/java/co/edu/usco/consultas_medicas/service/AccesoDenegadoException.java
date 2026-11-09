package co.edu.usco.consultas_medicas.service;

/**
 * Se lanza cuando un usuario intenta hacer algo que no le está permitido (por
 * ejemplo, un paciente cambiando la cita de otro paciente). Se responde con
 * 403 Forbidden.
 *
 * @author Juan Manuel Polanco
 */
public class AccesoDenegadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccesoDenegadoException(String mensaje) {
		super(mensaje);
	}
}
