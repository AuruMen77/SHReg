/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShCurrDtl;
import entity.ShCurrHdr;
import java.sql.*;
import java.sql.Connection;
//import com.mysql.jdbc.Connection;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import model.ShCurrHdrModel;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class CurriculumListController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    private TableView<ShCurrHdr> tblCurriculum;
    @FXML
    private TableColumn<ShCurrHdr, String> colNo,colName;
    @FXML
    private TextField txtSearch;
 
    @FXML
    private ShCurrHdrModel shCurrHdrModel;
    
    public ObservableList<ShCurrHdr> listCurriculum = FXCollections.observableArrayList();   

    @Override
     public void initialize(URL location, ResourceBundle resources) {
        shCurrHdrModel = new ShCurrHdrModel();

        loadData();

        colNo.setCellValueFactory(new PropertyValueFactory<>("currHdrId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("currName"));

        tblCurriculum.setItems(listCurriculum);
    }
     
    private void loadData() {

        if (!listCurriculum.isEmpty()) {
            listCurriculum.clear();
        }
        listCurriculum.addAll(shCurrHdrModel.getResCurriculum());
    }
    
    @FXML
    public void filterDataOnClick(ActionEvent event) throws Exception {
        
        FilteredList<ShCurrHdr> searchedData = new FilteredList<>(listCurriculum, p -> true);
        
        String newValue = txtSearch.getText();
        searchedData.setPredicate(objName -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if(objName.getCurrName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } 
                    return false;
                });
     
        SortedList<ShCurrHdr> sortedData = new SortedList<>(searchedData);
        sortedData.comparatorProperty().bind(tblCurriculum.comparatorProperty());
        tblCurriculum.setItems(sortedData);
    }
    
    
    private void filterDataOnKeypress() {
        
        FilteredList<ShCurrHdr> searchedData = new FilteredList<>(listCurriculum, e -> true);
        txtSearch.setOnKeyReleased(e -> {
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(objName -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if(objName.getCurrName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } 
                    return false;
                });
            });

            SortedList<ShCurrHdr> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(tblCurriculum.comparatorProperty());
            tblCurriculum.setItems(sortedData);
        });
    }
    
    @FXML
    public void actionAddCurriculum(ActionEvent event) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/CurriculumHdrEntry.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Curriculum Header");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void actionAddCurriculumSY(ActionEvent event) throws Exception {
        ShCurrHdr selectedCurrHdr = tblCurriculum.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/CurriculumSY.fxml")));
        CurriculumSYController controller = new CurriculumSYController();
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Curriculum SY");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        controller.setCurriculumHdr(selectedCurrHdr);
        tblCurriculum.getSelectionModel().clearSelection();
    }
    
    public void actionAddCurriculumSubject(ActionEvent event) throws Exception {
        ShCurrHdr selectedCurrHdr = tblCurriculum.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/CurriculumSubjectEntry.fxml")));
        CurriculumSubjectEntryController controller = new CurriculumSubjectEntryController();
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Curriculum Subjects");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        controller.setCurriculumHdr(selectedCurrHdr);
        tblCurriculum.getSelectionModel().clearSelection();
    }
  
}
