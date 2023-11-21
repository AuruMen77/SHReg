/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.CustomEnrolledStudent;
import entity.ShApplicant;
import entity.ShTermReg;
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
public class CustomEnrolledStudentModel {
    private static Session session;
    
    public ObservableList<CustomEnrolledStudent> getResEnrolledStudentForPromotion(String sy, String sem, String grade_level, String strand, String strandgroup, String sy_new, String sem_new, String grade_level_new, String strand_new, String strandgroup_new){

     
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        ObservableList<CustomEnrolledStudent> list = FXCollections.observableArrayList();
        try{
       
        
            String sql ="SELECT sh_stud_strand.`stud_idnum`,ss_sy AS sy,ss_sem AS sem,ss_yr_level AS grade_level, " +
                        "IFNULL(TRIM(`strand_code`),'') AS strand_code,IFNULL(TRIM(`strand_group`),'') AS strand_group,IFNULL(stud_section,'') AS section, " +
                        "IFNULL(CONCAT(TRIM(`stud_lname`),', ',TRIM(`stud_fname`),' ',TRIM(`stud_mi`)),'') AS stud_fullname,  " +
                        "IFNULL(TRIM(`stud_lname`),'') AS stud_lname, IFNULL(TRIM(`stud_fname`),'') AS stud_fname, " +
                        "IFNULL(TRIM(`stud_mi`),'') AS stud_mi, IFNULL(TRIM(`stud_suffix`),'') AS stud_suffix " +
                        "FROM `sh_stud_strand` " +
                        "LEFT JOIN `sh_studlist`    ON ss_sy=sh_studlist.sy AND ss_sem=sh_studlist.sem AND sh_stud_strand.`stud_idnum`=sh_studlist.`stud_idnum` " +
                        "LEFT JOIN `strand_section` ON ss_sy=strand_section.`sy` AND ss_sem=strand_section.`sem` AND ss_yr_level=strand_section.`grade_level` AND  " +
                        "			   TRIM(sh_stud_strand.`strand_code`)=strand_section.`strand` AND TRIM(sh_stud_strand.`strand_group`)=strand_section.`strandgroup` " +
                        "WHERE ss_sy = ?1 AND ss_sem = ?2 ";

            String col = "", col2 = "";
            int ctr = 3;
            List<String> listParameter = new ArrayList<>();

            if(!grade_level.equals("all")){
                col = "?" + ctr;
                sql += " AND ss_yr_level = " + col;
                listParameter.add(grade_level);
                ctr++;
            }
            
            if(!strand.equals("all")){
                col = "?" + ctr;
                sql += " AND TRIM(sh_stud_strand.`strand_code`) = " + col;
                listParameter.add(strand);
                ctr++;
            }
            
            if(!strandgroup.equals("all")){
                col = "?" + ctr;
                sql += " AND TRIM(sh_stud_strand.`strand_group`) = " + col;
                listParameter.add(strandgroup);
                ctr++;
            }
            
            if(sy_new != ""){
                String sql2=" AND sh_stud_strand.stud_idnum NOT IN (" +
                            "SELECT sh_stud_strand.`stud_idnum`" +
                            "FROM `sh_stud_strand` " +
                            "LEFT JOIN `sh_studlist` ON ss_sy=sh_studlist.sy AND ss_sem=sh_studlist.sem AND sh_stud_strand.`stud_idnum`=sh_studlist.`stud_idnum` " +
                            "LEFT JOIN `strand_section` ON ss_sy=strand_section.`sy` AND ss_sem=strand_section.`sem` AND ss_yr_level=strand_section.`grade_level` AND  " +
                            "TRIM(sh_stud_strand.`strand_code`)=strand_section.`strand` AND TRIM(sh_stud_strand.`strand_group`)=strand_section.`strandgroup` " +
                            "WHERE";

                col = "?" + ctr;
                sql2 += " ss_sy = " + col;
                listParameter.add(sy_new);
                ctr++;
                
                col = "?" + ctr;
                sql2 += " AND ss_sem = " + col;
                listParameter.add(sem_new);
                ctr++;

                if(!grade_level.equals("all")){
                    col = "?" + ctr;
                    sql2 += " AND ss_yr_level = " + col;
                    listParameter.add(grade_level_new);
                    ctr++;
                }
                
                if(!strand.equals("all")){
                    col = "?" + ctr;
                    sql2 += " AND TRIM(sh_stud_strand.`strand_code`) = " + col;
                    listParameter.add(strand_new);
                    ctr++;
                } 
                
                if(!strandgroup.equals("all")){
                    col = "?" + ctr;
                    sql2 += " AND TRIM(sh_stud_strand.`strand_group`) = " + col;
                    listParameter.add(strandgroup_new);
                    ctr++;
                }
                
                sql2 += ");";
                sql += sql2;
                
            }
            Query psmt  = session.createSQLQuery(sql);
            psmt.setParameter(1, sy);
            psmt.setParameter(2, sem);

            ctr = 3;
            Iterator iterator = listParameter.iterator();
            while(iterator.hasNext()) {
                psmt.setParameter(ctr, iterator.next());
                ctr++;
            }

            List<Object[]> listEnrolledStudent = psmt.list();

            txn.commit();

            for(Object[] row: listEnrolledStudent){
                
                
                CustomEnrolledStudent customEnrolledStudent= new CustomEnrolledStudent();       
                customEnrolledStudent.setStud_idnum(row[0].toString());
                customEnrolledStudent.setSy(row[1].toString());
                customEnrolledStudent.setSem(row[2].toString()); 
                customEnrolledStudent.setGrade_level(row[3].toString()); 
                customEnrolledStudent.setStrand_code(row[4].toString());
                customEnrolledStudent.setStrand_group(row[5].toString());
                customEnrolledStudent.setSection(row[6].toString());
                customEnrolledStudent.setStud_fullname(row[7].toString());
                customEnrolledStudent.setStud_lname(row[8].toString());
                customEnrolledStudent.setStud_fname(row[9].toString());
                customEnrolledStudent.setStud_mi(row[10].toString());
                customEnrolledStudent.setStud_suffix(row[11].toString());
                list.add(customEnrolledStudent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return list; 
    }
    
    public ObservableList<CustomEnrolledStudent> getResEnrolledStudentWithSearch(String sy, String sem, String grade_level, String strand, String section, String search){
        
      
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        ObservableList<CustomEnrolledStudent> list = FXCollections.observableArrayList();
        try{
       
        
            String sql ="SELECT sh_stud_strand.`stud_idnum`,ss_sy AS sy,ss_sem AS sem,ss_yr_level AS grade_level, " +
                        "IFNULL(TRIM(`strand_code`),'') AS strand_code,IFNULL(TRIM(`strand_group`),'') AS strand_group,IFNULL(stud_section,'') AS section, " +
                        "IFNULL(CONCAT(TRIM(`stud_lname`),', ',TRIM(`stud_fname`),' ',TRIM(`stud_mi`)),'') AS stud_fullname,  " +
                        "IFNULL(TRIM(`stud_lname`),'') AS stud_lname, IFNULL(TRIM(`stud_fname`),'') AS stud_fname, " +
                        "IFNULL(TRIM(`stud_mi`),'') AS stud_mi, IFNULL(TRIM(`stud_suffix`),'') AS stud_suffix " +
                        "FROM `sh_stud_strand` " +
                        "LEFT JOIN `sh_studlist`    ON ss_sy=sh_studlist.sy AND ss_sem=sh_studlist.sem AND sh_stud_strand.`stud_idnum`=sh_studlist.`stud_idnum` " +
                        "LEFT JOIN `strand_section` ON ss_sy=strand_section.`sy` AND ss_sem=strand_section.`sem` AND ss_yr_level=strand_section.`grade_level` AND  " +
                        "			   TRIM(sh_stud_strand.`strand_code`)=strand_section.`strand` AND TRIM(sh_stud_strand.`strand_group`)=strand_section.`strandgroup` " +
                        "WHERE ss_sy = ?1 AND ss_sem = ?2 ";

            String col = "", col2 = "";
            int ctr = 3;
            List<String> listParameter = new ArrayList<>();

            if(!grade_level.equals("all")){
                col = "?" + ctr;
                sql += " AND ss_yr_level = " + col;
                listParameter.add(grade_level);
                ctr++;
            }
            
            if(!strand.equals("all")){
                col = "?" + ctr;
                sql += " AND TRIM(sh_stud_strand.`strand_code`) = " + col;
                listParameter.add(strand);
                ctr++;
            }
            
            if(!section.equals("all")){
                col = "?" + ctr;
                sql += " AND TRIM(strand_section.`stud_section`) = " + col;
                listParameter.add(section);
                ctr++;
            }
            
            if(!search.equals("")){
                col = "?" + ctr;
                ctr++;
                col2 = "?" + ctr;
                sql += " AND (CONCAT(TRIM(`stud_lname`),', ',TRIM(`stud_fname`),' ',TRIM(`stud_mi`)) LIKE " +col+" OR  sh_stud_strand.`stud_idnum` LIKE "+col2+") ";
                listParameter.add("%" +search +"%");
                listParameter.add("%" +search +"%");
                ctr++;
            }


            Query psmt  = session.createSQLQuery(sql);
            psmt.setParameter(1, sy);
            psmt.setParameter(2, sem);

            ctr = 3;
            Iterator iterator = listParameter.iterator();
            while(iterator.hasNext()) {
                psmt.setParameter(ctr, iterator.next());
                ctr++;
            }

            List<Object[]> listEnrolledStudent = psmt.list();

            txn.commit();

            for(Object[] row: listEnrolledStudent){
                
                CustomEnrolledStudent customEnrolledStudent= new CustomEnrolledStudent();       
                customEnrolledStudent.setStud_idnum(row[0].toString());
                customEnrolledStudent.setSy(row[1].toString());
                customEnrolledStudent.setSem(row[2].toString()); 
                customEnrolledStudent.setGrade_level(row[3].toString()); 
                customEnrolledStudent.setStrand_code(row[4].toString());
                customEnrolledStudent.setStrand_group(row[5].toString());
                customEnrolledStudent.setSection(row[6].toString());
                customEnrolledStudent.setStud_fullname(row[7].toString());
                customEnrolledStudent.setStud_lname(row[8].toString());
                customEnrolledStudent.setStud_fname(row[9].toString());
                customEnrolledStudent.setStud_mi(row[10].toString());
                customEnrolledStudent.setStud_suffix(row[11].toString());
                list.add(customEnrolledStudent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return list; 
    }
    
    
    
    public ObservableList<CustomEnrolledStudent> getResStudentsToPromoteWithSearch(String sy, String sem, String grade_level, String strand, String section, String search){
        
      
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        ObservableList<CustomEnrolledStudent> list = FXCollections.observableArrayList();
        
        ShTermReg current_enrollment = new ShTermRegModel().getRowCurrentEnrollment();
        try{
       
        
            String sql ="SELECT sh_stud_strand.`stud_idnum`,ss_sy AS sy,ss_sem AS sem,ss_yr_level AS grade_level, " +
                        "IFNULL(TRIM(`strand_code`),'') AS strand_code,IFNULL(TRIM(`strand_group`),'') AS strand_group,IFNULL(stud_section,'') AS section, " +
                        "IFNULL(CONCAT(TRIM(`stud_lname`),', ',TRIM(`stud_fname`),' ',TRIM(`stud_mi`)),'') AS stud_fullname,  " +
                        "IFNULL(TRIM(`stud_lname`),'') AS stud_lname, IFNULL(TRIM(`stud_fname`),'') AS stud_fname, " +
                        "IFNULL(TRIM(`stud_mi`),'') AS stud_mi, IFNULL(TRIM(`stud_suffix`),'') AS stud_suffix " +
                        "FROM `sh_stud_strand` " +
                        "LEFT JOIN `sh_studlist`    ON ss_sy=sh_studlist.sy AND ss_sem=sh_studlist.sem AND sh_stud_strand.`stud_idnum`=sh_studlist.`stud_idnum` " +
                        "LEFT JOIN `strand_section` ON ss_sy=strand_section.`sy` AND ss_sem=strand_section.`sem` AND ss_yr_level=strand_section.`grade_level` AND  " +
                        "			   TRIM(sh_stud_strand.`strand_code`)=strand_section.`strand` AND TRIM(sh_stud_strand.`strand_group`)=strand_section.`strandgroup` " +
                        "WHERE ss_sy = ?1 AND ss_sem = ?2 AND sh_stud_strand.`stud_idnum` NOT IN (SELECT `stud_idnum` FROM sh_stud_strand WHERE ss_sy = ?3 AND ss_sem = ?4) ";

            String col = "", col2 = "";
            int ctr = 5;
            List<String> listParameter = new ArrayList<>();

            if(!grade_level.equals("all")){
                col = "?" + ctr;
                sql += " AND ss_yr_level = " + col;
                listParameter.add(grade_level);
                ctr++;
            }
            
            if(!strand.equals("all")){
                col = "?" + ctr;
                sql += " AND TRIM(sh_stud_strand.`strand_code`) = " + col;
                listParameter.add(strand);
                ctr++;
            }
            
            if(!section.equals("all")){
                col = "?" + ctr;
                sql += " AND TRIM(strand_section.`stud_section`) = " + col;
                listParameter.add(section);
                ctr++;
            }
            
            if(!search.equals("")){
                col = "?" + ctr;
                ctr++;
                col2 = "?" + ctr;
                sql += " AND (CONCAT(TRIM(`stud_lname`),', ',TRIM(`stud_fname`),' ',TRIM(`stud_mi`)) LIKE " +col+" OR  sh_stud_strand.`stud_idnum` LIKE "+col2+") ";
                listParameter.add("%" +search +"%");
                listParameter.add("%" +search +"%");
                ctr++;
            }

            Query psmt  = session.createSQLQuery(sql);
            psmt.setParameter(1, sy);
            psmt.setParameter(2, sem);
            psmt.setParameter(3, current_enrollment.getSyReg());
            psmt.setParameter(4, current_enrollment.getSemReg().toString());

            ctr = 5;
            Iterator iterator = listParameter.iterator();
            while(iterator.hasNext()) {
                psmt.setParameter(ctr, iterator.next());
                ctr++;
            }

            List<Object[]> listEnrolledStudent = psmt.list();

            txn.commit();

            for(Object[] row: listEnrolledStudent){
                
                CustomEnrolledStudent customEnrolledStudent= new CustomEnrolledStudent();       
                customEnrolledStudent.setStud_idnum(row[0].toString());
                customEnrolledStudent.setSy(row[1].toString());
                customEnrolledStudent.setSem(row[2].toString()); 
                customEnrolledStudent.setGrade_level(row[3].toString()); 
                customEnrolledStudent.setStrand_code(row[4].toString());
                customEnrolledStudent.setStrand_group(row[5].toString());
                customEnrolledStudent.setSection(row[6].toString());
                customEnrolledStudent.setStud_fullname(row[7].toString());
                customEnrolledStudent.setStud_lname(row[8].toString());
                customEnrolledStudent.setStud_fname(row[9].toString());
                customEnrolledStudent.setStud_mi(row[10].toString());
                customEnrolledStudent.setStud_suffix(row[11].toString());
                list.add(customEnrolledStudent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return list; 
    }
    
    public ObservableList<CustomEnrolledStudent> getResEnrolledStudent(String ss_sy, String ss_sem, String ss_yr_level, String strand_code, String strand_group){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        ObservableList<CustomEnrolledStudent> list = FXCollections.observableArrayList();
        try{
       
        
            String sql ="SELECT sh_stud_strand.`stud_idnum`,ss_sy AS sy,ss_sem AS sem,ss_yr_level AS grade_level, " +
                        "TRIM(`strand_code`) AS strand_code,TRIM(`strand_group`) AS strand_group,stud_section AS section, " +
                        "CONCAT(TRIM(`stud_lname`),', ',TRIM(`stud_fname`),' ',TRIM(`stud_mi`)) AS stud_fullname, TRIM(`stud_lname`) AS stud_lname, " +
                        "TRIM(`stud_fname`) AS stud_fname, IFNULL(TRIM(`stud_suffix`),'') AS stud_suffix, IFNULL(TRIM(`stud_mi`),'') AS stud_mi,TRIM(sh_applicant.app_no) AS app_no " +
                        "FROM `sh_stud_strand` " +
                        "LEFT JOIN sh_applicant ON sh_stud_strand.stud_idnum = sh_applicant.stud_idnum " +
                        "LEFT JOIN `sh_studlist`    ON ss_sy=sh_studlist.sy AND ss_sem=sh_studlist.sem AND sh_stud_strand.`stud_idnum`=sh_studlist.`stud_idnum` " +
                        "LEFT JOIN `strand_section` ON ss_sy=strand_section.`sy` AND ss_sem=strand_section.`sem` AND ss_yr_level=strand_section.`grade_level` AND  " +
                        "TRIM(sh_stud_strand.`strand_code`)=strand_section.`strand` AND TRIM(sh_stud_strand.`strand_group`)=strand_section.`strandgroup` " +
                        "WHERE ss_sy = :ss_sy AND ss_sem = :ss_sem AND ss_yr_level = :ss_yr_level AND TRIM(sh_stud_strand.`strand_code`) = :strand_code " +
                        "AND TRIM(sh_stud_strand.`strand_group`) = :strand_group ";


            Query psmt  = session.createSQLQuery(sql);
            psmt.setParameter("ss_sy", ss_sy)
                    .setParameter("ss_sem", ss_sem)
                    .setParameter("ss_yr_level", ss_yr_level)
                    .setParameter("strand_code", strand_code)
                    .setParameter("strand_group", strand_group);

            List<Object[]> listEnrolledStudent = psmt.list();

            txn.commit();

            for(Object[] row: listEnrolledStudent){
                CustomEnrolledStudent customEnrolledStudent= new CustomEnrolledStudent();       
                customEnrolledStudent.setStud_idnum(row[0].toString());
                customEnrolledStudent.setSy(row[1].toString());
                customEnrolledStudent.setSem(row[2].toString()); 
                customEnrolledStudent.setGrade_level(row[3].toString()); 
                customEnrolledStudent.setStrand_code(row[4].toString());
                customEnrolledStudent.setStrand_group(row[5].toString());
                customEnrolledStudent.setSection(row[6].toString());
                customEnrolledStudent.setStud_fullname(row[7].toString());
                customEnrolledStudent.setStud_lname(row[8].toString());
                customEnrolledStudent.setStud_fname(row[9].toString());
                customEnrolledStudent.setStud_suffix(row[10].toString());
                customEnrolledStudent.setStud_mi(row[11].toString());
                customEnrolledStudent.setApp_no(row[12].toString());
                list.add(customEnrolledStudent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return list; 
    }
    
    public CustomEnrolledStudent getRowEnrolledStudent(String sy, String sem,String stud_idnum){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        try{
           
            String sql ="SELECT sh_stud_strand.`stud_idnum`,ss_sy AS sy,ss_sem AS sem,ss_yr_level AS grade_level, " +
                        "TRIM(`strand_code`) AS strand_code,TRIM(`strand_group`) AS strand_group,stud_section AS section, " +
                        "CONCAT(TRIM(`stud_lname`),', ',TRIM(`stud_fname`),' ',TRIM(`stud_mi`)) AS stud_fullname " +
                        "FROM `sh_stud_strand` " +
                        "LEFT JOIN `sh_studlist`    ON ss_sy=sh_studlist.sy AND ss_sem=sh_studlist.sem AND sh_stud_strand.`stud_idnum`=sh_studlist.`stud_idnum` " +
                        "LEFT JOIN `strand_section` ON ss_sy=strand_section.`sy` AND ss_sem=strand_section.`sem` AND ss_yr_level=strand_section.`grade_level` AND  " +
                        "			   TRIM(sh_stud_strand.`strand_code`)=strand_section.`strand` AND TRIM(sh_stud_strand.`strand_group`)=strand_section.`strandgroup` " +
                        "WHERE ss_sy = ? AND ss_sem = ? AND sh_stud_strand.stud_idnum = ?";
            
            
            Query psmt  = session.createSQLQuery(sql)
                                                 .setParameter(1, sy)
                                                 .setParameter(2, sem)
                                                 .setParameter(3, stud_idnum);
             
            List<Object[]> listEnrolledStudent = psmt.list();
                                                
            txn.commit();
            
            CustomEnrolledStudent customEnrolledStudent= new CustomEnrolledStudent();    
            for(Object[] row: listEnrolledStudent){  
                customEnrolledStudent.setStud_idnum(row[0].toString());
                customEnrolledStudent.setSy(row[1].toString());
                customEnrolledStudent.setSem(row[2].toString()); 
                customEnrolledStudent.setGrade_level(row[3].toString()); 
                customEnrolledStudent.setStrand_code(row[4].toString());
                customEnrolledStudent.setStrand_group(row[5].toString());
                customEnrolledStudent.setSection(row[6] == null ? " " : row[6].toString());
                customEnrolledStudent.setStud_fullname(row[7].toString());
            }
       
            return customEnrolledStudent;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public CustomEnrolledStudent getRowEnrolledStudentByYearAndSem(String ss_yr_level, String ss_sem, String stud_idnum) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        try{
         
            String sql ="SELECT sh_stud_strand.`stud_idnum`,ss_sy AS sy,ss_sem AS sem,ss_yr_level AS grade_level, " +
                        "TRIM(sh_stud_strand.`strand_code`) AS strand_code,TRIM(`strand_group`) AS strand_group,IFNULL(stud_section, '') AS section, " +
                        "CONCAT(TRIM(`stud_lname`),', ',TRIM(`stud_fname`),' ',TRIM(`stud_mi`)) AS stud_fullname,ss_status,strands.`strand` " +
                        "FROM `sh_stud_strand` " +
                        "LEFT JOIN `sh_studlist`    ON ss_sy=sh_studlist.sy AND ss_sem=sh_studlist.sem AND sh_stud_strand.`stud_idnum`=sh_studlist.`stud_idnum` " +
                        "LEFT JOIN `strand_section` ON ss_sy=strand_section.`sy` AND ss_sem=strand_section.`sem` AND ss_yr_level=strand_section.`grade_level` AND  " +
                        "			   TRIM(sh_stud_strand.`strand_code`)=strand_section.`strand` AND TRIM(sh_stud_strand.`strand_group`)=strand_section.`strandgroup` " +
                        "LEFT JOIN `strands` ON sh_stud_strand.`strand_code`=strands.`strand_code` " +
                        "WHERE ss_yr_level = ? AND ss_sem = ? AND sh_stud_strand.stud_idnum = ?";
            

            Query psmt  = session.createSQLQuery(sql)
                                                 .setParameter(1, ss_yr_level)
                                                 .setParameter(2, ss_sem)
                                                 .setParameter(3, stud_idnum);
             
            List<Object[]> listEnrolledStudent = psmt.list();
                                                
            txn.commit();
            
            CustomEnrolledStudent customEnrolledStudent= new CustomEnrolledStudent();    
            for(Object[] row: listEnrolledStudent){  
                customEnrolledStudent.setStud_idnum(row[0].toString());
                customEnrolledStudent.setSy(row[1].toString());
                customEnrolledStudent.setSem(row[2].toString()); 
                customEnrolledStudent.setGrade_level(row[3].toString()); 
                customEnrolledStudent.setStrand_code(row[4].toString());
                customEnrolledStudent.setStrand_group(row[5].toString());
                customEnrolledStudent.setSection(row[6].toString());
                customEnrolledStudent.setStud_fullname(row[7].toString());
                customEnrolledStudent.setStud_status(row[8].toString());
                customEnrolledStudent.setStrand_code_desc(row[9].toString());
            }
       
            return customEnrolledStudent;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
}
