/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;


/**
 *
 * @author ACER
 */

public class CustomCheckbox {
  private BooleanProperty checked;

    public CustomCheckbox() {
      this.checked = new SimpleBooleanProperty(true);
    }

   public void setChecked(boolean checked) {
      this.checked.set(checked);
    }
    public BooleanProperty checkedProperty() {
      return checked;
    }
   

}
