package view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import controller.ControladorDepartamento;
import model.DAO.Util;
import model.DTO.Ciudad;
import model.DTO.Departamento;

@ManagedBean
@ApplicationScoped
public class BeanDepartamento {
	private Departamento departamento;
	private String nombre;
	private List<SelectItem> listaDepartamentos;
	private List<Ciudad> ciudades;
	private ControladorDepartamento controladorDepartamento;
	private static final String CODIGOERROR = "BED0";
	private static final String DESCRIPCIONERROR = " Error en bean Departamento ";
	private static final String MENSAJEEXITOSO = "Transaccion Exitosa ";
	private static String codError = CODIGOERROR;
	private static String descError = DESCRIPCIONERROR;
	private static String mensajeError = "";

	public BeanDepartamento() {
		departamento = new Departamento();
		ciudades = new ArrayList<Ciudad>();
		controladorDepartamento = new ControladorDepartamento();
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Ciudad> getCiudades() {
		return ciudades;
	}

	public void setCiudades(List<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}

	public List<SelectItem> getListaDepartamentos() {
		try {
			this.listaDepartamentos = new ArrayList<SelectItem>();
			List<Departamento> departamentosLista = controladorDepartamento.seleccionarDepartamentos();
			this.listaDepartamentos.clear();
			for (Departamento departamentos : departamentosLista) {
				SelectItem departamentoOpcion = new SelectItem(departamentos.getDepartamentoId(),
						departamentos.getNombreDepartamento());
				this.listaDepartamentos.add(departamentoOpcion);
			}
			Util.CreateLog(codError + "00", MENSAJEEXITOSO, "Consulta Exitosa en Bean Departamento");
		} catch (Exception e) {
			codError += "03";
			Util.CreateLog(CODIGOERROR + codError, DESCRIPCIONERROR, e.getMessage());
		}
		return listaDepartamentos;
	}

	public String consultaCiudadesDepartamento() {
		try {

			departamento = controladorDepartamento.seleccionarDepartamentoId(departamento.getDepartamentoId());
			ciudades.clear();
			ciudades = departamento.getCiudads();
			codError = "0000";
			mensajeError = "Transacción Exitosa en Submit";
			descError = "Transacción exitosa";
		} catch (Exception e) {
			codError = "03";
			mensajeError = "Error en submit " + e.getMessage();
		} finally {
			Util.CreateLog(CODIGOERROR + codError, descError, mensajeError);

		}
		return codError;

	}

	public String crearDepartamento() {
		String resultado = controladorDepartamento.crearDepartamento(nombre);
		System.out.println(resultado);
		return resultado;
	}

}
