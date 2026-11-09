package co.edu.usco.consultas_medicas.dto;

import co.edu.usco.consultas_medicas.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Datos públicos del usuario que inició sesión (sin la contraseña).
 *
 * @author Juan Manuel Polanco
 */
@Data
@Schema(description = "Usuario que inició sesión")
public class UsuarioResponse {

	@Schema(description = "Nombre de usuario", example = "admin")
	private String username;

	@Schema(description = "Nombre de la persona", example = "Administrador")
	private String nombre;

	@Schema(description = "Rol: ADMINISTRADOR, MEDICO o PACIENTE", example = "ADMINISTRADOR")
	private String rol;

	/** Convierte la entidad Usuario en esta respuesta. */
	public static UsuarioResponse desde(Usuario usuario) {
		UsuarioResponse respuesta = new UsuarioResponse();
		respuesta.setUsername(usuario.getUsername());
		respuesta.setNombre(usuario.getNombre());
		respuesta.setRol(usuario.getRol());
		return respuesta;
	}
}
