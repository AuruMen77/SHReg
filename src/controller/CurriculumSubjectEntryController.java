/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShCourseList;
import entity.ShCurrDtl;
import entity.ShCurrHdr;
import entity.ShCurrSy;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
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
import model.ShCurrHdrModel;
import model.ShCurrDtlModel;
import model.ShCourseListModel;
import others.AutoCompleteComboBoxListener;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class CurriculumSubjectEntryController implements Initializable {

    @FXML
    private TextField txtCurriculumID;
    @FXML
    private ComboBox<String> cbGradelevel;
    @FXML
    private ComboBox<String> cbSem;
    @FXML
    private ComboBox cbCourseCode;
    @FXML
    private Button btnAddToList;
    @FXML
    private Button btnSave;
    @FXML
    private TableView<ShCurrDtl> tblSubject;
    @FXML
    private TableColumn<ShCurrDtl, String> colNo,colID,colCourseCode,colDescription,colGradelevel,colSem;
    @FXML
    private Button btnRemoveFromLIst;
    
    public ObservableList<ShCurrDtl> listSubject = FXCollections.observableArrayList();
    private ShCurrDtlModel shCurrDtlModel;
    private ShCourseListModel subjectModel;
    private ShCurrHdr shCurrHdr;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shCurrDtlModel =  new ShCurrDtlModel();
        subjectModel = new ShCourseListModel();
        loadSubjectInCbox();
        loadGradelevelInCbox();
        loadSem();
    } 
    
    public void setCurriculumHdr(ShCurrHdr shCurrHdr) {
        this.shCurrHdr = shCurrHdr;    
        setCurriculumHdrDesc();
        loadSubjectsInTable();
    }
    
    public void setCurriculumHdrDesc() {
        this.txtCurriculumID.setText(shCurrHdr.getCurrName());   
    }
    
    public void saveItemsFromTableToList(){
        
        
        listSubject = FXCollections.observableArrayList();
        for(ShCurrDtl item : tblSubject.getItems()){
            ShCurrDtl shCurrDtl = new ShCurrDtl();
            
            
            shCurrDtl.setShCurrHdr(shCurrHdr);
            shCurrDtl.setShCourseList(item.getShCourseList());
            shCurrDtl.setYrlevel(item.getYrlevel());
            shCurrDtl.setSem(item.getSem());
            
            listSubject.add(shCurrDtl);
        }
    }
    
    private void loadSubjectInCbox() {
        ObservableList<String> listCourseCodeForCbox = FXCollections.observableArrayList(subjectModel.getResSubjectCodeListForCbox());
        cbCourseCode.setItems(listCourseCodeForCbox);   
    }
    
    private void loadGradelevelInCbox() {
        ObservableList<String> listGradelevel = FXCollections.observableArrayList();
        listGradelevel.add("11");
        listGradelevel.add("12");
        cbGradelevel.setItems(listGradelevel);
    }
    
    private void loadSem() {
        ObservableList<String> listSem = FXCollections.observableArrayList();
        listSem.add("1");
        listSem.add("2");
        listSem.add("3");
        cbSem.setItems(listSem);
    }
    
    private void loadSubjectsInTable() {

        if (!listSubject.isEmpty()) {
            listSubject.clear();
        }
        listSubject.addAll(shCurrDtlModel.getResCurriculumSubjects(shCurrHdr));
       
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCurrDtl, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCurrDtl, String> p) {
               return new ReadOnlyObjectWrapper(tblSubject.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        colID.setCellValueFactory(new PropertyValueFactory<>("currDtlId"));
        colCourseCode.setCellValueFactory((TableColumn.CellDataFeatures<ShCurrDtl, String> p)
                -> new SimpleStringProperty(p.getValue().getShCourseList().getCrsCode()));
        colDescription.setCellValueFactory((TableColumn.CellDataFeatures<ShCurrDtl, String> p)
                -> new SimpleStringProperty(p.getValue().getShCourseList().getCrsTitle()));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("yrlevel"));
        colSem.setCellValueFactory(new PropertyValueFactory<>("sem"));

        tblSubject.setItems(listSubject); 
    }
    
    @FXML
    private void actionAutocompleteSubject(ActionEvent event){
        new AutoCompleteComboBoxListener<>(cbCourseCode);
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
    
    public void actionAddToList(ActionEvent event) throws Exception {
        saveItemsFromTableToList();
        
        //--- get object for selected subject 
        ShCourseList shCourseList = subjectModel.getCourse(cbCourseCode.getSelectionModel().getSelectedIndex() + 1);
 
        ShCurrDtl shCurrDtl = new ShCurrDtl();
        shCurrDtl.setShCurrHdr(shCurrHdr);
        shCurrDtl.setShCourseList(shCourseList);
        shCurrDtl.setYrlevel(Integer.parseInt(cbGradelevel.getSelectionModel().getSelectedItem()));
        shCurrDtl.setSem(Integer.parseInt(cbSem.getSelectionModel().getSelectedItem()));

        listSubject.add(shCurrDtl);
            
        tblSubject.getItems().clear();
        tblSubject.setItems(listSubject);
    }
    
    public void actionRemove(ActionEvent event) throws Exception {
        tblSubject.getItems().remove(tblSubject.getSelectionModel().getSelectedItem()); 
    }
    
    public void actionSave(ActionEvent event) throws Exception {
        this.saveItemsFromTableToList();
        
        Boolean success = this.shCurrDtlModel.saveCurriculumSubjectsFromTable(listSubject, shCurrHdr);

//        ((Stage) btnSave.getScene().getWindow()).close();

        if(success==true)
            this.showMessage(success, "Successful", "Curriculum Subject Created!", "Curriculum Subject is created successfully");
        else
            this.showMessage(success, "Error", "Error", "Error occured");

    }
    
    
}
