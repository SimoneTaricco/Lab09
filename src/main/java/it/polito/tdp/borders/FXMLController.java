
package it.polito.tdp.borders;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.db.BordersDAO;
import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class FXMLController {

	private Model model;
	private BordersDAO dao;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    private ChoiceBox<Country> choiceBox;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	try {
    		int anno = Integer.parseInt(this.txtAnno.getText());
    		Map<Country, Integer> stats = model.creaGrafo(anno);
    		
    		txtResult.appendText("Numero stati: " + stats.size() + "\n");
    		
    		this.choiceBox.setDisable(false);
    		this.choiceBox.getItems().addAll(stats.keySet());
    		
    		for (Country c:stats.keySet()) {
    			txtResult.appendText(c + " " + stats.get(c) + "\n");	
    		}
	
    	} catch (NumberFormatException e) {
    		txtResult.setText("Formato anno non valido!");
    		return;
    	}

    }
    
    @FXML
    void doCalcolaStatiRaggiungibili(ActionEvent event) {
    	
    	if (this.choiceBox.getValue() == null) {
    		this.txtResult.setText("Selezionare uno stato.");
    		return;
    	} else {

    	this.txtResult.clear();
    	Country co = this.choiceBox.getValue();
    	txtResult.appendText("Stati raggiungibili da " + co + ":\n");
    	
    	for (Country c:model.statiRaggiungibili(this.choiceBox.getValue())){
    		if (co.equals(c) == false)
    			txtResult.appendText(c + "\n"); // non stampa lo stato di partenza (anche se questo è inserito nella lista)
    		}
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	choiceBox.setDisable(true);
    	choiceBox.setTooltip(new Tooltip("Seleziona uno stato"));
    }
}
