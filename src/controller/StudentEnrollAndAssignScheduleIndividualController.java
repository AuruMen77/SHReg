/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomEnrolledStudent;
import entity.ShApplicant;
import entity.ShConfirmation;
import entity.ShTermReg;
import interfaces.CustomEnrolledStudentInterface;
import static interfaces.CustomEnrolledStudentInterface.listEnrolledStudentCurrentSYSem;
import interfaces.ShApplicantInterface;
import interfaces.ShConfirmationInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
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
import model.CustomEnrolledStudentModel;
import model.ShConfirmationModel;
import model.ShTermRegModel;
import model.StrandSectionModel;
import model.StrandsModel;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class StudentEnrollAndAssignScheduleIndividualController implements Initializable, CustomEnrolledStudentInterface,ShConfirmationInterface {

    @FXML
    private ComboBox cbSY;
    @FXML
    private ComboBox cbStrand;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<ShConfirmation> tblApplicant;
    @FXML
    private TableColumn<ShConfirmation, String> colNo;
    @FXML
    private TableColumn<ShConfirmation, String> colStudentID;
    @FXML
    private TableColumn<ShConfirmation, String> colStudname;
    @FXML
    private TableColumn<ShConfirmation, String> colStrand;
    @FXML
    private Button btnEnroll;
    @FXML
    private TextField txtSY;
    @FXML
    private TextField txtSem;
    @FXML
    private ComboBox cbGradelevel;
    @FXML
    private ComboBox cbStrand2;
    @FXML
    private ComboBox cbSection;
    @FXML
    private TextField txtSearch2;
    @FXML
    private Button btnSearch2;
    @FXML
    private TableView<CustomEnrolledStudent> tblStudent;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colNo2;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colStudentID2;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colStudname2;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colSY2;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colSem2;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colGradelevel2;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colStrand2;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colSection2;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colValidation2;
    
    private StrandsModel strandsModel;
    private CustomEnrolledStudentModel studentListModel;
    private ShConfirmationModel shConfirmationModel;
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
        shConfirmationModel = new ShConfirmationModel();
        sectionsModel =  new StrandSectionModel();
        flagsModel =  new ShTermRegModel();
        
        currentEnrollment = flagsModel.getRowCurrentEnrollment();
        
        txtSY.setText(currentEnrollment.getSyReg());
        txtSem.setText(currentEnrollment.getSemReg().toString());
        
        loadSYinCbox();
        loadGradelevelInCbox();
        loadStrandsInCbox();
        loadSectionsInCbox();
        
        loadApplicantsInTable();
//        loadStudentsInTable();
        
        cbGradelevel.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        cbStrand2.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        
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
        
        cbStrand2.setItems(listStrands);
        cbStrand2.getSelectionModel().select("all");
    }
    
    private void loadSectionsInCbox(){
        ObservableList<String> listSections = FXCollections.observableArrayList(sectionsModel.getSectionsForCombobox(txtSY.getText(), 
                txtSem.getText(),cbGradelevel.getSelectionModel().getSelectedItem().toString(), 
                cbStrand2.getSelectionModel().getSelectedItem().toString()));
        listSections.add("all");
        cbSection.setItems(listSections);
        cbSection.getSelectionModel().select("all"); 
    }
    
    private void loadApplicantsInTable() {
        if (!listApplicantConfirmed.isEmpty()) {
            listApplicantConfirmed.clear();
        }

        listApplicantConfirmed.addAll(shConfirmationModel.getResApplicantConfirmationNotEnrolledWithSearch(cbSY.getSelectionModel().getSelectedItem().toString(),
                cbStrand.getSelectionModel().getSelectedItem().toString(), txtSearch.getText()));
       
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShConfirmation, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShConfirmation, String> p) {
               return new ReadOnlyObjectWrapper(tblApplicant.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudname.setCellValueFactory((TableColumn.CellDataFeatures<ShConfirmation, String> p)
                -> new SimpleStringProperty(p.getValue().getShApplicant().getAppLname().trim() +", "+ p.getValue().getShApplicant().getAppFname().trim() ));
        colStrand.setCellValueFactory(new PropertyValueFactory<>("strandCode"));
       
        tblApplicant.setItems(listApplicantConfirmed);
    }
    
    private void loadStudentsInTable() {
        if (!listEnrolledStudentCurrentSYSem.isEmpty()) {
            listEnrolledStudentCurrentSYSem.clear();
        }

        listEnrolledStudentCurrentSYSem.addAll(studentListModel.getResEnrolledStudentWithSearch(txtSY.getText(), txtSem.getText(), 
                cbGradelevel.getSelectionModel().getSelectedItem().toString(), cbStrand2.getSelectionModel().getSelectedItem().toString(), 
                cbSection.getSelectionModel().getSelectedItem().toString(), txtSearch2.getText()));
       
        colNo2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CustomEnrolledStudent, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<CustomEnrolledStudent, String> p) {
               return new ReadOnlyObjectWrapper(tblStudent.getItems().indexOf(p.getValue()) + 1);
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

        tblStudent.setItems(listEnrolledStudentCurrentSYSem);
    }

    
    @FXML
    private void actionSearchApplicant(ActionEvent event) {
        loadApplicantsInTable();
    }
    
    
    @FXML
    private void actionSearchEnrolledStudent(ActionEvent event) {
        loadStudentsInTable();
    }
    
    @FXML
    public void actionEnrollStudent(ActionEvent event) throws Exception {  
        ShConfirmation selectedApplicantConfirmation = this.tblApplicant.getSelectionModel().getSelectedItem();
        
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/StudentSubjectAssignmentEntry.fxml")));
        StudentSubjectAssignmentEntryController controller = new StudentSubjectAssignmentEntryController();
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Student Enrollment");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        
        
        controller.setApplicantDetails(selectedApplicantConfirmation, "11");
        tblStudent.getSelectionModel().clearSelection();
        this.loadStudentsInTable();
    }
    
}
