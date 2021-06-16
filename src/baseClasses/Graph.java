package baseClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T> {
	private Map<T, ArrayList<T>> VertexTable;

	public Graph() {
		//Pre:non
		//Post: initialize Graph
		
		this.VertexTable = new HashMap<>();
	}

	public void addVertex(T v) {
		//Pre: Vertex Table must have been initialized
		//Post: add the vertex to the VertexTable(Map)
		if(!this.VertexTable.containsKey(v)) VertexTable.put(v, new ArrayList<T>());
	}

	public void addEdge(T v, T d) {
		//Pre:Vertex Table must have been initialized
		//Post: Add an Edge to VertexTable
		VertexTable.get(v).add(d);
		VertexTable.get(d).add(v);

	}

	public void deleteVertex(T v) {
		//Pre:Vertex Table must have been initialized
		//Post: Delete vertex v from VertexTable
		
		ArrayList<T> adyacentes = VertexTable.get(v);
		VertexTable.remove(v);
		for (T t : adyacentes) {
			VertexTable.get(t).remove(v);
		}
	}

	public void deleteEdge(T v, T d) {
		//Pre:Vertex Table must have been initialized
		//Post: Delete edge from VertexTable
		VertexTable.get(v).remove(d);
		VertexTable.get(d).remove(v);
	}

	public int numberofVertex() {
		//Pre:Vertex Table must have been initialized
		// Post: return VertexTable size
		return VertexTable.size();
	}

	public boolean areAdjacent(T v, T d) {
		//Pre:Vertex Table must have been initialized
		//Post: Return true if vertices v and d are adjacent , false in other case.
		return VertexTable.get(v).contains(d);

	}

	public boolean containsVertex(T v) {
		//Pre:Vertex Table must have been initialized
		//Post: Return true if vertex v is in VertexTable
		return VertexTable.containsKey(v);
	}

	public List<T> vertexOfGraph() {
		//Pre:Vertex Table must have been initialized
		//Post: return a list of vertices of the VertexTable
		return new ArrayList<>(this.VertexTable.keySet());

	}

	public List<T> listAdjacentOfVertex(T t) {
		//Pre:Vertex Table must have been initialized
		//Post:Return a list with vertices that are adjacent
		return VertexTable.get(t);

	}
	 
	
	

}
