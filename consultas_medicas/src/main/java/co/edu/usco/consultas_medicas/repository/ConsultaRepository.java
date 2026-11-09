package co.edu.usco.consultas_medicas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usco.consultas_medicas.model.Consulta;

/**
 * Acceso a la tabla "consulta".
 *
 * @author Juan Manuel Polanco
 */
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

	/** Consultas de un médico, ordenadas por hora de inicio. */
	List<Consulta> findByMedicoIdOrderByHoraInicio(Integer medicoId);

	/** Consultas de un paciente (sin importar mayúsculas), ordenadas por hora. */
	List<Consulta> findByNombrePacienteIgnoreCaseOrderByHoraInicio(String nombrePaciente);

	/** Indica si un médico tiene consultas asignadas. */
	boolean existsByMedicoId(Integer medicoId);
}
