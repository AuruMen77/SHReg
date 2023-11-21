/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entity.CoreValues;
import entity.CustomEnrolledStudent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author CITS-Sheng
 */
public interface ClassCoreValuesInterface {
    public ObservableList<CoreValues> listCoreValues = FXCollections.observableArrayList();   
    public ObservableList<CustomEnrolledStudent> listEnrolledStudent = FXCollections.observableArrayList();   
}
