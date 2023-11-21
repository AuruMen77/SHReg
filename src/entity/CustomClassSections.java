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

public class CustomClassSections {
   private SimpleStringProperty class_id = new SimpleStringProperty(); 
   private SimpleStringProperty sy = new SimpleStringProperty();
   private SimpleStringProperty sem = new SimpleStringProperty();
   private SimpleStringProperty grade_level = new SimpleStringProperty();
   private SimpleStringProperty subject_code = new SimpleStringProperty();
   private SimpleStringProperty subject_name = new SimpleStringProperty();
   private SimpleStringProperty subject_section = new SimpleStringProperty();
   private SimpleStringProperty strand_code = new SimpleStringProperty();
   private SimpleStringProperty strand_group = new SimpleStringProperty();
   private SimpleStringProperty class_section = new SimpleStringProperty();
   private SimpleStringProperty class_teacher_id = new SimpleStringProperty();
   private SimpleStringProperty class_teacher_name = new SimpleStringProperty();
   

    public String getClass_id() {
        return class_id.get();
    }

    public void setClass_id(String class_id) {
        this.class_id.set(class_id);
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

    public String getClass_section() {
        return class_section.get();
    }

    public void setClass_section(String class_section) {
        this.class_section.set(class_section);
    }

    public String getClass_teacher_id() {
        return class_teacher_id.get();
    }

    public void setClass_teacher_id(String class_teacher_id) {
        this.class_teacher_id.set(class_teacher_id);
    }

    public String getClass_teacher_name() {
        return class_teacher_name.get();
    }

    public void setClass_teacher_name(String class_teacher_name) {
        this.class_teacher_name.set(class_teacher_name);;
    }

    public String getSubject_code() {
        return subject_code.get();
    }

    public void setSubject_code(String subject_code) {
        this.subject_code.set(subject_code);
    }

    public String getSubject_name() {
        return subject_name.get();
    }

    public void setSubject_name(String subject_name) {
        this.subject_name.set(subject_name);
    }

    public String getSubject_section() {
        return subject_section.get();
    }

    public void setSubject_section(String subject_section) {
        this.subject_section.set(subject_section);
    }

    
}
