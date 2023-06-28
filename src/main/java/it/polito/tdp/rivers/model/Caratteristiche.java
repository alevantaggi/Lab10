package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Caratteristiche {
	
	private LocalDate fistM;
	private LocalDate lastM;
	private Integer totM;
	private Double fMed;
	
	
	public Caratteristiche(LocalDate fistM, LocalDate lastM, Integer totM, Double fMed) {
		this.fistM = fistM;
		this.lastM = lastM;
		this.totM = totM;
		this.fMed = fMed;
	}


	public LocalDate getFistM() {
		return fistM;
	}


	public LocalDate getLastM() {
		return lastM;
	}


	public Integer getTotM() {
		return totM;
	}


	public Double getfMed() {
		return fMed;
	}
	
	
	
	

}
