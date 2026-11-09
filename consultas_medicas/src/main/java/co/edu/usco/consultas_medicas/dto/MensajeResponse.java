package co.edu.usco.consultas_medicas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Respuesta simple con un código de estado y un mensaje (se usa en errores y
 * confirmaciones).
 *
 * @author Juan Manuel Polanco
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta con código de estado y mensaje")
public class MensajeResponse {

	@Schema(description = "Código de estado HTTP", example = "403")
	private int estado;

	@Schema(description = "Mensaje explicativo", example = "Su rol (MEDICO) no tiene permiso para esta acción. Se requiere el rol ADMINISTRADOR.")
	private String mensaje;
}
