/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.CoreValues;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author ACER
 */
public class CoreValuesModel {
    
    private static Session session;
    
    
    public ObservableList<CoreValues> getResCoreValues() {

        ObservableList<CoreValues> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        List<CoreValues> listCoreValues = session.createQuery("from CoreValues").list();
        

        txn.commit();
        session.close(); session.getSessionFactory().close();
         
        listCoreValues.stream().forEach(list::add);

        return list;
    }
    
    public CoreValues getRowCoreValues(Integer cvId) {
        session = HibernateUtil.getSessionFactory().openSession();

        try {
            Transaction txn = session.beginTransaction();
            CoreValues coreValues = (CoreValues) session.createQuery(" FROM CoreValues "
                    + "WHERE cvId = :cvId ")
                    .setParameter("cvId", cvId)
                    .getSingleResult();
            
            txn.commit();
            
            return coreValues;
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        }
        
        
    }
    
}
