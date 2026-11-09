package co.edu.usco.consultas_medicas.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Datos del formulario para registrar (asignar) un consultorio.
 *
 * @author Juan Manuel Polanco
 */
@Data
@Schema(description = "Datos para asignar un consultorio a un paciente")
public class ConsultaRequest {

	@Schema(description = "Nombre del paciente: alfanumérico, máximo 40 caracteres", example = "Pablo Torres")
	private String nombrePaciente;

	@Schema(description = "Motivo de la consulta: máximo 100 caracteres", example = "Control general")
	private String motivo;

	@Schema(description = "Número del consultorio", example = "103")
	private Integer numeroConsultorio;

	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	@Schema(description = "Hora de inicio (HH:mm)", type = "string", example = "14:00")
	private LocalTime horaInicio;

	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	@Schema(description = "Hora de finalización (HH:mm), posterior a la de inicio", type = "string", example = "14:30")
	private LocalTime horaFin;

	@Schema(description = "Id del médico (de la tabla medico)", example = "1")
	private Integer medicoId;
}
