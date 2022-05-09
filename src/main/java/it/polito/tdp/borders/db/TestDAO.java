package it.polito.tdp.borders.db;

import java.util.List;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Border;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();

		System.out.println("Lista di tutte le nazioni:");
		//List<Country> countries = dao.loadAllCountries();
		List<Country> countries = dao.getCountriesWithBorders(1980);
		for(Country c : countries)
			System.out.println(c.getStateNme());
	}
}
