/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShCourseList;
import entity.ShInstructor;
import interfaces.ShCourseListInterface;
import java.math.BigDecimal;
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
import javafx.stage.Stage;
import model.ShCourseListModel;
import model.ShInstructorModel;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class CourseEntryController implements Initializable, ShCourseListInterface {

    @FXML
    private ComboBox cbSubjType;
    @FXML
    private TextField txtSubjCode;
    @FXML
    private TextField txtSubjDesc;
    @FXML
    private TextField txtSubjUnit;
    @FXML
    private CheckBox checkWithLab;
    @FXML
    private CheckBox checkNoGrade;
    @FXML
    private CheckBox checkClassBasis;
    @FXML
    private Button btnSave;
    
    private ShCourseListModel shCourseListModel;
    private ShCourseList selected_shCourseList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shCourseListModel = new ShCourseListModel();
        loadCourseTypeInCbox();
    }  
    
     private void loadCourseTypeInCbox() {
        ObservableList<String> listCourse = FXCollections.observableArrayList();
        listCourse.add("Core");
        listCourse.add("Specialized");
        listCourse.add("Applied");
        listCourse.add("Others");
        cbSubjType.setItems(listCourse);
        cbSubjType.getSelectionModel().select(0);
    }
    
    public void setCourse(ShCourseList shCourseList){
        this.selected_shCourseList = shCourseList;
        cbSubjType.getSelectionModel().select(selected_shCourseList.getCrsType());
        txtSubjCode.setText(selected_shCourseList.getCrsCode());
        txtSubjDesc.setText(selected_shCourseList.getCrsTitle());
        txtSubjUnit.setText(selected_shCourseList.getCrsUnit().toString());
     
        if(selected_shCourseList.getWithLab()==1)
            checkWithLab.setSelected(true);
        else
            checkWithLab.setSelected(false);
        
        
        if(selected_shCourseList.getIs_no_grade()==1)
            checkNoGrade.setSelected(true);
        else
            checkNoGrade.setSelected(false);
        
        if(selected_shCourseList.getIs_class_basis()==1)
            checkClassBasis.setSelected(true);
        else
            checkClassBasis.setSelected(false);
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

        if (txtSubjCode.getText() == null || txtSubjCode.getText().length() == 0) {
            errorMessage += "No Subject Code!\n";
        }
        
        if (txtSubjDesc.getText() == null || txtSubjDesc.getText().length() == 0) {
            errorMessage += "No Subject Description!\n";
        }
        
        if (txtSubjUnit.getText() == null || txtSubjUnit.getText().length() == 0) {
            errorMessage += "No Subject Unit!\n";
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
        ShCourseList shCourseList;


        if(this.selected_shCourseList == null)
            shCourseList = new ShCourseList();
        else
            shCourseList = this.selected_shCourseList;

        shCourseList.setCrsType(cbSubjType.getSelectionModel().getSelectedItem().toString());
        shCourseList.setCrsCode(txtSubjCode.getText());
        shCourseList.setCrsTitle(txtSubjDesc.getText());
        shCourseList.setCrsUnit(new BigDecimal(txtSubjUnit.getText()));
        
        if(checkWithLab.isSelected())
            shCourseList.setWithLab(1);
        else
             shCourseList.setWithLab(0);
        
        if(checkNoGrade.isSelected())
            shCourseList.setIs_no_grade(1);
        else
             shCourseList.setIs_no_grade(0);
        
        if(checkClassBasis.isSelected())
            shCourseList.setIs_class_basis(1);
        else
             shCourseList.setIs_class_basis(0);
        
        Boolean success = shCourseListModel.saveCourse(shCourseList);
            
        ((Stage) btnSave.getScene().getWindow()).close();

        if(success==true)
            this.showMessage(success, "Successful", "Course Saved!", "Course is saved successfully");
        else
            this.showMessage(success, "Error", "Error", "Error occured");
        
      
//        CourseListController con = new CourseListController();
        listCourse.clear();
//        con.loadCourses();
    }
}
