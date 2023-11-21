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
 * CoreValuesCategory generated by hbm2java
 */
@Entity
@Table(name="core_values_category"
    ,catalog="seniorhighdb"
)
public class CoreValuesCategory  implements java.io.Serializable {


     private Integer cvCatid;
     private String categoryName;
     private Set<CoreValues> coreValueses = new HashSet<CoreValues>(0);
     private Date ts;

    public CoreValuesCategory() {
    }

	
    public CoreValuesCategory(Date ts) {
        this.ts = ts;
    }
    public CoreValuesCategory(String categoryName, Set<CoreValues> coreValueses, Date ts) {
       this.categoryName = categoryName;
       this.coreValueses = coreValueses;
       this.ts = ts;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="cv_catid", unique=true, nullable=false)
    public Integer getCvCatid() {
        return this.cvCatid;
    }
    
    public void setCvCatid(Integer cvCatid) {
        this.cvCatid = cvCatid;
    }

    
    @Column(name="category_name", length=50)
    public String getCategoryName() {
        return this.categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="coreValuesCategory")
    public Set<CoreValues> getCoreValueses() {
        return this.coreValueses;
    }
    
    public void setCoreValueses(Set<CoreValues> coreValueses) {
        this.coreValueses = coreValueses;
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


