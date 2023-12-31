package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ShInstructor generated by hbm2java
 */
@Entity
@Table(name="sh_instructor"
    ,catalog="seniorhighdb"
)
public class ShInstructor  implements java.io.Serializable {


     private String instructorId;
     private String instructorName;
     private String instructorPw;
     private String instructorUname;
     private Boolean printFlag;
     private Boolean registrarFlag;
     private String rfidtag;
     private Boolean teacherFlag;
     private Date ts;

    public ShInstructor() {
    }

	
    public ShInstructor(String instructorId) {
        this.instructorId = instructorId;
    }
    public ShInstructor(String instructorId, String instructorName, String instructorPw, String instructorUname, Boolean printFlag, Boolean registrarFlag, String rfidtag, Boolean teacherFlag, Date ts) {
       this.instructorId = instructorId;
       this.instructorName = instructorName;
       this.instructorPw = instructorPw;
       this.instructorUname = instructorUname;
       this.printFlag = printFlag;
       this.registrarFlag = registrarFlag;
       this.rfidtag = rfidtag;
       this.teacherFlag = teacherFlag;
       this.ts = ts;
    }
   
     @Id 

    
    @Column(name="instructor_id", unique=true, nullable=false, length=20)
    public String getInstructorId() {
        return this.instructorId;
    }
    
    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    
    @Column(name="instructor_name", length=100)
    public String getInstructorName() {
        return this.instructorName;
    }
    
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    
    @Column(name="instructor_pw", length=20)
    public String getInstructorPw() {
        return this.instructorPw;
    }
    
    public void setInstructorPw(String instructorPw) {
        this.instructorPw = instructorPw;
    }

    
    @Column(name="instructor_uname", length=20)
    public String getInstructorUname() {
        return this.instructorUname;
    }
    
    public void setInstructorUname(String instructorUname) {
        this.instructorUname = instructorUname;
    }

    
    @Column(name="print_flag")
    public Boolean getPrintFlag() {
        return this.printFlag;
    }
    
    public void setPrintFlag(Boolean printFlag) {
        this.printFlag = printFlag;
    }

    
    @Column(name="registrar_flag")
    public Boolean getRegistrarFlag() {
        return this.registrarFlag;
    }
    
    public void setRegistrarFlag(Boolean registrarFlag) {
        this.registrarFlag = registrarFlag;
    }

    
    @Column(name="rfidtag", length=15)
    public String getRfidtag() {
        return this.rfidtag;
    }
    
    public void setRfidtag(String rfidtag) {
        this.rfidtag = rfidtag;
    }

    
    @Column(name="teacher_flag")
    public Boolean getTeacherFlag() {
        return this.teacherFlag;
    }
    
    public void setTeacherFlag(Boolean teacherFlag) {
        this.teacherFlag = teacherFlag;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ts", insertable=false, updatable=false, length=19)
    public Date getTs() {
        return this.ts;
    }
    
    public void setTs(Date ts) {
        this.ts = ts;
    }




}


