package co.edu.usco.consultas_medicas.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import co.edu.usco.consultas_medicas.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Revisa cada petición antes de llegar al controlador (autenticación y
 * autorización).
 * <ul>
 * <li>Rutas /admin y /api/admin: solo ADMINISTRADOR.</li>
 * <li>Rutas /medico y /api/medico: solo MEDICO.</li>
 * <li>Rutas /paciente y /api/paciente: solo PACIENTE.</li>
 * </ul>
 * Si no hay sesión: en la API responde 401 y en las páginas manda al login.
 * Si el rol no corresponde: en la API responde 403 y en las páginas muestra la
 * vista de acceso denegado.
 *
 * @author Juan Manuel Polanco
 */
@Component
public class AutorizacionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException, ServletException {
		// getServletPath() devuelve la ruta ya decodificada y limpia (sin ";..." ni "%61"),
		// así no se puede saltar la validación escribiendo la URL de otra forma.
		String ruta = request.getServletPath();
		String rolRequerido = rolRequerido(ruta);
		if (rolRequerido == null) {
			return true; // ruta pública (login, Swagger...)
		}

		boolean esApi = ruta.startsWith("/api/");
		HttpSession sesion = request.getSession(false);
		Usuario usuario = sesion == null ? null : (Usuario) sesion.getAttribute("usuario");

		if (usuario == null) {
			if (esApi) {
				responderJson(response, HttpServletResponse.SC_UNAUTHORIZED,
						"No autenticado: inicie sesión en POST /api/auth/login.");
			} else {
				response.sendRedirect(request.getContextPath() + "/login");
			}
			return false;
		}

		if (!usuario.getRol().equals(rolRequerido)) {
			String mensaje = "Su rol (" + usuario.getRol() + ") no tiene permiso para esta acción. Se requiere el rol "
					+ rolRequerido + ".";
			if (esApi) {
				responderJson(response, HttpServletResponse.SC_FORBIDDEN, mensaje);
			} else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				request.setAttribute("mensaje", mensaje);
				request.getRequestDispatcher("/WEB-INF/views/acceso-denegado.jsp").forward(request, response);
			}
			return false;
		}
		return true;
	}

	/** Devuelve el rol que exige la ruta, o null si la ruta es pública. */
	private String rolRequerido(String ruta) {
		if (ruta.startsWith("/admin") || ruta.startsWith("/api/admin")) {
			return Usuario.ADMINISTRADOR;
		}
		if (ruta.startsWith("/medico") || ruta.startsWith("/api/medico")) {
			return Usuario.MEDICO;
		}
		if (ruta.startsWith("/paciente") || ruta.startsWith("/api/paciente")) {
			return Usuario.PACIENTE;
		}
		return null;
	}

	/** Escribe una respuesta JSON simple con el código de estado indicado. */
	private void responderJson(HttpServletResponse response, int estado, String mensaje) throws IOException {
		response.setStatus(estado);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"estado\":" + estado + ",\"mensaje\":\"" + mensaje.replace("\"", "\\\"") + "\"}");
	}
}
