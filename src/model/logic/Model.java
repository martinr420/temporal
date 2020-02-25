package model.logic;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


import model.data_structures.IListaDoblementeEncadenada;
import model.data_structures.ListaDoblementeEncadenada;
import model.data_structures.ListaDoblementeEncadenada.IteratorLista;
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
		datos = new ListaDoblementeEncadenada<Multa>();
		
	}

	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
//	public Modelo(int capacidad)
//	{
//		//datos = new ArregloDinamico(capacidad);
//	}
	
	public ListaDoblementeEncadenada<Multa> darDatos()
	{
		return (ListaDoblementeEncadenada<Multa>) datos;
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
	public void agregar(Multa dato)
	{	
		datos.agregarNodoAlFinal(dato);;
	}

	/**
	 * Requerimiento buscar dato
	 * @param dato Dato a buscar
	 * @return dato encontrado
	 * @throws noExisteObjetoException 
	 */
	public int buscarPosicion(Multa dato) throws noExisteObjetoException
	{
		return datos.darPosicionNodo(dato);
	}

	/**
	 * Requerimiento eliminar dato
	 * @param dato Dato a eliminar
	 * @return dato eliminado
	 * @throws noExisteObjetoException 
	 */
	public Multa eliminar(Multa dato) throws noExisteObjetoException
	{
		return datos.EliminarNodoObj(dato);
	}

	public void cargarDatos() throws noExisteObjetoException 
	{
		String path = "./data/Gson";
		JsonReader lector;


		try {


			ListaDoblementeEncadenada<Multa> listaMultas = new ListaDoblementeEncadenada<>();


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


				
				datos.agregarNodoAlFinal(multa);

			} //llave for grande

		}//llave try
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		


	} //llave metodo
	
	
	public void darInfoCargaDatos() throws noExisteObjetoException
	{
		
		cargarDatos();
		System.out.println("La cantidad de comparendos es de: "+datos.darTamano());
		System.out.println("EL comparendo con el ID mas alto es :" + BuscarComparendoIDMasAlto().toString());
		System.out.println("El minimax es: ");
		System.out.println("longitud minima: " + darMinimax()[0]);
		System.out.println("longitud maxima: " + darMinimax()[1]);
		System.out.println("latitud minima: " + darMinimax()[2]);
		System.out.println("latitud maxima:" + darMinimax()[3]);
		
		
	}


	private Multa BuscarComparendoIDMasAlto() 
	{
		Nodo<Multa> actual = (Nodo<Multa>) datos.iterator();
		Multa laMulta = new Multa(-1, "", "", "", "", "", "","", null);
		
		while(actual.hasNext())
		{
			if(actual.darGenerico().getId() > laMulta.getId() )
			{
				laMulta = actual.darGenerico();
			}
		}
		return laMulta;
	}

	public Multa buscarComparendoPorId(long pID)
	{
		boolean parar = false;
		Multa laMulta = null;
		Nodo<Multa> actual = (Nodo<Multa>) datos.iterator();
		while(actual.hasNext() && !parar)
		{
			if(actual.darGenerico().getId() == pID)
			{
				parar = true;
				laMulta = actual.darGenerico();
				actual = actual.next();

			}
		}

		
		return laMulta;
	}
	
	
	public double[] darMinimax()
	{
		/*
		 * arreglo [0] = latitud minima 
		 * arreglo [1] = latitud maxima
		 * arreglo [2] = longitud minima 
		 * arreglo [3] = longitud maxima
		 */
		
		
		
		double[] arreglo = new double[4];
		
		double longitudMin = 180; // inicializada en el valor maximo que puede tener la longitud

		double longitudMax = -180; // inicializada en el valor minimo que puede tener la longitud
		
		double latitudMin = 90; // inicializada en el valor maximo que puede tener la latitud
		
		double latitudMax = -90; // inicializada en el valor minimo que puede tener la latitud 
		
		
		
		
		IteratorLista lista = (IteratorLista) datos.iterator();
		
		while(lista.hasNext())
		{
			Multa laMulta = (Multa) lista.next();
			double longitud = laMulta.getGeo().getCoordenadas()[0];
			
			double latitud = laMulta.getGeo().getCoordenadas()[1];
			if(longitud < longitudMin)
			{
				longitudMin = longitud;
			}
			if(longitud > longitudMax)
			{
				longitudMax = longitud;
			}
			
			if(latitud < latitudMin)
			{
				latitudMin = latitud;
			}
			if(latitud > latitudMax)
			{
				latitudMax = latitud;
			}
		}
		
		arreglo[0] = longitudMin;
		arreglo[1] = longitudMax;
		arreglo[2] = latitudMin;
		arreglo[3] = latitudMax;
		
		
		
		
		return arreglo;
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
