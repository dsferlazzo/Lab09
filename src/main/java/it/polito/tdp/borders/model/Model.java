package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	BordersDAO dao;
	private Graph<Country, DefaultEdge> grafo;
	private List<Country> countries;
	private Map<Integer, Country> countryIdMap;
	
	private List<Country> temp;

	public Model() {	
		temp = new ArrayList<Country>();
	}
	
	public List<Country> getCountryByAnno(int anno){
		dao = new BordersDAO();
		countries = dao.getCountriesWithBorders(anno);
		
		countryIdMap = new HashMap<Integer, Country>();
		
		for(Country c : countries) {
			countryIdMap.put(c.getcCode(), c);
		}
		
		return countries;
	}

	public String creaGrafo(int anno) {
		dao = new BordersDAO();
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
		
		Graphs.addAllVertices(grafo, getCountryByAnno(anno));
		
		List<Border> borders = dao.getCountryPairs(anno);
		for(Border b : borders) {
			this.grafo.addEdge(countryIdMap.get(b.getState1No()),countryIdMap.get(b.getState2No()));
		}
		
		String result="";
		result+="Numero di vertici: " + grafo.vertexSet().size()+"\n"+
				"Numero di archi: " + grafo.edgeSet().size()+"\n" +
				"Numero di componenti connesse: " + getComponentiConnesse() + "\n";
		for(Country c : countries) {
			result += c.getStateNme() + "   " + Graphs.neighborListOf(grafo, c).size() + "\n";
		}
		
		return result;
	}
	
	public int getComponentiConnesse() {
		
		ConnectivityInspector ci = new ConnectivityInspector(grafo);
		int result = ci.connectedSets().size();
		return result;
		
	}
	
	public List<Country> getStatiConnessi(Country c){
		ConnectivityInspector ci = new ConnectivityInspector(grafo);	//DEUBGGING
		//System.out.println(ci.connectedSetOf(c).size());	//DEUBGGING
		
		List<Country> daVisitare = new ArrayList<Country>();
		daVisitare.add(c);	//PASSO 0
		List<Country> visitati = new ArrayList<Country>();
		ricorsioneStatiConnessi(visitati, daVisitare, 1);
		//System.out.println(visitati.size());	//DEBUGGING
		return visitati;	//DA TOGLIERE
	}
	
	private void ricorsioneStatiConnessi(List<Country> visitati, List<Country> daVisitare, int livello) {
		
		if(daVisitare.size()==0)	//CASO TERMINALE
			return;
		temp = new ArrayList<Country>();
		for(Country c : daVisitare) {	//CREO LA LISTA TEMPORANEA DI COUNTRY DA VISITARE
			List<Country> connessi = Graphs.neighborListOf(grafo, c);
			for(Country c1:connessi) {
				if(!temp.contains(c1) && !visitati.contains(c1))
					temp.add(c1);		//TEMP: LISTA DI COUNTRY COLLEGATI CON DAVISITARE, NON IN VISITATI E NON RIPETUTI
			}
		}
		
		for(Country c : daVisitare) {
			if(!visitati.contains(c))
				visitati.add(c);
		}
		
		daVisitare = new ArrayList<Country>(temp);
		//System.out.println(daVisitare.size());	//DEBUGGING
		ricorsioneStatiConnessi(visitati, daVisitare, livello+1);
		
	}
	
	public List<Country> getSCIterator(Country partenza){
		GraphIterator<Country,DefaultEdge> visita = new BreadthFirstIterator<>(this.grafo, partenza);
		List<Country> result = new ArrayList<Country>();
		Country c;
		
		while(visita.hasNext())
		{
			c = visita.next();
			result.add(c);
		}
		return result;
	}
	
}
