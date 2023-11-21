/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entity.ShCClassStud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ACER
 */
public interface ShCClassStudInterface {
    public ObservableList<ShCClassStud> listStudentGrades = FXCollections.observableArrayList();   
    public ObservableList<ShCClassStud> listStudentSubjects = FXCollections.observableArrayList();
    public ObservableList<ShCClassStud> listStudentSchedules = FXCollections.observableArrayList();   
//    public ObservableList<ShCClassStud> listStudentScheduleToAssign = FXCollections.observableArrayList();
    
    public ObservableList<ShCClassStud> listStudentSubjectGrades1 = FXCollections.observableArrayList(); //--- first year, 1st sem
    public ObservableList<ShCClassStud> listStudentSubjectGrades2 = FXCollections.observableArrayList(); //--- first year, 2nd sem
    public ObservableList<ShCClassStud> listStudentSubjectGrades3 = FXCollections.observableArrayList(); //--- 2nd year, 1st sem
    public ObservableList<ShCClassStud> listStudentSubjectGrades4 = FXCollections.observableArrayList(); //--- 2nd year, 2nd sem
    public ObservableList<ShCClassStud> listStudentSubjectGrades5 = FXCollections.observableArrayList(); //--- summer, all subj
    public ObservableList<ShCClassStud> listStudentSubjectGrades5_1 = FXCollections.observableArrayList(); //--- summer, g11
    public ObservableList<ShCClassStud> listStudentSubjectGrades5_2 = FXCollections.observableArrayList(); //--- summer, g12
    
    
    
}
