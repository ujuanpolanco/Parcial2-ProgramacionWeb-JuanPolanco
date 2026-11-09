package co.edu.usco.consultas_medicas.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.usco.consultas_medicas.model.Usuario;
import co.edu.usco.consultas_medicas.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

/**
 * Inicio y cierre de sesión con el formulario personalizado (login.jsp).
 *
 * @author Juan Manuel Polanco
 */
@Controller
public class LoginController {

	@Autowired
	private UsuarioService usuarioService;

	/** Si ya hay sesión, va al inicio del rol; si no, al login. */
	@GetMapping("/")
	public String inicio(HttpSession sesion) {
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		return usuario == null ? "redirect:/login" : "redirect:" + paginaInicio(usuario);
	}

	@GetMapping("/login")
	public String formularioLogin(HttpSession sesion) {
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		return usuario == null ? "login" : "redirect:" + paginaInicio(usuario);
	}

	/** Valida las credenciales, guarda el usuario en la sesión y lo lleva a su vista. */
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, HttpSession sesion,
			Model model) {
		Optional<Usuario> usuario = usuarioService.login(username, password);
		if (usuario.isEmpty()) {
			model.addAttribute("error", "Usuario o contraseña incorrectos.");
			model.addAttribute("username", username);
			return "login";
		}
		sesion.setAttribute("usuario", usuario.get());
		return "redirect:" + paginaInicio(usuario.get());
	}

	@GetMapping("/logout")
	public String logout(HttpSession sesion) {
		sesion.invalidate();
		return "redirect:/login?salio=true";
	}

	/** Página principal según el rol del usuario. */
	private String paginaInicio(Usuario usuario) {
		switch (usuario.getRol()) {
		case Usuario.ADMINISTRADOR:
			return "/admin/consultas";
		case Usuario.MEDICO:
			return "/medico/consultas";
		default:
			return "/paciente/citas";
		}
	}
}
