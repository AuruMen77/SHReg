/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomClassSections;
import entity.ShClassInfo;
import interfaces.CustomClassSectionsInterface;
import interfaces.ShClassInfoInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.ShClassInfoModel;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class ClassSubjectListController implements Initializable,ShClassInfoInterface {

    @FXML
    private TextField txtSY;
    @FXML
    private TextField txtSem;
    @FXML
    private TextField txtGradelevel;
    @FXML
    private TextField txtStrand;
    @FXML
    private TextField txtSection;
    @FXML
    private TextField txtModerator;
    @FXML
    private Button btnSelect;
    @FXML
    private TableView<ShClassInfo> tblSubject;
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
    private ShClassInfo selected_shClassInfo;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shClassInfoModel =  new ShClassInfoModel();
    }    
    
    public void setClassSection(ShClassInfo shClassInfo){
        selected_shClassInfo = shClassInfo;
        txtSY.setText(selected_shClassInfo.getClaSy());
        txtSem.setText(selected_shClassInfo.getClaSem().toString());
        txtGradelevel.setText(selected_shClassInfo.getClaYrLevel().toString());
        txtStrand.setText(selected_shClassInfo.getStrandcode());
        txtSection.setText(selected_shClassInfo.getClass_section());
        txtModerator.setText(selected_shClassInfo.getClass_teacher_name());
        loadClassSubjectsInTable();
    }
    
    private void loadClassSubjectsInTable() {
        if (!listClassSubjects.isEmpty()) {
            listClassSubjects.clear();
        }
        listClassSubjects.addAll(shClassInfoModel.getResClassSubjectList(selected_shClassInfo.getClaSy(), 
               selected_shClassInfo.getClaSem().toString(), selected_shClassInfo.getClaYrLevel().toString(), 
               selected_shClassInfo.getStrandcode(), selected_shClassInfo.getStrandgroup()));

       
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShClassInfo, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShClassInfo, String> p) {
               return new ReadOnlyObjectWrapper(tblSubject.getItems().indexOf(p.getValue()) + 1);
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

        TableColumn<ShClassInfo, String> colAdd = new TableColumn<>();    
        colAdd.setText("waaaaa");
        
        TableColumn<ShClassInfo, String> colAdd2 = new TableColumn<>();    
        colAdd2.setText("subcol");
        
        colAdd.getColumns().add(colAdd2);
        
        tblSubject.getColumns().add(colAdd);
        tblSubject.setItems(listClassSubjects); 
    }
    
    
    
}
