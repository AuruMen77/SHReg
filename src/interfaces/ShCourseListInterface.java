/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entity.ShCourseList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author CITS-Sheng
 */
public interface ShCourseListInterface {
    ObservableList<ShCourseList> listCourse = FXCollections.observableArrayList(); 
}
