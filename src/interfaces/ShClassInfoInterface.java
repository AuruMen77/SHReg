/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entity.ShClassInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ACER
 */
public interface ShClassInfoInterface {
    public ObservableList<ShClassInfo> listSchedule = FXCollections.observableArrayList();   
    public ObservableList<ShClassInfo> listScheduleSelectSubject = FXCollections.observableArrayList();   
    public ObservableList<ShClassInfo> listClassSections = FXCollections.observableArrayList();   
    public ObservableList<ShClassInfo> listClassSubjects = FXCollections.observableArrayList(); 
    
    
}
