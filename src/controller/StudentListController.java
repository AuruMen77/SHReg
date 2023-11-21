/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.AppResults;
import entity.CustomEnrolledStudent;
import entity.ShTermReg;
import interfaces.CustomEnrolledStudentInterface;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.ShTermRegModel;
import model.StrandSectionModel;
import model.StrandsModel;
import model.CustomEnrolledStudentModel;
import model.SaLedgerHeader_SaLedgerModel;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class StudentListController implements Initializable,CustomEnrolledStudentInterface {

    @FXML
    private ComboBox cbSY;
    @FXML
    private ComboBox cbSem;
    @FXML
    private ComboBox cbGradelevel;
    @FXML
    private ComboBox cbStrand;
    @FXML
    private ComboBox cbSection;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnExport;
    @FXML
    private Button btnDrop;
    @FXML
    private TableView<CustomEnrolledStudent> tblStudent;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colNo;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colStudentID;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colStudname;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colSY;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colSem;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colGradelevel;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colStrand;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colSection;
    
    private StrandsModel strandsModel;
    private CustomEnrolledStudentModel studentListModel;
    private StrandSectionModel sectionsModel;
    private ShTermRegModel flagsModel;
    
    private ShTermReg currentEnrollment;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        strandsModel = new StrandsModel();
        studentListModel = new CustomEnrolledStudentModel();
        sectionsModel =  new StrandSectionModel();
        flagsModel =  new ShTermRegModel();
        
        currentEnrollment=flagsModel.getRowCurrentEnrollment();
        
        loadSYinCbox();
        loadSemInCbox();
        loadGradelevelInCbox();
        loadStrandsInCbox();
        loadSectionsInCbox();
        loadStudentsInTable();

    }  
    
    private void showMessage(boolean success, String title, String header, String content){        
        // <editor-fold defaultstate="collapsed" desc="Code">
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
        //</editor-fold>        
    }
    
    private void loadSYinCbox() {
        ObservableList<String> listSYforCbox = FXCollections.observableArrayList();
        
        String sy_val = "";
        for(int i=2016; i<=2050; i++){
            sy_val = i + "-" + (i+1);
            listSYforCbox.add(sy_val);
        }
        
        cbSY.setItems(listSYforCbox);
        cbSY.getSelectionModel().select(currentEnrollment.getSyReg());

    }
    
    private void loadSemInCbox() {
        ObservableList<String> listSem = FXCollections.observableArrayList();
        listSem.add("1");
        listSem.add("2");
        listSem.add("3");
        cbSem.setItems(listSem);
        cbSem.getSelectionModel().select(currentEnrollment.getSemReg());
    }
    
    private void loadGradelevelInCbox() {
        ObservableList<String> listGradelevel = FXCollections.observableArrayList();
        listGradelevel.add("all");
        listGradelevel.add("11");
        listGradelevel.add("12");
        cbGradelevel.setItems(listGradelevel);
        cbGradelevel.getSelectionModel().select("all");
    }
    
    private void loadStrandsInCbox(){
        ObservableList<String> listStrands = FXCollections.observableArrayList(strandsModel.getStrandsForCombobox());
        listStrands.add("all");
        cbStrand.setItems(listStrands);
        cbStrand.getSelectionModel().select("all");
    }
    
     private void loadSectionsInCbox(){
        ObservableList<String> listSections = FXCollections.observableArrayList(sectionsModel.getSectionsForCombobox(cbSY.getSelectionModel().getSelectedItem().toString(), 
                cbSem.getSelectionModel().getSelectedItem().toString(),cbGradelevel.getSelectionModel().getSelectedItem().toString(), 
                cbStrand.getSelectionModel().getSelectedItem().toString()));
        listSections.add("all");
        cbSection.setItems(listSections);
        cbSection.getSelectionModel().select("all");
    }
    
    private void loadStudentsInTable() {
        if (!listEnrolledStudent.isEmpty()) {
            listEnrolledStudent.clear();
        }
        listEnrolledStudent.addAll(studentListModel.getResEnrolledStudentWithSearch(currentEnrollment.getSyReg(), currentEnrollment.getSemReg().toString(), "all", "all", "all", ""));
       
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CustomEnrolledStudent, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<CustomEnrolledStudent, String> p) {
               return new ReadOnlyObjectWrapper(tblStudent.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("stud_idnum"));
        colStudname.setCellValueFactory(new PropertyValueFactory<>("stud_fullname"));
        colSY.setCellValueFactory(new PropertyValueFactory<>("sy"));
        colSem.setCellValueFactory(new PropertyValueFactory<>("sem"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("grade_level"));
        colStrand.setCellValueFactory(new PropertyValueFactory<>("strand_code"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));

        tblStudent.setItems(listEnrolledStudent); 
    }
    
    @FXML
    public void updateSectionList(ActionEvent event) throws Exception {
        loadSectionsInCbox();        
    }
    
    @FXML
    public void filterDataOnClick(ActionEvent event) throws Exception {
        if (!listEnrolledStudent.isEmpty()) {
            listEnrolledStudent.clear();  
        }
        listEnrolledStudent.addAll(studentListModel.getResEnrolledStudentWithSearch(cbSY.getSelectionModel().getSelectedItem().toString(), cbSem.getSelectionModel().getSelectedItem().toString(), 
                cbGradelevel.getSelectionModel().getSelectedItem().toString(), cbStrand.getSelectionModel().getSelectedItem().toString(), 
                cbSection.getSelectionModel().getSelectedItem().toString(), txtSearch.getText()));
       
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CustomEnrolledStudent, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<CustomEnrolledStudent, String> p) {
               return new ReadOnlyObjectWrapper(tblStudent.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("stud_idnum"));
        colStudname.setCellValueFactory(new PropertyValueFactory<>("stud_fullname"));
        colSY.setCellValueFactory(new PropertyValueFactory<>("sy"));
        colSem.setCellValueFactory(new PropertyValueFactory<>("sem"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("grade_level"));
        colStrand.setCellValueFactory(new PropertyValueFactory<>("strand_code"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));

        tblStudent.setItems(listEnrolledStudent); 
        //loadSectionsInCbox();
    }
    
    @FXML
    public void actionEnrollStudent(ActionEvent event) throws Exception {  
        CustomEnrolledStudent selectedStudent = tblStudent.getSelectionModel().getSelectedItem();
        selectedStudent = studentListModel.getRowEnrolledStudent(selectedStudent.getSy(),selectedStudent.getSem(),selectedStudent.getStud_idnum());
        
        
        
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/StudentSubjectAssignmentManage.fxml")));
        StudentSubjectAssignmentManageController controller = new StudentSubjectAssignmentManageController();
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Student Subjects");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        
        
        controller.setStudentDetails(selectedStudent);
        tblStudent.getSelectionModel().clearSelection();
//        loadStudentsInTable();
    }

    @FXML
    private void actionViewTOR(ActionEvent event) throws IOException {
        CustomEnrolledStudent selectedStudent = tblStudent.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/StudentGrades.fxml")));
        StudentGradesController controller = new StudentGradesController();
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Student Subjects");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        
        
        controller.setStudentDetails(selectedStudent);
        tblStudent.getSelectionModel().clearSelection();
//        loadStudentsInTable();
        
    }
    
    @FXML
    private void actionWriteExcel() throws Exception {
        Writer writer = null;
        boolean success = true;
        String _strand          = "";
        String _section         = "";
        try {
            File currentDirFile     = new File("");
            String currentDir       = currentDirFile.getAbsolutePath();           
            
            if(cbStrand.getSelectionModel().getSelectedItem().toString() != "all"){
                _strand             = " " + cbStrand.getSelectionModel().getSelectedItem().toString();                
            }
            if(cbSection.getSelectionModel().getSelectedItem().toString() != "all"){
                _section            = " " + cbSection.getSelectionModel().getSelectedItem().toString();
            }
            String filename         = currentDir + "/" + cbSY.getSelectionModel().getSelectedItem().toString() + " - " + cbSem.getSelectionModel().getSelectedItem().toString() +  _strand + _section + ".csv";
            File file               = new File(filename);
            writer                  = new BufferedWriter(new FileWriter(file));
            
            
            
            String header_1             = "#"; 
            String header_2             = "ID Number";
            String header_3             = "Fullname";
            String header_4             = "Grade Level";
            String header_5             = "Strand";
            String header_6             = "Section";
            int content_number          = 0;
            String content_fullname     = "";
            String content_idnumber     = "";
            String content_gradelevel   = "";
            String content_strand       = "";
            String content_section      = "";
            
            writer.append(header_1);
            writer.append(',');
            writer.append(header_2);
            writer.append(',');
            writer.append(header_3);
            writer.append(',');
            writer.append(header_4);
            writer.append(',');
            writer.append(header_5);
            writer.append(',');
            writer.append(header_6);
            writer.append('\n');
            
            for (CustomEnrolledStudent student : listEnrolledStudent) {
                content_number++;
                content_idnumber    = student.getStud_idnum();
                content_fullname    = "\"" + student.getStud_lname() + ", " + student.getStud_fname() + " " + student.getStud_mi() + " " + student.getStud_suffix() + "\"";
                content_gradelevel  = student.getGrade_level();
                content_strand      = student.getStrand_code();
                content_section     = student.getStrand_group();
                


                
                writer.append(String.valueOf(content_number));
                writer.append(',');
                writer.append(content_idnumber);
                writer.append(',');
                writer.append(content_fullname);                
                writer.append(',');
                writer.append(content_gradelevel);
                writer.append(',');
                writer.append(content_strand);
                writer.append(',');
                writer.append(content_section);
                writer.append('\n');
            }
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            success = false;
        }
        finally {           
            writer.flush();
            writer.close();
        } 
        
        if(success){
            File currentDirFile     = new File("");
            String currentDir       = currentDirFile.getAbsolutePath();
            
            if(cbStrand.getSelectionModel().getSelectedItem().toString() != "all"){
                _strand             = " " + cbStrand.getSelectionModel().getSelectedItem().toString();                
            }
            if(cbSection.getSelectionModel().getSelectedItem().toString() != "all"){
                _section            = " " + cbSection.getSelectionModel().getSelectedItem().toString();
            }
            
            String filename         = cbSY.getSelectionModel().getSelectedItem().toString() + " - " + cbSem.getSelectionModel().getSelectedItem().toString() +  _strand + _section + ".csv";
            
  
           this.showMessage(success, "Successful", "Successful!", "Export was successful! File is saved in " + currentDir + " with filename " + filename); 
        }
        else{
            this.showMessage(success, "Error", "Error!", "Export was unsuccessful!"); 
        }
    }
    
    
    @FXML
    private void actionUnenrollStudent() throws Exception {
        boolean success = false;
        CustomEnrolledStudent selectedStudent = tblStudent.getSelectionModel().getSelectedItem();
        SaLedgerHeader_SaLedgerModel saLedgerHeader_SaLedgerModel;
        saLedgerHeader_SaLedgerModel = new SaLedgerHeader_SaLedgerModel();
        
        if(selectedStudent == null){
            JOptionPane.showConfirmDialog(null, "No student selected", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        else{
            /*
            System.out.println(selectedStudent.getStud_idnum());
            System.out.println(selectedStudent.getSy());
            System.out.println(selectedStudent.getSem());
            System.out.println(selectedStudent.getGrade_level());
            System.out.println(selectedStudent.getStrand_code());
            System.out.println(selectedStudent.getStrand_group());
            */
            
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to drop the student? \nThis will unenroll (" +selectedStudent.getStud_idnum()+ ") " +selectedStudent.getStud_fullname()+ " from SY" + selectedStudent.getSy() + " - " + selectedStudent.getSem() + ".", "Select an Option", JOptionPane.YES_NO_OPTION);
            /*
            * Values for showConfirmDialog are as follows:
            * 0 || JOptionPane.YES_OPTION   - Yes 
            * 1 || JOptionPane.NO_OPTION    - No
            */
            
            if(confirm == JOptionPane.YES_OPTION){
                success = saLedgerHeader_SaLedgerModel.deleteStudentLedger(selectedStudent.getSy(), selectedStudent.getSem(), selectedStudent.getGrade_level(), selectedStudent.getStud_idnum());
                
                /*
                Add deletion of enrollment in ar.enrollment 
                */
                if(success){
                    this.showMessage(success, "Successful", "Successful!", "Student dropped successfully!");             
                    loadStudentsInTable();   
                }
                else{
                    this.showMessage(success, "Error", "Error!", "Error dropping student!");                     
                }
            }         
        }
    }
    
}
