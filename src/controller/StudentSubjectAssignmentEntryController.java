/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import config.DBUtilities;
import entity.CustomEnrolledStudent;
import entity.CustomStudentSubject;
import entity.JasperObject;
import entity.SaLedger;
import entity.SaLedgerHeader;
import entity.ShApplicant;
import entity.ShCClassStud;
import entity.ShConfirmation;
import entity.ShFee;
import entity.ShStudStrand;
import entity.ShStudlist;
import entity.ShStudlistId;
import entity.ShTermReg;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.ShApplicantModel;
import model.StrandsModel;
import model.ShCClassStudModel;
import interfaces.CustomStudentSubjectInterface;
import interfaces.ShCClassStudInterface;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JDialog;
import model.CustomEnrolledStudentModel;
import model.CustomStudentSubjectModel;
import model.SaLedgerHeader_SaLedgerModel;
import model.ShClassInfoModel;
import model.ShFeeModel;
import model.ShSectionsModel;
import model.ShStudStrand_ShStudlistModel;
import model.ShTermRegModel;
import model.StrandSectionModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import others.JRViewerFxController;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class StudentSubjectAssignmentEntryController implements Initializable,ShCClassStudInterface {

    @FXML
    private TextField txtFullname;
    @FXML
    private TextField txtDOB;
    @FXML
    private TextField txtGender;
    @FXML
    private TextField txtCivilStatus;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtSY;
    @FXML
    private TextField txtSem;
    @FXML
    private TextField txtGradelevel;
    @FXML
    private ComboBox cbStrand;
    @FXML
    private TextField txtStrandgroup;
    @FXML
    private Button btnSaveStrand;
    @FXML
    private Button btnSaveSubjects;
    @FXML
    private Button btnRefreshList;
    @FXML
    private CheckBox checkTransferee;
    @FXML 
    private CheckBox checkAssess;
    @FXML
    private CheckBox checkAssignSubject;
    @FXML
    private TableView<ShCClassStud> tblSubject;
    @FXML
    private TableColumn<ShCClassStud, String> colNo,colCode,colDesc,colUnit,colSection,colGradelevel,colRoomDayTime;
    @FXML
    private Label lblNote;
    
    private StrandSectionModel strandSectionModel;
    private CustomEnrolledStudentModel customEnrolledStudentModel;
    private StrandsModel strandsModel;
    private ShCClassStudModel shCClassStudModelModel;
    private ShFeeModel shFeeModel;
    private ShApplicantModel applicantModel;
    private ShStudStrand_ShStudlistModel shStudStrand_ShStudlistModel;
    private SaLedgerHeader_SaLedgerModel saLedgerHeader_SaLedgerModel;
    private ShTermRegModel flagsModel;
    
    private ShTermReg currentEnrollment, currentAssessment;
    private ShApplicant selectedShApplicant;
    private ShConfirmation selectedShConfirmation;
    private CustomEnrolledStudent selectedCustomEnrolledStudent;
    
    @FXML
    private String str_student_status;
//    private JRViewerFxMode printMode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        strandSectionModel = new StrandSectionModel();
        strandsModel = new StrandsModel();
        applicantModel =  new ShApplicantModel();
        shCClassStudModelModel = new ShCClassStudModel();
        shFeeModel = new ShFeeModel();
        shStudStrand_ShStudlistModel = new ShStudStrand_ShStudlistModel();
        saLedgerHeader_SaLedgerModel = new SaLedgerHeader_SaLedgerModel();
        flagsModel = new ShTermRegModel();
  
        currentEnrollment = flagsModel.getRowCurrentEnrollment();
        currentAssessment = flagsModel.getRowCurrentAssessment();
        
        if(currentEnrollment.getIsAssessment())
            this.checkAssess.setSelected(true);
        else
            this.checkAssess.setSelected(false);
        
        this.checkAssess.setSelected(false);
        
        loadStrandsInCbox();
        str_student_status = "";
    }   
    
    public void setStudentDetails(CustomEnrolledStudent selectedStudent, String promote_gradelevel) { 
        
        selectedCustomEnrolledStudent = selectedStudent;
        this.selectedShApplicant = applicantModel.getRowApplicantByStudID(selectedCustomEnrolledStudent.getStud_idnum());
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd, yyyy");
        
        System.out.println(selectedCustomEnrolledStudent.getStud_idnum());
        txtFullname.setText(selectedShApplicant.getAppLname().trim() +", "+ selectedShApplicant.getAppFname().trim() +" "+ selectedShApplicant.getAppMiddlename().trim());
        txtDOB.setText(sdf.format(selectedShApplicant.getAppBdate()));
//        txtAddress.setText(selectedShApplicant.getAppAddress().trim()); 
        txtGender.setText(selectedShApplicant.getAppSex().trim());
        
        setStudentStrand(promote_gradelevel);
        loadSubjects();
        
    }
    
    public void setApplicantDetails(ShConfirmation selectedShConfirmation, String promote_gradelevel) { 
        this.selectedShConfirmation = selectedShConfirmation;
        this.selectedShApplicant = selectedShConfirmation.getShApplicant();
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd, yyyy");
        
        
        txtFullname.setText(selectedShApplicant.getAppLname().trim() +", "+ selectedShApplicant.getAppFname().trim() +" "+ selectedShApplicant.getAppMiddlename().trim());
        txtDOB.setText(sdf.format(selectedShApplicant.getAppBdate()));
//        txtAddress.setText(selectedShApplicant.getAppAddress().trim()); 
        txtGender.setText(selectedShApplicant.getAppSex().trim());
        
        setApplicantStrand(promote_gradelevel);
//        loadSubjects();
        
    }
    
    private void setStudentStrand(String promote_gradelevel){
        txtSY.setText(currentEnrollment.getSyReg());
        //txtSY.setText("2020-2021");
        txtSem.setText(currentEnrollment.getSemReg().toString());
        
        
        txtGradelevel.setText(promote_gradelevel);
        //txtGradelevel.setText("12");
        
        cbStrand.getSelectionModel().select(selectedCustomEnrolledStudent.getStrand_code());
        txtStrandgroup.setText(selectedCustomEnrolledStudent.getStrand_group());
        //txtStrandgroup.setText("Z");
    }
    
    private void setApplicantStrand(String promote_gradelevel){
        txtSY.setText(currentEnrollment.getSyReg());
        //txtSY.setText("2020-2021");
        txtSem.setText(currentEnrollment.getSemReg().toString());
        //txtSem.setText("1");
        
        
        txtGradelevel.setText(promote_gradelevel);
        //txtGradelevel.setText("11");
        
        cbStrand.getSelectionModel().select(this.selectedShConfirmation.getStrandCode());
        txtStrandgroup.setText(selectedShConfirmation.getStrandGroup());
        //txtStrandgroup.setText("Z");
    }
    
    
    private void loadStrandsInCbox(){
        ObservableList<String> listStrands = FXCollections.observableArrayList(strandsModel.getStrandsForCombobox());
        cbStrand.setItems(listStrands);
        cbStrand.getSelectionModel().select("all");
    }
    

    private void loadSubjects(){
        boolean student_has_subjects = shCClassStudModelModel.hasSubjects(this.currentEnrollment.getSyReg(), this.currentEnrollment.getSemReg(), 
                selectedShApplicant.getStudIdnum());
       
        if(student_has_subjects == true){
            loadStudentSubjectsInTable();
            lblNote.setText("Below are the assigned subjects to students. When enroll student button is clicked, subjects will be overridden.");
        }else{
            loadOfferedSubjectsInTable();
            lblNote.setText("Student has no assigned subjects(s) yet. Below are the offered subjects for his/her section.");
        }
    }
    
    private void loadStudentSubjectsInTable() {
        if (!listStudentSubjects.isEmpty()) {
            listStudentSubjects.clear();
        }
        listStudentSubjects.addAll(shCClassStudModelModel.getResStudentSubjectList(txtSY.getText(), txtSem.getText(), 
                selectedCustomEnrolledStudent.getGrade_level(),selectedShApplicant.getStudIdnum()));
        
                
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCClassStud, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCClassStud, String> p) {
               return new ReadOnlyObjectWrapper(tblSubject.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        colCode.setCellValueFactory(new PropertyValueFactory<>("csCrsCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("subject_desc"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("subject_unit"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("csSection"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("csYrLevel"));
        colRoomDayTime.setCellValueFactory(new PropertyValueFactory<>("subject_room_daytime_list"));

        tblSubject.setItems(listStudentSubjects); 
    }
    
    private void loadOfferedSubjectsInTable() {
      
        if (!listStudentSubjects.isEmpty()) {
            listStudentSubjects.clear();
        }
        listStudentSubjects.addAll(shCClassStudModelModel.getResOfferedSubjectList(txtSY.getText(), txtSem.getText(), 
                selectedCustomEnrolledStudent.getGrade_level(),"NA",txtStrandgroup.getText()));

        
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCClassStud, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCClassStud, String> p) {
               return new ReadOnlyObjectWrapper(tblSubject.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        colCode.setCellValueFactory(new PropertyValueFactory<>("csCrsCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("subject_desc"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("subject_unit"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("csSection"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("csYrLevel"));
        colRoomDayTime.setCellValueFactory(new PropertyValueFactory<>("subject_room_daytime_list"));


        tblSubject.setItems(listStudentSubjects); 
    }
    
    private void reloadSubjectsInTable() {
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCClassStud, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCClassStud, String> p) {
               return new ReadOnlyObjectWrapper(tblSubject.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        colCode.setCellValueFactory(new PropertyValueFactory<>("csCrsCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("subject_desc"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("subject_unit"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("csSection"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("csYrLevel"));
        colRoomDayTime.setCellValueFactory(new PropertyValueFactory<>("subject_room_daytime_list"));

        tblSubject.setItems(listStudentSubjects); 
    }
    
    
    
    
    private void saveStudentLedger(){
        String sy = currentEnrollment.getSyReg();
        String sem = currentEnrollment.getSemReg().toString();
        
        ObservableList<ShFee> listShFee = FXCollections.observableArrayList();
        SaLedgerHeader saLedgerHeader = new SaLedgerHeader();
        ObservableList<SaLedger> listSaLedger = FXCollections.observableArrayList();
        
        
        

        
        listShFee = shFeeModel.getResFeeByGradelevelAndStrand(currentEnrollment.getSyReg(), currentEnrollment.getSemReg().toString(), 
                txtGradelevel.getText(), cbStrand.getSelectionModel().getSelectedItem().toString());
        

        Calendar now = Calendar.getInstance();   // Gets the current date and time
        String glyear = Integer.toString(now.get(Calendar.YEAR)); 
        String glperiod = Integer.toString(now.get(Calendar.MONTH) + 1); 
        String glday = Integer.toString(now.get(Calendar.DATE)); 
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date currDate = new Date();
        
        String syear = txtSY.getText() +"-"+ txtSem.getText();
       
        
        String tot_fee = shFeeModel.getTotalFeeByGradelevelAndStrand(txtSY.getText(), txtSem.getText(), txtGradelevel.getText(), cbStrand.getSelectionModel().getSelectedItem().toString());
        
        
        
        saLedgerHeader.setStudentgroup("SH");
        saLedgerHeader.setStudentid(selectedShApplicant.getStudIdnum());
        saLedgerHeader.setSchoolyear(syear);
        saLedgerHeader.setGlyear(glyear);
        saLedgerHeader.setAssessedAmount(Double.parseDouble(tot_fee));
        saLedgerHeader.setYrlevel(Integer.parseInt(txtGradelevel.getText()));
        
        for(ShFee shFee: listShFee){

            SaLedger saLedger = new SaLedger();

            saLedger.setSaLedgerHeader(saLedgerHeader);
            saLedger.setStudentgroup("SH");
            saLedger.setStudentid(selectedShApplicant.getStudIdnum());

            saLedger.setSource("AS");
            saLedger.setReference("SH" + selectedShApplicant.getStudIdnum());
            saLedger.setItemid(shFee.getCode());
            saLedger.setDebit(shFee.getFee());
            saLedger.setCredit(new BigDecimal(0));
            saLedger.setParticular(shFee.getItemname());

            saLedger.setSyear(syear);

            saLedger.setTransdate(currDate);
            saLedger.setGlyear(glyear);
            saLedger.setGlperiod(glperiod);
            saLedger.setRemark("");
            saLedger.setIsposted(false);
            saLedger.setIscancel(false);
            saLedger.setIsrefund(false);
            saLedger.setIsdiscount(false);
            saLedger.setDiscountcode("");

            listSaLedger.add(saLedger);
        }
        
       
        saLedgerHeader_SaLedgerModel.saveStudentSaLedgerHeader(syear, selectedShApplicant.getStudIdnum(), saLedgerHeader, listSaLedger);
        
        if(checkAssess.isSelected()){
            
        }
    }
    
    private void printAssessment(){
        
        HashMap parameters = new HashMap();
        
        //get fees and amounts from tblCharges
        String tui_fee,tui_amt,misc_fee,misc_amt,comp_fee,comp_amt,stud_section;  
        
        
//        tui_fee = tblCharges.getValueAt(0,2).toString();
//        tui_amt = tblCharges.getValueAt(0,3).toString();
//        misc_fee = tblCharges.getValueAt(1,2).toString();
//        misc_amt = tblCharges.getValueAt(1,3).toString(); 
////        comp_fee =tblCharges.getValueAt(2,2).toString();
////        comp_amt = tblCharges.getValueAt(2,3).toString();
//        stud_section = new DaoStudent().getStudentSection(txtStudID.getText(), cbYrlevel.getSelectedItem().toString(), cbSY.getSelectedItem().toString(), cbSem.getSelectedItem().toString());
//        
        String section_name = strandSectionModel.getStrandgroupSectionDesc(txtSY.getText(), txtSem.getText(), txtGradelevel.getText(), cbStrand.getSelectionModel().getSelectedItem().toString(), this.txtStrandgroup.getText());
        DateFormat dateFormat = new SimpleDateFormat("MMMMM dd, yyyy");
        Date date = new Date();
        String strDate = dateFormat.format(date); //2016/11/16 12:08:43

        //set parameters
        parameters.put("sy", txtSY.getText());
        parameters.put("sem", txtSem.getText());
        parameters.put("gradelevel", txtGradelevel.getText());
        parameters.put("stud_id",selectedShApplicant.getStudIdnum());

        
        parameters.put("curr_date", strDate);
        parameters.put("stud_section", section_name);
        parameters.put("stud_fullname", selectedShApplicant.getAppLname().trim() +", "+ selectedShApplicant.getAppFname().trim() +" "+ selectedShApplicant.getAppSuffix().trim() +" "+ selectedShApplicant.getAppMi().trim());
        parameters.put("stud_strand", cbStrand.getSelectionModel().getSelectedItem().toString());
        parameters.put("stud_status", str_student_status);
        try {
            List<Object[]> records = new ArrayList<>();
            
            ObservableList<ShFee> listShFee = shFeeModel.getResFeeByGradelevelAndStrand(currentEnrollment.getSyReg(), currentEnrollment.getSemReg().toString(), 
                txtGradelevel.getText(), cbStrand.getSelectionModel().getSelectedItem().toString());
             
                        
                        
//          String source = "D:\\Systems Working\\SHRegistrarJAVAFxml\\src\\report\\enrollment_form.jrxml"; 
            InputStream source=this.getClass().getResourceAsStream("/report/enrollment_form.jrxml");    
            if(txtSem.getText().equals("3")){
                /* Summer Version of form */
                source=this.getClass().getResourceAsStream("/report/enrollment_form_summer.jrxml");                
            }
//          String source = "D:\\report\\enrollment_form.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(source);
           
//          File f = new File("report/enrollment_form.jrxml");
//          JasperReport jr = JasperCompileManager.compileReport(f.getAbsolutePath());
         
          
            List<JasperObject> plist = new ArrayList<>();
          
            
            ObservableList<ShCClassStud> listSubjects = shCClassStudModelModel.getTotalSubjectCodes(txtSY.getText(), Integer.parseInt(txtSem.getText()), selectedShApplicant.getStudIdnum());
            
            /* Count total subjects to calculate fees depending on # of subjects (Sem 3 Summer ONLY) */
            int tot_subj = shCClassStudModelModel.getTotalSubjects(txtSY.getText(), Integer.parseInt(txtSem.getText()), selectedShApplicant.getStudIdnum());    
            double fee_summer_temp = 0;
            BigDecimal fee_summer;
            
            
          
          String tot_amount = "";          
          if(txtSem.getText().equals("3")){
                /* Summer Version */
                tot_amount = shFeeModel.getTotalSummerFee(currentEnrollment.getSyReg(), currentEnrollment.getSemReg().toString(), txtGradelevel.getText(), cbStrand.getSelectionModel().getSelectedItem().toString(), selectedShApplicant.getStudIdnum());
            }
            else{ /* Original */
                tot_amount = shFeeModel.getTotalFeeByGradelevelAndStrand(currentEnrollment.getSyReg(), currentEnrollment.getSemReg().toString(), txtGradelevel.getText(), cbStrand.getSelectionModel().getSelectedItem().toString());
            }
            
            int subj_count = 1;
            parameters.put("fee_desc_aca_1", "");              
            parameters.put("fee_desc_aca_2", "");  
            parameters.put("fee_desc_aca_3", "");  
            for(ShCClassStud subjects: listSubjects){                
                parameters.put("fee_desc_aca_"+subj_count, subjects.getCsCrsCode());
                subj_count++;
            }
          
          for(ShFee shFee: listShFee){
//              JasperObject obj = new JasperObject();
//              obj.setFee(Double.parseDouble(shFee.getFee().toString()));
//              obj.setItemname(shFee.getItemname());
//              plist.add(obj);
              
              if(shFee.getCode().equalsIgnoreCase("MISC")){
                  parameters.put("fee_desc_misc", shFee.getItemname());
                  parameters.put("fee_amount_misc", shFee.getFee().toString());
                  
              }else if(shFee.getCode().equalsIgnoreCase("COMP")){
                  parameters.put("fee_desc_comp", shFee.getItemname());
                  parameters.put("fee_amount_comp", shFee.getFee().toString());
              }else if(shFee.getCode().equalsIgnoreCase("ACA11") || shFee.getCode().equalsIgnoreCase("ACA12")){
                  
                if(txtSem.getText().equals("3")){ 
                    /*
                    fee_summer_temp = (shFee.getFee().doubleValue())*tot_subj;
                    String fee_new = String.valueOf(fee_summer_temp);  
                    
                    parameters.put("fee_amount_aca", fee_new);
                    */     

                    
                    parameters.put("fee_amount_aca_1", "");
                    parameters.put("fee_amount_aca_2", "");
                    parameters.put("fee_amount_aca_3", "");
                    
                    for(int i = 1; i <= tot_subj; i++){
                        parameters.put("fee_amount_aca_"+i, shFee.getFee().toString());                        
                    }
                  }
                else {                        
                    fee_summer = (shFee.getFee());                    
                    parameters.put("fee_amount_aca", fee_summer.toString());
                }                  
                parameters.put("fee_desc_aca", shFee.getItemname());
              }   
          }
          
          parameters.put("fee_tot", tot_amount);
//           parameters.put("fee_desc_misc", "a");
//           parameters.put("fee_amount_misc", "a");
//           
//           parameters.put("fee_desc_comp",  "b");
//           parameters.put("fee_amount_comp",  "b");
//           
//           parameters.put("fee_desc_aca", "c");
//           parameters.put("fee_amount_aca", "c");
           


          JRBeanCollectionDataSource jcs = new JRBeanCollectionDataSource(plist);
          JasperPrint jp = JasperFillManager.fillReport(jr, parameters, jcs);
          JasperViewer viewer = new JasperViewer(jp, false);
          viewer.setVisible(true);

            
        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Error in printing: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);           
            ex.printStackTrace(); 
            System.out.println("Error");
        }
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

        if (this.cbStrand.getSelectionModel().getSelectedItem().toString().equals("")) {
            errorMessage += "Please select strand!\n";
        }

        if (txtStrandgroup.getText() == null || txtStrandgroup.getText().length() == 0) {
            errorMessage += "No valid strandgroup!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            showMessage(false,"Invalid Fields","Please correct invalid fields",errorMessage);
            return false;
        }
    }
    
    @FXML
    public void actionPrintAssessment(ActionEvent event) throws Exception {
        this.printAssessment();
    }
    
    @FXML
    public void actionSaveStudentStrandAndStrandgroup(ActionEvent event) throws Exception {
        if (validateInput()) {
            boolean success,success_ar = true;

            //--- START sh_stud_strand && sh_studlist ---//
            ShStudStrand add_shStudStrand = new ShStudStrand();
            ShStudlist add_shStudlist = new ShStudlist();

            String student_status = shStudStrand_ShStudlistModel.getStudentEnrollmentStatus(selectedShApplicant.getStudIdnum(), txtSY.getText());
            this.str_student_status = student_status;

            add_shStudStrand.setSsId(txtSY.getText() +"-"+ txtSem.getText() +"-"+ selectedShApplicant.getStudIdnum());
            add_shStudStrand.setStudIdnum(selectedShApplicant.getStudIdnum());
            add_shStudStrand.setStrandCode(cbStrand.getSelectionModel().getSelectedItem().toString());
            add_shStudStrand.setStrandGroup(txtStrandgroup.getText().trim().toUpperCase());
            add_shStudStrand.setSsYrLevel(Integer.parseInt(txtGradelevel.getText()));
            add_shStudStrand.setSsStatus(student_status);

            add_shStudStrand.setSsSy(txtSY.getText());
            add_shStudStrand.setSsSem(Integer.parseInt(txtSem.getText()));


            ShStudlistId add_shStudlistId = new ShStudlistId();
            add_shStudlistId.setSy(txtSY.getText());
            add_shStudlistId.setSem(Integer.parseInt(txtSem.getText()));
            add_shStudlistId.setStudIdnum(selectedShApplicant.getStudIdnum());

            add_shStudlist.setId(add_shStudlistId);
            add_shStudlist.setAppNo(selectedShApplicant.getAppNo());
            add_shStudlist.setStudLname(selectedShApplicant.getAppLname().trim());
            add_shStudlist.setStudFname(selectedShApplicant.getAppFname().trim());
            add_shStudlist.setStudSuffix(selectedShApplicant.getAppSuffix().trim());
            add_shStudlist.setStudMi(selectedShApplicant.getAppMi().trim());
            add_shStudlist.setStatus(1);
            
            if(checkTransferee.isSelected())
                add_shStudlist.setStatusIn(2);
            else
                add_shStudlist.setStatusIn(1);
            
            add_shStudlist.setStatusOut(0);
            add_shStudlist.setAppNo(selectedShApplicant.getAppNo());

    //        success = shStudStrand_ShStudlistModel.saveStudentPromotion(txtSY.getText(), txtSem.getText(), selectedShApplicant.getStudIdnum(),
    //                add_shStudStrand, add_shStudlist);

            //--- END sh_stud_strand && sh_studlist ---//



            //--- START local studentledger ---//
            ObservableList<ShFee> listShFee = FXCollections.observableArrayList();
            SaLedgerHeader saLedgerHeader = new SaLedgerHeader();
            ObservableList<SaLedger> listSaLedger = FXCollections.observableArrayList();


            //listShFee = shFeeModel.getResFeeByGradelevelAndStrand(currentEnrollment.getSyReg(), currentEnrollment.getSemReg().toString(), txtGradelevel.getText(), cbStrand.getSelectionModel().getSelectedItem().toString());
            listShFee = shFeeModel.getResFeeByGradelevelAndStrand(currentEnrollment.getSyReg(), currentEnrollment.getSemReg().toString(), txtGradelevel.getText(), cbStrand.getSelectionModel().getSelectedItem().toString());

            Calendar now = Calendar.getInstance();   // Gets the current date and time
            String glyear = Integer.toString(now.get(Calendar.YEAR)); 
            String glperiod = Integer.toString(now.get(Calendar.MONTH) + 1); 
            String glday = Integer.toString(now.get(Calendar.DATE)); 

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currDate = new Date();

            String syear = txtSY.getText() +"-"+ txtSem.getText();

            /* Count total subjects to calculate fees depending on # of subjects (2020-2021 - 3 Summer ONLY) */
            int tot_subj = shCClassStudModelModel.getTotalSubjects(txtSY.getText(), Integer.parseInt(txtSem.getText()), selectedShApplicant.getStudIdnum());    
            double fee_summer_temp = 0;
            BigDecimal fee_summer;
            
            String tot_fee = "";
            
            if(txtSem.getText().equals("3")){
                /* Summer Version */
                tot_fee = shFeeModel.getTotalSummerFee(currentEnrollment.getSyReg(), currentEnrollment.getSemReg().toString(), txtGradelevel.getText(), cbStrand.getSelectionModel().getSelectedItem().toString(), selectedShApplicant.getStudIdnum());
                System.out.println("Fees for summer: " + tot_fee);
            }
            else{ /* Original */
                tot_fee = shFeeModel.getTotalFeeByGradelevelAndStrand(currentEnrollment.getSyReg(), currentEnrollment.getSemReg().toString(), txtGradelevel.getText(), cbStrand.getSelectionModel().getSelectedItem().toString());
                System.out.println("Fees for regular semester: " + tot_fee);
            }
            
            
            saLedgerHeader.setStudentgroup("SH");
            saLedgerHeader.setStudentid(selectedShApplicant.getStudIdnum());
            saLedgerHeader.setSchoolyear(syear);
            saLedgerHeader.setGlyear(glyear);
            saLedgerHeader.setAssessedAmount(Double.parseDouble(tot_fee));
            saLedgerHeader.setYrlevel(Integer.parseInt(txtGradelevel.getText()));

            for(ShFee shFee: listShFee){
                SaLedger saLedger = new SaLedger();

                saLedger.setSaLedgerHeader(saLedgerHeader);
                saLedger.setStudentgroup("SH");
                saLedger.setStudentid(selectedShApplicant.getStudIdnum());

                saLedger.setSource("AS");
                saLedger.setReference("SH" + selectedShApplicant.getStudIdnum());
                saLedger.setItemid(shFee.getCode());
                
                /* (Sem 3 - Summer ONLY Additional) Check if sy and sem are correct, then do computation accordingly */
                if(txtSem.getText().equals("3")){              
                    /** If fee is academic, do summer calculation.. else, set debit to MISC fee **/
                    if(shFee.getCode().equals("ACA11") || shFee.getCode().equals("ACA12")){
                        fee_summer_temp = (shFee.getFee().doubleValue())*tot_subj;
                        fee_summer = new BigDecimal(fee_summer_temp);
                        
                        saLedger.setDebit(fee_summer);
                    }
                    else{                        
                        saLedger.setDebit(shFee.getFee());
                    }
                }
                else{
                    saLedger.setDebit(shFee.getFee());
                }
                /* End of Summer Calculation */
                //saLedger.setDebit(shFee.getFee());
                saLedger.setCredit(new BigDecimal(0));
                saLedger.setParticular(shFee.getItemname());

                saLedger.setSyear(syear);

                saLedger.setTransdate(currDate);
                saLedger.setGlyear(glyear);
                saLedger.setGlperiod(glperiod);
                saLedger.setRemark("");
                saLedger.setIsposted(false);
                saLedger.setIscancel(false);
                saLedger.setIsrefund(false);
                saLedger.setIsdiscount(false);
                saLedger.setDiscountcode("");

                listSaLedger.add(saLedger);
            }

            //--- END local studentledger ---//

            Boolean check = false;
            if(checkAssess.isSelected())
                check = true;
            
        
            success = saLedgerHeader_SaLedgerModel.saveStudentPromotionAndLedger(txtSY.getText(), txtSem.getText(), syear, selectedShApplicant.getStudIdnum(),
                    txtGender.getText(),check, 
                    add_shStudStrand, add_shStudlist, saLedgerHeader, listSaLedger);

            
            if(success == true && checkAssess.isSelected()){
                
                 success_ar = saLedgerHeader_SaLedgerModel.saveStudentPromotionAndLedgerLive(txtSY.getText(), txtSem.getText(), syear, 
                         selectedShApplicant.getStudIdnum(),this.txtGender.getText(), 
                    add_shStudStrand, add_shStudlist, saLedgerHeader, listSaLedger);
            }

            if(success==true && success_ar==true){
               
                this.showMessage(success, "Successful", "Student successfully promoted!", "Student successfully promoted");
            }else{
                this.showMessage(success, "Error", "Error", "Error occured");
            }
            printAssessment();
        }
        
       
    }
    
    @FXML
    public void actionSaveSubjects(ActionEvent event) throws Exception {
        System.out.println("HELLOOO");
        if (validateInput()) {
            boolean success;
            String sy = listStudentSubjects.get(0).getCsSy();
            String sem = listStudentSubjects.get(0).getCsSem().toString();
            ObservableList<ShCClassStud> listStudentSubject = FXCollections.observableArrayList();

            String cclassId = "";

            

            for(ShCClassStud shCClassStud: listStudentSubjects){

                cclassId = shCClassStud.getCsCrsCode()+ "_" + selectedShApplicant.getStudIdnum() + "_" + txtGradelevel.getText() + '_' + txtSem.getText();
                

                ShCClassStud save_shCClassStud = new ShCClassStud();
                save_shCClassStud.setCclassId(cclassId);
                save_shCClassStud.setCsCrsCode(shCClassStud.getCsCrsCode());
                save_shCClassStud.setCsIdnum(selectedShApplicant.getStudIdnum());
                save_shCClassStud.setCsSection(shCClassStud.getCsSection());
                save_shCClassStud.setCsSy(sy);
                save_shCClassStud.setCsSem(Integer.parseInt(sem));
                save_shCClassStud.setCsStrandgroup(shCClassStud.getCsStrandgroup());
                save_shCClassStud.setCsYrLevel(shCClassStud.getCsYrLevel());

                listStudentSubject.add(save_shCClassStud);
            }
            
            ShStudStrand add_shStudStrand = new ShStudStrand();
            String student_status = shStudStrand_ShStudlistModel.getStudentEnrollmentStatus(selectedShApplicant.getStudIdnum(), txtSY.getText());
            this.str_student_status = student_status;
            add_shStudStrand.setSsId(txtSY.getText() +"-"+ txtSem.getText() +"-"+ selectedShApplicant.getStudIdnum());
            add_shStudStrand.setStudIdnum(selectedShApplicant.getStudIdnum());
            add_shStudStrand.setStrandCode(cbStrand.getSelectionModel().getSelectedItem().toString());
            add_shStudStrand.setStrandGroup(txtStrandgroup.getText().trim().toUpperCase());
            add_shStudStrand.setSsYrLevel(Integer.parseInt(txtGradelevel.getText()));
            add_shStudStrand.setSsStatus(student_status);

            add_shStudStrand.setSsSy(txtSY.getText());
            add_shStudStrand.setSsSem(Integer.parseInt(txtSem.getText()));

            success = shCClassStudModelModel.saveStudentSubjects(sy, sem, selectedShApplicant.getStudIdnum(),listStudentSubject, add_shStudStrand);
            
            if(success==true){  
                this.showMessage(success, "Successful", "Student subject assignment successful!", "Student subject assignment successful");
            }else{
                this.showMessage(success, "Error", "Error", "Error occured");
                
                
                
            }
        }       
        
    }
    
    @FXML
    public void actionAddSchedule(ActionEvent event) throws Exception { 
        try{
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/StudentSubjectAssignmentManageEntry.fxml")));
            StudentSubjectAssignmentManageEntryController controller = new StudentSubjectAssignmentManageEntryController();
            loader.setController(controller);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Subject Schedule Selection");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
    //        stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            
            selectedCustomEnrolledStudent.setGrade_level(txtGradelevel.getText());
            selectedCustomEnrolledStudent.setStrand_group(txtStrandgroup.getText());
            selectedCustomEnrolledStudent.setSection(strandSectionModel.getStrandgroupSectionDesc(txtSY.getText(), txtSem.getText(), 
                                                    txtGradelevel.getText(), cbStrand.getSelectionModel().getSelectedItem().toString(), txtStrandgroup.getText()));
            selectedCustomEnrolledStudent.setStrand_code(cbStrand.getSelectionModel().getSelectedItem().toString());
            
            controller.setStudentDetails(selectedCustomEnrolledStudent);
            //this.loadOfferedSubjectsInTable();
            loadSubjects();
            this.loadSubjects();
        }catch(Exception ex){
            ex.printStackTrace();   
        }
    }
    
    @FXML
    public void actionRefreshList(ActionEvent event) throws Exception { 
        try{            
            //this.tblSubject.refresh(); 
            this.loadOfferedSubjectsInTable();
            loadSubjects();
            this.loadSubjects();
            this.tblSubject.refresh();
        }catch(Exception ex){
            ex.printStackTrace();   
        }
    }
    
}
