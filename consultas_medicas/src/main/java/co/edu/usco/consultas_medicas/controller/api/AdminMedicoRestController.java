package co.edu.usco.consultas_medicas.controller.api;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.consultas_medicas.config.SwaggerConfig;
import co.edu.usco.consultas_medicas.dto.MedicoRequest;
import co.edu.usco.consultas_medicas.dto.MensajeResponse;
import co.edu.usco.consultas_medicas.model.Medico;
import co.edu.usco.consultas_medicas.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Servicios del ADMINISTRADOR para crear, editar, eliminar y listar médicos.
 * El interceptor exige sesión (401) y rol ADMINISTRADOR (403).
 *
 * @author Juan Manuel Polanco
 */
@RestController
@RequestMapping("/api/admin/medicos")
@Tag(name = "2. Administrador - Médicos", description = "Gestión de médicos. Requiere rol ADMINISTRADOR.")
@SecurityRequirement(name = SwaggerConfig.SESION)
public class AdminMedicoRestController {

	@Autowired
	private MedicoService medicoService;

	@Operation(summary = "Listar médicos", description = "Devuelve todos los médicos registrados.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: lista de médicos",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Medico.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesión",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden: su rol no es ADMINISTRADOR",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))) })
	@GetMapping
	public List<Medico> listar() {
		return medicoService.listar();
	}

	@Operation(summary = "Buscar médico por id", description = "Devuelve un médico según su id.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: médico encontrado",
					content = @Content(schema = @Schema(implementation = Medico.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesión",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden: su rol no es ADMINISTRADOR",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "404", description = "Not Found: el médico no existe",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))) })
	@GetMapping("/{id}")
	public Medico buscar(@Parameter(description = "Id del médico", example = "1") @PathVariable Integer id) {
		return medicoService.buscar(id)
				.orElseThrow(() -> new NoSuchElementException("No existe el médico con id " + id + "."));
	}

	@Operation(summary = "Crear médico", description = "Registra un médico y crea su usuario (rol MEDICO) para que pueda iniciar sesión.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: médico creado",
					content = @Content(schema = @Schema(implementation = Medico.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request: datos inválidos o usuario repetido",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesión",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden: su rol no es ADMINISTRADOR",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))) })
	@PostMapping
	public ResponseEntity<Medico> crear(@RequestBody MedicoRequest datos) {
		return ResponseEntity.ok(medicoService.crear(datos));
	}

	@Operation(summary = "Editar médico", description = "Actualiza los datos del médico y su usuario. Si la contraseña va vacía, se conserva la actual.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: médico actualizado",
					content = @Content(schema = @Schema(implementation = Medico.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request: datos inválidos o usuario repetido",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesión",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden: su rol no es ADMINISTRADOR",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "404", description = "Not Found: el médico no existe",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))) })
	@PutMapping("/{id}")
	public Medico actualizar(@Parameter(description = "Id del médico", example = "1") @PathVariable Integer id,
			@RequestBody MedicoRequest datos) {
		return medicoService.actualizar(id, datos);
	}

	@Operation(summary = "Eliminar médico", description = "Elimina el médico y su usuario. No se permite si tiene consultas asignadas.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK: médico eliminado",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesión",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden: su rol no es ADMINISTRADOR",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "404", description = "Not Found: el médico no existe",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))),
			@ApiResponse(responseCode = "409", description = "Conflict: el médico tiene consultas asignadas",
					content = @Content(schema = @Schema(implementation = MensajeResponse.class))) })
	@DeleteMapping("/{id}")
	public MensajeResponse eliminar(@Parameter(description = "Id del médico", example = "1") @PathVariable Integer id) {
		medicoService.eliminar(id);
		return new MensajeResponse(200, "Médico eliminado.");
	}
}
