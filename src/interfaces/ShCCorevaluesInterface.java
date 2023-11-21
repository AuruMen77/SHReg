/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entity.ShCCorevalues;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ACER
 */
public interface ShCCorevaluesInterface {
    public ObservableList<ShCCorevalues> listShCCoreValuesGrade11 = FXCollections.observableArrayList(); 
    public ObservableList<ShCCorevalues> listShCCoreValuesGrade12 = FXCollections.observableArrayList(); 
   
}
