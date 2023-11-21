/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomClassSections;
import entity.CustomEnrolledStudent;
import entity.CustomStudentSubject;
import entity.SaLedger;
import entity.SaLedgerHeader;
import entity.ShCClassStud;
import entity.ShClassInfo;
import entity.ShFee;
import entity.ShStudStrand;
import entity.ShStudlist;
import entity.ShStudlistId;
import entity.ShTermReg;
import interfaces.CustomEnrolledStudentInterface;
import static interfaces.CustomEnrolledStudentInterface.listEnrolledStudent;
import interfaces.CustomStudentSubjectInterface;
import interfaces.ShCClassStudInterface;
import interfaces.ShClassInfoInterface;
import static interfaces.ShClassInfoInterface.listClassSubjects;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.CustomEnrolledStudentModel;
import model.CustomStudentSubjectModel;
import model.SaLedgerHeader_SaLedgerModel;
import model.ShCClassStudModel;
import model.ShClassInfoModel;
import model.ShFeeModel;
import model.ShSectionsModel;
import model.ShStudStrand_ShStudlistModel;
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
public class StudentPromoteAndAssignScheduleBatchController implements Initializable, ShClassInfoInterface, CustomEnrolledStudentInterface, ShCClassStudInterface {

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
    private TableColumn<ShClassInfo, String> colRoomDayTime;
    @FXML
    private CheckBox checkAssess;
    @FXML
    private CheckBox checkAssignSubject;

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
    
    private ShStudStrand_ShStudlistModel shStudStrand_ShStudlistModel;
    private ShClassInfoModel shClassInfoModel;
    private ShCClassStudModel shCClassStudModel;
    private ShFeeModel shFeeModel;
    private SaLedgerHeader_SaLedgerModel saLedgerHeader_SaLedgerModel;
    private CustomEnrolledStudentModel customEnrolledStudentModel;
    private StrandsModel strandsModel;
    private StrandSectionModel sectionsModel;
    private ShTermRegModel flagsModel;
    
    private ShTermReg currentEnrollment;
    private ShTermReg currentPromotion;
    private ShClassInfo selected_shClassInfo_toPromoteFrom;
    private ShClassInfo selected_shClassInfo_toPromoteTo;
//    private String promotion_gradelevel;
    @FXML
    private TextField txtSYPromoteTo;
    @FXML
    private TextField txtSemPromoteTo;
    @FXML
    private ComboBox cbGradelevelPromoteTo;
    @FXML
    private ComboBox cbStrandPromoteTo;
    @FXML
    private ComboBox cbSectionPromoteTo;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shStudStrand_ShStudlistModel = new ShStudStrand_ShStudlistModel();
        shClassInfoModel = new ShClassInfoModel();
        shCClassStudModel = new ShCClassStudModel();
        shFeeModel = new ShFeeModel();
        saLedgerHeader_SaLedgerModel = new SaLedgerHeader_SaLedgerModel();
        customEnrolledStudentModel = new CustomEnrolledStudentModel();
        strandsModel = new StrandsModel();
        sectionsModel =  new StrandSectionModel();
        flagsModel =  new ShTermRegModel();
        
        currentEnrollment = flagsModel.getRowCurrentEnrollment();
        currentPromotion=flagsModel.getRowCurrentPromotion();
        
        txtSYPromoteTo.setText(currentEnrollment.getSyReg());
        txtSemPromoteTo.setText(currentEnrollment.getSemReg().toString());
        
        
        loadSYinCbox();
        loadSemInCbox();
        loadGradelevelInCbox();
        loadStrandsInCbox();
        loadSectionsInCbox();
        loadSectionsToPromoteInCbox();
        
        checkAssessmentFlag();
        
        cbSY.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        cbSem.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        cbGradelevel.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        cbStrand.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsInCbox());
        
        cbGradelevelPromoteTo.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsToPromoteInCbox());
        cbStrandPromoteTo.getSelectionModel().selectedItemProperty().addListener(e -> this.loadSectionsToPromoteInCbox());
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
    
    private void checkAssessmentFlag(){
        if(flagsModel.checkAssessmentOfSySem(currentEnrollment.getSyReg(), currentEnrollment.getSemReg().toString()))
            checkAssess.setVisible(true);
        else
            checkAssess.setVisible(true);
    }
    
    private void loadSYinCbox() {
        ObservableList<String> listSYforCbox = FXCollections.observableArrayList();
        
        String sy_val = "";
        for(int i=2016; i<=2050; i++){
            sy_val = i + "-" + (i+1);
            listSYforCbox.add(sy_val);
        }
        
        cbSY.setItems(listSYforCbox);
        cbSY.getSelectionModel().select(currentPromotion.getSyReg());

    }
    
    private void loadSemInCbox() {
        ObservableList<String> listSem = FXCollections.observableArrayList();
        listSem.add("1");
        listSem.add("2");
        cbSem.setItems(listSem);
        cbSem.getSelectionModel().select(currentPromotion.getSemReg());
    }
    
    private void loadGradelevelInCbox() {
        ObservableList<String> listGradelevel = FXCollections.observableArrayList();
        listGradelevel.add("11");
        listGradelevel.add("12");
        cbGradelevel.setItems(listGradelevel);
        cbGradelevel.getSelectionModel().select(0);
        
        cbGradelevelPromoteTo.setItems(listGradelevel);
        cbGradelevelPromoteTo.getSelectionModel().select(0);
    }
    
    private void loadStrandsInCbox(){
        ObservableList<String> listStrands = FXCollections.observableArrayList(strandsModel.getStrandsForCombobox());
        cbStrand.setItems(listStrands);
        cbStrand.getSelectionModel().select(0);
        
        cbStrandPromoteTo.setItems(listStrands);
        cbStrandPromoteTo.getSelectionModel().select(0);
    }
    
    private void loadSectionsInCbox(){
        ObservableList<String> listSections = FXCollections.observableArrayList(sectionsModel.getSectionsForCombobox(cbSY.getSelectionModel().getSelectedItem().toString(), 
                cbSem.getSelectionModel().getSelectedItem().toString(),cbGradelevel.getSelectionModel().getSelectedItem().toString(), 
                cbStrand.getSelectionModel().getSelectedItem().toString()));
        cbSection.setItems(listSections);
        cbSection.getSelectionModel().select(0);
    }
    
    private void loadSectionsToPromoteInCbox(){
        ObservableList<String> listSections = FXCollections.observableArrayList(sectionsModel.getSectionsForCombobox(txtSYPromoteTo.getText(), 
                txtSemPromoteTo.getText(),cbGradelevelPromoteTo.getSelectionModel().getSelectedItem().toString(), 
                cbStrandPromoteTo.getSelectionModel().getSelectedItem().toString()));
        cbSectionPromoteTo.setItems(listSections);
        cbSectionPromoteTo.getSelectionModel().select(0);
    }
    
    public void setClassSection(){
        selected_shClassInfo_toPromoteFrom = new ShClassInfo();
        selected_shClassInfo_toPromoteFrom.setClaSy(cbSY.getSelectionModel().getSelectedItem().toString());
        selected_shClassInfo_toPromoteFrom.setClaSem(Integer.parseInt(cbSem.getSelectionModel().getSelectedItem().toString()));
        selected_shClassInfo_toPromoteFrom.setClaYrLevel(Byte.parseByte(cbGradelevel.getSelectionModel().getSelectedItem().toString()));
        selected_shClassInfo_toPromoteFrom.setStrandcode(cbStrand.getSelectionModel().getSelectedItem().toString());
        
        
        String str_selected_section = cbSection.getSelectionModel().getSelectedItem().toString();
        selected_shClassInfo_toPromoteFrom.setClass_section(str_selected_section);
        
        selected_shClassInfo_toPromoteFrom.setStrandgroup(sectionsModel.getRowSection(selected_shClassInfo_toPromoteFrom.getClaSy(), 
            selected_shClassInfo_toPromoteFrom.getClaSem().toString(), selected_shClassInfo_toPromoteFrom.getClaYrLevel().toString(),
            selected_shClassInfo_toPromoteFrom.getStrandcode(), str_selected_section).getStrandgroup());  
        
        
        selected_shClassInfo_toPromoteTo = new ShClassInfo();
        selected_shClassInfo_toPromoteTo.setClaSy(txtSYPromoteTo.getText());
        selected_shClassInfo_toPromoteTo.setClaSem(Integer.valueOf(txtSemPromoteTo.getText()));
        selected_shClassInfo_toPromoteTo.setClaYrLevel(Byte.parseByte(cbGradelevelPromoteTo.getSelectionModel().getSelectedItem().toString()));
        selected_shClassInfo_toPromoteTo.setStrandcode(cbStrandPromoteTo.getSelectionModel().getSelectedItem().toString());
        
        str_selected_section = cbSectionPromoteTo.getSelectionModel().getSelectedItem().toString();
        selected_shClassInfo_toPromoteTo.setClass_section(str_selected_section);
        
        selected_shClassInfo_toPromoteTo.setStrandgroup(sectionsModel.getRowSection(selected_shClassInfo_toPromoteTo.getClaSy(), 
            selected_shClassInfo_toPromoteTo.getClaSem().toString(), selected_shClassInfo_toPromoteTo.getClaYrLevel().toString(),
            selected_shClassInfo_toPromoteTo.getStrandcode(), str_selected_section).getStrandgroup());  
        
        
        loadOfferedSubjectsInTable();
        loadStudentsInTable();
    }
    
    private void loadOfferedSubjectsInTable() {
        if (!listClassSubjects.isEmpty()) {
            listClassSubjects.clear();
        }
            
        System.out.println(selected_shClassInfo_toPromoteTo.getClaSy());
        System.out.println(selected_shClassInfo_toPromoteTo.getClaSem().toString());
        System.out.println(selected_shClassInfo_toPromoteTo.getClaYrLevel().toString());
        System.out.println(selected_shClassInfo_toPromoteTo.getStrandcode());
        System.out.println(selected_shClassInfo_toPromoteTo.getStrandgroup());
        listClassSubjects.addAll(shClassInfoModel.getResOfferedSubjectList(selected_shClassInfo_toPromoteTo.getClaSy(), selected_shClassInfo_toPromoteTo.getClaSem().toString(), 
                selected_shClassInfo_toPromoteTo.getClaYrLevel().toString(),selected_shClassInfo_toPromoteTo.getStrandcode(),selected_shClassInfo_toPromoteTo.getStrandgroup()));

        
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
    
    private void loadStudentsInTable() {
        if (!listEnrolledStudent.isEmpty()) {
            listEnrolledStudent.clear();
        }
        
       
//        promotion_gradelevel = "11";
//        if(cbGradelevel.getSelectionModel().getSelectedItem().toString().equals("11") && cbSem.getSelectionModel().getSelectedItem().toString().equals("2")){
//            promotion_gradelevel = "12";
//        }else if(cbGradelevel.getSelectionModel().getSelectedItem().toString().equals("12") && cbSem.getSelectionModel().getSelectedItem().toString().equals("1")){
//            promotion_gradelevel = "11";
//        }else if(cbGradelevel.getSelectionModel().getSelectedItem().toString().equals("12") && cbSem.getSelectionModel().getSelectedItem().toString().equals("2")){
//            promotion_gradelevel = "12";
//        }
        
        
      
        
        listEnrolledStudent.addAll(customEnrolledStudentModel.getResEnrolledStudentForPromotion(selected_shClassInfo_toPromoteFrom.getClaSy(), selected_shClassInfo_toPromoteFrom.getClaSem().toString(), 
                selected_shClassInfo_toPromoteFrom.getClaYrLevel().toString(), selected_shClassInfo_toPromoteFrom.getStrandcode(), selected_shClassInfo_toPromoteFrom.getStrandgroup(), 
                selected_shClassInfo_toPromoteTo.getClaSy(), selected_shClassInfo_toPromoteTo.getClaSem().toString(), selected_shClassInfo_toPromoteTo.getClaYrLevel().toString(), selected_shClassInfo_toPromoteTo.getStrandcode(),
                selected_shClassInfo_toPromoteTo.getStrandgroup()));

        
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
    
    
    @FXML
    private void actionShowStudentList(ActionEvent event){
        this.setClassSection();
    }
    
   
    
    
    @FXML
    public void actionRemoveStudentToPromote(ActionEvent event) throws Exception {       
        CustomEnrolledStudent selectedCustomEnrolledStudent = tblStudent.getSelectionModel().getSelectedItem();
        
        listEnrolledStudent.remove(selectedCustomEnrolledStudent);
    }
    
    @FXML
    public void actionBatchPromote(ActionEvent event) throws Exception {    
        ObservableList<ShFee> listShFee = FXCollections.observableArrayList();
        ObservableList<ShStudStrand> listShStudStrand = FXCollections.observableArrayList();
        ObservableList<ShStudlist> listShStudlist = FXCollections.observableArrayList();
        ObservableList<ShCClassStud> listShCClassStud = FXCollections.observableArrayList();
        ObservableList<SaLedgerHeader> listSaLedgerHeader = FXCollections.observableArrayList();
        ObservableList<SaLedger> listSaLedger = FXCollections.observableArrayList();
        String str_stud_ids = "";
        
         
        listShFee = shFeeModel.getResFeeByGradelevelAndStrand(selected_shClassInfo_toPromoteTo.getClaSy(), selected_shClassInfo_toPromoteTo.getClaSem().toString(), 
                selected_shClassInfo_toPromoteTo.getClaYrLevel().toString(), selected_shClassInfo_toPromoteTo.getStrandcode());
        
        String tot_amount = shFeeModel.getTotalFeeByGradelevelAndStrand(selected_shClassInfo_toPromoteTo.getClaSy(), selected_shClassInfo_toPromoteTo.getClaSem().toString(), 
                selected_shClassInfo_toPromoteTo.getClaYrLevel().toString(), selected_shClassInfo_toPromoteTo.getStrandcode());
        

        Calendar now = Calendar.getInstance();   // Gets the current date and time
        String glyear = Integer.toString(now.get(Calendar.YEAR)); 
        String glperiod = Integer.toString(now.get(Calendar.MONTH) + 1); 
        String glday = Integer.toString(now.get(Calendar.DATE)); 
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date currDate = new Date();
//        String currDate = Integer.toString(glyear)+"-"+Integer.toString(glperiod)+"-"+Integer.toString(glday);
        
        String syear = "";
        for(CustomEnrolledStudent row_student: listEnrolledStudent){
            syear = cbSY.getSelectionModel().getSelectedItem().toString() +"-"+ cbSem.getSelectionModel().getSelectedItem().toString();
            
            SaLedgerHeader saLedgerHeader = new SaLedgerHeader();
            saLedgerHeader.setStudentgroup("SH");
            saLedgerHeader.setStudentid(row_student.getStud_idnum());
            saLedgerHeader.setSchoolyear(syear);
            saLedgerHeader.setYrlevel(Integer.parseInt(selected_shClassInfo_toPromoteTo.getClaYrLevel().toString()));
            saLedgerHeader.setGlyear(glyear);
            saLedgerHeader.setAssessedAmount(Double.parseDouble(tot_amount));
            
            listSaLedgerHeader.add(saLedgerHeader);
            for(ShFee shFee: listShFee){
                

                SaLedger saLedger = new SaLedger();
                

                saLedger.setSaLedgerHeader(saLedgerHeader);
                saLedger.setStudentgroup("SH");
                saLedger.setStudentid(row_student.getStud_idnum());

                saLedger.setSource("AS");
                saLedger.setReference("SH" + row_student.getStud_idnum());
                saLedger.setItemid(shFee.getCode());
                saLedger.setDebit(shFee.getFee());
                saLedger.setCredit(BigDecimal.valueOf(0.00));
                saLedger.setParticular(shFee.getItemname());
                
               
//                if(selected_shClassInfo_toPromoteFrom.getClaSem() == 3){ //--- summer
//                    saLedger.setSyear(shFee.getTerm() +"-"+ shFee.getSem().toString());
//                    syear = shFee.getTerm() +"-"+ shFee.getSem().toString();
//                }else{
//                    saLedger.setSyear(shFee.getTerm());
//                    syear = shFee.getTerm();
//                }
                
                saLedger.setSyear(syear);
                saLedger.setTransdate(currDate);
                saLedger.setGlyear(glyear);
                saLedger.setGlperiod(glperiod);
                
                
                listSaLedger.add(saLedger);
            }
        
            
            if(!str_stud_ids.equals(""))
                str_stud_ids += ",";
            
            str_stud_ids += "'" + row_student.getStud_idnum() + "'";
           
            ShStudStrand add_shStudStrand = new ShStudStrand();
            add_shStudStrand.setSsId(selected_shClassInfo_toPromoteTo.getClaSy() +"_"+ selected_shClassInfo_toPromoteTo.getClaSem() +"_"+ row_student.getStud_idnum());
            add_shStudStrand.setStudIdnum(row_student.getStud_idnum());
            add_shStudStrand.setStrandCode(selected_shClassInfo_toPromoteTo.getStrandcode());
            add_shStudStrand.setStrandGroup(selected_shClassInfo_toPromoteTo.getStrandgroup());
            add_shStudStrand.setSsYrLevel(Integer.parseInt(selected_shClassInfo_toPromoteTo.getClaYrLevel().toString()));
            
//            if(selected_shClassInfo_toPromoteFrom.getClaYrLevel().equals("11") && selected_shClassInfo_toPromoteFrom.getClaSem()==1)
//                add_shStudStrand.setSsStatus("NEW STUDENT");
//            else
            add_shStudStrand.setSsStatus("OLD STUDENT");
            
            add_shStudStrand.setSsSy(selected_shClassInfo_toPromoteTo.getClaSy());
            add_shStudStrand.setSsSem(selected_shClassInfo_toPromoteTo.getClaSem());

            
            ShStudlistId add_shStudlistId = new ShStudlistId();
            add_shStudlistId.setSy(selected_shClassInfo_toPromoteTo.getClaSy());
            add_shStudlistId.setSem(selected_shClassInfo_toPromoteTo.getClaSem());
            add_shStudlistId.setStudIdnum(row_student.getStud_idnum());
            
            ShStudlist add_shStudlist = new ShStudlist();
            add_shStudlist.setId(add_shStudlistId);
            add_shStudlist.setStudLname(row_student.getStud_lname());
            add_shStudlist.setStudFname(row_student.getStud_fname());
            add_shStudlist.setStudSuffix(row_student.getStud_suffix());
            add_shStudlist.setStudMi(row_student.getStud_mi());
            add_shStudlist.setStatus(1);
            add_shStudlist.setStatusIn(1);
            add_shStudlist.setAppNo(row_student.getApp_no());
            row_student.setSy(selected_shClassInfo_toPromoteTo.getClaSy());
            row_student.setSem(selected_shClassInfo_toPromoteTo.getClaSem().toString());
            
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
            
            //--- objects add in list
            listShStudStrand.add(add_shStudStrand);
            listShStudlist.add(add_shStudlist);
        }
        
        
        Session session_;
        session_ = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session_.beginTransaction();
        boolean success = false;
        
        try{
            shStudStrand_ShStudlistModel.saveStudentForPromotionBatch(selected_shClassInfo_toPromoteTo.getClaSy(), selected_shClassInfo_toPromoteTo.getClaSem().toString(), str_stud_ids, listShStudStrand, listShStudlist, session_);

            if(checkAssignSubject.isSelected())
                shCClassStudModel.saveStudentSubjectsBatch(selected_shClassInfo_toPromoteTo.getClaSy(), selected_shClassInfo_toPromoteTo.getClaSem().toString(), str_stud_ids, listShCClassStud, session_);

            if(checkAssess.isSelected())
                saLedgerHeader_SaLedgerModel.saveStudentSaLedgerHeaderBatch(syear, str_stud_ids, listSaLedgerHeader, listSaLedger, session_);
            
            
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
        }
        else{
            this.showMessage(success, "Error", "Error", "Error occured");
        }
        loadStudentsInTable();

    }
}
