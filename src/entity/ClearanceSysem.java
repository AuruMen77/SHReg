/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.Column;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author CITS-TEST
 */
@Entity
@Table(name="clearance_sysem"
    ,catalog="seniorhighdb"
)
public class ClearanceSysem implements Serializable {
   private Integer sysem_id;
     private String sy;
     private Integer sem;
     private Integer is_active;
     private Date ts;
    public ClearanceSysem() {
    }

    public ClearanceSysem(String sy, Integer sem, Integer is_active, Date ts) {
       this.sy = sy;
       this.sem = sem;
       this.is_active = is_active;
       this.ts= ts;
    }
   
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="sysem_id", unique=true, nullable=false)
    public Integer getsysem_id() {
        return this.sysem_id;
    }
    
    public void setsysem_id(Integer sysem_id) {
        this.sysem_id = sysem_id;
    }

    
    @Column(name="sy", length = 10)
    public String getsy() {
        return this.sy;
    }
    
    public void setsy(String sy) {
        this.sy = sy;
    }
    
    @Column(name="sem")
    public Integer getsem() {
        return this.sem;
    }
    
    public void setsem(Integer sem) {
        this.sem = sem;
    }

    
    @Column(name="is_active")
    public Integer getis_active() {
        return this.is_active;
    }
    
    public void setis_active(Integer is_active) {
        this.is_active =  is_active;
    }

    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ts", nullable=false, length=19)
    public Date getts() {
        return this.ts;
    }
    
    public void setts(Date ts) {
        this.ts = ts;
    }

    
}
