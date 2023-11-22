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
@Table(name = "clearance_office", catalog = "seniorhighdb"
)
public class ClearanceOffice implements Serializable {

    private Integer office_id;
    private String office_abrev;
    private String office_desc;
    private Integer for_moderator;
    private Integer for_principal;
    private Integer for_finance;
    private Date ts;

    public ClearanceOffice() {
    }

    public ClearanceOffice (String office_abrev, String office_desc, Integer for_moderator, Integer for_principal, Integer for_finance, Date ts) {
   
       this.office_abrev = office_abrev;
       this.office_desc = office_desc;
       this.for_moderator = for_moderator;
       this.for_principal = for_principal;
       this.for_finance = for_finance;
       this.ts = ts;
    }
    

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "office_id", unique = true, nullable = false)
    public Integer getoffice_id() {
        return this.office_id;
    }

     public void setOffice_id(Integer office_id) {
    this.office_id = office_id;
    }

   
    @Column(name = "office_abrev")
    public String getOffice_abrev() {
        return this.office_abrev;
    }

    public void setOffice_abrev(String office_abrev) {
        this.office_abrev = office_abrev;
    }

    @Column(name = "office_desc")
    public String getOffice_desc() {
        return this.office_desc;
    }

    public void setOffice_desc(String office_desc) {
        this.office_desc = office_desc;
    }

    @Column(name = "for_moderator")
    public Integer getFor_moderator() {
        return this.for_moderator;
    }

    public void setFor_moderator(Integer for_moderator) {
        this.for_moderator = for_moderator;
    }

    @Column(name = "for_principal")
    public Integer getFor_principal() {
        return this.for_principal;
    }

    public void setFor_principal(Integer for_principal) {
        this.for_principal = for_principal;
    }

    @Column(name = "for_finance")
    public Integer getFor_finance() {
        return this.for_finance;
    }

    public void setFor_finance(Integer for_finance) {
        this.for_finance = for_finance;
    }

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ts", nullable = false, updatable = false)
    public Date getTs() {
        return this.ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

}
