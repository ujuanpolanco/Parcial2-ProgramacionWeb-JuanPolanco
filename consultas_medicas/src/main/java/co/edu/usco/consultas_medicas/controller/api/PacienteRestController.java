package co.edu.usco.consultas_medicas.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.consultas_medicas.config.SwaggerConfig;
import co.edu.usco.consultas_medicas.dto.HorarioRequest;
import co.edu.usco.consultas_medicas.dto.MensajeResponse;
import co.edu.usco.consultas_medicas.model.Consulta;
import co.edu.usco.consultas_medicas.model.Usuario;
import co.edu.usco.consultas_medicas.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

/**
 * Servicios del PACIENTE: ver sus citas y cambiar el horario solo de las citas
 * que le pertenecen.
 *
 * @author Juan Manuel Polanco
 */
@RestController
@RequestMapping("/api/paciente/citas")
@Tag(name = "5. Paciente", description = "Citas del paciente. Requiere rol PACIENTE.")
@SecurityRequirement(name = SwaggerConfig.SESION)
public class PacienteRestController {

	@Autowired
	private ConsultaService consultaService;

	@Operation(summary = "Ver mis citas", description = "Devuelve solo las citas del paciente que inició sesión.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: citas del paciente",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Consulta.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesión",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden: su rol no es PACIENTE",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))) })
	@GetMapping
	public List<Consulta> misCitas(HttpSession sesion) {
		Usuario paciente = (Usuario) sesion.getAttribute("usuario");
		return consultaService.listarPorPaciente(paciente.getNombre());
	}

	@Operation(summary = "Actualizar horario de una cita", description = "Cambia la hora de inicio y de fin de una cita. "
			+ "El paciente solo puede modificar las citas que tiene asignadas; si la cita es de otro paciente responde 403.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: horario actualizado",
					content = @Content(schema = @Schema(implementation = Consulta.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request: horario inválido (la hora de fin debe ser posterior a la de inicio)",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesión",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden: su rol no es PACIENTE o la cita no es suya",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "404", description = "Not Found: la cita no existe",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))) })
	@PutMapping("/{id}/horario")
	public Consulta actualizarHorario(@Parameter(description = "Id de la cita", example = "1") @PathVariable Integer id,
			@RequestBody HorarioRequest horario, HttpSession sesion) {
		Usuario paciente = (Usuario) sesion.getAttribute("usuario");
		return consultaService.actualizarHorario(id, horario, paciente);
	}
}
