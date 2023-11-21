/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.CustomEnrolledStudent;
import entity.CustomStudentSubject;
import entity.ShCClassStud;
import entity.ShStudStrand;
import entity.ShStudlist;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author CITS-Sheng
 */
public class ShCClassStudModel {
    private static Session session;
    
    public boolean hasSubjects(String csSy, int csSem, String csIdnum){
        session = HibernateUtil.getSessionFactory().openSession();
        boolean success = false;
        try{
            Transaction txn = session.beginTransaction();
            Long cntSubjects = (Long)session.createQuery("SELECT COUNT(*) FROM ShCClassStud WHERE csSy = :csSy AND csSem = :csSem AND csIdnum = :csIdnum")
                    .setParameter("csSy", csSy)
                    .setParameter("csSem", csSem)
                    .setParameter("csIdnum", csIdnum)
                     .uniqueResult();
            txn.commit();

            if(cntSubjects>0)
                success=true;
            else
                success=false;
        }catch(Exception ex){
            success=false;
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        return success;
    }
    
    
    //--- START sql query ----//
    
    public ObservableList<String[]> getResStudentSubjectGradesConcat(String ss_sy, String ss_sem, String ss_yr_level, 
            String strand_code, String strand_group, String str_cs_crs_code){
 
        
        
        session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<String[]> list = FXCollections.observableArrayList();
        try{
            Transaction txn = session.beginTransaction();
            List<String[]> listStudentCoreValues = session.createSQLQuery("SELECT sh_stud_strand.`stud_idnum`,  " +
            "CONCAT(TRIM(stud_lname),', ',TRIM(stud_fname)) AS student, " +
            "GROUP_CONCAT( " +
                "IF( " +
                    "CONCAT(cs_crs_code,'-',crs_unit) IS NULL," +
                    "CONCAT(TRIM(crs_code),'-',crs_unit,'-0-0'), " +
                    "CONCAT( " +
                             "TRIM(crs_code),'-',crs_unit,'-', " +
                             "IF(cs_mid_grade='' OR cs_mid_grade IS NULL,0,cs_mid_grade), '-', " +
                             "IF(cs_sec_qtr='' OR cs_sec_qtr IS NULL,0,cs_sec_qtr) " +
                    ") " +
                ") ORDER BY crs_code,cs_section ASC " +
            ") AS student_grades   " +
            "FROM `sh_stud_strand`   " +
            "LEFT JOIN sh_studlist ON sh_stud_strand.`stud_idnum` = sh_studlist.`stud_idnum` AND ss_sy=sy AND ss_sem=sem  " +
            "LEFT JOIN sh_course_list ON `crs_code` IN ( " + str_cs_crs_code + " ) " +
            "LEFT JOIN `sh_c_class_stud` ON ss_sy=cs_sy  AND ss_sem=cs_sem AND sh_stud_strand.`stud_idnum`=cs_idnum  AND `crs_code`=cs_crs_code " +
            "WHERE ss_sy= :ss_sy AND ss_sem = :ss_sem  AND ss_yr_level= :ss_yr_level " +
            "AND sh_stud_strand.`strand_code`= :strand_code AND sh_stud_strand.`strand_group`= :strand_group  " +
            "GROUP BY sh_stud_strand.`stud_idnum`")
            .setParameter("ss_sy", ss_sy)
            .setParameter("ss_sem", ss_sem)
            .setParameter("ss_yr_level", ss_yr_level)    
            .setParameter("strand_code", strand_code)       
            .setParameter("strand_group", strand_group)               
           .list();
            
            txn.commit();
            
            for(Object[] row : listStudentCoreValues){
                String[] row_add = new String[3];
                row_add[0] = row[0].toString();
                row_add[1] = row[1].toString();
                row_add[2] = row[2].toString();

                list.add(row_add);
            }
         
            
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        return list;
    }
    
    public ObservableList<ShCClassStud> getResStudentGradesBySubjectSection(String ss_sy, String ss_sem, String cs_crs_code, String cs_section) {
        ObservableList<ShCClassStud> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        List<Object[]> listStudentGrades = session.createSQLQuery("SELECT `cclass_id`,sh_stud_strand.stud_idnum, IFNULL(CONCAT(TRIM(`stud_lname`),', ',TRIM(`stud_fname`),' ',TRIM(`stud_suffix`),' ',TRIM(`stud_mi`)),'') AS student, "
                + "ss_sy, ss_sem, ss_yr_level, TRIM(strand_code) AS strand_code, TRIM(strand_group) AS strand_group, "
                + "`cs_crs_code`, `cs_section`, IFNULL(`cs_mid_grade`, '') AS grade1,  IFNULL(`cs_sec_qtr`, '') AS grade2, IFNULL(stud_section,'') AS stud_section " 
                + "FROM `sh_stud_strand` " 
                + "LEFT JOIN `sh_studlist` ON sh_stud_strand.`stud_idnum` = sh_studlist.`stud_idnum` AND ss_sy = sh_studlist.sy AND ss_sem = sh_studlist.sem  "
                + "LEFT JOIN `sh_c_class_stud` ON ss_sy = cs_sy AND ss_sem = cs_sem AND ss_yr_level = cs_yr_level AND sh_stud_strand.`stud_idnum`=cs_idnum  " 
                + "LEFT JOIN strand_section  ON ss_sy=`strand_section`.`sy` AND ss_sem=strand_section.`sem` AND ss_yr_level= strand_section.`grade_level`  "
                + "AND strand_code=strand AND strand_group=strand_section.strandgroup     " 
                + "WHERE ss_sy = :ss_sy AND ss_sem = :ss_sem AND cs_crs_code = :cs_crs_code AND cs_section = :cs_section ")
                .setParameter("ss_sy", ss_sy)
                .setParameter("ss_sem", ss_sem)
                .setParameter("cs_crs_code", cs_crs_code)
                .setParameter("cs_section", cs_section)
                .list();
        
        txn.commit();

        for(Object[] row : listStudentGrades){
            ShCClassStud shCClassStud = new ShCClassStud();
            shCClassStud.setCclassId(row[0].toString());
            shCClassStud.setCsIdnum(row[1].toString());
            shCClassStud.setStud_fullname(row[2].toString());
            shCClassStud.setCsSy(row[3].toString());
            shCClassStud.setCsSem(Integer.parseInt(row[4].toString()));
            shCClassStud.setCsYrLevel(Integer.parseInt(row[5].toString()));
            shCClassStud.setStrand_code(row[6].toString());
            shCClassStud.setCsStrandgroup(row[7].toString());
            shCClassStud.setSubject_desc(row[8].toString());
            shCClassStud.setCsSection(row[9].toString());
            shCClassStud.setCsMidGrade(row[10].toString());
            shCClassStud.setCsSecQtr(row[11].toString());
            shCClassStud.setStud_section(row[12].toString());
            list.add(shCClassStud);
        }
        
        session.close(); session.getSessionFactory().close();

        return list;
    }
    
    
    public ObservableList<ShCClassStud> getResStudentGradesIndividualBySYAndSem(String cs_sy, String cs_sem, String cs_idnum) {
        ObservableList<ShCClassStud> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        System.out.println(cs_sy + cs_sem + cs_idnum);
        
        try{
            List<Object[]> listStudentGrades = session.createSQLQuery("SELECT `cclass_id`,cs_idnum AS stud_idnum, cs_sy, cs_sem, " +
                    "IFNULL(cs_yr_level,'0') AS cs_yr_level,  " +
    "                `cs_crs_code`, crs_title, crs_unit,`cs_section`, IF(cs_mid_grade IS NULL || cs_mid_grade='','0',cs_mid_grade) AS grade1, " +
                    " IF(`cs_sec_qtr` IS NULL || `cs_sec_qtr`='','0',cs_sec_qtr) AS grade2, " +
                   " IFNULL((cs_mid_grade+cs_sec_qtr)/2,0) AS fg, IFNULL((IF(MOD(cs_mid_grade+cs_sec_qtr, 2)='1', CEILING((cs_mid_grade+cs_sec_qtr)/2), (cs_mid_grade+cs_sec_qtr)/2)),0) AS fg_round, crs_type " +
    "                FROM  `sh_c_class_stud`  " +
    "                LEFT JOIN `sh_course_list` ON cs_crs_code = `crs_code` " +
    "                WHERE `is_no_grade`=0 AND cs_sy= :cs_sy AND cs_sem = :cs_sem AND cs_idnum= :cs_idnum GROUP BY cs_crs_code")
                    .setParameter("cs_sy", cs_sy)
                    .setParameter("cs_sem", cs_sem)
                    .setParameter("cs_idnum", cs_idnum)
                    .list();

            txn.commit();

            for(Object[] row : listStudentGrades){
                Double Subject_fg = (Double) row[12];
                
                ShCClassStud shCClassStud = new ShCClassStud();
                shCClassStud.setCclassId(row[0].toString());
                shCClassStud.setCsIdnum(row[1].toString());
                shCClassStud.setCsSy(row[2].toString());
                shCClassStud.setCsSem(Integer.parseInt(row[3].toString()));
                shCClassStud.setCsYrLevel(Integer.parseInt(row[4].toString()));
                shCClassStud.setCsCrsCode(row[5].toString());
                shCClassStud.setSubject_desc(row[6].toString());
                shCClassStud.setSubject_unit(row[7].toString());
                shCClassStud.setCsSection(row[8].toString());
                shCClassStud.setCsMidGrade(row[9].toString());
                shCClassStud.setCsSecQtr(row[10].toString());
                shCClassStud.setSubject_fg("" + Subject_fg.intValue());
                shCClassStud.setSubject_type(row[13].toString());

                list.add(shCClassStud);
            }
        
//            txn.commit();
        }catch(Exception ex){
            
            ex.printStackTrace();
//            txn.rollback();
        }finally{
            session.close(); session.getSessionFactory().close();
        }

        return list;
    }
    
    
    public ObservableList<ShCClassStud> getResStudentOfferedSubjectsBySYAndSem(String sy, String sem, String yrlevel, String strandcode) {
        ObservableList<ShCClassStud> list = FXCollections.observableArrayList();
//        System.out.println("VAL: " + sy +" "+  sem +" "+ yrlevel +" "+ strandcode);
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        
       try{
            List<Object[]> listStudentGrades = session.createSQLQuery("SELECT '' AS `cclass_id`,'' AS stud_idnum, sh_curr_sy.sy AS cs_sy, sh_curr_dtl.`sem` AS cs_sem,  " +
"                    IFNULL(sh_curr_dtl.`yrlevel`,'0') AS cs_yr_level,   " +
"                    sh_course_list.`crs_code` AS `cs_crs_code`, crs_title, crs_unit,'' AS `cs_section`, 0 AS grade1,  " +
"                     0 AS grade2,  " +
"                    0 AS fg, 0 AS fg_round, crs_type  " +
"                    FROM  `sh_curr_hdr` " +
"                    LEFT JOIN `sh_curr_dtl` ON sh_curr_hdr.`curr_hdr_id` = sh_curr_dtl.`f_curr_hdr` " +
"                    LEFT JOIN `sh_curr_sy` ON sh_curr_hdr.`curr_hdr_id` = sh_curr_sy.`f_curr_hdr` " +
"                    LEFT JOIN `sh_course_list` ON sh_curr_dtl.`f_course` = sh_course_list.`crs_id` " +
"                    WHERE sh_curr_sy.`sy`= :sy AND sh_curr_dtl.`sem`= :sem AND sh_curr_dtl.`yrlevel`= :yrlevel AND sh_curr_hdr.`strandcode`= :strandcode AND is_no_grade = 0")
                    .setParameter("sy", sy)
                    .setParameter("sem", sem)
                    .setParameter("yrlevel", yrlevel)
                    .setParameter("strandcode", strandcode)
                    .list();

            txn.commit();

            for(Object[] row : listStudentGrades){
                
                ShCClassStud shCClassStud = new ShCClassStud();
                shCClassStud.setCclassId(row[0].toString());
                shCClassStud.setCsIdnum(row[1].toString());
                shCClassStud.setCsSy(row[2].toString());
                shCClassStud.setCsSem(Integer.parseInt(row[3].toString()));
                shCClassStud.setCsYrLevel(Integer.parseInt(row[4].toString()));
                shCClassStud.setCsCrsCode(row[5].toString());
                shCClassStud.setSubject_desc(row[6].toString());
                shCClassStud.setSubject_unit(row[7].toString());
                shCClassStud.setCsSection(row[8].toString());
                shCClassStud.setCsMidGrade(row[9].toString());
                shCClassStud.setCsSecQtr(row[10].toString());
                shCClassStud.setSubject_fg(row[12].toString());
                shCClassStud.setSubject_type(row[13].toString());

                list.add(shCClassStud);
            }
        
//            txn.commit();
        }catch(Exception ex){
            
            ex.printStackTrace();
//            txn.rollback();
        }finally{
            session.close(); session.getSessionFactory().close();
        }

        return list;
    }
    
    
    public ObservableList<ShCClassStud> getResStudentGradesIndividualSummer(String cs_sem, String cs_idnum) {
        ObservableList<ShCClassStud> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        
        try{
            List<Object[]> listStudentGrades = session.createSQLQuery(
                    "SELECT sh_c_class_stud.`cclass_id`, sh_c_class_stud.cs_idnum AS stud_idnum, sh_c_class_stud.cs_sy, sh_c_class_stud.cs_sem, " +
                    "IFNULL(sh_c_class_stud.`cs_yr_level`,'0') AS cs_yr_level, sh_c_class_stud.`cs_crs_code`, crs_title, crs_unit, sh_c_class_stud.`cs_section`, " +
                    "IFNULL(sh_c_class_student.`cs_mid_grade`,CEILING(IFNULL(sh_c_class_stud.`cs_mid_grade`,IFNULL((tbl2.`cs_mid_grade`+tbl2.`cs_sec_qtr`)/2, 1)))) AS grade1, " +
                    "IFNULL(sh_c_class_student.`cs_sec_qtr`,IFNULL(sh_c_class_stud.`cs_sec_qtr`,1)) AS grade2, " +
                    "(CEILING(IFNULL(sh_c_class_student.`cs_mid_grade`,IFNULL(sh_c_class_stud.`cs_mid_grade`,IFNULL((tbl2.`cs_mid_grade`+tbl2.`cs_sec_qtr`)/2, 1)))) + IFNULL(sh_c_class_student.`cs_sec_qtr`,IFNULL(sh_c_class_stud.`cs_sec_qtr`,1)))/2 AS fg, " +
                    "CEILING((IFNULL(sh_c_class_student.`cs_mid_grade`,IFNULL(sh_c_class_stud.`cs_mid_grade`,IFNULL((tbl2.`cs_mid_grade`+tbl2.`cs_sec_qtr`)/2, 1))) + IFNULL(sh_c_class_student.`cs_sec_qtr`,IFNULL(sh_c_class_stud.`cs_sec_qtr`,1)))/2) AS fg_round, crs_type " +
                    "FROM `sh_c_class_stud` " +
                    "LEFT JOIN `sh_course_list` ON cs_crs_code = `crs_code` " +
                    "LEFT JOIN sh_c_class_student ON sh_c_class_stud.`cs_idnum` = sh_c_class_student.`cs_idnum` AND sh_c_class_stud.`cs_crs_code` = sh_c_class_student.`cs_crs_code` " +
                    "AND sh_c_class_stud.`cs_section` = sh_c_class_student.`cs_section` " +
                    "LEFT JOIN (SELECT * FROM sh_c_class_stud WHERE cs_sem != '3' AND cs_idnum = :cs_idnum GROUP BY cs_crs_code) AS tbl2 ON sh_c_class_stud.`cs_crs_code` = tbl2.`cs_crs_code` " +
                    "WHERE `is_no_grade`=0  AND sh_c_class_stud.cs_idnum = :cs_idnum AND sh_c_class_stud.cs_sem = :cs_sem")
                    .setParameter("cs_sem", cs_sem)
                    .setParameter("cs_idnum", cs_idnum)
                    .list();

            txn.commit();

            for(Object[] row : listStudentGrades){
                ShCClassStud shCClassStud = new ShCClassStud();
                shCClassStud.setCclassId(row[0].toString());
                shCClassStud.setCsIdnum(row[1].toString());
                shCClassStud.setCsSy(row[2].toString());
                shCClassStud.setCsSem(Integer.parseInt(row[3].toString()));
                shCClassStud.setCsYrLevel(Integer.parseInt(row[4].toString()));
                shCClassStud.setCsCrsCode(row[5].toString());
                shCClassStud.setSubject_desc(row[6].toString());
                shCClassStud.setSubject_unit(row[7].toString());
                shCClassStud.setCsSection(row[8].toString());
                shCClassStud.setCsMidGrade(row[9].toString());
                shCClassStud.setCsSecQtr(row[10].toString());
                shCClassStud.setSubject_fg(row[12].toString());
                shCClassStud.setSubject_type(row[13].toString());
                
                
                list.add(shCClassStud);
            }
        
//            txn.commit();
        }catch(Exception ex){
            
            ex.printStackTrace();
//            txn.rollback();
        }finally{
            session.close(); session.getSessionFactory().close();
        }

        return list;
    }
    
    public ObservableList<ShCClassStud> getResStudentGradesIndividualSummer12(String cs_sem, String cs_idnum) {
        ObservableList<ShCClassStud> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        
        try{
            List<Object[]> listStudentGrades = session.createSQLQuery(
                    "SELECT sh_c_class_stud.`cclass_id`, sh_c_class_stud.cs_idnum AS stud_idnum, sh_c_class_stud.cs_sy, sh_c_class_stud.cs_sem, " +
                    "IFNULL(cs_yr_level,'0') AS cs_yr_level, sh_c_class_stud.`cs_crs_code`, crs_title, crs_unit, sh_c_class_stud.`cs_section`, " +
                    "IFNULL(sh_c_class_student.`cs_mid_grade`,IFNULL(sh_c_class_stud.`cs_mid_grade`,1)) AS grade1, " +
                    "IFNULL(sh_c_class_student.`cs_sec_qtr`,IFNULL(sh_c_class_stud.`cs_sec_qtr`,1)) AS grade2, " +
                    "(IFNULL(sh_c_class_student.`cs_mid_grade`,IFNULL(sh_c_class_stud.`cs_mid_grade`,1))+IFNULL(sh_c_class_student.`cs_sec_qtr`,IFNULL(sh_c_class_stud.`cs_sec_qtr`,1)))/2 AS fg, " +
                    "CEILING((IFNULL(sh_c_class_student.`cs_mid_grade`,IFNULL(sh_c_class_stud.`cs_mid_grade`,1))+IFNULL(sh_c_class_student.`cs_sec_qtr`,IFNULL(sh_c_class_stud.`cs_sec_qtr`,1)))/2) AS fg_round, crs_type " +
                    "FROM `sh_c_class_stud` " +
                    "LEFT JOIN `sh_course_list` ON cs_crs_code = `crs_code` " +
                    "LEFT JOIN sh_c_class_student ON sh_c_class_stud.`cs_idnum` = sh_c_class_student.`cs_idnum` AND sh_c_class_stud.`cs_crs_code` = sh_c_class_student.`cs_crs_code` " +
                    "AND sh_c_class_stud.`cs_section` = sh_c_class_student.`cs_section` " +
                    "WHERE `is_no_grade`=0  AND sh_c_class_stud.cs_idnum = :cs_idnum AND sh_c_class_stud.cs_sem = :cs_sem AND (sh_c_class_stud.cs_yr_level = :cs_yr_level)")
                    .setParameter("cs_sem", cs_sem)
                    .setParameter("cs_idnum", cs_idnum)
                    .setParameter("cs_yr_level", 12)
                    .list();

            txn.commit();

            for(Object[] row : listStudentGrades){
                ShCClassStud shCClassStud = new ShCClassStud();
                shCClassStud.setCclassId(row[0].toString());
                shCClassStud.setCsIdnum(row[1].toString());
                shCClassStud.setCsSy(row[2].toString());
                shCClassStud.setCsSem(Integer.parseInt(row[3].toString()));
                shCClassStud.setCsYrLevel(Integer.parseInt(row[4].toString()));
                shCClassStud.setCsCrsCode(row[5].toString());
                shCClassStud.setSubject_desc(row[6].toString());
                shCClassStud.setSubject_unit(row[7].toString());
                shCClassStud.setCsSection(row[8].toString());
                shCClassStud.setCsMidGrade(row[9].toString());
                shCClassStud.setCsSecQtr(row[10].toString());
                shCClassStud.setSubject_fg(row[12].toString());
                shCClassStud.setSubject_type(row[13].toString());
                
                
                list.add(shCClassStud);
            }
        
//            txn.commit();
        }catch(Exception ex){
            
            ex.printStackTrace();
//            txn.rollback();
        }finally{
            session.close(); session.getSessionFactory().close();
        }

        return list;
    }
    
    public ObservableList<ShCClassStud> getResStudentGradesIndividualSummer11(String cs_sem, String cs_idnum) {
        ObservableList<ShCClassStud> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        
        try{
            List<Object[]> listStudentGrades = session.createSQLQuery(
                    "SELECT sh_c_class_stud.`cclass_id`, sh_c_class_stud.cs_idnum AS stud_idnum, sh_c_class_stud.cs_sy, sh_c_class_stud.cs_sem, " +
                    "IFNULL(cs_yr_level,'0') AS cs_yr_level, sh_c_class_stud.`cs_crs_code`, crs_title, crs_unit, sh_c_class_stud.`cs_section`, " +
                    "IFNULL(sh_c_class_student.`cs_mid_grade`,IFNULL(sh_c_class_stud.`cs_mid_grade`,1)) AS grade1, " +
                    "IFNULL(sh_c_class_student.`cs_sec_qtr`,IFNULL(sh_c_class_stud.`cs_sec_qtr`,1)) AS grade2, " +
                    "(IFNULL(sh_c_class_student.`cs_mid_grade`,IFNULL(sh_c_class_stud.`cs_mid_grade`,1))+IFNULL(sh_c_class_student.`cs_sec_qtr`,IFNULL(sh_c_class_stud.`cs_sec_qtr`,1)))/2 AS fg, " +
                    "CEILING((IFNULL(sh_c_class_student.`cs_mid_grade`,IFNULL(sh_c_class_stud.`cs_mid_grade`,1))+IFNULL(sh_c_class_student.`cs_sec_qtr`,IFNULL(sh_c_class_stud.`cs_sec_qtr`,1)))/2) AS fg_round, crs_type " +
                    "FROM `sh_c_class_stud` " +
                    "LEFT JOIN `sh_course_list` ON cs_crs_code = `crs_code` " +
                    "LEFT JOIN sh_c_class_student ON sh_c_class_stud.`cs_idnum` = sh_c_class_student.`cs_idnum` AND sh_c_class_stud.`cs_crs_code` = sh_c_class_student.`cs_crs_code` " +
                    "AND sh_c_class_stud.`cs_section` = sh_c_class_student.`cs_section` " +
                    "WHERE `is_no_grade`=0  AND sh_c_class_stud.cs_idnum = :cs_idnum AND sh_c_class_stud.cs_sem = :cs_sem AND (sh_c_class_stud.cs_yr_level = :cs_yr_level OR sh_c_class_stud.cs_yr_level IS NULL)")
                    .setParameter("cs_sem", cs_sem)
                    .setParameter("cs_idnum", cs_idnum)
                    .setParameter("cs_yr_level", 11)
                    .list();

            txn.commit();

            for(Object[] row : listStudentGrades){
                ShCClassStud shCClassStud = new ShCClassStud();
                shCClassStud.setCclassId(row[0].toString());
                shCClassStud.setCsIdnum(row[1].toString());
                shCClassStud.setCsSy(row[2].toString());
                shCClassStud.setCsSem(Integer.parseInt(row[3].toString()));
                shCClassStud.setCsYrLevel(Integer.parseInt(row[4].toString()));
                shCClassStud.setCsCrsCode(row[5].toString());
                shCClassStud.setSubject_desc(row[6].toString());
                shCClassStud.setSubject_unit(row[7].toString());
                shCClassStud.setCsSection(row[8].toString());
                shCClassStud.setCsMidGrade(row[9].toString());
                shCClassStud.setCsSecQtr(row[10].toString());
                shCClassStud.setSubject_fg(row[12].toString());
                shCClassStud.setSubject_type(row[13].toString());
                
                
                list.add(shCClassStud);
            }
        
//            txn.commit();
        }catch(Exception ex){
            
            ex.printStackTrace();
//            txn.rollback();
        }finally{
            session.close(); session.getSessionFactory().close();
        }

        return list;
    }
    
    public ObservableList<ShCClassStud> getResStudentSubjectList(String cs_sy, String cs_sem, String cs_yr_level, String cs_idnum) {
        ObservableList<ShCClassStud> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        
        String sql ="SELECT cclass_id AS classid,cs_idnum AS stud_idnum,cs_sy AS sy,cs_sem AS sem, cs_yr_level AS grade_level, TRIM(`strandcode`) AS strand_code, "+
        "TRIM(sh_class_info.`strandgroup`) AS strand_group, `cs_section` AS subject_section,`crs_title` AS subject_desc, `cs_crs_code` AS subject_code, `crs_unit` AS subject_unit,stud_section as class_section, " +
        "GROUP_CONCAT(`cla_room`,' - ',`cla_day`,' ',`cla_t_startdesc`,' to ',`cla_t_enddesc` SEPARATOR '\n') AS room_daytime_list  " +
        "FROM `sh_c_class_stud`  " +
        "LEFT JOIN `sh_class_info`   ON  cs_sy = cla_sy AND cs_sem=cla_sem AND `cs_crs_code`=`cla_crs_code` AND cs_section = cla_section " +
        "LEFT JOIN `sh_course_list`  ON `cs_crs_code`=`crs_code` " +
        "LEFT JOIN `strand_section` ON cla_sy =  strand_section.`sy` AND cla_sem = strand_section.`sem` AND cs_yr_level = strand_section.`grade_level` " +
        "                               AND sh_class_info.`strandcode`=strand_section.`strand` AND sh_class_info.`strandgroup`=strand_section.`strandgroup` " +
        "WHERE strandcode IS NOT NULL AND cs_sy= :cs_sy AND cs_sem= :cs_sem AND cs_yr_level=:cs_yr_level AND cs_idnum= :cs_idnum " +
        "GROUP BY subject_code,subject_section " +
        "ORDER BY subject_code,subject_section ";

   
        List<Object[]> listSubject = session.createSQLQuery(sql)
            .setParameter("cs_sy", cs_sy)
            .setParameter("cs_sem", cs_sem)
            .setParameter("cs_yr_level", cs_yr_level)
            .setParameter("cs_idnum", cs_idnum)
            .list();

        txn.commit();
         
        for(Object[] row: listSubject){
                ShCClassStud shCClassStud= new ShCClassStud();   
                shCClassStud.setCclassId(row[0].toString());
                shCClassStud.setCsIdnum(row[1].toString());
                shCClassStud.setCsSy(row[2].toString());
                shCClassStud.setCsSem(Integer.parseInt(row[3].toString()));
                shCClassStud.setCsYrLevel(Integer.parseInt(row[4].toString()));
                shCClassStud.setStrand_code(row[5].toString());
                shCClassStud.setCsStrandgroup(row[6].toString());
                shCClassStud.setCsSection(row[7].toString());
                shCClassStud.setSubject_desc(row[8].toString());
                shCClassStud.setCsCrsCode(row[9].toString());
                shCClassStud.setSubject_unit(row[10].toString());
                shCClassStud.setClass_section(row[11].toString());
                //shCClassStud.setSubject_room_daytime_list((row[12]) == null ? "N/A" : row[12].toString());
                shCClassStud.setSubject_room_daytime_list(row[12].toString());
                list.add(shCClassStud);
        }


        session.close(); session.getSessionFactory().close();
        return list;
    }
    
    
    
    public ObservableList<ShCClassStud> getResStudentScheduleList(String cs_sy, String cs_sem, String cs_yr_level, String cs_idnum) {
        ObservableList<ShCClassStud> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        
        String sql ="SELECT classid,cs_idnum AS stud_idnum,cs_sy AS sy,cs_sem AS sem, cla_yr_level AS subject_gradelevel, TRIM(`strandcode`) AS strand_code, "+
        "TRIM(`strandgroup`) AS strand_group, `cs_section` AS subject_section,`crs_title` AS subject_desc, `cs_crs_code` AS subject_code, `crs_unit` AS subject_unit, " +
        "`cla_room` AS room, `cla_day` AS day_assigned, `cla_t_startdesc` AS time_start,`cla_t_enddesc` AS time_end " +
        "FROM `sh_c_class_stud`  " +
        "LEFT JOIN `sh_class_info`   ON  cs_sy = cla_sy AND cs_sem=cla_sem AND `cs_crs_code`=`cla_crs_code` AND cs_section = cla_section " +
        "LEFT JOIN `sh_course_list`  ON `cs_crs_code`=`crs_code` " +
        "WHERE cs_sy= :cs_sy AND cs_sem= :cs_sem AND cs_yr_level=:cs_yr_level AND cs_idnum= :cs_idnum";
   
        List<Object[]> listSubject = session.createSQLQuery(sql)
            .setParameter("cs_sy", cs_sy)
            .setParameter("cs_sem", cs_sem)
            .setParameter("cs_yr_level", cs_yr_level)
            .setParameter("cs_idnum", cs_idnum)
            .list();

        txn.commit();
         
        for(Object[] row: listSubject){
                ShCClassStud shCClassStud= new ShCClassStud();   
                shCClassStud.setCclassId(row[0].toString());
                shCClassStud.setCsIdnum(row[1].toString());
                shCClassStud.setCsSy(row[2].toString());
                shCClassStud.setCsSem(Integer.parseInt(row[3].toString()));
                shCClassStud.setSubject_gradelevel(row[4].toString());
                shCClassStud.setStrand_code(row[5].toString());
                shCClassStud.setCsStrandgroup(row[6].toString());
                shCClassStud.setCsSection(row[7].toString());
                shCClassStud.setSubject_desc(row[8].toString());
                shCClassStud.setCsCrsCode(row[9].toString());
                shCClassStud.setSubject_unit(row[10].toString());
                shCClassStud.setRoom(row[11].toString());
                shCClassStud.setDay_assigned(row[12].toString());
                shCClassStud.setTime_start(row[13].toString());
                shCClassStud.setTime_end(row[14].toString());
                list.add(shCClassStud);
        }


        session.close(); session.getSessionFactory().close();
        return list;
    }
    
    
    
    public ObservableList<ShCClassStud> getResOfferedSubjectList(String cla_sy, String cla_sem, String cla_yr_level, String strandcode, String strandgroup) {
        ObservableList<ShCClassStud> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        
        String sql ="SELECT classid,'' AS stud_idnum,cla_sy AS sy,cla_sem AS sem, cla_yr_level AS grade_level, TRIM(sh_class_info.`strandcode`) AS strand_code,  " +
"        TRIM(sh_class_info.`strandgroup`) AS strand_group, cla_section AS subject_section,`crs_title` AS subject_desc, cla_crs_code AS subject_code, `crs_unit` AS subject_unit,stud_section AS class_section, " +
"        IFNULL(GROUP_CONCAT(`cla_room`,' - ',`cla_day`,' ',`cla_t_startdesc`,' to ',`cla_t_enddesc` SEPARATOR '\\n'), ' ') AS room_daytime_list    " +
"        FROM `sh_class_info`  " +
"        LEFT JOIN `sh_course_list`  ON cla_crs_code=`crs_code`  " +
"        LEFT JOIN `strand_section` ON cla_sy =  strand_section.`sy` AND cla_sem = strand_section.`sem` AND cla_yr_level = strand_section.`grade_level` " +
"        AND sh_class_info.`strandcode`=strand_section.`strand` AND sh_class_info.`strandgroup`=strand_section.`strandgroup` " +
"        WHERE cla_sy= :cla_sy AND cla_sem= :cla_sem AND cla_yr_level=:cla_yr_level AND strandcode = :strandcode AND sh_class_info.strandgroup = :strandgroup " +
"        GROUP BY subject_code,subject_section " +
"        ORDER BY subject_code,subject_section ";
   
        List<Object[]> listSubject = session.createSQLQuery(sql)
            .setParameter("cla_sy", cla_sy)
            .setParameter("cla_sem", cla_sem)
            .setParameter("cla_yr_level", cla_yr_level)
            .setParameter("strandcode", strandcode)
            .setParameter("strandgroup", strandgroup)
            .list();

        txn.commit();
         
        for(Object[] row: listSubject){
                ShCClassStud shCClassStud= new ShCClassStud();  
                shCClassStud.setClass_info_id(row[0].toString());
                shCClassStud.setCsIdnum(row[1].toString());
                shCClassStud.setCsSy(row[2].toString());
                shCClassStud.setCsSem(Integer.parseInt(row[3].toString()));
                shCClassStud.setCsYrLevel(Integer.parseInt(row[4].toString()));
                shCClassStud.setStrand_code(row[5].toString());
                shCClassStud.setCsStrandgroup(row[6].toString());
                shCClassStud.setCsSection(row[7].toString());
                shCClassStud.setSubject_desc(row[8].toString());
                shCClassStud.setCsCrsCode(row[9].toString());
                shCClassStud.setSubject_unit(row[10].toString());
                shCClassStud.setClass_section(row[11].toString());
                shCClassStud.setSubject_room_daytime_list(row[12].toString());
   
                
                list.add(shCClassStud);
        }
        

        session.close(); session.getSessionFactory().close();
        return list;
    }
    
    
    public boolean saveStudentSubjects(String csSy, String csSem, String csIdnum, ObservableList<ShCClassStud> listStudentSubject) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            String cclassId;
            int i = 0;
            
            Query query = session.createQuery("DELETE FROM ShCClassStud WHERE csSy = :csSy AND csSem = :csSem AND csIdnum = :csIdnum ")
                    .setParameter("csSy", csSy)
                    .setParameter("csSem", Integer.parseInt(csSem))
                    .setParameter("csIdnum", csIdnum);
                    
            query.executeUpdate();
            
            for(ShCClassStud shCClassStud: listStudentSubject){
                if ( i % 20 == 0 ) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                
                session.save(shCClassStud);
                i++; 
            }
           
            txn.commit();
            success = true;
        } catch (Exception e) {
            txn.rollback();
            e.printStackTrace();
            success = false;
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return success;
    }
    
    public boolean saveStudentIndividualSubject(String csSy, String csSem, String csIdnum, ObservableList<ShCClassStud> listStudentSubject) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            String cclassId;
            int i = 0;
            
           
            
            for(ShCClassStud shCClassStud: listStudentSubject){
                if ( i % 20 == 0 ) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                
                session.save(shCClassStud);
                i++; 
            }
           
            txn.commit();
            success = true;
        } catch (Exception e) {
            txn.rollback();
            e.printStackTrace();
            success = false;
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return success;
    }
    
    public boolean saveStudentSubjectsBatch(String csSy, String csSem,String str_csIdnum, ObservableList<ShCClassStud> listStudentSubject, Session session_) {
        boolean success = false;
        String cclassId;
        int i = 0;

        Query query = session_.createQuery("DELETE FROM ShCClassStud WHERE csSy = :csSy AND csSem = :csSem AND csIdnum IN ( " + str_csIdnum + " ) ")
                .setParameter("csSy", csSy)
                .setParameter("csSem", Integer.parseInt(csSem));

        query.executeUpdate();



        for(ShCClassStud shCClassStud: listStudentSubject){
            //flush a batch of inserts and release memory:
            session_.flush();
            session_.clear();
            session_.save(shCClassStud);
            i++;
        }           
        
        return success;
    }
    
    public boolean saveShCClassStud(ShCClassStud shCClassStud){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(shCClassStud);
            txn.commit();
            success = true;
        }catch(Exception ex){
            txn.rollback();
            success = false;
            ex.printStackTrace();
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        return success;
    }

    public boolean deleteShCClassStud(ShCClassStud shCClassStud) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.delete(shCClassStud);
            txn.commit();
            success = true;
        } catch (Exception e) {
            txn.rollback();
            e.printStackTrace();
            success = false;
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return success;
    }
    
    public int getTotalSubjects(String csSy, int csSem, String csIdnum) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        try{        
            long cntSubjects = (long)session.createQuery("SELECT COUNT(*) FROM ShCClassStud WHERE csSy = :csSy AND csSem = :csSem AND csIdnum = :csIdnum")
                    .setParameter("csSy", csSy)
                    .setParameter("csSem", csSem)
                    .setParameter("csIdnum", csIdnum)
                     .uniqueResult();
            txn.commit();

            int cntSubj = (int) cntSubjects;
            return cntSubj;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    
    
    public ObservableList<ShCClassStud> getTotalSubjectCodes(String csSy, int csSem, String csIdnum) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        try{        
            ObservableList<ShCClassStud> list = FXCollections.observableArrayList();
            
            List<ShCClassStud> listSubject  = session.createQuery("FROM ShCClassStud WHERE csSy = :csSy AND csSem = :csSem AND csIdnum = :csIdnum")
                    .setParameter("csSy", csSy)
                    .setParameter("csSem", csSem)
                    .setParameter("csIdnum", csIdnum)
                    .list();
            txn.commit();

            listSubject.stream().forEach(list::add);
            return list; 
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
}
