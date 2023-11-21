/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShTermReg;
import org.hibernate.Session;
import org.hibernate.Transaction;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author CITS-Sheng
 */
public class ShTermRegModel {
    private static Session session;
    public ShTermReg getRowSYSem(){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            ShTermReg shTermReg = (ShTermReg) session.createQuery("FROM ShTermReg WHERE isCurSysem = 1 ")
                                                .getSingleResult();
            txn.commit();
            return shTermReg;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public ShTermReg getRowCurrentEnrollment(){
        session = HibernateUtil.getSessionFactory().openSession();

        try{
            Transaction txn = session.beginTransaction();
            ShTermReg shTermReg = (ShTermReg) session.createQuery("FROM ShTermReg WHERE isEnrollment = 1 ")
                                                .getSingleResult();
            txn.commit();
            System.out.println(shTermReg);
            return shTermReg;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public ShTermReg getRowCurrentPromotion(){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            ShTermReg shTermReg = (ShTermReg) session.createQuery("FROM ShTermReg WHERE isPromotion = 1 ")
                                                .getSingleResult();
            txn.commit();
            return shTermReg;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public ShTermReg getRowCurrentGradeSubmission(){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            ShTermReg shTermReg = (ShTermReg) session.createQuery("FROM ShTermReg WHERE onlineGradeSubmission = 1 ")
                                                .getSingleResult();
            txn.commit();
            return shTermReg;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public ShTermReg getRowCurrentEvalForStud(){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            ShTermReg shTermReg = (ShTermReg) session.createQuery("FROM ShTermReg WHERE isEvalForStud = 1 ")
                                                .getSingleResult();
            txn.commit();
            return shTermReg;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public ShTermReg getRowCurrentAssessment(){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            ShTermReg shTermReg = new ShTermReg();
            shTermReg = (ShTermReg) session.createQuery("FROM ShTermReg WHERE isAssessment = 1 ")
                                                .getSingleResult();
            txn.commit();
            return shTermReg;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    
    public boolean checkAssessmentOfSySem(String syReg, String semReg){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            ShTermReg shTermReg = (ShTermReg) session.createQuery("FROM ShTermReg WHERE syReg = :syReg AND semReg = :semReg ")
                    .setParameter("syReg", syReg)
                    .setParameter("semReg", Integer.parseInt(semReg))
                    .getSingleResult();
            
            txn.commit();
           
            return shTermReg.getIsAssessment();
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    
    public ShTermReg getAdmissionDate(){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            ShTermReg shTermReg = (ShTermReg) session.createQuery("FROM ShTermReg WHERE syReg = :syReg AND semReg = :semReg ")
                    .setParameter("syReg", "2016-2017")
                    .setParameter("semReg", 1)
                    .getSingleResult();
            
            txn.commit();
           
            return shTermReg;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public boolean updateStudentProfile(ShTermReg shTermReg){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(shTermReg);
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
