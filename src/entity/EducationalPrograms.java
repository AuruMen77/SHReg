package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * EducationalPrograms generated by hbm2java
 */
@Entity
@Table(name="educational_programs"
    ,catalog="seniorhighdb"
)
public class EducationalPrograms  implements java.io.Serializable {


     private String code;
     private String educProg;
     private Date epTs;
     private String mayTrack;
     private Integer minimunRange;
     private BigDecimal orderArrangement;
     private String strongTrack;

    public EducationalPrograms() {
    }

	
    public EducationalPrograms(String code, Date epTs) {
        this.code = code;
        this.epTs = epTs;
    }
    public EducationalPrograms(String code, String educProg, Date epTs, String mayTrack, Integer minimunRange, BigDecimal orderArrangement, String strongTrack) {
       this.code = code;
       this.educProg = educProg;
       this.epTs = epTs;
       this.mayTrack = mayTrack;
       this.minimunRange = minimunRange;
       this.orderArrangement = orderArrangement;
       this.strongTrack = strongTrack;
    }
   
     @Id 

    
    @Column(name="code", unique=true, nullable=false, length=30)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    
    @Column(name="educ_prog", length=50)
    public String getEducProg() {
        return this.educProg;
    }
    
    public void setEducProg(String educProg) {
        this.educProg = educProg;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ep_ts", nullable=false, length=19)
    public Date getEpTs() {
        return this.epTs;
    }
    
    public void setEpTs(Date epTs) {
        this.epTs = epTs;
    }

    
    @Column(name="may_track", length=100)
    public String getMayTrack() {
        return this.mayTrack;
    }
    
    public void setMayTrack(String mayTrack) {
        this.mayTrack = mayTrack;
    }

    
    @Column(name="minimun_range")
    public Integer getMinimunRange() {
        return this.minimunRange;
    }
    
    public void setMinimunRange(Integer minimunRange) {
        this.minimunRange = minimunRange;
    }

    
    @Column(name="order_arrangement", precision=10, scale=0)
    public BigDecimal getOrderArrangement() {
        return this.orderArrangement;
    }
    
    public void setOrderArrangement(BigDecimal orderArrangement) {
        this.orderArrangement = orderArrangement;
    }

    
    @Column(name="strong_track", length=100)
    public String getStrongTrack() {
        return this.strongTrack;
    }
    
    public void setStrongTrack(String strongTrack) {
        this.strongTrack = strongTrack;
    }




}

