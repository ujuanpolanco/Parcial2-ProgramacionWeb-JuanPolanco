package co.edu.usco.consultas_medicas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Datos generales de la documentación Swagger (título, versión, contacto y
 * forma de autenticación).
 *
 * @author Juan Manuel Polanco
 */
@Configuration
public class SwaggerConfig {

	/** Nombre del esquema de seguridad que usan los controladores protegidos. */
	public static final String SESION = "sesion";

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API de Consultas Médicas")
						.version("1.0.0")
						.description("Servicios web para la gestión de consultas médicas con tres roles: "
								+ "ADMINISTRADOR, MEDICO y PACIENTE.\n\n"
								+ "**Cómo probar:** primero ejecute `POST /api/auth/login` con un usuario de prueba "
								+ "(admin, jpolanco, sramos, amayor, pablo o samuel; contraseña 1234). La sesión queda guardada "
								+ "en la cookie JSESSIONID del navegador y se envía sola en las demás peticiones.\n\n"
								+ "**Códigos de respuesta:** 200 OK (éxito), "
								+ "400 Bad Request (datos inválidos), 401 Unauthorized (no ha iniciado sesión), "
								+ "403 Forbidden (su rol no tiene permiso), 404 Not Found (no existe).")
						.contact(new Contact().name("Juan Manuel Polanco")))
				.components(new Components().addSecuritySchemes(SESION,
						new SecurityScheme()
								.type(SecurityScheme.Type.APIKEY)
								.in(SecurityScheme.In.COOKIE)
								.name("JSESSIONID")
								.description("Cookie de sesión que se obtiene al iniciar sesión en POST /api/auth/login")));
	}
}
