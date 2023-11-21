/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShCurrHdr;
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
public class StrandsModel {
    private static Session session;
    public ObservableList<String> getStrandsForCombobox() {
        
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Strands.class);
        criteria.setProjection(Projections.property("strandCode"));
        ObservableList<String> list = FXCollections.observableArrayList(criteria.list());
        session.getTransaction().commit();
        session.close(); session.getSessionFactory().close();
        
        return list;
    }
    
    public ObservableList<Strands> getResStrand() {

        ObservableList<Strands> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        List<Strands> listStrand = session.createQuery("from Strands").list();
        
        
        for(Strands row : listStrand) {
            System.out.println(row.getStrandName());
        }

        txn.commit();
        session.close(); session.getSessionFactory().close();
         
        listStrand.stream().forEach(list::add);
        

        return list;
    }
    
    
    public Strands getRowStrandByCode(String strandCode) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        try{
            
            Strands strands = (Strands) session.createQuery("from Strands WHERE strandCode = :strandCode ")
                    .setParameter("strandCode", strandCode)
                    .getSingleResult();



            txn.commit();
            session.close(); session.getSessionFactory().close();


            return strands;
        }catch(Exception ex){
            txn.rollback();
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
}
