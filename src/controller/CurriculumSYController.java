/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShCurrHdr;
import entity.ShCurrSy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ShCurrHdrModel;
import model.ShCurrSyModel;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class CurriculumSYController implements Initializable {

    @FXML
    private TextField txtCurriculumID;
    @FXML
    private ComboBox<String> cbSY;
    @FXML
    private Button btnSave;
    @FXML
    private TableView<ShCurrSy> tblSY;
    @FXML
    private TableColumn<ShCurrSy, String> colNo,colSY;
    @FXML
    private Button btnRemove;
    
    public ObservableList<ShCurrSy> listSY = FXCollections.observableArrayList();
    private ShCurrHdrModel shCurrHdrModel;
    private ShCurrSyModel shCurrSyModel;
    
    private ShCurrHdr shCurrHdr;
    public ObservableList<ShCurrSy> listShCurrSy ;
    //List<ShCurrSy> listShCurrSy = new ArrayList<ShCurrSy>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shCurrSyModel =  new ShCurrSyModel();
        loadSYinCbox();
    }   
    
    public void setCurriculumHdr(ShCurrHdr shCurrHdr) {
        this.shCurrHdr = shCurrHdr;
        
        setCurriculumHdrDesc();
        loadSYinTable(); 
    }
    
    public void setCurriculumHdrDesc() {
        this.txtCurriculumID.setText(shCurrHdr.getCurrName());   
    }
    
    public void saveItemsFromTableToList(){
        listShCurrSy = FXCollections.observableArrayList();
        for(ShCurrSy item : tblSY.getItems()){
            ShCurrSy shCurrSy = new ShCurrSy();
            shCurrSy.setShCurrHdr(shCurrHdr);
            shCurrSy.setSy(colSY.getCellData(item));
            
            listShCurrSy.add(shCurrSy);
        }
    }
    
    private void loadSYinCbox() {
        ObservableList<String> listSYforCbox = FXCollections.observableArrayList();
        
        String sy_val = "";
        for(int i=2016; i<=2050; i++){
            sy_val = i + "-" + (i+1);
            listSYforCbox.add(sy_val);
        }
        
        cbSY.setItems(listSYforCbox);
    }
        
    private void loadSYinTable() {

        if (!listSY.isEmpty()) {
            listSY.clear();
        }
        listSY.addAll(shCurrSyModel.getResCurriculumSY(shCurrHdr));
       

        colSY.setCellValueFactory(new PropertyValueFactory<>("sy"));
        
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCurrSy, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCurrSy, String> p) {
               return new ReadOnlyObjectWrapper(tblSY.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colNo.setSortable(false);

        tblSY.setItems(listSY); 
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
    
    public void actionAddSY(ActionEvent event) throws Exception {
        saveItemsFromTableToList();
        
        ShCurrSy shCurrSy = new ShCurrSy();
        shCurrSy.setShCurrHdr(shCurrHdr);
        shCurrSy.setSy(cbSY.getSelectionModel().getSelectedItem());

        listShCurrSy.add(shCurrSy);
            
        tblSY.getItems().clear();
        tblSY.setItems(listShCurrSy);
    }
    
    public void actionRemoveSY(ActionEvent event) throws Exception {
        this.tblSY.getItems().remove(tblSY.getSelectionModel().getSelectedItem()); 
    }
    
    public void actionSave(ActionEvent event) throws Exception {
        this.saveItemsFromTableToList();
        
        Boolean success = shCurrSyModel.saveCurriculumSYFromTable(listShCurrSy,shCurrHdr);

//        ((Stage) btnSave.getScene().getWindow()).close();

        if(success==true)
            this.showMessage(success, "Successful", "Curriculum SY Created!", "Curriculum SY is created successfully");
        else
            this.showMessage(success, "Error", "Error", "Error occured");

    }
}
