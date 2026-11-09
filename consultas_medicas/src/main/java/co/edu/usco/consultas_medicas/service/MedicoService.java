package co.edu.usco.consultas_medicas.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usco.consultas_medicas.dto.MedicoRequest;
import co.edu.usco.consultas_medicas.model.Medico;
import co.edu.usco.consultas_medicas.model.Usuario;
import co.edu.usco.consultas_medicas.repository.ConsultaRepository;
import co.edu.usco.consultas_medicas.repository.MedicoRepository;
import co.edu.usco.consultas_medicas.repository.UsuarioRepository;

/**
 * Lógica de médicos (crear, editar, eliminar y listar). Cada médico tiene un
 * usuario con rol MEDICO para poder iniciar sesión.
 *
 * @author Juan Manuel Polanco
 */
@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ConsultaRepository consultaRepository;

	/** @return todos los médicos, ordenados por id */
	public List<Medico> listar() {
		return medicoRepository.findAll(Sort.by("id"));
	}

	/**
	 * @param id id del médico
	 * @return el médico si existe
	 */
	public Optional<Medico> buscar(Integer id) {
		return medicoRepository.findById(id);
	}

	/**
	 * @param medicoId id del médico
	 * @return el usuario con el que inicia sesión ese médico
	 */
	public Optional<Usuario> buscarUsuario(Integer medicoId) {
		return usuarioRepository.findByMedicoId(medicoId);
	}

	/**
	 * Crea un médico y su usuario.
	 *
	 * @param datos datos del formulario
	 * @return el médico creado
	 * @throws IllegalArgumentException si algún dato no es válido o el usuario ya existe
	 */
	@Transactional
	public Medico crear(MedicoRequest datos) {
		validar(datos, true);
		if (usuarioRepository.findByUsername(datos.getUsername().trim()).isPresent()) {
			throw new IllegalArgumentException("El usuario '" + datos.getUsername().trim() + "' ya existe.");
		}

		Medico medico = new Medico(null, datos.getNombre().trim(), datos.getEspecialidad().trim());
		medico = medicoRepository.save(medico);

		Usuario usuario = new Usuario();
		usuario.setUsername(datos.getUsername().trim());
		usuario.setPassword(datos.getPassword());
		usuario.setNombre(medico.getNombre());
		usuario.setRol(Usuario.MEDICO);
		usuario.setMedico(medico);
		usuarioRepository.save(usuario);

		return medico;
	}

	/**
	 * Actualiza un médico y su usuario. Si la contraseña viene vacía se deja la
	 * que tenía.
	 *
	 * @param id    id del médico
	 * @param datos datos nuevos
	 * @return el médico actualizado
	 * @throws NoSuchElementException   si el médico no existe
	 * @throws IllegalArgumentException si algún dato no es válido o el usuario ya lo usa otro
	 */
	@Transactional
	public Medico actualizar(Integer id, MedicoRequest datos) {
		Medico medico = medicoRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("No existe el médico con id " + id + "."));
		validar(datos, false);

		Usuario usuario = usuarioRepository.findByMedicoId(id).orElse(new Usuario());
		Optional<Usuario> otro = usuarioRepository.findByUsername(datos.getUsername().trim());
		if (otro.isPresent() && !otro.get().getId().equals(usuario.getId())) {
			throw new IllegalArgumentException("El usuario '" + datos.getUsername().trim() + "' ya existe.");
		}
		if (usuario.getId() == null && vacio(datos.getPassword())) {
			throw new IllegalArgumentException("La contraseña es obligatoria.");
		}

		medico.setNombre(datos.getNombre().trim());
		medico.setEspecialidad(datos.getEspecialidad().trim());
		medicoRepository.save(medico);

		usuario.setUsername(datos.getUsername().trim());
		if (!vacio(datos.getPassword())) {
			usuario.setPassword(datos.getPassword());
		}
		usuario.setNombre(medico.getNombre());
		usuario.setRol(Usuario.MEDICO);
		usuario.setMedico(medico);
		usuarioRepository.save(usuario);

		return medico;
	}

	/**
	 * Elimina un médico y su usuario. No se permite si tiene consultas asignadas.
	 *
	 * @param id id del médico
	 * @throws NoSuchElementException si el médico no existe
	 * @throws IllegalStateException  si el médico tiene consultas asignadas
	 */
	@Transactional
	public void eliminar(Integer id) {
		if (!medicoRepository.existsById(id)) {
			throw new NoSuchElementException("No existe el médico con id " + id + ".");
		}
		if (consultaRepository.existsByMedicoId(id)) {
			throw new IllegalStateException("No se puede eliminar: el médico tiene consultas asignadas.");
		}
		usuarioRepository.findByMedicoId(id).ifPresent(usuarioRepository::delete);
		medicoRepository.deleteById(id);
	}

	/** Revisa los campos obligatorios y sus tamaños. */
	private void validar(MedicoRequest datos, boolean esNuevo) {
		if (vacio(datos.getNombre()) || datos.getNombre().trim().length() > 60) {
			throw new IllegalArgumentException("El nombre es obligatorio y no debe superar los 60 caracteres.");
		}
		if (vacio(datos.getEspecialidad()) || datos.getEspecialidad().trim().length() > 60) {
			throw new IllegalArgumentException("La especialidad es obligatoria y no debe superar los 60 caracteres.");
		}
		if (vacio(datos.getUsername()) || datos.getUsername().trim().length() > 30) {
			throw new IllegalArgumentException("El usuario es obligatorio y no debe superar los 30 caracteres.");
		}
		if (esNuevo && vacio(datos.getPassword())) {
			throw new IllegalArgumentException("La contraseña es obligatoria.");
		}
		if (!vacio(datos.getPassword()) && datos.getPassword().length() > 60) {
			throw new IllegalArgumentException("La contraseña no debe superar los 60 caracteres.");
		}
	}

	private boolean vacio(String texto) {
		return texto == null || texto.trim().isEmpty();
	}
}
