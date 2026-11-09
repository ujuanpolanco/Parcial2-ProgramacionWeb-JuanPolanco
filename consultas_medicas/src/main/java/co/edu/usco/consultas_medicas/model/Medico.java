package co.edu.usco.consultas_medicas.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Médico de la clínica (tabla independiente "medico"). Sus datos llenan la
 * lista desplegable del formulario de consultas.
 *
 * @author Juan Manuel Polanco
 */
@Entity
@Table(name = "medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Médico registrado en la clínica")
public class Medico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador del médico", example = "1")
	private Integer id;

	@Column(nullable = false, length = 60)
	@Schema(description = "Nombre completo del médico", example = "Juan Polanco")
	private String nombre;

	@Column(nullable = false, length = 60)
	@Schema(description = "Especialidad del médico", example = "Medicina general")
	private String especialidad;
}
