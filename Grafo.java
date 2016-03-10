// Cole Ellison
// Sudoku • Marzo 2016
// Especificacion y Desarollo de Sistemas de Software

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Grafo {
	
	protected HashMap<Integer,HashSet<Integer>> mapa;

	public Grafo() {
		mapa = new HashMap<Integer,HashSet<Integer>>();
	}

	public void anadirVertice(int vertice) {
		mapa.put(vertice,new HashSet<Integer>());
	}

	public void anadirArista(int v1, int v2) {
		mapa.get(v1).add(v2);
		mapa.get(v2).add(v1);
	}

	public void eliminarVertice(int vertice) {
		mapa.remove(vertice);
		Iterator it = mapa.keySet().iterator();
	    while (it.hasNext()) {
	    	mapa.get(it.next()).remove(vertice);
	    }
	}

	public void eliminarArista(int v1, int v2) {
		if (mapa.containsKey(v1) && mapa.containsKey(v2)) {
			mapa.get(v1).remove(v2);
			mapa.get(v2).remove(v1);
		}
	}

	public boolean hayArista(int v1, int v2) {
		return mapa.get(v1).contains(v2);
	}

	public boolean hayVertice(int vertice) {
		return mapa.containsKey(vertice);
	}

	public HashSet listaVertices() {
		return new HashSet<Integer>(mapa.keySet());
	}

	public HashSet listaAdyacentes(int vertice) {
		return mapa.get(vertice);
	}

	public static void main(String[] args) {
		Grafo grafo = new Grafo();
		grafo.anadirVertice(3);
		grafo.anadirVertice(4);
		grafo.anadirVertice(2);
		grafo.anadirVertice(1);
		System.out.println(grafo.listaVertices());
		grafo.anadirArista(3,4);
		grafo.anadirArista(4,1);
		grafo.anadirArista(3,3);
		System.out.println(grafo.listaAdyacentes(3));
		grafo.eliminarArista(3,3);
		System.out.println(grafo.listaAdyacentes(3));
		grafo.eliminarVertice(3);
		System.out.println(grafo.listaAdyacentes(4));
		grafo.eliminarVertice(1);
		System.out.println(grafo.listaVertices());
		System.out.println(grafo.listaAdyacentes(4));
	}

}