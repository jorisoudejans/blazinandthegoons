package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;
import play.mvc.Result;
import util.camera.commands.PanTiltCommand;
import util.camera.commands.SnapshotCommand;

import javax.imageio.ImageIO;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

import static play.mvc.Results.ok;

/**
 * The model class for Presets. This is the representation used for the database.
 */
@Entity
public class Preset extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH)
    public Camera camera;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH)
    public Script script;

    /**
     * Preset parameter data
     */
    public int pan;
    public int tilt;
    public int zoom;
    public int focus;
    public int iris;

    /**
     * State of this preset.
     */
    public Status status = Status.OK;

    /**
     * Returns the camera id to which this preset belongs. Used on client side
     * @return camera id if or 0
     */
    public Long getCameraId() {
        return camera != null ? camera.id : 0;
    }

    /**
     * Apply this preset.
     */
    @JsonIgnore
    public boolean apply() {
        if (isLinked()) {
            // TODO: Implement this
        }
        return false;
    }

    /**
     * Whether this preset is linked to a real preset.
     * @return <code>true</code> when linked, <code>false</code> when not
     */
    public boolean isLinked() {
        return camera != null && pan != 0 && tilt != 0 && zoom != 0 && focus != 0 && iris != 0;
    }

    /**
     * Link a new preset.
     * @param c the camera to link it with
     * @param pan camera value
     * @param tilt camera value
     * @param iris camera value
     * @param focus camera value
     * @param zoom camera value
     */
    public void link(Camera c, int pan, int tilt, int zoom, int focus, int iris) {
        this.camera = c;
        this.pan = pan;
        this.tilt = tilt;
        this.zoom = zoom;
        this.focus = focus;
        this.iris = iris;
        this.save();
    }

    /**
     * Get the thumbnail for this preset. May take some time to load due to moving the camera in the right position
     * @return a thumbnail image
     */
    @JsonIgnore
    public CompletionStage<Result> getThumbnail() {
        // apply this preset
        this.apply();

        return CompletableFuture.supplyAsync(new Supplier<byte[]>() {

            @Override
            public byte[] get() {
                // sleep this thread
                try {
                    Thread.sleep(3000);

                    BufferedImage i = new SnapshotCommand().get(Camera.make("Boilerplate", "192.168.10.101"), SnapshotCommand.RES_1280);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(i, "jpg", baos);

                    return baos.toByteArray();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;

            }
        }).thenApply(image -> ok(image).as("image/jpeg"));
    }

    public enum Status {

        @EnumValue("OK")
        OK,

        @EnumValue("FAULTY")
        FAULTY,

        @EnumValue("ERROR")
        ERROR,
    }


    public static Finder<Long, Preset> find = new Finder<>(Preset.class);


    /**
     * A static create function which can be called to create a Preset object
     * with the specified parameters.
     * @param name  The name of the preset.
     * @param script script to link to
     * @return The created Preset object.
     */
    public static Preset createDummyPreset(
            String name, Script script) {
        Preset pr = new Preset();
        pr.name = name;
        pr.script = script;
        pr.save();

        return pr;
    }

}

