/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShCourseList;
import entity.ShCurrHdr;
import entity.ShCurrSy;
import entity.ShInstructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author ACER
 */
public class ShCourseListModel {
    private static Session session;
    
    public ObservableList<ShCourseList> getResSubjectList(String crsType, String search ) {
        ObservableList<ShCourseList> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        String hql = "from ShCourseList WHERE crsId > 0 ";
       
        int ctr = 1;
        String col = "", col2="";
        List<Object> listParameter = new ArrayList<>();
        
        if(!crsType.equals("all")){
            col = "?" + ctr;
            hql += " AND crsType = " + col;
            listParameter.add(crsType);
            ctr++;
        }
        
        if(!search.equals("")){
            col = "?" + ctr;
            ctr++;
            col2 = "?" + ctr;
            hql += " AND (crsCode LIKE " + col + " OR crsTitle LIKE " + col2 + ") ";
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
        
        
        List<ShCourseList> listCourse = query.list();

        txn.commit();
         
        listCourse.stream().forEach(list::add);
        session.close(); session.getSessionFactory().close();
        return list;
    }
    
    public ObservableList<String> getResSubjectCodeListForCbox() {      
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        Criteria criteria = session.createCriteria(ShCourseList.class);
        criteria.setProjection(Projections.property("crsCode"));
//        criteria.addOrder(Order.asc("crsCode"));
        ObservableList<String> listSubject = FXCollections.observableArrayList(criteria.list());                             
        txn.commit();
        session.close(); session.getSessionFactory().close();
         
        return listSubject;
    }
  
    public ShCourseList getCourse(Integer id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        ShCourseList shCourseList = session.get(ShCourseList.class, id);
        session.getTransaction().commit();
        session.close(); session.getSessionFactory().close();

        return shCourseList;
    }
    
    public ShCourseList getCourseByCode(String crsCode) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        ShCourseList shCourseList = (ShCourseList) session.createQuery("FROM ShCourseList WHERE crsCode = :crsCode")
                .setParameter("crsCode", crsCode)
                .getSingleResult();
        session.getTransaction().commit();
        session.close(); session.getSessionFactory().close();

        return shCourseList;
    }
    
    public boolean saveCourse(ShCourseList shCourseList){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(shCourseList);
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
    
    
    public boolean deleteCourse(ShCourseList shCourseList) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.delete(shCourseList);
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
