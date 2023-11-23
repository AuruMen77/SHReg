/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomEnrolledStudent;
import entity.ShApplicant;
import entity.ShCClassStud;
import entity.ShClassInfo;
import entity.ShTermReg;
import entity.StrandSection;
import interfaces.ShCClassStudInterface;
import interfaces.ShClassInfoInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.CustomEnrolledStudentModel;
import model.ShApplicantModel;
import model.ShCClassStudModel;
import model.ShClassInfoModel;
import model.ShSectionsModel;
import model.ShTermRegModel;
import model.StrandSectionModel;
/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class StudentSubjectAssignmentManageEntryController implements Initializable,ShClassInfoInterface,ShCClassStudInterface {


    @FXML
    private Button btnSave;
    @FXML
    private TableView<ShClassInfo> tblSubject;
    @FXML
    private TableColumn<ShClassInfo, String> colCurrDtlID;
    @FXML
    private TableColumn<ShClassInfo, String> colSY;
    @FXML
    private TableColumn<ShClassInfo, String> colSem;
    @FXML
    private TableColumn<ShClassInfo, String> colGradelevel;
    @FXML
    private TableColumn<ShClassInfo, String> colSubjCode;
    @FXML
    private TableColumn<ShClassInfo, String> colStrand;
    @FXML
    private TableColumn<ShClassInfo, String> colSection;
    @FXML
    private TableColumn<ShClassInfo, String> colNo;
    @FXML
    private TableColumn<ShClassInfo, String> colSubjsection;
    @FXML
    private TableColumn<ShClassInfo, String> colRoomDayTime;
    @FXML
    private TextField txtSY;
    @FXML
    private TextField txtSem;
    @FXML
    private TextField txtStrand;
    @FXML
    private ComboBox cbGradelevel;
    @FXML
    private ComboBox cbSection;
    @FXML
    private Button btnSearch;
   
    
    
    private StrandSectionModel strandSectionModel;
    private CustomEnrolledStudentModel customEnrolledStudentModel;
    private ShCClassStudModel shCClassStudModelModel;
    private ShClassInfoModel shClassInfoModel;
    private ShTermRegModel shTermRegModel;
    private ShApplicantModel applicantModel;
    private StrandSectionModel sectionsModel;
    
    private ShTermReg currentEnrollment;
    private ShApplicant selectedShApplicant;
    private CustomEnrolledStudent selectedCustomEnrolledStudent;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        strandSectionModel = new StrandSectionModel();
        applicantModel =  new ShApplicantModel();
        shCClassStudModelModel = new ShCClassStudModel();
        shClassInfoModel = new ShClassInfoModel();
        shTermRegModel = new ShTermRegModel();
        sectionsModel = new StrandSectionModel();

        currentEnrollment = shTermRegModel.getRowCurrentEnrollment();
        
        cbGradelevel.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
    }  
    
    private void loadGradelevelInCbox() {
        ObservableList<String> listGradelevel = FXCollections.observableArrayList();
        listGradelevel.add("all");
        listGradelevel.add("11");
        listGradelevel.add("12");
        cbGradelevel.setItems(listGradelevel);
        cbGradelevel.getSelectionModel().select("all");
    }
    
     private void loadSectionsInCbox(){ 
        ObservableList<String> listSections = FXCollections.observableArrayList(sectionsModel.getSectionsForCombobox(currentEnrollment.getSyReg(), 
                currentEnrollment.getSemReg().toString(),cbGradelevel.getSelectionModel().getSelectedItem().toString(), 
                selectedCustomEnrolledStudent.getStrand_code()));
        listSections.add("all");
        cbSection.setItems(listSections);
        cbSection.getSelectionModel().select("all");
    }
    
    public void setStudentDetails(CustomEnrolledStudent selectedStudent) { 
        selectedCustomEnrolledStudent = selectedStudent;
        setStudentStrand();
        loadGradelevelInCbox();
        cbGradelevel.getSelectionModel().select(selectedCustomEnrolledStudent.getGrade_level());
        
        loadSectionsInCbox();
        
        cbSection.getSelectionModel().select(selectedCustomEnrolledStudent.getSection());
        
        loadStudentNotEnrolledSubjectsInTable();  
        
    }

    private void setStudentStrand(){
        txtSY.setText(currentEnrollment.getSyReg());
        txtSem.setText(currentEnrollment.getSemReg().toString());
        txtStrand.setText(selectedCustomEnrolledStudent.getStrand_code());
        cbGradelevel.getSelectionModel().select(selectedCustomEnrolledStudent.getGrade_level());
        cbSection.getSelectionModel().select(selectedCustomEnrolledStudent.getSection());  
    }
    
    private void loadStudentNotEnrolledSubjectsInTable() {
        if (!listClassSubjects.isEmpty()) {
            listClassSubjects.clear();
        }
        
        String selectedStrandgroup = new StrandSectionModel().getRowSection(txtSY.getText(), txtSem.getText(), 
                cbGradelevel.getSelectionModel().getSelectedItem().toString(), selectedCustomEnrolledStudent.getStrand_code(), cbSection.getSelectionModel().getSelectedItem().toString()).getStrandgroup();
        
        listClassSubjects.addAll(shClassInfoModel.getResStudentNotEnrolledSubjectList(txtSY.getText(),  txtSem.getText(), 
                cbGradelevel.getSelectionModel().getSelectedItem().toString(),txtStrand.getText(),
                selectedStrandgroup,selectedCustomEnrolledStudent.getStud_idnum()));
                
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShClassInfo, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShClassInfo, String> p) {
               return new ReadOnlyObjectWrapper(tblSubject.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        colNo.setCellValueFactory(     
            new PropertyValueFactory<>("customSelect")
        );      

        
        colSY.setCellValueFactory(new PropertyValueFactory<>("claSy"));
        colSem.setCellValueFactory(new PropertyValueFactory<>("claSem"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("claYrLevel"));
        colSubjCode.setCellValueFactory(new PropertyValueFactory<>("claCrsCode"));
        colSubjsection.setCellValueFactory(new PropertyValueFactory<>("claSection"));
        colStrand.setCellValueFactory(new PropertyValueFactory<>("strandcode"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("class_section"));
        colRoomDayTime.setCellValueFactory(new PropertyValueFactory<>("subject_room_daytime_list"));

        tblSubject.setItems(listClassSubjects); 
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
    
    @FXML
    private void actionSearch(ActionEvent event) {
        loadStudentNotEnrolledSubjectsInTable();
    }
    
    @FXML
    public void actionSaveSubjects(ActionEvent event) throws Exception {  
        String cclassId = "";
        boolean success;
        ObservableList<ShCClassStud> listStudentSubject = FXCollections.observableArrayList();
        for(ShClassInfo row : listClassSubjects)
        {
           if(row.getCustomSelect().isSelected())
           {
                cclassId = row.getClassid() + "_" + this.selectedCustomEnrolledStudent.getStud_idnum();

                ShCClassStud save_shCClassStud = new ShCClassStud();
                save_shCClassStud.setCclassId(cclassId);
                save_shCClassStud.setCsCrsCode(row.getClaCrsCode());
                save_shCClassStud.setCsIdnum(selectedCustomEnrolledStudent.getStud_idnum());
                save_shCClassStud.setCsSection(row.getClaSection());
                save_shCClassStud.setCsSy(txtSY.getText());
                save_shCClassStud.setCsSem(Integer.parseInt(txtSem.getText()));
                save_shCClassStud.setCsStrandgroup(row.getStrandgroup());
                save_shCClassStud.setCsYrLevel(Integer.parseInt(row.getClaYrLevel().toString()));
                
                listStudentSubject.add(save_shCClassStud);

           }
            
        }
        
        success = shCClassStudModelModel.saveStudentIndividualSubject(txtSY.getText(), txtSem.getText(), 
                selectedCustomEnrolledStudent.getStud_idnum(),listStudentSubject);
        
        System.out.println("txtSY: " + txtSY.getText());
        System.out.println("txtSem: " + txtSem.getText());
        System.out.println("Student ID: " + selectedCustomEnrolledStudent.getStud_idnum());
        System.out.println("List of Subjects: " + listStudentSubject);
        
        if(success==true)
            this.showMessage(success, "Successful", "Add Individual Subject!", "Individual Subject is saved successfully");
        else
            this.showMessage(success, "Error", "Error", "Error occured");
        
       ((Stage) btnSave.getScene().getWindow()).close();
    }
    
    
    
    
    
    
}
