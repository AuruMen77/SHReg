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

public class CustomStudentGrades {
   private SimpleStringProperty cclass_id = new SimpleStringProperty(); 
   private SimpleStringProperty stud_idnum = new SimpleStringProperty();
   private SimpleStringProperty stud_fullname = new SimpleStringProperty();
   private SimpleStringProperty stud_section = new SimpleStringProperty();
   private SimpleStringProperty sy = new SimpleStringProperty();
   private SimpleStringProperty sem = new SimpleStringProperty();
   private SimpleStringProperty grade_level = new SimpleStringProperty();
   private SimpleStringProperty strand_code = new SimpleStringProperty();
   private SimpleStringProperty strand_group = new SimpleStringProperty();
   private SimpleStringProperty subject_code = new SimpleStringProperty();
   private SimpleStringProperty subject_desc = new SimpleStringProperty();
   private SimpleStringProperty subject_section = new SimpleStringProperty();
   private SimpleStringProperty grade1 = new SimpleStringProperty();
   private SimpleStringProperty grade2 = new SimpleStringProperty();

    public String getCclass_id() {
        return cclass_id.get();
    }

    public void setCclass_id(String cclass_id) {
        this.cclass_id.set(cclass_id);
    }

    public String getStud_idnum() {
        return stud_idnum.get();
    }

    public void setStud_idnum(String stud_idnum) {
        this.stud_idnum.set(stud_idnum);
    }

    public String getStud_fullname() {
        return stud_fullname.get();
    }

    public void setStud_fullname(String stud_fullname) {
        this.stud_fullname.set(stud_fullname);
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

    public String getSubject_code() {
        return subject_code.get();
    }

    public void setSubject_code(String subject_code) {
        this.subject_code.set(subject_code);
    }

    public String getSubject_desc() {
        return subject_desc.get();
    }

    public void setSubject_desc(String subject_desc) {
        this.subject_desc.set(subject_desc);
    }

    public String getSubject_section() {
        return subject_section.get();
    }

    public void setSubject_section(String subject_section) {
        this.subject_section.set(subject_section);
    }

    public String getGrade1() {
        return grade1.get();
    }

    public void setGrade1(String grade1) {
        this.grade1.set(grade1);
    }

    public String getGrade2() {
        return grade2.get();
    }

    public void setGrade2(String grade2) {
        this.grade2.set(grade2);
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

    public String getStud_section() {
        return stud_section.get();
    }

    public void setStud_section(String stud_section) {
        this.stud_section.set(stud_section);
    }
}
