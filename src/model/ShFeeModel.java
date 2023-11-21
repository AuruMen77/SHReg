/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShCurrHdr;
import entity.ShFee;
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
public class ShFeeModel {
    private static Session session;
    
    public ObservableList<ShFee> getResFeeByGradelevelAndStrand(String term, String sem, String gradelevel, String strandCode) {
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            ObservableList<ShFee> list = FXCollections.observableArrayList();
            Transaction txn = session.beginTransaction();
            
            List<ShFee> listFee = session.createQuery("FROM ShFee WHERE term = :term  AND sem = :sem AND gradelevel = :gradelevel AND strandCode = :strandCode")
                    .setParameter("term", term)
                    .setParameter("sem", Integer.parseInt(sem))
                    .setParameter("gradelevel", Integer.parseInt(gradelevel))
                    .setParameter("strandCode", strandCode)
                    .list();
            txn.commit();

            listFee.stream().forEach(list::add);
            return list; 
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public String getTotalFeeByGradelevelAndStrand(String term, String sem, String gradelevel, String strand_code ) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        try{

            String tot_fee = session.createSQLQuery("SELECT IFNULL(SUM(`fee`),0) AS tot_fee " +
                    "FROM `sh_fee` WHERE  `term` = :term " +
                    "AND `sem` = :sem AND `gradelevel` = :gradelevel  " +
                    "AND `strand_code` = :strand_code")
                    .setParameter("term", term)
                    .setParameter("sem", sem)
                    .setParameter("gradelevel", gradelevel)
                    .setParameter("strand_code", strand_code)
                    .uniqueResult().toString();
            txn.commit();

         
            return tot_fee;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public String getTotalSummerFee(String term, String sem, String gradelevel, String strand_code, String idnum) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        try{
            
            long cntSubjects = (long) session.createQuery("SELECT COUNT(*) FROM ShCClassStud WHERE csSy = :csSy AND csSem = :csSem AND csIdnum = :csIdnum")
                    .setParameter("csSy", term)
                    .setParameter("csSem", Integer.parseInt(sem))
                    .setParameter("csIdnum", idnum)
                     .uniqueResult();        
            
            
            int cntSubj = (int) cntSubjects;
            
            String fee_aca = session.createSQLQuery("SELECT fee " +
                    "FROM `sh_fee` WHERE  `term` = :term " +
                    "AND `sem` = :sem AND `gradelevel` = :gradelevel  " +
                    "AND `strand_code` = :strand_code AND `code` LIKE :code ;")
                    .setParameter("term", term)
                    .setParameter("sem", sem)
                    .setParameter("gradelevel", gradelevel)
                    .setParameter("strand_code", strand_code)
                    .setParameter("code", "%ACA%")
                    .uniqueResult().toString();            
            double fee_aca_temp = Double.parseDouble(fee_aca);
            fee_aca_temp = fee_aca_temp * cntSubj;
            
            String fee_misc = session.createSQLQuery("SELECT fee " +
                    "FROM `sh_fee` WHERE  `term` = :term " +
                    "AND `sem` = :sem AND `gradelevel` = :gradelevel  " +
                    "AND `strand_code` = :strand_code AND `code` = :code")
                    .setParameter("term", term)
                    .setParameter("sem", sem)
                    .setParameter("gradelevel", gradelevel)
                    .setParameter("strand_code", strand_code)
                    .setParameter("code", "MISC")
                    .uniqueResult().toString();            
            double fee_misc_temp = Double.parseDouble(fee_misc);        
            
            txn.commit();

            String tot_fee = String.valueOf(fee_aca_temp + fee_misc_temp);
            
            return tot_fee;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    
}
