package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;
import it.polito.tdp.metroparis.model.Fermata;

public class Model {
	
	private BordersDAO dao;
	private Graph<Country,DefaultEdge> grafo; // ha tutti i collegamenti
	private Map<Integer,Country> idMap; // Chiave: Ccode
	Map<Country,Country> predecessore;
		
	public BordersDAO getDao() {
		return dao;
	}

	public Model() {
		dao = new BordersDAO();		
		idMap = new HashMap<Integer,Country>(); 
		
		for (Country c:dao.loadAllCountries()) { 
			idMap.put(c.getCcode(), c); 
		}
	}
	
	public Map<Country,Integer> creaGrafo(int anno) { 
		
		grafo = new SimpleGraph<>(DefaultEdge.class);
		
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

	public List<Country> statiRaggiungibili(Country stato) {

		List<Country> res = new ArrayList<Country>();
		
		
		BreadthFirstIterator<Country,DefaultEdge> bfv = new BreadthFirstIterator<>(this.grafo,stato);
		
		/*
		// Algoritmo iterativo (visita in ampiezza)
		this.predecessore = new HashMap<Country,Country>();
		this.predecessore.put(stato, null);
		
		bfv.addTraversalListener(new TraversalListener<Country,DefaultEdge>(){
			
			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				DefaultEdge arco = e.getEdge();
				
				Country a = grafo.getEdgeSource(arco);
				Country b = grafo.getEdgeTarget(arco);

				if (predecessore.containsKey(b) && !predecessore.containsKey(a)) {
					predecessore.put(a, b);
				} else if (predecessore.containsKey(a) && !predecessore.containsKey(b)) {
					predecessore.put(b, a);
				}				
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
				Country nuova = e.getVertex();
				Country precedente = bfv.getParent(nuova);
			predecessore.put(nuova,precedente);
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
			}			
		});
		*/
		
		DepthFirstIterator<Country,DefaultEdge> dfv = new DepthFirstIterator<>(this.grafo,stato);
		
		while(bfv.hasNext()) {
			Country c = bfv.next(); 
			res.add(c);
		}
		
		System.out.println("Stati raggiungibili: " + res.size());

		return res;
		
	}
}
