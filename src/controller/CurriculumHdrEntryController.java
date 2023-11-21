/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShCurrHdr;
import entity.Strands;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ShCurrHdrModel;
import model.StrandsModel;
import others.AutoCompleteComboBoxListener;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class CurriculumHdrEntryController implements Initializable {

    @FXML
    private Button btnSave;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDescription;
    @FXML
    private ComboBox cbStrand;
    
    private StrandsModel strandsModel;
    private ShCurrHdrModel shCurrHdrModel;
    
    private ShCurrHdr shCurrHdr;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        strandsModel = new StrandsModel();
        shCurrHdrModel = new ShCurrHdrModel();
        
        ObservableList<String> listStrands = FXCollections.observableArrayList(strandsModel.getStrandsForCombobox());
        cbStrand.setItems(listStrands); 
    }   

 

    public void actionSave(ActionEvent event) throws Exception {
        if (validateInput()) {

            ShCurrHdr shCurrHdr = new ShCurrHdr();
            
            shCurrHdr.setCurrName(txtName.getText());
            shCurrHdr.setCurrDesc(txtDescription.getText());
            shCurrHdr.setStrandcode(cbStrand.getValue().toString());

            Boolean success = shCurrHdrModel.saveCurriculum(shCurrHdr);
            
            ((Stage) btnSave.getScene().getWindow()).close();
            
            if(success==true)
                this.showMessage(success, "Successful", "Curriculum Header Created!", "Curriculum Header is created successfully");
            else
                this.showMessage(success, "Error", "Error", "Error occured");

        }
        
    }
    
    
    
    private void showMessage(boolean success, String title, String header, String content){
        Alert alert;
        if (success == true) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
        }
        
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
        
    }
    
    private boolean validateInput() {

        String errorMessage = "";

        if (txtName.getText() == null || txtName.getText().length() == 0) {
            errorMessage += "No valid name!\n";
        }

        if (txtDescription.getText() == null || txtDescription.getText().length() == 0) {
            errorMessage += "No valid description!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            showMessage(false,"Invalid Fields","Please correct invalid fields",errorMessage);
            return false;
        }
    }
    
    
}
