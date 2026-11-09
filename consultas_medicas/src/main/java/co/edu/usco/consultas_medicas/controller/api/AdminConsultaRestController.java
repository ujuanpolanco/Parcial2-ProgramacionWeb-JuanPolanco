package co.edu.usco.consultas_medicas.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.consultas_medicas.config.SwaggerConfig;
import co.edu.usco.consultas_medicas.dto.ConsultaRequest;
import co.edu.usco.consultas_medicas.dto.MensajeResponse;
import co.edu.usco.consultas_medicas.model.Consulta;
import co.edu.usco.consultas_medicas.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Servicios del ADMINISTRADOR para asignar consultorios (registrar consultas)
 * y ver todas las consultas.
 *
 * @author Juan Manuel Polanco
 */
@RestController
@RequestMapping("/api/admin/consultas")
@Tag(name = "3. Administrador - Consultorios", description = "Asignación de consultorios. Requiere rol ADMINISTRADOR.")
@SecurityRequirement(name = SwaggerConfig.SESION)
public class AdminConsultaRestController {

	@Autowired
	private ConsultaService consultaService;

	@Operation(summary = "Listar todas las consultas", description = "Devuelve todas las consultas registradas, ordenadas por hora de inicio.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: lista de consultas",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Consulta.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesión",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden: su rol no es ADMINISTRADOR",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))) })
	@GetMapping
	public List<Consulta> listar() {
		return consultaService.listarTodas();
	}

	@Operation(summary = "Asignar consultorio", description = "Registra una consulta: nombre del paciente (alfanumérico, máx. 40), "
			+ "motivo (máx. 100), número de consultorio, hora de inicio y de fin (HH:mm) y el id del médico.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: consultorio asignado",
					content = @Content(schema = @Schema(implementation = Consulta.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request: datos inválidos",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesión",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden: su rol no es ADMINISTRADOR",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))) })
	@PostMapping
	public ResponseEntity<Consulta> crear(@RequestBody ConsultaRequest datos) {
		return ResponseEntity.ok(consultaService.crear(datos));
	}
}
