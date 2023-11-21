package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
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
 * ShSchooldays generated by hbm2java
 */
@Entity
@Table(name="sh_schooldays"
    ,catalog="seniorhighdb"
)
public class ShSchooldays  implements java.io.Serializable {


     private Integer schooldayId;
     private BigDecimal schooldays;
     private Schoolmonth schoolmonth;
     private Boolean schoolsem;
     private String schoolsy;

    public ShSchooldays() {
    }

    public ShSchooldays(BigDecimal schooldays, Schoolmonth schoolmonth, Boolean schoolsem, String schoolsy) {
       this.schooldays = schooldays;
       this.schoolmonth = schoolmonth;
       this.schoolsem = schoolsem;
       this.schoolsy = schoolsy;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="schoolday_id", unique=true, nullable=false)
    public Integer getSchooldayId() {
        return this.schooldayId;
    }
    
    public void setSchooldayId(Integer schooldayId) {
        this.schooldayId = schooldayId;
    }

    
    @Column(name="schooldays", precision=5, scale=1)
    public BigDecimal getSchooldays() {
        return this.schooldays;
    }
    
    public void setSchooldays(BigDecimal schooldays) {
        this.schooldays = schooldays;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="schoolmonth")
    public Schoolmonth getSchoolmonth() {
        return this.schoolmonth;
    }
    
    public void setSchoolmonth(Schoolmonth schoolmonth) {
        this.schoolmonth = schoolmonth;
    }

    
    @Column(name="schoolsem")
    public Boolean getSchoolsem() {
        return this.schoolsem;
    }
    
    public void setSchoolsem(Boolean schoolsem) {
        this.schoolsem = schoolsem;
    }

    
    @Column(name="schoolsy", length=9)
    public String getSchoolsy() {
        return this.schoolsy;
    }
    
    public void setSchoolsy(String schoolsy) {
        this.schoolsy = schoolsy;
    }




}


