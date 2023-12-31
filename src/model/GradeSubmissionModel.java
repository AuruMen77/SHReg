/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ClearanceSysem;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author CITS-Sheng
 */
public class GradeSubmissionModel {

    private static Session session;

    public int setSySemActive(String sy, Integer sem) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        int updateCount = 0;
        Integer isActive = 0;
        try {

            Query<Integer> checkQuery = session.createQuery(
                    "SELECT cs.is_active FROM ClearanceSysem cs "
                    + "WHERE cs.sy = :sy AND cs.sem = :sem", Integer.class)
                    .setParameter("sem", sem)
                    .setParameter("sy", sy);

            //SelectQuery for checking if SYSEM is in the database
            Query<Long> selectQuery = session.createQuery("SELECT COUNT(*) FROM ClearanceSysem "
                    + "WHERE sy = :sy AND sem = :sem ")
                    .setParameter("sem", sem)
                    .setParameter("sy", sy);

            //Resets everything back to 0 before activating specfic 1
            Query resetQuery = session.createQuery("UPDATE ClearanceSysem "
                    + "SET is_active = 0 ");

            //Activates the selected Sem 
            Query updateQuery = session.createQuery("UPDATE ClearanceSysem "
                    + "SET is_active = 1 "
                    + "WHERE sy = :sy AND sem = :sem ")
                    .setParameter("sem", sem)
                    .setParameter("sy", sy);

            isActive = checkQuery.uniqueResult();
            Long selectCount = selectQuery.uniqueResult();

            if (selectCount > 0 && isActive != null && isActive == 0 ) {
                resetQuery.executeUpdate();
                updateCount = updateQuery.executeUpdate();
            }
            else if (selectCount == 0){
                 updateCount = 0;
            }
            else {
                updateCount = -1; //-1 is used, any random negative number will also be fine.
            }
            txn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
            session.getSessionFactory().close();
        }
        return updateCount;
    }
}
