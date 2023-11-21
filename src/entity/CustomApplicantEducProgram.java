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
public class CustomApplicantEducProgram {
    private SimpleStringProperty app_no = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleStringProperty rating = new SimpleStringProperty();

    public String getApp_no() {
        return app_no.get();
    }

    public void setApp_no(String app_no) {
        this.app_no.set(app_no);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getRating() {
        return rating.get();
    }

    public void setRating(String rating) {
        this.rating.set(rating);
    }
    
    
    
}
