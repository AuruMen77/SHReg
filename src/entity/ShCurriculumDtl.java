package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ShCurriculumDtl generated by hbm2java
 */
@Entity
@Table(name="sh_curriculum_dtl"
    ,catalog="seniorhighdb"
)
public class ShCurriculumDtl  implements java.io.Serializable {


     private Long currDtlId;
     private String crsCode;
     private Byte currCrsType;
     private Boolean currMajor;
     private String currNote;
     private Boolean currSem;
     private Byte currYrlevel;
     private ShCurriculumHdr shCurriculumHdr;

    public ShCurriculumDtl() {
    }

	
    public ShCurriculumDtl(ShCurriculumHdr shCurriculumHdr) {
        this.shCurriculumHdr = shCurriculumHdr;
    }
    public ShCurriculumDtl(String crsCode, Byte currCrsType, Boolean currMajor, String currNote, Boolean currSem, Byte currYrlevel, ShCurriculumHdr shCurriculumHdr) {
       this.crsCode = crsCode;
       this.currCrsType = currCrsType;
       this.currMajor = currMajor;
       this.currNote = currNote;
       this.currSem = currSem;
       this.currYrlevel = currYrlevel;
       this.shCurriculumHdr = shCurriculumHdr;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="curr_dtl_id", unique=true, nullable=false)
    public Long getCurrDtlId() {
        return this.currDtlId;
    }
    
    public void setCurrDtlId(Long currDtlId) {
        this.currDtlId = currDtlId;
    }

    
    @Column(name="crs_code", length=15)
    public String getCrsCode() {
        return this.crsCode;
    }
    
    public void setCrsCode(String crsCode) {
        this.crsCode = crsCode;
    }

    
    @Column(name="curr_crs_type")
    public Byte getCurrCrsType() {
        return this.currCrsType;
    }
    
    public void setCurrCrsType(Byte currCrsType) {
        this.currCrsType = currCrsType;
    }

    
    @Column(name="curr_major")
    public Boolean getCurrMajor() {
        return this.currMajor;
    }
    
    public void setCurrMajor(Boolean currMajor) {
        this.currMajor = currMajor;
    }

    
    @Column(name="curr_note", length=200)
    public String getCurrNote() {
        return this.currNote;
    }
    
    public void setCurrNote(String currNote) {
        this.currNote = currNote;
    }

    
    @Column(name="curr_sem")
    public Boolean getCurrSem() {
        return this.currSem;
    }
    
    public void setCurrSem(Boolean currSem) {
        this.currSem = currSem;
    }

    
    @Column(name="curr_yrlevel")
    public Byte getCurrYrlevel() {
        return this.currYrlevel;
    }
    
    public void setCurrYrlevel(Byte currYrlevel) {
        this.currYrlevel = currYrlevel;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="curr_hdr_id", nullable=false)
    public ShCurriculumHdr getShCurriculumHdr() {
        return this.shCurriculumHdr;
    }
    
    public void setShCurriculumHdr(ShCurriculumHdr shCurriculumHdr) {
        this.shCurriculumHdr = shCurriculumHdr;
    }




}


