package co.edu.usco.consultas_medicas.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.consultas_medicas.dto.LoginRequest;
import co.edu.usco.consultas_medicas.dto.MensajeResponse;
import co.edu.usco.consultas_medicas.dto.UsuarioResponse;
import co.edu.usco.consultas_medicas.model.Usuario;
import co.edu.usco.consultas_medicas.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

/**
 * Servicios de autenticación: iniciar sesión, cerrar sesión y ver la sesión
 * actual. Son públicos (no exigen rol).
 *
 * @author Juan Manuel Polanco
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "1. Autenticación", description = "Inicio y cierre de sesión. Inicie sesión aquí antes de probar los demás servicios.")
public class AuthRestController {

	@Autowired
	private UsuarioService usuarioService;

	@Operation(summary = "Iniciar sesión", description = "Valida usuario y contraseña. Si son correctos, guarda el usuario "
			+ "en la sesión (cookie JSESSIONID) y devuelve sus datos y su rol. Usuarios de prueba: admin, jpolanco, sramos, "
			+ "amayor, pablo, samuel (contraseña 1234).")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: sesión iniciada",
					content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: usuario o contraseña incorrectos",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden: no aplica, es un servicio público (no exige rol)",
					content = @Content) })
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest credenciales, HttpSession sesion) {
		Optional<Usuario> usuario = usuarioService.login(credenciales.getUsername(), credenciales.getPassword());
		if (usuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new MensajeResponse(401, "Usuario o contraseña incorrectos."));
		}
		sesion.setAttribute("usuario", usuario.get());
		return ResponseEntity.ok(UsuarioResponse.desde(usuario.get()));
	}

	@Operation(summary = "Cerrar sesión", description = "Elimina la sesión del usuario.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: sesión cerrada",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: no aplica, es un servicio público (no exige sesión)",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden: no aplica, es un servicio público (no exige rol)",
					content = @Content) })
	@PostMapping("/logout")
	public ResponseEntity<MensajeResponse> logout(HttpSession sesion) {
		sesion.invalidate();
		return ResponseEntity.ok(new MensajeResponse(200, "Sesión cerrada."));
	}

	@Operation(summary = "Ver la sesión actual", description = "Devuelve el usuario que tiene la sesión iniciada.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: hay una sesión iniciada",
					content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: no hay sesión iniciada",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden: no aplica, es un servicio público (no exige rol)",
					content = @Content) })
	@GetMapping("/sesion")
	public ResponseEntity<?> sesion(HttpSession sesion) {
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		if (usuario == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new MensajeResponse(401, "No hay una sesión iniciada."));
		}
		return ResponseEntity.ok(UsuarioResponse.desde(usuario));
	}
}
