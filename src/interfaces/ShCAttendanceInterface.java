/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entity.ShCAttendance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ACER
 */
public interface ShCAttendanceInterface {
    public ObservableList<ShCAttendance> listShCAttendanceGrade11 = FXCollections.observableArrayList(); 
    public ObservableList<ShCAttendance> listShCAttendanceGrade12 = FXCollections.observableArrayList(); 
}
