package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ShCurriculumHdr generated by hbm2java
 */
@Entity
@Table(name="sh_curriculum_hdr"
    ,catalog="seniorhighdb"
    , uniqueConstraints = @UniqueConstraint(columnNames={"strand_code", "curr_sy"}) 
)
public class ShCurriculumHdr  implements java.io.Serializable {


     private String currHdrId;
     private String currSy;
     private Set<ShCurriculumDtl> shCurriculumDtls = new HashSet<ShCurriculumDtl>(0);
     private Set<ShCurriculumSem> shCurriculumSems = new HashSet<ShCurriculumSem>(0);
     private Strands strands;

    public ShCurriculumHdr() {
    }

	
    public ShCurriculumHdr(String currHdrId) {
        this.currHdrId = currHdrId;
    }
    public ShCurriculumHdr(String currHdrId, String currSy, Set<ShCurriculumDtl> shCurriculumDtls, Set<ShCurriculumSem> shCurriculumSems, Strands strands) {
       this.currHdrId = currHdrId;
       this.currSy = currSy;
       this.shCurriculumDtls = shCurriculumDtls;
       this.shCurriculumSems = shCurriculumSems;
       this.strands = strands;
    }
   
     @Id 

    
    @Column(name="curr_hdr_id", unique=true, nullable=false, length=10)
    public String getCurrHdrId() {
        return this.currHdrId;
    }
    
    public void setCurrHdrId(String currHdrId) {
        this.currHdrId = currHdrId;
    }

    
    @Column(name="curr_sy", length=9)
    public String getCurrSy() {
        return this.currSy;
    }
    
    public void setCurrSy(String currSy) {
        this.currSy = currSy;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="shCurriculumHdr")
    public Set<ShCurriculumDtl> getShCurriculumDtls() {
        return this.shCurriculumDtls;
    }
    
    public void setShCurriculumDtls(Set<ShCurriculumDtl> shCurriculumDtls) {
        this.shCurriculumDtls = shCurriculumDtls;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="shCurriculumHdr")
    public Set<ShCurriculumSem> getShCurriculumSems() {
        return this.shCurriculumSems;
    }
    
    public void setShCurriculumSems(Set<ShCurriculumSem> shCurriculumSems) {
        this.shCurriculumSems = shCurriculumSems;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="strand_code")
    public Strands getStrands() {
        return this.strands;
    }
    
    public void setStrands(Strands strands) {
        this.strands = strands;
    }




}

