/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author ACER
 */
public class ShSectionsModel {
    private static Session session;
    
    public ObservableList<String> getStrandgroupsForCombobox() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        
        ObservableList<String> list = FXCollections.observableArrayList(session.createQuery("SELECT section FROM ShSections").list());

        txn.commit();
        session.close(); session.getSessionFactory().close();

      
        return list;
    }
    
   
}
