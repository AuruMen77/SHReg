/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShTermReg;
import controller.Config;
import interfaces.StrandSectionInterface;
import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author CITS-Gio
 */
public class UploadStudents implements Initializable, StrandSectionInterface {

    @FXML
    private Button btnUploadStudents;
    @FXML
    private ComboBox activeSY;
    @FXML
    private ComboBox activeSem;
    @FXML
    private ProgressBar uploadBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        loadSYinCbox();
        loadSemInCbox();
    }

    public void actionUpload(ActionEvent event) {
        boolean confirmed = showConfirmationDialog("Confirmation", "Please ensure that the selected School Year and Semester is correct.", "Do you wish to proceed?");
        if (confirmed) {
            String sy = (String) activeSY.getValue();
            String sem = (String) activeSem.getValue();

            // Create a Task to run the database query
            Task<Void> databaseTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    // Place your query execution logic here
                    Connection connection = null;
                    PreparedStatement statement = null;

                    try {
                        connection = DriverManager.getConnection(Config.connection_url, Config.DATABASE_USER_ID, Config.DATABASE_PASSWORD);
                        connection.setAutoCommit(false);
                        String sql = "INSERT INTO adzu_sh_stud_class (stud_id, course_code, section, strand_group, yr_level, sem, sy, mid_grade, sec_qtr_grade, fin_grade, remarks, strand_code, stud_name)\n"
                                + "SELECT cs_idnum, cs_crs_code, cs_section, strand_group, cs_yr_level, sem, sy,\n"
                                + "cs_mid_grade, cs_sec_qtr, cs_fin_grade, cs_remarks, strand_code,\n"
                                + "CONCAT (TRIM(sl.stud_lname), ', ', TRIM(sl.stud_fname),' ', stud_mi)  AS stud_name\n"
                                + "FROM `sh_c_class_stud` ccs\n"
                                + "LEFT JOIN sh_stud_strand ss ON (ss.stud_idnum = ccs.cs_idnum)\n"
                                + "LEFT JOIN sh_studlist AS sl ON (sl.stud_idnum = ccs.cs_idnum)\n"
                                + "WHERE cs_sy = '" + sy + "'\n"
                                + "AND cs_sem = '" + sem + "'\n"
                                + "AND ss_sy = '" + sy + "'\n"
                                + "AND ss_sem = '" + sem + "'\n"
                                + "AND sl.sy = '" + sy + "'\n"
                                + "AND sl.sem = '" + sem + "';";
                        statement = connection.prepareStatement(sql);
                        statement.executeUpdate();
                        connection.commit();
                    } catch (SQLException e) {
                        e.printStackTrace();

                        try {
                            if (connection != null) {
                                connection.rollback();
                            }
                        } catch (SQLException rollbackException) {
                            rollbackException.printStackTrace();
                        }
                    } finally {
                        try {
                            if (statement != null) {
                                statement.close();
                            }
                            if (connection != null) {
                                connection.setAutoCommit(true);
                                connection.close();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }
            };

            // Bind the progress bar to the task's progress property
            uploadBar.progressProperty().bind(databaseTask.progressProperty());

            // Show the progress bar
            uploadBar.setVisible(true);

            // Start the task in a background thread
            Thread thread = new Thread(databaseTask);
            thread.setDaemon(true); // Daemon threads automatically terminate when the program exits
            thread.start();

            // Handle task completion
            databaseTask.stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    // Task completed successfully
                    uploadBar.setVisible(false); // Hide the progress bar
                }
            });
        } else {
            // User clicked "No," do nothing or handle the cancellation
        }
    }

    private boolean showConfirmationDialog(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        // Add custom buttons (in this case, "Yes" and "No")
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        // Show the dialog and wait for a user response
        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == yesButton;
    }

    private void loadSYinCbox() {
//        System.out.println("checkpoint loadsy");
        ObservableList<String> listSYforCbox = FXCollections.observableArrayList();
        Year currentYear = Year.now();
        int yearNow = currentYear.getValue();
        int yearNext = yearNow + 1;
        String currSY = yearNow + "-" + yearNext;

        for (int i = yearNow; i >= 2016; i--) {
            String sy_val = i + "-" + (i + 1);
            listSYforCbox.add(sy_val);
        }

        activeSY.getItems().addAll(listSYforCbox);
        activeSY.setValue(currSY);
    }

    private void loadSemInCbox() {
//        System.out.println("checkpoint loadsem");
        ObservableList<String> listSem = FXCollections.observableArrayList();
        listSem.add("1");
        listSem.add("2");
        listSem.add("3");
        activeSem.getItems().addAll(listSem);
        activeSem.setValue("1");
    }
}
