/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShTermReg;
import entity.StrandSection;
import interfaces.StrandSectionInterface;
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

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class SectionListController implements Initializable, StrandSectionInterface {

    @FXML
    private ComboBox cbSY;
    @FXML
    private ComboBox cbSem;
    @FXML
    private ComboBox cbGradelevel;
    @FXML
    private ComboBox cbStrand;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<StrandSection> tblSection;
    @FXML
    private TableColumn<StrandSection, String> colNo;
    @FXML
    private TableColumn<StrandSection, String> colSY;
    @FXML
    private TableColumn<StrandSection, String> colSem;
    @FXML
    private TableColumn<StrandSection, String> colGradelevel;
    @FXML
    private TableColumn<StrandSection, String> colStrand;
    @FXML
    private TableColumn<StrandSection, String> colStrandgroup;
    @FXML
    private TableColumn<StrandSection, String> colSection;
    
    private StrandSectionModel sectionsModel;
    private StrandsModel strandsModel;
    private ShTermRegModel flagsModel;
    
    private ShTermReg currentEnrollment;
    @FXML
    private Button btnViewSubjects;
    @FXML
    private Button btnViewCorevalues;
    @FXML
    private Button btnViewAttendance;
    @FXML
    private Button btnViewSubjectGrades;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sectionsModel =  new StrandSectionModel();
        strandsModel = new StrandsModel();
        flagsModel =  new ShTermRegModel();
        
        currentEnrollment=flagsModel.getRowCurrentEnrollment();
        
        loadSYinCbox();
        loadSemInCbox();
        loadGradelevelInCbox();
        loadStrandsInCbox();
        loadSectionsInTable();
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
    
    private void loadSectionsInTable() {
        if (!listSection.isEmpty()) {
            listSection.clear();
        }
        listSection.addAll(sectionsModel.getResSection(cbSY.getSelectionModel().getSelectedItem().toString(), this.cbSem.getSelectionModel().getSelectedItem().toString(), 
                cbGradelevel.getSelectionModel().getSelectedItem().toString(), cbStrand.getSelectionModel().getSelectedItem().toString()));

       
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StrandSection, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<StrandSection, String> p) {
               return new ReadOnlyObjectWrapper(tblSection.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        
        colSY.setCellValueFactory(new PropertyValueFactory<>("sy"));
        colSem.setCellValueFactory(new PropertyValueFactory<>("sem"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        colStrand.setCellValueFactory(new PropertyValueFactory<>("strand"));
        colStrandgroup.setCellValueFactory(new PropertyValueFactory<>("strandgroup"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("studSection"));


        tblSection.setItems(listSection); 
    }
    
    
    
    @FXML
    public void actionSearch(ActionEvent event) throws Exception { 
        loadSectionsInTable();
    }
    
    public void actionAddSection(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/SectionEntry.fxml")));
        SectionEntryController controller = new SectionEntryController();
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

        tblSection.refresh();
    }
    
    
    
    public void actionEditSection(ActionEvent event) throws Exception {
 
        StrandSection selectedStrandSection = tblSection.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/SectionEntry.fxml")));
        SectionEntryController controller = new SectionEntryController();
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
        
        
        controller.setSection(selectedStrandSection);
        tblSection.refresh();
    }
}
