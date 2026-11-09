package co.edu.usco.consultas_medicas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usco.consultas_medicas.model.Usuario;
import co.edu.usco.consultas_medicas.repository.UsuarioRepository;

/**
 * Lógica de usuarios: inicio de sesión y consulta de pacientes.
 *
 * @author Juan Manuel Polanco
 */
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 * Valida las credenciales de un usuario.
	 *
	 * @param username nombre de usuario
	 * @param password contraseña
	 * @return el usuario si las credenciales son correctas, o vacío si no
	 */
	public Optional<Usuario> login(String username, String password) {
		if (username == null || password == null) {
			return Optional.empty();
		}
		return usuarioRepository.findByUsername(username.trim())
				.filter(usuario -> usuario.getPassword().equals(password));
	}

	/** @return los usuarios con rol PACIENTE, ordenados por nombre */
	public List<Usuario> listarPacientes() {
		return usuarioRepository.findByRolOrderByNombre(Usuario.PACIENTE);
	}
}
