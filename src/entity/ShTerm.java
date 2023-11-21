package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShTerm generated by hbm2java
 */
@Entity
@Table(name="sh_term"
    ,catalog="seniorhighdb"
)
public class ShTerm  implements java.io.Serializable {


     private String sy;
     private Byte allowFinance;
     private Byte isactive;
     private Byte sem;

    public ShTerm() {
    }

	
    public ShTerm(String sy) {
        this.sy = sy;
    }
    public ShTerm(String sy, Byte allowFinance, Byte isactive, Byte sem) {
       this.sy = sy;
       this.allowFinance = allowFinance;
       this.isactive = isactive;
       this.sem = sem;
    }
   
     @Id 

    
    @Column(name="sy", unique=true, nullable=false, length=9)
    public String getSy() {
        return this.sy;
    }
    
    public void setSy(String sy) {
        this.sy = sy;
    }

    
    @Column(name="allow_finance")
    public Byte getAllowFinance() {
        return this.allowFinance;
    }
    
    public void setAllowFinance(Byte allowFinance) {
        this.allowFinance = allowFinance;
    }

    
    @Column(name="isactive")
    public Byte getIsactive() {
        return this.isactive;
    }
    
    public void setIsactive(Byte isactive) {
        this.isactive = isactive;
    }

    
    @Column(name="sem")
    public Byte getSem() {
        return this.sem;
    }
    
    public void setSem(Byte sem) {
        this.sem = sem;
    }




}


