// Cole Ellison
// Sudoku • Marzo 2016
// Especificacion y Desarollo de Sistemas de Software

import java.util.HashMap;
import java.util.Iterator;

class GrafoConColores extends Grafo {
	
	private HashMap<Integer,Integer> colores;

	public GrafoConColores() {
		super();
		colores = new HashMap<Integer,Integer>();
	}

	public void colorear(int vertice, int color) {
		if (puedeColorear(vertice,color)) {
			colores.put(vertice,color);
		}
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
		boolean puede = true;
		Iterator it = mapa.get(vertice).iterator();
	    while (it.hasNext()) {
		    puede = puede && (color == colores.get(it.next()));
	    }
	    return puede;
	}

	public int recibir(int vertice) {
		return colores.get(vertice);
	}

	public static void main(String[] args) {

	}
}