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
public class ShUsersModel {
    private static Session session;
    
    public int cntUser(String username, String pwd){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        int tot_schedule = 0;
        try{
            
            String sql = "SELECT COUNT(username) AS cnt FROM `seniorhighdb`.`sh_users` WHERE username='"+username+"' AND pwd=PASSWORD('"+pwd+"') AND is_assess='1'";
            tot_schedule = Integer.parseInt(session.createSQLQuery(sql)
                            .uniqueResult().toString());
            txn.commit();  
        }catch(Exception ex){
            txn.rollback();
            ex.printStackTrace();
//            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        
        return tot_schedule;
    }
    
    
}
