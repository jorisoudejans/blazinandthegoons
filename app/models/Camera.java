package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a camera to record with.
 */
@Entity
public class Camera extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String ip;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JsonIgnore
    public Location location;

    @OneToMany(mappedBy = "camera", cascade = CascadeType.ALL)
    public List<Preset> presets;

    public long deactTime = 0;

    /**
     * Returns the camera's ip address.
     * @return IP address
     */
    @JsonIgnore
    public String getIp() {
        return ip;
    }

    public static Finder<Long, Camera> find = new Finder<>(Camera.class);

    /**
     * Creates a new camera instance and *DOES NOT* save it.
     * @param name the name it should name
     * @param ip the ip it should have
     * @return new camera object
     */
    public static Camera make(String name, String ip) {
        Camera c = new Camera();
        c.name = name;
        c.ip = ip;
        return c;
    }

}
