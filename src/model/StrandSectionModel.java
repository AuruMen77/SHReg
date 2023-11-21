/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShCurrHdr;
import entity.StrandSection;
import entity.Strands;
import java.math.BigDecimal;
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
import org.hibernate.stat.Statistics;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author ACER
 */
public class StrandSectionModel {
    private static Session session;
    
    public ObservableList<String> getSectionsForCombobox(String sy, String sem, String gradeLevel, String strand) {
       
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        
        String sql = "SELECT studSection FROM StrandSection WHERE sy= :sy  AND sem = ?2 ";
        
        String col = "";
        int ctr = 3;
        List<Object> listParameter = new ArrayList<>();
        if(!gradeLevel.equals("all")){
            col = "?" + ctr;
            sql += " AND gradeLevel = " + col;
            listParameter.add(Integer.parseInt(gradeLevel));
            ctr++;
        }
        
        if(!strand.equals("all")){
            col = "?" + ctr;
            sql += " AND strand = " + col;
            listParameter.add(strand);
            ctr++;
        }
        
        Query query = session.createQuery(sql)
                .setParameter("sy", sy)
                .setParameter(2, Integer.parseInt(sem));
        
        ctr = 3;
        Iterator iterator = listParameter.iterator();
        while(iterator.hasNext()) {
            query.setParameter(ctr, iterator.next());
            ctr++;
        }
        
        ObservableList<String> list = FXCollections.observableArrayList(query.list());
        

        txn.commit();
        session.close(); session.getSessionFactory().close();

      
        return list;
    }
    
    public ObservableList<StrandSection> getResSection(String sy, String sem, String gradeLevel, String strand) {
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            ObservableList<StrandSection> list = FXCollections.observableArrayList();
            Transaction txn = session.beginTransaction();
            
            String sql = "FROM StrandSection WHERE sy = ?1  AND sem = ?2 ";
            
            int ctr = 3;
            String col = "";
            List<Object> listParameter = new ArrayList<>();
            if(!gradeLevel.equals("all")){
                col = "?" + ctr;
                sql += " AND gradeLevel = " + col;
                listParameter.add(Integer.parseInt(gradeLevel));
                ctr++;
            }

            if(!strand.equals("all")){
                col = "?" + ctr;
                sql += " AND strand = " + col;
                listParameter.add(strand);
                ctr++;
            }
       

            Query query = session.createQuery(sql)
                    .setParameter(1, sy)
                    .setParameter(2, Integer.parseInt(sem));
            
            ctr = 3;
            Iterator iterator = listParameter.iterator();
            while(iterator.hasNext()) {
                query.setParameter(ctr , iterator.next());
                ctr++;
            }
            
            List<StrandSection> listSection = query.list();

            txn.commit();

            listSection.stream().forEach(list::add);
            return list; 
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public String getStrandgroupSectionDesc(String sy, String sem, String gradeLevel, String strand, String strandgroup){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            StrandSection strandSection =  (StrandSection) session.createQuery("FROM StrandSection " +
                    "WHERE sy = :sy AND sem = :sem AND gradeLevel = :gradeLevel AND strand = :strand AND strandgroup = :strandgroup ")
                    .setParameter("sy", sy)
                    .setParameter("sem", Integer.parseInt(sem))
                    .setParameter("gradeLevel", Integer.parseInt(gradeLevel))
                    .setParameter("strand", strand)
                    .setParameter("strandgroup", strandgroup)
                    .getSingleResult();
            
            String studSection = strandSection.getStudSection();
           
            txn.commit();
            return studSection;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public StrandSection getRowSection(String sy, String sem, String gradeLevel, String strand, String studSection){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            StrandSection strandSection = (StrandSection) session.createQuery("FROM StrandSection " +
                    "WHERE sy = :sy AND sem = :sem AND gradeLevel = :gradeLevel AND strand = :strand AND studSection = :studSection ")
                    .setParameter("sy", sy)
                    .setParameter("sem", Integer.parseInt(sem))
                    .setParameter("gradeLevel", Integer.parseInt(gradeLevel))
                    .setParameter("strand", strand)
                    .setParameter("studSection", studSection)
                    .getSingleResult();
            txn.commit();
            return strandSection;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public boolean saveSection(StrandSection strandSection) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tran = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(strandSection);
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
