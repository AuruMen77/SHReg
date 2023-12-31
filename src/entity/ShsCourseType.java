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
 * ShsCourseType generated by hbm2java
 */
@Entity
@Table(name="shs_course_type"
    ,catalog="seniorhighdb"
)
public class ShsCourseType  implements java.io.Serializable {


     private Integer crstypeId;
     private String crstypeDesc;
     private Date ts;

    public ShsCourseType() {
    }

	
    public ShsCourseType(Date ts) {
        this.ts = ts;
    }
    public ShsCourseType(String crstypeDesc, Date ts) {
       this.crstypeDesc = crstypeDesc;
       this.ts = ts;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="crstype_id", unique=true, nullable=false)
    public Integer getCrstypeId() {
        return this.crstypeId;
    }
    
    public void setCrstypeId(Integer crstypeId) {
        this.crstypeId = crstypeId;
    }

    
    @Column(name="crstype_desc", length=100)
    public String getCrstypeDesc() {
        return this.crstypeDesc;
    }
    
    public void setCrstypeDesc(String crstypeDesc) {
        this.crstypeDesc = crstypeDesc;
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


