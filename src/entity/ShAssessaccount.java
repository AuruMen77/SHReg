package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
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
 * ShAssessaccount generated by hbm2java
 */
@Entity
@Table(name="sh_assessaccount"
    ,catalog="seniorhighdb"
)
public class ShAssessaccount  implements java.io.Serializable {


     private Long accntid;
     private Boolean assessFlag;
     private Boolean assessSem;
     private String assessStrand;
     private String assessSy;
     private String idnum;
     private String itemcode;
     private BigDecimal itemfee;
     private String itemname;
     private Byte pmode;
     private Date transdate;
     private Date ts;

    public ShAssessaccount() {
    }

	
    public ShAssessaccount(String idnum, Date ts) {
        this.idnum = idnum;
        this.ts = ts;
    }
    public ShAssessaccount(Boolean assessFlag, Boolean assessSem, String assessStrand, String assessSy, String idnum, String itemcode, BigDecimal itemfee, String itemname, Byte pmode, Date transdate, Date ts) {
       this.assessFlag = assessFlag;
       this.assessSem = assessSem;
       this.assessStrand = assessStrand;
       this.assessSy = assessSy;
       this.idnum = idnum;
       this.itemcode = itemcode;
       this.itemfee = itemfee;
       this.itemname = itemname;
       this.pmode = pmode;
       this.transdate = transdate;
       this.ts = ts;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="accntid", unique=true, nullable=false)
    public Long getAccntid() {
        return this.accntid;
    }
    
    public void setAccntid(Long accntid) {
        this.accntid = accntid;
    }

    
    @Column(name="assess_flag")
    public Boolean getAssessFlag() {
        return this.assessFlag;
    }
    
    public void setAssessFlag(Boolean assessFlag) {
        this.assessFlag = assessFlag;
    }

    
    @Column(name="assess_sem")
    public Boolean getAssessSem() {
        return this.assessSem;
    }
    
    public void setAssessSem(Boolean assessSem) {
        this.assessSem = assessSem;
    }

    
    @Column(name="assess_strand", length=15)
    public String getAssessStrand() {
        return this.assessStrand;
    }
    
    public void setAssessStrand(String assessStrand) {
        this.assessStrand = assessStrand;
    }

    
    @Column(name="assess_sy", length=10)
    public String getAssessSy() {
        return this.assessSy;
    }
    
    public void setAssessSy(String assessSy) {
        this.assessSy = assessSy;
    }

    
    @Column(name="idnum", nullable=false, length=6)
    public String getIdnum() {
        return this.idnum;
    }
    
    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    
    @Column(name="itemcode", length=20)
    public String getItemcode() {
        return this.itemcode;
    }
    
    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    
    @Column(name="itemfee", precision=10, scale=0)
    public BigDecimal getItemfee() {
        return this.itemfee;
    }
    
    public void setItemfee(BigDecimal itemfee) {
        this.itemfee = itemfee;
    }

    
    @Column(name="itemname", length=50)
    public String getItemname() {
        return this.itemname;
    }
    
    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    
    @Column(name="pmode")
    public Byte getPmode() {
        return this.pmode;
    }
    
    public void setPmode(Byte pmode) {
        this.pmode = pmode;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="transdate", length=10)
    public Date getTransdate() {
        return this.transdate;
    }
    
    public void setTransdate(Date transdate) {
        this.transdate = transdate;
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


