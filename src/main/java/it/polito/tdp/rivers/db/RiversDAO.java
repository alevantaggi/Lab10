package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Caratteristiche;
import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public List<River> getAllRivers(Map<Integer, River> riverIdMap) {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				River r= new River(res.getInt("id"), res.getString("name"));
				rivers.add(r);
				riverIdMap.put(r.getId(), r);
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public Caratteristiche getAllInformations(Integer riverId) {
		String sql="SELECT MIN(DAY) AS fMis, MAX(DAY) AS lMis, COUNT(*) AS tot, AVG(flow) AS fmed "
				+ "FROM flow "
				+ "WHERE river=? "
				+ "ORDER BY DAY";
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			
			st.setInt(1, riverId);
			
			ResultSet rs= st.executeQuery();
			
			rs.first();
			
			Caratteristiche c= new Caratteristiche(rs.getDate("fMis").toLocalDate(), rs.getDate("lMis").toLocalDate(), rs.getInt("tot"), rs.getDouble("fmed"));
			
			conn.close();
			return c;
			
		} catch (Exception e) {
			System.out.println("Errore in getAllInformations");
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Flow> getAllFlowById(Integer riverId){
		String sql="SELECT DAY, flow "
				+ "FROM flow "
				+ "WHERE river=? "
				+ "ORDER BY DAY ";
		List<Flow> risultato= new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			
			st.setInt(1, riverId);
			
			ResultSet rs= st.executeQuery();
			
			while(rs.next())
				risultato.add(new Flow(rs.getDate("DAY").toLocalDate(), rs.getDouble("flow")*86400));
			
			conn.close();
			return risultato;	
			
		} catch (Exception e) {
			System.out.println("Errore in getAllFlowById");
			e.printStackTrace();
			return null;
		}

	}
}
