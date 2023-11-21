package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShDays generated by hbm2java
 */
@Entity
@Table(name="sh_days"
    ,catalog="seniorhighdb"
)
public class ShDays  implements java.io.Serializable {

     private Integer days_id;
     private String days;
     private String daycode;

    public ShDays() {
    }

    public ShDays(String days, String daycode) {
       this.days = days;
       this.daycode = daycode;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    @Column(name="days_id", unique=true, nullable=false)
    public Integer getDays_id() {
        return days_id;
    }

    public void setDays_id(Integer days_id) {
        this.days_id = days_id;
    }

    
    @Column(name="days",  nullable=false, length=10)
    public String getDays() {
        return this.days;
    }
    
    public void setDays(String days) {
        this.days = days;
    }

    
    @Column(name="daycode", nullable=false, length=10)
    public String getDaycode() {
        return this.daycode;
    }
    
    public void setDaycode(String daycode) {
        this.daycode = daycode;
    }

   



}


