/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CoreValues;
import entity.CustomClassSections;
import entity.CustomEnrolledStudent;
import entity.ShCCorevalues;
import entity.ShClassInfo;
import interfaces.ClassCoreValuesInterface;
import interfaces.ShApplicantInterface;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import model.CoreValuesModel;
import model.CustomEnrolledStudentModel;
import model.ShCCorevaluesModel;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class ClassCoreValuesController implements Initializable, ClassCoreValuesInterface {

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
    private TableView<ObservableList<String[]>> tblStudentCoreValue;
//    @FXML
//    private TableColumn<Object, String> colNo;
//    @FXML
//    private TableColumn<Object, String> colSY;
//    @FXML
//    private TableColumn<Object, String> colSem;
//    @FXML
//    private TableColumn<Object, String> colGradelevel;
//    @FXML
//    private TableColumn<Object, String> colStrand;
//    @FXML
//    private TableColumn<Object, String> colSection;
//    @FXML
//    private TableColumn<Object, String> colSubjectCode;
//    @FXML
//    private TableColumn<Object, String> colTeacher;
    
    private CoreValuesModel coreValuesModel;
    private CustomEnrolledStudentModel customEnrolledStudentModel;
    private ShCCorevaluesModel shCCorevaluesModel;
    
    private ShClassInfo selected_shClassInfo;
    
    
    //TABLE VIEW AND DATA
    private ObservableList<ObservableList<String[]>> listStudentCoreValues;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        coreValuesModel = new CoreValuesModel();
        customEnrolledStudentModel = new CustomEnrolledStudentModel();
        shCCorevaluesModel= new ShCCorevaluesModel();
    }  
    
    public void setClassSection(ShClassInfo shClassInfo){
        selected_shClassInfo = shClassInfo;
        txtSY.setText(selected_shClassInfo.getClaSy());
        txtSem.setText(selected_shClassInfo.getClaSem().toString());
        txtGradelevel.setText(selected_shClassInfo.getClaYrLevel().toString());
        txtStrand.setText(selected_shClassInfo.getStrandcode());
        txtSection.setText(selected_shClassInfo.getClass_section());
        txtModerator.setText(selected_shClassInfo.getClass_teacher_name());
        loadCoreValuesInTable();
    }
    
    
    
    private void loadCoreValuesInTable() {
        if (!listCoreValues.isEmpty()) {
            listCoreValues.clear();
        }
        
        if (!listEnrolledStudent.isEmpty()) {
            listEnrolledStudent.clear();
        }
        
        
        listCoreValues.addAll(coreValuesModel.getResCoreValues());
        listEnrolledStudent.addAll(customEnrolledStudentModel.getResEnrolledStudent(selected_shClassInfo.getClaSy(), selected_shClassInfo.getClaSem().toString(), 
                selected_shClassInfo.getClaYrLevel().toString(), selected_shClassInfo.getStrandcode(), selected_shClassInfo.getStrandgroup()));
      
        
        listStudentCoreValues = FXCollections.observableArrayList();
        try {
            tblStudentCoreValue.setEditable(true);
//            tblStudentCoreValue.getSelectionModel().cellSelectionEnabledProperty().set(true);

            ObservableList<String> listColumn = FXCollections.observableArrayList();   
            
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */
            listColumn.add("#");
            listColumn.add("ID Number");
            listColumn.add("Student");
            
            for(CoreValues row_list: listCoreValues){
                listColumn.add(row_list.getDescription());
            }
            
           
            for (int i = 0; i < listColumn.size(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(listColumn.get(i));

                
                TableColumn<ObservableList<String[]>, String> colGrade1 = new TableColumn<>("Grade1");    

                TableColumn<ObservableList<String[]>, String> colGrade2 = new TableColumn<>();    
                colGrade2.setText("Grade2");
                
                //--- START make subcolumns editable ---//
                colGrade1.setCellFactory(TextFieldTableCell.<ObservableList<String[]>>forTableColumn());
                colGrade1.setOnEditCommit(e->{
                    ObservableList<String[]> row = e.getRowValue();
                      row.set(j, new String[]{row.get(j)[0], e.getNewValue(),row.get(j)[2]});
                });
                
                colGrade2.setCellFactory(TextFieldTableCell.<ObservableList<String[]>>forTableColumn());
                colGrade2.setOnEditCommit(e->{
                    ObservableList<String[]> row = e.getRowValue();
                    row.set(j, new String[]{row.get(j)[0], row.get(j)[1], e.getNewValue()});
                });
                //--- END make subcolumns editable ---//
                    
                if(i>2){
                    col.getColumns().add(colGrade1);
                    col.getColumns().add(colGrade2);
                }
                
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String[]>, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList<String[]>, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j)[0]);
                    }
                });
                
                colGrade1.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String[]>, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList<String[]>, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j)[1]);
                    }
                });
                
                colGrade2.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String[]>, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList<String[]>, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j)[2]);
                    }
                });
 
                col.setText(listColumn.get(i));
                
                tblStudentCoreValue.getColumns().addAll(col); 
            
            }
            
            int ctr_no = 0;
            
            ObservableList<String[]> list = this.shCCorevaluesModel.getResStudentCoreValuesConcat(selected_shClassInfo.getClaSy(),
                    selected_shClassInfo.getClaYrLevel().toString(), selected_shClassInfo.getStrandcode(), selected_shClassInfo.getStrandgroup());
            
            for(String[] row_student_corevalues: list){
                //Iterate Row
               
                ObservableList<String[]> row = FXCollections.observableArrayList();
                
                ctr_no++;

                row.add(new String[]{String.valueOf(ctr_no), ""});
                row.add(new String[]{row_student_corevalues[0], ""});
                row.add(new String[]{row_student_corevalues[1], ""});

                String[] arr_corevalues_hdr = row_student_corevalues[2].split(",");
                
                for (int i = 0; i < listCoreValues.size(); i++) {
//                    Iterate Column         
//                    ShCCorevalues shCCorevalues = shCCorevaluesModel.getRowStudentCoreValues(row_customEnrolledStudent.getSy(), row_customEnrolledStudent.getSem(), 
//                            row_customEnrolledStudent.getStud_idnum(), listCoreValues.get(i).getCvId().toString());
//                 
//                    row.add(new String[]{shCCorevalues.getCoreValues().getCvId().toString(),shCCorevalues.getGrade1(),shCCorevalues.getGrade2()});
                      String[] arr_corevalues = arr_corevalues_hdr[i].split("-");
                      row.add(new String[]{arr_corevalues[0], arr_corevalues[1], arr_corevalues[2]});
                }
                listStudentCoreValues.add(row);
            }

            tblStudentCoreValue.setItems(listStudentCoreValues);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    
    @FXML
    public void actionSave(ActionEvent event) throws Exception {
        ObservableList<ShCCorevalues> listShCCorevalues = FXCollections.observableArrayList();   
        
        int ctr=0;
        for(CustomEnrolledStudent row_student: listEnrolledStudent){
            
            for(int i=3; i<listCoreValues.size()+3; i++){
                ShCCorevalues shCCorevalues = new ShCCorevalues();
                
                shCCorevalues.setCvSy(selected_shClassInfo.getClaSy());
                shCCorevalues.setCvSem(selected_shClassInfo.getClaSem().toString());
                shCCorevalues.setCvYrlevel(Integer.parseInt(selected_shClassInfo.getClaYrLevel().toString()));
                shCCorevalues.setCvStrand(selected_shClassInfo.getStrandcode());
                shCCorevalues.setCvStrandgroup(selected_shClassInfo.getStrandgroup());
                shCCorevalues.setCvIdnum(listStudentCoreValues.get(ctr).get(1)[0]);
                shCCorevalues.setCoreValues(coreValuesModel.getRowCoreValues(Integer.parseInt(listStudentCoreValues.get(ctr).get(i)[0])));
                shCCorevalues.setGrade1(listStudentCoreValues.get(ctr).get(i)[1]);
                shCCorevalues.setGrade2(listStudentCoreValues.get(ctr).get(i)[2]);
                
                listShCCorevalues.add(shCCorevalues);
            }
            ctr++;
            
        }
        
        shCCorevaluesModel.saveCoreValuesBatch(selected_shClassInfo.getClaSy(),selected_shClassInfo.getClaSem().toString(),
                selected_shClassInfo.getClaYrLevel().toString(),selected_shClassInfo.getStrandcode(),
                selected_shClassInfo.getStrandgroup(),listShCCorevalues);
        
        loadCoreValuesInTable();
//        System.out.print(listStudentCoreValues.get(0).get(1)[0] +" " + listStudentCoreValues.get(0).get(2)[0] +" " + listStudentCoreValues.get(0).get(4)[0] +" " + listStudentCoreValues.get(0).get(4)[1]);
    }
    
}
