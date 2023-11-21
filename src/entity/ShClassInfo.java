package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import controller.ScheduleEntryController;
import java.util.Date;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javafx.beans.property.SimpleStringProperty;

/**
 * ShClassInfo generated by hbm2java
 */
@Entity
@Table(name="sh_class_info"
    ,catalog="seniorhighdb"
)
public class ShClassInfo  implements java.io.Serializable {


    @Id
    @Column(name="classid", unique=true, nullable=false, length=20)
     private String classid;
     @Column(name="f_curr_sy")
     private Integer fCurrSy;
     @Column(name="f_curr_dtl")
     private Integer fCurrDtl;
     @Column(name="cla_common")
     private Boolean claCommon;
     @Column(name="cla_crs_code", nullable=false, length=15)
     private String claCrsCode;
     @Column(name="cla_day", length=7)
     private String claDay;
     @Column(name="cla_endcode")
     private Integer claEndcode;
     @Column(name="cla_remarks", length=200)
     private String claRemarks;
     @Column(name="cla_room", length=15)
     private String claRoom;
     @Column(name="cla_section", length=3)
     private String claSection;
     @Column(name="cla_sem")
     private Integer claSem;
     @Column(name="cla_startcode")
     private Integer claStartcode;
     @Column(name="cla_stud_allowed")
     private Integer claStudAllowed;
     @Column(name="cla_stud_enrolled")
     private Integer claStudEnrolled;
     @Column(name="cla_stud_res")
     private Integer claStudRes;
     @Column(name="cla_sy", length=9)
     private String claSy;
     @Column(name="cla_t_end", length=10)
     private String claTEnd;
     @Column(name="cla_t_enddesc", length=10)
     private String claTEnddesc;
     @Column(name="cla_t_start", length=10)
     private String claTStart;
     @Column(name="cla_t_startdesc", length=10)
     private String claTStartdesc;
     @Column(name="cla_tea_idnum", length=20)
     private String claTeaIdnum;
     @Column(name="cla_yr_level")
     private Byte claYrLevel;
     @Temporal(TemporalType.TIMESTAMP)
     @Column(name="date_clusterinput", length=19)
     private Date dateClusterinput;
     @Column(name="multiple_section")
     private Integer multipleSection;
     @Column(name="strandcode", length=20)
     private String strandcode;
     @Column(name="strandgroup", length=2)
     private String strandgroup;
     @Column(name="subj_dep")
     private Integer subjDep;
     @Column(name="tobedeleted")
     private Boolean tobedeleted;
     @Temporal(TemporalType.TIMESTAMP)
     @Column(name="ts", insertable=false, updatable=false, length=19)
     private Date ts;
     @Column(name="with_cluster")
     private Boolean withCluster;
     
     @Transient
     private SimpleStringProperty subject_name = new SimpleStringProperty();
     
     @Transient
     private SimpleStringProperty subject_unit = new SimpleStringProperty();
     
     @Transient
     private SimpleStringProperty subject_room_daytime_list = new SimpleStringProperty();
     
     @Transient
     private SimpleStringProperty class_section = new SimpleStringProperty();
     
     @Transient
     private SimpleStringProperty class_teacher_name = new SimpleStringProperty();
     
     
     
     
     @Transient
     private CheckBox customSelect;    
     
     @Transient
     Button update;

    public ShClassInfo() {
        this.customSelect = new CheckBox();   
    }

	
    public ShClassInfo(String classid, String claCrsCode) {
        this.classid = classid;
        this.claCrsCode = claCrsCode;
        this.customSelect = new CheckBox();   
    }
    public ShClassInfo(String classid, Integer fCurrSy, Integer fCurrDtl, Boolean claCommon, String claCrsCode, String claDay, Integer claEndcode, String claRemarks, 
            String claRoom, String claSection, Integer claSem, Integer claStartcode, Integer claStudAllowed, Integer claStudEnrolled, Integer claStudRes, 
            String claSy, String claTEnd, String claTEnddesc, String claTStart, String claTStartdesc, String claTeaIdnum, Byte claYrLevel, Date dateClusterinput, 
            Integer multipleSection, String strandcode, String strandgroup, Integer subjDep, Boolean tobedeleted, Date ts, Boolean withCluster, Button update) {
       this.classid = classid;
       this.fCurrSy = fCurrSy;
       this.fCurrDtl = fCurrDtl;
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
       this.claTEnddesc = claTEnddesc;
       this.claTStart = claTStart;
       this.claTStartdesc = claTStartdesc;
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
       this.customSelect = new CheckBox();   
       
       update.setOnAction(e -> {
           ObservableList<ShClassInfo> listShClassInfo = ScheduleEntryController.tblSchedule_for_update.getSelectionModel().getSelectedItems();
//           for(ShClassInfo ShClassInfo: listShClassInfo){
//               
//           }
       });
    }
   
    public String getClassid() {
        return this.classid;
    }
    
    public void setClassid(String classid) {
        this.classid = classid;
    }
    
    public Integer getFCurrSy() {
        return fCurrSy;
    }

    public void setFCurrSy(Integer fCurrSy) {
        this.fCurrSy = fCurrSy;
    }

    public Integer getFCurrDtl() {
        return fCurrDtl;
    }

    public void setFCurrDtl(Integer fCurrDtl) {
        this.fCurrDtl = fCurrDtl;
    }

    
    public Boolean getClaCommon() {
        return this.claCommon;
    }
    
    public void setClaCommon(Boolean claCommon) {
        this.claCommon = claCommon;
    }

    
    public String getClaCrsCode() {
        return this.claCrsCode;
    }
    
    public void setClaCrsCode(String claCrsCode) {
        this.claCrsCode = claCrsCode;
    }

    
    public String getClaDay() {
        return this.claDay;
    }
    
    public void setClaDay(String claDay) {
        this.claDay = claDay;
    }

    
    public Integer getClaEndcode() {
        return this.claEndcode;
    }
    
    public void setClaEndcode(Integer claEndcode) {
        this.claEndcode = claEndcode;
    }

    
    public String getClaRemarks() {
        return this.claRemarks;
    }
    
    public void setClaRemarks(String claRemarks) {
        this.claRemarks = claRemarks;
    }

    
    public String getClaRoom() {
        return this.claRoom;
    }
    
    public void setClaRoom(String claRoom) {
        this.claRoom = claRoom;
    }

    
    public String getClaSection() {
        return this.claSection;
    }
    
    public void setClaSection(String claSection) {
        this.claSection = claSection;
    }

    
    public Integer getClaSem() {
        return this.claSem;
    }
    
    public void setClaSem(Integer claSem) {
        this.claSem = claSem;
    }

    
    public Integer getClaStartcode() {
        return this.claStartcode;
    }
    
    public void setClaStartcode(Integer claStartcode) {
        this.claStartcode = claStartcode;
    }

    
    public Integer getClaStudAllowed() {
        return this.claStudAllowed;
    }
    
    public void setClaStudAllowed(Integer claStudAllowed) {
        this.claStudAllowed = claStudAllowed;
    }

    
    public Integer getClaStudEnrolled() {
        return this.claStudEnrolled;
    }
    
    public void setClaStudEnrolled(Integer claStudEnrolled) {
        this.claStudEnrolled = claStudEnrolled;
    }

    
    public Integer getClaStudRes() {
        return this.claStudRes;
    }
    
    public void setClaStudRes(Integer claStudRes) {
        this.claStudRes = claStudRes;
    }

    
    public String getClaSy() {
        return this.claSy;
    }
    
    public void setClaSy(String claSy) {
        this.claSy = claSy;
    }

    
    public String getClaTEnd() {
        return this.claTEnd;
    }
    
    public void setClaTEnd(String claTEnd) {
        this.claTEnd = claTEnd;
    }

    
    public String getClaTEnddesc() {
        return this.claTEnddesc;
    }
    
    public void setClaTEnddesc(String claTEnddesc) {
        this.claTEnddesc = claTEnddesc;
    }

    
    public String getClaTStart() {
        return this.claTStart;
    }
    
    public void setClaTStart(String claTStart) {
        this.claTStart = claTStart;
    }

    
    public String getClaTStartdesc() {
        return this.claTStartdesc;
    }
    
    public void setClaTStartdesc(String claTStartdesc) {
        this.claTStartdesc = claTStartdesc;
    }

    
    public String getClaTeaIdnum() {
        return this.claTeaIdnum;
    }
    
    public void setClaTeaIdnum(String claTeaIdnum) {
        this.claTeaIdnum = claTeaIdnum;
    }

    
    public Byte getClaYrLevel() {
        return this.claYrLevel;
    }
    
    public void setClaYrLevel(Byte claYrLevel) {
        this.claYrLevel = claYrLevel;
    }

    public Date getDateClusterinput() {
        return this.dateClusterinput;
    }
    
    public void setDateClusterinput(Date dateClusterinput) {
        this.dateClusterinput = dateClusterinput;
    }

    
    public Integer getMultipleSection() {
        return this.multipleSection;
    }
    
    public void setMultipleSection(Integer multipleSection) {
        this.multipleSection = multipleSection;
    }

    
    public String getStrandcode() {
        return this.strandcode;
    }
    
    public void setStrandcode(String strandcode) {
        this.strandcode = strandcode;
    }

    
    public String getStrandgroup() {
        return this.strandgroup;
    }
    
    public void setStrandgroup(String strandgroup) {
        this.strandgroup = strandgroup;
    }

    
    public Integer getSubjDep() {
        return this.subjDep;
    }
    
    public void setSubjDep(Integer subjDep) {
        this.subjDep = subjDep;
    }

    
    public Boolean getTobedeleted() {
        return this.tobedeleted;
    }
    
    public void setTobedeleted(Boolean tobedeleted) {
        this.tobedeleted = tobedeleted;
    }

    public Date getTs() {
        return this.ts;
    }
    
    public void setTs(Date ts) {
        this.ts = ts;
    }

    
    public Boolean getWithCluster() {
        return this.withCluster;
    }
    
    public void setWithCluster(Boolean withCluster) {
        this.withCluster = withCluster;
    }
    
    public String getSubject_name() {
        return subject_name.get();
    }

    public void setSubject_name(String subject_name) {
        this.subject_name.set(subject_name);
    }
    
    public String getSubject_unit() {
        return subject_unit.get();
    }

    public void setSubject_unit(String subject_unit) {
        this.subject_unit.set(subject_unit);
    }
    
    public String getSubject_room_daytime_list() {
        return subject_room_daytime_list.get();
    }

    public void setSubject_room_daytime_list(String subject_room_daytime_list) {
        this.subject_room_daytime_list.set(subject_room_daytime_list);
    }
    
    public String getClass_section() {
        return class_section.get();
    }

    public void setClass_section(String class_section) {
        this.class_section.set(class_section);
    }
    
     public String getClass_teacher_name() {
        return class_teacher_name.get();
    }

    public void setClass_teacher_name(String class_teacher_name) {
        this.class_teacher_name.set(class_teacher_name);;
    }  

    public CheckBox getCustomSelect() {
        return customSelect;
    }

    public void setCustomSelect(CheckBox customSelect) {
        this.customSelect = customSelect;
    }

    
   




}


