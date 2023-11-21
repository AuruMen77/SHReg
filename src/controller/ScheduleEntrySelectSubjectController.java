/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomCheckbox;
import entity.ShClassInfo;
import entity.ShCurrDtl;
import entity.ShCurrSy;
import entity.ShCurriculumDtl;
import entity.ShTermReg;
import static interfaces.ScheduleEntrySelectSubjectlInterface.listCurriculumSubjects;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ShCurrDtlModel;
import model.ShTermRegModel;
import model.ShCurrSyModel;
import model.StrandsModel;
import interfaces.ShClassInfoInterface;
import interfaces.ScheduleEntrySelectSubjectlInterface;
import static interfaces.ScheduleEntrySelectSubjectlInterface.listCurriculumSubjects;
import static interfaces.ShClassInfoInterface.listScheduleSelectSubject;
import model.ShCurrDtl_CurrSyModel;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class ScheduleEntrySelectSubjectController implements Initializable, ScheduleEntrySelectSubjectlInterface, ShClassInfoInterface {

    @FXML
    private ComboBox cbSY;
    @FXML
    private ComboBox cbSem;
    @FXML
    private ComboBox cbStrand;
    @FXML
    private ComboBox cbGradelevel;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAssignSched;
    @FXML
    private CheckBox checkAllSchedule;
    @FXML
    private TableView<ShCurrDtl> tblSubject;
    @FXML
    private TableColumn<ShCurrDtl, String> colNo;
    @FXML
    private TableColumn<ShCurrDtl, String> colCurrDtlID, colSY, colSem;
    @FXML
    private TableColumn<ShCurrDtl, String> colGradelevel;
    @FXML
    private TableColumn<ShCurrDtl, String> colSubjCode;
    @FXML
    private TableColumn<ShCurrDtl, String> colSubjDesc;


    private ShCurrDtl_CurrSyModel shCurrDtl_CurrSyModel;
    private StrandsModel strandsModel;
    private ShTermRegModel flagsModel;
    private ShCurrSyModel shCurrSyModel;
    
    private ShTermReg currentEnrollment;
    
    
    
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shCurrDtl_CurrSyModel = new ShCurrDtl_CurrSyModel();
        strandsModel = new StrandsModel();
        flagsModel =  new ShTermRegModel();
        shCurrSyModel = new ShCurrSyModel();
        
        currentEnrollment=flagsModel.getRowCurrentEnrollment();
        
        loadSYinCbox();
        loadSemInCbox();
        loadGradelevelInCbox();
        loadStrandsInCbox();
        
//        cbStrand2.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        checkAllSchedule.selectedProperty().addListener( e -> toggleCheckAllSchedule());
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
        cbStrand.setItems(listStrands);
        cbStrand.getSelectionModel().select(0);
    }
    
    private void loadSubjectsInTable() {
        if (!listCurriculumSubjects.isEmpty()) {
            listCurriculumSubjects.clear();
        }
        listCurriculumSubjects.addAll(shCurrDtl_CurrSyModel.getResCurriculumSubjects(cbSY.getSelectionModel().getSelectedItem().toString(), 
                cbSem.getSelectionModel().getSelectedItem().toString(), cbStrand.getSelectionModel().getSelectedItem().toString(), 
                cbGradelevel.getSelectionModel().getSelectedItem().toString()));
       
        
        colNo.setSortable(false);

        colNo.setCellValueFactory(     
                 new PropertyValueFactory<>("customSelect")
        );      


        colCurrDtlID.setCellValueFactory(new PropertyValueFactory<>("currDtlId"));
        colSY.setCellValueFactory(new PropertyValueFactory<>("customSy"));
        colSem.setCellValueFactory(new PropertyValueFactory<>("sem"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("yrlevel"));

        
        colSubjCode.setCellValueFactory((TableColumn.CellDataFeatures<ShCurrDtl, String> p)
                -> new SimpleStringProperty(p.getValue().getShCourseList().getCrsCode()));
        
        colSubjDesc.setCellValueFactory((TableColumn.CellDataFeatures<ShCurrDtl, String> p)
                -> new SimpleStringProperty(p.getValue().getShCourseList().getCrsTitle().trim()));
       

        tblSubject.setItems(listCurriculumSubjects); 

    }
    
    public ObservableList<ShClassInfo> getListScheduleSelectSubject(){
        return listScheduleSelectSubject;
    }
    
    public void toggleCheckAllSchedule(){
        
        if(checkAllSchedule.isSelected()){
            for(int i = 0; i < listCurriculumSubjects.size(); i++){
                listCurriculumSubjects.get(i).getCustomSelect().setSelected(true);
            }
        }else{
            for(int i = 0; i < listCurriculumSubjects.size(); i++){
                listCurriculumSubjects.get(i).getCustomSelect().setSelected(false);
            }
        }
    }
    
    
    @FXML
    public void actionSearch(ActionEvent event) throws Exception {  
        loadSubjectsInTable();
    }
    
    @FXML
    public void actionCheckAllSchedule(ActionEvent event) throws Exception {  
        if(checkAllSchedule.isSelected()){
            for(int i = 0; i < listCurriculumSubjects.size(); i++){
                listCurriculumSubjects.get(i).setCustomSelect(checkAllSchedule);
            }
        }else{
            for(int i = 0; i < listCurriculumSubjects.size(); i++){
                listCurriculumSubjects.get(i).setCustomSelect(checkAllSchedule);
            }
        }
    }
    
    @FXML
    public void actionAssignSchedule(ActionEvent event) throws Exception { 

        ObservableList<ShCurrDtl> listToAssign = FXCollections.observableArrayList();
        ShCurrSy selected_ShCurrSy = shCurrSyModel.getRowCurrSy(cbSY.getSelectionModel().getSelectedItem().toString(), 
                cbStrand.getSelectionModel().getSelectedItem().toString());
        
        listScheduleSelectSubject.clear();
        for(ShCurrDtl row : listCurriculumSubjects)
        {
           if(row.getCustomSelect().isSelected())
           {
             listToAssign.add(row);
             ShClassInfo shClassInfo =  new ShClassInfo();
        
              
             shClassInfo.setClassid(null);
             shClassInfo.setFCurrSy(selected_ShCurrSy.getCurrSyId());
             shClassInfo.setFCurrDtl(row.getCurrDtlId());
             shClassInfo.setClaSy(row.getCustomSy());
             shClassInfo.setClaSem(row.getSem());
             shClassInfo.setClaYrLevel(Byte.valueOf(row.getYrlevel().toString()));
             shClassInfo.setClaCrsCode(row.getShCourseList().getCrsCode());
             shClassInfo.setStrandcode(row.getShCurrHdr().getStrandcode());
      
             
             listScheduleSelectSubject.add(shClassInfo);

             
           }
            
        }
        
//        System.out.println("val " + listScheduleSelectSubject.get(0).getFCurrSy());
       ((Stage) btnAssignSched.getScene().getWindow()).close();
    }
}
