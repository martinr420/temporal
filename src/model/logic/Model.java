package model.logic;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.data_structures.ArregloDinamico;
import model.data_structures.IArregloDinamico;
import model.data_structures.IListaDoblementeEncadenada;
import model.data_structures.ListaEncadenada;
import model.data_structures.ListaEncadenada.IteratorLista;
import model.data_structures.Nodo;
import model.data_structures.noExisteObjetoException;

/**
 * Definicion del modelo del mundo
 *
 */
public class Model {
	/**
	 * Atributos del modelo del mundo
	 */
	private IListaDoblementeEncadenada<Multa> datos;


	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Model()
	{
		datos = new ListaEncadenada<Multa>();
		
	}

	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
//	public Modelo(int capacidad)
//	{
//		//datos = new ArregloDinamico(capacidad);
//	}
	
	public ListaEncadenada<Multa> darDatos()
	{
		return (ListaEncadenada<Multa>) datos;
	}

	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano()
	{
		return datos.darTamano();
	}

	/**
	 * Requerimiento de agregar dato
	 * @param dato
	 */
	public void agregar(Nodo<Multa> dato)
	{	
		datos.agregarNodoAlFinal(dato);;
	}

	/**
	 * Requerimiento buscar dato
	 * @param dato Dato a buscar
	 * @return dato encontrado
	 * @throws noExisteObjetoException 
	 */
	public int buscarPosicion(Nodo<Multa> dato) throws noExisteObjetoException
	{
		return datos.darPosicionNodo(dato);
	}

	/**
	 * Requerimiento eliminar dato
	 * @param dato Dato a eliminar
	 * @return dato eliminado
	 * @throws noExisteObjetoException 
	 */
	public Nodo<Multa> eliminar(Nodo<Multa> dato) throws noExisteObjetoException
	{
		return datos.EliminarNodoObj(dato);
	}

	public void cargarDatos() throws noExisteObjetoException 
	{
		String path = "./data/Gson";
		JsonReader lector;


		try {


			ListaEncadenada<Multa> listaMultas = new ListaEncadenada<>();


			lector = new JsonReader(new FileReader(path));
			JsonElement elem = JsonParser.parseReader(lector);
			JsonObject ja = elem.getAsJsonObject();

			JsonArray features = ja.getAsJsonArray("features");


			for(JsonElement e : features)
			{



				JsonObject propiedades = (JsonObject) e.getAsJsonObject().get("properties");
				long id = propiedades.get("OBJECTID").getAsLong();
				String fecha = propiedades.get("FECHA_HORA").getAsString();
				String medioDete = propiedades.getAsJsonObject().get("MEDIO_DETE").getAsString();
				String claseVehiculo = propiedades.getAsJsonObject().get("CLASE_VEHI").getAsString();
				String tipoServicio = propiedades.getAsJsonObject().get("TIPO_SERVI").getAsString();
				String infraccion = propiedades.getAsJsonObject().get("INFRACCION").getAsString();
				String descripcion = propiedades.getAsJsonObject().get("DES_INFRAC").getAsString();
				String localidad = propiedades.getAsJsonObject().get("LOCALIDAD").getAsString();


				JsonObject geometry = (JsonObject) e.getAsJsonObject().get("geometry");

				String tipo = geometry.get("type").getAsString();

				double[] listaCoords = new double[3];

				JsonArray coordsJson = geometry.getAsJsonArray("coordinates");




				for(int i = 0; i < coordsJson.size(); i ++)
				{
					listaCoords[i] = coordsJson.get(i).getAsDouble();


				}

				Geo geometria = new Geo(tipo, listaCoords);

				Multa multa = new Multa(id, fecha, medioDete, claseVehiculo, tipoServicio, infraccion, descripcion, localidad, geometria);


				Nodo<Multa> nMulta = new Nodo<Multa>(multa);
				datos.agregarNodoAlFinal(nMulta);

			} //llave for grande

		}//llave try
		catch (IOException e) 
		{
			e.printStackTrace();
		}


	} //llave metodo


	public Multa buscarComparendoPorId(long pID)
	{
		boolean parar = false;
		Multa laMulta = null;
		Nodo<Multa> actual = datos.darPrimero();
		while(actual.darSiguiente() != null && !parar)
		{
			if(actual.darGenerico().getId() == pID)
			{
				parar = true;
				laMulta = actual.darGenerico();

			}
		}

		
		return laMulta;
	}
	
	public int darTotalComparendos()
	{
		return 0;
	}
	
	public Multa darComparendoMayorID()
	{
		return null;
	}
	
	public ArrayList<Integer> darMinimax()
	{
		return null;
	}
	
	
	public Multa darComparendoLocalidad()
	{
		return null;
	}
	
	public ArrayList<Multa> darMultasComparacion(String fecha1, String fecha2)
	{
		return null;
	}
	
	public Multa ConsultarComparendoPorINfraccion(String pInfraccion)
	{
		return null;
	}
	
	public ArrayList<Multa> CompararCOmparendosTipoServi()
	{
		return null;
	}
	
	public ArrayList<Multa> compararComparendoLocalidad(String pLocalidad)
	{
		return null;
	}
	
	public ArrayList<Multa> darMasComparendo(String date1, String date2)
	{
		return null;
	}
	public ArrayList<Localidad> darLocalidadesConMultas()
	{
		return null;
	}
	
	
	
	
	

}//llave clase
