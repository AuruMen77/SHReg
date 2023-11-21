/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShInstructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author ACER
 */
public class ShInstructorModel {
    private static Session session;
    public ObservableList<String> getInstructorsForCombobox() {
        
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(ShInstructor.class);
        criteria.setProjection(Projections.property("instructorName"));
        ObservableList<String> list = FXCollections.observableArrayList(criteria.list());
        session.getTransaction().commit();
        session.close(); session.getSessionFactory().close();
        
        return list;
    }
    
    public ObservableList<ShInstructor> getResInstructors(String search) {

        ObservableList<ShInstructor> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();

        String hql = "from ShInstructor ";
       
        int ctr = 1;
        String col = "", col2="", col3="";
        List<Object> listParameter = new ArrayList<>();
        if(!search.equals("")){
            col = "?" + ctr;
            ctr++;
            col2 = "?" + ctr;
            ctr++;
            col3= "?" + ctr;
            hql += " WHERE instructorId LIKE " +col+" OR instructorUname LIKE "+col2+ " OR instructorUname LIKE "+col3;
            listParameter.add("%" + search + "%");
            listParameter.add("%" + search + "%");
            listParameter.add("%" + search + "%");
            ctr++;
        }
        
        Query query = session.createQuery(hql);
        
        ctr = 1;
        Iterator iterator = listParameter.iterator();
        while(iterator.hasNext()) {
            query.setParameter(ctr , iterator.next());
            ctr++;
        }
        
        
        List<ShInstructor> listInstructors = query.list();
        

        txn.commit();
        session.close(); session.getSessionFactory().close();
         
        listInstructors.stream().forEach(list::add);

        return list;
    }
    
    public ShInstructor getRowInstructorByID(String instructorId){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        try{
            ShInstructor shInstructor = (ShInstructor) session.createQuery("FROM ShInstructor WHERE instructorId = :instructorId")
                    .setParameter("instructorId", instructorId)
                    .getSingleResult();
            
            txn.commit();
            return shInstructor;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        
    }
    

    
    public boolean saveInstructor(ShInstructor shInstructor){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(shInstructor);
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
    
    
    public boolean deleteInstructor(ShInstructor shInstructor) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.delete(shInstructor);
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
