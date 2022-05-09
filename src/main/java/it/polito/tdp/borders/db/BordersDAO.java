package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	/**
	 * Dato un anno, ritorno tutti gli stati con almeno un border entro quell'anno
	 * @param anno
	 * @return
	 */
	public List<Country> getCountriesWithBorders(int anno) {
		String sql = "SELECT DISTINCT c.CCode, c.StateAbb, c.StateNme "
				+ "FROM country c, contiguity cy "
				+ "WHERE cy.year<=? AND(cy.state1no=c.CCode OR cy.state2no=c.CCode) AND cy.conttype=1 "
				+ "ORDER BY  c.StateNme asc";
		//DA CAMBIARE CON ORDER BY PER CNAME (?)
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			Country c;
			while (rs.next()) {
				c = new Country (rs.getInt("CCode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				result.add(c);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	/**
	 * Dato un anno limite, ritorna tutti bordi esistenti entro quell'anno
	 * @param anno
	 * @return
	 */
	public List<Border> getCountryPairs(int anno) {
		String sql = "SELECT state1no AS id1, state2no AS id2 "
				+ "FROM contiguity "
				+ "WHERE YEAR<=? AND conttype=1";
		List<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			Border b ;
			while(rs.next()) {
				b = new Border(rs.getInt("id1"), rs.getInt("id2"));
				result.add(b);
			}
			conn.close();
			return result;
			
		} catch (SQLException e ) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		
	}
}
