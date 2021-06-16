package baseClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class colorGraph<T> extends Graph<T> {
	public Map<T, Integer> colorTable;
	

	public colorGraph() {
		//Pre:non
		//Post:initialize ColorGraph and Graph
		super();
		colorTable = new HashMap<>();
	}

	public boolean addColorToVertex(T v, Integer c) {
		//Pre:Color Table must have been initialized
		//Post add color c to vertex v
		if (this.validateColor(v, c)) {
			this.colorTable.put(v, c);
			return true;
		}
		return false;
	}

	public boolean deleteColorOfVertex(T v) {
		//Pre:Color Table must have been initialized
		//Post delete color of vertex v
		if (this.containsVertex(v)) {
			colorTable.remove(v);
			return true;
		}
		return false;

	}

	public Integer informationAboutColorOfVertex(T v) {
		//Pre:Color Table must have been initialized
		//Post: return the color of Vertex v.
		return colorTable.get(v);
	}

	public void resetColorTable() {
		//Pre:Color Table must have been initialized.
		//Post: reset colorTable.
		colorTable.clear();
	}

	public List<T> verticessWithColor() {
		//Pre:Color Table must have been initialized.
		//Post: Return a list with the key elements of the map(Vertices)
		
		List<T> keys = new ArrayList<>(this.colorTable.keySet());
		return keys;

	}

	public boolean validateColor(T v, int n) {
		//Pre:Color Table must have been initialized.
		//Post: Return true if color is not yet in the vertices adjacent.
		//false in other case.
		
		List<T> adjacent= super.listAdjacentOfVertex(v);

		for(T d: adjacent)
		{
		  if( this.colorTable.containsKey(d) &&  this.informationAboutColorOfVertex(d).equals(n) )
		  {
				return false;
		  }
		}
		return true;
	}

	public boolean colorear(int numColores) {
		List<T> listaVertices;
		// lista auxiliar en la que colocaré todos los vértices

		/*
		 * Para poder aplicar el algoritmo de coloración de un grafo necesito tener los
		 * vértices almacenados en orden. En primer lugar colocaré los vértices que
		 * tienen ya un color asignado (este color no podrá modificarse). A continuación
		 * colocaré en la lista el resto de vértices, a los que el algoritmo de
		 * coloración irá asignando diferentes colores hasta dar con una combinación
		 * correcta.
		 */

		List<T> listaVerticesColoreados = this.verticessWithColor();
		List<T> listaVerticesNoColoreados = super.vertexOfGraph(); // todos
		listaVerticesNoColoreados.removeAll(listaVerticesColoreados); // quito los
																		// coloreados

		// Compruebo que los colores que ya están asignados son correctos
		for (T v : listaVerticesColoreados) {
			if (!this.validateColor(v, this.informationAboutColorOfVertex(v)))
				return false;
		}

		// vuelco los vértices en la nueva lista, en el orden correcto
		listaVertices = new ArrayList<>();
		listaVertices.addAll(listaVerticesColoreados);
		listaVertices.addAll(listaVerticesNoColoreados);

		int k = listaVerticesColoreados.size();
		boolean b = this.coloreoConRetroceso(listaVertices, k, numColores);

		if (!b) {
			// no se ha podido colorear el grafo
			// vuelvo a la situación inicial

			for (int i = 0, n = listaVerticesNoColoreados.size(); i < n; i++) {
				this.colorTable.remove(listaVerticesNoColoreados.get(i));
			}
		}
		return b;
	}

	private boolean aceptable(List<T> listaVertices, int color, int posicion) {
		/*
		 * devuelve true si al vértice que ocupa la posición k en listaVertices puedo
		 * asignarle el color k de modo que no haya ningún vértice en las posiciones
		 * anteriores que sea adyacente y que tenga el mismo color asignado.
		 */

		T vertPos = listaVertices.get(posicion);
		boolean acept = true;
		for (int i = 0; i < posicion && acept; i++) {
			if (super.areAdjacent(listaVertices.get(i), vertPos)
					&& this.informationAboutColorOfVertex(listaVertices.get(i)) == color)
				acept = false;
		}
		return acept;
	}

	private boolean coloreoConRetroceso(List<T> listaVertices, int k, int numColores) {
		/*
		 * Supongo que a los vértices situados en las posiciones 0..k-1 de listaVertices
		 * ya les he asignado color. Busco un color para el vértice en la posición k que
		 * sea compatible con los anteriores.
		 */

		if (k == listaVertices.size())
			return true;
		else {
			T vertAux = listaVertices.get(k);
			for (int c = 1; c <= numColores; c++) {
				if (this.aceptable(listaVertices, c, k)) {
					this.colorTable.put(vertAux, c);
					boolean b = coloreoConRetroceso(listaVertices, k + 1, numColores);
					if (b)
						return b;
				}
			}
		}

		// he recorrido todas las combinaciones y ninguna es válida, devuelvo falso.
		return false;

	}

	// Optional
	public void  show() {
		
		//Pre:Color Table must have been initialized
		//Post: show the structure of graph and colorGraph
		
		//This method has only been used to solve errors
		System.out.println("List of vertex" + super.vertexOfGraph());
		System.out.println("Edges:  ");
		for (T t : super.vertexOfGraph()) {
			System.out.println(t + ":" + super.listAdjacentOfVertex(t));
		}
		System.out.println("Colors: ");

		for (T t : this.colorTable.keySet()) {
			System.out.println(t + ":" + this.informationAboutColorOfVertex(t));
		}

	}

}
