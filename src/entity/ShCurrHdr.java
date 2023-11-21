package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ShCurrHdr generated by hbm2java
 */
@Entity
@Table(name="sh_curr_hdr"
    ,catalog="seniorhighdb"
)
public class ShCurrHdr  implements java.io.Serializable {


     private Integer currHdrId;
     private String currDesc;
     private String currName;
     private Date dateCreated;
     private Set<ShCurrDtl> shCurrDtls = new HashSet<ShCurrDtl>(0);
     private Set<ShCurrSy> shCurrSies = new HashSet<ShCurrSy>(0);
     private String strandcode;
     private Date ts;

    public ShCurrHdr() {
    }

	
    public ShCurrHdr(String strandcode, Date ts) {
        this.strandcode = strandcode;
        this.ts = ts;
    }
    public ShCurrHdr(String currDesc, String currName, Date dateCreated, Set<ShCurrDtl> shCurrDtls, Set<ShCurrSy> shCurrSies, String strandcode, Date ts) {
       this.currDesc = currDesc;
       this.currName = currName;
       this.dateCreated = dateCreated;
       this.shCurrDtls = shCurrDtls;
       this.shCurrSies = shCurrSies;
       this.strandcode = strandcode;
       this.ts = ts;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="curr_hdr_id", unique=true, nullable=false)
    public Integer getCurrHdrId() {
        return this.currHdrId;
    }
    
    public void setCurrHdrId(Integer currHdrId) {
        this.currHdrId = currHdrId;
    }

    
    @Column(name="curr_desc", length=65535)
    public String getCurrDesc() {
        return this.currDesc;
    }
    
    public void setCurrDesc(String currDesc) {
        this.currDesc = currDesc;
    }

    
    @Column(name="curr_name", length=50)
    public String getCurrName() {
        return this.currName;
    }
    
    public void setCurrName(String currName) {
        this.currName = currName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_created", length=19)
    public Date getDateCreated() {
        return this.dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="shCurrHdr")
    public Set<ShCurrDtl> getShCurrDtls() {
        return this.shCurrDtls;
    }
    
    public void setShCurrDtls(Set<ShCurrDtl> shCurrDtls) {
        this.shCurrDtls = shCurrDtls;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="shCurrHdr")
    public Set<ShCurrSy> getShCurrSies() {
        return this.shCurrSies;
    }
    
    public void setShCurrSies(Set<ShCurrSy> shCurrSies) {
        this.shCurrSies = shCurrSies;
    }

    
    @Column(name="strandcode", nullable=false, length=20)
    public String getStrandcode() {
        return this.strandcode;
    }
    
    public void setStrandcode(String strandcode) {
        this.strandcode = strandcode;
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

