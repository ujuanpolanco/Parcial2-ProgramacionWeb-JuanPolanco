package co.edu.usco.consultas_medicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usco.consultas_medicas.model.Medico;

/**
 * Acceso a la tabla "medico". JpaRepository ya trae findAll, findById, save y
 * deleteById.
 *
 * @author Juan Manuel Polanco
 */
public interface MedicoRepository extends JpaRepository<Medico, Integer> {
}
