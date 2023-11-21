/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomEnrolledStudent;
import entity.ShCClassStud;
import entity.ShClassInfo;
import entity.ShTermReg;
import interfaces.CustomEnrolledStudentInterface;
import static interfaces.CustomEnrolledStudentInterface.listEnrolledStudent;
import interfaces.ShClassInfoInterface;
import static interfaces.ShClassInfoInterface.listClassSubjects;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.CustomEnrolledStudentModel;
import model.ShCClassStudModel;
import model.ShClassInfoModel;
import model.ShTermRegModel;
import model.StrandSectionModel;
import model.StrandsModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import shregistrarjavafxml.HibernateUtil;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class StudentAssignScheduleBatchController implements Initializable, ShClassInfoInterface, CustomEnrolledStudentInterface {

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
    private Button btnShowStudentList;
    @FXML
    private Button btnBatchPromote;
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
    private TableColumn<ShClassInfo, String> colTimeStart;
    @FXML
    private TableColumn<ShClassInfo, String> colTimeEnd;
    @FXML
    private TableColumn<ShClassInfo, String> colRoom;
    @FXML
    private TableView<CustomEnrolledStudent> tblStudent;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colStudNo;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colStudID;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colStudname;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colStudStrand;
    @FXML
    private TableColumn<CustomEnrolledStudent, String> colStudStrandgroup;
    @FXML
    private Button btnRemoveStudent;
    
     
    private ShClassInfoModel shClassInfoModel;
    private ShCClassStudModel shCClassStudModel;
    private CustomEnrolledStudentModel customEnrolledStudentModel;
    private StrandsModel strandsModel;
    private StrandSectionModel sectionsModel;
    private ShTermRegModel flagsModel;
    
    private ShTermReg currentEnrollment;
    private ShClassInfo selected_shClassInfo;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shClassInfoModel = new ShClassInfoModel();
        shCClassStudModel = new ShCClassStudModel();
        customEnrolledStudentModel = new CustomEnrolledStudentModel();
        strandsModel = new StrandsModel();
        sectionsModel =  new StrandSectionModel();
        flagsModel =  new ShTermRegModel();
        
        currentEnrollment = flagsModel.getRowCurrentEnrollment();
        
        loadSYinCbox();
        loadSemInCbox();
        loadGradelevelInCbox();
        loadStrandsInCbox();
        loadSectionsInCbox();

        
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
        cbSem.setItems(listSem);
        cbSem.getSelectionModel().select(currentEnrollment.getSemReg());
    }
    
    private void loadGradelevelInCbox() {
        ObservableList<String> listGradelevel = FXCollections.observableArrayList();
        listGradelevel.add("11");
        listGradelevel.add("12");
        cbGradelevel.setItems(listGradelevel);
        cbGradelevel.getSelectionModel().select(0);
    }
    
    private void loadStrandsInCbox(){
        ObservableList<String> listStrands = FXCollections.observableArrayList(strandsModel.getStrandsForCombobox());
        cbStrand.setItems(listStrands);
        cbStrand.getSelectionModel().select(0);
    }
    
    private void loadSectionsInCbox(){
        ObservableList<String> listSections = FXCollections.observableArrayList(sectionsModel.getSectionsForCombobox(cbSY.getSelectionModel().getSelectedItem().toString(), 
                cbSem.getSelectionModel().getSelectedItem().toString(),cbGradelevel.getSelectionModel().getSelectedItem().toString(), 
                cbStrand.getSelectionModel().getSelectedItem().toString()));
        cbSection.setItems(listSections);
        cbSection.getSelectionModel().select(0);
    }
    
    public void setClassSection(){
        selected_shClassInfo = new ShClassInfo();
        selected_shClassInfo.setClaSy(cbSY.getSelectionModel().getSelectedItem().toString());
        selected_shClassInfo.setClaSem(Integer.parseInt(cbSem.getSelectionModel().getSelectedItem().toString()));
        selected_shClassInfo.setClaYrLevel(Byte.parseByte(cbGradelevel.getSelectionModel().getSelectedItem().toString()));
        selected_shClassInfo.setStrandcode(cbStrand.getSelectionModel().getSelectedItem().toString());
        
        
        String str_selected_section = cbSection.getSelectionModel().getSelectedItem().toString();
        selected_shClassInfo.setClass_section(str_selected_section);
        
        selected_shClassInfo.setStrandgroup(sectionsModel.getRowSection(selected_shClassInfo.getClaSy(), 
            selected_shClassInfo.getClaSem().toString(), selected_shClassInfo.getClaYrLevel().toString(),
            selected_shClassInfo.getStrandcode(), str_selected_section).getStrandgroup());  
        
        loadOfferedSubjectsInTable();
        loadStudentsInTable();
       
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
        colTimeStart.setCellValueFactory(new PropertyValueFactory<>("claTStartdesc"));
        colTimeEnd.setCellValueFactory(new PropertyValueFactory<>("claTEnddesc"));
        colRoom.setCellValueFactory(new PropertyValueFactory<>("claRoom"));

        tblScheduleToAssign.setItems(listClassSubjects); 
    }
    
    private void loadStudentsInTable() {
        if (!listEnrolledStudent.isEmpty()) {
            listEnrolledStudent.clear();
        }

        
        listEnrolledStudent.addAll(customEnrolledStudentModel.getResEnrolledStudentForPromotion(selected_shClassInfo.getClaSy(), selected_shClassInfo.getClaSem().toString(), 
                selected_shClassInfo.getClaYrLevel().toString(), selected_shClassInfo.getStrandcode(), selected_shClassInfo.getStrandgroup(), "", "", "", "", ""));

        
        colStudNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CustomEnrolledStudent, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<CustomEnrolledStudent, String> p) {
               return new ReadOnlyObjectWrapper(tblStudent.getItems().indexOf(p.getValue()) + 1);
           }  
        });

//        colStudNo.setCellValueFactory(     
//            new PropertyValueFactory<>("customSelect")
//        );   
        
        colStudID.setCellValueFactory(new PropertyValueFactory<>("stud_idnum"));
        colStudname.setCellValueFactory(new PropertyValueFactory<>("stud_fullname"));
        colStudStrand.setCellValueFactory(new PropertyValueFactory<>("strand_code"));
        colStudStrandgroup.setCellValueFactory(new PropertyValueFactory<>("strand_group"));

        tblStudent.setItems(listEnrolledStudent); 
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
    
    private boolean validateInput() {

        String errorMessage = "";

        if (this.cbGradelevel.getSelectionModel().getSelectedItem().toString().equals("")) {
            errorMessage += "Please select gradelevel!\n";
        }
        
        if (this.cbStrand.getSelectionModel().getSelectedItem().toString().equals("")) {
            errorMessage += "Please select strand!\n";
        }
        
        if (this.cbSection.getSelectionModel().getSelectedItem().toString().equals("")) {
            errorMessage += "Please select section!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            showMessage(false,"Invalid Fields","Please correct invalid fields",errorMessage);
            return false;
        }
    }
    

    @FXML
    private void actionShowStudentList(ActionEvent event) throws Exception {    
        setClassSection();
        loadOfferedSubjectsInTable();
        loadStudentsInTable();
    }
    

    @FXML
    private void actionRemoveStudentToPromote(ActionEvent event) {
        CustomEnrolledStudent selectedCustomEnrolledStudent = tblStudent.getSelectionModel().getSelectedItem();
        
        listEnrolledStudent.remove(selectedCustomEnrolledStudent);
    }
    
     @FXML
    private void actionBatchAssignStudentSchedule(ActionEvent event) {
        if (validateInput()) {
            Boolean success = false;
            ObservableList<ShCClassStud> listShCClassStud = FXCollections.observableArrayList();
            String str_stud_ids = "";

            for(CustomEnrolledStudent row_student: listEnrolledStudent){
                if(!str_stud_ids.equals(""))
                    str_stud_ids += ",";

                str_stud_ids += "'" + row_student.getStud_idnum() + "'";

                for(ShClassInfo row_schedule: listClassSubjects){    
                    ShCClassStud add_shCClassStud = new ShCClassStud();

                    add_shCClassStud.setCclassId(row_schedule.getClassid() + "_" + row_student.getStud_idnum());
                    add_shCClassStud.setCsIdnum(row_student.getStud_idnum());
                    add_shCClassStud.setCsCrsCode(row_schedule.getClaCrsCode());
                    add_shCClassStud.setCsSection(row_schedule.getClaSection());
                    add_shCClassStud.setCsStrandgroup(row_schedule.getStrandgroup());
                    add_shCClassStud.setCsSy(row_schedule.getClaSy());
                    add_shCClassStud.setCsSem(row_schedule.getClaSem());
                    add_shCClassStud.setCsYrLevel(Integer.valueOf(row_schedule.getClaYrLevel()));

                    listShCClassStud.add(add_shCClassStud);
                }
            }
            
            Session session_;
            session_ = HibernateUtil.getSessionFactory().openSession();
            Transaction txn = session_.beginTransaction();
            try{
                shCClassStudModel.saveStudentSubjectsBatch(selected_shClassInfo.getClaSy(), selected_shClassInfo.getClaSem().toString(), str_stud_ids, listShCClassStud, session_);
                txn.commit();
                success = true;
            }
            catch (Exception e){
                txn.rollback();
                e.printStackTrace();
                success = false;
            } finally {
                session_.close();
            }
            
            
            if(success==true){

                this.showMessage(success, "Successful", "Successful!", "Successfully assigned batch schedule.");
            }else{
                this.showMessage(success, "Error", "Error", "Error occured");
            }
        
        }
        
        
    }
    
}
