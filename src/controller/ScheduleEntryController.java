/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomClassSections;
import entity.ShClassInfo;
import entity.ShCurrDtl;
import entity.ShCurrHdr;
import entity.ShDays;
import entity.ShTermReg;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import model.ShStudStrand_ShStudlistModel;
import model.ShDaysModel;
import model.ShTermRegModel;
import model.ShClassInfoModel;
import model.ShSectionsModel;
import model.StrandsModel;
import model.CustomEnrolledStudentModel;
import model.ShTimesModel;
import interfaces.ShClassInfoInterface;
import interfaces.ShCClassStudInterface;
import javafx.scene.image.Image;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class ScheduleEntryController implements Initializable,ShClassInfoInterface {

    private ComboBox cbGradelevel;
    @FXML
    public TableView<ShClassInfo> tblSchedule;
    
    
    public static TableView<ShClassInfo> tblSchedule_for_update;
    
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
    private Button btnSelectSubject;
    
    @FXML
    private TableColumn<ShClassInfo, String> colNo;
    @FXML
    private TableColumn<ShClassInfo, String> colID;
    @FXML
    private TableColumn<ShClassInfo, String> colCurrDtlID;
    @FXML
    private TableColumn<ShClassInfo, String> colSY;
    @FXML
    private TableColumn<ShClassInfo, String> colSem;
    @FXML
    private TableColumn<ShClassInfo, String> colGradelevel;
    @FXML
    private TableColumn<ShClassInfo, String> colSubjCode;
    @FXML
    private TableColumn<ShClassInfo, String> colSubjSection;
    @FXML
    private TableColumn<ShClassInfo, String> colStrand;
    @FXML
    private TableColumn<ShClassInfo, String> colStrandgroup;
    @FXML
    private TableColumn<ShClassInfo, String> colDay;
    @FXML
    private TableColumn<ShClassInfo, String> colTimeStart;
    @FXML
    private TableColumn<ShClassInfo, String> colTimeEnd;
    @FXML
    private TableColumn<ShClassInfo, String> colRoom;
    @FXML
    private TableColumn<ShClassInfo, String> colAllowed;

    @FXML
    private Button btnSave;
    
    private ShClassInfoModel scheduleListModel;
    private ShClassInfoModel scheduleEntryModel;
    private StrandsModel strandsModel;
    private CustomEnrolledStudentModel studentListModel;
    private ShSectionsModel strandgroupModel;
    private ShTermRegModel flagsModel;
    private ShDaysModel daysModel;
    private ShTimesModel timeModel;
    
    private ShTermReg currentEnrollment;
    private ObservableList<String> listDays; 
    private ObservableList<String> listTimes; 
    private CustomClassSections selected_customClassSections;
    private ShClassInfo selected_shClassInfo;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tblSchedule_for_update = tblSchedule;
        scheduleListModel = new ShClassInfoModel();
        strandsModel = new StrandsModel();
        studentListModel = new CustomEnrolledStudentModel();
        strandgroupModel =  new ShSectionsModel();
        flagsModel =  new ShTermRegModel();
        scheduleEntryModel = new ShClassInfoModel();
        
        currentEnrollment=flagsModel.getRowCurrentEnrollment();
        
        daysModel = new ShDaysModel();
        listDays = FXCollections.observableArrayList(daysModel.getDaysForCombobox());
        
        timeModel = new ShTimesModel();
        listTimes = FXCollections.observableArrayList(timeModel.getTimesForCombobox());
       
        
        
//        loadSYinCbox();
//        loadSemInCbox();
//        loadGradelevelInCbox();
//        loadStrandsInCbox();
//        loadStrandgroupsInCbox();

    }    
    
    
    public void setClassSection(ShClassInfo shClassInfo){
        selected_shClassInfo = shClassInfo;
//        txtSY.setText(selected_customClassSections.getSy());
//        txtSem.setText(selected_customClassSections.getSem());
//        txtGradelevel.setText(selected_customClassSections.getGrade_level());
//        txtStrand.setText(selected_customClassSections.getStrand_code());
//        txtSection.setText(selected_customClassSections.getClass_section());

        listScheduleSelectSubject.clear();
        listScheduleSelectSubject.addAll(scheduleEntryModel.getRowScheduleByID(selected_shClassInfo.getClassid()));
        loadSchedulesInTable();
        btnSelectSubject.setVisible(false);
    }
    
    
//    private void loadSYinCbox() {
//        ObservableList<String> listSYforCbox = FXCollections.observableArrayList();
//        
//        String sy_val = "";
//        for(int i=2016; i<=2050; i++){
//            sy_val = i + "-" + (i+1);
//            listSYforCbox.add(sy_val);
//        }
//        
//        cbSY.setItems(listSYforCbox);
//        cbSY.getSelectionModel().select(currentEnrollment.getSyReg());
//
//    }
    
//    private void loadSemInCbox() {
//        ObservableList<String> listSem = FXCollections.observableArrayList();
//        listSem.add("1");
//        listSem.add("2");
//        listSem.add("3");
//        cbSem.setItems(listSem);
//        cbSem.getSelectionModel().select(currentEnrollment.getSemReg());
//    }
//    
//    private void loadGradelevelInCbox() {
//        ObservableList<String> listGradelevel = FXCollections.observableArrayList();
//        listGradelevel.add("all");
//        listGradelevel.add("11");
//        listGradelevel.add("12");
//        cbGradelevel.setItems(listGradelevel);
//        cbGradelevel.getSelectionModel().select("all");
//    }
//    
//    private void loadStrandsInCbox(){
//        ObservableList<String> listStrands = FXCollections.observableArrayList(strandsModel.getStrandsForCombobox());
//        listStrands.add("all");
//        cbStrand.setItems(listStrands);
//        cbStrand.getSelectionModel().select("all");
//    }
//    
//     private void loadStrandgroupsInCbox(){
//        ObservableList<String> listStrandgroups = FXCollections.observableArrayList(strandgroupModel.getStrandgroupsForCombobox());
//        cbStrandgroup.setItems(listStrandgroups);
//        cbStrandgroup.getSelectionModel().select("A");
//    }


    
    private void setEditableColumn() {
        tblSchedule.setEditable(true);
        tblSchedule.getSelectionModel().cellSelectionEnabledProperty().set(true);

        colSubjSection.setCellFactory(TextFieldTableCell.forTableColumn());
        colSubjSection.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setClaSection(e.getNewValue());
        });
        
        
        colStrandgroup.setCellFactory(TextFieldTableCell.forTableColumn());
        colStrandgroup.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setStrandgroup(e.getNewValue());
        });
        
        colDay.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), this.listDays));
        colDay.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setClaDay(e.getNewValue());
        });
        
        colTimeStart.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), listTimes));
        colTimeStart.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setClaTStartdesc(e.getNewValue());
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setClaStartcode(timeModel.getTimecodeByTimedesc(e.getNewValue()));
        });
        
        colTimeEnd.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), listTimes));
        colTimeEnd.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setClaTEnddesc(e.getNewValue());
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setClaEndcode(timeModel.getTimecodeByTimedesc(e.getNewValue()));
        });
        
        colRoom.setCellFactory(TextFieldTableCell.forTableColumn());
        colRoom.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setClaRoom(e.getNewValue());
        });
        

        colAllowed.setCellFactory(TextFieldTableCell.forTableColumn());
        colAllowed.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setClaStudAllowed(Integer.parseInt(e.getNewValue()));
        });
        
        tblSchedule.refresh();
    }
    
    
    
    public void loadSchedulesInTable() {
//        if (!listScheduleSelectSubject.isEmpty()) {
//            listScheduleSelectSubject.clear();
//        }
//        listSchedule.addAll(scheduleListModel.getResSchedule(cbSY.getSelectionModel().getSelectedItem().toString(), cbSem.getSelectionModel().getSelectedItem().toString(), 
//                cbGradelevel.getSelectionModel().getSelectedItem().toString(), cbStrand.getSelectionModel().getSelectedItem().toString(), 
//                cbSection.getSelectionModel().getSelectedItem().toString()));
       
//        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShClassInfo, String>,ObservableValue<String>>(){
//           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShClassInfo, String> p) {
//               return new ReadOnlyObjectWrapper(tblSchedule.getItems().indexOf(p.getValue()) + 1);
//           }  
//        });
//        
//        colNo.setSortable(false);
//        
//        colID.setCellValueFactory(new PropertyValueFactory<>("classid"));
//        colSY.setCellValueFactory(new PropertyValueFactory<>("claSy"));
//        colSem.setCellValueFactory(new PropertyValueFactory<>("claSem"));
//        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("claYrLevel"));
//        colSubjCode.setCellValueFactory(new PropertyValueFactory<>("claCrsCode"));
//        colSubjCode.setCellValueFactory(new PropertyValueFactory<>("claCrsCode"));
//        colSubjSection.setCellValueFactory(new PropertyValueFactory<>("claSection"));
//        colStrand.setCellValueFactory(new PropertyValueFactory<>("strandcode"));
//        colStrandgroup.setCellValueFactory(new PropertyValueFactory<>("strandgroup"));
//        colDay.setCellValueFactory(new PropertyValueFactory<>("claDay"));
//        colTimeStart.setCellValueFactory(new PropertyValueFactory<>("claTStart"));
//        colTimeEnd.setCellValueFactory(new PropertyValueFactory<>("claTEnd"));
//        colRoom.setCellValueFactory(new PropertyValueFactory<>("claRoom"));
//        colAllowed.setCellValueFactory(new PropertyValueFactory<>("claStudAllowed"));


        

//        System.out.println("Val1 " + listScheduleSelectSubject.get(0).getfCurrDtl());
//        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShClassInfo, String>,ObservableValue<String>>(){
//           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShClassInfo, String> p) {
//               return new ReadOnlyObjectWrapper(tblSchedule.getItems().indexOf(p.getValue()) + 1);
//           }  
//        });
//        
//        colNo.setSortable(false);
         
        colID.setCellValueFactory(new PropertyValueFactory<>("classid"));
        colCurrDtlID.setCellValueFactory(new PropertyValueFactory<>("fCurrDtl"));
        colSY.setCellValueFactory(new PropertyValueFactory<>("claSy"));
        colSem.setCellValueFactory(new PropertyValueFactory<>("claSem"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("claYrLevel"));
        colSubjCode.setCellValueFactory(new PropertyValueFactory<>("claCrsCode"));
        colSubjSection.setCellValueFactory(new PropertyValueFactory<>("claSection"));
        colStrand.setCellValueFactory(new PropertyValueFactory<>("strandcode"));
        colStrandgroup.setCellValueFactory(new PropertyValueFactory<>("strandgroup"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("claDay"));
        colTimeStart.setCellValueFactory(new PropertyValueFactory<>("claTStartdesc"));
        colTimeEnd.setCellValueFactory(new PropertyValueFactory<>("claTEnddesc"));
        colRoom.setCellValueFactory(new PropertyValueFactory<>("claRoom"));
//        colAllowed.setCellValueFactory(new PropertyValueFactory<>("claStudAllowed"));
        
        
        
        tblSchedule.setItems(listScheduleSelectSubject); 
        setEditableColumn();
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
        

        if (cbGradelevel.getSelectionModel().getSelectedItem().toString() == "" ) {
            errorMessage += "Select gradelevel!\n";
        }
       


        if (errorMessage.length() == 0) {
            return true;
        } else {
            showMessage(false,"Invalid Fields","Please correct invalid fields",errorMessage);
            return false;
        }
    }
    
    @FXML
    public void actionSave(ActionEvent event) throws Exception {
//            int fCurrSy = listScheduleSelectSubject.get(0).getFCurrSy();
            String sy = listScheduleSelectSubject.get(0).getClaSy();
            String sem = listScheduleSelectSubject.get(0).getClaSem().toString();
            int tot_sched_for_sy = this.scheduleEntryModel.cntScheduleByCurrSubj(sy,sem);
            Boolean success = false;

            
            if(selected_shClassInfo != null){ //--- UPDATE schedule
                selected_shClassInfo.setClaSection(listScheduleSelectSubject.get(0).getClaSection());
                selected_shClassInfo.setStrandgroup(listScheduleSelectSubject.get(0).getStrandgroup());
                selected_shClassInfo.setClaDay(listScheduleSelectSubject.get(0).getClaDay());
                selected_shClassInfo.setClaStudAllowed(listScheduleSelectSubject.get(0).getClaStudAllowed());
                selected_shClassInfo.setClaTStartdesc(listScheduleSelectSubject.get(0).getClaTStartdesc());
                selected_shClassInfo.setClaStartcode(timeModel.getTimecodeByTimedesc(listScheduleSelectSubject.get(0).getClaTStartdesc()));
                selected_shClassInfo.setClaTEnddesc(listScheduleSelectSubject.get(0).getClaTEnddesc());
                selected_shClassInfo.setClaEndcode(timeModel.getTimecodeByTimedesc(listScheduleSelectSubject.get(0).getClaTEnddesc()));
                selected_shClassInfo.setClaRoom(listScheduleSelectSubject.get(0).getClaRoom());

                
                success = scheduleEntryModel.saveSchedule(selected_shClassInfo);
            }else{ //--- SAVE NEW schedules
                success = scheduleEntryModel.saveScheduleBatch(tot_sched_for_sy,sy,sem,listScheduleSelectSubject);
            }
            
                        
            
            ((Stage) btnSave.getScene().getWindow()).close();
            
            if(success==true)
                this.showMessage(success, "Successful", "Schedule Entry!", "Schedule is saved successfully");
            else
                this.showMessage(success, "Error", "Error", "Error occured");

           
//        if (validateInput()) {


//
//            Boolean success = 
//            
//            ((Stage) btnSave.getScene().getWindow()).close();
//            
//            if(success==true)
//                this.showMessage(success, "Successful", "Curriculum Header Created!", "Curriculum Header is created successfully");
//            else
//                this.showMessage(success, "Error", "Error", "Error occured");

//        }
        
    }
    
    @FXML
    public void actionSelectSubject(ActionEvent event) throws Exception { 
        Parent root = FXMLLoader.load(getClass().getResource("/view/ScheduleEntrySelectSubject.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Subject Selection");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        this.loadSchedulesInTable();

    }
    
}
