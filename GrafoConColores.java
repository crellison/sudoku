// Cole Ellison
// Sudoku • Marzo 2016
// Especificacion y Desarollo de Sistemas de Software

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

class GrafoConColores extends Grafo {
	
	private HashMap<Integer,Integer> colores;

	public GrafoConColores() {
		super();
		colores = new HashMap<Integer,Integer>();
	}

	public void borrar(int vertice) {
		colores.put(vertice,0);
	}

	public void borrarTodo() {
		Iterator it = mapa.keySet().iterator();
		while (it.hasNext()) {
	    	colores.put((Integer)it.next(),0);
	    }
	}

	public void anadirVertice(int vertice) {
		super.anadirVertice(vertice);
		colores.put(vertice,0);
	}

	public void eliminarVertice(int vertice) {
		super.eliminarVertice(vertice);
		colores.remove(vertice);
	}

	public boolean puedeColorear(int vertice, int color) {
		HashSet<Integer> adyacentes = super.listaAdyacentes(vertice);
		boolean puede = true;
		for (Integer temp : adyacentes) {
			puede = puede && (recibir(temp) != color);
		}
	    return puede;
	}

	public boolean colorear(int vertice, int color) {
		if (puedeColorear(vertice,color)==true) {
			colores.put(vertice,color);
			return true;
		} else {return false;}
	}

	public int recibir(int vertice) {
		return colores.get(vertice);
	}

	public static void main(String[] args) {
		GrafoConColores grafo = new GrafoConColores();
		grafo.anadirVertice(3);
		grafo.anadirVertice(4);
		grafo.anadirVertice(2);
		grafo.anadirVertice(1);
		System.out.println(grafo.listaVertices());
		grafo.anadirArista(3,4);
		grafo.anadirArista(4,1);
		grafo.anadirArista(3,3);
		System.out.println(grafo.listaAdyacentes(3));
		grafo.colorear(3,10);
		System.out.println(grafo.puedeColorear(4,10));
		grafo.eliminarArista(3,3);
		System.out.println(grafo.listaAdyacentes(3));
		grafo.eliminarVertice(3);
		System.out.println(grafo.listaAdyacentes(4));
		grafo.eliminarVertice(1);
		System.out.println(grafo.listaVertices());
		System.out.println(grafo.listaAdyacentes(4));
	}
}