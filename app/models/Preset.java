package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;
import play.mvc.Result;
import util.camera.commands.FocusCommand;
import util.camera.commands.IrisCommand;
import util.camera.commands.PanTiltCommand;
import util.camera.commands.SnapshotCommand;
import util.camera.commands.ZoomCommand;

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

    private static final int SNAPSHOTSLEEP = 3000;

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    public String description;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH)
    public Camera camera;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH)
    public Script script;

    /**
     * Preset parameter data.
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
     * Returns the camera id to which this preset belongs. Used on client side.
     * @return camera id if or 0
     */
    public Long getCameraId() {
        return camera != null ? camera.id : 0;
    }

    /**
     * Apply this preset.
     * @return true if the preset was correctly applied, false otherwise.
     */
    @JsonIgnore
    public boolean apply() {
        if (isLinked()) {
            boolean f = new FocusCommand(focus).execute(camera);
            boolean pt = new PanTiltCommand(tilt, pan).execute(camera);
            boolean z = new ZoomCommand(zoom).execute(camera);
            boolean i = new IrisCommand(iris).execute(camera);
            return f && pt && z && i;
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
     * @param linkData preset data to link with
     */
    public void link(Camera c, PresetLinkData linkData) {
        this.camera = c;
        this.pan = linkData.getPan();
        this.tilt = linkData.getTilt();
        this.zoom = linkData.getZoom();
        this.focus = linkData.getFocus();
        this.iris = linkData.getIris();
        this.save();
    }

    /**
     * Get the thumbnail for this preset.
     * May take some time to load due to moving the camera in the right position
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
                    Thread.sleep(SNAPSHOTSLEEP);

                    BufferedImage i = new SnapshotCommand().get(camera, SnapshotCommand.RES_1280);

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

    /**
     * The 3 possible statuses a preset can have.
     */
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
     * @param description Description of the preset.
     * @param script script to link to
     * @return The created Preset object.
     */
    public static Preset createDummyPreset(
            String name, String description, Script script) {
        Preset pr = new Preset();
        pr.name = name;
        pr.script = script;
        pr.description = description;
        pr.save();

        return pr;
    }

}

