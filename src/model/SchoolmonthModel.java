/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.MonthAttendance;
import entity.Schoolmonth;
import entity.ShDays;
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
public class SchoolmonthModel {
    private static Session session;

    
    public ObservableList<Schoolmonth> getResMonths() {

        ObservableList<Schoolmonth> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        List<Schoolmonth> listMonths = session.createQuery("FROM Schoolmonth ORDER BY monthnum").list();
        
        txn.commit();
        session.close(); session.getSessionFactory().close();
         
        listMonths.stream().forEach(list::add);
        
       
        return list;
    }
}
