package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private BordersDAO dao;
	private Graph<Country,DefaultWeightedEdge> grafo; // ha tutti i collegamenti
	private Map<Integer,Country> idMap; // Chiave: Ccode
		
	public Model() {
		dao = new BordersDAO();		
		idMap = new HashMap<Integer,Country>(); 
		
		for (Country c:dao.loadAllCountries()) { 
			idMap.put(c.getCcode(), c); 
		}
	}
	
	public Map<Country,Integer> creaGrafo(int anno) { 
		
		grafo = new SimpleGraph<>(DefaultWeightedEdge.class);
		
		Map<Country,Integer> res = new HashMap<Country,Integer>(); 
			
		for(Adiacenza a:dao.getAdiacenze(anno)) { 
			this.grafo.addVertex(idMap.get(a.getState1no()));
			this.grafo.addVertex(idMap.get(a.getState2no()));
			this.grafo.addEdge(idMap.get(a.getState1no()), idMap.get(a.getState2no()));		
		}
		
		for (Country c:this.grafo.vertexSet()) {
			res.put(c,grafo.degreeOf(c));
		}
	
	   System.out.println("Numero di vertici: " + grafo.vertexSet().size());
	   System.out.println("Numero di archi: " + grafo.edgeSet().size());	
	   
	   return res;
			
	}


}
