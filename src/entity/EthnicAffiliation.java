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
 * EthnicAffiliation generated by hbm2java
 */
@Entity
@Table(name="ethnic_affiliation"
    ,catalog="seniorhighdb"
)
public class EthnicAffiliation  implements java.io.Serializable {


     private String ethnic;
     private Date etnicTs;

    public EthnicAffiliation() {
    }

    public EthnicAffiliation(String ethnic, Date etnicTs) {
       this.ethnic = ethnic;
       this.etnicTs = etnicTs;
    }
   
     @Id 

    
    @Column(name="ethnic", unique=true, nullable=false, length=50)
    public String getEthnic() {
        return this.ethnic;
    }
    
    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="etnic_ts", nullable=false, length=19)
    public Date getEtnicTs() {
        return this.etnicTs;
    }
    
    public void setEtnicTs(Date etnicTs) {
        this.etnicTs = etnicTs;
    }




}


