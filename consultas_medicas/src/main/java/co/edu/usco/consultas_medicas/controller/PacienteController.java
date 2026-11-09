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

import co.edu.usco.consultas_medicas.dto.HorarioRequest;
import co.edu.usco.consultas_medicas.model.Consulta;
import co.edu.usco.consultas_medicas.model.Usuario;
import co.edu.usco.consultas_medicas.service.AccesoDenegadoException;
import co.edu.usco.consultas_medicas.service.ConsultaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Páginas del PACIENTE: ver sus citas y cambiar el horario de las que le
 * pertenecen.
 *
 * @author Juan Manuel Polanco
 */
@Controller
@RequestMapping("/paciente")
public class PacienteController {

	@Autowired
	private ConsultaService consultaService;

	@GetMapping("/citas")
	public String misCitas(HttpSession sesion, Model model) {
		Usuario paciente = (Usuario) sesion.getAttribute("usuario");
		model.addAttribute("citas", consultaService.listarPorPaciente(paciente.getNombre()));
		return "paciente/citas";
	}

	@GetMapping("/citas/horario/{id}")
	public String formularioHorario(@PathVariable Integer id, HttpSession sesion, HttpServletResponse response,
			Model model) {
		Usuario paciente = (Usuario) sesion.getAttribute("usuario");
		Optional<Consulta> cita = consultaService.buscar(id);
		if (cita.isEmpty()) {
			return "redirect:/paciente/citas?msg=noexiste";
		}
		if (!consultaService.esDelPaciente(cita.get(), paciente)) {
			return accesoDenegado(response, model, "Solo puede modificar el horario de sus propias citas.");
		}
		HorarioRequest horario = new HorarioRequest();
		horario.setHoraInicio(cita.get().getHoraInicio());
		horario.setHoraFin(cita.get().getHoraFin());
		return formulario(model, cita.get(), horario, null);
	}

	@PostMapping("/citas/horario/{id}")
	public String actualizarHorario(@PathVariable Integer id, @ModelAttribute HorarioRequest horario,
			BindingResult binding, HttpSession sesion, HttpServletResponse response, Model model) {
		Usuario paciente = (Usuario) sesion.getAttribute("usuario");
		try {
			if (binding.hasErrors()) {
				throw new IllegalArgumentException("Las horas deben tener formato HH:mm.");
			}
			consultaService.actualizarHorario(id, horario, paciente);
			return "redirect:/paciente/citas?msg=actualizado";
		} catch (NoSuchElementException e) {
			return "redirect:/paciente/citas?msg=noexiste";
		} catch (AccesoDenegadoException e) {
			return accesoDenegado(response, model, e.getMessage());
		} catch (IllegalArgumentException e) {
			Optional<Consulta> cita = consultaService.buscar(id);
			if (cita.isEmpty()) {
				return "redirect:/paciente/citas?msg=noexiste";
			}
			if (!consultaService.esDelPaciente(cita.get(), paciente)) {
				return accesoDenegado(response, model, "Solo puede modificar el horario de sus propias citas.");
			}
			return formulario(model, cita.get(), horario, e.getMessage());
		}
	}

	private String formulario(Model model, Consulta cita, HorarioRequest horario, String error) {
		model.addAttribute("cita", cita);
		model.addAttribute("form", horario);
		model.addAttribute("error", error);
		return "paciente/form-horario";
	}

	/** Muestra la vista informativa de acceso denegado con estado 403. */
	private String accesoDenegado(HttpServletResponse response, Model model, String mensaje) {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		model.addAttribute("mensaje", mensaje);
		return "acceso-denegado";
	}
}
