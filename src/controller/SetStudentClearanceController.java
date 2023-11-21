/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import entity.Settings;
import entity.ShTermReg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ShTermRegModel;
import model.ClearanceSysemModel;
import entity.ClearanceSysem;
import entity.ClearanceOfficeSysem;

/**
 * FXML Controller class
 *
 * @author CITS-TEST
 */
public class SetStudentClearanceController implements Initializable {

    @FXML
    private Button btnStudentClearance;
    
    @FXML
    private ComboBox<String> cbSY;

    @FXML
    private ComboBox<String> cbSem;

    private ShTermReg currentEnrollment = new ShTermReg();

    private ClearanceSysem ClearanceSysem = new ClearanceSysem();

    private ClearanceSysemModel ClearanceSysemModel = new ClearanceSysemModel();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadSYinCbox();
        loadSemInCbox();
    }

    private void setStudentClearanceToggle() {

        int isUpdated = 0;

        String selectedSy = cbSY.getSelectionModel().getSelectedItem();
        Integer selectedSem = Integer.parseInt(cbSem.getSelectionModel().getSelectedItem());

        isUpdated = ClearanceSysemModel.setSySemActive(selectedSy, selectedSem);
        
        // Show an information alert
        //Successful update
        if (isUpdated > 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Clearance for School Year " + selectedSy + " Semester " + selectedSem + " has been opened!");
            alert.showAndWait();
        //Error, activated already active
        } else if (isUpdated == -1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error: Already active");
            alert.setHeaderText(null);
            alert.setContentText("Clearance for this SY and SEM is already active");
            alert.showAndWait();
        //Error: selection not in database
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error: No record found");
            alert.setHeaderText(null);
            alert.setContentText("SY and SEM not found in database. No changes were made");
            alert.showAndWait();
        }
    }

    @FXML
    private void actionSetStudentClearanceToggle(ActionEvent event) {
        setStudentClearanceToggle();
    }

    private void loadSemInCbox() {
        ObservableList<String> listSem = FXCollections.observableArrayList();
        listSem.add("1");
        listSem.add("2");
        cbSem.setItems(listSem);
        //  cbSem.getSelectionModel().select(currentEnrollment.getSemReg());
    }

    private void loadSYinCbox() {
        ObservableList<String> listSYforCbox = FXCollections.observableArrayList();

        String sy_val = "";
        for (int i = 2023; i <= 2030; i++) {
            sy_val = i + "-" + (i + 1);
            listSYforCbox.add(sy_val);
        }

        cbSY.setItems(listSYforCbox);
        //cbSY.getSelectionModel().select(currentEnrollment.getSyReg());

    }
}
