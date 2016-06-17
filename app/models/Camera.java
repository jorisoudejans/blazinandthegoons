package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;
import util.camera.commands.FocusCommand;
import util.camera.commands.IrisCommand;
import util.camera.commands.PanTiltCommand;
import util.camera.commands.ZoomCommand;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    /**
     * Returns the camera's ip address.
     * @return IP address
     */
    @JsonIgnore
    public String getIp() {
        return ip;
    }

    /**
     * Get the camera values the camera is currently in
     * @return current values
     */
    @JsonIgnore
    public PresetLinkData getCameraValues() {
        Integer[] panTilt = new PanTiltCommand(0, 0).get(this);
        Integer focus = new FocusCommand(0).get(this);
        Integer iris = new IrisCommand(0).get(this);
        Integer zoom = new ZoomCommand(0).get(this);
        if (panTilt != null && focus != null && iris != null && zoom != null) {
            return new PresetLinkData(panTilt[0], panTilt[1], zoom, focus, iris);
        }
        return new PresetLinkData(0, 0, 0, 0, 0);
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
