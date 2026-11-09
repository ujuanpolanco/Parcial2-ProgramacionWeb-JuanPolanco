package co.edu.usco.consultas_medicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.edu.usco.consultas_medicas.model.Usuario;
import co.edu.usco.consultas_medicas.service.ConsultaService;
import jakarta.servlet.http.HttpSession;

/**
 * Página del MEDICO: solo ve las consultas que tiene asignadas, sin acciones.
 *
 * @author Juan Manuel Polanco
 */
@Controller
@RequestMapping("/medico")
public class MedicoController {

	@Autowired
	private ConsultaService consultaService;

	@GetMapping("/consultas")
	public String misConsultas(HttpSession sesion, Model model) {
		Usuario medico = (Usuario) sesion.getAttribute("usuario");
		model.addAttribute("consultas", consultaService.listarPorMedico(medico.getMedico().getId()));
		return "medico/consultas";
	}
}
