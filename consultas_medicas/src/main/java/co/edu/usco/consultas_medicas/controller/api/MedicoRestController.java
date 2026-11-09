package co.edu.usco.consultas_medicas.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.consultas_medicas.config.SwaggerConfig;
import co.edu.usco.consultas_medicas.dto.MensajeResponse;
import co.edu.usco.consultas_medicas.model.Consulta;
import co.edu.usco.consultas_medicas.model.Usuario;
import co.edu.usco.consultas_medicas.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

/**
 * Servicio del MEDICO: solo puede consultar sus propias consultas, sin
 * modificar nada.
 *
 * @author Juan Manuel Polanco
 */
@RestController
@RequestMapping("/api/medico/consultas")
@Tag(name = "4. Médico", description = "Consulta de las citas asignadas al médico. Requiere rol MEDICO. Solo lectura.")
@SecurityRequirement(name = SwaggerConfig.SESION)
public class MedicoRestController {

	@Autowired
	private ConsultaService consultaService;

	@Operation(summary = "Ver mis consultas", description = "Devuelve solo las consultas asignadas al médico que inició sesión "
			+ "(no muestra las de otros médicos).")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: consultas del médico",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Consulta.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesión",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden: su rol no es MEDICO",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))) })
	@GetMapping
	public List<Consulta> misConsultas(HttpSession sesion) {
		Usuario medico = (Usuario) sesion.getAttribute("usuario");
		return consultaService.listarPorMedico(medico.getMedico().getId());
	}
}
