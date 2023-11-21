/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShCourseList;
import entity.ShCurrDtl;
import entity.ShCurrHdr;
import entity.ShCurrSy;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author ACER
 */
public class ShCurrDtlModel {
    private static Session session;
    
    public ObservableList<ShCurrDtl> getResCurriculumSubjects(ShCurrHdr shCurrHdr) {
        ObservableList<ShCurrDtl> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        List<ShCurrDtl> listCurrSubject = session.createQuery("FROM ShCurrDtl WHERE shCurrHdr = :shCurrHdr ")
                                           .setParameter("shCurrHdr", shCurrHdr)
                                           .list();
        
        for(ShCurrDtl file : listCurrSubject){
            Hibernate.initialize(file.getShCourseList());
        }

        txn.commit();
        session.close(); session.getSessionFactory().close();

        listCurrSubject.stream().forEach(list::add);
        
        return list;
    }
    
    public boolean saveCurriculumSubjectsFromTable(List<ShCurrDtl> listShCurrDtl, ShCurrHdr shCurrHdr) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tran = session.beginTransaction();
        boolean success = false;
        try{
            session.createQuery("DELETE ShCurrDtl WHERE shCurrHdr = :shCurrHdr  ")
                   .setParameter("shCurrHdr", shCurrHdr)
                   .executeUpdate();
            
            for (ShCurrDtl rowShCurrDtl: listShCurrDtl)
            {
                session.saveOrUpdate(rowShCurrDtl);
      
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
