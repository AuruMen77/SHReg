/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomClassSections;
import entity.ShClassInfo;
import entity.ShCurrHdr;
import entity.ShTermReg;
import java.net.URL;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.ShTermRegModel;
import model.ShSectionsModel;
import model.StrandsModel;
import model.CustomEnrolledStudentModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import interfaces.ShClassInfoInterface;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import model.ShClassInfoModel;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class ScheduleListController implements Initializable,ShClassInfoInterface {

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
    private Button btnSearch;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<ShClassInfo> tblSchedule;
    @FXML
    private TableColumn<ShClassInfo, String> colNo;
    @FXML
    private TableColumn<ShClassInfo, String> colID;
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

    private ShClassInfoModel scheduleListModel;
    private StrandsModel strandsModel;
    private CustomEnrolledStudentModel studentListModel;
    private ShSectionsModel strandgroupModel;
    private ShTermRegModel flagsModel;
    
    private ShTermReg currentEnrollment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        strandsModel = new StrandsModel();
        studentListModel = new CustomEnrolledStudentModel();
        strandgroupModel =  new ShSectionsModel();
        flagsModel =  new ShTermRegModel();
        scheduleListModel = new ShClassInfoModel();
        
        currentEnrollment=flagsModel.getRowCurrentEnrollment();

        loadSYinCbox();
        loadSemInCbox();
        loadGradelevelInCbox();
        loadStrandsInCbox();
        loadStrandgroupsInCbox();
        loadSchedulesInTable(); 
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
        listStrands.add("all");
        cbStrand.setItems(listStrands);
        cbStrand.getSelectionModel().select("all");
    }
    
     private void loadStrandgroupsInCbox(){
        ObservableList<String> listStrandgroups = FXCollections.observableArrayList(strandgroupModel.getStrandgroupsForCombobox());
        listStrandgroups.add("all");
        cbSection.setItems(listStrandgroups);
        cbSection.getSelectionModel().select("all");
    }
    
     private void setEditableColumn() {
        tblSchedule.setEditable(true);
        // allows the individual cells to be selected
//        tblSchedule.getSelectionModel().cellSelectionEnabledProperty().set(true);

        colID.setCellFactory(TextFieldTableCell.forTableColumn());
        colID.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setClassid(e.getNewValue());
        });
    }
    private void loadSchedulesInTable() {
        if (!listSchedule.isEmpty()) {
            listSchedule.clear();
        }
        listSchedule.addAll(scheduleListModel.getResSchedule(cbSY.getSelectionModel().getSelectedItem().toString(), cbSem.getSelectionModel().getSelectedItem().toString(), 
                cbGradelevel.getSelectionModel().getSelectedItem().toString(), cbStrand.getSelectionModel().getSelectedItem().toString(), 
                cbSection.getSelectionModel().getSelectedItem().toString()));
       
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShClassInfo, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShClassInfo, String> p) {
               return new ReadOnlyObjectWrapper(tblSchedule.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        colID.setCellValueFactory(new PropertyValueFactory<>("classid"));
        colSY.setCellValueFactory(new PropertyValueFactory<>("claSy"));
        colSem.setCellValueFactory(new PropertyValueFactory<>("claSem"));
        colGradelevel.setCellValueFactory(new PropertyValueFactory<>("claYrLevel"));
        colSubjCode.setCellValueFactory(new PropertyValueFactory<>("claCrsCode"));
        colSubjCode.setCellValueFactory(new PropertyValueFactory<>("claCrsCode"));
        colSubjSection.setCellValueFactory(new PropertyValueFactory<>("claSection"));
        colStrand.setCellValueFactory(new PropertyValueFactory<>("strandcode"));
        colStrandgroup.setCellValueFactory(new PropertyValueFactory<>("strandgroup"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("claDay"));
        colTimeStart.setCellValueFactory(new PropertyValueFactory<>("claTStartdesc"));
        colTimeEnd.setCellValueFactory(new PropertyValueFactory<>("claTEnddesc"));
        colRoom.setCellValueFactory(new PropertyValueFactory<>("claRoom"));
        colAllowed.setCellValueFactory(new PropertyValueFactory<>("claStudAllowed"));

        tblSchedule.setItems(listSchedule); 
        setEditableColumn();
    }
    
    @FXML
    public void actionSearchSchedule(ActionEvent event) throws Exception {  
        loadSchedulesInTable();
    }
    
    
    @FXML
    public void actionAddSchedule(ActionEvent event) throws Exception {
//        CustomClassSections selectedCustomClassSections = 
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/ScheduleEntry.fxml")));
        ScheduleEntryController controller = new ScheduleEntryController();
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Schedule Entry");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        listScheduleSelectSubject.clear();
        controller.loadSchedulesInTable();
        
        this.tblSchedule.getSelectionModel().clearSelection();
        loadSchedulesInTable();
    }
    
    @FXML
    public void actionEditSchedule(ActionEvent event) throws Exception {
        ShClassInfo selectedShClassInfo = scheduleListModel.getRowScheduleByID(tblSchedule.getSelectionModel().getSelectedItem().getClassid());
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/ScheduleEntry.fxml")));
        ScheduleEntryController controller = new ScheduleEntryController();
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Student Promotion");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        int index = tblSchedule.getSelectionModel().getSelectedIndex();
        controller.setClassSection(selectedShClassInfo);
        
        this.tblSchedule.getSelectionModel().clearSelection();
        loadSchedulesInTable();
    }
    
    
    @FXML
    public void actionDeleteSchedule(ActionEvent event){
        ShClassInfo selectedShClassInfo = scheduleListModel.getRowScheduleByID(tblSchedule.getSelectionModel().getSelectedItem().getClassid());
         
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Boolean success = this.scheduleListModel.deleteSchedule(selectedShClassInfo);
        }
    }
    
    
    
}
