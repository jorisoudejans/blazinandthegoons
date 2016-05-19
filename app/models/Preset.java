package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The model class for Presets. This is the representation used for the database.
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
    public int pan;

    @Constraints.Required
    public int tilt;

    @Constraints.Required
    public int zoom;

    @Constraints.Required
    public int focus;

    public static Finder<Long, Preset> find = new Finder<>(Preset.class);


    /**
     * A static create function which can be called to create a Preset object
     * with the specified parameters.
     * @param name  The name of the preset.
     * @param camera    The camera for which the preset is created.
     * @param pan   The pan position of the camera.
     * @param tilt  The tilt position of the camera.
     * @param zoom  The zoom value of the camera.
     * @param focus The focus value of the camera.
     * @return The created Preset object.
     */
    public static Preset createPreset(
            String name, int camera, int pan, int tilt, int zoom, int focus) {
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

