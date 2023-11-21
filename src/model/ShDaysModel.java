/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShDays;
import entity.Strands;
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
public class ShDaysModel {
    private static Session session;
    public ObservableList<String> getDaysForCombobox() {
        
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(ShDays.class);
        criteria.setProjection(Projections.property("days"));
        ObservableList<String> list = FXCollections.observableArrayList(criteria.list());
        session.getTransaction().commit();
        session.close(); session.getSessionFactory().close();
        
        return list;
    }
    
    public ObservableList<ShDays> getResDays(String search) {
        ObservableList<ShDays> list = FXCollections.observableArrayList();        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        List<ShDays> listDays = session.createQuery("from ShDays WHERE days LIKE :days")
                .setParameter("days", "%"+ search +"%")
                .list();

        txn.commit();
        /**
        * Both session and SessionFactory needs to be
        * closed to avoid connection from sleeping.
        * 
        * 
        * Calling session.close() will cause the connection
        * to return to the connection pool.
        * 
        * Calling getSessionFactory().close() will 
        * close all connections.
        **/
        session.close(); session.getSessionFactory().close();
         
        listDays.stream().forEach(list::add);
        return list;
    }
    
    public ShDays getRowDay(String days){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        try{
            ShDays shDays = (ShDays) session.createQuery("FROM ShDays WHERE days = :days")
                    .setParameter("days", days)
                    .getSingleResult();
            
            txn.commit();
            return shDays;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        
    }
    

    
    public boolean saveDay(ShDays shDays){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(shDays);
            txn.commit();
            success = true;
        }catch(Exception ex){
            txn.rollback();
            success = false;
            ex.printStackTrace();
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        return success;
    }
    
    public boolean saveDayNew(String txtDay, String txtDaycode){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            ShDays save_shDays = new ShDays();
            save_shDays.setDays_id(28);
            save_shDays.setDays(txtDay);
            save_shDays.setDaycode(txtDaycode);
            session.save(save_shDays);
            txn.commit();
            success = true;
        }catch(Exception ex){
            txn.rollback();
            success = false;
            ex.printStackTrace();
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        return success;
    }
    
    
}
