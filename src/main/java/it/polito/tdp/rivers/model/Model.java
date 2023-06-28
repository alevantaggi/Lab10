package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	private Map<Integer, River> riverIdMap;
	private RiversDAO dao;
	private List<River> rivers;
	
	public Model() {
		this.dao= new RiversDAO();
		this.riverIdMap= new HashMap<>();
		this.rivers= new ArrayList<>(this.dao.getAllRivers(riverIdMap));
	}
	
	public List<River> getAllRivers(){
		return this.rivers;
	}
	
	public Caratteristiche getAllInformations(Integer riverID) {
		return this.dao.getAllInformations(riverID);
	}
	
	public List<Flow> getAllFlowById(Integer riverID){
		return this.dao.getAllFlowById(riverID);
	}
	
	public String getParametriSimulazione(Integer riverID, Integer k) {
		List<Flow> flows= new ArrayList<>(getAllFlowById(riverID));
		Caratteristiche car= getAllInformations(riverID);
		
		double Q= k*car.getfMed()*30*86400;
				
		Simulator sim= new Simulator(Q/2, flows, Q, car.getfMed()*86400);
		sim.initialize();
		sim.run();
		
		return"Numero di gironi 'critici': "+sim.getNumGiorniCritici()+"\nOccupazione media del bacino: "+ sim.getCmed()+"\nSIMULAZIONE TERMINATA";
	}

}
