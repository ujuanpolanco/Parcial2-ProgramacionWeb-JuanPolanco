package co.edu.usco.consultas_medicas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Registra el interceptor de autorización para todas las rutas.
 *
 * @author Juan Manuel Polanco
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private AutorizacionInterceptor autorizacionInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(autorizacionInterceptor).addPathPatterns("/**");
	}
}
