/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShDays;
import entity.ShDaysId;
import interfaces.ShDaysInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ShDaysModel;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class DayEntryController implements Initializable, ShDaysInterface {

    @FXML
    private TextField txtDay;
    @FXML
    private TextField txtDaycode;
    @FXML
    private Button btnSave;
    
    private ShDaysModel shDaysModel;
    
    private ShDays selected_shDays;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shDaysModel = new ShDaysModel();
    }    
    
    public void setDay(ShDays shDays){
        selected_shDays = shDays;
        
        txtDay.setText(selected_shDays.getDays());
        txtDaycode.setText(selected_shDays.getDaycode());
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

        if (txtDay.getText() == null || txtDay.getText().length() == 0) {
            errorMessage += "No valid day!\n";
        }
        
        if (txtDaycode.getText() == null || txtDaycode.getText().length() == 0) {
            errorMessage += "No valid daycode!\n";
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
//        ShDaysId shDaysId = new ShDaysId();
        ShDays shDays = selected_shDays;
        Boolean success = false;
        if(selected_shDays != null){
            shDays.setDays(txtDay.getText());
            shDays.setDaycode(txtDaycode.getText());  
            
            success = shDaysModel.saveDay(shDays);
        }
        else{
            success = shDaysModel.saveDayNew(txtDay.getText(), txtDaycode.getText());
        }

            
        ((Stage) btnSave.getScene().getWindow()).close();

        if(success==true)
            this.showMessage(success, "Successful", "Day Saved!", "Day is saved successfully");
        else
            this.showMessage(success, "Error", "Error", "Error occured");
        
      
    }
    
}
