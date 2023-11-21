package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ShUsers generated by hbm2java
 */
@Entity
@Table(name="sh_users"
    ,catalog="seniorhighdb"
)
public class ShUsers  implements java.io.Serializable {


     private String username;
     private Boolean active;
     private Date created;
     private String fullname;
     private Boolean isAssess;
     private Date lastlogin;
     private String pwd;
     private String rights;
     private String syssection;

    public ShUsers() {
    }

	
    public ShUsers(String username, Date created, String pwd, String rights) {
        this.username = username;
        this.created = created;
        this.pwd = pwd;
        this.rights = rights;
    }
    public ShUsers(String username, Boolean active, Date created, String fullname, Boolean isAssess, Date lastlogin, String pwd, String rights, String syssection) {
       this.username = username;
       this.active = active;
       this.created = created;
       this.fullname = fullname;
       this.isAssess = isAssess;
       this.lastlogin = lastlogin;
       this.pwd = pwd;
       this.rights = rights;
       this.syssection = syssection;
    }
   
     @Id 

    
    @Column(name="username", unique=true, nullable=false, length=20)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    
    @Column(name="active")
    public Boolean getActive() {
        return this.active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created", nullable=false, length=19)
    public Date getCreated() {
        return this.created;
    }
    
    public void setCreated(Date created) {
        this.created = created;
    }

    
    @Column(name="fullname", length=50)
    public String getFullname() {
        return this.fullname;
    }
    
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    
    @Column(name="is_assess")
    public Boolean getIsAssess() {
        return this.isAssess;
    }
    
    public void setIsAssess(Boolean isAssess) {
        this.isAssess = isAssess;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="lastlogin", length=19)
    public Date getLastlogin() {
        return this.lastlogin;
    }
    
    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    
    @Column(name="pwd", nullable=false, length=65535)
    public String getPwd() {
        return this.pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    
    @Column(name="rights", nullable=false, length=65535)
    public String getRights() {
        return this.rights;
    }
    
    public void setRights(String rights) {
        this.rights = rights;
    }

    
    @Column(name="syssection", length=50)
    public String getSyssection() {
        return this.syssection;
    }
    
    public void setSyssection(String syssection) {
        this.syssection = syssection;
    }




}


