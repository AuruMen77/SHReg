package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * ShTermReg generated by hbm2java
 */
@Entity
@Table(name="sh_term_reg"
    ,catalog="seniorhighdb"
    , uniqueConstraints = @UniqueConstraint(columnNames={"sy_reg", "sem_reg"}) 
)
public class ShTermReg  implements java.io.Serializable {


     private int termId;
     private String syReg;
     private Integer semReg;
     private Date enrollDate;
     private Date enrollClose;
     private Boolean isactive;
     private Boolean onlineGradeSubmission;
     private Boolean onlineSubmission;
     private Boolean financeClearance;
     private Boolean clusterInput;
     private Boolean clusterSave;
     private Boolean isPromotion;
     private Boolean isEnrollment;
     private Boolean isResection;
     private Boolean isAssessment;
     private Boolean isCurSysem;
     private Boolean isTeachingload;
     private Boolean isEvalForStud;
     private Date ts;

    public ShTermReg() {
    }

	
    public ShTermReg(int termId, String syReg, Date ts) {
        this.termId = termId;
        this.syReg = syReg;
        this.ts = ts;
    }
    public ShTermReg(int termId, String syReg, Integer semReg, Date enrollDate, Date enrollClose, Boolean isactive, Boolean onlineGradeSubmission, Boolean onlineSubmission, Boolean financeClearance, Boolean clusterInput, Boolean clusterSave, Boolean isEnrollment, Boolean isResection, Boolean isAssessment, Boolean isCurSysem, Boolean isTeachingload, Boolean isEvalForStud, Date ts) {
       this.termId = termId;
       this.syReg = syReg;
       this.semReg = semReg;
       this.enrollDate = enrollDate;
       this.enrollClose = enrollClose;
       this.isactive = isactive;
       this.onlineGradeSubmission = onlineGradeSubmission;
       this.onlineSubmission = onlineSubmission;
       this.financeClearance = financeClearance;
       this.clusterInput = clusterInput;
       this.clusterSave = clusterSave;
       this.isEnrollment = isEnrollment;
       this.isResection = isResection;
       this.isAssessment = isAssessment;
       this.isCurSysem = isCurSysem;
       this.isTeachingload = isTeachingload;
       this.isEvalForStud = isEvalForStud;
       this.ts = ts;
    }
   
     @Id 

    
    @Column(name="term_id", unique=true, nullable=false)
    public int getTermId() {
        return this.termId;
    }
    
    public void setTermId(int termId) {
        this.termId = termId;
    }

    
    @Column(name="sy_reg", nullable=false, length=9)
    public String getSyReg() {
        return this.syReg;
    }
    
    public void setSyReg(String syReg) {
        this.syReg = syReg;
    }

    
    @Column(name="sem_reg")
    public Integer getSemReg() {
        return this.semReg;
    }
    
    public void setSemReg(Integer semReg) {
        this.semReg = semReg;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="enroll_date", length=10)
    public Date getEnrollDate() {
        return this.enrollDate;
    }
    
    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="enroll_close", length=10)
    public Date getEnrollClose() {
        return this.enrollClose;
    }
    
    public void setEnrollClose(Date enrollClose) {
        this.enrollClose = enrollClose;
    }

    
    @Column(name="isactive")
    public Boolean getIsactive() {
        return this.isactive;
    }
    
    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    
    @Column(name="onlineGradeSubmission")
    public Boolean getOnlineGradeSubmission() {
        return this.onlineGradeSubmission;
    }
    
    public void setOnlineGradeSubmission(Boolean onlineGradeSubmission) {
        this.onlineGradeSubmission = onlineGradeSubmission;
    }

    
    @Column(name="onlineSubmission")
    public Boolean getOnlineSubmission() {
        return this.onlineSubmission;
    }
    
    public void setOnlineSubmission(Boolean onlineSubmission) {
        this.onlineSubmission = onlineSubmission;
    }

    
    @Column(name="financeClearance")
    public Boolean getFinanceClearance() {
        return this.financeClearance;
    }
    
    public void setFinanceClearance(Boolean financeClearance) {
        this.financeClearance = financeClearance;
    }

    
    @Column(name="clusterInput")
    public Boolean getClusterInput() {
        return this.clusterInput;
    }
    
    public void setClusterInput(Boolean clusterInput) {
        this.clusterInput = clusterInput;
    }

    
    @Column(name="clusterSave")
    public Boolean getClusterSave() {
        return this.clusterSave;
    }
    
    public void setClusterSave(Boolean clusterSave) {
        this.clusterSave = clusterSave;
    }
    
    @Column(name="isPromotion")
    public Boolean getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(Boolean isPromotion) {
        this.isPromotion = isPromotion;
    }

    
    @Column(name="isEnrollment")
    public Boolean getIsEnrollment() {
        return this.isEnrollment;
    }
    
    public void setIsEnrollment(Boolean isEnrollment) {
        this.isEnrollment = isEnrollment;
    }

    
    @Column(name="isResection")
    public Boolean getIsResection() {
        return this.isResection;
    }
    
    public void setIsResection(Boolean isResection) {
        this.isResection = isResection;
    }

    
    @Column(name="isAssessment")
    public Boolean getIsAssessment() {
        return this.isAssessment;
    }
    
    public void setIsAssessment(Boolean isAssessment) {
        this.isAssessment = isAssessment;
    }

    
    @Column(name="isCurSysem")
    public Boolean getIsCurSysem() {
        return this.isCurSysem;
    }
    
    public void setIsCurSysem(Boolean isCurSysem) {
        this.isCurSysem = isCurSysem;
    }

    
    @Column(name="isTeachingload")
    public Boolean getIsTeachingload() {
        return this.isTeachingload;
    }
    
    public void setIsTeachingload(Boolean isTeachingload) {
        this.isTeachingload = isTeachingload;
    }

    
    @Column(name="isEvalForStud")
    public Boolean getIsEvalForStud() {
        return this.isEvalForStud;
    }
    
    public void setIsEvalForStud(Boolean isEvalForStud) {
        this.isEvalForStud = isEvalForStud;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ts", nullable=false, length=19)
    public Date getTs() {
        return this.ts;
    }
    
    public void setTs(Date ts) {
        this.ts = ts;
    }

    




}


