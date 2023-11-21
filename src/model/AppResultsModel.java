/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.AppResults;
import entity.CustomApplicantAptitude;
import entity.CustomApplicantEducProgram;
import entity.ShApplicant;
import entity.ShConfirmation;
import entity.ShCurrHdr;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author ACER
 */
public class AppResultsModel {
    private static Session session;
    
   
    
    public AppResults getRowApplicantResult(ShApplicant shApplicant) {
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            AppResults appResults= (AppResults) session.createQuery("FROM AppResults WHERE shApplicant = :shApplicant ")
                                               .setParameter("shApplicant", shApplicant)
                                               .getSingleResult();
            
            Hibernate.initialize(appResults.getShApplicant()); //--- use when @ManyToOne(fetch=FetchType.LAZY)
//            for(AppResults file : resultset){
//                Hibernate.initialize(file.getShApplicant());
//            }
            txn.commit();

//            if (resultset.size() > 0) {
//                return resultset.get(0); 
//            } else {
//                return null;
//            }
            
            return appResults;
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        }  
    }
    
   
   
}
