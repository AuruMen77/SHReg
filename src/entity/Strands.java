package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Strands generated by hbm2java
 */
@Entity
@Table(name="strands"
    ,catalog="seniorhighdb"
)
public class Strands  implements java.io.Serializable {


     private String strandCode;
     private String arStrand;
     private Boolean isActive;
     private Set<ShCurriculumHdr> shCurriculumHdrs = new HashSet<ShCurriculumHdr>(0);
     private String strand;
     private String strandName;
     private String track;

    public Strands() {
    }

	
    public Strands(String strandCode) {
        this.strandCode = strandCode;
    }
    public Strands(String strandCode, String arStrand, Boolean isActive, Set<ShCurriculumHdr> shCurriculumHdrs, String strand, String strandName, String track) {
       this.strandCode = strandCode;
       this.arStrand = arStrand;
       this.isActive = isActive;
       this.shCurriculumHdrs = shCurriculumHdrs;
       this.strand = strand;
       this.strandName = strandName;
       this.track = track;
    }
   
     @Id 

    
    @Column(name="strand_code", unique=true, nullable=false, length=20)
    public String getStrandCode() {
        return this.strandCode;
    }
    
    public void setStrandCode(String strandCode) {
        this.strandCode = strandCode;
    }

    
    @Column(name="ar_strand", length=15)
    public String getArStrand() {
        return this.arStrand;
    }
    
    public void setArStrand(String arStrand) {
        this.arStrand = arStrand;
    }

    
    @Column(name="is_active")
    public Boolean getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="strands")
    public Set<ShCurriculumHdr> getShCurriculumHdrs() {
        return this.shCurriculumHdrs;
    }
    
    public void setShCurriculumHdrs(Set<ShCurriculumHdr> shCurriculumHdrs) {
        this.shCurriculumHdrs = shCurriculumHdrs;
    }

    
    @Column(name="strand", length=100)
    public String getStrand() {
        return this.strand;
    }
    
    public void setStrand(String strand) {
        this.strand = strand;
    }

    
    @Column(name="strand_name", length=100)
    public String getStrandName() {
        return this.strandName;
    }
    
    public void setStrandName(String strandName) {
        this.strandName = strandName;
    }

    
    @Column(name="track", length=50)
    public String getTrack() {
        return this.track;
    }
    
    public void setTrack(String track) {
        this.track = track;
    }




}


