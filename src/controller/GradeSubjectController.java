/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomClassSections;
import entity.CustomStudentGrades;
import entity.CustomStudentGrades;
import entity.ShCClassStud;
import entity.ShClassInfo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.ShCClassStudModel;
import interfaces.ShCClassStudInterface;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class GradeSubjectController implements Initializable, ShCClassStudInterface {

    @FXML
    private TextField txtSY;
    @FXML
    private TextField txtSem;
    @FXML
    private TextField txtSubjectSection;
    @FXML
    private TextField txtSubject;
    @FXML
    private TableView<ShCClassStud> tblGradeStudents;
    @FXML
    private TableColumn<ShCClassStud, String> colNo;
    @FXML
    private TableColumn<ShCClassStud, String> colStudentID;
    @FXML
    private TableColumn<ShCClassStud, String> colStudentName;
    @FXML
    private TableColumn<ShCClassStud, String> colGradelevel;
    @FXML
    private TableColumn<ShCClassStud, String> colStrand;
    @FXML
    private TableColumn<ShCClassStud, String> colSection;
    @FXML
    private TableColumn<ShCClassStud, String> colGrade1;
    @FXML
    private TableColumn<ShCClassStud, String> colGrade2;
    @FXML
    private TableColumn<ShCClassStud, String> colFG;
    
    private ShCClassStudModel shCClassStudModel;
    private ShClassInfo selected_shClassInfo;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shCClassStudModel = new ShCClassStudModel();
    }  
    
    public void setClassSection(ShClassInfo shClassInfo){
        selected_shClassInfo = shClassInfo;
        txtSY.setText(selected_shClassInfo.getClaSy());
        txtSem.setText(selected_shClassInfo.getClaSem().toString());
        txtSubjectSection.setText(selected_shClassInfo.getClaSection());
        txtSubject.setText(selected_shClassInfo.getClaCrsCode());
        loadStudentGradesInTable();
    }
    
    private void loadStudentGradesInTable() {
        if (!listStudentGrades.isEmpty()) {
            listStudentGrades.clear();
        }
        listStudentGrades.addAll(shCClassStudModel.getResStudentGradesBySubjectSection(selected_shClassInfo.getClaSy(), selected_shClassInfo.getClaSem().toString(), 
                selected_shClassInfo.getClaCrsCode(), selected_shClassInfo.getClaSection()));

       
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCClassStud, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCClassStud, String> p) {
               return new ReadOnlyObjectWrapper(tblGradeStudents.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("csIdnum"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("stud_fullname"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("csYrLevel"));
        colStrand.setCellValueFactory(new PropertyValueFactory<>("strand_code"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("stud_section"));
        colGrade1.setCellValueFactory(new PropertyValueFactory<>("csMidGrade"));
        colGrade2.setCellValueFactory(new PropertyValueFactory<>("csSecQtr"));

        tblGradeStudents.setItems(listStudentGrades); 
    }
    
    
    
}
