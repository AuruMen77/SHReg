/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomEnrolledStudent;
import entity.ShApplicant;
import entity.ShCClassStud;
import entity.ShTermReg;
import interfaces.ShCClassStudInterface;
import interfaces.ShClassInfoInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.CustomEnrolledStudentModel;
import model.ShApplicantModel;
import model.ShCClassStudModel;
import model.ShStudStrand_ShStudlistModel;
import model.ShTermRegModel;
import model.StrandSectionModel;
import model.StrandsModel;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class StudentSubjectAssignmentManageController implements Initializable,ShCClassStudInterface {

    @FXML
    private TextField txtFullname;
    @FXML
    private TextField txtDOB;
    @FXML
    private TextField txtGender;
    @FXML
    private TextField txtCivilStatus;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtSY;
    @FXML
    private TextField txtSem;
    @FXML
    private TextField txtGradelevel;
    @FXML
    private TextField txtStrand;
    @FXML
    private TextField txtStrandgroup;
    @FXML
    private Button btnAddSubject;
    @FXML
    private Button btnEditSubjectsection;
    @FXML
    private Button btnEditSubject;
    @FXML
    private TableView<ShCClassStud> tblSubject;
    @FXML
    private TableColumn<ShCClassStud, String> colSubjNo;
    @FXML
    private TableColumn<ShCClassStud, String> colSubjCode;
    @FXML
    private TableColumn<ShCClassStud, String> colSubjDesc;
    @FXML
    private TableColumn<ShCClassStud, String> colSubjUnit;
    @FXML
    private TableColumn<ShCClassStud, String> colSubjSubjsection;
    @FXML
    private TableColumn<ShCClassStud, String> colRoomDayTime;
    
    
    private StrandSectionModel strandSectionModel;
    private CustomEnrolledStudentModel customEnrolledStudentModel;
    private ShCClassStudModel shCClassStudModel;
    private ShTermRegModel shTermRegModel;
    private ShApplicantModel applicantModel;
    
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
        shCClassStudModel = new ShCClassStudModel();
       
        shTermRegModel = new ShTermRegModel();
  
        currentEnrollment = shTermRegModel.getRowCurrentEnrollment();
        
    }  
    
    public void setStudentDetails(CustomEnrolledStudent selectedStudent) { 
        
        selectedCustomEnrolledStudent = selectedStudent;
        this.selectedShApplicant = applicantModel.getRowApplicantByStudID(selectedCustomEnrolledStudent.getStud_idnum());
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd, yyyy");
        
        
        txtFullname.setText(selectedShApplicant.getAppLname().trim() +", "+ selectedShApplicant.getAppFname().trim() +" "+ selectedShApplicant.getAppMiddlename().trim());
        txtDOB.setText(sdf.format(selectedShApplicant.getAppBdate()));
//        txtAddress.setText(selectedShApplicant.getAppAddress().trim()); 
        txtGender.setText(selectedShApplicant.getAppSex().trim());
        
        setStudentStrand();
        loadStudentSubjectsInTable();    
    }

    private void setStudentStrand(){
        txtSY.setText(selectedCustomEnrolledStudent.getSy());
        txtSem.setText(selectedCustomEnrolledStudent.getSem());
        
        
        txtGradelevel.setText(selectedCustomEnrolledStudent.getGrade_level());
        
        txtStrand.setText(selectedCustomEnrolledStudent.getStrand_code());
        txtStrandgroup.setText(selectedCustomEnrolledStudent.getStrand_group());
    }
    
    private void setEditableColumn() {
        tblSubject.setEditable(true);

        colSubjSubjsection.setCellFactory(TextFieldTableCell.forTableColumn());
        colSubjSubjsection.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCsSection(e.getNewValue());
        });
     }
    
    
    protected void loadStudentSubjectsInTable() {
        if (!listStudentSubjects.isEmpty()) {
            listStudentSubjects.clear();
        }
        listStudentSubjects.addAll(shCClassStudModel.getResStudentSubjectList(selectedCustomEnrolledStudent.getSy(), selectedCustomEnrolledStudent.getSem(), 
                selectedCustomEnrolledStudent.getGrade_level(),selectedCustomEnrolledStudent.getStud_idnum()));
                
        colSubjNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCClassStud, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCClassStud, String> p) {
               return new ReadOnlyObjectWrapper(tblSubject.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colSubjNo.setSortable(false);
        
        colSubjCode.setCellValueFactory(new PropertyValueFactory<>("csCrsCode"));
        colSubjDesc.setCellValueFactory(new PropertyValueFactory<>("subject_desc"));
        colSubjUnit.setCellValueFactory(new PropertyValueFactory<>("subject_unit"));
        colSubjSubjsection.setCellValueFactory(new PropertyValueFactory<>("csSection"));
        colRoomDayTime.setCellValueFactory(new PropertyValueFactory<>("subject_room_daytime_list"));
       
        
        tblSubject.setItems(listStudentSubjects); 
        setEditableColumn();
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
    public void actionAddSubject(ActionEvent event) throws Exception {  
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/StudentSubjectAssignmentManageEntry.fxml")));
        StudentSubjectAssignmentManageEntryController controller = new StudentSubjectAssignmentManageEntryController();
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Student Subjects Manage Entry");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        
        
        controller.setStudentDetails(selectedCustomEnrolledStudent);
        tblSubject.getSelectionModel().clearSelection();
        listStudentSubjects.clear();
        loadStudentSubjectsInTable();
    }
    
    @FXML
    public void actionEditSubject(ActionEvent event) throws Exception {
        ShCClassStud selectedShCClassStud = tblSubject.getSelectionModel().getSelectedItem();
        Boolean success = false;

        success = shCClassStudModel.saveShCClassStud(selectedShCClassStud);

        if(success==true)
            this.showMessage(success, "Successful", "Schedule Entry!", "Schedule is saved successfully");
        else
            this.showMessage(success, "Error", "Error", "Error occured");
    }
    
    @FXML
    public void actionDeleteSubject(ActionEvent event){
        ShCClassStud selectedShCClassStud = tblSubject.getSelectionModel().getSelectedItem();
         
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Boolean success = shCClassStudModel.deleteShCClassStud(selectedShCClassStud);
            
            if(success==true)
                this.showMessage(success, "Successful", "Subject Delete!", "Subject assigned deleted successfully");
            else
                this.showMessage(success, "Error", "Error", "Error occured");
        }
        
        listStudentSubjects.clear();
        loadStudentSubjectsInTable();
    }
    
}
