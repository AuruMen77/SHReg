/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomEnrolledStudent;
import entity.CustomStudentSubject;
import entity.ShClassInfo;
import entity.ShTermReg;
import interfaces.CustomStudentSubjectInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ShTermRegModel;
import model.StrandsModel;
import model.ShCourseListModel;
import interfaces.ShClassInfoInterface;
import model.ShClassInfoModel;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class StudentSubjectAssignmentEntrySelectScheduleController implements Initializable, ShClassInfoInterface, CustomStudentSubjectInterface {

    @FXML
    private TextField txtSY;
    @FXML
    private TextField txtSem;
    @FXML
    private ComboBox cbGradelevel;
    @FXML
    private ComboBox cbStrand;
    @FXML
    private TextField txtStrandgroup;
    @FXML
    private TextField txtSubject;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnSelectSched;
    @FXML
    private TableView<ShClassInfo> tblSubject;
    @FXML
    private TableColumn<ShClassInfo, String> colNo;
    @FXML
    private TableColumn<ShClassInfo, String> colSubjCode;
    @FXML
    private TableColumn<ShClassInfo, String> colStrandcode;
     @FXML
    private TableColumn<ShClassInfo, String> colStrandgroup;
    @FXML
    private TableColumn<ShClassInfo, String> colGradelevel;
    @FXML
    private TableColumn<ShClassInfo, String> colUnit;
    @FXML
    private TableColumn<ShClassInfo, String> colSubjSection;
    @FXML
    private TableColumn<ShClassInfo, String> colRoom;
    @FXML
    private TableColumn<ShClassInfo, String> colDay;
    @FXML
    private TableColumn<ShClassInfo, String> colTImeStart;
    @FXML
    private TableColumn<ShClassInfo, String> colTimeEnd;
    
    private CustomEnrolledStudent selectedCustomEnrolledStudent;
    
    private ShClassInfoModel shClassInfoModel;
    private StrandsModel strandsModel;
    private ShTermRegModel flagsModel;
    private ShCourseListModel subjectModel;
    
    private ShTermReg currentEnrollment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shClassInfoModel = new ShClassInfoModel();
        strandsModel = new StrandsModel();
        flagsModel =  new ShTermRegModel();
        subjectModel = new ShCourseListModel();
        
        currentEnrollment=flagsModel.getRowCurrentEnrollment();
        
        loadGradelevelInCbox();
        loadStrandsInCbox();  
    } 
    
    public void setCustomEnrolledStudent(CustomEnrolledStudent customEnrolledStudent){
        this.selectedCustomEnrolledStudent = customEnrolledStudent;
    }
    
    public void setHeaderDetails(CustomEnrolledStudent customEnrolledStudent){
        try{
            selectedCustomEnrolledStudent = customEnrolledStudent;
            txtSY.setText(customEnrolledStudent.getSy());
            txtSem.setText(customEnrolledStudent.getSem());
            cbGradelevel.getSelectionModel().select(customEnrolledStudent.getGrade_level());
            cbStrand.getSelectionModel().select(customEnrolledStudent.getStrand_code());
            txtStrandgroup.setText(customEnrolledStudent.getStrand_group().toUpperCase().trim());
            loadSubjectSchedulesInTable();
        }catch(Exception ex){
            ex.printStackTrace();   
        }
    }
    
    private void loadGradelevelInCbox() {
        ObservableList<String> listGradelevel = FXCollections.observableArrayList();
        listGradelevel.add("all");
        listGradelevel.add("11");
        listGradelevel.add("12");
        cbGradelevel.setItems(listGradelevel);
    }
    
    private void loadStrandsInCbox(){
        ObservableList<String> listStrands = FXCollections.observableArrayList(strandsModel.getStrandsForCombobox());
        cbStrand.setItems(listStrands);
    }
    
    private void loadSubjectSchedulesInTable() {
        if (!listSchedule.isEmpty()) {
            listSchedule.clear();
        }

        listSchedule.addAll(shClassInfoModel.getResSubjectSchedules(txtSY.getText(),
            txtSem.getText(), cbGradelevel.getSelectionModel().getSelectedItem().toString(), 
            cbStrand.getSelectionModel().getSelectedItem().toString(), 
            txtStrandgroup.getText(), txtSubject.getText()));
        
        
        colNo.setSortable(false);

        colNo.setCellValueFactory(     
            new PropertyValueFactory<>("customSelect")
        );      


        colSubjCode.setCellValueFactory(new PropertyValueFactory<>("claCrsCode"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("claYrLevel"));
        colStrandcode.setCellValueFactory(new PropertyValueFactory<>("strandcode"));
        colStrandgroup.setCellValueFactory(new PropertyValueFactory<>("strandgroup"));
        colSubjSection.setCellValueFactory(new PropertyValueFactory<>("claSection"));
        colRoom.setCellValueFactory(new PropertyValueFactory<>("claRoom"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("claDay"));
        colTImeStart.setCellValueFactory(new PropertyValueFactory<>("claTStartdesc"));
        colTimeEnd.setCellValueFactory(new PropertyValueFactory<>("claTEnddesc"));
        
        tblSubject.setItems(listSchedule); 

    }

    @FXML
    private void actionSearch(ActionEvent event) {
        loadSubjectSchedulesInTable();
    }
    
    @FXML
    public void actionSelectSchedule(ActionEvent event) throws Exception {  
        String subject_desc = "";
        String subject_unit = "0";
        for(ShClassInfo row : listSchedule)
        {
           if(row.getCustomSelect().isSelected())
           {

             CustomStudentSubject customStudentSubject =  new CustomStudentSubject();
             subject_desc = subjectModel.getCourseByCode(row.getClaCrsCode()).getCrsTitle();
             subject_unit = subjectModel.getCourseByCode(row.getClaCrsCode()).getCrsUnit().toString();

             customStudentSubject.setClass_id(row.getClassid());
             customStudentSubject.setStud_idnum(selectedCustomEnrolledStudent.getStud_idnum());
             customStudentSubject.setSy(row.getClaSy());
             customStudentSubject.setSem(row.getClaSem().toString());
             customStudentSubject.setGrade_level(row.getClaYrLevel().toString());
             customStudentSubject.setStrand_code(row.getStrandcode());
             customStudentSubject.setStrand_group(row.getStrandgroup());
             customStudentSubject.setSubject_section(row.getClaSection());
             customStudentSubject.setSubject_desc(subject_desc);
             customStudentSubject.setSubject_code(row.getClaCrsCode());
             customStudentSubject.setSubject_unit(subject_unit);
             customStudentSubject.setRoom(row.getClaRoom());
             customStudentSubject.setDay_assigned(row.getClaDay());
             customStudentSubject.setTime_start(row.getClaTStart());
             customStudentSubject.setTime_end(row.getClaTEnd());

             listStudentSubjects.add(customStudentSubject);
           }
            
        }
        
       ((Stage) btnSelectSched.getScene().getWindow()).close();
    }
    
}
