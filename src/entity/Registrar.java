package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Registrar generated by hbm2java
 */
@Entity
@Table(name="registrar"
    ,catalog="seniorhighdb"
)
public class Registrar  implements java.io.Serializable {


     private String rname;

    public Registrar() {
    }

    public Registrar(String rname) {
       this.rname = rname;
    }
   
     @Id 

    
    @Column(name="rname", unique=true, nullable=false, length=60)
    public String getRname() {
        return this.rname;
    }
    
    public void setRname(String rname) {
        this.rname = rname;
    }




}


