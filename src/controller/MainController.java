/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CITS-Sheng
 */
public class MainController implements Initializable {

    @FXML
    private Menu menuFile;
    @FXML
    private Menu menuApplicant;
    @FXML
    private MenuItem menuItemApplicantList;
    @FXML
    private Menu menuStudent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    public void actionApplicantList(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/ApplicantList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Applicant List");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    public void actionStudentList(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/StudentList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Student List");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    public void actionCurriculumList(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/CurriculumList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Curriculum List");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    public void actionScheduleList(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/ScheduleList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Schedule List");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void actionStudentAssignScheduleBatch(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/StudentAssignScheduleBatch.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Student Batch Schedule Assignment");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    public void actionScheduleEntry(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/ScheduleEntrySelectSubject.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Schedule List");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void actionGradesBySection(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/GradeSectionsList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Schedule List");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void actionSetGradeView(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/GradeView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Grade Viewing");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    public void actionSetClearance(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/SetStudentClearance.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Student Clearance Settings");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
        
    public void actionSetGradeSubmission(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/SetGradeSubmission.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Grade Submission Settings");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void actionClassList(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/ClassList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Class List");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void actionSectionList(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/SectionList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Section List");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void actionIndividualEnrollment(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/StudentEnrollAndAssignScheduleIndividual.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Individual Enrollment");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
      @FXML
    public void actionIndividualPromotion(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/StudentPromoteAndAssignScheduleIndividual.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Individual Enrollment");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void actionBatchEnrollment(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/StudentEnrollAndAssignScheduleBatch.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Batch Enrollment");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void actionBatchPromotion(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/StudentPromoteAndAssignScheduleBatch.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Batch Enrollment");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void actionDayList(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/DayList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Day List");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void actionInstructorList(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/InstructorList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Instructor List");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void actionCourseList(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/CourseList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Course List");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/adzu_logo.png")));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }    
    
    @FXML
    public void actionManageTerm(ActionEvent event) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("/view/ManageTerm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Manage Term");
        stage.getIcons().add(new Image("/image/adzu_logo.png"));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    //20231023 -- gio testing
    @FXML
    public void actionUpload (ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/UploadStudents.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Upload Students");
        stage.getIcons().add(new Image("/image/adzu_logo.png"));
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    
}
