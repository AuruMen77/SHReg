/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import entity.ClearanceSysem;
import entity.ClearanceOffice;
import entity.ClearanceOfficeSysem;
import entity.ClearanceHoldlist;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author CITS-Sheng
 */
public class ClearanceSysemModel {

    private static Session session;

    public int setSySemActive(String sy, Integer sem) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();

        Integer prevSem = 0;
        String prevSy = "";
        if (sem == 2) {
            prevSem = 1;
            prevSy = sy;
        } else if (sem == 1) {
            prevSem = 2;
            prevSy = reduceSchoolYear(sy);
        }

        int updateCount = 0;
        Integer isActive = 0;
        Integer sysem_id = 0;
        try {

            Query<Integer> checkQuery = session.createQuery(
                    "SELECT cs.is_active FROM ClearanceSysem cs "
                    + "WHERE cs.sy = :sy AND cs.sem = :sem", Integer.class)
                    .setParameter("sem", sem)
                    .setParameter("sy", sy);

            Query<Integer> getYearQuery = session.createQuery(
                    "SELECT cs.sysem_id FROM ClearanceSysem cs "
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
            sysem_id = getYearQuery.uniqueResult();
            Long selectCount = selectQuery.uniqueResult();

            if (selectCount > 0 && isActive != null && isActive == 0) {
                resetQuery.executeUpdate();
                updateCount = updateQuery.executeUpdate();

                // adds the 10 offices in the clearance_office_sysem table
                for (int i = 1; i <= 10; i++) {
                    ClearanceOfficeSysem officeSysem = new ClearanceOfficeSysem();
                    officeSysem.setf_office(i);
                    officeSysem.setf_sysem(sysem_id);
                    session.save(officeSysem);
                }

                //Select the off_sysem_id for f_off_sysem;
                List<Integer> getFOffSysemList = session.createQuery(
                        "SELECT off_sysem_id FROM ClearanceOfficeSysem "
                        + "WHERE f_sysem = :sysem_id", Integer.class)
                        .setParameter("sysem_id", sysem_id).list();

                for (Integer fOffSysemValue : getFOffSysemList) {
                    Query insertToHoldlist = session.createSQLQuery(
                            "INSERT INTO seniorhighdb.clearance_holdlist "
                            + "(f_off_sysem, student_id, grade_level, remarks_hold, current_status) "
                            + "SELECT :fOffSysem, stud_idnum, ss_yr_level, 'default hold', 'hold' "
                            + "FROM sh_stud_strand a "
                            + "WHERE a.ss_sy = :prevSy AND a.ss_sem = :prevSem")
                            .setParameter("prevSy", prevSy)
                            .setParameter("prevSem", prevSem)
                            .setParameter("fOffSysem", fOffSysemValue);

                    int rowsAffected = insertToHoldlist.executeUpdate();
                }
            } else if (selectCount == 0) {
                updateCount = 0;
            } else {
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

    public static String reduceSchoolYear(String sy) {
        // Assuming the input format is "YYYY-YYYY"
        String[] parts = sy.split("-");

        if (parts.length == 2) {
            int startYear = Integer.parseInt(parts[0]);
            int endYear = Integer.parseInt(parts[1]);

            // Reduce both start and end years by 1
            startYear--;
            endYear--;

            // Format the result
            return String.format("%d-%d", startYear, endYear);
        } else {
            // Handle invalid input format
            return "Invalid input format";
        }
    }

}
