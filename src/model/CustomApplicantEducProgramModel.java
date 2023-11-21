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
public class CustomApplicantEducProgramModel {
    private static Session session;
    
    
    
     public ObservableList<CustomApplicantEducProgram> getResApplicantEducProgram(String app_no) {
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            ObservableList<CustomApplicantEducProgram> list = FXCollections.observableArrayList();
            Transaction txn = session.beginTransaction();
            String sql = "SELECT app_results.app_no,'SE' AS description,ep_se AS rating FROM app_results " +
                         "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.app_no,'AG' AS description,ep_ag AS rating FROM app_results " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.app_no,'HA' AS description,ep_ha AS rating FROM app_results " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.app_no,'SBL' AS description,ep_sbl AS rating FROM app_results " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.app_no,'ED' AS description,ep_ed AS rating FROM app_results " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.app_no,'EMC' AS description,ep_emc AS rating FROM app_results " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                       "SELECT app_results.app_no,'TC' AS description,ep_tc AS rating FROM app_results " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.app_no,'HW' AS description,ep_hw AS rating FROM app_results " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.app_no,'SC' AS description,ep_sc AS rating FROM app_results " +
                        "WHERE app_no = :app_no " ;

            List<Object[]> listApplicantEducProgram = session.createSQLQuery(sql)
                                                            .setParameter("app_no", app_no)
                                                            .list();

            txn.commit();

            for(Object[] row: listApplicantEducProgram){
                CustomApplicantEducProgram customApplicantEducProgram = new CustomApplicantEducProgram();       
                customApplicantEducProgram.setApp_no(row[0].toString());
                customApplicantEducProgram.setDescription(row[1].toString());
                
                if(row[2].toString().equals("1"))
                   customApplicantEducProgram.setRating("Low");
                else if(row[2].toString().equals("2"))
                   customApplicantEducProgram.setRating("Average");
                else if(row[2].toString().equals("3"))
                   customApplicantEducProgram.setRating("High");
                
                list.add(customApplicantEducProgram);
            }

            return list;
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        }  
    }
    
  
    
   
}
