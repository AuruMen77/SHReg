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
@Table(name = "clearance_holdlist", catalog = "seniorhighdb"
)
public class ClearanceHoldlist implements Serializable {

    private Integer holdlist_id;
    private Integer f_off_sysem;
    private String student_id;
    private Integer grade_level;
    private String remarks_hold;
    private String remarks_cleared;
    private String hold_by;
    private String hold_by_name;
    private String cleared_by;
    private String cleared_by_name;
    private String current_status;
    private Date ts;

    public ClearanceHoldlist() {
    }

    public ClearanceHoldlist( Integer f_off_sysem, String student_id, Integer grade_level,
                             String remarks_hold, String remarks_cleared, String hold_by, String hold_by_name,
                             String cleared_by, String cleared_by_name, String current_status, Date ts) {
     
        this.f_off_sysem = f_off_sysem;
        this.student_id = student_id;
        this.grade_level = grade_level;
        this.remarks_hold = remarks_hold;
        this.remarks_cleared = remarks_cleared;
        this.hold_by = hold_by;
        this.hold_by_name = hold_by_name;
        this.cleared_by = cleared_by;
        this.cleared_by_name = cleared_by_name;
        this.current_status = current_status;
        this.ts = ts;
    }

      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holdlist_id", unique = true, nullable = false)
    public Integer getHoldlist_id() {
        return holdlist_id;
    }

    public void setHoldlist_id(Integer holdlist_id) {
        this.holdlist_id = holdlist_id;
    }

    @Column(name = "f_off_sysem")
    public Integer getF_off_sysem() {
        return f_off_sysem;
    }

    public void setF_off_sysem(Integer f_off_sysem) {
        this.f_off_sysem = f_off_sysem;
    }

    @Column(name = "student_id")
    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    @Column(name = "grade_level")
    public Integer getGrade_level() {
        return grade_level;
    }

    public void setGrade_level(Integer grade_level) {
        this.grade_level = grade_level;
    }

    @Column(name = "remarks_hold")
    public String getRemarks_hold() {
        return remarks_hold;
    }

    public void setRemarks_hold(String remarks_hold) {
        this.remarks_hold = remarks_hold;
    }

    @Column(name = "remarks_cleared")
    public String getRemarks_cleared() {
        return remarks_cleared;
    }

    public void setRemarks_cleared(String remarks_cleared) {
        this.remarks_cleared = remarks_cleared;
    }

    @Column(name = "hold_by")
    public String getHold_by() {
        return hold_by;
    }

    public void setHold_by(String hold_by) {
        this.hold_by = hold_by;
    }

    @Column(name = "hold_by_name")
    public String getHold_by_name() {
        return hold_by_name;
    }

    public void setHold_by_name(String hold_by_name) {
        this.hold_by_name = hold_by_name;
    }

    @Column(name = "cleared_by")
    public String getCleared_by() {
        return cleared_by;
    }

    public void setCleared_by(String cleared_by) {
        this.cleared_by = cleared_by;
    }

    @Column(name = "cleared_by_name")
    public String getCleared_by_name() {
        return cleared_by_name;
    }

    public void setCleared_by_name(String cleared_by_name) {
        this.cleared_by_name = cleared_by_name;
    }

    @Column(name = "current_status")
    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
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
