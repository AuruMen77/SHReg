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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import entity.Settings;

/**
 * FXML Controller class
 *
 * @author CITS-TEST
 */
public class GradeViewController implements Initializable {
    
    @FXML
    private Button btnGradeView;
      @FXML
    private Label lblGradeView;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

            // Load the GradeView entity (assuming you have an ID for it)
            Settings gradeView = session.get(Settings.class, 0);

            if (gradeView.getStatus()== 0) {
                btnGradeView.setText("Enable Grade Viewing");
                lblGradeView.setText("Grade Viewing in the Portal is Disabled");
                      gradeView.setStatus(0);
            } else {
                 lblGradeView.setText("Grade Viewing in the Portal is Enabled");
                btnGradeView.setText("Disable Grade Viewing");
                      gradeView.setStatus(1);
            }

    }    

    private void setGradeViewToggle() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        
                try {
            tx = session.beginTransaction();

            // Load the GradeView entity (assuming you have an ID for it)
            Settings gradeView = session.get(Settings.class, 0);

            if (gradeView.getStatus()== 0) {
           
                // Set the "status" to 1 to enable
                gradeView.setStatus(1);
               btnGradeView.setText("Disable Grade Viewing");
              lblGradeView.setText("Grade Viewing in the Portal is Enabled");
            } else {
         
                // Set the "status" to 0 to disable
                gradeView.setStatus(0);
               btnGradeView.setText("Enable Grade Viewing");
               lblGradeView.setText("Grade Viewing in the Portal is Disabled");
            }

            // Persist the changes to the database
            session.update(gradeView);
            
            tx.commit();
            
              // Show an information alert
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Grade Viewing settings updated!");
            alert.showAndWait();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            // Handle any exceptions
        } finally {
            session.close();
            sessionFactory.close();
        }
     }
     
    
    @FXML
    private void actionSetGradeViewToggle(ActionEvent event) {
        setGradeViewToggle();
    }
    
    
}
