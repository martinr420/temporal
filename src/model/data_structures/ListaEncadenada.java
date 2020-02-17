package model.data_structures;

import java.util.ArrayList;
import java.util.Iterator;
import com.google.gson.Gson;

public class ListaEncadenada<K> implements IListaDoblementeEncadenada{


	//Atributos 
	private Nodo<K> primero;

	private Nodo<K> ultimo;

	private int tamano;

	//constructor
	public ListaEncadenada()
	{
		primero = null;

		ultimo = null;

		tamano = 0;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Nodo<K> darUltimo()
	{
		return ultimo;
	}
	@Override
	public int darTamano() {
		// TODO Auto-generated method stub
		return tamano;
	}

	@Override
	public Nodo darNodoEnPos(int pPosicion) throws noExisteObjetoException {
		// TODO Auto-generated method stub
		Nodo<K> actual = primero;

		int cont = 0;

		while(cont < pPosicion)
		{
			actual = actual.darSiguiente();

			cont++;
		}



		return actual;
	}

	@Override
	public int darPosicionNodo(Nodo pNodo) throws noExisteObjetoException {

		Nodo<K> actual = primero;

		int cont = 0;

		while(actual != pNodo)
		{
			actual.darSiguiente();
			cont++;
		}


		// TODO Auto-generated method stub
		return cont;
	}

	@Override
	public Nodo<K> EliminarUltimo() throws noExisteObjetoException {

		if(tamano == 0)
		{
			throw new noExisteObjetoException();
		}
		else
		{
			Nodo<K> aEliminar = ultimo;
			
			if(aEliminar.darAnterior() != null)
			{
				
				ultimo = aEliminar.darAnterior();

				ultimo.desconectarSiguiente();
				


			}
			else
			{
			
				ultimo = null;
				primero = null;
				


			}



			tamano--;

			return aEliminar;
		}

		// TODO Auto-generated method stub

	}

	@Override
	public Nodo<K> EliminarPrimero() throws noExisteObjetoException {


		if(tamano == 0)
		{
			throw new noExisteObjetoException();
		}
		else
		{
			Nodo<K> aEliminar = primero;

			if(aEliminar.darSiguiente() != null)
			{
				primero = aEliminar.darSiguiente();

				primero.desconectarAnterior();


			}
			else
			{
				primero = null;
				ultimo = null;
			}

			tamano--;
			
			return aEliminar;
		}



		// TODO Auto-generated method stub

	}

	@Override
	public Nodo<K> EliminarNodoPos(int pPosicion) throws FueraDeAlcanceException, noExisteObjetoException {

		if(pPosicion >= tamano)
		{
			throw new FueraDeAlcanceException(pPosicion, tamano);
		}
		else
		{
			if(pPosicion == 0)
			{
				
				EliminarPrimero();
				return primero;
			}
			else if(pPosicion == tamano - 1)
			{
				EliminarUltimo();
				
				return ultimo;
			}
			else
			{
				int cont = 0;
				Nodo<K> aEliminar = primero;

				while(cont < pPosicion)
				{
					aEliminar = aEliminar.darSiguiente();
					cont++;
				}

				aEliminar.darAnterior().cambiarSiguiente(aEliminar.darSiguiente());
				aEliminar.darSiguiente().cambiarAnterior(aEliminar.darAnterior());
				tamano--;
				
				return aEliminar;
			
			}
			
		}
		
		
		// TODO Auto-generated method stub

	}

	@Override
	public Nodo<K> EliminarNodoObj(Nodo pNodo) throws noExisteObjetoException {
		Nodo<K> aEliminar = primero;



		while(aEliminar != pNodo )
		{
			if(aEliminar.darSiguiente()== null)
			{
				throw new noExisteObjetoException();
			}
			else
			{
				aEliminar = aEliminar.darSiguiente();
			}

		}

		aEliminar.darAnterior().cambiarSiguiente(aEliminar.darSiguiente());
		aEliminar.darSiguiente().cambiarAnterior(aEliminar.darAnterior());
		tamano--;
		return aEliminar;




		// TODO Auto-generated method stub

	}

	@Override
	public Nodo<K> agregarNodoInicio(Nodo pNodo) {

		if(primero == null)
		{
			primero = pNodo;
			ultimo = pNodo;
		}
		else
		{
			pNodo.cambiarSiguiente(primero);
			primero.cambiarAnterior(pNodo);

			primero = pNodo;
		}
		tamano++;
		
		return pNodo;

		// TODO Auto-generated method stub

	}

	@Override
	public Nodo<K> agregarNodoAlFinal(Nodo pNodo) {

		if(primero == null)
		{
			primero = pNodo;
			ultimo = pNodo;

		}
		else
		{
			pNodo.cambiarAnterior(ultimo);
			ultimo.cambiarSiguiente(pNodo);
			ultimo = pNodo;
		}

		tamano++;
		return pNodo;
		// TODO Auto-generated method stub

	}

	@Override
	public Nodo<K> agregarNodoEnPosicion(int pPosicion, Nodo pNodo) throws FueraDeAlcanceException {

		if(pPosicion >= tamano)
		{

			throw new FueraDeAlcanceException(pPosicion, pPosicion);

			// TODO Auto-generated method stub
		}
		else
		{
			Nodo<K> actual = primero;

			int cont = 0;
			if(pPosicion == 0)
			{
				agregarNodoInicio(pNodo);
			}

			else if(pPosicion == tamano-1)
			{
				agregarNodoAlFinal(pNodo);
			}

			else
			{
				while(cont < pPosicion-1)
				{

					actual = actual.darSiguiente();


				}
				
				

				pNodo.cambiarSiguiente(actual.darSiguiente());
				pNodo.cambiarAnterior(actual);
				actual.darSiguiente().cambiarAnterior(pNodo);
				actual.cambiarSiguiente(pNodo);
				tamano++;
				
				



			}


		}
		return pNodo;
	}

	@Override
	public ArrayList<Nodo<K>> darNodos() {

		Nodo<K> actual = primero;
		ArrayList<Nodo<K>> lista = new ArrayList<Nodo<K>>();

		while(actual != null)
		{
			lista.add(actual);
		}





		// TODO Auto-generated method stub
		return lista;
	}

	public class IteratorLista<K> implements Iterator<Nodo<K>>
	{

		private boolean PrimeraIter;
		private Nodo<K> actual;
		ListaEncadenada<K> lista;


		public IteratorLista() 
		{
			PrimeraIter = true;
			actual = null;
			lista = new ListaEncadenada<K>();
			
			

			
		}	// TODO Auto-generated constructor stub
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			boolean existe = false;
			
			if(PrimeraIter && primero != null)
			{	
					existe = true;	
			}
			else if (actual.darSiguiente() != null)
			{
				existe = true;
			}
			
			
			
			return existe;
		}

		@Override
		public Nodo<K> next() 
		{
			if(PrimeraIter)
			{
				actual = (Nodo<K>) primero;
				PrimeraIter = false;
			}
			else 
			{
				actual = actual.darSiguiente();
			}
			// TODO Auto-generated method stub
			return  actual;
		}

	}

	@Override
	public Nodo darPrimero() {
		// TODO Auto-generated method stub
		return primero;
	}






}
