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
import model.ShTermRegModel;
import model.StrandSectionModel;
import model.StrandsModel;
import model.CustomEnrolledStudentModel;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class StudentPromoteAndAssignScheduleIndividualController implements Initializable,CustomEnrolledStudentInterface {

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
    private Button btnEnroll;
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
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colValidation;
    
    
    @FXML
    private TextField txtSY;
    @FXML
    private TextField txtSem;
    @FXML
    private ComboBox cbGradelevel2;
    @FXML
    private ComboBox cbStrand2;
    @FXML
    private ComboBox cbSection2;
    @FXML
    private TextField txtSearch2;
    @FXML
    private Button btnSearch2;
    @FXML
    private TableView<CustomEnrolledStudent> tblStudent2;
    @FXML
    private TableColumn<CustomEnrolledStudent,String> colNo2;
    @FXML
    private TableColumn<CustomEnrolledStudent,String> colStudentID2;
    @FXML
    private TableColumn<CustomEnrolledStudent,String> colStudname2;
    @FXML
    private TableColumn<CustomEnrolledStudent,String> colSY2;
    @FXML
    private TableColumn<CustomEnrolledStudent,String> colSem2;
    @FXML
    private TableColumn<CustomEnrolledStudent,String> colGradelevel2;
    @FXML
    private TableColumn<CustomEnrolledStudent,String> colStrand2;
    @FXML
    private TableColumn<CustomEnrolledStudent,String> colSection2;
    @FXML
    private TableColumn<CustomEnrolledStudent,String> colValidation2;
    
    private StrandsModel strandsModel;
    private CustomEnrolledStudentModel studentListModel;
    private StrandSectionModel sectionsModel;
    private ShTermRegModel flagsModel;
    
    private ShTermReg currentPromotion;
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
        
        currentPromotion=flagsModel.getRowCurrentPromotion();
        currentEnrollment = flagsModel.getRowCurrentEnrollment();
        
        loadSYinCbox();
        loadSemInCbox();
        loadGradelevelInCbox();
        loadStrandsInCbox();
        loadSectionsInCbox();
        loadSectionsInCbox2();
        loadStudentsToPromoteInTable();
        loadStudentsInTable2();
        
        cbSY.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        cbSem.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        cbGradelevel.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        cbStrand.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        
        cbGradelevel2.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox2());
        cbStrand2.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox2());  
    }  
    
    private void loadSYinCbox() {
        ObservableList<String> listSYforCbox = FXCollections.observableArrayList();
        
        String sy_val = "";
        for(int i=2016; i<=2050; i++){
            sy_val = i + "-" + (i+1);
            listSYforCbox.add(sy_val);
        }
        
        cbSY.setItems(listSYforCbox);
        
        
//        int sy_from = Integer.parseInt(currentPromotion.getSyReg().substring(0,4));
//        int sy_to = Integer.parseInt(currentPromotion.getSyReg().substring(5));
//        
        String sy = currentPromotion.getSyReg();
//       
//        if(currentPromotion.getSemReg() == 1){
//            sy_from--;
//            sy_to--;
//            
//            sy = sy_from +"-"+ sy_to;
//        }
        
        cbSY.getSelectionModel().select(currentPromotion.getSyReg());
        
        txtSY.setText(currentEnrollment.getSyReg());
        //txtSY.setText("2020-2021");
        
    }
    
    private void loadSemInCbox() {
        ObservableList<Integer> listSem = FXCollections.observableArrayList();
        listSem.add(1);
        listSem.add(2);
        listSem.add(3);
        cbSem.setItems(listSem);
        
        int sem = currentPromotion.getSemReg();
//        if(currentPromotion.getSemReg() == 1){
//            sem++;
//        }else if(currentPromotion.getSemReg() > 1){
//            sem--;
//        }
// 
        cbSem.getSelectionModel().select(currentPromotion.getSemReg());

        txtSem.setText(currentEnrollment.getSemReg().toString());
    }
    
    private void loadGradelevelInCbox() {
        ObservableList<String> listGradelevel = FXCollections.observableArrayList();
        listGradelevel.add("all");
        listGradelevel.add("11");
        listGradelevel.add("12");
        cbGradelevel.setItems(listGradelevel);
        cbGradelevel.getSelectionModel().select("all");
        
        cbGradelevel2.setItems(listGradelevel);
        cbGradelevel2.getSelectionModel().select("all");
    }
    
    private void loadStrandsInCbox(){
        ObservableList<String> listStrands = FXCollections.observableArrayList(strandsModel.getStrandsForCombobox());
        listStrands.add("all");
        cbStrand.setItems(listStrands);
        cbStrand.getSelectionModel().select("all");
        
        cbStrand2.setItems(listStrands);
        cbStrand2.getSelectionModel().select("all");
    }
    
    private void loadSectionsInCbox(){
        ObservableList<String> listSections = FXCollections.observableArrayList(sectionsModel.getSectionsForCombobox(cbSY.getSelectionModel().getSelectedItem().toString(), 
                cbSem.getSelectionModel().getSelectedItem().toString(),cbGradelevel.getSelectionModel().getSelectedItem().toString(), 
                cbStrand.getSelectionModel().getSelectedItem().toString()));
        listSections.add("all");
        cbSection.setItems(listSections);
        cbSection.getSelectionModel().select("all"); 
    }
     
    private void loadSectionsInCbox2(){
        ObservableList<String> listSections = FXCollections.observableArrayList(sectionsModel.getSectionsForCombobox(txtSY.getText(), 
                txtSem.getText(),cbGradelevel2.getSelectionModel().getSelectedItem().toString(), 
                cbStrand2.getSelectionModel().getSelectedItem().toString()));
        listSections.add("all");
        cbSection2.setItems(listSections);
        cbSection2.getSelectionModel().select("all"); 
    }
    
    
    @FXML
    public void loadStudentsToPromoteInTable(){
        if (!listEnrolledStudent.isEmpty()) {
            listEnrolledStudent.clear();  
        }
        listEnrolledStudent.addAll(studentListModel.getResStudentsToPromoteWithSearch(cbSY.getSelectionModel().getSelectedItem().toString(), cbSem.getSelectionModel().getSelectedItem().toString(), 
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
    }
    
    private void loadStudentsInTable2() {
        if (!listEnrolledStudentCurrentSYSem.isEmpty()) {
            listEnrolledStudentCurrentSYSem.clear();
        }

        listEnrolledStudentCurrentSYSem.addAll(studentListModel.getResEnrolledStudentWithSearch(txtSY.getText(), txtSem.getText(), 
                cbGradelevel2.getSelectionModel().getSelectedItem().toString(), cbStrand2.getSelectionModel().getSelectedItem().toString(), 
                cbSection2.getSelectionModel().getSelectedItem().toString(), txtSearch2.getText()));
       
        colNo2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CustomEnrolledStudent, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<CustomEnrolledStudent, String> p) {
               return new ReadOnlyObjectWrapper(tblStudent2.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo2.setSortable(false);
        
        colStudentID2.setCellValueFactory(new PropertyValueFactory<>("stud_idnum"));
        colStudname2.setCellValueFactory(new PropertyValueFactory<>("stud_fullname"));
        colSY2.setCellValueFactory(new PropertyValueFactory<>("sy"));
        colSem2.setCellValueFactory(new PropertyValueFactory<>("sem"));
        colGradelevel2.setCellValueFactory(new PropertyValueFactory<>("grade_level"));
        colStrand2.setCellValueFactory(new PropertyValueFactory<>("strand_code"));
        colSection2.setCellValueFactory(new PropertyValueFactory<>("section"));

        tblStudent2.setItems(listEnrolledStudentCurrentSYSem);
    }
    
    @FXML
    public void actionEnrollStudent(ActionEvent event) throws Exception {  
        CustomEnrolledStudent selectedStudent = tblStudent.getSelectionModel().getSelectedItem();
        selectedStudent = studentListModel.getRowEnrolledStudent(selectedStudent.getSy(),selectedStudent.getSem(),selectedStudent.getStud_idnum());
      
        
        
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/StudentSubjectAssignmentEntry.fxml")));
        StudentSubjectAssignmentEntryController controller = new StudentSubjectAssignmentEntryController();
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Student Promotion");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        String promote_gradelevel = "";
        /*
        if(currentEnrollment.getSemReg() == 1)
            promote_gradelevel = "12";
        else
            promote_gradelevel = "11";
        */
        if(currentEnrollment.getSemReg() == 1)
            promote_gradelevel = "12";
        else
            promote_gradelevel = selectedStudent.getGrade_level();
        
        
        controller.setStudentDetails(selectedStudent,promote_gradelevel);
        tblStudent.getSelectionModel().clearSelection();
        this.loadStudentsToPromoteInTable();
    }
    
    @FXML
    public void actionViewStudent(ActionEvent event) throws Exception {  
        CustomEnrolledStudent selectedStudent = tblStudent2.getSelectionModel().getSelectedItem();
        selectedStudent = studentListModel.getRowEnrolledStudent(selectedStudent.getSy(),selectedStudent.getSem(),selectedStudent.getStud_idnum());
        
        
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/StudentSubjectAssignmentEntry.fxml")));
        StudentSubjectAssignmentEntryController controller = new StudentSubjectAssignmentEntryController();
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Student Promotion");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        String promote_gradelevel = "";
        /*
        if(currentEnrollment.getSemReg() == 1)
            promote_gradelevel = "12";
        else
            promote_gradelevel = "11";
        */
        if(currentEnrollment.getSemReg() == 1)
            promote_gradelevel = "12";
        else
            promote_gradelevel = selectedStudent.getGrade_level();
        
        controller.setStudentDetails(selectedStudent,promote_gradelevel);
        tblStudent2.getSelectionModel().clearSelection();
        this.loadStudentsInTable2();
    }
    
    
    @FXML
    public void actionSearchPromotionList(ActionEvent event) throws Exception {
        this.loadStudentsToPromoteInTable();
    }
    
    @FXML
    public void actionSearchEnrolledList(ActionEvent event) throws Exception {
        this.loadStudentsInTable2();
    }
}
