/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shregistrarjavafxml;

//import javax.security.auth.login.Configuration;
import entity.AppResults;
import entity.Children;
import entity.ClinicDates;
import entity.CoreValues;
import entity.CoreValuesCategory;
import entity.CustomApplicantAptitude;
import entity.CustomApplicantEducProgram;
import entity.CustomCheckbox;
import entity.CustomClassSections;
import entity.CustomEnrolledStudent;
import entity.CustomStudentGrades;
import entity.CustomStudentSubject;
import entity.EducAttainmentSibling;
import entity.EducationalPrograms;
import entity.EthnicAffiliation;
import entity.Honors;
import entity.JasperObject;
import entity.Livingcond;
import entity.MonthAttendance;
import entity.Registrar;
import entity.Religions;
import entity.SaLedger;
import entity.SaLedgerHeader;
import entity.SchoolAttended;
import entity.Schoolmonth;
import entity.Schools;
import entity.ShApplicant;
import entity.ShAssessaccount;
import entity.ShCAttendance;
import entity.ShCClassStud;
import entity.ShCCorevalues;
import entity.ShClassInfo;
import entity.ShConfirmation;
import entity.ShCourse;
import entity.ShCourseList;
import entity.ShCurrDtl;
import entity.ShCurrHdr;
import entity.ShCurrSy;
import entity.ShCurriculumDtl;
import entity.ShCurriculumHdr;
import entity.ShCurriculumSem;
import entity.ShDates;
import entity.ShDays;
import entity.ShDaysId;
import entity.ShFee;
import entity.ShInstructor;
import entity.ShRankingDtl;
import entity.ShRankingFinal;
import entity.ShRankingHdr;
import entity.ShSchooldays;
import entity.ShSections;
import entity.ShStrandsections;
import entity.ShStudStrand;
import entity.ShStudlist;
import entity.ShStudlistId;
import entity.ShTermReg;
import entity.ShTimes;
import entity.ShUsers;
import entity.StrandSection;
import entity.Strands;
import entity.Tracks;
import entity.ClearanceSysem;
import entity.ClearanceOfficeSysem;
import entity.ClearanceOffice;
import entity.ClearanceHoldlist;
import java.io.File;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author ACER
 */
public class HibernateUtil {

//    private static SessionFactory sessionFactory;
//
//    public static boolean setSessionFactory() {
//        try {
//            sessionFactory = new Configuration()
//                    .configure()
//                    .buildSessionFactory();
//           
//        } catch (HibernateException ex) {
//            System.out.println(ex.getMessage());
//            return false;
//            
//        }
//        
//        return true;
//    }
//
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        try {
            loadSessionFactory();
        } catch (Exception e) {
            System.err.println("Exception while initializing hibernate util.. ");
            e.printStackTrace();
        }
        return sessionFactory;
    }

    public static void loadSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.configure("/hibernate.cfg.xml");
        configuration.addAnnotatedClass(AppResults.class);
        configuration.addAnnotatedClass(ClearanceSysem.class);
        configuration.addAnnotatedClass(ClearanceOfficeSysem.class);
        configuration.addAnnotatedClass(ClearanceOffice.class);
        configuration.addAnnotatedClass(ClearanceHoldlist.class);
        configuration.addAnnotatedClass(Children.class);
        configuration.addAnnotatedClass(ClinicDates.class);
        configuration.addAnnotatedClass(CoreValues.class);
        configuration.addAnnotatedClass(CoreValuesCategory.class);
        configuration.addAnnotatedClass(CustomApplicantAptitude.class);
        configuration.addAnnotatedClass(CustomApplicantEducProgram.class);
        configuration.addAnnotatedClass(CustomCheckbox.class);
        configuration.addAnnotatedClass(CustomClassSections.class);
        configuration.addAnnotatedClass(CustomEnrolledStudent.class);
        configuration.addAnnotatedClass(CustomStudentGrades.class);
        configuration.addAnnotatedClass(CustomStudentSubject.class);
        configuration.addAnnotatedClass(EducAttainmentSibling.class);
        configuration.addAnnotatedClass(EducationalPrograms.class);
        configuration.addAnnotatedClass(EthnicAffiliation.class);
        configuration.addAnnotatedClass(Honors.class);
        configuration.addAnnotatedClass(JasperObject.class);
        configuration.addAnnotatedClass(Livingcond.class);
        configuration.addAnnotatedClass(MonthAttendance.class);
        configuration.addAnnotatedClass(Registrar.class);
        configuration.addAnnotatedClass(Religions.class);
        configuration.addAnnotatedClass(SaLedger.class);
        configuration.addAnnotatedClass(SaLedgerHeader.class);
        configuration.addAnnotatedClass(SchoolAttended.class);
        configuration.addAnnotatedClass(Schoolmonth.class);
        configuration.addAnnotatedClass(Schools.class);
        configuration.addAnnotatedClass(ShApplicant.class);
        configuration.addAnnotatedClass(ShAssessaccount.class);
        configuration.addAnnotatedClass(ShCAttendance.class);
        configuration.addAnnotatedClass(ShCClassStud.class);
        configuration.addAnnotatedClass(ShCCorevalues.class);
        configuration.addAnnotatedClass(ShClassInfo.class);
        configuration.addAnnotatedClass(ShConfirmation.class);
        configuration.addAnnotatedClass(ShCourse.class);

        configuration.addAnnotatedClass(ShCourseList.class);
        configuration.addAnnotatedClass(ShCurrDtl.class);
        configuration.addAnnotatedClass(ShCurrHdr.class);
        configuration.addAnnotatedClass(ShCurrSy.class);
        configuration.addAnnotatedClass(ShCurriculumDtl.class);
        configuration.addAnnotatedClass(ShCurriculumHdr.class);
        configuration.addAnnotatedClass(ShCurriculumSem.class);

        configuration.addAnnotatedClass(ShDates.class);
        configuration.addAnnotatedClass(ShDays.class);
        configuration.addAnnotatedClass(ShDaysId.class);
        configuration.addAnnotatedClass(ShFee.class);
        configuration.addAnnotatedClass(ShInstructor.class);
        configuration.addAnnotatedClass(ShRankingDtl.class);
        configuration.addAnnotatedClass(ShRankingFinal.class);
        configuration.addAnnotatedClass(ShRankingHdr.class);
        configuration.addAnnotatedClass(ShSchooldays.class);
        configuration.addAnnotatedClass(ShSections.class);
        configuration.addAnnotatedClass(ShStrandsections.class);
        configuration.addAnnotatedClass(ShStudStrand.class);
        configuration.addAnnotatedClass(ShStudlist.class);
        configuration.addAnnotatedClass(ShStudlistId.class);
        configuration.addAnnotatedClass(ShTermReg.class);
        configuration.addAnnotatedClass(ShTimes.class);
        configuration.addAnnotatedClass(ShUsers.class);
        configuration.addAnnotatedClass(StrandSection.class);
        configuration.addAnnotatedClass(Strands.class);
        configuration.addAnnotatedClass(Tracks.class);
        ServiceRegistry srvcReg = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(srvcReg);
    }

//    public static Session getSession() throws HibernateException {
// 
//        Session retSession=null;
//            try {
//                retSession=sessionFactory.openSession();
//            }catch(Throwable t){
//            System.err.println("Exception while getting session.. ");
//            t.printStackTrace();
//            }
//            if(retSession == null) {
//                System.err.println("session is discovered null");
//            }
// 
//            return retSession;
//    }
}
