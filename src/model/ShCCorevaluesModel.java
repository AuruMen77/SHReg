/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShCCorevalues;
import java.math.BigDecimal;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author CITS-Sheng
 */
public class ShCCorevaluesModel {
    private static Session session;
    
//    public ObservableList<ShCCorevalues> getResStudCoreValues(){
//        session = HibernateUtil.getSessionFactory().openSession();
//        ObservableList<ShCCorevalues> list = FXCollections.observableArrayList();
//        Transaction txn = session.beginTransaction();
//        List<ShCCorevalues> listStudentCoreValues = session.createQuery("FROM ShCCorevalues WHERE cvSy = :cvSy "+
//                " AND cvSem = :cvSem AND cvIdnum = :cvIdnum AND CoreValues.cvId = :cvId ").list();
//        txn.commit();
//
//        listStudentCoreValues.stream().forEach(list::add);
////        for(ShCCorevalues row:listStudentCoreValues){
////            list.add(row);
////        }
////        
//////        Statistics stats = session.getSessionFactory().getStatistics();
//////        stats.setStatisticsEnabled(true);
//////        stats.getSessionOpenCount();
////// 
//////        stats.logSummary();
//        return list; 
//    }
    
    public ObservableList<ShCCorevalues> getResStudentCoreValues(String cvSy, String cvYrlevel, String cvIdnum){
        ObservableList<ShCCorevalues> list = FXCollections.observableArrayList();
        session = HibernateUtil.getSessionFactory().openSession();        
        try{
            /*
            Transaction txn = session.beginTransaction();
            List<ShCCorevalues> listShCCorevalues = session.createQuery("FROM ShCCorevalues WHERE cvSy = :cvSy "+
                    "AND cvYrlevel = :cvYrlevel AND cvIdnum = :cvIdnum   ")
                    .setParameter("cvSy", cvSy)
                    .setParameter("cvYrlevel", Integer.parseInt(cvYrlevel))
                    .setParameter("cvIdnum", cvIdnum)
                .list();
            
            for(ShCCorevalues file : listShCCorevalues){
                Hibernate.initialize(file.getCoreValues());
            }
            
            txn.commit();
            listShCCorevalues.stream().forEach(list::add);
            */
            Transaction txn = session.beginTransaction();
            List<String[]> listShCCorevalues = session.createSQLQuery(
                "SELECT cv_classid, cv_idnum, sh_c_corevalues.f_cv_id, grade1, grade2, b.grade3, b.grade4, description, cv_sy, cv_sem, cv_strand, cv_strandgroup, cv_yrlevel, sh_c_corevalues.ts " +
                "FROM sh_c_corevalues " +
                "LEFT JOIN (" +
                "	SELECT grade1 AS grade3, grade2 AS grade4, f_cv_id " +
                "	FROM sh_c_corevalues " +
                "	WHERE cv_sy = :cv_sy AND cv_yrlevel = :cv_yrlevel AND cv_idnum = :cv_idnum AND cv_sem = '2' " +
                "	) b ON b.f_cv_id = sh_c_corevalues.`f_cv_id` " +
                "LEFT JOIN core_values ON core_values.`cv_id` = sh_c_corevalues.`f_cv_id` " +
                "WHERE cv_sy = :cv_sy AND cv_yrlevel = :cv_yrlevel AND cv_idnum = :cv_idnum AND cv_sem = '1' AND core_values.`core_status` = '1' ; ")
            .setParameter("cv_sy", cvSy)
            .setParameter("cv_yrlevel", cvYrlevel)    
            .setParameter("cv_idnum", cvIdnum) 
            .list();
            
            txn.commit();
            
            
            
            cvSy = cvSy == null ? "2020-2021" : cvSy.trim(); //Temporary codes for hiding core values until core values are submitted again
            if(cvSy.equals("2020-2021") || cvSy.equals("2021-2022")){
                for(Object[] row : listShCCorevalues){
                    ShCCorevalues shCCorevalues = new ShCCorevalues();
                    shCCorevalues.setGrade1("-");
                    shCCorevalues.setGrade2("-");
                    shCCorevalues.setGrade3("-");
                    shCCorevalues.setGrade4("-");
                    shCCorevalues.setCvStrand((row[7] == null) ? "N/A" : row[7].toString());

                    list.add(shCCorevalues);
                }
            }
            else{
                for(Object[] row : listShCCorevalues){
                    ShCCorevalues shCCorevalues = new ShCCorevalues();
                    shCCorevalues.setGrade1((row[3] == null) ? "N/A" : row[3].toString());
                    shCCorevalues.setGrade2((row[4] == null) ? "N/A" : row[4].toString());
                    shCCorevalues.setGrade3((row[5] == null) ? "N/A" : row[5].toString());
                    shCCorevalues.setGrade4((row[6] == null) ? "N/A" : row[6].toString());
                    shCCorevalues.setCvStrand((row[7] == null) ? "N/A" : row[7].toString());

                    list.add(shCCorevalues);
                }
            }
            
            
            return list;
            
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    
    public ObservableList<String[]> getResStudentCoreValuesConcat(String ss_sy, String ss_yr_level, 
            String strand_code, String strand_group){
        session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<String[]> list = FXCollections.observableArrayList();
        try{
            Transaction txn = session.beginTransaction();
            List<String[]> listStudentCoreValues = session.createSQLQuery("SELECT `stud_idnum`,student, " +
            "GROUP_CONCAT(  " +
                            "IF( " +
                                "f_cv_id IS NULL, " +
                                "CONCAT(cv_id,'-0-0'), " +
                                "CONCAT(f_cv_id,'-',IF(grade1='','0',grade1),'-',IF(grade2='','0',grade2)) " +
                            ") ORDER BY cv_id ASC " +
                        ") AS core_values  " +
            "FROM " +
            "( " +
                "SELECT sh_stud_strand.`stud_idnum`, CONCAT(TRIM(stud_lname),', ',TRIM(stud_fname)) AS student, " +
                "ss_sy,ss_yr_level,strand_code,strand_group " +
                "FROM `sh_stud_strand`   " +
                "LEFT JOIN sh_studlist ON sh_stud_strand.`stud_idnum` = sh_studlist.`stud_idnum` AND ss_sy=sy AND ss_sem=sem  " +
                "WHERE ss_sy = :ss_sy AND ss_yr_level = :ss_yr_level AND strand_code = :strand_code AND strand_group = :strand_group " +
                "GROUP BY sh_stud_strand.`stud_idnum` " +
            ") tbl_student " +
            "LEFT JOIN `core_values` ON `cv_id` > 0 " +
            "LEFT JOIN `sh_c_corevalues` ON ss_sy=cv_sy AND `stud_idnum`=cv_idnum AND `cv_id` = f_cv_id " +
            "GROUP BY `stud_idnum`")
            .setParameter("ss_sy", ss_sy)
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
    
    public boolean saveCoreValuesBatch(String cvSy, String cvSem, String cvYrlevel,
            String cvStrand, String cvStrandgroup, ObservableList<ShCCorevalues> listShCCorevalues) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            //--- START delete all corevalues in a section ---// 
            session.createQuery("DELETE ShCCorevalues WHERE cvSy = :cvSy AND cvSem = :cvSem "
                    + "AND cvYrlevel = :cvYrlevel AND cvStrand = :cvStrand AND cvStrandgroup = :cvStrandgroup  ")
                    .setParameter("cvSy", cvSy)
                    .setParameter("cvSem", cvSem)
                    .setParameter("cvYrlevel", Integer.parseInt(cvYrlevel))
                    .setParameter("cvStrand", cvStrand)
                    .setParameter("cvStrandgroup", cvStrandgroup)
                    .executeUpdate();
            
            //--- END delete all corevalues in a section ---//
            
            //--- START batch insert ---// 
            String setClassid;
            int i = 0;
            for(ShCCorevalues shCCorevalues:listShCCorevalues){
                
                if ( i % 20 == 0 ) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                
                session.save(shCCorevalues);
                i++;
            }
            //--- END batch insert ---// 
            
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
    
}
