package entity;
// Generated 06 1, 19 5:52:32 AM by Hibernate Tools 4.3.1


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ShDays generated by hbm2java
 */
@Entity
@Table(name="sh_days"
    ,catalog="seniorhighdb"
)
public class ShDays2  implements java.io.Serializable {


     private ShDaysId id;
     private String daycode;

    public ShDays2() {
    }

    public ShDays2(ShDaysId id, String daycode) {
       this.id = id;
       this.daycode = daycode;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="daysId", column=@Column(name="days_id", nullable=false) ), 
        @AttributeOverride(name="days", column=@Column(name="days", nullable=false, length=10) ) } )
    public ShDaysId getId() {
        return this.id;
    }
    
    public void setId(ShDaysId id) {
        this.id = id;
    }

    
    @Column(name="daycode", nullable=false, length=10)
    public String getDaycode() {
        return this.daycode;
    }
    
    public void setDaycode(String daycode) {
        this.daycode = daycode;
    }




}


