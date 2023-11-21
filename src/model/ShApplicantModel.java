/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShApplicant;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author ACER
 */
public class ShApplicantModel {
    private static Session session;
    
    public ObservableList<ShApplicant> getResApplicant(){
        session = HibernateUtil.getSessionFactory().openSession();
        try{
        ObservableList<ShApplicant> list = FXCollections.observableArrayList();
        Transaction txn = session.beginTransaction();
        List<ShApplicant> listApplicant = session.createQuery("from ShApplicant WHERE appNo <= 150414").list();
        txn.commit();

        listApplicant.stream().forEach(list::add);
        for(ShApplicant row:listApplicant){
            list.add(row);
        }
        
//        Statistics stats = session.getSessionFactory().getStatistics();
//        stats.setStatisticsEnabled(true);
//        stats.getSessionOpenCount();
// 
//        stats.logSummary();
        return list; 
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public ObservableList<ShApplicant> searchResApplicant(String txtSearch){
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            ObservableList<ShApplicant> list = FXCollections.observableArrayList();
            Transaction txn = session.beginTransaction();
            List<ShApplicant> listApplicant = session.createQuery("from ShApplicant WHERE studIdnum LIKE :txtSearch " +
                                                     "OR TRIM(appLname) LIKE :txtSearch OR TRIM(appFname) LIKE :txtSearch ")
                                                     .setParameter("txtSearch", "%"+ txtSearch +"%")
                                                     .list();
            txn.commit();

            listApplicant.stream().forEach(list::add);
            return list; 
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public ShApplicant getRowApplicantByAppNo(String appNo){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            List<ShApplicant> resultset = session.createQuery("FROM ShApplicant WHERE appNo = :appNo ")
                                                 .setParameter("appNo", appNo)
                                                 .list();
            txn.commit();
            if(resultset.size() > 0){
                return resultset.get(0);
            }else{
                return null;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public ShApplicant getRowApplicantByStudID(String studIdnum){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            List<ShApplicant> resultset = session.createQuery("FROM ShApplicant WHERE studIdnum = :studIdnum ")
                                                 .setParameter("studIdnum", studIdnum)
                                                 .list();
            txn.commit();
            if(resultset.size() > 0){
                return resultset.get(0);
            }else{
                return null;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public boolean updateStudentProfile(ShApplicant shApplicant){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(shApplicant);
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
