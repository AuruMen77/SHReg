/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomClassSections;
import entity.Schoolmonth;
import entity.ShApplicant;
import entity.ShCAttendance;
import entity.ShClassInfo;
import interfaces.ClassAttendanceInterface;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.SchoolmonthModel;
import model.ShCAttendanceModel;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class ClassAttendanceController implements Initializable, ClassAttendanceInterface {

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
    private TableView<ShCAttendance> tblStudentAttendance;
    
    private ShCAttendanceModel shCAttendanceModel;
    private SchoolmonthModel schoolmonthModel;
    
    private ShClassInfo selected_shClassInfo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shCAttendanceModel = new ShCAttendanceModel();
        schoolmonthModel = new SchoolmonthModel();
    }   
    
    public void setClassSection(ShClassInfo shClassInfo){
        selected_shClassInfo = shClassInfo;
        txtSY.setText(selected_shClassInfo.getClaSy());
        txtSem.setText(selected_shClassInfo.getClaSem().toString());
        txtGradelevel.setText(selected_shClassInfo.getClaYrLevel().toString());
        txtStrand.setText(selected_shClassInfo.getStrandcode());
        txtSection.setText(selected_shClassInfo.getClass_section());
        txtModerator.setText(selected_shClassInfo.getClass_teacher_name());
        loadClassAttendanceInTable();
    }
    
    private void loadClassAttendanceInTable() {
        if (!listStudentAttendance.isEmpty()) {
            listStudentAttendance.clear();
        }
        
        if (!listMonth.isEmpty()) {
            listMonth.clear();
        }
        
        listMonth.addAll(schoolmonthModel.getResMonths());
        

        ObservableList<String> listColumn = FXCollections.observableArrayList();   
        
        listColumn.add("#");
        listColumn.add("ID Number");
        listColumn.add("Student");
            
        for(Schoolmonth row: listMonth){
            listColumn.add(row.getSchoolmonth());
        }
        
        for (int i = 0; i < listColumn.size(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
//                TableColumn col = new TableColumn(listColumn.get(i));

                
                TableColumn<ShCAttendance, String> col = new TableColumn<>(listColumn.get(i));    

                
                
                //--- START make subcolumns editable ---//
//                colGrade1.setCellFactory(TextFieldTableCell.<ObservableList<String[]>>forTableColumn());
//                colGrade1.setOnEditCommit(e->{
//                    ObservableList<String[]> row = e.getRowValue();
//                      row.set(j, new String[]{row.get(j)[0], e.getNewValue(),row.get(j)[2]});
//                });
                
               
                //--- END make subcolumns editable ---//
                if(i == 0){
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCAttendance, String>,ObservableValue<String>>(){
                        @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCAttendance, String> p) {
                            return new ReadOnlyObjectWrapper(tblStudentAttendance.getItems().indexOf(p.getValue()) + 1);
                        }  
                     });
                }else if(i == 1){
                    col.setCellValueFactory(new PropertyValueFactory<>("studIdnum"));
                }else if(i == 2){
                    col.setCellValueFactory(new PropertyValueFactory<>("stud_fullname"));
                }else if(i == 3){
                    col.setCellValueFactory(new PropertyValueFactory<>("month1"));
                }else if(i == 4){
                    col.setCellValueFactory(new PropertyValueFactory<>("month2"));
                }else if(i == 5){
                    col.setCellValueFactory(new PropertyValueFactory<>("month3"));
                }else if(i == 6){
                    col.setCellValueFactory(new PropertyValueFactory<>("month4"));
                }else if(i == 7){
                    col.setCellValueFactory(new PropertyValueFactory<>("month5"));
                }else if(i == 8){
                    col.setCellValueFactory(new PropertyValueFactory<>("month6"));
                }else if(i == 9){
                    col.setCellValueFactory(new PropertyValueFactory<>("month7"));
                }else if(i == 10){
                    col.setCellValueFactory(new PropertyValueFactory<>("month8"));
                }else if(i == 11){
                    col.setCellValueFactory(new PropertyValueFactory<>("month9"));
                }else if(i == 12){
                    col.setCellValueFactory(new PropertyValueFactory<>("month10"));
                }else if(i == 13){
                    col.setCellValueFactory(new PropertyValueFactory<>("month11"));
                }else if(i == 14){
                    col.setCellValueFactory(new PropertyValueFactory<>("month12"));
                }
                
                
                    

              
 
                col.setText(listColumn.get(i));
                
                tblStudentAttendance.getColumns().addAll(col); 
            
            }
        
        listStudentAttendance.addAll(shCAttendanceModel.getResStudentAttendance(selected_shClassInfo.getClaSy(), 
                selected_shClassInfo.getClaSem().toString(), selected_shClassInfo.getClaYrLevel().toString(), 
                selected_shClassInfo.getStrandcode(), selected_shClassInfo.getStrandgroup()));
        
        tblStudentAttendance.setItems(listStudentAttendance);
    }

    @FXML
    private void actionSave(ActionEvent event) {
    }
    
}
