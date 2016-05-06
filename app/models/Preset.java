package models;

import com.avaje.ebean.Ebean;
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

    @Constraints.Required
    public String name;

    @Constraints.Required
    public int camera;

    @Constraints.Required
    public float pan;

    @Constraints.Required
    public float tilt;

    @Constraints.Required
    public float zoom;

    @Constraints.Required
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

    public static Preset createPresetWithFixedId(Long id, String name, int camera, float pan, float tilt, float zoom, float focus) {
        Preset pr = new Preset();
        pr.id = id;
        pr.name = name;
        pr.camera = camera;
        pr.pan = pan;
        pr.tilt = tilt;
        pr.zoom = zoom;
        pr.focus = focus;
        if(Preset.find.byId(id) == null)
            pr.save();

        return pr;
    }
}

