/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import config.DBUtilities;
import entity.SaLedger;
import entity.SaLedgerHeader;
import entity.ShStudStrand;
import entity.ShStudlist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author CITS-Sheng
 */
public class SaLedgerHeader_SaLedgerModel {
    private static Session session;
    
    public ObservableList<SaLedger> getResLedgerDtlByStudent(String schoolyear, String studentid) {
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            ObservableList<SaLedger> list = FXCollections.observableArrayList();
            Transaction txn = session.beginTransaction();
            
            List<SaLedger> listSaLedger = session.createQuery("FROM SaLedger WHERE SaLedgerHeader.schoolyear = :schoolyear AND SaLedgerHeader.studentid = :studentid")
                    .setParameter("schoolyear", schoolyear)
                    .setParameter("studentid", studentid)
                    .list();
            txn.commit();

            listSaLedger.stream().forEach(list::add);
            return list; 
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public boolean saveStudentPromotionAndLedger(String ssSy, String ssSem, String schoolyear, String studIdnum, String gender, Boolean checkAssess,
            ShStudStrand shStudStrand, ShStudlist shStudlist,
            SaLedgerHeader saLedgerHeader, ObservableList<SaLedger> listSaLedger) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            //--- START sh_stud_strand && sh_studlist ---//
            Query query = session.createQuery("DELETE FROM ShStudStrand WHERE ssSy = :ssSy AND ssSem = :ssSem AND studIdnum = :studIdnum ")
                    .setParameter("ssSy", ssSy)
                    .setParameter("ssSem", Integer.parseInt(ssSem))
                    .setParameter("studIdnum", studIdnum);
                    
            query.executeUpdate();
            
            
            
            
            session.saveOrUpdate(shStudStrand);
            session.saveOrUpdate(shStudlist);
            //--- END sh_stud_strand && sh_studlist ---//
            
            
            //--- START sa_ledger_header ---//
            query = session.createQuery("DELETE FROM SaLedgerHeader WHERE schoolyear = :schoolyear AND studentid = :studentid ")
                    .setParameter("schoolyear", (ssSy.concat("-").concat(ssSem) ))
                    .setParameter("studentid", studIdnum);
                    
            query.executeUpdate();
            
            //--- START local ledger ---//
            session.saveOrUpdate(saLedgerHeader);
            for(SaLedger saLedger: listSaLedger){
                session.saveOrUpdate(saLedger);
            }
            //--- END local ledger ---//
            
            
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
    
    public boolean saveStudentPromotionAndLedgerLive(String ssSy, String ssSem, String schoolyear, String studIdnum, String studGender, ShStudStrand shStudStrand, ShStudlist shStudlist,
            SaLedgerHeader saLedgerHeader, ObservableList<SaLedger> listSaLedger) throws SQLException {
        
        
        ResultSet rs,rs_student;
        DBUtilities dbUtilities = new DBUtilities();
        Connection conn = dbUtilities.getConnection();
        PreparedStatement stmt = null;
        
 
        boolean success = false;
        try{
           conn.setAutoCommit(false);
            //--- START local ledger ---//

            
            //--- just variables
            String studID,strandcode,ledgerHdrID;
                    
            //--- enrollment table variables for inserting 
            String sql="",sysem_studID,reference,courseID="",yearGrade,studStatus,amount, syear="", 
                    studFullname="", studFname="", studLname="", studMI="", studSuffix="";
            
            //--- enrollmentdtl table variables for inserting 
            String itemID,itemAmount; 
            
            //--- studentledger table variables for inserting 
            int enrolmentDtlID=0; String enrolmentHdrID=""; 
            
            studID = studIdnum;
            sysem_studID = "SH"+ ssSy + studIdnum;
            reference = "SH" + studIdnum;
            
            if(ssSem.equals("3")){
                syear = ssSy +"-"+ ssSem;
                sysem_studID = "SH"+ syear + studIdnum;
            }else{
                syear = ssSy;
            }
            
           
            
            courseID = new StrandsModel().getRowStrandByCode(shStudStrand.getStrandCode().trim()).getArStrand();
            
            
          
            studFullname = shStudlist.getStudLname().trim() +", "+ shStudlist.getStudFname().trim() +" "+ shStudlist.getStudMi().trim() +" "+ shStudlist.getStudSuffix().trim();
            studLname = shStudlist.getStudLname().trim();
            studFname = shStudlist.getStudFname().trim();
            studMI = shStudlist.getStudMi().trim();
            studSuffix = shStudlist.getStudSuffix().trim();

            yearGrade = shStudStrand.getSsYrLevel().toString();
            studStatus = shStudStrand.getSsStatus();
            amount = String.valueOf(saLedgerHeader.getAssessedAmount());
            
            
            
            sql = "SELECT * FROM ar.`student` WHERE `STUDENTGROUP`='SH' AND `ID` = ?  ";
            stmt = conn.prepareStatement(sql);
            int pstmt_ctr = 1;
            stmt.setString(1, studID);
            rs_student = stmt.executeQuery();
            
           
            if ( !rs_student.next() ) {
//                  stmt = conn.createStatement(); 
                  sql="INSERT INTO `ar`.`student` SET ID = ?,  `STUDENTNAME` = ?, `LASTNAME` = ?, "
                          + "`FIRSTNAME` = ?, `MIDNAME` = ?, "
                          + "`SEX` = ?, `STUDENTGROUP` = ?, `COURSEID`= ?, `YEARGRADE` = ?, `ISACTIVE` = ?" ;
                  stmt = conn.prepareStatement(sql);
                  pstmt_ctr = 1;
                  stmt.setString(pstmt_ctr++, studID);
                  stmt.setString(pstmt_ctr++, studFullname);
                  stmt.setString(pstmt_ctr++, studLname);
                  stmt.setString(pstmt_ctr++, studFname);
                  stmt.setString(pstmt_ctr++, studMI);
                  stmt.setString(pstmt_ctr++, studGender);
                  stmt.setString(pstmt_ctr++, "SH");
                  stmt.setString(pstmt_ctr++, courseID);
                  stmt.setString(pstmt_ctr++, yearGrade);
                   stmt.setString(pstmt_ctr++, "1");
                  System.out.println(sql);
                  stmt.executeUpdate();
                  
                  conn.commit();
            }else{
                System.out.println("with data");
            }
            
            
            sql = "DELETE FROM ar.`enrollment` WHERE syear= ? AND studentgroup= ? AND yeargrade= ? AND studentid=?";
            stmt = conn.prepareStatement(sql);
            pstmt_ctr = 1;
            stmt.setString(pstmt_ctr++, syear);
            stmt.setString(pstmt_ctr++, "SH");
            stmt.setString(pstmt_ctr++, yearGrade);
            stmt.setString(pstmt_ctr++, studID);
            stmt.executeUpdate();
            System.out.println(sql);
            System.out.println(syear);
            System.out.println("SH");
            System.out.println(yearGrade);
            System.out.println(studID);
     
//            stmt = conn.createStatement(); 
            sql="INSERT INTO `ar`.`enrollment` " +
                            " SET ID= ?,REFERENCE= ?,TRANSDATE=NOW(), " +
                            " SYEAR= ?,STUDENTGROUP='SH',STUDENTID=?,COURSEID=?,YEARGRADE=?, ENROLLSTATUS= ?, " +
                            " AMOUNT=?,ENROLLTYPE='1',GLYEAR=YEAR(NOW()),GLPERIOD=MONTH(NOW())" ;
            stmt = conn.prepareStatement(sql);
            pstmt_ctr = 1;
            stmt.setString(pstmt_ctr++, sysem_studID);
            stmt.setString(pstmt_ctr++, reference);
            stmt.setString(pstmt_ctr++, syear);
            stmt.setString(pstmt_ctr++, studID);
            stmt.setString(pstmt_ctr++, courseID);
            stmt.setString(pstmt_ctr++, yearGrade);
            stmt.setString(pstmt_ctr++, studStatus);
            stmt.setString(pstmt_ctr++, amount);            
            
            System.out.println("INSERT");
            System.out.println(sysem_studID);
            System.out.println(reference);
            System.out.println(syear);
            System.out.println(studID);
            System.out.println(courseID);
            System.out.println(yearGrade);
            System.out.println(studStatus);
            System.out.println(amount);
            
            stmt.executeUpdate();
//            System.out.println(sql);
            
                
            
            for(SaLedger saLedger: listSaLedger){
                
                itemID = saLedger.getItemid();
                itemAmount = saLedger.getDebit().toString();

                //--- insert enrollmentdtl
//                stmt = connection.createStatement(); //--- need to recreatestatement cuz 2nd generated keys for hdr insert is incorrect
//                stmt = conn.createStatement(); 
                sql=" INSERT INTO ar.enrollmentdtl SET ENROLLMENTID=?,STUDENTGROUP='SH',ITEMID=?,AMOUNT=?" ;
                stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt_ctr = 1;
                stmt.setString(pstmt_ctr++, sysem_studID);
                stmt.setString(pstmt_ctr++, itemID);
                stmt.setString(pstmt_ctr++, itemAmount);
                stmt.executeUpdate();
        
//                System.out.println(sql);
             
                

                rs = stmt.getGeneratedKeys();  
                if ( rs.next() ) {
                     // Retrieve the auto generated key(s).
                     enrolmentDtlID = rs.getInt(1);
                }

                //--- insert studentledger
//                stmt = conn.createStatement(); //--- need to recreatestatement cuz 2nd generated keys for hdr insert is incorrect
                sql="INSERT INTO ar.studentledger  " +
                        " SET STUDENTGROUP='SH',STUDENTID= ?,TRANSDATE=NOW(),SOURCECD='AS', " +
                        " REFERENCE=?,SYEAR= ?,ITEMID= ?,DEBIT= ?, " +
                        " ENROLLMENTDTLID= ?,GLYEAR=YEAR(NOW()),GLPERIOD=MONTH(NOW())" ;
                stmt = conn.prepareStatement(sql);
                pstmt_ctr = 1;
                stmt.setString(pstmt_ctr++, studID);
                stmt.setString(pstmt_ctr++, reference);
                stmt.setString(pstmt_ctr++, syear);
                stmt.setString(pstmt_ctr++, itemID);
                stmt.setString(pstmt_ctr++, itemAmount);
                stmt.setString(pstmt_ctr++, String.valueOf(enrolmentDtlID));
                stmt.executeUpdate();
                
                System.out.println(itemID);
//                System.out.println(sql);
                
               
            }
            //--- END local ledger ---//
            
            
            conn.commit();
            success = true;
        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
            success = false;
        } finally {
           
        }
        return success;
    }
    
    public boolean saveStudentSaLedgerHeader(String schoolyear, String studentid, SaLedgerHeader saLedgerHeader, ObservableList<SaLedger> listSaLedger) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            String cclassId;
            int i = 0;
            
            Query query = session.createQuery("DELETE FROM SaLedgerHeader WHERE schoolyear = :schoolyear AND studentid = :studentid ")
                    .setParameter("schoolyear", schoolyear)
                    .setParameter("studentid", studentid);
                    
            query.executeUpdate();
            
            session.save(saLedgerHeader);
            
            for(SaLedger saLedger: listSaLedger){
                session.save(saLedger);
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
    
    public boolean saveStudentSaLedgerHeaderBatch(String schoolyear, String str_studentid, ObservableList<SaLedgerHeader> listSaLedgerHeader, ObservableList<SaLedger> listSaLedger, Session session_) {
        boolean success = false;
        String cclassId;
        int i = 0;

        Query query = session_.createQuery("DELETE FROM SaLedgerHeader WHERE schoolyear = :schoolyear AND studentid IN ( " + str_studentid + " ) ")
                .setParameter("schoolyear", schoolyear);

        query.executeUpdate();

        for(SaLedgerHeader saLedgerHeader: listSaLedgerHeader){
            if ( i % 20 == 0 ) { //20, same as the JDBC batch size
                //flush a batch of inserts and release memory:
                session_.flush();
                session_.clear();
            }

            session_.save(saLedgerHeader);
            i++; 
        }

        i = 0;
        for(SaLedger saLedger: listSaLedger){

            if ( i % 20 == 0 ) { //20, same as the JDBC batch size
                //flush a batch of inserts and release memory:
                session_.flush();
                session_.clear();
            }

            session_.save(saLedger);
            i++; 
        }



        success = true;
       
        return success;
    }
    
    
    public boolean deleteStudentLedger(String ssSy, String ssSem, String ssYrLevel, String studIdnum) throws SQLException {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        
        PreparedStatement stmt = null;
        DBUtilities dbUtilities = new DBUtilities();
        Connection conn = dbUtilities.getConnection();
        int pstmt_ctr = 1;
        String syear = "";
        
        try{            
            //--- START sa_ledger_header ---//
            Query query = session.createQuery("DELETE FROM SaLedgerHeader WHERE schoolyear = :schoolyear AND studentid = :studentid ")
                    .setParameter("schoolyear", (ssSy.concat("-").concat(ssSem) ))
                    .setParameter("studentid", studIdnum);
                    
            query.executeUpdate();
            //--- END sa_ledger_header ---//
            
            //--- START sh_stud_strand ---//
            query = session.createQuery("DELETE FROM ShStudStrand WHERE ssSy = :ssSy AND ssSem = :ssSem AND studIdnum = :studIdnum AND ssYrLevel = :ssYrLevel ")
                    .setParameter("ssSy", ssSy)
                    .setParameter("ssSem", Integer.parseInt(ssSem))
                    .setParameter("studIdnum", studIdnum)
                    .setParameter("ssYrLevel", Integer.parseInt(ssYrLevel));
                    
            query.executeUpdate();
            //--- END sh_stud_strand ---//
            
            //--- START sh_studlist ---//
            /*
            * Not working due to weird table structure and
            * entity mapping for table sh_studlist..
            *
            query = session.createQuery("DELETE FROM ShStudlist WHERE sy = :sy AND sem = :sem AND id = :studIdnum ")
                    .setParameter("sy", ssSy)
                    .setParameter("sem", Integer.parseInt(ssSem))
                    .setParameter("studIdnum", studIdnum);
                    
            query.executeUpdate();
            */
            //--- END sh_studlist ---//
            
            //--- START sh_c_class_stud ---//
            query = session.createQuery("DELETE FROM ShCClassStud WHERE csSy = :csSy AND csSem = :csSem AND csIdnum = :csIdnum AND csYrLevel = :csYrLevel ")
                    .setParameter("csSy", ssSy)
                    .setParameter("csSem", Integer.parseInt(ssSem))
                    .setParameter("csIdnum", studIdnum)
                    .setParameter("csYrLevel", Integer.parseInt(ssYrLevel));
                    
            query.executeUpdate();
            //--- END sh_c_class_stud ---//
            
            //--- START ar.enrollment ---//            
            conn.setAutoCommit(false);
            if(ssSem.equals("3")){
                syear = ssSy +"-"+ ssSem;
            }else{
                syear = ssSy;
            }
            
            String sql = "DELETE FROM ar.`enrollment` WHERE syear= ? AND studentgroup= ? AND yeargrade= ? AND studentid=?";
            stmt = conn.prepareStatement(sql);
            pstmt_ctr = 1;
            stmt.setString(pstmt_ctr++, syear);
            stmt.setString(pstmt_ctr++, "SH");
            stmt.setString(pstmt_ctr++, ssYrLevel);
            stmt.setString(pstmt_ctr++, studIdnum);
            stmt.executeUpdate();            
            //--- END ar.enrollment ---//
            
            txn.commit();
            conn.commit();
            success = true;
            
        } catch (Exception e) {
            txn.rollback();
            conn.rollback();
            e.printStackTrace();
            success = false;
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return success;
    }
    
}
