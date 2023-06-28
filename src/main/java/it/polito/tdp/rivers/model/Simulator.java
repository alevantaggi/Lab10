package it.polito.tdp.rivers.model;

import java.util.List;
import java.util.PriorityQueue;


public class Simulator {

	// Stato del sistema e output
	private double C;
	private Integer numTotEventi;
		


	// Coda degli eventi
	private PriorityQueue<Flow> queue;
		

	// PARAMETRI DI INGRESSO - TUNING
	private List<Flow> flows;
	private double Q; 
	private double fMed;
		
		

	// INDICATORI DI USCITA
	private double Cmed;
	private Integer numGiorniCritici;
	
		
	public Simulator(Double c, List<Flow> flows, Double q, Double fMed) {
		C = c;
		this.flows = flows;
		Q = q;
		this.fMed = fMed;
		this.queue= new PriorityQueue<>();
		this.Cmed=0.0;
		this.numGiorniCritici=0;
	}

	public void initialize() {
		for(Flow f: flows) 
			this.queue.add(f);
		this.numTotEventi=flows.size();
	}
		
	public void run() {
		while(! this.queue.isEmpty()) {
			Flow f= this.queue.poll();
			processEvent(f);			
		}	
	}
		
	private void processEvent(Flow f) {
		double fOut;
			
		if(Math.random()> 0.95) {
			fOut=8*this.fMed;
		}
		else {
			fOut=0.8*this.fMed;
		}
		
		C+= f.getFlow();
		if(C> Q) {
			C=Q;
		}
		
		
		if(C< fOut) {
			this.numGiorniCritici++;
			C=0;
		}
		else {
			C-= fOut;
		}
		
		this.Cmed+=C;
	}

	public double getCmed() {
		return Cmed/this.numTotEventi;
	}

	public Integer getNumGiorniCritici() {
		return numGiorniCritici;
	}
	
	

	
	
}
