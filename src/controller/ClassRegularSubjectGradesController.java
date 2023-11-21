/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomClassSections;
import entity.ShClassInfo;
import entity.ShCurrDtl;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import model.ShCClassStudModel;
import model.ShCurrDtl_CurrSyModel;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class ClassRegularSubjectGradesController implements Initializable {

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
    private Button btnSave;
    @FXML
    private TableView<ObservableList<String[]>> tblStudentSubjectGrade;
    
    private ShCurrDtl_CurrSyModel shCurrDtl_CurrSyModel;
    private ShCClassStudModel shCClassStudModel;
    
    private ShClassInfo selected_shClassInfo;
   
    private ObservableList<ObservableList<String[]>> listStudentGrades;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shCurrDtl_CurrSyModel = new ShCurrDtl_CurrSyModel();
        shCClassStudModel = new ShCClassStudModel();
    }    
    
    public void setClassSection(ShClassInfo shClassInfo){
        selected_shClassInfo = shClassInfo;
        txtSY.setText(selected_shClassInfo.getClaSy());
        txtSem.setText(selected_shClassInfo.getClaSem().toString());
        txtGradelevel.setText(selected_shClassInfo.getClaYrLevel().toString());
        txtStrand.setText(selected_shClassInfo.getStrandcode());
        txtSection.setText(selected_shClassInfo.getClass_section());
        txtModerator.setText(selected_shClassInfo.getClass_teacher_name());
        loadStudentSubjectGradesInTable();
    }
    
    private void loadStudentSubjectGradesInTable() {
        ObservableList<ShCurrDtl> listCurriculumSubjects = shCurrDtl_CurrSyModel.getResCurriculumSubjects(selected_shClassInfo.getClaSy(), 
                selected_shClassInfo.getClaSem().toString(), selected_shClassInfo.getStrandcode(), selected_shClassInfo.getClaYrLevel().toString());
        
        listStudentGrades = FXCollections.observableArrayList();
        try {
            tblStudentSubjectGrade.setEditable(true);

            ObservableList<String> listColumn = FXCollections.observableArrayList();   
            
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */
            listColumn.add("#");
            listColumn.add("ID Number");
            listColumn.add("Student");
            
            String str_crs_codes = "";
            for(ShCurrDtl row_subject: listCurriculumSubjects){
                listColumn.add(row_subject.getShCourseList().getCrsCode());
                
                if(!str_crs_codes.equals(""))
                    str_crs_codes += ",";
                
                str_crs_codes += "'" + row_subject.getShCourseList().getCrsCode().trim() + "'";
            }
      
            listColumn.add("GPA");
            
           
            for (int i = 0; i < listColumn.size(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(listColumn.get(i));

                
                TableColumn<ObservableList<String[]>, String> colGrade1 = new TableColumn<>(" ");    

                TableColumn<ObservableList<String[]>, String> colGrade2 = new TableColumn<>();    
                colGrade2.setText(" ");
                
                TableColumn<ObservableList<String[]>, String> colFG = new TableColumn<>("FG");    
                
                //--- START make subcolumns editable ---//
                colGrade1.setCellFactory(TextFieldTableCell.<ObservableList<String[]>>forTableColumn());
                colGrade1.setOnEditCommit(e->{
                    ObservableList<String[]> row = e.getRowValue();
                      row.set(j, new String[]{row.get(j)[0],row.get(j)[1], e.getNewValue(),row.get(j)[3]});
                });
                
                colGrade2.setCellFactory(TextFieldTableCell.<ObservableList<String[]>>forTableColumn());
                colGrade2.setOnEditCommit(e->{
                    ObservableList<String[]> row = e.getRowValue();
                    row.set(j, new String[]{row.get(j)[0], row.get(j)[1], row.get(j)[2], e.getNewValue()});
                });
                
  
                //--- END make subcolumns editable ---//
                    
                if(i>2 && i < listColumn.size()-1){
                    col.getColumns().add(colGrade1);
                    col.getColumns().add(colGrade2);
                    col.getColumns().add(colFG);
                }
                
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String[]>, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList<String[]>, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j)[0]);
                    }
                });
                
                colGrade1.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String[]>, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList<String[]>, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j)[2]);
                    }
                });
                
                colGrade2.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String[]>, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList<String[]>, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j)[3]);
                    }
                });
                
                colFG.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String[]>, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList<String[]>, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j)[4]);
                    }
                });
 
                col.setText(listColumn.get(i));
                
                tblStudentSubjectGrade.getColumns().addAll(col); 
            
            }
            
            int ctr_no = 0;
//            
            ObservableList<String[]> list = this.shCClassStudModel.getResStudentSubjectGradesConcat(selected_shClassInfo.getClaSy(),
                    selected_shClassInfo.getClaSem().toString(), selected_shClassInfo.getClaYrLevel().toString(), 
                    selected_shClassInfo.getStrandcode(), selected_shClassInfo.getStrandgroup(), str_crs_codes);
            
            double grade1= 0;
            double grade2 = 0; //--- final term grade
            double final_grade = 0;
            double subj_grade = 0; //--- fg * unit
            double row_unit = 0;
            double tot_grade = 0;
            double tot_unit = 0;
            double gpa = 0;
            DecimalFormat f = new DecimalFormat("##.00");
            
            for(String[] row_student_grades: list){
                //redeclare to zero per student
                grade1= 0;
                grade2 = 0; //--- final term grade
                final_grade = 0;
                subj_grade = 0; //--- fg * unit
                row_unit = 0;
                tot_grade = 0;
                tot_unit = 0;
                gpa = 0;

                ObservableList<String[]> row = FXCollections.observableArrayList();
                
                ctr_no++;

                row.add(new String[]{String.valueOf(ctr_no), ""});
                row.add(new String[]{row_student_grades[0], ""});
                row.add(new String[]{row_student_grades[1], ""});

                String[] arr_subjects = row_student_grades[2].split(",");
                
                for (int i = 0; i < listCurriculumSubjects.size(); i++) {

                    String[] arr_subject_grades = arr_subjects[i].split("-");

                    grade1= Double.parseDouble(arr_subject_grades[2]);
                    grade2 = Double.parseDouble(arr_subject_grades[3]);
                    final_grade = (grade1 + grade2)/2;
                    final_grade = Math.round(final_grade);
                    
                    row_unit = Double.parseDouble(arr_subject_grades[1]);
                    
                    subj_grade = final_grade * row_unit; 
                    subj_grade = Double.parseDouble(f.format(subj_grade));
                    
                    tot_grade += subj_grade;
                    tot_unit += row_unit;
                    
                    row.add(new String[]{arr_subject_grades[0], arr_subject_grades[1], arr_subject_grades[2], arr_subject_grades[3], String.valueOf(final_grade) });
                }
                gpa = tot_grade/tot_unit;
                gpa = Double.parseDouble(f.format(gpa));
                
                row.add(new String[]{String.valueOf(gpa), ""});
                
                listStudentGrades.add(row);
            }

            tblStudentSubjectGrade.setItems(listStudentGrades);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    
    

    @FXML
    private void actionSave(ActionEvent event) {
    }
    
}
