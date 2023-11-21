/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ShClassInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import shregistrarjavafxml.HibernateUtil;

/**
 *
 * @author CITS-Sheng
 */
public class ShClassInfoModel {
    private static Session session;
    
    public int cntScheduleByCurrSubj(String claSy, String claSem){
        session = HibernateUtil.getSessionFactory().openSession();
        int tot_schedule = 0;
        try{
            Transaction txn = session.beginTransaction();
            tot_schedule = Integer.parseInt(session.createQuery("SELECT COUNT(claCrsCode) AS cnt FROM ShClassInfo WHERE claSy = :claSy AND claSem = :claSem ")
                            .setParameter("claSy", claSy)
                            .setParameter("claSem", Integer.parseInt(claSem))
                            .uniqueResult().toString());
            txn.commit();  
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
        
        return tot_schedule;
    }

    public boolean saveSchedule(ShClassInfo shClassInfo) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.saveOrUpdate(shClassInfo);
            txn.commit();
            success = true;
        } catch (Exception e) {
            txn.rollback();
            e.printStackTrace();
            success = false;
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return success;
    }
    
    public boolean saveScheduleBatch(int cnt, String sy, String sem, ObservableList<ShClassInfo> listShClassInfo) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            
            String setClassid;
            int i = 0;
            for(ShClassInfo shClassInfo:listShClassInfo){
              
                if ( i % 20 == 0 ) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                
                setClassid = shClassInfo.getFCurrSy() + "_" + shClassInfo.getFCurrDtl() + "_" + cnt +"_"+ i;
                
                
                ShClassInfo save_shClassInfo = new ShClassInfo();
                save_shClassInfo.setClassid(setClassid);
                save_shClassInfo.setFCurrSy(shClassInfo.getFCurrSy());
                save_shClassInfo.setFCurrDtl(shClassInfo.getFCurrDtl());
                save_shClassInfo.setClaCrsCode(shClassInfo.getClaCrsCode());
                save_shClassInfo.setClaSection(shClassInfo.getClaSection());
                save_shClassInfo.setClaSy(sy);
                save_shClassInfo.setClaSem(Integer.parseInt(sem));
                
                save_shClassInfo.setClaYrLevel(shClassInfo.getClaYrLevel());
                save_shClassInfo.setClaDay(shClassInfo.getClaDay());
                save_shClassInfo.setClaTStartdesc(shClassInfo.getClaTStartdesc());
                save_shClassInfo.setClaTStart(shClassInfo.getClaTStart());
                save_shClassInfo.setClaTEnddesc(shClassInfo.getClaTEnddesc());
                save_shClassInfo.setStrandcode(shClassInfo.getStrandcode());
                save_shClassInfo.setStrandgroup(shClassInfo.getStrandgroup());
                save_shClassInfo.setClaRoom(shClassInfo.getClaRoom());
                
                
                session.save(save_shClassInfo);
                cnt++;
                i++;
            }
            
            
            txn.commit();
            success = true;
        } catch (Exception e) {
            txn.rollback();
            e.printStackTrace();
            success = false;
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return success;
    }
    
    public ObservableList<ShClassInfo> getResSchedule(String claSy, String claSem, String claYrLevel, String strandcode, String strandgroup ){
        session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<ShClassInfo> list = FXCollections.observableArrayList();
        Transaction txn = session.beginTransaction();
        
        String sql = "FROM ShClassInfo WHERE claSy = :claSy AND claSem = :claSem ";
       
        int ctr = 1;
        String col = "";
        List<Object> listParameter = new ArrayList<>();
        if(!claYrLevel.equals("all")){
            col = "?" + ctr;
            sql += " AND claYrLevel = "+col;
            listParameter.add(Byte.valueOf(claYrLevel));
            ctr++;
        }
        
        if(!strandcode.equals("all")){
            col = "?" + ctr;
            sql += " AND strandcode = "+col;
            listParameter.add(strandcode);
            ctr++;
        }
        
        if(!strandgroup.equals("all")){
            col = "?" + ctr;
            sql += " AND strandgroup =  "+col;
            listParameter.add(strandgroup);
            ctr++;
        }
        
        Query query = session.createQuery(sql)
                .setParameter("claSy", claSy)
                .setParameter("claSem", Integer.parseInt(claSem));
        
        ctr = 1;
        Iterator iterator = listParameter.iterator();
        while(iterator.hasNext()) {
            query.setParameter(ctr , iterator.next());
            ctr++;
        }
        
        
        List<ShClassInfo> listApplicant = query.list();
        txn.commit();
        session.close(); session.getSessionFactory().close();

        listApplicant.stream().forEach(list::add);  

        return list; 
    }
    
    public ShClassInfo getRowScheduleByID(String classid){
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();
            ShClassInfo shClassInfo = (ShClassInfo) session.createQuery("FROM ShClassInfo WHERE classid = :classid ")
                                                 .setParameter("classid", classid)
                                                 .getSingleResult();
            txn.commit();
            return shClassInfo;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
    
    public ShClassInfo getRowStudentModeratingSchedule(String claSy, String claSem, String claYrLevel, String strandcode, String strandgroup){
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("VAL: "+claSy +" "+ claSem +" "+ claYrLevel +" "+ strandcode +" "+ strandgroup);
        try{
            Transaction txn = session.beginTransaction();
            ShClassInfo shClassInfo = (ShClassInfo) session.createQuery("FROM ShClassInfo "
                    + "WHERE claCrsCode='Homeroom' AND claSy = :claSy AND claSem = :claSem AND claYrLevel = :claYrLevel AND strandcode = :strandcode AND strandgroup = :strandgroup  ")
                    .setParameter("claSy", claSy)
                    .setParameter("claSem", Integer.parseInt(claSem))
                    .setParameter("claYrLevel", Byte.parseByte(claYrLevel))
                    .setParameter("strandcode", strandcode)
                    .setParameter("strandgroup", strandgroup)
                    .getSingleResult();
            txn.commit();
            return shClassInfo;
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();  
        }
    }
    

     
    
    public boolean deleteSchedule(ShClassInfo shClassInfo) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        boolean success = false;
        try{
            session.delete(shClassInfo);
            txn.commit();
            success = true;
        } catch (Exception e) {
            txn.rollback();
            e.printStackTrace();
            success = false;
        } finally {
            session.close(); session.getSessionFactory().close();   
        }
        return success;
    }
    
     public ObservableList<ShClassInfo> getResSubjectSchedules(String claSy, String claSem, String claYrLevel, 
            String strandcode,String strandgroup, String search ) {
        ObservableList<ShClassInfo> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction txn = session.beginTransaction();

            String sql = "SELECT a FROM ShClassInfo a " +
                         "LEFT JOIN ShCourseList b ON a.claCrsCode = b.crsCode " +
                         "WHERE a.claSy = :claSy AND a.claSem = :claSem ";

            List<ShClassInfo> listSubjectSchedules;

            int ctr = 1;
            String col = "";
            List<Object> listParameter = new ArrayList<>();
            if(!claYrLevel.equals("all")){
                col = "?" + ctr;
                sql += " AND a.claYrLevel = "+col;
                listParameter.add(Byte.valueOf(claYrLevel));
                ctr++;
            }

            if(!strandcode.equals("all")){
                col = "?" + ctr;
                sql += " AND a.strandcode = "+col;
                listParameter.add(strandcode);
                ctr++;
            }

            if(!strandgroup.equals("")){
                col = "?" + ctr;
                sql += " AND a.strandgroup = "+col;
                listParameter.add(strandgroup);
                ctr++;
            }
            if(!search.equals("")){
                col = "?" + ctr;
                sql += " AND a.claCrsCode LIKE "+col;
                listParameter.add(search + "%");
                ctr++;
            }


            Query query = session.createQuery(sql)
                    .setParameter("claSy", claSy)
                    .setParameter("claSem", Integer.parseInt(claSem));

            ctr = 1;
            Iterator iterator = listParameter.iterator();
            while(iterator.hasNext()) {
                query.setParameter(ctr , iterator.next());
                ctr++;
            }

            List<ShClassInfo> listSubjectSchedule = query.list();


            txn.commit();

            listSubjectSchedule.stream().forEach(list::add);  

            return list;
        } catch (Exception ex) {
             ex.printStackTrace();
             throw ex;
        } finally {
            session.close(); session.getSessionFactory().close();
        }  
    }
     
     
    //--- START sql query ---//
     public ObservableList<ShClassInfo> getResClass(String cla_sy, String cla_sem, String cla_yr_level, 
            String strandcode, String stud_section, String instructor_name) {
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            ObservableList<ShClassInfo> list = FXCollections.observableArrayList();
            Transaction txn = session.beginTransaction();
            
            String sql = "SELECT classid,cla_sy AS sy,cla_sem AS sem, cla_yr_level AS grade_level, TRIM(strandcode) AS strand_code,TRIM(sh_class_info.strandgroup) AS strand_group,  " +
            "IFNULL(stud_section,'') AS stud_section,IFNULL(instructor_id,'') AS instructor_id, IFNULL(instructor_name,'') AS instructor_name,cla_crs_code,crs_title,cla_section " +
            "FROM sh_class_info  " +
            "LEFT JOIN strand_section  ON cla_sy=sy AND cla_sem=sem AND cla_yr_level= grade_level " +
            "AND strandcode=strand AND sh_class_info.strandgroup=strand_section.strandgroup " +
            "LEFT JOIN sh_instructor  ON cla_tea_idnum=instructor_id " +
            "LEFT JOIN sh_course_list ON sh_class_info.cla_crs_code = sh_course_list.crs_code " +
            "WHERE is_class_basis='1' AND cla_sy= ?1 AND cla_sem = ?2  ";
            
            int ctr = 3;
            String col = "";
            List<Object> listParameter = new ArrayList<>();
            if(!cla_yr_level.equals("all")){
                col = "?" + ctr;
                sql += " AND cla_yr_level = " + col;
                listParameter.add(Byte.valueOf(cla_yr_level));
                ctr++;
            }

            if(!strandcode.equals("all")){
                col = "?" + ctr;
                sql += " AND strandcode = " + col;
                listParameter.add(strandcode);
                ctr++;
            }
            
            if(!stud_section.equals("all")){
                col = "?" + ctr;
                sql += " AND stud_section = " + col;
                listParameter.add(stud_section);
                ctr++;
            }
            
            if(!instructor_name.equals("")){
                col = "?" + ctr;
                sql += " AND instructor_name LIKE " + col;
                listParameter.add("%" + instructor_name + "%");
                ctr++;
            }
       
            sql +=" GROUP BY cla_sy, cla_sem, cla_yr_level, strandcode, sh_class_info.strandgroup,cla_crs_code  ";
            Query query = session.createSQLQuery(sql)
                    .setParameter(1, cla_sy)
                    .setParameter(2, Integer.parseInt( cla_sem));
            
            ctr = 3;
            Iterator iterator = listParameter.iterator();
            while(iterator.hasNext()) {
                query.setParameter(ctr , iterator.next());
                ctr++;
            }
            
            List<Object[]> listClass = query.list();

            txn.commit();
            
            for(Object[] row : listClass){
                ShClassInfo shClassInfo= new ShClassInfo();  
                shClassInfo.setClassid(row[0].toString());
                shClassInfo.setClaSy(row[1].toString());
                shClassInfo.setClaSem(Integer.parseInt(row[2].toString()));
                shClassInfo.setClaYrLevel(Byte.parseByte(row[3].toString()));
                shClassInfo.setStrandcode(row[4].toString());
                shClassInfo.setStrandgroup(row[5].toString());
                shClassInfo.setClass_section(row[6].toString());
                shClassInfo.setClaTeaIdnum(row[7].toString());
                shClassInfo.setClass_teacher_name(row[8].toString());
                shClassInfo.setClaCrsCode(row[9].toString());
                shClassInfo.setSubject_name(row[10].toString());
                shClassInfo.setClaSection(row[11].toString());
                list.add(shClassInfo);
            }

            return list; 
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
        }
    }
     
    public ObservableList<ShClassInfo> getResStudentNotEnrolledSubjectList(String cla_sy, String cla_sem, String cla_yr_level, 
            String strandcode, String strandgroup, String cs_idnum) {
        ObservableList<ShClassInfo> list = FXCollections.observableArrayList();
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        
        String sql ="SELECT `classid`,`cla_sy` AS sy,`cla_sem` AS sem, `cla_yr_level` AS grade_level, TRIM(`strandcode`) AS strand_code,  " +
"        TRIM(sh_class_info.`strandgroup`) AS strand_group, IFNULL(`cla_section`,'') AS subject_section,`crs_title` AS subject_desc, `cla_crs_code` AS subject_code, `crs_unit` AS subject_unit,stud_section as class_section,   " +
"        IFNULL(GROUP_CONCAT(`cla_room`,' - ',`cla_day`,' ',`cla_t_startdesc`,' to ',`cla_t_enddesc` SEPARATOR '\n'), ' ') AS room_daytime_list " +           
"        FROM `sh_class_info`    " +
"        LEFT JOIN `sh_course_list`  ON  `cla_crs_code`=`crs_code`   " +
"        LEFT JOIN `strand_section` ON cla_sy =  strand_section.`sy` AND cla_sem = strand_section.`sem` AND cla_yr_level = strand_section.`grade_level` " +
"        AND sh_class_info.`strandcode`=strand_section.`strand` AND sh_class_info.`strandgroup`=strand_section.`strandgroup`   " +
                
"        WHERE `cla_sy`= :cla_sy  AND `cla_sem`= :cla_sem AND `cla_yr_level`= :cla_yr_level  " +
"        AND `strandcode`= :strandcode AND sh_class_info.`strandgroup`= :strandgroup " +
"        AND CONCAT(cla_crs_code,'-',cla_section) NOT IN " +
"        (SELECT CONCAT(cs_crs_code,'-',cs_section) FROM sh_c_class_stud WHERE cs_sy= :cs_sy AND cs_sem= :cs_sem AND cs_idnum= :cs_idnum) " +
"        GROUP BY subject_code,subject_section " +
"        ORDER BY subject_code,subject_section ";
   
      
        List<Object[]> listSubject = session.createSQLQuery(sql)
            .setParameter("cla_sy", cla_sy)
            .setParameter("cla_sem", cla_sem)
            .setParameter("cla_yr_level", cla_yr_level)
            .setParameter("strandcode", strandcode)
            .setParameter("strandgroup", strandgroup)
            .setParameter("cs_sy", cla_sy)
            .setParameter("cs_sem", cla_sem)
            .setParameter("cs_idnum", cs_idnum) 
            .list();

        txn.commit();
         
        for(Object[] row: listSubject){
                ShClassInfo shClassInfo= new ShClassInfo();   
                shClassInfo.setClassid(row[0].toString());
                shClassInfo.setClaSy(row[1].toString());
                shClassInfo.setClaSem(Integer.parseInt(row[2].toString()));
                shClassInfo.setClaYrLevel(Byte.parseByte(row[3].toString()));
                shClassInfo.setStrandcode(row[4].toString());
                shClassInfo.setStrandgroup(row[5].toString());
                shClassInfo.setClaSection(row[6].toString());
                shClassInfo.setSubject_name(row[7].toString());
                shClassInfo.setClaCrsCode(row[8].toString());
                shClassInfo.setSubject_unit(row[9].toString()); 
                shClassInfo.setClass_section(row[10].toString()); 
                shClassInfo.setSubject_room_daytime_list(row[11].toString());
                list.add(shClassInfo);
        }


        session.close(); session.getSessionFactory().close();
        return list;
    }
    
    
    public ObservableList<ShClassInfo> getResClassSubjectList(String cla_sy, String cla_sem, String cla_yr_level, String strandcode, 
            String strandgroup ){
        session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<ShClassInfo> list = FXCollections.observableArrayList();
        Transaction txn = session.beginTransaction();
        try{
        
       
            String sql = "SELECT classid,cla_sy AS sy,cla_sem AS sem, cla_yr_level AS grade_level, TRIM(strandcode) AS strand_code,TRIM(sh_class_info.strandgroup) AS strand_group,  " +
            "stud_section,IFNULL(instructor_id,'') AS instructor_id, IFNULL(instructor_name,'') AS instructor_name,cla_crs_code,`crs_title`,cla_section " +
            "FROM sh_class_info  " +
            "LEFT JOIN strand_section  ON cla_sy=sy AND cla_sem=sem AND cla_yr_level= grade_level " +
            "AND strandcode=strand AND sh_class_info.strandgroup=strand_section.strandgroup " +
            "LEFT JOIN sh_instructor  ON cla_tea_idnum=instructor_id " +
            "LEFT JOIN sh_course_list ON sh_class_info.cla_crs_code = sh_course_list.crs_code " +
            "WHERE is_no_grade='0' AND cla_sy= :cla_sy AND cla_sem = :cla_sem AND cla_yr_level = :cla_yr_level AND strandcode = :strandcode AND sh_class_info.strandgroup = :strandgroup ";

            

            sql +=" GROUP BY cla_sy, cla_sem, cla_yr_level, strandcode, sh_class_info.strandgroup,cla_crs_code  ";
            Query query = session.createSQLQuery(sql)
                    .setParameter("cla_sy", cla_sy)
                    .setParameter("cla_sem", cla_sem)
                    .setParameter("cla_yr_level", cla_yr_level)
                    .setParameter("strandcode", strandcode)
                    .setParameter("strandgroup", strandgroup);

           

            List<Object[]> listClass = query.list();

            txn.commit();

            for(Object[] row: listClass){
                    ShClassInfo shClassInfo= new ShClassInfo();  
                    shClassInfo.setClassid(row[0].toString());
                    shClassInfo.setClaSy(row[1].toString());
                    shClassInfo.setClaSem(Integer.parseInt(row[2].toString()));
                    shClassInfo.setClaYrLevel(Byte.parseByte(row[3].toString()));
                    shClassInfo.setStrandcode(row[4].toString());
                    shClassInfo.setStrandgroup(row[5].toString());
                    shClassInfo.setClass_section(row[6].toString());
                    shClassInfo.setClaTeaIdnum(row[7].toString());
                    shClassInfo.setClass_teacher_name(row[8].toString());
                    shClassInfo.setClaCrsCode(row[9].toString());
                    shClassInfo.setSubject_name(row[10].toString());
                    shClassInfo.setClaSection(row[11].toString());
                    list.add(shClassInfo);
            }

        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
            
        }
        return list;
        
    }
    
    
    public ObservableList<ShClassInfo> getResGradeSubjectSections(String cla_sy, String cla_sem, String cla_yr_level, String strandcode, 
            String stud_section, String subject ){
        session = HibernateUtil.getSessionFactory().openSession();
        ObservableList<ShClassInfo> list = FXCollections.observableArrayList();
        Transaction txn = session.beginTransaction();
        try{
           
       
            String sql = "SELECT classid,cla_sy AS sy,cla_sem AS sem, cla_yr_level AS grade_level, IFNULL(TRIM(strandcode),'') AS strand_code,IFNULL(TRIM(sh_class_info.strandgroup),'') AS strand_group,  " +
            "IFNULL(stud_section,'') AS stud_section,IFNULL(instructor_id,'') AS instructor_id, IFNULL(instructor_name,'') AS instructor_name,cla_crs_code,`crs_title`,IFNULL(cla_section,'') AS cla_section " +
            "FROM sh_class_info  " +
            "LEFT JOIN strand_section  ON cla_sy=sy AND cla_sem=sem AND cla_yr_level= grade_level " +
            "AND strandcode=strand AND sh_class_info.strandgroup=strand_section.strandgroup " +
            "LEFT JOIN sh_instructor  ON cla_tea_idnum=instructor_id " +
            "LEFT JOIN sh_course_list ON sh_class_info.cla_crs_code = sh_course_list.crs_code " +
            "WHERE is_no_grade='0' AND cla_sy= ?1 AND cla_sem = ?2  ";

            int ctr = 3;
            String col = "";
            List<Object> listParameter = new ArrayList<>();
            if(!cla_yr_level.equals("all")){
                col = "?" + ctr;
                sql += " AND cla_yr_level = "+col;
                listParameter.add(Byte.valueOf(cla_yr_level));
                ctr++;
            }

            if(!strandcode.equals("all")){
                col = "?" + ctr;
                sql += " AND strandcode = "+col;
                listParameter.add(strandcode);
                ctr++;
            }

            if(!stud_section.equals("all")){
                col = "?" + ctr;
                sql += " AND stud_section =  "+col;
                listParameter.add(stud_section);
                ctr++;
            }

            if(!subject.equals("")){
                col = "?" + ctr;
                sql += " AND (cla_crs_code LIKE  "+col;
                ctr++;
                col = "?" + ctr;
                sql += " OR crs_title LIKE  "+col +")";
                ctr++;
                listParameter.add("%" + subject + "%");
                listParameter.add("%" + subject + "%");
               
            }

            sql +=" GROUP BY cla_sy, cla_sem, cla_yr_level, strandcode, sh_class_info.strandgroup,cla_crs_code  ";
            
            
            Query query = session.createSQLQuery(sql)
                    .setParameter(1, cla_sy)
                    .setParameter(2, Integer.parseInt(cla_sem));

            ctr = 3;
            Iterator iterator = listParameter.iterator();
            while(iterator.hasNext()) {
                query.setParameter(ctr , iterator.next());
                ctr++;
            }


            List<Object[]> listClass = query.list();

            txn.commit();

            for(Object[] row: listClass){
                    ShClassInfo shClassInfo= new ShClassInfo();  
                    shClassInfo.setClassid(row[0].toString());
                    shClassInfo.setClaSy(row[1].toString());
                    shClassInfo.setClaSem(Integer.parseInt(row[2].toString()));
                    shClassInfo.setClaYrLevel(Byte.parseByte(row[3].toString()));
                    shClassInfo.setStrandcode(row[4].toString());
                    shClassInfo.setStrandgroup(row[5].toString());
                    shClassInfo.setClass_section(row[6].toString());
                    shClassInfo.setClaTeaIdnum(row[7].toString());
                    shClassInfo.setClass_teacher_name(row[8].toString());
                    shClassInfo.setClaCrsCode(row[9].toString());
                    shClassInfo.setSubject_name(row[10].toString());
                    shClassInfo.setClaSection(row[11].toString());
                    list.add(shClassInfo);
            }

        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close(); session.getSessionFactory().close();
            
        }
        return list;
        
    }
    
    public ObservableList<ShClassInfo> getResOfferedSubjectList(String cla_sy, String cla_sem, String cla_yr_level, String strandcode, String strandgroup) {
        ObservableList<ShClassInfo> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        
        String sql ="SELECT classid,'' AS stud_idnum,cla_sy AS sy,cla_sem AS sem, cla_yr_level AS grade_level, TRIM(sh_class_info.`strandcode`) AS strand_code,  " +
"        TRIM(`strandgroup`) AS strand_group, cla_section AS subject_section,`crs_title` AS subject_desc, cla_crs_code AS subject_code, `crs_unit` AS subject_unit,   " +
"        IFNULL(CONCAT(cla_room, ' - ', cla_day, ' ', cla_t_startdesc, ' to ', cla_t_enddesc),' ') AS room_daytime_list, cla_room, IFNULL(cla_t_startdesc, ' ') AS cla_t_startdesc, IFNULL(cla_t_enddesc, ' ') AS cla_t_enddesc   " +
"        FROM `sh_class_info`  " +
"        LEFT JOIN `sh_course_list`  ON cla_crs_code=`crs_code`  " +
"        WHERE cla_sy= :cla_sy AND cla_sem= :cla_sem AND cla_yr_level=:cla_yr_level AND strandcode = :strandcode AND strandgroup = :strandgroup " +
"        ORDER BY subject_code,subject_section ";

        List<Object[]> listSubject = session.createSQLQuery(sql)
            .setParameter("cla_sy", cla_sy)
            .setParameter("cla_sem", cla_sem)
            .setParameter("cla_yr_level", cla_yr_level)
            .setParameter("strandcode", strandcode)
            .setParameter("strandgroup", strandgroup)
            .list();

        txn.commit();
         
        for(Object[] row: listSubject){
                ShClassInfo shClassInfo= new ShClassInfo();  
                shClassInfo.setClassid(row[0].toString());
                
                shClassInfo.setClaSy(row[2].toString());
                shClassInfo.setClaSem(Integer.parseInt(row[3].toString()));
                shClassInfo.setClaYrLevel(Byte.parseByte(row[4].toString()));
                shClassInfo.setStrandcode(row[5].toString());
                shClassInfo.setStrandgroup(row[6].toString());
                shClassInfo.setClaSection(row[7].toString());
                shClassInfo.setSubject_name(row[8].toString());
                shClassInfo.setClaCrsCode(row[9].toString());
                shClassInfo.setSubject_unit(row[10].toString());
                shClassInfo.setSubject_room_daytime_list(row[11].toString());
                shClassInfo.setClaRoom((row[12] == null ? " " :row[12].toString()));
                shClassInfo.setClaTStartdesc(row[13].toString());
                shClassInfo.setClaTEnddesc(row[14].toString());
                
                list.add(shClassInfo);
        }
        

        session.close(); session.getSessionFactory().close();
        return list;
    }
    //--- END sql query ---//
    
}
