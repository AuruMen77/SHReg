/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShTermReg;
import entity.StrandSection;
import interfaces.StrandSectionInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ShTermRegModel;
import model.StrandSectionModel;
import model.ShSectionsModel;
import model.StrandsModel;
import model.CustomEnrolledStudentModel;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class SectionEntryController implements Initializable,StrandSectionInterface {

    @FXML
    private ComboBox cbSY;
    @FXML
    private ComboBox cbSem;
    @FXML
    private ComboBox cbGradelevel;
    @FXML
    private ComboBox cbStrand;
    @FXML
    private ComboBox cbStrandgroup;
    @FXML
    private TextField txtSection;
    @FXML
    private Button btnSave;
    
    private StrandSectionModel sectionsModel;
    private StrandsModel strandsModel;
    private ShSectionsModel strandgroupModel;
    private ShTermRegModel flagsModel;
    
    private ShTermReg currentEnrollment;
    private StrandSection selected_strandSection;
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sectionsModel = new StrandSectionModel();
        strandsModel = new StrandsModel();
        strandgroupModel =  new ShSectionsModel();
        flagsModel =  new ShTermRegModel();
        
        currentEnrollment=flagsModel.getRowCurrentEnrollment();
        
        loadSYinCbox();
        loadSemInCbox();
        loadGradelevelInCbox();
        loadStrandsInCbox();
        loadStrandgroupsInCbox();
    } 
    
    public void setSection(StrandSection strandSection){
        selected_strandSection = strandSection;
        cbSY.getSelectionModel().select(selected_strandSection.getSy());
        cbSem.getSelectionModel().select(selected_strandSection.getSem());
        cbGradelevel.getSelectionModel().select(selected_strandSection.getGradeLevel());
        cbStrand.getSelectionModel().select(selected_strandSection.getStrand());
        cbStrandgroup.getSelectionModel().select(selected_strandSection.getStrandgroup());
        txtSection.setText(selected_strandSection.getStudSection());
    }
    
    
    
    private void loadSYinCbox() {
        ObservableList<String> listSYforCbox = FXCollections.observableArrayList();
        
        String sy_val = "";
        for(int i=2016; i<=2050; i++){
            sy_val = i + "-" + (i+1);
            listSYforCbox.add(sy_val);
        }
        
        cbSY.setItems(listSYforCbox);
        cbSY.getSelectionModel().select(currentEnrollment.getSyReg());

    }
    
    private void loadSemInCbox() {
        ObservableList<String> listSem = FXCollections.observableArrayList();
        listSem.add("1");
        listSem.add("2");
        listSem.add("3");
        cbSem.setItems(listSem);
        cbSem.getSelectionModel().select(currentEnrollment.getSemReg());
    }
    
    private void loadGradelevelInCbox() {
        ObservableList<String> listGradelevel = FXCollections.observableArrayList();
        listGradelevel.add("11");
        listGradelevel.add("12");
        cbGradelevel.setItems(listGradelevel);
        cbGradelevel.getSelectionModel().select(0);
    }
    
    private void loadStrandsInCbox(){
        ObservableList<String> listStrands = FXCollections.observableArrayList(strandsModel.getStrandsForCombobox());
        cbStrand.setItems(listStrands);
        cbStrand.getSelectionModel().select(0);
    }
    
     private void loadStrandgroupsInCbox(){
        ObservableList<String> listStrandgroups = FXCollections.observableArrayList(strandgroupModel.getStrandgroupsForCombobox());
        cbStrandgroup.setItems(listStrandgroups);
        cbStrandgroup.getSelectionModel().select("A");
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

        if (txtSection.getText() == null || txtSection.getText().length() == 0) {
            errorMessage += "No valid name!\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            showMessage(false,"Invalid Fields","Please correct invalid fields",errorMessage);
            return false;
        }
    }
    
    @FXML
    public void actionSave(ActionEvent event) throws Exception {

        if (validateInput()) {
            StrandSection strandSection;

            if(selected_strandSection == null){
                strandSection = new StrandSection();
            }else{
                strandSection = selected_strandSection;
            }

            strandSection.setSy(cbSY.getSelectionModel().getSelectedItem().toString());
            strandSection.setSem(Integer.parseInt(cbSem.getSelectionModel().getSelectedItem().toString()));
            strandSection.setGradeLevel(Integer.parseInt(cbGradelevel.getSelectionModel().getSelectedItem().toString()));
            strandSection.setStrand(cbStrand.getSelectionModel().getSelectedItem().toString());
            strandSection.setStrandgroup(cbStrandgroup.getSelectionModel().getSelectedItem().toString());
            strandSection.setStudSection(txtSection.getText());

            Boolean success = sectionsModel.saveSection(strandSection);
            
            ((Stage) btnSave.getScene().getWindow()).close();
            
            if(success==true)
                this.showMessage(success, "Successful", "Section Saved!", "Section is saved successfully");
            else
                this.showMessage(success, "Error", "Error", "Error occured");
            
            
            listSection.clear();
            listSection.removeAll(listSection);
            
            listSection.addAll(sectionsModel.getResSection(cbSY.getSelectionModel().getSelectedItem().toString(), this.cbSem.getSelectionModel().getSelectedItem().toString(), 
                cbGradelevel.getSelectionModel().getSelectedItem().toString(), cbStrand.getSelectionModel().getSelectedItem().toString()));
            
            
            
        }

    }
    
}
