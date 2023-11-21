/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShApplicant;
import entity.ShCurrDtl;
import entity.ShCurrHdr;
import controller.ApplicantConfirmationEntryController;
import entity.AppResults;
import interfaces.ShApplicantInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.AppResultsModel;
import model.CustomApplicantAptitudeModel;
import model.ShApplicantModel;
import model.ShStudStrand_ShStudlistModel;
import model.ShCurrHdrModel;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class ApplicantListController implements Initializable,ShApplicantInterface {

    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch,btnConfirmStudent,btnPromoteStudent;
    @FXML
    private TableView<ShApplicant> tblApplicant;
    @FXML
    private TableColumn<ShApplicant, String> colNo;
    @FXML
    private TableColumn<ShApplicant, String> colAppNo;
    @FXML
    private TableColumn<ShApplicant, String> colStudID;
    @FXML
    private TableColumn<ShApplicant, String> colStudLname;
    @FXML
    private TableColumn<ShApplicant, String> colFname;
    @FXML
    private TableColumn<ShApplicant, String> colMname;
    
    public ShApplicantModel applicantModel;
    public AppResultsModel appResultsModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        applicantModel = new ShApplicantModel();
        appResultsModel = new AppResultsModel();
        loadApplicantsInTable(); 
    }    
    
    private void loadApplicantsInTable() {
    
        if (!listApplicant.isEmpty()) {
            listApplicant.clear();
        }
        listApplicant.addAll(applicantModel.getResApplicant());
       
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShApplicant, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShApplicant, String> p) {
               return new ReadOnlyObjectWrapper(tblApplicant.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        colAppNo.setCellValueFactory(new PropertyValueFactory<>("appNo"));
        colStudID.setCellValueFactory(new PropertyValueFactory<>("studIdnum"));
        colStudLname.setCellValueFactory(new PropertyValueFactory<>("appLname"));
        colFname.setCellValueFactory(new PropertyValueFactory<>("appFname"));
        colMname.setCellValueFactory(new PropertyValueFactory<>("appMiddlename"));

        tblApplicant.setItems(listApplicant); 
    }
    
    @FXML
    public void filterDataOnClick(ActionEvent event) throws Exception {
        if (!listApplicant.isEmpty()) {
            listApplicant.clear();  
        }
        listApplicant.addAll(applicantModel.searchResApplicant(this.txtSearch.getText()));
       
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShApplicant, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShApplicant, String> p) {
               return new ReadOnlyObjectWrapper(tblApplicant.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);
        
        colAppNo.setCellValueFactory(new PropertyValueFactory<>("appNo"));
        colStudID.setCellValueFactory(new PropertyValueFactory<>("studIdnum"));
        colStudLname.setCellValueFactory(new PropertyValueFactory<>("appLname"));
        colFname.setCellValueFactory(new PropertyValueFactory<>("appFname"));
        colMname.setCellValueFactory(new PropertyValueFactory<>("appMiddlename"));

        tblApplicant.setItems(listApplicant); 
   
//--- working
//        FilteredList<ShApplicant> searchedData = new FilteredList<>(listApplicant, p -> true);
//        
//        String newValue = txtSearch.getText();
//        searchedData.setPredicate(objName -> {
//                    if (newValue == null || newValue.isEmpty()) {
//                        return true;
//                    }
//                    String lowerCaseFilter = newValue.toLowerCase();
//                    if(objName.getStudIdnum().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    }else if(objName.getAppLname().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    }else if(objName.getAppFname().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    }else if(objName.getAppMiddlename().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    }   
//                    return false;
//                });
//     
//        SortedList<ShApplicant> sortedData = new SortedList<>(searchedData);
//        sortedData.comparatorProperty().bind(tblApplicant.comparatorProperty());
//        tblApplicant.setItems(sortedData); 
    }
    
    public void actionConfirmStudent(ActionEvent event) throws Exception {       
        AppResults selectedShApplicantResult = this.appResultsModel.getRowApplicantResult(tblApplicant.getSelectionModel().getSelectedItem());
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/ApplicantConfirmationEntry.fxml")));
        ApplicantConfirmationEntryController controller = new ApplicantConfirmationEntryController();
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Student Confirmation");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        controller.setApplicantResults(selectedShApplicantResult);
        tblApplicant.getSelectionModel().clearSelection();
        this.loadApplicantsInTable();
    }
    
    
    public void actionPromoteStudent(ActionEvent event) throws Exception {  
        AppResults selectedShApplicantResult = this.appResultsModel.getRowApplicantResult(tblApplicant.getSelectionModel().getSelectedItem());
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/ApplicantPromoteEntry.fxml")));
        ApplicantPromoteEntryController controller = new ApplicantPromoteEntryController();
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
        
        controller.setApplicantDetails(selectedShApplicantResult.getShApplicant());
        tblApplicant.getSelectionModel().clearSelection();
        this.loadApplicantsInTable();
    }
    
}
