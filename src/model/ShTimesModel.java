/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShTimes;
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
public class ShTimesModel {
    private static Session session;
    public ObservableList<String> getTimesForCombobox() {
        
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(ShTimes.class);
        criteria.setProjection(Projections.property("timesdesc"));
        ObservableList<String> list = FXCollections.observableArrayList(criteria.list());
        session.getTransaction().commit();
        session.close(); session.getSessionFactory().close();
        
        return list;
    }
    
    public ObservableList<ShTimes> getResTimes() {

        ObservableList<ShTimes> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        List<ShTimes> listTimes = session.createQuery("from ShTimes").list();
        
        

        txn.commit();
        session.close(); session.getSessionFactory().close();
         
        listTimes.stream().forEach(list::add);
        

        return list;
    }
    
    public Integer getTimecodeByTimedesc(String timesdesc){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            ShTimes shTimes =  (ShTimes) session.createQuery("FROM ShTimes " +
                    "WHERE timesdesc = :timesdesc ")
                    .setParameter("timesdesc", timesdesc)
                    .getSingleResult();
            
            int timecode = shTimes.getTimecode();
           
            txn.commit();
            return timecode;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
}
