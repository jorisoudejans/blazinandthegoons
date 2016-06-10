package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;
import play.mvc.Result;
import util.camera.commands.PanTiltCommand;
import util.camera.commands.SnapshotCommand;

import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.Id;
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

    /**
     * Apply this preset.
     */
    @JsonIgnore
    public boolean apply() {
        Camera camera = Camera.make("Boilerplate camera", "192.168.10.101"); // TODO: should be changed, not hardcoded
        return new PanTiltCommand(tilt, pan).execute(camera);
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

