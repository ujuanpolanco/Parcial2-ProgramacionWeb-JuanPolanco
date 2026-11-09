package co.edu.usco.consultas_medicas.model;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Consulta médica: asigna un consultorio y un horario a un paciente con un
 * médico.
 *
 * @author Juan Manuel Polanco
 */
@Entity
@Table(name = "consulta")
@Data
@NoArgsConstructor
@Schema(description = "Consulta médica con su consultorio y horario")
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador de la consulta", example = "1")
	private Integer id;

	@Column(name = "nombre_paciente", nullable = false, length = 40)
	@Schema(description = "Nombre del paciente (máximo 40 caracteres)", example = "Pablo Torres")
	private String nombrePaciente;

	@Column(nullable = false, length = 100)
	@Schema(description = "Motivo de la consulta (máximo 100 caracteres)", example = "Dolor de cabeza frecuente")
	private String motivo;

	@Column(name = "numero_consultorio", nullable = false)
	@Schema(description = "Número del consultorio", example = "101")
	private Integer numeroConsultorio;

	@Column(name = "hora_inicio", nullable = false)
	@JsonFormat(pattern = "HH:mm")
	@Schema(description = "Hora de inicio (HH:mm)", type = "string", example = "08:00")
	private LocalTime horaInicio;

	@Column(name = "hora_fin", nullable = false)
	@JsonFormat(pattern = "HH:mm")
	@Schema(description = "Hora de finalización (HH:mm)", type = "string", example = "08:30")
	private LocalTime horaFin;

	@ManyToOne
	@JoinColumn(name = "medico_id", nullable = false)
	@Schema(description = "Médico que atiende la consulta")
	private Medico medico;
}
