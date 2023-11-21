/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomEnrolledStudent;
import entity.JasperObject;
import entity.ReportStudentGrades;
import entity.ShApplicant;
import entity.ShCAttendance;
import entity.ShCClassStud;
import entity.ShCCorevalues;
import entity.ShClassInfo;
import entity.ShStudStrand;
import entity.ShTermReg;
import interfaces.ShCAttendanceInterface;
import static interfaces.ShCAttendanceInterface.listShCAttendanceGrade11;
import static interfaces.ShCAttendanceInterface.listShCAttendanceGrade12;
import interfaces.ShCClassStudInterface;
import static interfaces.ShCClassStudInterface.listStudentSubjectGrades1;
import static interfaces.ShCClassStudInterface.listStudentSubjectGrades2;
import static interfaces.ShCClassStudInterface.listStudentSubjectGrades3;
import static interfaces.ShCClassStudInterface.listStudentSubjectGrades4;
import static interfaces.ShCClassStudInterface.listStudentSubjectGrades5;
import interfaces.ShCCorevaluesInterface;
import static interfaces.ShCCorevaluesInterface.listShCCoreValuesGrade11;
import static interfaces.ShCCorevaluesInterface.listShCCoreValuesGrade12;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.CustomEnrolledStudentModel;
import model.ShApplicantModel;
import model.ShCAttendanceModel;
import model.ShCClassStudModel;
import model.ShCCorevaluesModel;
import model.ShClassInfoModel;
import model.ShInstructorModel;
import model.ShStudStrand_ShStudlistModel;
import model.ShTermRegModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class StudentGradesController implements Initializable,ShCClassStudInterface,ShCCorevaluesInterface,ShCAttendanceInterface {

    @FXML
    private TextField txtFullname;
    @FXML
    private TextField txt1SY;
    @FXML
    private TextField txt1Strand;
    @FXML
    private TextField txt1Section;
    @FXML
    private TableView<ShCClassStud> tbl1Studentgrades;
    @FXML
    private TableColumn<ShCClassStud, String> col1No;
    @FXML
    private TableColumn<ShCClassStud, String> col1SubjCode;
    @FXML
    private TableColumn<ShCClassStud, String> col1SubjDesc;
    @FXML
    private TableColumn<ShCClassStud, String> col1SubjUnit;
    @FXML
    private TableColumn<ShCClassStud, String> col1Grade1;
    @FXML
    private TableColumn<ShCClassStud, String> col1Grade2;
    @FXML
    private TableColumn<ShCClassStud, String> col1FG;
    @FXML
    private TextField txt1GPA;
    @FXML
    private TextField txt1Unit;
    @FXML
    private TextField txt2SY;
    @FXML
    private TextField txt2Strand;
    @FXML
    private TextField txt2Section;
    @FXML
    private TableView<ShCClassStud> tbl2Studentgrades;
    @FXML
    private TableColumn<ShCClassStud, String> col2No;
    @FXML
    private TableColumn<ShCClassStud, String> col2SubjCode;
    @FXML
    private TableColumn<ShCClassStud, String> col2SubjDesc;
    @FXML
    private TableColumn<ShCClassStud, String> col2SubjUnit;
    @FXML
    private TableColumn<ShCClassStud, String> col2Grade1;
    @FXML
    private TableColumn<ShCClassStud, String> col2Grade2;
    @FXML
    private TableColumn<ShCClassStud, String> col2FG;
    @FXML
    private TextField txt2GPA;
    @FXML
    private TextField txt2Unit;
    
    @FXML
    private TextField txt3SY;
    @FXML
    private TextField txt3Strand;
    @FXML
    private TextField txt3Section;
    @FXML
    private TableView<ShCClassStud> tbl3Studentgrades;
    @FXML
    private TableColumn<ShCClassStud, String> col3No;
    @FXML
    private TableColumn<ShCClassStud, String> col3SubjCode;
    @FXML
    private TableColumn<ShCClassStud, String> col3SubjDesc;
    @FXML
    private TableColumn<ShCClassStud, String> col3SubjUnit;
    @FXML
    private TableColumn<ShCClassStud, String> col3Grade1;
    @FXML
    private TableColumn<ShCClassStud, String> col3Grade2;
    @FXML
    private TableColumn<ShCClassStud, String> col3FG;
    @FXML
    private TextField txt3GPA;
    @FXML
    private TextField txt3Unit;
    @FXML
    private TextField txt4SY;
    @FXML
    private TextField txt4Strand;
    @FXML
    private TextField txt4Section;
    @FXML
    private TableView<ShCClassStud> tbl4Studentgrades;
    @FXML
    private TableColumn<ShCClassStud, String> col4No;
    @FXML
    private TableColumn<ShCClassStud, String> col4SubjCode;
    @FXML
    private TableColumn<ShCClassStud, String> col4SubjDesc;
    @FXML
    private TableColumn<ShCClassStud, String> col4SubjUnit;
    @FXML
    private TableColumn<ShCClassStud, String> col4Grade1;
    @FXML
    private TableColumn<ShCClassStud, String> col4Grade2;
    @FXML
    private TableColumn<ShCClassStud, String> col4FG;
    @FXML
    private TextField txt4GPA;
    @FXML
    private TextField txt4Unit;
    @FXML
    private TableView<ShCClassStud> tblSummer;
    @FXML
    private TableColumn<ShCClassStud, String> colSummerNo;
    @FXML
    private TableColumn<ShCClassStud, String> colSummerSubjCode;
    @FXML
    private TableColumn<ShCClassStud, String> colSummerSubjDesc;
    @FXML
    private TableColumn<ShCClassStud, String> colSummerSubjUnit;
    @FXML
    private TableColumn<ShCClassStud, String> colSummerGrade1;
    @FXML
    private TableColumn<ShCClassStud, String> colSummerGrade2;
    @FXML
    private TableColumn<ShCClassStud, String> colSummerFG;
    @FXML
    private TableView<ShCCorevalues> tblGrade11CoreValues;
    @FXML
    private TableColumn<ShCCorevalues, String> colG11CvNo;
    @FXML
    private TableColumn<ShCCorevalues, String> colG11CvDesc;
    @FXML
    private TableColumn<ShCCorevalues, String> colG11CvGrade1;
    @FXML
    private TableColumn<ShCCorevalues, String> colG11CvGrade2;
    @FXML
    private TableColumn<ShCCorevalues, String> colG11CvGrade3;
    @FXML
    private TableColumn<ShCCorevalues, String> colG11CvGrade4;
    @FXML
    private TableView<ShCAttendance> tblGrade11Attendance;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttNo;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttDesc;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttMonth6;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttMonth7;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttMonth8;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttMonth9;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttMonth10;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttMonth11;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttMonth12;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttMonth1;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttMonth2;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttMonth3;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttMonth4;
    @FXML
    private TableColumn<ShCAttendance, String> colG11AttTotal;
    @FXML
    private TableView<ShCCorevalues> tblGrade12CoreValues;
    @FXML
    private TableColumn<ShCCorevalues, String> colG12CvNo;
    @FXML
    private TableColumn<ShCCorevalues, String> colG12CvDesc;
    @FXML
    private TableColumn<ShCCorevalues, String> colG12CvGrade1;
    @FXML
    private TableColumn<ShCCorevalues, String> colG12CvGrade2;
    @FXML
    private TableColumn<ShCCorevalues, String> colG12CvGrade3;
    @FXML
    private TableColumn<ShCCorevalues, String> colG12CvGrade4;
    @FXML
    private TableView<ShCAttendance> tblGrade12Attendance;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttNo;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttDesc;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttMonth6;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttMonth7;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttMonth8;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttMonth9;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttMonth10;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttMonth11;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttMonth12;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttMonth1;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttMonth2;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttMonth3;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttMonth4;
    @FXML
    private TableColumn<ShCAttendance, String> colG12AttTotal;
    @FXML
    private ComboBox<String> cbPrintGradelevel;
    @FXML
    private ComboBox<String> cbPrintSem;
    @FXML
    private Button btnPrintCard;
    @FXML
    private Button btnPrintCoreValues;
    @FXML
    private TextField txtGenDay;
    @FXML
    private TextField txtGenMonthYear;
    @FXML
    private TextField txtGenAdmission;
    @FXML
    private TextField txtGenReceiver;
    @FXML
    private TextField txtGenDateReceived;
    @FXML
    private TextField txtGenStrand;
    @FXML
    private TextField txtGenAward;
    @FXML
    private DatePicker dpGraduation;
    @FXML
    private TextField txtGenPurpose;
    @FXML
    private ComboBox<String> cbGenSY;
    @FXML
    private ComboBox<String> cbGenPurpose;
    @FXML
    private Button btnUpdateProfile;
    @FXML
    private TextField txtProfLname;
    @FXML
    private TextField txtProfMname;
    @FXML
    private TextField txtProfFname;
    @FXML
    private TextField txtProfSuffix;
    @FXML
    private DatePicker dpProf;
    @FXML
    private DatePicker dpAdmission;
    @FXML
    private TextField txtProfLRN;
    @FXML
    private ComboBox<String> cbProfGender;
    @FXML
    private TextField txtProfJHSchool;
    @FXML
    private TextField txtProfJHAddress;
    @FXML
    private TextField txtProfJHSY;
    @FXML
    private TextField txtProfJHGPA;
    
  
    private CustomEnrolledStudentModel customEnrolledStudentModel;
    private ShCClassStudModel shCClassStudModel;
    private ShStudStrand_ShStudlistModel shStudStrand_ShStudlistModel;
    private ShApplicantModel applicantModel;
    private ShCCorevaluesModel shCCorevaluesModel;
    private ShCAttendanceModel shCAttendanceModel;
    private ShClassInfoModel shClassInfoModel;
    private ShInstructorModel shInstructorModel;
    private ShTermRegModel shTermRegModel;
    
    private ShApplicant selectedShApplicant;
    private ShTermReg currentShTermReg;
    private CustomEnrolledStudent selectedCustomEnrolledStudent;
    private CustomEnrolledStudent selectedCustomEnrolledStudent1; //--- first year first sem
    private CustomEnrolledStudent selectedCustomEnrolledStudent2; //--- first year 2nd sem
    private CustomEnrolledStudent selectedCustomEnrolledStudent3; //--- 2nd year first sem
    private CustomEnrolledStudent selectedCustomEnrolledStudent4; //--- 2nd year 2nd sem
    private CustomEnrolledStudent selectedCustomEnrolledStudent5; //--- summer
    private HashMap parameters; //--- parameters for jasperreport
    private List<ReportStudentGrades> records_1stSem;
    private List<ReportStudentGrades> records_2ndSem;
    private List<ReportStudentGrades> records_g11_1stSem;
    private List<ReportStudentGrades> records_g11_2ndSem;
    private List<ReportStudentGrades> records_g12_1stSem;
    private List<ReportStudentGrades> records_g12_2ndSem;
//    private Map<String, Object> parameters; //--- parameters for jasperreport
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        customEnrolledStudentModel = new CustomEnrolledStudentModel();
        applicantModel =  new ShApplicantModel();
        shCClassStudModel = new ShCClassStudModel();
        shStudStrand_ShStudlistModel = new ShStudStrand_ShStudlistModel();
        shCCorevaluesModel = new ShCCorevaluesModel();
        shCAttendanceModel = new ShCAttendanceModel();
        shClassInfoModel = new ShClassInfoModel();
        shInstructorModel = new ShInstructorModel();
        shTermRegModel = new ShTermRegModel();
        parameters = new HashMap();
//        parameters = new HashMap<String, Object>();
       
        records_1stSem = new ArrayList<>();
        records_2ndSem = new ArrayList<>();
        records_g11_1stSem = new ArrayList<>();
        records_g11_2ndSem = new ArrayList<>();
        records_g12_1stSem = new ArrayList<>();
        records_g12_2ndSem = new ArrayList<>();
        
        loadGradelevelInCbox();
        loadSemInCbox();
        loadSYInCbox();
        loadPurposeInCbox();
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
    
   private void loadGradelevelInCbox() {
        ObservableList<String> listGradelevel = FXCollections.observableArrayList();
        listGradelevel.add("11");
        listGradelevel.add("12");
        cbPrintGradelevel.setItems(listGradelevel);
        cbPrintGradelevel.getSelectionModel().select(0);
    }
    
    private void loadSemInCbox() {
        ObservableList<String> listSem = FXCollections.observableArrayList();
        listSem.add("1");
        listSem.add("2");
        cbPrintSem.setItems(listSem);
        cbPrintSem.getSelectionModel().select(0);
    }
    
    private void loadSYInCbox() {
        ObservableList<String> listSY = FXCollections.observableArrayList();
        listSY.add("2016-2017");
        listSY.add("2017-2018");
        listSY.add("2018-2019");
        listSY.add("2019-2020"); 
        listSY.add("2020-2021");
        listSY.add("2021-2022");
        listSY.add("2022-2023");
        cbGenSY.setItems(listSY);
        cbGenSY.getSelectionModel().select(0);
    }
    
    private void loadPurposeInCbox() {
        ObservableList<String> listPurpose = FXCollections.observableArrayList();
        listPurpose.add("blank");               
        cbGenPurpose.setItems(listPurpose);
        cbGenPurpose.getSelectionModel().select(0);
    }
    
    public void setStudentDetails(CustomEnrolledStudent selectedStudent) { 
        
        selectedCustomEnrolledStudent = selectedStudent;
        this.selectedShApplicant = applicantModel.getRowApplicantByStudID(selectedCustomEnrolledStudent.getStud_idnum());       
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd, yyyy");
        
        if(selectedShApplicant.getAppMiddlename() == null){
            txtFullname.setText(selectedShApplicant.getAppLname().trim() +", "+ selectedShApplicant.getAppFname().trim());
        }
        else{
            txtFullname.setText(selectedShApplicant.getAppLname().trim() +", "+ selectedShApplicant.getAppFname().trim() +" "+ selectedShApplicant.getAppMiddlename().trim());
        }
        
        setStudentStrand1stYear1stSem();
        setStudentStrand1stYear2ndSem();
        setStudentStrand2ndYear1stSem();
        setStudentStrand2ndYear2ndSem();
        setStudentStrandSummer();
        
        loadStudentSubjectsInTable1stYear1stSem();
        loadStudentSubjectsInTable1stYear2ndSem();
        loadStudentSubjectsInTable2ndYear1stSem();
        loadStudentSubjectsInTable2ndYear2ndSem();
        loadStudentSubjectsInTableSummer();
        
        loadStudentCoreValuesGrade11InTable();
        loadStudentCoreValuesGrade12InTable();
        
        loadStudentAttendanceGrade11InTable();
        loadStudentAttendanceGrade12InTable();
        loadStudentProfile();
    }

    private void setStudentStrand1stYear1stSem(){
        selectedCustomEnrolledStudent1 = this.customEnrolledStudentModel.getRowEnrolledStudentByYearAndSem("11", "1",selectedCustomEnrolledStudent.getStud_idnum());
        txt1SY.setText(selectedCustomEnrolledStudent1.getSy());
        txt1Strand.setText(selectedCustomEnrolledStudent1.getStrand_code_desc());
        txt1Section.setText(selectedCustomEnrolledStudent1.getSection());
        
        parameters.put("STUD_SCHOOL_NAME_1", "Ateneo de Zamboanga University");
        parameters.put("STUD_SCHOOL_ID_1", "404925");
        parameters.put("STUD_SY_1", selectedCustomEnrolledStudent1.getSy());
        parameters.put("STUD_SEM_1", selectedCustomEnrolledStudent1.getSem());
        parameters.put("STUD_GRADELEVEL_1", selectedCustomEnrolledStudent1.getGrade_level());
        parameters.put("STUD_STRAND_1", selectedCustomEnrolledStudent1.getStrand_code_desc());
        parameters.put("STUD_SECTION_1", selectedCustomEnrolledStudent1.getSection());
    }
    
    private void loadStudentSubjectsInTable1stYear1stSem() {
        if (!listStudentSubjectGrades1.isEmpty()) {
            listStudentSubjectGrades1.clear();
        }
        listStudentSubjectGrades1.addAll(shCClassStudModel.getResStudentGradesIndividualBySYAndSem(selectedCustomEnrolledStudent1.getSy(), selectedCustomEnrolledStudent1.getSem(), 
                selectedCustomEnrolledStudent.getStud_idnum()));
                
        col1No.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCClassStud, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCClassStud, String> p) {
               return new ReadOnlyObjectWrapper(tbl1Studentgrades.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        col1No.setSortable(false);
        
        col1SubjCode.setCellValueFactory(new PropertyValueFactory<>("csCrsCode"));
        col1SubjDesc.setCellValueFactory(new PropertyValueFactory<>("subject_desc"));
        col1SubjUnit.setCellValueFactory(new PropertyValueFactory<>("subject_unit"));
        col1Grade1.setCellValueFactory(new PropertyValueFactory<>("csMidGrade"));
        col1Grade2.setCellValueFactory(new PropertyValueFactory<>("csSecQtr"));
        col1FG.setCellValueFactory(new PropertyValueFactory<>("subject_fg"));

        tbl1Studentgrades.setItems(listStudentSubjectGrades1); 
        
        double initial_grades = 0, total_grades = 0, total_units = 0 ;
        DecimalFormat df_fg = new DecimalFormat("00");
        for(ShCClassStud row : listStudentSubjectGrades1) {
//            total_grades += (Double.parseDouble(row.getSubject_fg()) * Double.parseDouble(row.getSubject_unit()));            
//            total_grades += Double.parseDouble(row.getSubject_fg());
            total_units += Double.parseDouble(row.getSubject_unit());
            initial_grades = Double.parseDouble(row.getSubject_fg()) * Double.parseDouble(row.getSubject_unit());
            total_grades += initial_grades;
            
            
            ReportStudentGrades reportStudentGrades = new ReportStudentGrades();
            
            reportStudentGrades.setSubj_code_1(row.getCsCrsCode().trim());
            reportStudentGrades.setSubj_type_1(row.getSubject_type());
            reportStudentGrades.setSubj_desc_1(row.getSubject_desc());
            reportStudentGrades.setSubj_grade1_1(Double.parseDouble(row.getCsMidGrade()));
            reportStudentGrades.setSubj_grade2_1(Double.parseDouble(row.getCsSecQtr()));
            reportStudentGrades.setSubj_fg_1(Double.parseDouble(row.getSubject_fg()));
            reportStudentGrades.setSubj_unit_1(Double.parseDouble(row.getSubject_unit()));
            reportStudentGrades.setSubj_sy_1(row.getCsSy());
            
//            records_1stSem.add(reportStudentGrades);
            records_g11_1stSem.add(reportStudentGrades);
        }
        DecimalFormat df = new DecimalFormat("00.00");
//        double gpa = total_grades / total_units;
        String GPA_1 = df.format(total_grades/total_units);
        if(total_grades/total_units == 0 || Double.isNaN(total_grades/total_units))
            txt1GPA.setText("N/A");
        else
            txt1GPA.setText(GPA_1);
//        txt1GPA.setText(String.valueOf(total_grades/total_units));
        txt1Unit.setText(String.valueOf(total_units));
    }
    
    private void setStudentStrand1stYear2ndSem(){
//        selectedCustomEnrolledStudent2 = this.selectedCustomEnrolledStudent1;
        selectedCustomEnrolledStudent2 = this.customEnrolledStudentModel.getRowEnrolledStudentByYearAndSem("11", "2",selectedCustomEnrolledStudent.getStud_idnum());
        if(selectedCustomEnrolledStudent2.getStrand_code() == null){
            selectedCustomEnrolledStudent2 = this.selectedCustomEnrolledStudent1;
        }
        
        txt2SY.setText(selectedCustomEnrolledStudent2.getSy());
        txt2Strand.setText(selectedCustomEnrolledStudent2.getStrand_code_desc());
        txt2Section.setText(selectedCustomEnrolledStudent2.getSection());
        
        parameters.put("STUD_SCHOOL_NAME_2", "Ateneo de Zamboanga University");
        parameters.put("STUD_SCHOOL_ID_2", "404925");
        parameters.put("STUD_SY_2", selectedCustomEnrolledStudent2.getSy());
        parameters.put("STUD_SEM_2", "2");
        parameters.put("STUD_GRADELEVEL_2", selectedCustomEnrolledStudent2.getGrade_level());
        parameters.put("STUD_STRAND_2", selectedCustomEnrolledStudent2.getStrand_code_desc());
        parameters.put("STUD_SECTION_2", selectedCustomEnrolledStudent2.getSection());
        
        selectedCustomEnrolledStudent2 = this.customEnrolledStudentModel.getRowEnrolledStudentByYearAndSem("11", "2",selectedCustomEnrolledStudent.getStud_idnum());
    }
    
    private void loadStudentSubjectsInTable1stYear2ndSem() {
        if (!listStudentSubjectGrades2.isEmpty()) {
            listStudentSubjectGrades2.clear();
        }

        if(selectedCustomEnrolledStudent2.getStrand_code() == null){
            listStudentSubjectGrades2.addAll(shCClassStudModel.getResStudentOfferedSubjectsBySYAndSem(selectedCustomEnrolledStudent1.getSy(), "2", "11",selectedCustomEnrolledStudent1.getStrand_code()));      
        }
        else{
            listStudentSubjectGrades2.addAll(shCClassStudModel.getResStudentGradesIndividualBySYAndSem(selectedCustomEnrolledStudent2.getSy(), selectedCustomEnrolledStudent2.getSem(), 
                selectedCustomEnrolledStudent.getStud_idnum()));
        }
        

//        listStudentSubjectGrades2.addAll(shCClassStudModel.getResStudentGradesIndividualBySYAndSem(selectedCustomEnrolledStudent2.getSy(), selectedCustomEnrolledStudent2.getSem(), 
//                selectedCustomEnrolledStudent.getStud_idnum()));
        
//        listStudentSubjectGrades2.addAll(shCClassStudModel.getResStudentOfferedSubjectsBySYAndSem(selectedCustomEnrolledStudent2.getSy(), selectedCustomEnrolledStudent2.getSem(), 
//               selectedCustomEnrolledStudent2.getGrade_level(),selectedCustomEnrolledStudent2.getStrand_code()));
                
        col2No.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCClassStud, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCClassStud, String> p) {
               return new ReadOnlyObjectWrapper(tbl2Studentgrades.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        col2No.setSortable(false);
        
        col2SubjCode.setCellValueFactory(new PropertyValueFactory<>("csCrsCode"));
        col2SubjDesc.setCellValueFactory(new PropertyValueFactory<>("subject_desc"));
        col2SubjUnit.setCellValueFactory(new PropertyValueFactory<>("subject_unit"));
        col2Grade1.setCellValueFactory(new PropertyValueFactory<>("csMidGrade"));
        col2Grade2.setCellValueFactory(new PropertyValueFactory<>("csSecQtr"));
        col2FG.setCellValueFactory(new PropertyValueFactory<>("subject_fg"));

        tbl2Studentgrades.setItems(listStudentSubjectGrades2); 
        
        double initial_grades = 0, total_grades = 0, total_units = 0 ;
        for(ShCClassStud row : listStudentSubjectGrades2) {
//            total_grades += (Double.parseDouble(row.getSubject_fg()) * Double.parseDouble(row.getSubject_unit()));
//            total_units += Double.parseDouble(row.getSubject_unit());
            total_units += Double.parseDouble(row.getSubject_unit());
            initial_grades = Double.parseDouble(row.getSubject_fg()) * Double.parseDouble(row.getSubject_unit());
            total_grades += initial_grades;
            
            ReportStudentGrades reportStudentGrades = new ReportStudentGrades();
            
            reportStudentGrades.setSubj_code_2(row.getCsCrsCode().trim());
            reportStudentGrades.setSubj_type_2(row.getSubject_type());
            reportStudentGrades.setSubj_desc_2(row.getSubject_desc());
            reportStudentGrades.setSubj_grade1_2(Double.parseDouble(row.getCsMidGrade()));
            reportStudentGrades.setSubj_grade2_2(Double.parseDouble(row.getCsSecQtr()));
            reportStudentGrades.setSubj_fg_2(Double.parseDouble(row.getSubject_fg()));
            reportStudentGrades.setSubj_unit_2(Double.parseDouble(row.getSubject_unit()));
            
//            records_2ndSem.add(reportStudentGrades);
            records_g11_2ndSem.add(reportStudentGrades);
        }
        
        DecimalFormat df = new DecimalFormat("00.00");
//        double gpa = total_grades / total_units;
        String GPA_2 = df.format(total_grades/total_units);
        if(total_grades/total_units == 0 || Double.isNaN(total_grades/total_units))
            txt2GPA.setText("N/A");
        else
            txt2GPA.setText(GPA_2);
//        txt2GPA.setText(String.valueOf(total_grades/total_units));
        txt2Unit.setText(String.valueOf(total_units));

    }
    
    
    private void setStudentStrand2ndYear1stSem(){
        
        selectedCustomEnrolledStudent3 = this.customEnrolledStudentModel.getRowEnrolledStudentByYearAndSem("12", "1",selectedCustomEnrolledStudent.getStud_idnum());
        
        txt3SY.setText(selectedCustomEnrolledStudent3.getSy());
        txt3Strand.setText(selectedCustomEnrolledStudent3.getStrand_code_desc());
        txt3Section.setText(selectedCustomEnrolledStudent3.getSection());
        
        parameters.put("STUD_SCHOOL_NAME_3", "Ateneo de Zamboanga University");
        parameters.put("STUD_SCHOOL_ID_3", "404925");
        parameters.put("STUD_SY_3", selectedCustomEnrolledStudent3.getSy());
        parameters.put("STUD_SEM_3", selectedCustomEnrolledStudent3.getSem());
        parameters.put("STUD_GRADELEVEL_3", selectedCustomEnrolledStudent3.getGrade_level());
        parameters.put("STUD_STRAND_3", selectedCustomEnrolledStudent3.getStrand_code_desc());
        parameters.put("STUD_SECTION_3", selectedCustomEnrolledStudent3.getSection());
    }
    
    private void loadStudentSubjectsInTable2ndYear1stSem() {
        try{
            if (!listStudentSubjectGrades3.isEmpty()) {
                listStudentSubjectGrades3.clear();
            }            
            /*
            String sy_substr = ((selectedCustomEnrolledStudent1.getSy() == null ? "" : selectedCustomEnrolledStudent1.getSy())).substring(0, 4);
            int sy_concat1 = Integer.parseInt(sy_substr) + 1;
            int sy_concat2 = Integer.parseInt(sy_substr) + 2;
            String sy_new = sy_concat1 + "-" + sy_concat2;
            */
            String sy_new = (customEnrolledStudentModel.getRowEnrolledStudentByYearAndSem("12", "2", selectedCustomEnrolledStudent.getStud_idnum()).getSy() == null ? "": customEnrolledStudentModel.getRowEnrolledStudentByYearAndSem("12", "1", selectedCustomEnrolledStudent.getStud_idnum()).getSy());
            
            if(selectedCustomEnrolledStudent3.getStrand_code() == null){
                listStudentSubjectGrades3.addAll(shCClassStudModel.getResStudentOfferedSubjectsBySYAndSem(sy_new, "2", "11",selectedCustomEnrolledStudent1.getStrand_code()));            
            }
            else{
                listStudentSubjectGrades3.addAll(shCClassStudModel.getResStudentGradesIndividualBySYAndSem(selectedCustomEnrolledStudent3.getSy(), selectedCustomEnrolledStudent3.getSem(), 
                    selectedCustomEnrolledStudent.getStud_idnum()));
            }

//            listStudentSubjectGrades3.addAll(shCClassStudModel.getResStudentGradesIndividualBySYAndSem(selectedCustomEnrolledStudent3.getSy(), selectedCustomEnrolledStudent3.getSem(), 
//                    selectedCustomEnrolledStudent.getStud_idnum()));
            
//            listStudentSubjectGrades3.addAll(shCClassStudModel.getResStudentOfferedSubjectsBySYAndSem(selectedCustomEnrolledStudent3.getSy(), selectedCustomEnrolledStudent3.getSem(), 
//                selectedCustomEnrolledStudent3.getGrade_level(),selectedCustomEnrolledStudent3.getStrand_code()));

            col3No.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCClassStud, String>,ObservableValue<String>>(){
               @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCClassStud, String> p) {
                   return new ReadOnlyObjectWrapper(tbl3Studentgrades.getItems().indexOf(p.getValue()) + 1);
               }  
            });

            col3No.setSortable(false);

            col3SubjCode.setCellValueFactory(new PropertyValueFactory<>("csCrsCode"));
            col3SubjDesc.setCellValueFactory(new PropertyValueFactory<>("subject_desc"));
            col3SubjUnit.setCellValueFactory(new PropertyValueFactory<>("subject_unit"));
            col3Grade1.setCellValueFactory(new PropertyValueFactory<>("csMidGrade"));
            col3Grade2.setCellValueFactory(new PropertyValueFactory<>("csSecQtr"));
            col3FG.setCellValueFactory(new PropertyValueFactory<>("subject_fg"));

            tbl3Studentgrades.setItems(listStudentSubjectGrades3); 

            double initial_grades = 0, total_grades = 0, total_units = 0 ;
            for(ShCClassStud row : listStudentSubjectGrades3) {
//            total_grades += (Double.parseDouble(row.getSubject_fg()) * Double.parseDouble(row.getSubject_unit()));
            total_units += Double.parseDouble(row.getSubject_unit());
            initial_grades = Double.parseDouble(row.getSubject_fg()) * Double.parseDouble(row.getSubject_unit());
            total_grades += initial_grades;


                ReportStudentGrades reportStudentGrades = new ReportStudentGrades();


//                reportStudentGrades.setSubj_type_1(null);
//                reportStudentGrades.setSubj_desc_1(null);
//                reportStudentGrades.setSubj_fg_1(null);
//                reportStudentGrades.setSubj_unit_1(null);

                
                
                reportStudentGrades.setSubj_code_3(row.getCsCrsCode().trim());
                reportStudentGrades.setSubj_type_3(row.getSubject_type());
                reportStudentGrades.setSubj_desc_3(row.getSubject_desc());
                reportStudentGrades.setSubj_grade1_3(Double.parseDouble(row.getCsMidGrade()));
                reportStudentGrades.setSubj_grade2_3(Double.parseDouble(row.getCsSecQtr()));
                reportStudentGrades.setSubj_fg_3(Double.parseDouble(row.getSubject_fg()));
                reportStudentGrades.setSubj_unit_3(Double.parseDouble(row.getSubject_unit()));

    //            records_1stSem.add(reportStudentGrades);
                records_g12_1stSem.add(reportStudentGrades);
            }

            DecimalFormat df = new DecimalFormat("00.00");
//            double gpa = total_grades / total_units;
            String GPA_3 = df.format(total_grades/total_units);
            if(total_grades/total_units == 0 || Double.isNaN(total_grades/total_units))
                txt3GPA.setText("N/A");
            else
                txt3GPA.setText(GPA_3);
//            txt3GPA.setText(String.valueOf(total_grades/total_units));
            txt3Unit.setText(String.valueOf(total_units));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void setStudentStrand2ndYear2ndSem(){
//        selectedCustomEnrolledStudent4 = this.selectedCustomEnrolledStudent3;
        selectedCustomEnrolledStudent4 = this.customEnrolledStudentModel.getRowEnrolledStudentByYearAndSem("12", "2",selectedCustomEnrolledStudent.getStud_idnum());
        String sem = null;
        if(selectedCustomEnrolledStudent4.getStrand_code() == null){
            selectedCustomEnrolledStudent4 = this.selectedCustomEnrolledStudent3;
            if(selectedCustomEnrolledStudent4.getSem() != null){
                sem = "2";
            }
            else{
                sem = selectedCustomEnrolledStudent4.getSem();
            }
        }

        
        txt4SY.setText(selectedCustomEnrolledStudent4.getSy());
        txt4Strand.setText(selectedCustomEnrolledStudent4.getStrand_code_desc());
        txt4Section.setText(selectedCustomEnrolledStudent4.getSection());
        
        parameters.put("STUD_SCHOOL_NAME_4", "Ateneo de Zamboanga University");
        parameters.put("STUD_SCHOOL_ID_4", "404925");
        parameters.put("STUD_SY_4", selectedCustomEnrolledStudent4.getSy());
        parameters.put("STUD_SEM_4", selectedCustomEnrolledStudent4.getSem());
        parameters.put("STUD_GRADELEVEL_4", selectedCustomEnrolledStudent4.getGrade_level());
        parameters.put("STUD_STRAND_4", selectedCustomEnrolledStudent4.getStrand_code_desc());
        parameters.put("STUD_SECTION_4", selectedCustomEnrolledStudent4.getSection());
        
        selectedCustomEnrolledStudent4 = this.customEnrolledStudentModel.getRowEnrolledStudentByYearAndSem("12", "2",selectedCustomEnrolledStudent.getStud_idnum());
    }
    
    private void loadStudentSubjectsInTable2ndYear2ndSem() {
        //<editor-fold defaultstate="collapsed" desc="code">
        if (!listStudentSubjectGrades4.isEmpty()) {
            listStudentSubjectGrades4.clear();
        }
        /*
        String sy_substr = (selectedCustomEnrolledStudent1.getSy()).substring(0, 4);
        int sy_concat1 = Integer.parseInt(sy_substr) + 1;
        int sy_concat2 = Integer.parseInt(sy_substr) + 2;
        String sy_new = sy_concat1 + "-" + sy_concat2;
        */
        //String sy_new = (customEnrolledStudentModel.getRowEnrolledStudentByYearAndSem("12", "2", selectedCustomEnrolledStudent.getStud_idnum()).getSy() == null ? "": customEnrolledStudentModel.getRowEnrolledStudentByYearAndSem("12", "2", selectedCustomEnrolledStudent.getStud_idnum()).getSy());
        //String sy_new = (shStudStrand_ShStudlistModel.getRowShStudStrandByYearAndSem(selectedCustomEnrolledStudent.getStud_idnum(), "12", "2").getSsSy() == null ? "": shStudStrand_ShStudlistModel.getRowShStudStrandByYearAndSem(selectedCustomEnrolledStudent.getStud_idnum(), "12", "2").getSsSy());
        
        if(selectedCustomEnrolledStudent4.getStrand_code() == null){
            listStudentSubjectGrades4.addAll(shCClassStudModel.getResStudentOfferedSubjectsBySYAndSem(selectedCustomEnrolledStudent3.getSy(), "2", "12",selectedCustomEnrolledStudent3.getStrand_code()));
        }
        else{
            listStudentSubjectGrades4.addAll(shCClassStudModel.getResStudentGradesIndividualBySYAndSem(selectedCustomEnrolledStudent4.getSy(), selectedCustomEnrolledStudent4.getSem(),
                    selectedCustomEnrolledStudent.getStud_idnum()));
        }
        
//        listStudentSubjectGrades4.addAll(shCClassStudModel.getResStudentGradesIndividualBySYAndSem(selectedCustomEnrolledStudent4.getSy(), selectedCustomEnrolledStudent4.getSem(),
//                selectedCustomEnrolledStudent.getStud_idnum()));

//        listStudentSubjectGrades4.addAll(shCClassStudModel.getResStudentOfferedSubjectsBySYAndSem(selectedCustomEnrolledStudent4.getSy(), selectedCustomEnrolledStudent4.getSem(),
//                selectedCustomEnrolledStudent4.getGrade_level(),selectedCustomEnrolledStudent4.getStrand_code()));

        col4No.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCClassStud, String>,ObservableValue<String>>(){
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCClassStud, String> p) {
                return new ReadOnlyObjectWrapper(tbl4Studentgrades.getItems().indexOf(p.getValue()) + 1);
            }
        });

        col4No.setSortable(false);

        col4SubjCode.setCellValueFactory(new PropertyValueFactory<>("csCrsCode"));
        col4SubjDesc.setCellValueFactory(new PropertyValueFactory<>("subject_desc"));
        col4SubjUnit.setCellValueFactory(new PropertyValueFactory<>("subject_unit"));
        col4Grade1.setCellValueFactory(new PropertyValueFactory<>("csMidGrade"));
        col4Grade2.setCellValueFactory(new PropertyValueFactory<>("csSecQtr"));
        col4FG.setCellValueFactory(new PropertyValueFactory<>("subject_fg"));

        tbl4Studentgrades.setItems(listStudentSubjectGrades4);

        double initial_grades = 0, total_grades = 0, total_units = 0 ;
        for(ShCClassStud row : listStudentSubjectGrades4) {
        //            total_grades += (Double.parseDouble(row.getSubject_fg()) * Double.parseDouble(row.getSubject_unit()));
        total_units += Double.parseDouble(row.getSubject_unit());
        initial_grades = Double.parseDouble(row.getSubject_fg()) * Double.parseDouble(row.getSubject_unit());
        total_grades += initial_grades;


        ReportStudentGrades reportStudentGrades = new ReportStudentGrades();

        reportStudentGrades.setSubj_code_4(row.getCsCrsCode().trim());
        reportStudentGrades.setSubj_type_4(row.getSubject_type());
        reportStudentGrades.setSubj_desc_4(row.getSubject_desc());
        reportStudentGrades.setSubj_grade1_4(Double.parseDouble(row.getCsMidGrade()));
        reportStudentGrades.setSubj_grade2_4(Double.parseDouble(row.getCsSecQtr()));
        reportStudentGrades.setSubj_fg_4(Double.parseDouble(row.getSubject_fg()));
        reportStudentGrades.setSubj_unit_4(Double.parseDouble(row.getSubject_unit()));

        //            records_2ndSem.add(reportStudentGrades);
        records_g12_2ndSem.add(reportStudentGrades);
        }

        DecimalFormat df = new DecimalFormat("00.00");
        //        double gpa = total_grades / total_units;
        String GPA_4 = df.format(total_grades/total_units);
        if(total_grades/total_units == 0 || Double.isNaN(total_grades/total_units))
            txt4GPA.setText("N/A");
        else
            txt4GPA.setText(GPA_4);
        //        txt4GPA.setText(String.valueOf(total_grades/total_units));
        txt4Unit.setText(String.valueOf(total_units));
        //</editor-fold>
    }
    
    private void setStudentStrandSummer(){
        //<editor-fold defaultstate="collapsed" desc="code">
        // String summerdaterange = new SimpleDateFormat("MM/dd/yyyy").format(result.getDate("enroll_date")) +" to "+ new SimpleDateFormat("MM/dd/yyyy").format(result.getDate("enroll_date_to"));
        // param.put("g11_summerdate", summerdaterange ); 
        String summer_schoolname_g11 = "Ateneo de Zamboanga University";
        String summer_schoolid_g11 = "404925";
        String summer_schoolname_g12 = "Ateneo de Zamboanga University";
        String summer_schoolid_g12 = "404925";
        
        String summersy_g11 = (txt1SY.getText() == null? "" : txt1SY.getText());
        
        String summerdaterange_g11 = "";
        
        if(summersy_g11.equals("2016-2017")){
            summerdaterange_g11 = "04/11/2017 to 05/19/2017";
        }
        if(summersy_g11.equals("2017-2018")){
            summerdaterange_g11 = "04/11/2018 to 05/19/2018";
        }
        if(summersy_g11.equals("2018-2019")){
            summerdaterange_g11 = "04/22/2019 to 05/25/2019";
        }
        
        
        String summersy_g12 = (txt2SY.getText() == null ? "" : txt2SY.getText());
        String summerdaterange_g12 = "";
        
        if(summersy_g12.equals("2016-2017")){
            summerdaterange_g12 = "04/11/2017 to 05/19/2017";
        }
        if(summersy_g12.equals("2017-2018")){
            summerdaterange_g12 = "04/11/2018 to 05/19/2018";
        }
        if(summersy_g12.equals("2018-2019")){
            summerdaterange_g12 = "04/22/2019 to 05/25/2019";
        }
        
        if(shCClassStudModel.getResStudentGradesIndividualSummer11("3", selectedCustomEnrolledStudent.getStud_idnum()).isEmpty()){
            summerdaterange_g11 = "";
            summer_schoolname_g11 = "";
            summer_schoolid_g11 = "";
        }
        if(shCClassStudModel.getResStudentGradesIndividualSummer12("3", selectedCustomEnrolledStudent.getStud_idnum()).isEmpty()){
            summerdaterange_g12 = "";
            summer_schoolname_g12 = "";
            summer_schoolid_g12 = "";
        }
        
        parameters.put("STUD_SUMMER_DATE1", summerdaterange_g11);
        parameters.put("STUD_SUMMER_DATE2", summerdaterange_g12);
        parameters.put("STUD_SUMMER_SCHOOLNAME1", summer_schoolname_g11);
        parameters.put("STUD_SUMMER_SCHOOLID1", summer_schoolid_g11);
        parameters.put("STUD_SUMMER_SCHOOLNAME2", summer_schoolname_g12);
        parameters.put("STUD_SUMMER_SCHOOLID2", summer_schoolid_g12);
        //</editor-fold>
    }
    
    private void loadStudentSubjectsInTableSummer() {
        //<editor-fold defaultstate="collapsed" desc="code">
        if (!listStudentSubjectGrades5.isEmpty()) {
            listStudentSubjectGrades5.clear();
            listStudentSubjectGrades5_1.clear();
            listStudentSubjectGrades5_2.clear();
        }
        
        listStudentSubjectGrades5.addAll(shCClassStudModel.getResStudentGradesIndividualSummer("3",
                selectedCustomEnrolledStudent.getStud_idnum()));
        
        listStudentSubjectGrades5_1.addAll(shCClassStudModel.getResStudentGradesIndividualSummer11("3",
                selectedCustomEnrolledStudent.getStud_idnum()));
        listStudentSubjectGrades5_2.addAll(shCClassStudModel.getResStudentGradesIndividualSummer12("3",
                selectedCustomEnrolledStudent.getStud_idnum()));
        
        colSummerNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCClassStud, String>,ObservableValue<String>>(){
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCClassStud, String> p) {
                return new ReadOnlyObjectWrapper(tblSummer.getItems().indexOf(p.getValue()) + 1);
            }
        });
        
        colSummerNo.setSortable(false);
        
        colSummerSubjCode.setCellValueFactory(new PropertyValueFactory<>("csCrsCode"));
        colSummerSubjDesc.setCellValueFactory(new PropertyValueFactory<>("subject_desc"));
        colSummerSubjUnit.setCellValueFactory(new PropertyValueFactory<>("subject_unit"));
        colSummerGrade1.setCellValueFactory(new PropertyValueFactory<>("csMidGrade"));
        colSummerGrade2.setCellValueFactory(new PropertyValueFactory<>("csSecQtr"));
        colSummerFG.setCellValueFactory(new PropertyValueFactory<>("subject_fg"));
        
        tblSummer.setItems(listStudentSubjectGrades5);
        DecimalFormat df = new DecimalFormat("00");
        
        int ctr = 0;
        for(ShCClassStud row : listStudentSubjectGrades5_1) {
            parameters.put("g11_summersubjtype" + (ctr+1), listStudentSubjectGrades5_1.get(ctr).getSubject_type() );
            parameters.put("g11_summersubject" + (ctr+1), listStudentSubjectGrades5_1.get(ctr).getSubject_desc());
            parameters.put("g11_summer_fingrade" + (ctr+1), listStudentSubjectGrades5_1.get(ctr).getCsMidGrade());
            parameters.put("g11_summer_remgrade" + (ctr+1), listStudentSubjectGrades5_1.get(ctr).getCsSecQtr());
            parameters.put("g11_summer_recompgrade" + (ctr+1), df.format(Double.parseDouble(listStudentSubjectGrades5_1.get(ctr).getSubject_fg())));
            ctr++;
        }
        
        ctr = 0;
        for(ShCClassStud row : listStudentSubjectGrades5_2) {
            parameters.put("g12_summersubjtype" + (ctr+1), listStudentSubjectGrades5_2.get(ctr).getSubject_type() );
            parameters.put("g12_summersubject" + (ctr+1), listStudentSubjectGrades5_2.get(ctr).getSubject_desc());
            parameters.put("g12_summer_fingrade" + (ctr+1), listStudentSubjectGrades5_2.get(ctr).getCsMidGrade());
            parameters.put("g12_summer_remgrade" + (ctr+1), listStudentSubjectGrades5_2.get(ctr).getCsSecQtr());
            parameters.put("g12_summer_recompgrade" + (ctr+1), df.format(Double.parseDouble(listStudentSubjectGrades5_2.get(ctr).getSubject_fg())));
            ctr++;
        }
    //</editor-fold>
    }
    
    private void loadStudentCoreValuesGrade11InTable() {
        //<editor-fold defaultstate="collapsed" desc="code">
        if (!listShCCoreValuesGrade11.isEmpty()) {
            listShCCoreValuesGrade11.clear();
        }
        listShCCoreValuesGrade11.addAll(shCCorevaluesModel.getResStudentCoreValues(selectedCustomEnrolledStudent1.getSy(),
                "11", selectedCustomEnrolledStudent.getStud_idnum()));
        
        colG11CvNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCCorevalues, String>,ObservableValue<String>>(){
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCCorevalues, String> p) {
                return new ReadOnlyObjectWrapper(tblGrade11CoreValues.getItems().indexOf(p.getValue()) + 1);
            }
        });
        
        colG11CvNo.setSortable(false);
        
        colG11CvDesc.setCellValueFactory(new PropertyValueFactory<>("cvStrand"));
        
        colG11CvGrade1.setCellValueFactory(new PropertyValueFactory<>("grade1"));
        colG11CvGrade2.setCellValueFactory(new PropertyValueFactory<>("grade2"));
        colG11CvGrade3.setCellValueFactory(new PropertyValueFactory<>("grade3"));
        colG11CvGrade4.setCellValueFactory(new PropertyValueFactory<>("grade4"));
        
        tblGrade11CoreValues.setItems(listShCCoreValuesGrade11);
        //</editor-fold>
    }
    
    private void loadStudentCoreValuesGrade12InTable() {        
        // <editor-fold defaultstate="collapsed" desc="Code">
        if (!listShCCoreValuesGrade12.isEmpty()) {
            listShCCoreValuesGrade12.clear();
        }
        /***********************************************************/
        /* ADDED - Checking if grade 12 sem 1 and sem 2 schoolyear */
        /* are the same for core value consistency in the event of */
        /* returnee/transferee.                                    */
        /***********************************************************/
        if(selectedCustomEnrolledStudent3.getSy() == selectedCustomEnrolledStudent4.getSy()){
            listShCCoreValuesGrade12.addAll(shCCorevaluesModel.getResStudentCoreValues(selectedCustomEnrolledStudent3.getSy(), "12", selectedCustomEnrolledStudent.getStud_idnum()));   
        }
        else{
            listShCCoreValuesGrade12.addAll(shCCorevaluesModel.getResStudentCoreValues(selectedCustomEnrolledStudent.getSy(), "12", selectedCustomEnrolledStudent.getStud_idnum())); 
        }
                
        colG12CvNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCCorevalues, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCCorevalues, String> p) {
               return new ReadOnlyObjectWrapper(tblGrade12CoreValues.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colG12CvNo.setSortable(false);
        
        colG12CvDesc.setCellValueFactory(new PropertyValueFactory<>("cvStrand"));

        colG12CvGrade1.setCellValueFactory(new PropertyValueFactory<>("grade1"));
        colG12CvGrade2.setCellValueFactory(new PropertyValueFactory<>("grade2"));
        colG12CvGrade3.setCellValueFactory(new PropertyValueFactory<>("grade3"));
        colG12CvGrade4.setCellValueFactory(new PropertyValueFactory<>("grade4"));

        tblGrade12CoreValues.setItems(listShCCoreValuesGrade12);
        // </editor-fold>
    }
    
    private void loadStudentAttendanceGrade11InTable() {        
        // <editor-fold defaultstate="collapsed" desc="Code">
        if (!listShCAttendanceGrade11.isEmpty()) {
            listShCAttendanceGrade11.clear();
        }
        listShCAttendanceGrade11.addAll(this.shCAttendanceModel.getResStudentAttendanceByAbsentAndPresentDays(selectedCustomEnrolledStudent1.getSy(),
                selectedCustomEnrolledStudent.getStud_idnum()));
                
        colG11AttNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCAttendance, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCAttendance, String> p) {
               return new ReadOnlyObjectWrapper(tblGrade11Attendance.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colG11AttNo.setSortable(false);

        colG11AttDesc.setCellValueFactory(new PropertyValueFactory<>("days_desc"));
        colG11AttMonth1.setCellValueFactory(new PropertyValueFactory<>("month1"));
        colG11AttMonth2.setCellValueFactory(new PropertyValueFactory<>("month2"));
        colG11AttMonth3.setCellValueFactory(new PropertyValueFactory<>("month3"));
        colG11AttMonth4.setCellValueFactory(new PropertyValueFactory<>("month4"));
        colG11AttMonth6.setCellValueFactory(new PropertyValueFactory<>("month6"));
        colG11AttMonth7.setCellValueFactory(new PropertyValueFactory<>("month7"));
        colG11AttMonth8.setCellValueFactory(new PropertyValueFactory<>("month8"));
        colG11AttMonth9.setCellValueFactory(new PropertyValueFactory<>("month9"));
        colG11AttMonth10.setCellValueFactory(new PropertyValueFactory<>("month10"));
        colG11AttMonth11.setCellValueFactory(new PropertyValueFactory<>("month11"));
        colG11AttMonth12.setCellValueFactory(new PropertyValueFactory<>("month12"));
        colG11AttTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblGrade11Attendance.setItems(listShCAttendanceGrade11);  
        // </editor-fold>
    }
    
    private void loadStudentAttendanceGrade12InTable() {
        // <editor-fold defaultstate="collapsed" desc="Code">
        if (!listShCAttendanceGrade12.isEmpty()) {
            listShCAttendanceGrade12.clear();
        }
        /***********************************************************/
        /* ADDED - Checking if grade 12 sem 1 and sem 2 schoolyear */
        /* are the same for attendance consistency in the event of */
        /* returnee/transferee.                                    */
        /***********************************************************/
        System.out.println(selectedCustomEnrolledStudent4.getSy());
        if(selectedCustomEnrolledStudent3.getSy() == selectedCustomEnrolledStudent4.getSy()){
            listShCAttendanceGrade12.addAll(this.shCAttendanceModel.getResStudentAttendanceByAbsentAndPresentDays(selectedCustomEnrolledStudent3.getSy(),
                selectedCustomEnrolledStudent.getStud_idnum()));
        }
        else{
            listShCAttendanceGrade12.addAll(this.shCAttendanceModel.getResStudentAttendanceByAbsentAndPresentDays(selectedCustomEnrolledStudent3.getSy(),
                selectedCustomEnrolledStudent.getStud_idnum()));
        }
                
        colG12AttNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCAttendance, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCAttendance, String> p) {
               return new ReadOnlyObjectWrapper(tblGrade12Attendance.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colG12AttNo.setSortable(false);

        colG12AttDesc.setCellValueFactory(new PropertyValueFactory<>("days_desc"));
        colG12AttMonth1.setCellValueFactory(new PropertyValueFactory<>("month1"));
        colG12AttMonth2.setCellValueFactory(new PropertyValueFactory<>("month2"));
        colG12AttMonth3.setCellValueFactory(new PropertyValueFactory<>("month3"));
        colG12AttMonth4.setCellValueFactory(new PropertyValueFactory<>("month4"));
        colG12AttMonth6.setCellValueFactory(new PropertyValueFactory<>("month6"));
        colG12AttMonth7.setCellValueFactory(new PropertyValueFactory<>("month7"));
        colG12AttMonth8.setCellValueFactory(new PropertyValueFactory<>("month8"));
        colG12AttMonth9.setCellValueFactory(new PropertyValueFactory<>("month9"));
        colG12AttMonth10.setCellValueFactory(new PropertyValueFactory<>("month10"));
        colG12AttMonth11.setCellValueFactory(new PropertyValueFactory<>("month11"));
        colG12AttMonth12.setCellValueFactory(new PropertyValueFactory<>("month12"));
        colG12AttTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblGrade12Attendance.setItems(listShCAttendanceGrade12);
        // </editor-fold>
    }
    
    private void loadStudentProfile() {        
        // <editor-fold defaultstate="collapsed" desc="Code">
        txtProfLname.setText(selectedShApplicant.getAppLname().trim());
        txtProfFname.setText(selectedShApplicant.getAppFname().trim());
        txtProfMname.setText(selectedShApplicant.getAppMiddlename().trim());
        txtProfSuffix.setText((selectedShApplicant.getAppSuffix() == null) ? "" : selectedShApplicant.getAppSuffix().trim());        
        txtProfLRN.setText((selectedShApplicant.getAppLrn() == null) ? "" : selectedShApplicant.getAppLrn().trim());        
        txtProfJHSchool.setText((selectedShApplicant.getAppJuniorhs() == null) ? "" : selectedShApplicant.getAppJuniorhs().trim());
        txtProfJHAddress.setText((selectedShApplicant.getAppJuniorhsAddress() == null) ? "" : selectedShApplicant.getAppJuniorhsAddress().trim());
        txtProfJHSY.setText((selectedShApplicant.getAppJhsSy() == null) ? "" : selectedShApplicant.getAppJhsSy().trim());        
        txtProfJHGPA.setText((selectedShApplicant.getAppJhsGpa() == null) ? "" : selectedShApplicant.getAppJhsGpa().trim());
        
        
        Date date = selectedShApplicant.getAppBdate();    
        int dateYear = date.getYear()+ 1900;
        int dateMonth = date.getMonth()+ 1;
        int dateDate = date.getDate();        
        dpProf.setValue(LocalDate.of(dateYear, dateMonth, dateDate));
        
        this.currentShTermReg = shTermRegModel.getAdmissionDate();
        Date admissionDate = currentShTermReg.getEnrollDate();       
        int admDateYear = admissionDate.getYear()+ 1900;
        int admDateMonth = admissionDate.getMonth()+ 1;
        int admDateDate = admissionDate.getDate();       
        dpAdmission.setValue(LocalDate.of(admDateYear, admDateMonth, admDateDate));
        

        ObservableList<String> listGender = FXCollections.observableArrayList();
        if(selectedShApplicant.getAppSex().trim().equals("M")){
            listGender.add("Male");
            listGender.add("Female");

        }
        else{
            listGender.add("Female");
            listGender.add("Male");
        }
        cbProfGender.setItems(listGender);
        cbProfGender.getSelectionModel().select(0);
        
        // </editor-fold>
    }
    
    public void printCardGrade11() throws JRException{
        // <editor-fold defaultstate="collapsed" desc="Code">
        InputStream source=this.getClass().getResourceAsStream("/report/formCard.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(source);
          
        List<ReportStudentGrades> plist = new ArrayList<ReportStudentGrades>();  
        
        JRBeanCollectionDataSource list1;// = new JRBeanCollectionDataSource(records_g11_1stSem);
        JRBeanCollectionDataSource list2;
        
        records_1stSem = new ArrayList<>();
        records_2ndSem = new ArrayList<>();

        String instructor_id = "";
        if(cbPrintSem.getSelectionModel().getSelectedItem().equalsIgnoreCase("1")){
           
            instructor_id = shClassInfoModel.getRowStudentModeratingSchedule(txt1SY.getText(), "1", "11", 
                    selectedCustomEnrolledStudent1.getStrand_code(), this.selectedCustomEnrolledStudent1.getStrand_group()).getClaTeaIdnum();     

            parameters.put("STUD_SECTION",  txt1Section.getText());
            parameters.put("STUD_SY",  txt1SY.getText());
            parameters.put("STUD_STRAND",  txt1Strand.getText());
            parameters.put("STUD_MODERATOR",  shInstructorModel.getRowInstructorByID(instructor_id).getInstructorName().toUpperCase().trim());
            parameters.put("STUD_STRAND",  selectedCustomEnrolledStudent1.getStrand_code_desc());
            parameters.put("STUD_STATUS", selectedCustomEnrolledStudent1.getStud_status() );
            
        }else{

            instructor_id = shClassInfoModel.getRowStudentModeratingSchedule(txt2SY.getText(), "2", "11", 
                    selectedCustomEnrolledStudent2.getStrand_code(), this.selectedCustomEnrolledStudent2.getStrand_group()).getClaTeaIdnum();
            
            parameters.put("STUD_SECTION",  txt2Section.getText());
            parameters.put("STUD_SY",  txt2SY.getText());
            parameters.put("STUD_STRAND",  txt2Strand.getText());
            parameters.put("STUD_MODERATOR",  shInstructorModel.getRowInstructorByID(instructor_id).getInstructorName().toUpperCase().trim());
            parameters.put("STUD_STRAND",  selectedCustomEnrolledStudent2.getStrand_code_desc());
            parameters.put("STUD_STATUS", selectedCustomEnrolledStudent2.getStud_status() );
            
        }
       
        for(ReportStudentGrades row_grade : records_g11_1stSem){
            ReportStudentGrades new_grade = new ReportStudentGrades();
            new_grade.setSubj_code(row_grade.getSubj_code_1());
            new_grade.setSubj_desc(row_grade.getSubj_desc_1());
            new_grade.setSubj_unit(row_grade.getSubj_unit_1());
            new_grade.setSubj_grade1(row_grade.getSubj_grade1_1());
            new_grade.setSubj_grade2(row_grade.getSubj_grade2_1());
            new_grade.setSubj_fg(row_grade.getSubj_fg_1());
            records_1stSem.add(new_grade);
        }
        
        for(ReportStudentGrades row_grade : records_g11_2ndSem){
            ReportStudentGrades new_grade = new ReportStudentGrades();
            new_grade.setSubj_code(row_grade.getSubj_code_2());
            new_grade.setSubj_desc(row_grade.getSubj_desc_2());
            new_grade.setSubj_unit(row_grade.getSubj_unit_2());
            new_grade.setSubj_grade1(row_grade.getSubj_grade1_2());
            new_grade.setSubj_grade2(row_grade.getSubj_grade2_2());
            new_grade.setSubj_fg(row_grade.getSubj_fg_2());
            records_2ndSem.add(new_grade);
        }
              
        list1 = new JRBeanCollectionDataSource(records_1stSem);
        list2 = new JRBeanCollectionDataSource(records_2ndSem);
        
        parameters.put("LIST_GRADE1", list1);
        parameters.put("LIST_GRADE2", list2);
        parameters.put("STUD_FULLNAME", selectedShApplicant.getAppLname().trim() +", "+ selectedShApplicant.getAppSuffix().trim() +" "+ selectedShApplicant.getAppFname().trim() +" "+ selectedShApplicant.getAppMiddlename().trim());
        parameters.put("STUD_GRADELEVEL",  "11");
        parameters.put("STUD_IDNUM",  selectedShApplicant.getStudIdnum());
        parameters.put("STUD_LRN",  selectedShApplicant.getAppLrn());
        parameters.put("STUD_SCHOOL_ID", "404925");
        parameters.put("STUD_GPA1", txt1GPA.getText() );
        parameters.put("STUD_GPA2", txt2GPA.getText() );
        
        
        JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());
        JasperViewer viewer = new JasperViewer(jp, false);
        viewer.setVisible(true);
        
        
        try {
            source.close();
        } catch (IOException ex) {
            Logger.getLogger(StudentGradesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // </editor-fold>
    }
    
     public void printCardGrade12() throws JRException{         
        // <editor-fold defaultstate="collapsed" desc="Code">
        InputStream source=this.getClass().getResourceAsStream("/report/formCard.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(source);
          
        List<ReportStudentGrades> plist = new ArrayList<ReportStudentGrades>();  
        
        JRBeanCollectionDataSource list1;// = new JRBeanCollectionDataSource(records_g11_1stSem);
        JRBeanCollectionDataSource list2;
        
        records_1stSem = new ArrayList<>();
        records_2ndSem = new ArrayList<>();

        String instructor_id = "";
        if(cbPrintSem.getSelectionModel().getSelectedItem().equalsIgnoreCase("1")){
           
            
            
            instructor_id = shClassInfoModel.getRowStudentModeratingSchedule(txt3SY.getText(), "1", "12", 
                    selectedCustomEnrolledStudent3.getStrand_code(), this.selectedCustomEnrolledStudent3.getStrand_group()).getClaTeaIdnum();

            parameters.put("STUD_SECTION",  txt3Section.getText());
            parameters.put("STUD_SY",  txt3SY.getText());
            parameters.put("STUD_STRAND",  txt3Strand.getText());
            parameters.put("STUD_MODERATOR",  shInstructorModel.getRowInstructorByID(instructor_id).getInstructorName().toUpperCase().trim());
            parameters.put("STUD_STRAND",  selectedCustomEnrolledStudent3.getStrand_code_desc());
            parameters.put("STUD_STATUS", selectedCustomEnrolledStudent3.getStud_status() );
            
        }else{

            instructor_id = shClassInfoModel.getRowStudentModeratingSchedule(txt4SY.getText(), "2", "12", 
                    selectedCustomEnrolledStudent4.getStrand_code(), this.selectedCustomEnrolledStudent4.getStrand_group()).getClaTeaIdnum();
            
            parameters.put("STUD_SECTION",  txt4Section.getText());
            parameters.put("STUD_SY",  txt4SY.getText());
            parameters.put("STUD_STRAND",  txt4Strand.getText());
            parameters.put("STUD_MODERATOR",  shInstructorModel.getRowInstructorByID(instructor_id).getInstructorName().toUpperCase().trim());
            parameters.put("STUD_STRAND",  selectedCustomEnrolledStudent4.getStrand_code_desc());
            parameters.put("STUD_STATUS", selectedCustomEnrolledStudent4.getStud_status() );
            
        }
       
        for(ReportStudentGrades row_grade : records_g12_1stSem){
            ReportStudentGrades new_grade = new ReportStudentGrades();
            new_grade.setSubj_code(row_grade.getSubj_code_3());
            new_grade.setSubj_desc(row_grade.getSubj_desc_3());
            new_grade.setSubj_unit(row_grade.getSubj_unit_3());
            new_grade.setSubj_grade1(row_grade.getSubj_grade1_3());
            new_grade.setSubj_grade2(row_grade.getSubj_grade2_3());
            new_grade.setSubj_fg(row_grade.getSubj_fg_3());
            records_1stSem.add(new_grade);
        }
        
        for(ReportStudentGrades row_grade : records_g12_2ndSem){
            ReportStudentGrades new_grade = new ReportStudentGrades();
            new_grade.setSubj_code(row_grade.getSubj_code_4());
            new_grade.setSubj_desc(row_grade.getSubj_desc_4());
            new_grade.setSubj_unit(row_grade.getSubj_unit_4());
            new_grade.setSubj_grade1(row_grade.getSubj_grade1_4());
            new_grade.setSubj_grade2(row_grade.getSubj_grade2_4());
            new_grade.setSubj_fg(row_grade.getSubj_fg_4());
            records_2ndSem.add(new_grade);
        }
              
        list1 = new JRBeanCollectionDataSource(records_1stSem);
        list2 = new JRBeanCollectionDataSource(records_2ndSem);
        
        parameters.put("LIST_GRADE1", list1);
        parameters.put("LIST_GRADE2", list2);
        parameters.put("STUD_FULLNAME", selectedShApplicant.getAppLname().trim() +", "+ selectedShApplicant.getAppSuffix().trim() +" "+ selectedShApplicant.getAppFname().trim() +" "+ selectedShApplicant.getAppMiddlename().trim());
        parameters.put("STUD_GRADELEVEL",  "12");
        parameters.put("STUD_IDNUM",  selectedShApplicant.getStudIdnum());
        parameters.put("STUD_LRN",  selectedShApplicant.getAppLrn());
        parameters.put("STUD_SCHOOL_ID", "404925");
        parameters.put("STUD_GPA1", txt3GPA.getText() );
        parameters.put("STUD_GPA2", txt4GPA.getText() );
        
        
        JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());
        JasperViewer viewer = new JasperViewer(jp, false);
        viewer.setVisible(true);
        // </editor-fold>
    }
    
    public void printCoreValuesGrade11() throws JRException{
        // <editor-fold defaultstate="collapsed" desc="Code">
        InputStream source=this.getClass().getResourceAsStream("/report/formCard2.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(source);
          
        JRBeanCollectionDataSource list1;// = new JRBeanCollectionDataSource(records_g11_1stSem);
        JRBeanCollectionDataSource list2;
        

        String instructor_id = "";
        if(cbPrintSem.getSelectionModel().getSelectedItem().equalsIgnoreCase("1")){
            instructor_id = shClassInfoModel.getRowStudentModeratingSchedule(txt1SY.getText(), "1", "11", 
                    selectedCustomEnrolledStudent1.getStrand_code(), this.selectedCustomEnrolledStudent1.getStrand_group()).getClaTeaIdnum();     

            parameters.put("STUD_STRAND",  selectedCustomEnrolledStudent1.getStrand_code_desc());
            parameters.put("STUD_SECTION",  txt1Section.getText());
            parameters.put("STUD_MODERATOR",  shInstructorModel.getRowInstructorByID(instructor_id).getInstructorName());
        }else{

            instructor_id = shClassInfoModel.getRowStudentModeratingSchedule(txt2SY.getText(), "2", "11", 
                    selectedCustomEnrolledStudent2.getStrand_code(), this.selectedCustomEnrolledStudent2.getStrand_group()).getClaTeaIdnum();
            
            parameters.put("STUD_STRAND",  selectedCustomEnrolledStudent2.getStrand_code_desc());
            parameters.put("STUD_SECTION",  txt2Section.getText());
            parameters.put("STUD_MODERATOR",  shInstructorModel.getRowInstructorByID(instructor_id).getInstructorName());
        }
        
        List<ShCCorevalues> records_CoreValues = new ArrayList<>();
        List<ShCAttendance> records_Attendance = new ArrayList<>();
        
        for(ShCCorevalues row: listShCCoreValuesGrade11){
            ShCCorevalues new_row = new ShCCorevalues();
            new_row.setCorevalue_desc(row.getCvStrand());
            new_row.setGrade1(row.getGrade1());
            new_row.setGrade2(row.getGrade2());
            new_row.setGrade3(row.getGrade3());
            new_row.setGrade4(row.getGrade4());
            records_CoreValues.add(new_row);
            
        } 
        
        for(ShCAttendance row: listShCAttendanceGrade11){
            ShCAttendance new_row = new ShCAttendance();
            new_row.setDays_desc(row.getDays_desc());
            new_row.setDays_total(row.getDays_total());
            new_row.setMonth1(row.getMonth1());
            new_row.setMonth2(row.getMonth2());
            new_row.setMonth3(row.getMonth3());
            new_row.setMonth4(row.getMonth4());
            new_row.setMonth5(row.getMonth5());
            new_row.setMonth6(row.getMonth6());
            new_row.setMonth7(row.getMonth7());
            new_row.setMonth8(row.getMonth8());
            new_row.setMonth9(row.getMonth9());
            new_row.setMonth10(row.getMonth10());
            new_row.setMonth11(row.getMonth11());
            new_row.setMonth12(row.getMonth12());
            records_Attendance.add(new_row);
        }
              
        list1 = new JRBeanCollectionDataSource(records_CoreValues);
        list2 = new JRBeanCollectionDataSource(records_Attendance);
        
        parameters.put("LIST_COREVALUES", list1);
        parameters.put("LIST_ATTENDANCE", list2);
        parameters.put("STUD_FULLNAME", selectedShApplicant.getAppLname().trim() +", "+ selectedShApplicant.getAppSuffix().trim() +" "+ selectedShApplicant.getAppFname().trim() +" "+ selectedShApplicant.getAppMiddlename().trim());
      
        
        
        JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());
        JasperViewer viewer = new JasperViewer(jp, false);
        viewer.setVisible(true);
        // </editor-fold>
    }
    
    public void printCoreValuesGrade12() throws JRException{        
        // <editor-fold defaultstate="collapsed" desc="Code">
        InputStream source=this.getClass().getResourceAsStream("/report/formCard2.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(source);
          
        JRBeanCollectionDataSource list1;// = new JRBeanCollectionDataSource(records_g11_1stSem);
        JRBeanCollectionDataSource list2;
        

        String instructor_id = "";
        if(cbPrintSem.getSelectionModel().getSelectedItem().equalsIgnoreCase("1")){
           
            instructor_id = shClassInfoModel.getRowStudentModeratingSchedule(txt3SY.getText(), "1", "12", 
                    selectedCustomEnrolledStudent3.getStrand_code(), this.selectedCustomEnrolledStudent3.getStrand_group()).getClaTeaIdnum();     

            parameters.put("STUD_STRAND",  selectedCustomEnrolledStudent3.getStrand_code_desc());
            parameters.put("STUD_SECTION",  txt3Section.getText());
            parameters.put("STUD_MODERATOR",  shInstructorModel.getRowInstructorByID(instructor_id).getInstructorName());
        }else{

            instructor_id = shClassInfoModel.getRowStudentModeratingSchedule(txt4SY.getText(), "2", "12", 
                    selectedCustomEnrolledStudent4.getStrand_code(), this.selectedCustomEnrolledStudent4.getStrand_group()).getClaTeaIdnum();
            
            parameters.put("STUD_STRAND",  selectedCustomEnrolledStudent4.getStrand_code_desc());
            parameters.put("STUD_SECTION",  txt4Section.getText());
            parameters.put("STUD_MODERATOR",  shInstructorModel.getRowInstructorByID(instructor_id).getInstructorName().toUpperCase().trim());
           
            
        }
        
        List<ShCCorevalues> records_CoreValues = new ArrayList<>();
        List<ShCAttendance> records_Attendance = new ArrayList<>();
        
         for(ShCCorevalues row: listShCCoreValuesGrade12){
            ShCCorevalues new_row = new ShCCorevalues();
            new_row.setCorevalue_desc(row.getCvStrand());
            new_row.setGrade1(row.getGrade1());
            new_row.setGrade2(row.getGrade2());
            new_row.setGrade3(row.getGrade3());
            new_row.setGrade4(row.getGrade4());
            records_CoreValues.add(new_row);
        } 
        
        for(ShCAttendance row: listShCAttendanceGrade12){
            ShCAttendance new_row = new ShCAttendance();
            new_row.setDays_desc(row.getDays_desc());
            new_row.setDays_total(row.getDays_total());
            new_row.setMonth1(row.getMonth1());
            new_row.setMonth2(row.getMonth2());
            new_row.setMonth3(row.getMonth3());
            new_row.setMonth4(row.getMonth4());
            new_row.setMonth5(row.getMonth5());
            new_row.setMonth6(row.getMonth6());
            new_row.setMonth7(row.getMonth7());
            new_row.setMonth8(row.getMonth8());
            new_row.setMonth9(row.getMonth9());
            new_row.setMonth10(row.getMonth10());
            new_row.setMonth11(row.getMonth11());
            new_row.setMonth12(row.getMonth12());
            records_Attendance.add(new_row);
        }
       
              
        list1 = new JRBeanCollectionDataSource(records_CoreValues);
        list2 = new JRBeanCollectionDataSource(records_Attendance);
        
        parameters.put("LIST_COREVALUES", list1);
        parameters.put("LIST_ATTENDANCE", list2);
        parameters.put("STUD_FULLNAME", selectedShApplicant.getAppLname().trim() +", "+ selectedShApplicant.getAppSuffix().trim() +" "+ selectedShApplicant.getAppFname().trim() +" "+ selectedShApplicant.getAppMiddlename().trim());
      
        
        
        JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());
        JasperViewer viewer = new JasperViewer(jp, false);
        viewer.setVisible(true);
        // </editor-fold>
    }
     
    @FXML
    public void actionPrintTOR(ActionEvent event) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Code">
        InputStream source=this.getClass().getResourceAsStream("/report/reportTOR_new.jrxml");
        //InputStream source=this.getClass().getResourceAsStream("/report/formTOR.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(source);
          
        List<ReportStudentGrades> plist = new ArrayList<ReportStudentGrades>();
//        plist.add(recordsG11_1stSem);
//        JRBeanCollectionDataSource jcs = new JRBeanCollectionDataSource(recordsG11_1stSem);
//        JRBeanCollectionDataSource jcs = new JRBeanCollectionDataSource(plist);
//        JREmptyDataSource a = new JREmptyDataSource(recordsG11_1stSem);
        
        JRBeanCollectionDataSource list1 = new JRBeanCollectionDataSource(records_g11_1stSem);
        JRBeanCollectionDataSource list2 = new JRBeanCollectionDataSource(records_g11_2ndSem); 
        JRBeanCollectionDataSource list3 = new JRBeanCollectionDataSource(records_g12_1stSem);
        JRBeanCollectionDataSource list4 = new JRBeanCollectionDataSource(records_g12_2ndSem);

//        for(ReportStudentGrades row: records_1stSem){
//            System.out.println("VAL: " + row.getSubj_desc_1() +" "+ row.getSubj_desc_3());
//        }
        
        parameters.put("item1", list1);
        parameters.put("item2", list2);
        parameters.put("item3", list3);
        parameters.put("item4", list4);
        parameters.put("STUD_FULLNAME", selectedShApplicant.getAppLname().trim() +", "+ selectedShApplicant.getAppSuffix().trim() +" "+ selectedShApplicant.getAppFname().trim() +" "+ selectedShApplicant.getAppMiddlename().trim());
        parameters.put("STUD_BDATE",  new SimpleDateFormat("MM/dd/yyyy").format(selectedShApplicant.getAppBdate()));
        parameters.put("STUD_JHS_SCH_NAME",  selectedShApplicant.getAppJuniorhs());
        parameters.put("STUD_JHS_SCH_ADDRESS",  selectedShApplicant.getAppJuniorhsAddress());
        parameters.put("STUD_JHS_SY",  selectedShApplicant.getAppJhsSy());
        
        DecimalFormat df = new DecimalFormat("00.00");
        String GPA = "";
        if(selectedShApplicant.getAppJhsGpa() == null || selectedShApplicant.getAppJhsGpa() == "")
            GPA = "";
        else
            GPA = df.format(Double.parseDouble(selectedShApplicant.getAppJhsGpa())) + '%';
            
        
        parameters.put("STUD_JHS_AVG",  GPA);
        parameters.put("STUD_LRN",  selectedShApplicant.getAppLrn());
        //parameters.put("STUD_GENDER",  selectedShApplicant.getAppSex());
        parameters.put("STUD_GENDER",  cbProfGender.getSelectionModel().getSelectedItem());        
        //parameters.put("STUD_SHS_ADM_DATE", cbPrintGradelevel.getSelectionModel().getSelectedItem()); 
        parameters.put("STUD_SHS_ADM_DATE", (dpAdmission.getValue() == null ? "" : dpAdmission.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
        parameters.put("STUD_SHS_GRAD_DATE", (dpGraduation.getValue() == null ? "" : dpGraduation.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
        
        //--- gpa
//        parameters.put("STUD_GPA_1", (Double.parseDouble(txt1GPA.getText()) > 0)? txt1GPA.getText() : "0.0" );
//        parameters.put("STUD_TOT_UNIT_1", (Double.parseDouble(txt1Unit.getText()) > 0)? txt1Unit.getText() : "0.0" );
//        parameters.put("STUD_GPA_2",  (Double.parseDouble(txt2GPA.getText()) > 0)? txt2GPA.getText() : "0.0" );
//        parameters.put("STUD_TOT_UNIT_2", (Double.parseDouble(txt2Unit.getText()) > 0)? txt2Unit.getText() : "0.0");
//        parameters.put("STUD_GPA_3", (Double.parseDouble(txt3GPA.getText()) > 0)? txt3GPA.getText() : "0.0" );
//        parameters.put("STUD_TOT_UNIT_3", (Double.parseDouble(txt3Unit.getText()) > 0)? txt3Unit.getText() : "0.0");
//        parameters.put("STUD_GPA_4",  (Double.parseDouble(txt4GPA.getText()) > 0)? txt4GPA.getText() : "0.0" );
//        parameters.put("STUD_TOT_UNIT_4", (Double.parseDouble(txt4Unit.getText()) > 0)? txt4Unit.getText() : "0.0" );
        
        parameters.put("STUD_GPA_1", txt1GPA.getText() );
        parameters.put("STUD_TOT_UNIT_1", txt1Unit.getText() );
        parameters.put("STUD_GPA_2",  txt2GPA.getText() );
        parameters.put("STUD_TOT_UNIT_2", txt2Unit.getText());
        parameters.put("STUD_GPA_3", txt3GPA.getText() );
        parameters.put("STUD_TOT_UNIT_3", txt3Unit.getText());
        parameters.put("STUD_GPA_4",  txt4GPA.getText() );
        parameters.put("STUD_TOT_UNIT_4", txt4Unit.getText() );
        
        double gpa_1 = (txt1GPA.getText().equals("N/A")) ? 0.00 : Double.parseDouble(txt1GPA.getText());
        double gpa_2 = (txt2GPA.getText().equals("N/A")) ? 0.00 : Double.parseDouble(txt2GPA.getText());
        double gpa_3 = (txt3GPA.getText().equals("N/A")) ? 0.00 : Double.parseDouble(txt3GPA.getText());
        double gpa_4 = (txt4GPA.getText().equals("N/A")) ? 0.00 : Double.parseDouble(txt4GPA.getText());

        
        double gen_ave = (gpa_1 + gpa_2 + gpa_3 + gpa_4)/4;
        String gpa_final = df.format(gen_ave);
        parameters.put("GEN_AVERAGE", gpa_final);
        
        parameters.put("GEN_DAY", txtGenDay.getText() );
        parameters.put("GEN_MONTH_YEAR", txtGenMonthYear.getText() );
        parameters.put("GEN_ADMISSION", txtGenAdmission.getText() );
        parameters.put("GEN_RECEIVER", txtGenReceiver.getText() );
        parameters.put("GEN_DATE_RECEIVED", txtGenDateReceived.getText() );
        parameters.put("GEN_STATEMENT", "This student is eligible in this "+ ((txtGenDay.getText().isEmpty())? "_______" : txtGenDay.getText()) +
                " of "+ ((txtGenMonthYear.getText().isEmpty())? "______________" : txtGenMonthYear.getText()) +
                " for admission to "+ ((txtGenAdmission.getText().isEmpty())? "_________" : txtGenAdmission.getText()) +
                " and has no money or responsibility in this school. Copy of this record sent to "+ ((txtGenReceiver.getText().isEmpty())? "_____________" : txtGenReceiver.getText()) +
                " on "+ ((txtGenDateReceived.getText().isEmpty())? "_____________" : txtGenDateReceived.getText()) +".");
        
        parameters.put("GEN_STRAND", (txtGenStrand.getText().isEmpty())? "" : txtGenStrand.getText());
        parameters.put("GEN_AWARD", (txtGenAward.getText().isEmpty())? "" : txtGenAward.getText());   
        parameters.put("GEN_PURPOSE", txtGenPurpose.getText() );
        
        JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());
//        JasperPrint jp = JasperFillManager.fillReport("report/reportTOR.jasper", parameters, new JREmptyDataSource());
        JasperViewer viewer = new JasperViewer(jp, false);
        viewer.setVisible(true);
        // </editor-fold>
    }    
    
    @FXML
    public void actionPrintEvaluationTOR(ActionEvent event) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Code">
        InputStream source=this.getClass().getResourceAsStream("/report/reportTOR_evaluation.jrxml");
        //InputStream source=this.getClass().getResourceAsStream("/report/formTOR.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(source);
          
        List<ReportStudentGrades> plist = new ArrayList<ReportStudentGrades>();
//        plist.add(recordsG11_1stSem);
//        JRBeanCollectionDataSource jcs = new JRBeanCollectionDataSource(recordsG11_1stSem);
//        JRBeanCollectionDataSource jcs = new JRBeanCollectionDataSource(plist);
//        JREmptyDataSource a = new JREmptyDataSource(recordsG11_1stSem);
        
        JRBeanCollectionDataSource list1 = new JRBeanCollectionDataSource(records_g11_1stSem);
        JRBeanCollectionDataSource list2 = new JRBeanCollectionDataSource(records_g11_2ndSem); 
        JRBeanCollectionDataSource list3 = new JRBeanCollectionDataSource(records_g12_1stSem);
        JRBeanCollectionDataSource list4 = new JRBeanCollectionDataSource(records_g12_2ndSem);

//        for(ReportStudentGrades row: records_1stSem){
//            System.out.println("VAL: " + row.getSubj_desc_1() +" "+ row.getSubj_desc_3());
//        }
        
        parameters.put("item1", list1);
        parameters.put("item2", list2);
        parameters.put("item3", list3);
        parameters.put("item4", list4);
        parameters.put("STUD_FULLNAME", selectedShApplicant.getAppLname().trim() +", "+ selectedShApplicant.getAppSuffix().trim() +" "+ selectedShApplicant.getAppFname().trim() +" "+ selectedShApplicant.getAppMiddlename().trim());
        parameters.put("STUD_BDATE",  new SimpleDateFormat("MM/dd/yyyy").format(selectedShApplicant.getAppBdate()));
        parameters.put("STUD_JHS_SCH_NAME",  selectedShApplicant.getAppJuniorhs());
        parameters.put("STUD_JHS_SCH_ADDRESS",  selectedShApplicant.getAppJuniorhsAddress());
        parameters.put("STUD_JHS_SY",  selectedShApplicant.getAppJhsSy());
        
        DecimalFormat df = new DecimalFormat("00.00");
        String GPA = "";
        if(selectedShApplicant.getAppJhsGpa() == null || selectedShApplicant.getAppJhsGpa() == "")
            GPA = "";
        else
            GPA = df.format(Double.parseDouble(selectedShApplicant.getAppJhsGpa())) + '%';
            
        
        parameters.put("STUD_JHS_AVG",  GPA);
        parameters.put("STUD_LRN",  selectedShApplicant.getAppLrn());
        //parameters.put("STUD_GENDER",  selectedShApplicant.getAppSex());
        parameters.put("STUD_GENDER",  cbProfGender.getSelectionModel().getSelectedItem());        
        //parameters.put("STUD_SHS_ADM_DATE", cbPrintGradelevel.getSelectionModel().getSelectedItem()); 
        parameters.put("STUD_SHS_ADM_DATE", (dpAdmission.getValue() == null ? "" : dpAdmission.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
        parameters.put("STUD_SHS_GRAD_DATE", (dpGraduation.getValue() == null ? "" : dpGraduation.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
        
        //--- gpa
//        parameters.put("STUD_GPA_1", (Double.parseDouble(txt1GPA.getText()) > 0)? txt1GPA.getText() : "0.0" );
//        parameters.put("STUD_TOT_UNIT_1", (Double.parseDouble(txt1Unit.getText()) > 0)? txt1Unit.getText() : "0.0" );
//        parameters.put("STUD_GPA_2",  (Double.parseDouble(txt2GPA.getText()) > 0)? txt2GPA.getText() : "0.0" );
//        parameters.put("STUD_TOT_UNIT_2", (Double.parseDouble(txt2Unit.getText()) > 0)? txt2Unit.getText() : "0.0");
//        parameters.put("STUD_GPA_3", (Double.parseDouble(txt3GPA.getText()) > 0)? txt3GPA.getText() : "0.0" );
//        parameters.put("STUD_TOT_UNIT_3", (Double.parseDouble(txt3Unit.getText()) > 0)? txt3Unit.getText() : "0.0");
//        parameters.put("STUD_GPA_4",  (Double.parseDouble(txt4GPA.getText()) > 0)? txt4GPA.getText() : "0.0" );
//        parameters.put("STUD_TOT_UNIT_4", (Double.parseDouble(txt4Unit.getText()) > 0)? txt4Unit.getText() : "0.0" );
        
        parameters.put("STUD_GPA_1", txt1GPA.getText() );
        parameters.put("STUD_TOT_UNIT_1", txt1Unit.getText() );
        parameters.put("STUD_GPA_2",  txt2GPA.getText() );
        parameters.put("STUD_TOT_UNIT_2", txt2Unit.getText());
        parameters.put("STUD_GPA_3", txt3GPA.getText() );
        parameters.put("STUD_TOT_UNIT_3", txt3Unit.getText());
        parameters.put("STUD_GPA_4",  txt4GPA.getText() );
        parameters.put("STUD_TOT_UNIT_4", txt4Unit.getText() );
        
        double gpa_1 = (txt1GPA.getText().equals("N/A")) ? 0.00 : Double.parseDouble(txt1GPA.getText());
        double gpa_2 = (txt2GPA.getText().equals("N/A")) ? 0.00 : Double.parseDouble(txt2GPA.getText());
        double gpa_3 = (txt3GPA.getText().equals("N/A")) ? 0.00 : Double.parseDouble(txt3GPA.getText());
        double gpa_4 = (txt4GPA.getText().equals("N/A")) ? 0.00 : Double.parseDouble(txt4GPA.getText());

        
        double gen_ave = (gpa_1 + gpa_2 + gpa_3 + gpa_4)/4;
        String gpa_final = df.format(gen_ave);
        parameters.put("GEN_AVERAGE", gpa_final);
        
        parameters.put("GEN_DAY", txtGenDay.getText() );
        parameters.put("GEN_MONTH_YEAR", txtGenMonthYear.getText() );
        parameters.put("GEN_ADMISSION", txtGenAdmission.getText() );
        parameters.put("GEN_RECEIVER", txtGenReceiver.getText() );
        parameters.put("GEN_DATE_RECEIVED", txtGenDateReceived.getText() );
        parameters.put("GEN_STATEMENT", "This student is eligible in this "+ ((txtGenDay.getText().isEmpty())? "_______" : txtGenDay.getText()) +
                " of "+ ((txtGenMonthYear.getText().isEmpty())? "______________" : txtGenMonthYear.getText()) +
                " for admission to "+ ((txtGenAdmission.getText().isEmpty())? "_________" : txtGenAdmission.getText()) +
                " and has no money or responsibility in this school. Copy of this record sent to "+ ((txtGenReceiver.getText().isEmpty())? "_____________" : txtGenReceiver.getText()) +
                " on "+ ((txtGenDateReceived.getText().isEmpty())? "_____________" : txtGenDateReceived.getText()) +".");
        
        parameters.put("GEN_STRAND", (txtGenStrand.getText().isEmpty())? "" : txtGenStrand.getText());
        parameters.put("GEN_AWARD", (txtGenAward.getText().isEmpty())? "" : txtGenAward.getText());   
        parameters.put("GEN_PURPOSE", txtGenPurpose.getText() );
        
        JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());
//        JasperPrint jp = JasperFillManager.fillReport("report/reportTOR.jasper", parameters, new JREmptyDataSource());
        JasperViewer viewer = new JasperViewer(jp, false);
        viewer.setVisible(true);
        // </editor-fold>
    }
    
    @FXML
    public void actionPrintCardGrade(ActionEvent event) throws Exception {                
        // <editor-fold defaultstate="collapsed" desc="Code">
        if(this.cbPrintGradelevel.getSelectionModel().getSelectedItem().equalsIgnoreCase("11")){
            this.printCardGrade11();
        }else{
            this.printCardGrade12();
        }
        // </editor-fold>
    }
    
    @FXML
    public void actionPrintCoreValues(ActionEvent event) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Code">
        if(this.cbPrintGradelevel.getSelectionModel().getSelectedItem().equalsIgnoreCase("11")){
            this.printCoreValuesGrade11();
        }else{
            this.printCoreValuesGrade12();
        }
        // </editor-fold>
    }
    
    @FXML
    public void actionUpdateProfile(ActionEvent event) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Code">
        ShApplicant shapplicant = selectedShApplicant;
        ShTermReg shtermreg = currentShTermReg;
        Boolean success = false;

        try{
            shapplicant.setAppLname(txtProfLname.getText());
            shapplicant.setAppFname(txtProfFname.getText());
            shapplicant.setAppMiddlename(txtProfMname.getText());
            shapplicant.setAppSuffix(txtProfSuffix.getText());
            shapplicant.setAppLrn(txtProfLRN.getText());
            shapplicant.setAppJuniorhs(txtProfJHSchool.getText());
            shapplicant.setAppJuniorhsAddress(txtProfJHAddress.getText());
            shapplicant.setAppJhsSy(txtProfJHSY.getText());
            shapplicant.setAppJhsGpa(txtProfJHGPA.getText());
            
            LocalDate date = dpProf.getValue();
            Date _date = selectedShApplicant.getAppBdate();     
            int dateYear = date.getYear();
            int dateMonth = date.getMonthValue();
            int dateDate = date.getDayOfMonth();
            _date.setYear(dateYear - 1900);
            _date.setMonth(dateMonth - 1);
            _date.setDate(dateDate);
            shapplicant.setAppBdate(_date);
            
            LocalDate dateAdm = dpAdmission.getValue();
            Date _dateAdm = currentShTermReg.getEnrollDate();                 
            int admDateYear = dateAdm.getYear();
            int admDateMonth = dateAdm.getMonthValue();
            int admDateDate = dateAdm.getDayOfMonth();
            _dateAdm.setYear(admDateYear - 1900);
            _dateAdm.setMonth(admDateMonth - 1);
            _dateAdm.setDate(admDateDate);
            shtermreg.setEnrollDate(_dateAdm);
            
            if(cbProfGender.getSelectionModel().getSelectedItem().toString().equals("Male")){
                shapplicant.setAppSex("M");
            }
            if(cbProfGender.getSelectionModel().getSelectedItem().toString().equals("Female")){
                shapplicant.setAppSex("F");
            }
            //shapplicant.setAppSex(cbProfGender.getSelectionModel().getSelectedItem().toString());
            
            success = shTermRegModel.updateStudentProfile(shtermreg);
            success = applicantModel.updateStudentProfile(shapplicant);
            
        }catch(Exception ex){
            success = false;
            ex.printStackTrace();
        }
        
        
        
        
        if(success==true)
            this.showMessage(success, "Successful", "Profile Updated!", "Student profile is successfully updated!");
        else
            this.showMessage(success, "Error", "Error", "Error occured");
        
        
        //</editor-fold>
    }
}
