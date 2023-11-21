/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.CustomEnrolledStudent;
import entity.CustomStudentSubject;
import entity.ShCClassStud;
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
public class CustomStudentSubjectModel {
    private static Session session;
    
    
    
//    public ObservableList<CustomStudentSubject> getResStudentSubjectList(String cs_sy, String cs_sem, String cs_yr_level, String cs_idnum) {
//        ObservableList<CustomStudentSubject> list = FXCollections.observableArrayList();
//        
//        session = HibernateUtil.getSessionFactory().openSession();
//        Transaction txn = session.beginTransaction();
//        
//        String sql ="SELECT classid,cs_idnum AS stud_idnum,cs_sy AS sy,cs_sem AS sem, cs_yr_level AS grade_level, TRIM(`strandcode`) AS strand_code, "+
//        "TRIM(`strandgroup`) AS strand_group, `cs_section` AS subject_section,`crs_title` AS subject_desc, `cs_crs_code` AS subject_code, `crs_unit` AS subject_unit, " +
//        "`cla_room` AS room, `cla_day` AS day_assigned, `cla_t_start` AS time_start,`cla_t_end` AS time_end " +
//        "FROM `sh_c_class_stud`  " +
//        "LEFT JOIN `sh_class_info`   ON  cs_sy = cla_sy AND cs_sem=cla_sem AND `cs_crs_code`=`cla_crs_code` AND cs_section = cla_section " +
//        "LEFT JOIN `sh_course_list`  ON `cs_crs_code`=`crs_code` " +
//        "WHERE cs_sy= :cs_sy AND cs_sem= :cs_sem AND cs_yr_level=:cs_yr_level AND cs_idnum= :cs_idnum";
//   
//        List<Object[]> listSubject = session.createSQLQuery(sql)
//            .setParameter("cs_sy", cs_sy)
//            .setParameter("cs_sem", cs_sem)
//            .setParameter("cs_yr_level", cs_yr_level)
//            .setParameter("cs_idnum", cs_idnum)
//            .list();
//
//        txn.commit();
//         
//        for(Object[] row: listSubject){
//                CustomStudentSubject customStudentSubject= new CustomStudentSubject();   
//                customStudentSubject.setClass_id(row[0].toString());
//                customStudentSubject.setStud_idnum(row[1].toString());
//                customStudentSubject.setSy(row[2].toString());
//                customStudentSubject.setSem(row[3].toString());
//                customStudentSubject.setGrade_level(row[4].toString());
//                customStudentSubject.setStrand_code(row[5].toString());
//                customStudentSubject.setStrand_group(row[6].toString());
//                customStudentSubject.setSubject_section(row[7].toString());
//                customStudentSubject.setSubject_desc(row[8].toString());
//                customStudentSubject.setSubject_code(row[9].toString());
//                customStudentSubject.setSubject_unit(row[10].toString());
//                customStudentSubject.setRoom(row[11].toString());
//                customStudentSubject.setDay_assigned(row[12].toString());
//                customStudentSubject.setTime_start(row[13].toString());
//                customStudentSubject.setTime_end(row[14].toString());
//                list.add(customStudentSubject);
//        }
//
//
//        session.close(); session.getSessionFactory().close();
//        return list;
//    }
    
    
//    public ObservableList<CustomStudentSubject> getResOfferedSubjectList(String cla_sy, String cla_sem, String cla_yr_level, String strandcode, String strandgroup) {
//        ObservableList<CustomStudentSubject> list = FXCollections.observableArrayList();
//        
//        session = HibernateUtil.getSessionFactory().openSession();
//        Transaction txn = session.beginTransaction();
//        
//        String sql ="SELECT classid,'' AS stud_idnum,cla_sy AS sy,cla_sem AS sem, cla_yr_level AS grade_level, TRIM(sh_class_info.`strandcode`) AS strand_code,  " +
//"        TRIM(`strandgroup`) AS strand_group, cla_section AS subject_section,`crs_title` AS subject_desc, cla_crs_code AS subject_code, `crs_unit` AS subject_unit,   " +
//"        `cla_room` AS room, `cla_day` AS day_assigned, `cla_t_start` AS time_start,`cla_t_end` AS time_end   " +
//"        FROM `sh_class_info`  " +
//"        LEFT JOIN `sh_course_list`  ON cla_crs_code=`crs_code`  " +
//        "WHERE cla_sy= :cla_sy AND cla_sem= :cla_sem AND cla_yr_level=:cla_yr_level AND strandcode = :strandcode AND strandgroup = :strandgroup ";
//   
//        List<Object[]> listSubject = session.createSQLQuery(sql)
//            .setParameter("cla_sy", cla_sy)
//            .setParameter("cla_sem", cla_sem)
//            .setParameter("cla_yr_level", cla_yr_level)
//            .setParameter("strandcode", strandcode)
//            .setParameter("strandgroup", strandgroup)
//            .list();
//
//        txn.commit();
//         
//        for(Object[] row: listSubject){
//                CustomStudentSubject customStudentSubject= new CustomStudentSubject();  
//                customStudentSubject.setClass_id(row[0].toString());
//                customStudentSubject.setStud_idnum(row[1].toString());
//                customStudentSubject.setSy(row[2].toString());
//                customStudentSubject.setSem(row[3].toString());
//                customStudentSubject.setGrade_level(row[4].toString());
//                customStudentSubject.setStrand_code(row[5].toString());
//                customStudentSubject.setStrand_group(row[6].toString());
//                customStudentSubject.setSubject_section(row[7].toString());
//                customStudentSubject.setSubject_desc(row[8].toString());
//                customStudentSubject.setSubject_code(row[9].toString());
//                customStudentSubject.setSubject_unit(row[10].toString());
//                customStudentSubject.setRoom(row[11].toString());
//                customStudentSubject.setDay_assigned(row[12].toString());
//                customStudentSubject.setTime_start(row[13].toString());
//                customStudentSubject.setTime_end(row[14].toString());
//                list.add(customStudentSubject);
//        }
//        
//
//        session.close(); session.getSessionFactory().close();
//        return list;
//    }
    
//    public boolean saveStudentSubjectsBatch(String csSy, String csSem, String csIdnum, ObservableList<CustomStudentSubject> listStudentSubject) {
//        session = HibernateUtil.getSessionFactory().openSession();
//        Transaction txn = session.beginTransaction();
//        boolean success = false;
//        try{
//            String cclassId;
//            int i = 0;
//            
//            Query query = session.createQuery("DELETE FROM ShCClassStud WHERE csSy = :csSy AND csSem = :csSem AND csIdnum = :csIdnum ")
//                    .setParameter("csSy", csSy)
//                    .setParameter("csSem", Integer.parseInt(csSem))
//                    .setParameter("csIdnum", csIdnum);
//                    
//            query.executeUpdate();
//            
//            for(CustomStudentSubject customStudentSubject: listStudentSubject){
//                if ( i % 20 == 0 ) { //20, same as the JDBC batch size
//                    //flush a batch of inserts and release memory:
//                    session.flush();
//                    session.clear();
//                }
//                cclassId = customStudentSubject.getClass_id() + "_" + csIdnum;
//                
//                ShCClassStud save_shCClassStud = new ShCClassStud();
//                save_shCClassStud.setCclassId(cclassId);
//                save_shCClassStud.setCsCrsCode(customStudentSubject.getSubject_code());
//                save_shCClassStud.setCsIdnum(csIdnum);
//                save_shCClassStud.setCsSection(customStudentSubject.getSubject_section());
//                save_shCClassStud.setCsSy(csSy);
//                save_shCClassStud.setCsSem(Integer.parseInt(csSem));
//                save_shCClassStud.setCsStrandgroup(customStudentSubject.getStrand_group());
//                save_shCClassStud.setCsYrLevel(Integer.parseInt(customStudentSubject.getGrade_level()));
//
//                session.save(save_shCClassStud);
//                i++; 
//            }
//           
//            txn.commit();
//            success = true;
//        } catch (Exception e) {
//            txn.rollback();
//            e.printStackTrace();
//            success = false;
//        } finally {
//            session.close(); session.getSessionFactory().close();   
//        }
//        return success;
//    }
    
}
