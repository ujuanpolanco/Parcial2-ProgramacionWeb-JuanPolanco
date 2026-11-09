package co.edu.usco.consultas_medicas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usco.consultas_medicas.model.Usuario;

/**
 * Acceso a la tabla "usuario". Spring crea las consultas a partir del nombre
 * de cada método.
 *
 * @author Juan Manuel Polanco
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	/** Busca el usuario por su nombre de usuario (para el login). */
	Optional<Usuario> findByUsername(String username);

	/** Busca el usuario asociado a un médico. */
	Optional<Usuario> findByMedicoId(Integer medicoId);

	/** Lista los usuarios de un rol, ordenados por nombre. */
	List<Usuario> findByRolOrderByNombre(String rol);
}
