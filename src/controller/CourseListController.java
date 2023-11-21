/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ShCourseList;
import interfaces.ShCourseListInterface;
import java.math.BigDecimal;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ShCourseListModel;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class CourseListController implements Initializable, ShCourseListInterface {

    @FXML
    private ComboBox cbCourseType;
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
    private TableView<ShCourseList> tblSubject;
    @FXML
    private TableColumn<ShCourseList, String> colNo;
    @FXML
    private TableColumn<ShCourseList, String> colID;
    @FXML
    private TableColumn<ShCourseList, String> colSubjCode;
    @FXML
    private TableColumn<ShCourseList, String> colSubjDesc;
    @FXML
    private TableColumn<ShCourseList, String> colSubjType;
    @FXML
    private TableColumn<ShCourseList, BigDecimal> colSubjUnit;
    
    private ShCourseListModel shCourseListModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shCourseListModel = new ShCourseListModel();
        loadCourseTypeInCbox();
        loadCourses();
    }    
    
    private void loadCourseTypeInCbox() {
        ObservableList<String> listCourse = FXCollections.observableArrayList();
        listCourse.add("Core");
        listCourse.add("Specialized");
        listCourse.add("Applied");
        listCourse.add("Others");
        cbCourseType.setItems(listCourse);
        cbCourseType.getSelectionModel().select(0);
    }
    
    public void loadCourses() {
        
        if (!listCourse.isEmpty()) {
            listCourse.clear();
        }
        listCourse.addAll(shCourseListModel.getResSubjectList(cbCourseType.getSelectionModel().getSelectedItem().toString(),txtSearch.getText()));
        
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShCourseList, String>,ObservableValue<String>>(){
           @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<ShCourseList, String> p) {
               return new ReadOnlyObjectWrapper(tblSubject.getItems().indexOf(p.getValue()) + 1);
           }  
        });
        
        colID.setCellValueFactory(new PropertyValueFactory<>("crsId"));
        colSubjCode.setCellValueFactory(new PropertyValueFactory<>("crsCode"));
        colSubjDesc.setCellValueFactory(new PropertyValueFactory<>("crsTitle"));
        colSubjType.setCellValueFactory(new PropertyValueFactory<>("crsType"));
        colSubjUnit.setCellValueFactory(new PropertyValueFactory<>("crsUnit"));

        
        tblSubject.setItems(listCourse);
    }
    
    @FXML
    private void actionSearch(ActionEvent event){
        listCourse.clear();
        loadCourses();
    }
    
    @FXML
    public void actionAddCourse(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/CourseEntry.fxml")));
        CourseEntryController controller = new CourseEntryController();
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

//        listCourse.clear();
//        tblSubject.getItems().clear();
//        loadCourses();
//        tblSubject.refresh();
    }
    
    @FXML
    public void actionEditCourse(ActionEvent event) throws Exception {
 
        ShCourseList selectedShCourse = tblSubject.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/CourseEntry.fxml")));
        CourseEntryController controller = new CourseEntryController();
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
        
        
        controller.setCourse(selectedShCourse);
        tblSubject.refresh();
    }
    
}
