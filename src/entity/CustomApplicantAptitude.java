/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ACER
 */

public class CustomApplicantAptitude {
   private SimpleStringProperty app_no = new SimpleStringProperty();
   private SimpleStringProperty description = new SimpleStringProperty();
   private SimpleStringProperty score_number = new SimpleStringProperty();
   private SimpleStringProperty score_letter = new SimpleStringProperty();

    public String  getApp_no() {
        return app_no.get();
    }

    public void setApp_no(String  app_no) {
        this.app_no.set(app_no);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getScore_number() {
        return score_number.get();
    }

    public void setScore_number(String score_number) {
        this.score_number.set(score_number);
    }

    public String getScore_letter() {
        return score_letter.get();
    }

    public void setScore_letter(String score_letter) {
        this.score_letter.set(score_letter);
    }
    
}
