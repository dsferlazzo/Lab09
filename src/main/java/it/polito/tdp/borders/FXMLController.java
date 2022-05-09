package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Country> cmbStati;

    @FXML
    private TextField txtAnno;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	int anno;
    	try {
    	anno = Integer.parseInt(txtAnno.getText());	//TODO CONTROLLI SULL'INPUT
    	} catch(Exception e ) {
    		e.printStackTrace();
			txtResult.appendText("Inserire un valore valido nel campo \"Anno\"");
			throw new RuntimeException("Error Connection Database");
    	}
    	
    	if(anno<1816 || anno>2016)
    	{
    		txtResult.appendText("Inserire un valore nell'intervallo [1816,2016]");
    		return;
    	}
    	
    	String s = model.creaGrafo(anno);
    	txtResult.appendText(s);
    	
    	//AGGIUNGERE INSERIMENTO STATI IN CMB
    	
    	List<Country> countries = model.getCountryByAnno(anno);
    	for(Country c : countries)
    		cmbStati.getItems().add(c);
    }

    @FXML
    void doStatiRaggiungibili(ActionEvent event) {
    	txtResult.clear();
    	if(cmbStati.getValue()==null)
    	{
    		txtResult.appendText("Selezionare uno stato, per calcolarne gli stati raggiungibili");
    		return;
    	}
    	
    	Country c = cmbStati.getValue();	//TODO CONTROLLI SULL'INPUT
    	
    	List<Country> raggiungibili = model.getStatiConnessi(c);
    	//List<Country> raggiungibili = model.getSCIterator(c);
    	//System.out.println("Controller: " + raggiungibili.size());	//DEBUGGING
    	for(Country c1: raggiungibili)
    	{
    		txtResult.appendText(c1+"\n");
    	}

    }

    @FXML
    void initialize() {
        assert cmbStati != null : "fx:id=\"cmbStati\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    void setModel(Model model) {
    	this.model=model;
    }

}
