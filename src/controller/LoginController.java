/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ShUsersModel;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    
    private ShUsersModel shUsersModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shUsersModel = new ShUsersModel();
    }    
    
    public Integer countUser(){
        int cnt_user = 0;
        
        cnt_user = shUsersModel.cntUser(txtUsername.getText().trim(), txtPassword.getText().trim());
        
        return cnt_user;
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
        

        if (txtUsername.getText() == ""  || txtUsername.getLength() == 0) {
            errorMessage += "Input username!\n";
        }
        
        if (txtPassword.getText() == ""  || txtPassword.getLength() == 0) {
            errorMessage += "Input password!\n";
        }
       


        if (errorMessage.length() == 0) {
            return true;
        } else {
            showMessage(false,"Invalid Fields","Please correct invalid fields",errorMessage);
            return false;
        }
    }
    
    @FXML
    public void actionLogin(ActionEvent event) throws Exception {  
        if(validateInput()){
            if(countUser() >= 1){
                Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Main");
                stage.setFullScreen(true);
//                stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
        //        stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
            }else{
                this.showMessage(false, "Login", "Login!", "Incorrect username and/or password.");
            }
        }
    }
    
}
