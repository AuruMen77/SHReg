/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShTermReg;
import entity.StrandSection;
import interfaces.StrandSectionInterface;
import java.awt.Image;
import static java.awt.SystemColor.window;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ShTermRegModel;
import model.StrandSectionModel;
import model.StrandsModel;

/**
 * FXML Controller class
 *
 * @author CITS-Chie
 */
public class ManageTermController implements Initializable, StrandSectionInterface {
    @FXML
    private Button btnNewEnrollmentTerm;
    @FXML
    private Button btnPreviousQuarter;    
    @FXML
    private Button btnNextQuarter;
    @FXML
    private Label lblSchoolYear;
    @FXML
    private Label lblSemester;
    @FXML
    private Label lblQuarter;
    @FXML
    private Label lblEnrollment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO       
        lblEnrollment.setText("Hello World");
        
    }
    
    public void actionNewEnrollmentTerm(ActionEvent event) throws Exception{    
        /*
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Confirm new enrollment term");
        ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.setContentText("Are you sure about creating and opening the new enrollment term? This will do: \n1. This \n2. That");
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.setResultConverter(ButtonType::getText);

        Optional<String> result = dialog.showAndWait();
        System.out.println(result);
        //dialog.showAndWait();
        */
        
        ButtonType OK = new ButtonType("OK", ButtonData.OK_DONE);
        ButtonType CANCEL = new ButtonType("CANCEL", ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(AlertType.NONE, "Promote pawn to:", OK, CANCEL);
        a.setTitle("Confirm");
        a.setResizable(true);
        a.setContentText("Are you sure about creating and opening the new enrollment term? This will do: \n1. This \n2. That");
        a.showAndWait().ifPresent(response -> {
        if (response == OK) {
            System.out.println("OK is clicked");
        } else if (response == CANCEL) {
            System.out.println("CANCEL is clicked");
        }
        });
    }
    
    public void actionPreviousQuarter(ActionEvent event) throws Exception {
        switch(lblQuarter.getText()) {
            case "1st Quarter":
                // Do Nothing
                break;
            case "2nd Quarter":
                lblQuarter.setText("1st Quarter");
                break;
            case "3rd Quarter":
                lblQuarter.setText("2nd Quarter");
                break;
            case "4th Quarter":
                lblQuarter.setText("3rd Quarter");
                break;
            default:
                break;                
        }
    }
    
    public void actionNextQuarter(ActionEvent event) throws Exception {
        switch(lblQuarter.getText()) {
            case "1st Quarter":
                lblQuarter.setText("2nd Quarter");
                break;
            case "2nd Quarter":
                lblQuarter.setText("3rd Quarter");
                break;
            case "3rd Quarter":
                lblQuarter.setText("4th Quarter");
                break;
            case "4th Quarter":
                break;
            default:
                break;                
        }        
    }
}
