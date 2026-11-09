package co.edu.usco.consultas_medicas.service;

import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import co.edu.usco.consultas_medicas.dto.ConsultaRequest;
import co.edu.usco.consultas_medicas.dto.HorarioRequest;
import co.edu.usco.consultas_medicas.model.Consulta;
import co.edu.usco.consultas_medicas.model.Medico;
import co.edu.usco.consultas_medicas.model.Usuario;
import co.edu.usco.consultas_medicas.repository.ConsultaRepository;
import co.edu.usco.consultas_medicas.repository.MedicoRepository;

/**
 * Lógica de consultas: asignar consultorios, listar por rol y cambiar
 * horarios.
 *
 * @author Juan Manuel Polanco
 */
@Service
public class ConsultaService {

	/** Letras (con tildes y ñ), números y espacios. */
	private static final String ALFANUMERICO = "[A-Za-zÁÉÍÓÚáéíóúÑñÜü0-9 ]+";

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private MedicoRepository medicoRepository;

	/** @return todas las consultas, ordenadas por hora de inicio (administrador) */
	public List<Consulta> listarTodas() {
		return consultaRepository.findAll(Sort.by("horaInicio"));
	}

	/**
	 * @param medicoId id del médico
	 * @return solo las consultas asignadas a ese médico
	 */
	public List<Consulta> listarPorMedico(Integer medicoId) {
		return consultaRepository.findByMedicoIdOrderByHoraInicio(medicoId);
	}

	/**
	 * @param nombrePaciente nombre del paciente
	 * @return solo las citas de ese paciente
	 */
	public List<Consulta> listarPorPaciente(String nombrePaciente) {
		return consultaRepository.findByNombrePacienteIgnoreCaseOrderByHoraInicio(nombrePaciente);
	}

	/**
	 * @param id id de la consulta
	 * @return la consulta si existe
	 */
	public Optional<Consulta> buscar(Integer id) {
		return consultaRepository.findById(id);
	}

	/**
	 * Registra una consulta (asigna el consultorio).
	 *
	 * @param datos datos del formulario
	 * @return la consulta guardada
	 * @throws IllegalArgumentException si algún dato no es válido
	 */
	public Consulta crear(ConsultaRequest datos) {
		String nombre = datos.getNombrePaciente() == null ? "" : datos.getNombrePaciente().trim();
		if (nombre.isEmpty() || nombre.length() > 40 || !nombre.matches(ALFANUMERICO)) {
			throw new IllegalArgumentException(
					"El nombre del paciente es obligatorio, solo admite letras, números y espacios, y no debe superar los 40 caracteres.");
		}
		String motivo = datos.getMotivo() == null ? "" : datos.getMotivo().trim();
		if (motivo.isEmpty() || motivo.length() > 100) {
			throw new IllegalArgumentException("El motivo es obligatorio y no debe superar los 100 caracteres.");
		}
		if (datos.getNumeroConsultorio() == null || datos.getNumeroConsultorio() <= 0) {
			throw new IllegalArgumentException("El número de consultorio debe ser un número mayor que cero.");
		}
		validarHorario(datos.getHoraInicio(), datos.getHoraFin());
		if (datos.getMedicoId() == null) {
			throw new IllegalArgumentException("Debe seleccionar un médico.");
		}
		Medico medico = medicoRepository.findById(datos.getMedicoId())
				.orElseThrow(() -> new IllegalArgumentException("El médico seleccionado no existe."));

		Consulta consulta = new Consulta();
		consulta.setNombrePaciente(nombre);
		consulta.setMotivo(motivo);
		consulta.setNumeroConsultorio(datos.getNumeroConsultorio());
		consulta.setHoraInicio(datos.getHoraInicio());
		consulta.setHoraFin(datos.getHoraFin());
		consulta.setMedico(medico);
		return consultaRepository.save(consulta);
	}

	/**
	 * Cambia el horario de una cita. El paciente solo puede cambiar sus propias
	 * citas.
	 *
	 * @param id       id de la cita
	 * @param horario  nuevo horario
	 * @param paciente usuario que hace el cambio
	 * @return la cita actualizada
	 * @throws NoSuchElementException   si la cita no existe
	 * @throws AccesoDenegadoException  si la cita es de otro paciente
	 * @throws IllegalArgumentException si el horario no es válido
	 */
	public Consulta actualizarHorario(Integer id, HorarioRequest horario, Usuario paciente) {
		Consulta consulta = consultaRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("No existe la cita con id " + id + "."));
		if (!esDelPaciente(consulta, paciente)) {
			throw new AccesoDenegadoException("Solo puede modificar el horario de sus propias citas.");
		}
		validarHorario(horario.getHoraInicio(), horario.getHoraFin());
		consulta.setHoraInicio(horario.getHoraInicio());
		consulta.setHoraFin(horario.getHoraFin());
		return consultaRepository.save(consulta);
	}

	/**
	 * @return true si la consulta pertenece al paciente (se compara por nombre)
	 */
	public boolean esDelPaciente(Consulta consulta, Usuario paciente) {
		return consulta.getNombrePaciente().trim().equalsIgnoreCase(paciente.getNombre().trim());
	}

	/** La hora de inicio y la de fin son obligatorias y el fin debe ser después del inicio. */
	private void validarHorario(LocalTime inicio, LocalTime fin) {
		if (inicio == null || fin == null) {
			throw new IllegalArgumentException("La hora de inicio y la hora de finalización son obligatorias.");
		}
		if (!fin.isAfter(inicio)) {
			throw new IllegalArgumentException("La hora de finalización debe ser posterior a la hora de inicio.");
		}
	}
}
