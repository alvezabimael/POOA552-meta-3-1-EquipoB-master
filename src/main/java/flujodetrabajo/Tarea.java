package flujodetrabajo;

import java.io.Serializable;

public class Tarea implements Serializable {

	private String nombre;
	private Actividad actividad;
	private Fase fase;
	private FlujoDeTrabajo flujoDeTrabajo;

	public Tarea(String nombre, Actividad actividad, Fase fase, FlujoDeTrabajo flujoDeTrabajo) {
		this.nombre = nombre;
		this.actividad = actividad;
		this.flujoDeTrabajo = flujoDeTrabajo;
		this.fase = fase;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}

	@Override
	public String toString() {
		return actividad.getNombre() +
				": " + nombre;
	}
}