package entity;
// Generated 03 29, 19 5:28:16 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tracks generated by hbm2java
 */
@Entity
@Table(name="tracks"
    ,catalog="seniorhighdb"
)
public class Tracks  implements java.io.Serializable {


     private String track;

    public Tracks() {
    }

    public Tracks(String track) {
       this.track = track;
    }
   
     @Id 

    
    @Column(name="track", unique=true, nullable=false, length=50)
    public String getTrack() {
        return this.track;
    }
    
    public void setTrack(String track) {
        this.track = track;
    }




}


