/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShInstructor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ShInstructorModel;



/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class InstructorEntryController implements Initializable {

    @FXML
    private TextField txtEmpID;
    @FXML
    private TextField txtFullname;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnSave;
    
    private ShInstructorModel shInstructorModel;
    private ShInstructor selected_shInstructor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shInstructorModel = new ShInstructorModel();
    }    
    
    public void setInstructor(ShInstructor shInstructor){
        this.selected_shInstructor = shInstructor;
        txtEmpID.setText(selected_shInstructor.getInstructorId());
        txtFullname.setText(selected_shInstructor.getInstructorName());
        txtUsername.setText(selected_shInstructor.getInstructorUname());
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

        if (txtEmpID.getText() == null || txtEmpID.getText().length() == 0) {
            errorMessage += "No ID Number!\n";
        }
        
        if (txtFullname.getText() == null || txtFullname.getText().length() == 0) {
            errorMessage += "No valid fullname!\n";
        }
        
        if (txtUsername.getText() == null || txtUsername.getText().length() == 0) {
            errorMessage += "No valid username!\n";
        }
        
        if (txtPassword.getText() == null || txtPassword.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            showMessage(false,"Invalid Fields","Please correct invalid fields",errorMessage);
            return false;
        }
    }
    
    
    @FXML
    private void actionSave(ActionEvent event){
        ShInstructor shInstructor;


        if(selected_shInstructor == null)
            shInstructor = new ShInstructor();
        else
            shInstructor = this.selected_shInstructor;
        
       

        shInstructor.setInstructorId(txtEmpID.getText());
        shInstructor.setInstructorName(txtFullname.getText());
        shInstructor.setInstructorUname(txtUsername.getText());
        
        Boolean success = shInstructorModel.saveInstructor(shInstructor);
            
        ((Stage) btnSave.getScene().getWindow()).close();

        if(success==true)
            this.showMessage(success, "Successful", "Instructor Saved!", "Instructor is saved successfully");
        else
            this.showMessage(success, "Error", "Error", "Error occured");
        
      
    }
    
    
    
}
