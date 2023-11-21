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
 * Schools generated by hbm2java
 */
@Entity
@Table(name="schools"
    ,catalog="seniorhighdb"
)
public class Schools  implements java.io.Serializable {


     private String school;
     private String schoolAddress;
     private Date schoolTs;
     private String schoolType;

    public Schools() {
    }

	
    public Schools(String school, Date schoolTs) {
        this.school = school;
        this.schoolTs = schoolTs;
    }
    public Schools(String school, String schoolAddress, Date schoolTs, String schoolType) {
       this.school = school;
       this.schoolAddress = schoolAddress;
       this.schoolTs = schoolTs;
       this.schoolType = schoolType;
    }
   
     @Id 

    
    @Column(name="school", unique=true, nullable=false, length=100)
    public String getSchool() {
        return this.school;
    }
    
    public void setSchool(String school) {
        this.school = school;
    }

    
    @Column(name="school_address", length=200)
    public String getSchoolAddress() {
        return this.schoolAddress;
    }
    
    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="school_ts", nullable=false, length=19)
    public Date getSchoolTs() {
        return this.schoolTs;
    }
    
    public void setSchoolTs(Date schoolTs) {
        this.schoolTs = schoolTs;
    }

    
    @Column(name="school_type", length=10)
    public String getSchoolType() {
        return this.schoolType;
    }
    
    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }




}

