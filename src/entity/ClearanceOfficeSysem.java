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
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author CITS-TEST
 */
@Entity
@Table(name = "clearance_office_sysem", catalog = "seniorhighdb"
)
public class ClearanceOfficeSysem implements Serializable {

    private Integer off_sysem_id;
    private Integer f_office;
    private Integer f_sysem;
    private Date ts;

    public ClearanceOfficeSysem() {
    }

    public ClearanceOfficeSysem(Integer f_office, Integer f_sysem, Date ts) {
        this.f_office = f_office;
        this.f_sysem = f_sysem;
        this.ts = ts;
    }
    

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "off_sysem_id", unique = true, nullable = false)
    public Integer getoff_sysem_id() {
        return this.off_sysem_id;
    }

    public void setoff_sysem_id(Integer off_sysem_id) {
        this.off_sysem_id = off_sysem_id;
    }

    @Column(name = "f_sysem")
    public Integer getf_sysem() {
        return this.f_sysem;
    }

    public void setf_sysem(Integer f_sysem) {
        this.f_sysem = f_sysem;
    }

    @Column(name = "f_office")
    public Integer getf_office() {
        return this.f_office;
    }

    public void setf_office(Integer f_office) {
        this.f_office = f_office;
    }
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ts", nullable = false, length = 19)
    public Date getts() {
        return this.ts;
    }

    public void setts(Date ts) {
        this.ts = ts;
    }

}
