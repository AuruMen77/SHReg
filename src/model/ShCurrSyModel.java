/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShCurrHdr;
import entity.ShCurrSy;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author CITS-Sheng
 */
public class ShCurrSyModel {
    private static Session session;
    public ShCurrSy getRowCurrSy(String sy, String strandcode){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            ShCurrSy shCurrSy = (ShCurrSy) session.createQuery("FROM ShCurrSy WHERE sy = :sy AND shCurrHdr.strandcode = :strandcode")
                    .setParameter("sy", sy)
                    .setParameter("strandcode", strandcode)
                    .getSingleResult();
            
            txn.commit();
            return shCurrSy;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public ObservableList<ShCurrSy> getResCurriculumSY(ShCurrHdr shCurrHdr) {
        ObservableList<ShCurrSy> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        List<ShCurrSy> listCurrSY = session.createQuery("FROM ShCurrSy WHERE shCurrHdr = :shCurrHdr ")
                                           .setParameter("shCurrHdr", shCurrHdr)
                                           .list();
        

        txn.commit();
         
        listCurrSY.stream().forEach(list::add);
        session.close(); session.getSessionFactory().close();
        return list;
    }
    
    
    
    public boolean saveCurriculumSY(ShCurrSy shCurrSy) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tran = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(shCurrSy);
            tran.commit();
            success = true;
        } catch (Exception e) {
            tran.rollback();
            e.printStackTrace();
            success = false;
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return success;
    }
    
    public boolean saveCurriculumSYFromTable(List<ShCurrSy> listShCurrSy, ShCurrHdr shCurrHdr) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tran = session.beginTransaction();
        boolean success = false;
        try{
            session.createQuery("DELETE ShCurrSy WHERE shCurrHdr = :shCurrHdr ")
                   .setParameter("shCurrHdr", shCurrHdr)
                   .executeUpdate();
            
            for (ShCurrSy rowShCurrSy: listShCurrSy)
            {
                session.saveOrUpdate(rowShCurrSy);
      
            }
            
            tran.commit();
            success = true;
        } catch (Exception e) {
            tran.rollback();
            e.printStackTrace();
            success = false;
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return success;
    }
    
    
}
