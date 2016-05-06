package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Shane on 3-5-2016.
 */
@Entity
public class Preset extends Model {

    @Id
    public Long id;

    public String name;
    public int camera;
    public float pan;
    public float tilt;
    public float zoom;
    public float focus;

    public static Finder<Long, Preset> find = new Finder<Long,Preset>(Preset.class);


    public static Preset createPreset(String name, int camera, float pan, float tilt, float zoom, float focus) {
        Preset pr = new Preset();
        pr.name = name;
        pr.camera = camera;
        pr.pan = pan;
        pr.tilt = tilt;
        pr.zoom = zoom;
        pr.focus = focus;
        pr.save();

        return pr;
    }
}

