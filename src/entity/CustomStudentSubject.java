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

public class CustomStudentSubject {
   private SimpleStringProperty class_id = new SimpleStringProperty(); 
   private SimpleStringProperty stud_idnum = new SimpleStringProperty();
   private SimpleStringProperty sy = new SimpleStringProperty();
   private SimpleStringProperty sem = new SimpleStringProperty();
   private SimpleStringProperty grade_level = new SimpleStringProperty();
   private SimpleStringProperty strand_code = new SimpleStringProperty();
   private SimpleStringProperty strand_group = new SimpleStringProperty();
   private SimpleStringProperty subject_section = new SimpleStringProperty();
   private SimpleStringProperty subject_desc = new SimpleStringProperty();
   private SimpleStringProperty subject_code = new SimpleStringProperty();
   private SimpleStringProperty subject_unit = new SimpleStringProperty();
   private SimpleStringProperty room = new SimpleStringProperty();
   private SimpleStringProperty day_assigned = new SimpleStringProperty();
   private SimpleStringProperty time_start = new SimpleStringProperty();
   private SimpleStringProperty time_end = new SimpleStringProperty();
   

    public String getClass_id() {
        return class_id.get();
    }

    public void setClass_id(String class_id) {
        this.class_id.set(class_id);
    }
    
    public String getStud_idnum() {
        return stud_idnum.get();
    }

    public void setStud_idnum(String stud_idnum) {
        this.stud_idnum.set(stud_idnum);
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

    public String getStrand_group() {
        return strand_group.get();
    }

    public void setStrand_group(String strand_group) {
        this.strand_group.set(strand_group);
    }

    public String getSubject_section() {
        return subject_section.get();
    }

    public void setSubject_section(String subject_section) {
        this.subject_section.set(subject_section);
    }

    public String getSubject_desc() {
        return subject_desc.get();
    }

    public void setSubject_desc(String subject_desc) {
        this.subject_desc.set(subject_desc);
    }
    
    
    public String getSubject_code() {
        return subject_code.get();
    }

    public void setSubject_code(String subject_code) {
        this.subject_code.set(subject_code);
    }

    public String getSubject_unit() {
        return subject_unit.get();
    }

    public void setSubject_unit(String subject_unit) {
        this.subject_unit.set(subject_unit);
    }
    
    public String getRoom() {
        return room.get();
    }

    public void setRoom(String room) {
        this.room.set(room);
    }
    
    public String getDay_assigned() {
        return day_assigned.get();
    }

    public void setDay_assigned(String day_assigned) {
        this.day_assigned.set(day_assigned);
    }
    
    public String getTime_start() {
        return time_start.get();
    }

    public void setTime_start(String time_start) {
        this.time_start.set(time_start);
    }
    
    public String getTime_end() {
        return time_end.get();
    }

    public void setTime_end(String time_end) {
        this.time_end.set(time_end);
    }
    
}
