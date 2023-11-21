/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.AppResults;
import entity.CustomApplicantAptitude;
import entity.CustomApplicantEducProgram;
import entity.ShApplicant;
import entity.ShConfirmation;
import entity.ShCurrDtl;
import entity.ShCurrHdr;
import interfaces.ShApplicantInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.CustomApplicantAptitudeModel;
import model.ShApplicantModel;
import model.ShCurrDtlModel;
import model.CustomApplicantEducProgramModel;
import model.ShConfirmationModel;
import model.StrandsModel;
import model.ShCourseListModel;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class ApplicantConfirmationEntryController implements Initializable, ShApplicantInterface {

    @FXML
    private TextField txtSY,txtStrandgroup;
    @FXML
    private TextField txtTestDate;
    @FXML
    private TextField txtAppNo;
    @FXML
    private TextField txtStudName;
    @FXML
    private TextField txtChoice1;
    @FXML
    private TextField txtChoice2;
    @FXML
    private TextField txtChoice3;
    @FXML
    private TitledPane tblRecommendation1;
    @FXML
    private TextArea txtEncouragedStrands;
    @FXML
    private TextArea txtMayEnrollToStrands;
    @FXML
    private TitledPane tblRecommendation2;
    @FXML
    private ComboBox cbChosenStrand;
    @FXML
    private CheckBox chkGoodMoral;
    @FXML
    private CheckBox chkPAC;
    @FXML
    private CheckBox chkBirthCertificate;
    @FXML
    private CheckBox chkForm138Copy;
    @FXML
    private CheckBox chkForm138Original;
    @FXML
    private TextField txtLRN;
    @FXML
    private TextField txtStudID;
    @FXML
    private TextField txtClinicDate;
    @FXML
    private Button btnConfirmStudent;
    @FXML
    private Button btnPreview;
    @FXML
    private TableView<CustomApplicantAptitude> tblAptitude;
    @FXML
    private TableColumn<CustomApplicantAptitude, String> colAptitudeDesc;
    @FXML
    private TableColumn<?, ?> colAptitudeScore;
    @FXML
    private TableColumn<?, ?> colAptitudeLetter;
    @FXML
    private TableView<CustomApplicantEducProgram> tblEducProgram;
    @FXML
    private TableColumn<?, ?> colEducProgramDesc;
    @FXML
    private TableColumn<?, ?> colEducProgramRating;
    
    public ObservableList<CustomApplicantAptitude> listAptitude = FXCollections.observableArrayList();
    public ObservableList<CustomApplicantEducProgram> listEducProgram = FXCollections.observableArrayList();
    private CustomApplicantAptitudeModel customApplicantAptitudeModel;
    private CustomApplicantEducProgramModel customApplicantEducProgramModel;
    private ShConfirmationModel shConfirmationModel;
    private StrandsModel strandsModel;
    private AppResults appResults;
    private ShConfirmation shConfirmation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void setApplicantResults(AppResults appResults) {  
        strandsModel = new StrandsModel();
        customApplicantAptitudeModel = new CustomApplicantAptitudeModel();
        customApplicantEducProgramModel =  new CustomApplicantEducProgramModel();
        shConfirmationModel = new ShConfirmationModel();
        this.appResults = appResults;  
        this.shConfirmation = shConfirmationModel.getRowApplicantConfirmation(appResults.getShApplicant());
        setApplicantDetails();
        loadApplicantResults();
        setApplicantRecommendationDetails();
        setApplicantConfirmationDetails();
    }
    
    public void setApplicantDetails() {
               
       txtSY.setText(appResults.getArSy());
       txtTestDate.setText(appResults.getTestDate().toString());
       txtAppNo.setText(appResults.getShApplicant().getAppNo());
       txtStudName.setText(appResults.getShApplicant().getAppLname().trim() +", "+ appResults.getShApplicant().getAppFname().trim() 
               +" "+ appResults.getShApplicant().getAppMiddlename().trim());
       txtChoice1.setText(appResults.getShApplicant().getAppFirstchoice());
       txtChoice2.setText(appResults.getShApplicant().getAppSecondchoice());
       txtChoice3.setText(appResults.getShApplicant().getAppThirdchoice());
    }
    
    public void setApplicantRecommendationDetails() {
       txtEncouragedStrands.setText(appResults.getEncouraged());
       txtMayEnrollToStrands.setText(appResults.getMayEnroll());
    }
    
    public void setApplicantConfirmationDetails() {
        loadStrandsInCombobox();
        
        if(shConfirmation!=null){
            cbChosenStrand.getSelectionModel().select(shConfirmation.getStrandCode());
            txtStrandgroup.setText(shConfirmation.getStrandGroup());

            if(shConfirmation.getReqGmc()==true)
                chkGoodMoral.setSelected(true);
            else
                chkGoodMoral.setSelected(false);

            if(shConfirmation.getReqPact()==true)
                chkPAC.setSelected(true);
            else
                chkPAC.setSelected(false);

            if(shConfirmation.getReqBc()==true)
                chkBirthCertificate.setSelected(true);
            else
                chkBirthCertificate.setSelected(false);

            if(shConfirmation.getReqPrc()==true)
                chkForm138Copy.setSelected(true);
            else
                chkForm138Copy.setSelected(false);

            if(shConfirmation.getReqOrc()==true)
                chkForm138Original.setSelected(true);
            else
                chkForm138Original.setSelected(false);

            txtLRN.setText(appResults.getShApplicant().getAppLrn());
            txtStudID.setText(appResults.getShApplicant().getStudIdnum());
            txtClinicDate.setText(shConfirmation.getClinicDate() +" "+ shConfirmation.getClinicAmpm());
        }
    }
    
    private void loadStrandsInCombobox(){
        ObservableList<String> listStrands = FXCollections.observableArrayList(strandsModel.getStrandsForCombobox());
        cbChosenStrand.setItems(listStrands);
    }
    
    private void loadApplicantResults(){
        loadAptitudeResultInTable();
        loadEducProgramResultInTable();
    }
    
    private void loadAptitudeResultInTable() {
        if (!listAptitude.isEmpty()) {
            listAptitude.clear();
        }
        listAptitude.addAll(this.customApplicantAptitudeModel.getResApplicantAptitude(appResults.getShApplicant().getAppNo()));
      
        colAptitudeDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAptitudeScore.setCellValueFactory(new PropertyValueFactory<>("score_number"));
        colAptitudeLetter.setCellValueFactory(new PropertyValueFactory<>("score_letter"));

        tblAptitude.setItems(listAptitude); 
    }
    
    private void loadEducProgramResultInTable() {
        if (!listEducProgram.isEmpty()) {
            listEducProgram.clear();
        }
        
        
        listEducProgram.addAll(customApplicantEducProgramModel.getResApplicantEducProgram(appResults.getShApplicant().getAppNo()));
      
        colEducProgramDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.colEducProgramRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        tblEducProgram.setItems(listEducProgram); 
    }
    
    public void actionSave(ActionEvent event) throws Exception {
        if (validateInput()) {
                
            Date date_current = new Date();
            
            int studID_to_assign = this.shConfirmationModel.getMaxStudIDNum() + 1;

            Boolean success;
            
            if(shConfirmation==null){
                ShApplicant shApplicant = this.appResults.getShApplicant();
                shApplicant.setStudIdnum(String.valueOf(studID_to_assign));

                shConfirmation = new ShConfirmation();  
                shConfirmation.setShApplicant(shApplicant);
                shConfirmation.setStudentId(String.valueOf(studID_to_assign));
                shConfirmation.setConfirmed(true);
                shConfirmation.setConfirmDate(date_current);
                
                shConfirmation.setConSy(appResults.getArSy());
                shConfirmation.setStrandCode(this.cbChosenStrand.getSelectionModel().getSelectedItem().toString());
                shConfirmation.setStrandGroup(txtStrandgroup.getText().toUpperCase());
                shConfirmation.setReqGmc(chkGoodMoral.selectedProperty().getValue());
                shConfirmation.setReqPact(chkPAC.selectedProperty().getValue());
                shConfirmation.setReqBc(chkBirthCertificate.selectedProperty().getValue());
                shConfirmation.setReqPrc(chkForm138Copy.selectedProperty().getValue());
                shConfirmation.setReqOrc(chkForm138Original.selectedProperty().getValue());
                success = this.shConfirmationModel.saveApplicantConfirmation(shConfirmation,shApplicant);
            }else{
                shConfirmation.setShApplicant(this.appResults.getShApplicant());
                shConfirmation.setStudentId(this.txtStudID.getText());
                
                shConfirmation.setConSy(appResults.getArSy());
                shConfirmation.setStrandCode(this.cbChosenStrand.getSelectionModel().getSelectedItem().toString());
                shConfirmation.setStrandGroup(txtStrandgroup.getText().toUpperCase());
                shConfirmation.setReqGmc(chkGoodMoral.selectedProperty().getValue());
                shConfirmation.setReqPact(chkPAC.selectedProperty().getValue());
                shConfirmation.setReqBc(chkBirthCertificate.selectedProperty().getValue());
                shConfirmation.setReqPrc(chkForm138Copy.selectedProperty().getValue());
                shConfirmation.setReqOrc(chkForm138Original.selectedProperty().getValue());
                success = this.shConfirmationModel.saveApplicantConfirmation(shConfirmation);         
            } 

            //--- reload applicant list from previous form 
            ShApplicantModel applicantModel = new ShApplicantModel();
            listApplicant.clear();
            listApplicant.addAll(applicantModel.getResApplicant());
            
            ((Stage) btnConfirmStudent.getScene().getWindow()).close();
            
            if(success==true)
                this.showMessage(success, "Successful", "Student Confirmation", "Student is confirmed successfully");
            else
                this.showMessage(success, "Error", "Error", "Error occured");  
        }
        
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

        if (cbChosenStrand.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Select chosen strand!\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            showMessage(false,"Invalid Fields","Please correct invalid fields",errorMessage);
            return false;
        }
    }
}
