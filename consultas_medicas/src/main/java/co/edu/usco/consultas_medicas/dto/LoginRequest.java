package co.edu.usco.consultas_medicas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Credenciales que se envían para iniciar sesión.
 *
 * @author Juan Manuel Polanco
 */
@Data
@Schema(description = "Credenciales para iniciar sesión")
public class LoginRequest {

	@Schema(description = "Nombre de usuario", example = "admin")
	private String username;

	@Schema(description = "Contraseña", example = "1234")
	private String password;
}
