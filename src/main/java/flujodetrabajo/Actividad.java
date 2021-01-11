package flujodetrabajo;

import java.io.Serializable;
import java.util.*;

public class Actividad implements Serializable {

	private String nombre;
	private FlujoDeTrabajo flujoDeTrabajo;
	private Vector<Tarea> tareas;

	public Actividad(String nombre, FlujoDeTrabajo flujoDeTrabajo) {
		this.nombre = nombre;
		this.flujoDeTrabajo = flujoDeTrabajo;
		this.tareas = new Vector<Tarea>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public FlujoDeTrabajo getFlujoDeTrabajo() {
		return flujoDeTrabajo;
	}

	public void setFlujoDeTrabajo(FlujoDeTrabajo flujoDeTrabajo) {
		this.flujoDeTrabajo = flujoDeTrabajo;
	}

	public Vector<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(Vector<Tarea> tareas) {
		this.tareas = tareas;
	}

	@Override
	public String toString() {
		return "Actividad{" +
				"nombre='" + nombre + '\'' +
				'}';
	}
}