package co.edu.usco.consultas_medicas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Datos del formulario para crear o editar un médico. Incluye el usuario y la
 * contraseña con los que el médico inicia sesión.
 *
 * @author Juan Manuel Polanco
 */
@Data
@Schema(description = "Datos para crear o editar un médico y su usuario")
public class MedicoRequest {

	@Schema(description = "Nombre completo del médico (máximo 60 caracteres)", example = "María Ramírez")
	private String nombre;

	@Schema(description = "Especialidad (máximo 60 caracteres)", example = "Dermatología")
	private String especialidad;

	@Schema(description = "Usuario con el que el médico inicia sesión", example = "mramirez")
	private String username;

	@Schema(description = "Contraseña. Al editar, si se deja vacía se conserva la actual", example = "1234")
	private String password;
}
