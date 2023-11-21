/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShApplicant;
import entity.ShClassInfo;
import entity.ShConfirmation;
import entity.ShTermReg;
import interfaces.ShClassInfoInterface;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.SaLedgerHeader_SaLedgerModel;
import model.ShCClassStudModel;
import model.ShClassInfoModel;
import model.ShConfirmationModel;
import model.ShFeeModel;
import model.ShStudStrand_ShStudlistModel;
import model.ShTermRegModel;
import model.StrandSectionModel;
import model.StrandsModel;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class StudentEnrollAndAssignScheduleBatchController implements Initializable, ShClassInfoInterface, ShConfirmationInterface {

    @FXML
    private ComboBox cbSYConfirmedFrom;
    @FXML
    private ComboBox cbStrandConfirmedFrom;
    @FXML
    private Button btnShowApplicantList;
    @FXML
    private TextField txtSYToEnroll;
    @FXML
    private TextField txtSemToEnroll;
    @FXML
    private ComboBox cbStrandToEnroll;
    @FXML
    private ComboBox cbSectionToEnroll;
    @FXML
    private Button btnShowScheduleList;
    @FXML
    private TableView<ShClassInfo> tblScheduleToAssign;
    @FXML
    private TableColumn<ShClassInfo, String> colSchedNo;
    @FXML
    private TableColumn<ShClassInfo, String> colSchedID;
    @FXML
    private TableColumn<ShClassInfo, String> colSubjCode;
    @FXML
    private TableColumn<ShClassInfo, String> colSubjSection;
    @FXML
    private TableColumn<ShClassInfo, String> colRoomDayTime;
    @FXML
    private CheckBox checkAssess;
    @FXML
    private CheckBox checkAssignSubject;
    @FXML
    private Button btnBatchEnroll;
    @FXML
    private TableView<ShConfirmation> tblApplicant;
    @FXML
    private TableColumn<ShConfirmation, String> colStudNo;
    @FXML
    private TableColumn<ShConfirmation, String> colStudID;
    @FXML
    private TableColumn<ShConfirmation, String> colStudname;
    @FXML
    private TableColumn<ShConfirmation, String> colStudStrand;
    @FXML
    private TableColumn<ShConfirmation, String> colStudStrandgroup;
    @FXML
    private Button btnRemoveApplicant;
    @FXML
    private TextField txtGradelevelToEnroll;
    
    private ShConfirmationModel shConfirmationModel;
    private ShStudStrand_ShStudlistModel shStudStrand_ShStudlistModel;
    private ShClassInfoModel shClassInfoModel;
    private ShCClassStudModel shCClassStudModel;
    private ShFeeModel shFeeModel;
    private SaLedgerHeader_SaLedgerModel saLedgerHeader_SaLedgerModel;
    private StrandsModel strandsModel;
    private StrandSectionModel sectionsModel;
    private ShTermRegModel flagsModel;
    
    private ShTermReg currentEnrollment;
    private ShTermReg currentPromotion;
    private ShClassInfo selected_shClassInfo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shConfirmationModel = new ShConfirmationModel();
        shStudStrand_ShStudlistModel = new ShStudStrand_ShStudlistModel();
        shClassInfoModel = new ShClassInfoModel();
        shCClassStudModel = new ShCClassStudModel();
        shFeeModel = new ShFeeModel();
        saLedgerHeader_SaLedgerModel = new SaLedgerHeader_SaLedgerModel();
        strandsModel = new StrandsModel();
        sectionsModel =  new StrandSectionModel();
        flagsModel =  new ShTermRegModel();
        
        currentEnrollment = flagsModel.getRowCurrentEnrollment();
        currentPromotion=flagsModel.getRowCurrentPromotion();
        
        txtSYToEnroll.setText(currentEnrollment.getSyReg());
        txtSemToEnroll.setText(currentEnrollment.getSemReg().toString());
        txtGradelevelToEnroll.setText("11");
        
        
        loadSYInCbox();
        loadStrandsInCbox();
        loadSectionsInCbox();
        
        checkAssessmentFlag();
     
       
        cbStrandToEnroll.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());

    }    
    
    private void checkAssessmentFlag(){
        if(flagsModel.checkAssessmentOfSySem(currentEnrollment.getSyReg(), currentEnrollment.getSemReg().toString()))
            checkAssess.setVisible(true);
        else
            checkAssess.setVisible(false);
    }
    
    private void loadSYInCbox() {
        ObservableList<String> listSYforCbox = FXCollections.observableArrayList();
        
        String sy_val = "";
        for(int i=2016; i<=2050; i++){
            sy_val = i + "-" + (i+1);
            listSYforCbox.add(sy_val);
        }
        
        cbSYConfirmedFrom.setItems(listSYforCbox);
        cbSYConfirmedFrom.getSelectionModel().select(currentEnrollment.getSyReg());

    }

    
    
    private void loadStrandsInCbox(){
        ObservableList<String> listStrands = FXCollections.observableArrayList(strandsModel.getStrandsForCombobox());
        cbStrandConfirmedFrom.setItems(listStrands);
        cbStrandConfirmedFrom.getSelectionModel().select(0);
        
        cbStrandToEnroll.setItems(listStrands);
        cbStrandToEnroll.getSelectionModel().select(0);
    }
    
    private void loadSectionsInCbox(){
        ObservableList<String> listSections = FXCollections.observableArrayList(sectionsModel.getSectionsForCombobox(txtSYToEnroll.getText(), 
                txtSemToEnroll.getText(),txtGradelevelToEnroll.getText(), 
                cbStrandToEnroll.getSelectionModel().getSelectedItem().toString()));
        cbSectionToEnroll.setItems(listSections);
        cbSectionToEnroll.getSelectionModel().select(0);
    }
    
    public void setClassSection(){
//
//        String str_selected_section = cbSection.getSelectionModel().getSelectedItem().toString();
//        selected_shClassInfo_toPromoteFrom.setClass_section(str_selected_section);
//        
//        selected_shClassInfo_toPromoteFrom.setStrandgroup(sectionsModel.getRowSection(selected_shClassInfo_toPromoteFrom.getClaSy(), 
//            selected_shClassInfo_toPromoteFrom.getClaSem().toString(), selected_shClassInfo_toPromoteFrom.getClaYrLevel().toString(),
//            selected_shClassInfo_toPromoteFrom.getStrandcode(), str_selected_section).getStrandgroup());  
//        
        
        selected_shClassInfo = new ShClassInfo();
        selected_shClassInfo.setClaSy(txtSYToEnroll.getText());
        selected_shClassInfo.setClaSem(Integer.valueOf(txtSemToEnroll.getText()));
        selected_shClassInfo.setClaYrLevel(Byte.parseByte(txtGradelevelToEnroll.getText()));
        selected_shClassInfo.setStrandcode(cbStrandToEnroll.getSelectionModel().getSelectedItem().toString());
        
        String str_selected_section = cbSectionToEnroll.getSelectionModel().getSelectedItem().toString();
        selected_shClassInfo.setClass_section(str_selected_section);
        
        selected_shClassInfo.setStrandgroup(sectionsModel.getRowSection(selected_shClassInfo.getClaSy(), 
            selected_shClassInfo.getClaSem().toString(), selected_shClassInfo.getClaYrLevel().toString(),
            selected_shClassInfo.getStrandcode(), str_selected_section).getStrandgroup());  
        
        
       
       
    }
    
    private void loadOfferedSubjectsInTable() {
        if (!listClassSubjects.isEmpty()) {
            listClassSubjects.clear();
        }
            
        
        listClassSubjects.addAll(shClassInfoModel.getResOfferedSubjectList(selected_shClassInfo.getClaSy(), selected_shClassInfo.getClaSem().toString(), 
                selected_shClassInfo.getClaYrLevel().toString(),selected_shClassInfo.getStrandcode(),selected_shClassInfo.getStrandgroup()));

        
        colSchedNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShClassInfo, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShClassInfo, String> p) {
               return new ReadOnlyObjectWrapper(tblScheduleToAssign.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colSchedID.setCellValueFactory(new PropertyValueFactory<>("classid"));
        colSubjCode.setCellValueFactory(new PropertyValueFactory<>("claCrsCode"));
        colSubjSection.setCellValueFactory(new PropertyValueFactory<>("claSection"));
        colRoomDayTime.setCellValueFactory(new PropertyValueFactory<>("subject_room_daytime_list"));

        tblScheduleToAssign.setItems(listClassSubjects); 
    }
    
    private void loadApplicantsInTable() {
        if (!listApplicantConfirmed.isEmpty()) {
            listApplicantConfirmed.clear();
        }
      
       
        listApplicantConfirmed.addAll(this.shConfirmationModel.getResApplicantConfirmationWithSearch(cbSYConfirmedFrom.getSelectionModel().getSelectedItem().toString(),
                cbStrandConfirmedFrom.getSelectionModel().getSelectedItem().toString(), ""));

        
        colStudNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShConfirmation, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShConfirmation, String> p) {
               return new ReadOnlyObjectWrapper(tblApplicant.getItems().indexOf(p.getValue()) + 1);
           }  
        });

        
        colStudID.setCellValueFactory((TableColumn.CellDataFeatures<ShConfirmation, String> p)
                -> new SimpleStringProperty(p.getValue().getShApplicant().getStudIdnum()));
        
        colStudname.setCellValueFactory((TableColumn.CellDataFeatures<ShConfirmation, String> p)
                -> new SimpleStringProperty(p.getValue().getShApplicant().getAppLname() +", "+ p.getValue().getShApplicant().getAppFname() ));
        
        colStudStrand.setCellValueFactory(new PropertyValueFactory<>("strandCode"));
        

        tblApplicant.setItems(listApplicantConfirmed); 
    }
    

    @FXML
    private void actionShowApplicantList(ActionEvent event) {
        loadApplicantsInTable();
    }

    @FXML
    private void actionShowScheduleList(ActionEvent event) {
        setClassSection();
        loadOfferedSubjectsInTable();
    }

    @FXML
    private void actionRemoveApplicantToEnroll(ActionEvent event) {
        ShConfirmation selectedShConfirmation = this.tblApplicant.getSelectionModel().getSelectedItem(); 
        listApplicantConfirmed.remove(selectedShConfirmation);
    }
    
}
