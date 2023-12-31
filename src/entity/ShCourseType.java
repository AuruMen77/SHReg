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
 * ShCourseType generated by hbm2java
 */
@Entity
@Table(name="sh_course_type"
    ,catalog="seniorhighdb"
)
public class ShCourseType  implements java.io.Serializable {


     private Integer ctypeId;
     private String ctype;
     private String ctypeVal;
     private Integer isActive;
     private Date ts;

    public ShCourseType() {
    }

	
    public ShCourseType(Date ts) {
        this.ts = ts;
    }
    public ShCourseType(String ctype, String ctypeVal, Integer isActive, Date ts) {
       this.ctype = ctype;
       this.ctypeVal = ctypeVal;
       this.isActive = isActive;
       this.ts = ts;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="ctype_id", unique=true, nullable=false)
    public Integer getCtypeId() {
        return this.ctypeId;
    }
    
    public void setCtypeId(Integer ctypeId) {
        this.ctypeId = ctypeId;
    }

    
    @Column(name="ctype", length=50)
    public String getCtype() {
        return this.ctype;
    }
    
    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    
    @Column(name="ctype_val", length=50)
    public String getCtypeVal() {
        return this.ctypeVal;
    }
    
    public void setCtypeVal(String ctypeVal) {
        this.ctypeVal = ctypeVal;
    }

    
    @Column(name="is_active")
    public Integer getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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


