// Cole Ellison
// Sudoku • Marzo 2016
// Especificacion y Desarollo de Sistemas de Software

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashSet;

import java.lang.IndexOutOfBoundsException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

class Sudoku {
	
	private ArrayList<Integer> tablero;
	protected GrafoConColores datos;
	private int maxSqrt;

	public Sudoku() {
		this(3);
	}

	public Sudoku(int n) {
		if (n<1) {
			throw new IndexOutOfBoundsException("\n"+
				"El tamano de los cuadros pequenos debe ser mas de cero");
		}
		maxSqrt = n;
		int numVertices = n*n*n*n;
		tablero = new ArrayList<Integer>(numVertices);
		datos = new GrafoConColores();
		for (int i=0;i<numVertices;i++) {
			tablero.add(i,0);
			datos.anadirVertice(i);
			// vertices anumerado 0,1,2, ...,79,80
		}
		conectarVertices();
		limpiarAristas();
	}

	public void conectarVertices() {
		int max = maxSqrt*maxSqrt;
		for (int i=0; i<max; i++) {
			for (int j=0; j<max; j++) {
				int vertice = (max*i)+j;
				for (int k=0; k<max; k++) {
					datos.anadirArista(vertice,(max*k)+j); //columna
					datos.anadirArista(vertice,(max*i)+k); //fila
				}
			}	
		}
		int temp = 0;
		for (int i=0; i<maxSqrt; i++) {
			for (int k=0; k<maxSqrt; k++) {
				conectarCuadro(temp+(maxSqrt*k));
			}
			temp+=max*maxSqrt;
		}
	}
	public void conectarCuadro(int vertice) {
		ArrayList<Integer> vertices = new ArrayList<Integer>(maxSqrt*maxSqrt);
		for (int i=0; i<maxSqrt; i++) {
			for (int j=0; j<maxSqrt; j++) {
				int estaVertice = vertice+j+(maxSqrt*maxSqrt*i);
				vertices.add(estaVertice);
				for (Integer temp : vertices) {
					datos.anadirArista(estaVertice,temp);
				}
			}
		}
	}

	public void limpiarAristas() {
		int numVertices = maxSqrt*maxSqrt*maxSqrt*maxSqrt;
		for (int i=0; i<numVertices; i++) {
			datos.eliminarArista(i,i);
		}
	}

	public boolean anadirNum(int vertice,int n) {
		return datos.colorear(vertice,n);
	}

	public void colorearHumano() {
		System.out.println();
		int max = maxSqrt*maxSqrt;
		Scanner input = new Scanner(System.in);
		int vertice = elegirVertice(max);
		while (datos.recibir(vertice)!=0) {
			System.out.println("\nEsta vertice ya tiene numero\nEligelo otra vez");
			vertice = elegirVertice(max);
		}
		int color = 0;
		boolean valido = false;
		do {
			System.out.print("Escribe el numero que quieres usar: ");
			if (input.hasNextInt()) {
				color = input.nextInt();
				if (color<=max && color>0) {
					valido = datos.colorear(vertice,color);
					if (valido==false) {
						System.out.println("\nNo puede usar este numero\nEligelo otra vez");
					}
				}  else {
					System.out.println("El numero debe ser entre 0 y "+(max+1));
				}
			} else {
				System.out.println("La entrada debe der un entero");
				input.next();
			}
		} while (valido==false);
	}

	public int elegirVertice(int max) {
		Scanner input = new Scanner(System.in);
		ArrayList<Integer> vertice = new ArrayList<Integer>(2);
		int valido = 0;
		do {
			if (valido==0){
				System.out.print("Escribe la fila de la cuadrado que quieres usar: ");
			} else {
				System.out.print("Escribe la columna de la cuadrado que quieres usar: ");
			}
			if (input.hasNextInt()) {
				int temp = input.nextInt();
				if (temp<max && temp>=0) {
					vertice.add(temp);
					valido++;
				} else {
					System.out.println("El numero debe ser entre mayor igual de 0 y menor de "+max);
				}
			} else {
				System.out.println("La entrada debe der un entero");
			}
		} while (valido<2);
		return (vertice.get(0)*max)+vertice.get(1);
	}

	public void mostrar() {
		System.out.print("\033[H\033[2J"); // vacia el console
		titulo();
		int numVertices = maxSqrt*maxSqrt*maxSqrt*maxSqrt;
		String l = "–––––––";
		for (int i=0; i<numVertices; i++) {
			if (i!=0 && i%(maxSqrt*maxSqrt*maxSqrt)==0) {
				System.out.println("\n"+l+"+"+l+"+"+l);
			} else if (i%(maxSqrt*maxSqrt)==0) {
				System.out.println();
			} else if (i!=0 && i%maxSqrt==0) {
				System.out.print(" |");
			}
			System.out.print(" "+datos.recibir(i));
		}
		System.out.println();
	}

	public void titulo() {
		String documento = "sudoku_uso.txt";
		String line = null;
		try {
            FileReader lector = new FileReader(documento);
            BufferedReader bufferedLector = new BufferedReader(lector);

            while((line = bufferedLector.readLine()) != null) {
                System.out.println(line);
            }   
            bufferedLector.close();   
            System.out.println();      
        } catch(FileNotFoundException e) {
            System.out.println("Imposible a abrir documento '"+documento+"'");
        } catch (java.io.IOException e) {
        	System.out.println("Habia un error de leer el documento");
        }
	}

	public void partidaBasica() {
		String documento = "sudoku_uso.txt";
		String line = null;
		try {
            FileReader lector = new FileReader(documento);
            BufferedReader bufferedLector = new BufferedReader(lector);
            line = bufferedLector.readLine();
            bufferedLector.close();        
        } catch(FileNotFoundException e) {
            System.out.println("Imposible a abrir documento '"+documento+"'");
        } catch (java.io.IOException e) {
        	System.out.println("Habia un error de leer el documento");
        }
        String[] temp = line.split(" ");
        ArrayList<String> entradas = new ArrayList<String>(Arrays.asList(temp));
        for (int i=0; i<(entradas.size()/2); i++) {
        	datos.colorear(Integer.parseInt(entradas.get(i*2)),
        		Integer.parseInt(entradas.get(2*i +1)));
        } // NON FUNCTIONAL - NEED TO DEBUG
	}

	public static void main(String[] args) {
		int entrada = Integer.parseInt(args[0]);
		Sudoku sudoku = new Sudoku(entrada);

		// sudoku.partidaBasica(); 

		sudoku.mostrar();
		boolean temp = true;
		while (temp==true) {
			sudoku.colorearHumano();
			sudoku.mostrar();
		}
	}
}