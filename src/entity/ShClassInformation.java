package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ShClassInformation generated by hbm2java
 */
@Entity
@Table(name="sh_class_information"
    ,catalog="seniorhighdb"
)
public class ShClassInformation  implements java.io.Serializable {


     private Long classid;
     private Integer claCommon;
     private String claCrsCode;
     private String claDay;
     private Integer claEndcode;
     private String claRemarks;
     private String claRoom;
     private String claSection;
     private String claSem;
     private Integer claStartcode;
     private Integer claStudAllowed;
     private Integer claStudEnrolled;
     private Integer claStudRes;
     private String claSy;
     private String claTEnd;
     private String claTStart;
     private String claTeaIdnum;
     private Integer claYrLevel;
     private Date dateClusterinput;
     private Integer multipleSection;
     private String strandcode;
     private String strandgroup;
     private Integer subjDep;
     private Integer tobedeleted;
     private Date ts;
     private Boolean withCluster;

    public ShClassInformation() {
    }

	
    public ShClassInformation(Date ts) {
        this.ts = ts;
    }
    public ShClassInformation(Integer claCommon, String claCrsCode, String claDay, Integer claEndcode, String claRemarks, String claRoom, String claSection, String claSem, Integer claStartcode, Integer claStudAllowed, Integer claStudEnrolled, Integer claStudRes, String claSy, String claTEnd, String claTStart, String claTeaIdnum, Integer claYrLevel, Date dateClusterinput, Integer multipleSection, String strandcode, String strandgroup, Integer subjDep, Integer tobedeleted, Date ts, Boolean withCluster) {
       this.claCommon = claCommon;
       this.claCrsCode = claCrsCode;
       this.claDay = claDay;
       this.claEndcode = claEndcode;
       this.claRemarks = claRemarks;
       this.claRoom = claRoom;
       this.claSection = claSection;
       this.claSem = claSem;
       this.claStartcode = claStartcode;
       this.claStudAllowed = claStudAllowed;
       this.claStudEnrolled = claStudEnrolled;
       this.claStudRes = claStudRes;
       this.claSy = claSy;
       this.claTEnd = claTEnd;
       this.claTStart = claTStart;
       this.claTeaIdnum = claTeaIdnum;
       this.claYrLevel = claYrLevel;
       this.dateClusterinput = dateClusterinput;
       this.multipleSection = multipleSection;
       this.strandcode = strandcode;
       this.strandgroup = strandgroup;
       this.subjDep = subjDep;
       this.tobedeleted = tobedeleted;
       this.ts = ts;
       this.withCluster = withCluster;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="classid", unique=true, nullable=false)
    public Long getClassid() {
        return this.classid;
    }
    
    public void setClassid(Long classid) {
        this.classid = classid;
    }

    
    @Column(name="cla_common")
    public Integer getClaCommon() {
        return this.claCommon;
    }
    
    public void setClaCommon(Integer claCommon) {
        this.claCommon = claCommon;
    }

    
    @Column(name="cla_crs_code", length=15)
    public String getClaCrsCode() {
        return this.claCrsCode;
    }
    
    public void setClaCrsCode(String claCrsCode) {
        this.claCrsCode = claCrsCode;
    }

    
    @Column(name="cla_day", length=5)
    public String getClaDay() {
        return this.claDay;
    }
    
    public void setClaDay(String claDay) {
        this.claDay = claDay;
    }

    
    @Column(name="cla_endcode")
    public Integer getClaEndcode() {
        return this.claEndcode;
    }
    
    public void setClaEndcode(Integer claEndcode) {
        this.claEndcode = claEndcode;
    }

    
    @Column(name="cla_remarks", length=200)
    public String getClaRemarks() {
        return this.claRemarks;
    }
    
    public void setClaRemarks(String claRemarks) {
        this.claRemarks = claRemarks;
    }

    
    @Column(name="cla_room", length=15)
    public String getClaRoom() {
        return this.claRoom;
    }
    
    public void setClaRoom(String claRoom) {
        this.claRoom = claRoom;
    }

    
    @Column(name="cla_section", length=2)
    public String getClaSection() {
        return this.claSection;
    }
    
    public void setClaSection(String claSection) {
        this.claSection = claSection;
    }

    
    @Column(name="cla_sem", length=10)
    public String getClaSem() {
        return this.claSem;
    }
    
    public void setClaSem(String claSem) {
        this.claSem = claSem;
    }

    
    @Column(name="cla_startcode")
    public Integer getClaStartcode() {
        return this.claStartcode;
    }
    
    public void setClaStartcode(Integer claStartcode) {
        this.claStartcode = claStartcode;
    }

    
    @Column(name="cla_stud_allowed")
    public Integer getClaStudAllowed() {
        return this.claStudAllowed;
    }
    
    public void setClaStudAllowed(Integer claStudAllowed) {
        this.claStudAllowed = claStudAllowed;
    }

    
    @Column(name="cla_stud_enrolled")
    public Integer getClaStudEnrolled() {
        return this.claStudEnrolled;
    }
    
    public void setClaStudEnrolled(Integer claStudEnrolled) {
        this.claStudEnrolled = claStudEnrolled;
    }

    
    @Column(name="cla_stud_res")
    public Integer getClaStudRes() {
        return this.claStudRes;
    }
    
    public void setClaStudRes(Integer claStudRes) {
        this.claStudRes = claStudRes;
    }

    
    @Column(name="cla_sy", length=10)
    public String getClaSy() {
        return this.claSy;
    }
    
    public void setClaSy(String claSy) {
        this.claSy = claSy;
    }

    
    @Column(name="cla_t_end", length=10)
    public String getClaTEnd() {
        return this.claTEnd;
    }
    
    public void setClaTEnd(String claTEnd) {
        this.claTEnd = claTEnd;
    }

    
    @Column(name="cla_t_start", length=10)
    public String getClaTStart() {
        return this.claTStart;
    }
    
    public void setClaTStart(String claTStart) {
        this.claTStart = claTStart;
    }

    
    @Column(name="cla_tea_idnum", length=20)
    public String getClaTeaIdnum() {
        return this.claTeaIdnum;
    }
    
    public void setClaTeaIdnum(String claTeaIdnum) {
        this.claTeaIdnum = claTeaIdnum;
    }

    
    @Column(name="cla_yr_level")
    public Integer getClaYrLevel() {
        return this.claYrLevel;
    }
    
    public void setClaYrLevel(Integer claYrLevel) {
        this.claYrLevel = claYrLevel;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_clusterinput", length=19)
    public Date getDateClusterinput() {
        return this.dateClusterinput;
    }
    
    public void setDateClusterinput(Date dateClusterinput) {
        this.dateClusterinput = dateClusterinput;
    }

    
    @Column(name="multiple_section")
    public Integer getMultipleSection() {
        return this.multipleSection;
    }
    
    public void setMultipleSection(Integer multipleSection) {
        this.multipleSection = multipleSection;
    }

    
    @Column(name="strandcode", length=20)
    public String getStrandcode() {
        return this.strandcode;
    }
    
    public void setStrandcode(String strandcode) {
        this.strandcode = strandcode;
    }

    
    @Column(name="strandgroup", length=2)
    public String getStrandgroup() {
        return this.strandgroup;
    }
    
    public void setStrandgroup(String strandgroup) {
        this.strandgroup = strandgroup;
    }

    
    @Column(name="subj_dep")
    public Integer getSubjDep() {
        return this.subjDep;
    }
    
    public void setSubjDep(Integer subjDep) {
        this.subjDep = subjDep;
    }

    
    @Column(name="tobedeleted")
    public Integer getTobedeleted() {
        return this.tobedeleted;
    }
    
    public void setTobedeleted(Integer tobedeleted) {
        this.tobedeleted = tobedeleted;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ts", nullable=false, length=19)
    public Date getTs() {
        return this.ts;
    }
    
    public void setTs(Date ts) {
        this.ts = ts;
    }

    
    @Column(name="with_cluster")
    public Boolean getWithCluster() {
        return this.withCluster;
    }
    
    public void setWithCluster(Boolean withCluster) {
        this.withCluster = withCluster;
    }




}


