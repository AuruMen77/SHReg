/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entity.CustomEnrolledStudent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ACER
 */
public interface CustomEnrolledStudentInterface {
    public ObservableList<CustomEnrolledStudent> listEnrolledStudent = FXCollections.observableArrayList();    
    public ObservableList<CustomEnrolledStudent> listStudentsToPromote = FXCollections.observableArrayList();
    
    public ObservableList<CustomEnrolledStudent> listEnrolledStudentCurrentSYSem = FXCollections.observableArrayList();   
}
