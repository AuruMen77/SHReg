/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShCClassStud;
import entity.ShStudStrand;
import entity.ShStudlist;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author CITS-Sheng
 */
public class ShStudStrand_ShStudlistModel {
    private static Session session;
    
    public boolean checkStudentIsPromoted(String stud_idnum) {
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{  
            Transaction txn = session.beginTransaction();
            Long  cnt = (Long)session.createQuery("SELECT COUNT(studIdnum) "
                    + "FROM ShStudStrand "
                    + "WHERE studIdnum = :stud_idnum ")
                    .setParameter("stud_idnum", stud_idnum)
                    .uniqueResult();
            txn.commit();
            
            if(cnt > 0)
                return true;
            else
                return false;
            
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        }  
    }
    
    public String getStudentEnrollmentStatus(String stud_idnum, String ssSy) {
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{  
            Transaction txn = session.beginTransaction();
            Long  cnt = (Long)session.createQuery("SELECT COUNT(studIdnum) "
                    + "FROM ShStudStrand "
                    + "WHERE studIdnum = :stud_idnum AND ssSy != :ssSy")
                    .setParameter("stud_idnum", stud_idnum)
                    .setParameter("ssSy", ssSy)
                    .uniqueResult();
            txn.commit();
            
            if(cnt > 0)
                return "OLD STUDENT";
            else
                return "NEW STUDENT";
            
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        }  
    }
    
    public ShStudStrand getRowShStudStrandByYearAndSem(String stud_idnum, String ssYrLevel, String ssSem) {
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{  
            Transaction txn = session.beginTransaction();
            ShStudStrand  shStudStrand = (ShStudStrand)session.createQuery("FROM ShStudStrand "
                    + "WHERE studIdnum = :stud_idnum AND ssYrLevel = :ssYrLevel AND  ssSem = :ssSem ")
                    .setParameter("stud_idnum", stud_idnum)
                    .setParameter("ssYrLevel", Integer.parseInt(ssYrLevel))
                    .setParameter("ssSem", Integer.parseInt(ssSem))
                    .getSingleResult();
            
            txn.commit();
            
            return shStudStrand;
            
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        }  
    }
    
    public boolean saveApplicantEnrollment(ShStudStrand shStudStrand, ShStudlist shStudlist) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(shStudStrand);
            session.saveOrUpdate(shStudlist);
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
    
    public boolean saveStudentPromotion(String ssSy, String ssSem, String studIdnum, ShStudStrand shStudStrand, ShStudlist shStudlist) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            Query query = session.createQuery("DELETE FROM ShStudStrand WHERE ssSy = :ssSy AND ssSem = :ssSem AND studIdnum = :studIdnum ")
                    .setParameter("ssSy", ssSy)
                    .setParameter("ssSem", Integer.parseInt(ssSem))
                    .setParameter("studIdnum", studIdnum);
                    
            query.executeUpdate();
            
            session.saveOrUpdate(shStudStrand);
            session.saveOrUpdate(shStudlist);
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
    
    public boolean saveStudentForPromotionBatch(String ssSy, String ssSem, String str_studIdnum, ObservableList<ShStudStrand> listStudentStrand, 
            ObservableList<ShStudlist> listStudentList, Session session_) {
        boolean success = false; 
        int i = 0;

        Query query = session_.createQuery("DELETE FROM ShStudStrand WHERE ssSy = :ssSy AND ssSem = :ssSem AND studIdnum IN ( " + str_studIdnum + " ) ")
                .setParameter("ssSy", ssSy)
                .setParameter("ssSem", Integer.parseInt(ssSem));

        query.executeUpdate();

        for(ShStudStrand shStudStrand: listStudentStrand){
            if ( i % 20 == 0 ) { //20, same as the JDBC batch size
                //flush a batch of inserts and release memory:
                session_.flush();
                session_.clear();
            }

            session_.save(shStudStrand);
            i++; 
        }

        for(ShStudlist shStudlist: listStudentList){
            //flush a batch of inserts and release memory:
            session_.flush();
            session_.clear();
            session_.save(shStudlist);
            i++;
        }

        return success;
    }
    
    
    
}
