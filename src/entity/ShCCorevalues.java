package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * ShCCorevalues generated by hbm2java
 */
@Entity
@Table(name="sh_c_corevalues"
    ,catalog="seniorhighdb"
)
public class ShCCorevalues  implements java.io.Serializable {


@Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="cv_classid", unique=true, nullable=false)
     private Integer cvClassid;
     @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="f_cv_id")
     private CoreValues coreValues;
     @Column(name="cv_idnum", length=6)
     private String cvIdnum;
     @Column(name="cv_sem", length=6)
     private String cvSem;
     @Column(name="cv_strand", length=10)
     private String cvStrand;
     @Column(name="cv_strandgroup", length=10)
     private String cvStrandgroup;
     @Column(name="cv_sy", length=10)
     private String cvSy;
     @Column(name="cv_yrlevel")
     private Integer cvYrlevel;
     @Column(name="grade1", length=5)
     private String grade1;
     @Column(name="grade2", length=5)
     private String grade2;
     @Column(name="grade3", length=5)
     private String grade3;
     @Column(name="grade4", length=5)
     private String grade4;
     @Temporal(TemporalType.TIMESTAMP)
     @Column(name="ts",  length=19)
     private Date ts;
     
     @Transient
     private String corevalue_desc;

    public ShCCorevalues() {
    }

	
    public ShCCorevalues(Date ts) {
        this.ts = ts;
    }
    public ShCCorevalues(CoreValues coreValues, String cvIdnum, String cvSem, String cvStrand, String cvStrandgroup, String cvSy, Integer cvYrlevel, String grade1, String grade2, Date ts) {
       this.coreValues = coreValues;
       this.cvIdnum = cvIdnum;
       this.cvSem = cvSem;
       this.cvStrand = cvStrand;
       this.cvStrandgroup = cvStrandgroup;
       this.cvSy = cvSy;
       this.cvYrlevel = cvYrlevel;
       this.grade1 = grade1;
       this.grade2 = grade2;
       this.ts = ts;
    }
   
    public Integer getCvClassid() {
        return this.cvClassid;
    }
    
    public void setCvClassid(Integer cvClassid) {
        this.cvClassid = cvClassid;
    }

    public CoreValues getCoreValues() {
        return this.coreValues;
    }
    
    public void setCoreValues(CoreValues coreValues) {
        this.coreValues = coreValues;
    }

    
    public String getCvIdnum() {
        return this.cvIdnum;
    }
    
    public void setCvIdnum(String cvIdnum) {
        this.cvIdnum = cvIdnum;
    }

    
    public String getCvSem() {
        return this.cvSem;
    }
    
    public void setCvSem(String cvSem) {
        this.cvSem = cvSem;
    }

    
    public String getCvStrand() {
        return this.cvStrand;
    }
    
    public void setCvStrand(String cvStrand) {
        this.cvStrand = cvStrand;
    }

    
    public String getCvStrandgroup() {
        return this.cvStrandgroup;
    }
    
    public void setCvStrandgroup(String cvStrandgroup) {
        this.cvStrandgroup = cvStrandgroup;
    }

    
    public String getCvSy() {
        return this.cvSy;
    }
    
    public void setCvSy(String cvSy) {
        this.cvSy = cvSy;
    }

    
    public Integer getCvYrlevel() {
        return this.cvYrlevel;
    }
    
    public void setCvYrlevel(Integer cvYrlevel) {
        this.cvYrlevel = cvYrlevel;
    }

    
    public String getGrade1() {
        return this.grade1;
    }
    
    public void setGrade1(String grade1) {
        this.grade1 = grade1;
    }

    
    public String getGrade2() {
        return this.grade2;
    }
    
    public void setGrade2(String grade2) {
        this.grade2 = grade2;
    }
    
    public String getGrade3() {
        return this.grade3;
    }
    
    public void setGrade3(String grade3) {
        this.grade3 = grade3;
    }
    
    public String getGrade4() {
        return this.grade4;
    }
    
    public void setGrade4(String grade4) {
        this.grade4 = grade4;
    }

    public Date getTs() {
        return this.ts;
    }
    
    public void setTs(Date ts) {
        this.ts = ts;
    }

    public String getCorevalue_desc() {
        return corevalue_desc;
    }

    public void setCorevalue_desc(String corevalue_desc) {
        this.corevalue_desc = corevalue_desc;
    }




}


