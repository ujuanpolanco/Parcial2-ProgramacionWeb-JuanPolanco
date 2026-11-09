package co.edu.usco.consultas_medicas.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.edu.usco.consultas_medicas.dto.ConsultaRequest;
import co.edu.usco.consultas_medicas.dto.MedicoRequest;
import co.edu.usco.consultas_medicas.model.Medico;
import co.edu.usco.consultas_medicas.service.ConsultaService;
import co.edu.usco.consultas_medicas.service.MedicoService;
import co.edu.usco.consultas_medicas.service.UsuarioService;

/**
 * Páginas del ADMINISTRADOR: gestión de médicos y asignación de consultorios.
 * El interceptor ya garantiza que solo entra el rol ADMINISTRADOR.
 *
 * @author Juan Manuel Polanco
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private MedicoService medicoService;

	@Autowired
	private ConsultaService consultaService;

	@Autowired
	private UsuarioService usuarioService;

	// ===================== Médicos =====================

	@GetMapping("/medicos")
	public String listarMedicos(Model model) {
		model.addAttribute("medicos", medicoService.listar());
		return "admin/medicos";
	}

	@GetMapping("/medicos/nuevo")
	public String nuevoMedico(Model model) {
		return formularioMedico(model, new MedicoRequest(), "Nuevo médico", "/admin/medicos/guardar", null);
	}

	@PostMapping("/medicos/guardar")
	public String guardarMedico(@ModelAttribute MedicoRequest datos, Model model) {
		try {
			medicoService.crear(datos);
			return "redirect:/admin/medicos?msg=creado";
		} catch (IllegalArgumentException e) {
			return formularioMedico(model, datos, "Nuevo médico", "/admin/medicos/guardar", e.getMessage());
		}
	}

	@GetMapping("/medicos/editar/{id}")
	public String editarMedico(@PathVariable Integer id, Model model) {
		Optional<Medico> medico = medicoService.buscar(id);
		if (medico.isEmpty()) {
			return "redirect:/admin/medicos?msg=noexiste";
		}
		MedicoRequest datos = new MedicoRequest();
		datos.setNombre(medico.get().getNombre());
		datos.setEspecialidad(medico.get().getEspecialidad());
		medicoService.buscarUsuario(id).ifPresent(usuario -> datos.setUsername(usuario.getUsername()));
		return formularioMedico(model, datos, "Editar médico", "/admin/medicos/actualizar/" + id, null);
	}

	@PostMapping("/medicos/actualizar/{id}")
	public String actualizarMedico(@PathVariable Integer id, @ModelAttribute MedicoRequest datos, Model model) {
		try {
			medicoService.actualizar(id, datos);
			return "redirect:/admin/medicos?msg=actualizado";
		} catch (NoSuchElementException e) {
			return "redirect:/admin/medicos?msg=noexiste";
		} catch (IllegalArgumentException e) {
			return formularioMedico(model, datos, "Editar médico", "/admin/medicos/actualizar/" + id, e.getMessage());
		}
	}

	@PostMapping("/medicos/eliminar/{id}")
	public String eliminarMedico(@PathVariable Integer id) {
		try {
			medicoService.eliminar(id);
			return "redirect:/admin/medicos?msg=eliminado";
		} catch (NoSuchElementException e) {
			return "redirect:/admin/medicos?msg=noexiste";
		} catch (IllegalStateException e) {
			return "redirect:/admin/medicos?msg=tieneconsultas";
		}
	}

	/** Prepara el formulario de médico (sirve para crear y para editar). */
	private String formularioMedico(Model model, MedicoRequest datos, String titulo, String accion, String error) {
		model.addAttribute("form", datos);
		model.addAttribute("titulo", titulo);
		model.addAttribute("accion", accion);
		model.addAttribute("esNuevo", accion.endsWith("guardar"));
		model.addAttribute("error", error);
		return "admin/form-medico";
	}

	// ===================== Consultorios =====================

	@GetMapping("/consultas")
	public String listarConsultas(Model model) {
		model.addAttribute("consultas", consultaService.listarTodas());
		return "admin/consultas";
	}

	@GetMapping("/consultas/nueva")
	public String nuevaConsulta(Model model) {
		return formularioConsulta(model, new ConsultaRequest(), null);
	}

	@PostMapping("/consultas/guardar")
	public String guardarConsulta(@ModelAttribute ConsultaRequest datos, BindingResult binding, Model model) {
		if (binding.hasErrors()) {
			return formularioConsulta(model, datos,
					"Revise los datos: el consultorio debe ser un número y las horas deben tener formato HH:mm.");
		}
		try {
			consultaService.crear(datos);
			return "redirect:/admin/consultas?msg=asignado";
		} catch (IllegalArgumentException e) {
			return formularioConsulta(model, datos, e.getMessage());
		}
	}

	/** Prepara el formulario de consultorio con la lista de médicos de la base de datos. */
	private String formularioConsulta(Model model, ConsultaRequest datos, String error) {
		model.addAttribute("form", datos);
		model.addAttribute("medicos", medicoService.listar());
		model.addAttribute("pacientes", usuarioService.listarPacientes());
		model.addAttribute("error", error);
		return "admin/form-consulta";
	}
}
