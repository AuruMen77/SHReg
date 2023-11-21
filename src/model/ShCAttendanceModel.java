/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.CustomEnrolledStudent;
import entity.CustomStudentSubject;
import entity.ShCAttendance;
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
public class ShCAttendanceModel {
    private static Session session;
    
    
    
    public ObservableList<ShCAttendance> getResStudentAttendance(String ss_sy, String ss_sem, String ss_yr_level, 
            String strand_code, String strand_group){
 
        session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<ShCAttendance> list = FXCollections.observableArrayList();
        try{
            Transaction txn = session.beginTransaction();
            List<String[]> listStudentAttendance = session.createSQLQuery("SELECT tbl_student.`stud_idnum`,student, " +
            "IF(month1 IS NULL OR month1 = '',0,month1) AS month1, IF(month2 IS NULL OR month2 = '',0,month2) AS month2, " +
            "IF(month3 IS NULL OR month3 = '',0,month3) AS month3, IF(month4 IS NULL OR month4 = '',0,month4) AS month4, " +
            "IF(month5 IS NULL OR month5 = '',0,month5) AS month5, IF(month6 IS NULL OR month6 = '',0,month6) AS month6, " +
            "IF(month7 IS NULL OR month7 = '',0,month7) AS month7, IF(month8 IS NULL OR month8 = '',0,month8) AS month8, " +
            "IF(month9 IS NULL OR month9 = '',0,month9) AS month9, IF(month10 IS NULL OR month10 = '',0,month10) AS month10, " +
            "IF(month11 IS NULL OR month11 = '',0,month11) AS month11, IF(month12 IS NULL OR month12 = '',0,month12) AS month12 " +
            "FROM " +
            "( " +
                "SELECT sh_stud_strand.`stud_idnum`,  " +
                "CONCAT(TRIM(stud_lname),', ',TRIM(stud_fname)) AS student,ss_sy,ss_yr_level,strand_code,strand_group " +
                "FROM `sh_stud_strand`   " +
                "LEFT JOIN sh_studlist ON sh_stud_strand.`stud_idnum` = sh_studlist.`stud_idnum` AND ss_sy=sy AND ss_sem=sem  " +
                "WHERE ss_sy = :ss_sy AND ss_yr_level = :ss_yr_level AND strand_code = :strand_code AND strand_group = :strand_group " +
                "GROUP BY sh_stud_strand.`stud_idnum` " +
            ") tbl_student " +
            "LEFT JOIN `sh_c_attendance` ON ss_sy=att_sy AND tbl_student.`stud_idnum`=sh_c_attendance.`stud_idnum` ")
            .setParameter("ss_sy", ss_sy)
            .setParameter("ss_yr_level", ss_yr_level)    
            .setParameter("strand_code", strand_code)       
            .setParameter("strand_group", strand_group)               
           .list();
            
            txn.commit();
            
            for(Object[] row : listStudentAttendance){
                ShCAttendance shCAttendance = new ShCAttendance();
                shCAttendance.setStudIdnum(row[0].toString());
                shCAttendance.setStud_fullname(row[1].toString());
                shCAttendance.setMonth1(row[2].toString());
                shCAttendance.setMonth2(row[3].toString());
                shCAttendance.setMonth3(row[4].toString());
                shCAttendance.setMonth4(row[5].toString());
                shCAttendance.setMonth5(row[6].toString());
                shCAttendance.setMonth6(row[7].toString());
                shCAttendance.setMonth7(row[8].toString());
                shCAttendance.setMonth8(row[9].toString());
                shCAttendance.setMonth9(row[10].toString());
                shCAttendance.setMonth10(row[11].toString());
                shCAttendance.setMonth11(row[12].toString());
                shCAttendance.setMonth12(row[13].toString());
 
                list.add(shCAttendance);
            }
         
            
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        return list;
    }
    
    public ShCAttendance getRowStudentAttendanceByAbsentDays(String attSy, String attYrlevel, String stud_idnum){
 
        session = HibernateUtil.getSessionFactory().openSession();
        ShCAttendance shCAttendance = new ShCAttendance();
        try{
           Transaction txn = session.beginTransaction();
           shCAttendance = (ShCAttendance) session.createQuery("FROM ShCAttendance "
                   + "WHERE attSy = :attSy AND attYrlevel = :attYrlevel AND stud_idnum = :stud_idnum")
                   .setParameter("attSy", attSy)
                   .setParameter("attYrlevel", attYrlevel)
                   .setParameter("stud_idnum", stud_idnum)
                   .getSingleResult();
            
            txn.commit();
            
            
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        return shCAttendance;
    }
    
    public ObservableList<ShCAttendance> getResStudentAttendanceByAbsentAndPresentDays(String ss_sy, String stud_idnum){
 
        session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<ShCAttendance> list = FXCollections.observableArrayList();
        try{
            Transaction txn = session.beginTransaction();
            String presentDaysSQL1 = "";
            String presentDaysSQL2 = "";
            String absentDaysSQL1 = "";
            String absentDaysSQL2 = "";
            int i = 0;
            int countSQL2 = 0;
            int[] monthsActive = {
                0,0,0,
                0,0,1,
                1,1,1,
                0,0,0,
            };

            for(int isActive : monthsActive) {
                i++;
                if(isActive == 1){
                    countSQL2++;
                    presentDaysSQL1 += "("+"month"+i+"-"+"absent"+i+") as month"+i+",";
                    absentDaysSQL1 += "month"+i+",";


                    if(countSQL2 == 1) {
                        presentDaysSQL2 +=  "(month"+i+"-"+"absent"+i+")";
                        absentDaysSQL2 +=  "month"+i;
                    }
                    else {
                        presentDaysSQL2 +=  "+(month"+i+"-"+"absent"+i+")";
                        absentDaysSQL2 +=  "+month"+i;
                    }                    
                }
                else {
                    presentDaysSQL1 += "(0) as month"+i+",";
                    absentDaysSQL1 += "0 as month"+i+",";
                }
            }
                      
            
            List<String[]> listStudentAttendance = session.createSQLQuery("SELECT 'School Days' AS days_desc,'',month1,month2,month3, "+
            "month4,month5,month6,month7,month8,month9,month10,month11,month12, " +
            "(month1 + month2 + month3 +  month4 + month5 + month6 + month7 + month8 + month9 + month10 + month11 + month12) AS total  "+
            "FROM `month_attendance_days`  " +
            "WHERE `sy` = :ss_sy " +
            "UNION " +
            "SELECT 'Days Present' AS days_desc,stud_idnum,"+presentDaysSQL1+" " +
            " ("+presentDaysSQL2+") AS total  " +
            "FROM " +
            "( " +
            "SELECT `att_classid`,stud_idnum,sh_c_attendance.month1 AS absent1,sh_c_attendance.month2 AS absent2,sh_c_attendance.month3 AS absent3, " +
            "sh_c_attendance.month4 AS absent4,sh_c_attendance.month5 AS absent5,sh_c_attendance.month6 AS absent6,sh_c_attendance.month7 AS absent7, " +
            "sh_c_attendance.month8 AS absent8,sh_c_attendance.month9 AS absent9,sh_c_attendance.month10 AS absent10,sh_c_attendance.month11 AS absent11, " +
            "sh_c_attendance.month12 AS absent12,month_attendance_days.* " +
            "FROM `sh_c_attendance`  " +
            "LEFT JOIN `month_attendance_days`  ON sh_c_attendance.`att_sy`=month_attendance_days.`sy` " +
            "WHERE `sy` = :ss_sy AND stud_idnum= :stud_idnum " +
            ")tbl_present " +
            "UNION " +
            "SELECT 'Days Absent' AS days_desc,stud_idnum,"+absentDaysSQL1+" " +
            "("+absentDaysSQL2+") AS total   " +
            "FROM `sh_c_attendance`  " +
            "WHERE `att_sy`= :ss_sy AND `stud_idnum`= :stud_idnum ")
            .setParameter("ss_sy", ss_sy)
            .setParameter("stud_idnum", stud_idnum)                 
            .list();
            
            System.out.println(ss_sy + " " + stud_idnum);
            /*
            List<String[]> listStudentAttendance = session.createSQLQuery("SELECT 'School Days' AS days_desc,'',month1,month2,month3, "+
            "month4,month5,month6,month7,month8,month9,month10,month11,month12, " +
            "(month1 + month2 + month3 +  month4 + month5 + month6 + month7 + month8 + month9 + month10 + month11 + month12) AS total  "+
            "FROM `month_attendance_days`  " +
            "WHERE `sy` = :ss_sy " +
            "UNION " +
            "SELECT 'Days Present' AS days_desc,stud_idnum,(month1-absent1) AS month1,(month2-absent2) AS month2,(month3-absent3) AS month3, " +
            "(month4-absent4) AS month4,(month5-absent5) AS month5,(month6-absent6) AS month6,(month7-absent7) AS month7,(month8-absent8) AS month8, " +
            "(month9-absent9) AS month9,(month10-absent10) AS month10,(month11-absent11) AS month11,(month12-absent12) AS month12, " +
            " ((month1-absent1) + (month2-absent2) + (month3-absent3) + (month4-absent4) + (month5-absent5) + (month6-absent6) + (month7-absent7) +    " +
"            (month8-absent8) + (month9-absent9) + (month10-absent10) + (month11-absent11) + (month12-absent12)) AS total  " +
            "FROM " +
            "( " +
            "SELECT `att_classid`,stud_idnum,sh_c_attendance.month1 AS absent1,sh_c_attendance.month2 AS absent2,sh_c_attendance.month3 AS absent3, " +
            "sh_c_attendance.month4 AS absent4,sh_c_attendance.month5 AS absent5,sh_c_attendance.month6 AS absent6,sh_c_attendance.month7 AS absent7, " +
            "sh_c_attendance.month8 AS absent8,sh_c_attendance.month9 AS absent9,sh_c_attendance.month10 AS absent10,sh_c_attendance.month11 AS absent11, " +
            "sh_c_attendance.month12 AS absent12,month_attendance_days.* " +
            "FROM `sh_c_attendance`  " +
            "LEFT JOIN `month_attendance_days`  ON sh_c_attendance.`att_sy`=month_attendance_days.`sy` " +
            "WHERE `sy` = :ss_sy AND stud_idnum= :stud_idnum " +
            ")tbl_present " +
            "UNION " +
            "SELECT 'Days Absent' AS days_desc,stud_idnum,month1,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12, " +
            "(month1 + month2 + month3 + month4 + month5 + month6 + month7 + month8 + month9 + month10 + month11 + month12) AS total   " +
            "FROM `sh_c_attendance`  " +
            "WHERE `att_sy`= :ss_sy AND `stud_idnum`= :stud_idnum ")
            .setParameter("ss_sy", ss_sy)
            .setParameter("stud_idnum", stud_idnum)                 
            .list();
            */
            txn.commit();
            
            for(Object[] row : listStudentAttendance){
                ShCAttendance shCAttendance = new ShCAttendance();
                shCAttendance.setDays_desc(row[0].toString());
                shCAttendance.setStudIdnum(row[1].toString());
                shCAttendance.setMonth1(row[2].toString());
                shCAttendance.setMonth2(row[3].toString());
                shCAttendance.setMonth3(row[4].toString());
                shCAttendance.setMonth4(row[5].toString());
                shCAttendance.setMonth5(row[6].toString());
                shCAttendance.setMonth6(row[7].toString());
                shCAttendance.setMonth7(row[8].toString());
                shCAttendance.setMonth8(row[9].toString());
                shCAttendance.setMonth9(row[10].toString());
                shCAttendance.setMonth10(row[11].toString());
                shCAttendance.setMonth11(row[12].toString());
                shCAttendance.setMonth12(row[13].toString());
                shCAttendance.setDays_total(row[14].toString());
                
                list.add(shCAttendance);
            }
         
            
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        return list;
    }
    
}
