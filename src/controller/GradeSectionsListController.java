/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomClassSections;
import entity.ShClassInfo;
import entity.ShTermReg;
import interfaces.CustomClassSectionsInterface;
import interfaces.ShClassInfoInterface;
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
import model.ShSectionsModel;
import model.StrandsModel;
import model.CustomEnrolledStudentModel;
import model.ShClassInfoModel;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class GradeSectionsListController implements Initializable, ShClassInfoInterface {

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
    private TextField txtSubject;
    @FXML
    private Button btnSelect;
    @FXML
    private TableView<ShClassInfo> tblStudent;
    @FXML
    private TableColumn<ShClassInfo, String> colNo;
    @FXML
    private TableColumn<ShClassInfo, String> colSY;
    @FXML
    private TableColumn<ShClassInfo, String> colSem;
    @FXML
    private TableColumn<ShClassInfo, String> colGradelevel;
    @FXML
    private TableColumn<ShClassInfo, String> colStrand;
    @FXML
    private TableColumn<ShClassInfo, String> colSection;
    @FXML
    private TableColumn<ShClassInfo, String> colSubjectCode;
    @FXML
    private TableColumn<ShClassInfo, String> colTeacher;
    
    private ShClassInfoModel shClassInfoModel;
    private StrandsModel strandsModel;
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
        sectionsModel =  new StrandSectionModel();
        flagsModel =  new ShTermRegModel();
        shClassInfoModel = new ShClassInfoModel();
        
        currentEnrollment=flagsModel.getRowCurrentEnrollment();

        loadSYinCbox();
        loadSemInCbox();
        loadGradelevelInCbox();
        loadStrandsInCbox();
        loadSectionsInCbox();
        loadClassInTable(); 
        
        cbSY.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        cbSem.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        cbGradelevel.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        cbStrand.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
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
        ObservableList<String> listSections = FXCollections.observableArrayList(this.sectionsModel.getSectionsForCombobox(cbSY.getSelectionModel().getSelectedItem().toString(), 
                cbSem.getSelectionModel().getSelectedItem().toString(), cbGradelevel.getSelectionModel().getSelectedItem().toString(), 
                cbStrand.getSelectionModel().getSelectedItem().toString()));
        listSections.add("all");
        cbSection.setItems(listSections);
        cbSection.getSelectionModel().select("all");
    }
    
    private void loadClassInTable() {
        if (!listClassSections.isEmpty()) {
            listClassSections.clear();
        }
        listClassSections.addAll(shClassInfoModel.getResGradeSubjectSections(cbSY.getSelectionModel().getSelectedItem().toString(), cbSem.getSelectionModel().getSelectedItem().toString(), 
                cbGradelevel.getSelectionModel().getSelectedItem().toString(), cbStrand.getSelectionModel().getSelectedItem().toString(), 
                cbSection.getSelectionModel().getSelectedItem().toString(), txtSubject.getText()));

       
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShClassInfo, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShClassInfo, String> p) {
               return new ReadOnlyObjectWrapper(tblStudent.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        
        colSY.setCellValueFactory(new PropertyValueFactory<>("claSy"));
        colSem.setCellValueFactory(new PropertyValueFactory<>("claSem"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("claYrLevel"));
        colStrand.setCellValueFactory(new PropertyValueFactory<>("strandcode"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("class_section"));
        colTeacher.setCellValueFactory(new PropertyValueFactory<>("class_teacher_name"));
        colSubjectCode.setCellValueFactory(new PropertyValueFactory<>("claCrsCode"));

        this.tblStudent.setItems(listClassSections); 
    }
    
    public void actionSearch(ActionEvent event) throws Exception { 
        this.loadClassInTable();
    }
    
    public void actionSelectSubject(ActionEvent event) throws Exception {
        ShClassInfo selectedShClassInfo = tblStudent.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/GradeSubject.fxml")));
        GradeSubjectController controller = new GradeSubjectController();
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        controller.setClassSection(selectedShClassInfo);
    }
    
}
