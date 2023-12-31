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
 * StrandSection generated by hbm2java
 */
@Entity
@Table(name="strand_section"
    ,catalog="seniorhighdb"
)
public class StrandSection  implements java.io.Serializable {


     private Integer strandsecId;
     private Integer gradeLevel;
     private Integer sem;
     private String strand;
     private String strandgroup;
     private String studSection;
     private String sy;
     private Date ts;

    public StrandSection() {
    }

	
    public StrandSection(Date ts) {
        this.ts = ts;
    }
    public StrandSection(Integer gradeLevel, Integer sem, String strand, String strandgroup, String studSection, String sy, Date ts) {
       this.gradeLevel = gradeLevel;
       this.sem = sem;
       this.strand = strand;
       this.strandgroup = strandgroup;
       this.studSection = studSection;
       this.sy = sy;
       this.ts = ts;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="strandsec_id", unique=true, nullable=false)
    public Integer getStrandsecId() {
        return this.strandsecId;
    }
    
    public void setStrandsecId(Integer strandsecId) {
        this.strandsecId = strandsecId;
    }

    
    @Column(name="grade_level")
    public Integer getGradeLevel() {
        return this.gradeLevel;
    }
    
    public void setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    
    @Column(name="sem")
    public Integer getSem() {
        return this.sem;
    }
    
    public void setSem(Integer sem) {
        this.sem = sem;
    }

    
    @Column(name="strand", length=10)
    public String getStrand() {
        return this.strand;
    }
    
    public void setStrand(String strand) {
        this.strand = strand;
    }

    
    @Column(name="strandgroup", length=3)
    public String getStrandgroup() {
        return this.strandgroup;
    }
    
    public void setStrandgroup(String strandgroup) {
        this.strandgroup = strandgroup;
    }

    
    @Column(name="stud_section", length=50)
    public String getStudSection() {
        return this.studSection;
    }
    
    public void setStudSection(String studSection) {
        this.studSection = studSection;
    }

    
    @Column(name="sy", length=15)
    public String getSy() {
        return this.sy;
    }
    
    public void setSy(String sy) {
        this.sy = sy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ts", insertable=false, updatable=false,length=19)
    public Date getTs() {
        return this.ts;
    }
    
    public void setTs(Date ts) {
        this.ts = ts;
    }




}


