/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

/**
 *
 * @author ACER
 */

public class CustomEnrolledStudent {
   private SimpleStringProperty stud_idnum = new SimpleStringProperty();
   private SimpleStringProperty app_no = new SimpleStringProperty();
   private SimpleStringProperty sy = new SimpleStringProperty();
   private SimpleStringProperty sem = new SimpleStringProperty();
   private SimpleStringProperty grade_level = new SimpleStringProperty();
   private SimpleStringProperty strand_code = new SimpleStringProperty();
   private SimpleStringProperty strand_code_desc = new SimpleStringProperty();
   private SimpleStringProperty strand_group = new SimpleStringProperty();
   private SimpleStringProperty section = new SimpleStringProperty();
   private SimpleStringProperty stud_fullname = new SimpleStringProperty();
   private SimpleStringProperty stud_lname = new SimpleStringProperty();
   private SimpleStringProperty stud_fname = new SimpleStringProperty();
   private SimpleStringProperty stud_suffix = new SimpleStringProperty();
   private SimpleStringProperty stud_mi = new SimpleStringProperty();
   private SimpleStringProperty stud_status = new SimpleStringProperty();

   private CheckBox customSelect;    
   
    public CustomEnrolledStudent() {
        this.customSelect = new CheckBox();   
    }

    public String getStud_idnum() {
        return stud_idnum.get();
    }

    public void setStud_idnum(String stud_idnum) {
        this.stud_idnum.set(stud_idnum);
    }
    
    public String getApp_no() {
        return app_no.get();
    }

    public void setApp_no(String app_no) {
        this.app_no.set(app_no);
    }

    public String getSy() {
        return sy.get();
    }

    public void setSy(String sy) {
        this.sy.set(sy);
    }

    public String getSem() {
        return sem.get();
    }

    public void setSem(String sem) {
        this.sem.set(sem);
    }

    public String getGrade_level() {
        return grade_level.get();
    }

    public void setGrade_level(String grade_level) {
        this.grade_level.set(grade_level);
    }

    public String getStrand_code() {
        return strand_code.get();
    }

    public void setStrand_code(String strand_code) {
        this.strand_code.set(strand_code);
    }
    
    public String getStrand_code_desc() {
        return strand_code_desc.get();
    }

    public void setStrand_code_desc(String strand_code_desc) {
        this.strand_code_desc.set(strand_code_desc);
    }

    public String getStrand_group() {
        return strand_group.get();
    }

    public void setStrand_group(String strand_group) {
        this.strand_group.set(strand_group);
    }

    public String getSection() {
        return section.get();
    }

    public void setSection(String section) {
        this.section.set(section);
    }

    public String getStud_fullname() {
        return stud_fullname.get();
    }

    public void setStud_fullname(String stud_fullname) {
        this.stud_fullname.set(stud_fullname);
    }

    public String getStud_lname() {
        return stud_lname.get();
    }

    public void setStud_lname(String stud_lname) {
        this.stud_lname.set(stud_lname);
    }
    
    public String getStud_fname() {
        return stud_fname.get();
    }

    public void setStud_fname(String stud_fname) {
        this.stud_fname.set(stud_fname);
    }
    
    public String getStud_suffix() {
        return stud_suffix.get();
    }

    public void setStud_suffix(String stud_suffix) {
        this.stud_suffix.set(stud_suffix);
    }
    
    public String getStud_mi() {
        return stud_mi.get();
    }

    public void setStud_mi(String stud_mi) {
        this.stud_mi.set(stud_mi);
    }
    
    public String getStud_status() {
        return stud_status.get();
    }

    public void setStud_status(String stud_status) {
        this.stud_status.set(stud_status);
    }
    
    public CheckBox getCustomSelect() {
        return customSelect;
    }

    public void setCustomSelect(CheckBox customSelect) {
        this.customSelect = customSelect;
    }
    
}
