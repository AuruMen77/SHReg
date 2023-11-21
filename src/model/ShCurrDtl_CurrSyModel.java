/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShCourseList;
import entity.ShCurrDtl;
import entity.ShCurrHdr;
import entity.ShCurrSy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author ACER
 */
public class ShCurrDtl_CurrSyModel {
    private static Session session;
    
    
    public ObservableList<ShCurrDtl> getResCurriculumSubjects(String sy, String sem, String strandcode, String yrlevel) {
        ObservableList<ShCurrDtl> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction txn = session.beginTransaction();

    //        String sql = "SELECT curr_sy_id,sy,curr_hdr_id,curr_name,curr_desc,strandcode, " +
    //                "curr_dtl_id,yrlevel,sem,crs_id,crs_code,pre_crs_code,crs_title,crs_unit,with_lab,crs_type " +
    //                "FROM `sh_curr_sy`  " +
    //                "LEFT JOIN  `sh_curr_hdr` ON sh_curr_sy.`f_curr_hdr`=sh_curr_hdr.`curr_hdr_id` " +
    //                "LEFT JOIN `sh_curr_dtl` ON sh_curr_hdr.`curr_hdr_id`=sh_curr_dtl.`f_curr_hdr` " +
    //                "LEFT JOIN `sh_course_list` ON sh_curr_dtl.`f_course`=sh_course_list.`crs_id` " +
    //                "WHERE sy = '"+sy+"' AND sem= '"+sem+"' AND strandcode= '"+strandcode+"' ";

            List<ShCurrDtl> listCurrSubject = new ArrayList();
            
            
            if(yrlevel.equals("all")){
               listCurrSubject = session.createQuery("SELECT a FROM ShCurrDtl a LEFT JOIN ShCurrSy b "
                     + "ON a.shCurrHdr = b.shCurrHdr WHERE b.sy= :sy AND a.sem = :sem AND a.shCurrHdr.strandcode = :strandcode ")
                        .setParameter("sy", sy)
                        .setParameter("sem", Integer.parseInt(sem))
                        .setParameter("strandcode", strandcode)
                        .list(); 
            }else{
                listCurrSubject = session.createQuery("SELECT a FROM ShCurrDtl a LEFT JOIN ShCurrSy b "
                     + "ON a.shCurrHdr = b.shCurrHdr WHERE b.sy= :sy AND a.sem = :sem AND a.shCurrHdr.strandcode = :strandcode AND a.yrlevel = :yrlevel ")
                        .setParameter("sy", sy)
                        .setParameter("sem", Integer.parseInt(sem))
                        .setParameter("strandcode", strandcode)
                        .setParameter("yrlevel", Integer.parseInt(yrlevel))
                        .list(); 
            }
            
            txn.commit();
            

//            if(yrlevel.equals("all")){
//               listCurrSubject = session.createQuery("SELECT a FROM ShCurrDtl a LEFT JOIN ShCurrSy b "
//                     + "ON a.shCurrHdr = b.shCurrHdr WHERE b.sy= :sy AND a.sem = :sem ")
//                        .setParameter("sy", sy)
//                        .setParameter("sem", Integer.parseInt(sem))
//                        .list(); 
//            }else{
//    //            listCurrSubject = session.createQuery("SELECT a FROM ShCurrDtl a "
//    //                 + "LEFT JOIN ShCurrSy b ON a.shCurrHdr = b.shCurrHdr "
//    //                 + "WHERE b.sy= :sy AND a.sem = :sem AND a.yrlevel = :yrlevel shCurrHdr.strandcode = :strandcode ")
//    //                    .setParameter("sy", sy)
//    //                    .setParameter("sem", Integer.parseInt(sem))
//    //                    .setParameter("yrlevel", Integer.parseInt(yrlevel))
//    //                    .setParameter("strandcode", strandcode)
//    //                    .list();
//
//System.out.println("VAL "+ sy +" "+ sem +" "+sem +" "+ yrlevel +" "+strandcode);
//                 List<Object[]> listObject = session.createSQLQuery("SELECT a.* FROM sh_curr_dtl a "
//                     + "LEFT JOIN sh_curr_sy b ON a.f_curr_hdr = b.f_curr_hdr "
//                     + "LEFT JOIN sh_curr_hdr c ON a.f_curr_hdr = c.curr_hdr_id " 
//                     + "WHERE b.sy= :sy AND a.sem = :sem AND a.yrlevel = :yrlevel AND c.strandcode = :strandcode ")
//                        .setParameter("sy", sy)
//                        .setParameter("sem", Integer.parseInt(sem))
//                        .setParameter("yrlevel", Integer.parseInt(yrlevel))
//                        .setParameter("strandcode", strandcode)
//                        .list();
//
//                 for(Object[] row: listObject){
//                     ShCurrDtl shCurrDtl = new ShCurrDtl();
//                     shCurrDtl.setCurrDtlId(Integer.parseInt(row[0].toString()));
//                     shCurrDtl.setShCurrHdr(new ShCurrHdrModel().getRowCurriculumHdr((int)row[1]));
//                     shCurrDtl.setShCourseList(new ShCourseListModel().getCourse((int)row[2]));
//                     shCurrDtl.setYrlevel(Integer.parseInt(row[3].toString()));
//                     shCurrDtl.setSem(Integer.parseInt(row[4].toString()));
//                     shCurrDtl.setDateCreated((Date)row[5]);
//                     shCurrDtl.setTs((Date)row[6]);
//                     listCurrSubject.add(shCurrDtl);
//                 }
//            }



            for(ShCurrDtl file : listCurrSubject){
                Hibernate.initialize(file.getShCourseList());
                Hibernate.initialize(file.getShCurrHdr());
                file.setCustomSy(sy);
                file.setCustomFCurrSy(strandcode);
            }

           

            listCurrSubject.stream().forEach(list::add);

            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        return list;
    }
}
