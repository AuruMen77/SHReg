/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShApplicant;
import entity.ShStudStrand;
import entity.ShStudlist;
import entity.ShStudlistId;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.ShStudStrand_ShStudlistModel;
import model.StrandsModel;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class ApplicantPromoteEntryController implements Initializable {

    @FXML
    private TextField txtStudentID;
    @FXML
    private TextField txtStudentName;
    @FXML
    private ComboBox<String> cbSY;
    @FXML
    private ComboBox<String> cbSem;
    @FXML
    private ComboBox<String> cbGradelevel;
    @FXML
    private ComboBox cbStrand;
    @FXML
    private TextField txtStrandgroup;
    @FXML
    private CheckBox chkTransferee;
    @FXML
    private Button btnAssign;
    
    ShApplicant selected_shApplicant;
    private StrandsModel strandsModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        strandsModel = new StrandsModel();
        loadSYinCbox();
        loadSemInCbox();
        loadGradelevelInCbox();
        loadStrandsInCbox();
    }    
    
    public void setApplicantDetails(ShApplicant shApplicant) {
       selected_shApplicant = shApplicant;
       txtStudentID.setText(shApplicant.getStudIdnum());
       txtStudentName.setText(shApplicant.getAppLname().trim()+", "+shApplicant.getAppFname().trim()+" "+shApplicant.getAppMiddlename().trim());
       txtStrandgroup.setText("Z");
    }
    
    private void loadSYinCbox() {
        ObservableList<String> listSYforCbox = FXCollections.observableArrayList();
        
        String sy_val = "";
        for(int i=2016; i<=2050; i++){
            sy_val = i + "-" + (i+1);
            listSYforCbox.add(sy_val);
        }
        
        cbSY.setItems(listSYforCbox);
        cbSY.getSelectionModel().select("2020-2021");
    }
    
    private void loadSemInCbox() {
        ObservableList<String> listSem = FXCollections.observableArrayList();
        listSem.add("1");
        listSem.add("2");
        listSem.add("3");
        cbSem.setItems(listSem);
        cbSem.getSelectionModel().select("1");
    }
    
    private void loadGradelevelInCbox() {
        ObservableList<String> listGradelevel = FXCollections.observableArrayList();
        listGradelevel.add("11");
        listGradelevel.add("12");
        cbGradelevel.setItems(listGradelevel);
        cbGradelevel.getSelectionModel().select("11");
    }
    
    private void loadStrandsInCbox(){
        ObservableList<String> listStrands = FXCollections.observableArrayList(strandsModel.getStrandsForCombobox());
        cbStrand.setItems(listStrands);
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
        ShStudStrand_ShStudlistModel applicantPromoteEntryModel = new ShStudStrand_ShStudlistModel();
        boolean isPromoted = applicantPromoteEntryModel.checkStudentIsPromoted(txtStudentID.getText());

        if (txtStrandgroup.getText() == null || txtStrandgroup.getText() == "") {
            errorMessage += "Input Strandgroup!\n";
        }
        if(isPromoted){
            errorMessage += "Student is already promoted!\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            showMessage(false,"Invalid Fields","Please correct invalid fields",errorMessage);
            return false;
        }
    }
    
    @FXML
    private void actionSaveStudentPromotion(ActionEvent event) throws Exception {
        if (validateInput()) {
            ShStudStrand_ShStudlistModel applicantPromoteEntryModel = new ShStudStrand_ShStudlistModel();
            Boolean success;
            ShStudStrand shStudStrand = new ShStudStrand();
            ShStudlist shStudlist = new ShStudlist();
            ShStudlistId shStudlistId =  new ShStudlistId();
            
            String ss_id = cbSY.getSelectionModel().getSelectedItem()+"-"+ cbSem.getSelectionModel().getSelectedItem() +"-"+ txtStudentID.getText();
            shStudStrand.setSsId(ss_id);
            shStudStrand.setStudIdnum(selected_shApplicant.getStudIdnum());
            shStudStrand.setStrandCode(cbStrand.getSelectionModel().getSelectedItem().toString());
            shStudStrand.setStrandGroup(txtStrandgroup.getText().toUpperCase());
            shStudStrand.setSsYrLevel(Integer.parseInt(cbGradelevel.getSelectionModel().getSelectedItem()));
            shStudStrand.setSsStatus("NEW STUDENT");
            shStudStrand.setSsSy(cbSY.getSelectionModel().getSelectedItem());
            shStudStrand.setSsSem(Integer.parseInt(cbSem.getSelectionModel().getSelectedItem()));
            
            //--- set default values
            shStudStrand.setGpa(0.00);
            shStudStrand.setTotSubjWithgrade(0);
            shStudStrand.setSsIsreg(Byte.valueOf("2"));
            
            shStudlistId.setSem(Integer.parseInt(cbSem.getSelectionModel().getSelectedItem()));
            shStudlistId.setSy(cbSY.getSelectionModel().getSelectedItem());
            shStudlistId.setStudIdnum(selected_shApplicant.getStudIdnum());
            
            shStudlist.setId(shStudlistId);
            shStudlist.setStudFname(selected_shApplicant.getAppFname().trim().toUpperCase());
            shStudlist.setStudLname(selected_shApplicant.getAppLname().toUpperCase().trim());
            shStudlist.setStudMi(selected_shApplicant.getAppMi().trim().toUpperCase());
            shStudlist.setStudSuffix(selected_shApplicant.getAppSuffix().trim().toUpperCase());
            shStudlist.setAppNo(selected_shApplicant.getAppNo());
                 
            //--- set default values
            shStudlist.setStatus(1);
            shStudlist.setStatusIn(1);
            shStudlist.setStatusOut(0);
            shStudlist.setIsAssess(0);
            shStudlist.setConfirm(0);
            shStudlist.setRegConfirm(0);
            shStudlist.setEnrolStatus(1);
            shStudlist.setIsCardprinted(false);
   
            success =  applicantPromoteEntryModel.saveApplicantEnrollment(shStudStrand, shStudlist);
            
            if(success==true)
                this.showMessage(success, "Successful", "Student Promotion", "Student is promoted successfully");
            else
                this.showMessage(success, "Error", "Error", "Error occured");  
            
        }
    }
    
}
