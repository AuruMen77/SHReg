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
 * ShTmpsubject generated by hbm2java
 */
@Entity
@Table(name="sh_tmpsubject"
    ,catalog="seniorhighdb"
)
public class ShTmpsubject  implements java.io.Serializable {


     private String tmpid;
     private String crsCode;
     private BigDecimal crsUnits;
     private String idnum;
     private String section;
     private Date ts;

    public ShTmpsubject() {
    }

	
    public ShTmpsubject(String tmpid, String idnum, Date ts) {
        this.tmpid = tmpid;
        this.idnum = idnum;
        this.ts = ts;
    }
    public ShTmpsubject(String tmpid, String crsCode, BigDecimal crsUnits, String idnum, String section, Date ts) {
       this.tmpid = tmpid;
       this.crsCode = crsCode;
       this.crsUnits = crsUnits;
       this.idnum = idnum;
       this.section = section;
       this.ts = ts;
    }
   
     @Id 

    
    @Column(name="tmpid", unique=true, nullable=false, length=10)
    public String getTmpid() {
        return this.tmpid;
    }
    
    public void setTmpid(String tmpid) {
        this.tmpid = tmpid;
    }

    
    @Column(name="crs_code", length=20)
    public String getCrsCode() {
        return this.crsCode;
    }
    
    public void setCrsCode(String crsCode) {
        this.crsCode = crsCode;
    }

    
    @Column(name="crs_units", precision=5, scale=0)
    public BigDecimal getCrsUnits() {
        return this.crsUnits;
    }
    
    public void setCrsUnits(BigDecimal crsUnits) {
        this.crsUnits = crsUnits;
    }

    
    @Column(name="idnum", nullable=false, length=6)
    public String getIdnum() {
        return this.idnum;
    }
    
    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    
    @Column(name="section", length=5)
    public String getSection() {
        return this.section;
    }
    
    public void setSection(String section) {
        this.section = section;
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


