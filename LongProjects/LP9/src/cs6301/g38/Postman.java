// Starter code for LP9
package cs6301.g38;
import cs6301.g38.Graph.*;
import java.util.List;

// Find a minimum weight postman tour that goes through every edge of g at least once

public class Postman {
	private Graph graph;
	
	private Vertex startVertex;
    public Postman(Graph g) {
    	
    	this.graph=g;
    	this.startVertex = g.getVertex(1);
    	
    }

    public Postman(Graph g, Vertex startVertex) {
    	this.graph=g;

    	this.startVertex=startVertex;
    }
    
    // Get a postman tour
    public List<Edge> getTour() {
    	PostManGraph pmg = new PostManGraph(graph, startVertex);
    	return pmg.getTour();
    }

    // Find length of postman tour
    public long postmanTour() {
    	PostManGraph pmg = new PostManGraph(graph, startVertex);
    	return pmg.postmanTour();    }
}
