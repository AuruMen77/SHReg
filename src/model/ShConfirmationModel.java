/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import entity.ShApplicant;
import entity.ShConfirmation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author ACER
 */
public class ShConfirmationModel {
    private static Session session;
    
    public ObservableList<ShConfirmation> getResApplicantConfirmationWithSearch(String conSy, String strandCode, String search) {
      
        session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<ShConfirmation> list = FXCollections.observableArrayList();
        
        try{  
            Transaction txn = session.beginTransaction();

            String hql = "FROM ShConfirmation WHERE shApplicant.appNo!='' AND confirmed = 1 AND conSy = :conSy  ";
            
            String col = "",  col2 = "";
            int ctr = 1;
            List<String> listParameter = new ArrayList<>();
            
            if(!strandCode.equals("all")){
                col = "?" + ctr;
                hql += " AND strandCode = " + col;
                listParameter.add(strandCode);
                ctr++;
            }

            if(!search.equals("")){
                col = "?" + ctr;
                ctr++;
                col2 = "?" + ctr;
                hql += " AND (studentId LIKE " + col +" OR CONCAT(TRIM(shApplicant.appLname),', ',TRIM(shApplicant.appFname)) LIKE " + col2 +")";
                 
                listParameter.add("%"+search+"%");
                listParameter.add("%"+search+"%");
                ctr++;
            }
            
            Query psmt  = session.createQuery(hql);
            psmt.setParameter("conSy", conSy);
            
            ctr = 1;
            Iterator iterator = listParameter.iterator();
            while(iterator.hasNext()) {
                psmt.setParameter(ctr, iterator.next());
                ctr++;
            }
            
            List<ShConfirmation> listApplicantConfirmed = psmt.list();
 
          
            for(ShConfirmation file : listApplicantConfirmed){
 
                Hibernate.initialize(file.getShApplicant());
                
            }
            
            txn.commit();
            listApplicantConfirmed.stream().forEach(list::add);
           
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        } 
        return list; 
    }
    
    
    public ObservableList<ShConfirmation> getResApplicantConfirmationNotEnrolledWithSearch(String conSy, String strandCode, String search) {
      
        session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<ShConfirmation> list = FXCollections.observableArrayList();
        
        try{  
            
            Transaction txn = session.beginTransaction();
            
            String hql = "FROM ShConfirmation WHERE shApplicant.appNo!='' AND confirmed = 1 AND conSy = :conSy "
                    + "AND studentId NOT IN ( SELECT studIdnum FROM ShStudStrand WHERE ssSy = :ssSy )  ";
            
            String col = "",  col2 = "";
            int ctr = 1;
            List<String> listParameter = new ArrayList<>();
            
            if(!strandCode.equals("all")){
                col = "?" + ctr;
                hql += " AND strandCode = " + col;
                listParameter.add(strandCode);
                ctr++;
            }

            if(!search.equals("")){
                col = "?" + ctr;
                ctr++;
                col2 = "?" + ctr;
                hql += " AND (studentId LIKE " + col +" OR CONCAT(TRIM(shApplicant.appLname),', ',TRIM(shApplicant.appFname)) LIKE " + col2 +")";
                 
                listParameter.add("%"+search+"%");
                listParameter.add("%"+search+"%");
                ctr++;
            }
            
            Query psmt  = session.createQuery(hql);
            psmt.setParameter("conSy", conSy);
            psmt.setParameter("ssSy", conSy);
   
            
            ctr = 1;
            Iterator iterator = listParameter.iterator();
            while(iterator.hasNext()) {
                psmt.setParameter(ctr, iterator.next());
                ctr++;
            }
            
            List<ShConfirmation> listApplicantConfirmed = psmt.list();
 
          
            for(ShConfirmation file : listApplicantConfirmed){
 
                Hibernate.initialize(file.getShApplicant());
                
            }
            
            txn.commit();
            listApplicantConfirmed.stream().forEach(list::add);
           
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        } 
        return list; 
    }
    
    
    public ShConfirmation getRowApplicantConfirmation(ShApplicant shApplicant) {
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{  
            Transaction txn = session.beginTransaction();
            ShConfirmation shConfirmation = (ShConfirmation) session.createQuery("FROM ShConfirmation WHERE shApplicant = :shApplicant ")
                                               .setParameter("shApplicant", shApplicant)
                                               .getSingleResult();
            
            Hibernate.initialize(shConfirmation.getShApplicant());
            txn.commit();

            return shConfirmation; 
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        }  
    }
    
    public int getMaxStudIDNum() {
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{  
            Transaction txn = session.beginTransaction();
            String max_studID = (String)session.createQuery("SELECT MAX(studentId) FROM ShConfirmation")
                                               .uniqueResult();
            txn.commit();
            
            return Integer.valueOf(max_studID);
            
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        }  
    }
    
    public boolean saveApplicantConfirmation(ShConfirmation shConfirmation) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(shConfirmation);
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
    
    public boolean saveApplicantConfirmation(ShConfirmation shConfirmation,ShApplicant shApplicant) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(shConfirmation);
            session.saveOrUpdate(shApplicant);
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
