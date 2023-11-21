/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShInstructor;
import interfaces.ShInstructorInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import model.ShInstructorModel;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class InstructorListController implements Initializable, ShInstructorInterface {

    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<ShInstructor> tblTeacher;
    @FXML
    private TableColumn<ShInstructor, String> colNo;
    @FXML
    private TableColumn<ShInstructor, String> colTeacherID;
    @FXML
    private TableColumn<ShInstructor, String> colTeacherUsername;
    @FXML
    private TableColumn<ShInstructor, String> colTeacherFullname;
    
    private ShInstructorModel shInstructorModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shInstructorModel = new ShInstructorModel();
        loadInstructors();
    }    
    
    private void loadInstructors() {

        if (!listInstructors.isEmpty()) {
            listInstructors.clear();
        }
        listInstructors.addAll(shInstructorModel.getResInstructors(txtSearch.getText()));
        
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShInstructor, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShInstructor, String> p) {
               return new ReadOnlyObjectWrapper(tblTeacher.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colTeacherID.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colTeacherUsername.setCellValueFactory(new PropertyValueFactory<>("instructorUname"));
        colTeacherFullname.setCellValueFactory(new PropertyValueFactory<>("instructorName"));

        
        tblTeacher.setItems(listInstructors);
    }
    
    @FXML
    private void actionSearch(ActionEvent event){
        listInstructors.clear();
        loadInstructors();
    }
    
    
    @FXML
    public void actionAddInstructor(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/InstructorEntry.fxml")));
        InstructorEntryController controller = new InstructorEntryController();
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

        listInstructors.clear();
        loadInstructors();
        tblTeacher.refresh();
    }
    
    @FXML
    public void actionEditInstructor(ActionEvent event) throws Exception {
 
        ShInstructor selectedShInstructor = tblTeacher.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/InstructorEntry.fxml")));
        InstructorEntryController controller = new InstructorEntryController();
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
        
        
        controller.setInstructor(selectedShInstructor);
        tblTeacher.refresh();
    }
    
    
}
