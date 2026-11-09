package co.edu.usco.consultas_medicas.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Nuevo horario de una cita (lo usa el paciente para actualizarla).
 *
 * @author Juan Manuel Polanco
 */
@Data
@Schema(description = "Nuevo horario de la cita")
public class HorarioRequest {

	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	@Schema(description = "Nueva hora de inicio (HH:mm)", type = "string", example = "15:00")
	private LocalTime horaInicio;

	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	@Schema(description = "Nueva hora de finalización (HH:mm)", type = "string", example = "15:30")
	private LocalTime horaFin;
}
