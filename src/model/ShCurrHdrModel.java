/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import entity.ShCurrDtl;
import entity.ShCurrHdr;
import entity.ShCurrSy;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author ACER
 */
public class ShCurrHdrModel {
    private static Session session;
    
    
       
    public ObservableList<ShCurrHdr> getResCurriculum() {
        ObservableList<ShCurrHdr> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        List<ShCurrHdr> listCurriculum = session.createQuery("from ShCurrHdr").list();
        txn.commit();

        listCurriculum.stream().forEach(list::add);
        session.close(); session.getSessionFactory().close();

        return list;
    }
    
    public ShCurrHdr getRowCurriculumHdr(Integer currHdrId) {
        session = HibernateUtil.getSessionFactory().openSession();

        try {
            Transaction txn = session.beginTransaction();
            List<ShCurrHdr> resultset = session.createQuery(" FROM ShCurrHdr "
                    + "WHERE currHdrId = :currHdrId ")
                    .setParameter("currHdrId", currHdrId)
                    .list();
            txn.commit();
            if (resultset.size() > 0) {
                return resultset.get(0); 
            } else {
                return null;
            }
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public ShCurrHdr getRowCurriculumHdr(ShCurrDtl shCurrDtls, ShCurrSy shCurrSies, String strandcode) {
        session = HibernateUtil.getSessionFactory().openSession();

        try {
            Transaction txn = session.beginTransaction();
            List<ShCurrHdr> resultset = session.createQuery(" FROM ShCurrHdr "
                    + "WHERE shCurrDtls = :shCurrDtls AND shCurrSies = :shCurrSies AND strandcode = :strandcode ")
                    .setParameter("shCurrDtls", shCurrDtls)
                    .setParameter("shCurrSies", shCurrSies)
                    .setParameter("strandcode", strandcode)
                    .list();
            txn.commit();
            if (resultset.size() > 0) {
                return resultset.get(0); 
            } else {
                return null;
            }
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public boolean saveCurriculum(ShCurrHdr curriculum) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tran = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(curriculum);
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
