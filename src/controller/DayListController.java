/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShDays;
import interfaces.ShDaysInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import model.ShDaysModel;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class DayListController implements Initializable, ShDaysInterface {

    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private TableView<ShDays> tblDay;
    @FXML
    private TableColumn<ShDays, String> colNo;
    @FXML
    private TableColumn<ShDays, String> colDay;
    
    private ShDaysModel shDaysModel;
    
    private ShDays selected_shDays;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shDaysModel = new ShDaysModel();
        loadDaysInTable();
    }  
    
    private void loadDaysInTable(){
        if(listDays.isEmpty()){
            listDays.clear();
        }
        
        listDays.addAll(shDaysModel.getResDays(txtSearch.getText()));
        
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShDays, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShDays, String> p) {
               return new ReadOnlyObjectWrapper(tblDay.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colDay.setCellValueFactory(new PropertyValueFactory<>("days"));
//        colDay.setCellValueFactory((TableColumn.CellDataFeatures<ShDays, String> p) -> 
//                new SimpleStringProperty(p.getValue().getId().getDays()));
        
        tblDay.setItems(listDays);
        
    }
    
    @FXML
    private void actionSearch(ActionEvent event){
        listDays.clear();
        loadDaysInTable();
    }
    
    @FXML
    public void actionAddDay(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/DayEntry.fxml")));
        DayEntryController controller = new DayEntryController();
        loader.setController(controller);

        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        listDays.clear();
        tblDay.getItems().clear();
        loadDaysInTable();
        tblDay.refresh();
    }
    
    @FXML
    public void actionEditDay(ActionEvent event) throws Exception {
 
        ShDays selectedShDays = tblDay.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/DayEntry.fxml")));
        DayEntryController controller = new DayEntryController();
        loader.setController(controller);
        
        
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        
        controller.setDay(selectedShDays);
        tblDay.refresh();
    }
    
}
