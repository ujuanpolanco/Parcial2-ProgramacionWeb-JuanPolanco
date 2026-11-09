package co.edu.usco.consultas_medicas.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Usuario que inicia sesión en la aplicación. Su rol puede ser ADMINISTRADOR,
 * MEDICO o PACIENTE.
 * <ul>
 * <li>Si es MEDICO, el campo {@code medico} indica qué médico es.</li>
 * <li>Si es PACIENTE, su {@code nombre} debe ser igual al nombre del paciente
 * registrado en sus consultas.</li>
 * </ul>
 * Se guarda en la sesión (HttpSession) cuando inicia sesión.
 *
 * @author Juan Manuel Polanco
 */
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String ADMINISTRADOR = "ADMINISTRADOR";
	public static final String MEDICO = "MEDICO";
	public static final String PACIENTE = "PACIENTE";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true, length = 30)
	private String username;

	@Column(nullable = false, length = 60)
	private String password;

	@Column(nullable = false, length = 60)
	private String nombre;

	@Column(nullable = false, length = 15)
	private String rol;

	@ManyToOne
	@JoinColumn(name = "medico_id")
	private Medico medico;
}
