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
public class CustomApplicantAptitudeModel {
    private static Session session;
    
    public ObservableList<CustomApplicantAptitude> getResApplicantAptitude(String app_no) {
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            ObservableList<CustomApplicantAptitude> list = FXCollections.observableArrayList();
            Transaction txn = session.beginTransaction();
            String sql = "SELECT app_results.app_no,'SD' AS description,a_sd_s AS score_number,a_sd_l AS score_letter FROM app_results " +
                         "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.`app_no`,'FD' AS description,a_fd_s AS score_number,a_fd_l AS score_letter FROM `app_results` " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.`app_no`,'VE' AS description,a_ve_s AS score_number,a_ve_l AS score_letter FROM `app_results` " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.`app_no`,'NF' AS description,a_nf_s AS score_number,a_nf_l AS score_letter FROM `app_results`" +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.`app_no`,'IN' AS description,a_in_s AS score_number,a_in_l AS score_letter FROM `app_results` " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.`app_no`,'FC' AS description,a_fc_s AS score_number,a_fc_l AS score_letter FROM `app_results` " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.`app_no`,'VF' AS description,a_vf_s AS score_number,a_vf_l AS score_letter FROM `app_results` " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.`app_no`,'SA' AS description,a_sa_s AS score_number,a_sa_l AS score_letter FROM `app_results` " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.`app_no`,'MR' AS description,a_mr_s AS score_number,a_mr_l AS score_letter FROM `app_results` " +
                        "WHERE app_no = :app_no " +
                        "UNION " +
                        "SELECT app_results.app_no,'PA' AS description,a_pa_s AS score_number,a_pa_l AS score_letter FROM app_results " +
                        "WHERE app_no = :app_no ";

            List<Object[]> listApplicantAptitude = session.createSQLQuery(sql)
                                                            .setParameter("app_no", app_no)
                                                            .list();

            txn.commit();

            for(Object[] row: listApplicantAptitude){
                CustomApplicantAptitude customApplicantAptitude = new CustomApplicantAptitude();       
                customApplicantAptitude.setApp_no(row[0].toString());
                customApplicantAptitude.setDescription(row[1].toString());
                customApplicantAptitude.setScore_number(row[2].toString());
                customApplicantAptitude.setScore_letter(row[3].toString());
                
                list.add(customApplicantAptitude);
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
