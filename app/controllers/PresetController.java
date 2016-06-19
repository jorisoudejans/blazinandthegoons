package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Action;
import models.Camera;
import models.Preset;
import models.Script;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Api controller for handling the json calls from the client.
 */
public class PresetController extends Controller {

    /**
     * Get a list of all PresetController objects in the database.
     * @return The list of all PresetController objects.
     */
    public Result getAll() {
        List<models.Preset> presetList = models.Preset.find.all();
        return ok(Json.toJson(presetList));
    }

    /**
     * Looks in the database to find the PresetController object with id.
     * @param id    The id of the PresetController object to be found.
     * @return  ok with the found object.
     */
    public Result get(Long id) {
        models.Preset preset = models.Preset.find.byId(id);
        return ok(Json.toJson(preset));
    }

    /**
     * Method that uses the json body to create and save a PresetController object.
     * @param id script that owns this preset
     * @return  ok with the created object.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result create(Long id) {
        Script script = Script.find.byId(id);
        if (script != null) {
            JsonNode json = request().body().asJson();
            String name = json.findPath("name").textValue();
            String description = json.findPath("description").textValue();

            models.Preset preset = models.Preset.createDummyPreset(name, description, script);

            return ok(Json.toJson(preset));
        }
        return notFound();
    }

    /**
     * Applies a preset.
     * @param id preset id
     * @return whether it worked
     */
    public Result apply(Long id) {
        models.Preset preset = models.Preset.find.byId(id); // find preset
        if (preset != null) {
            if (preset.apply()) { // apply preset
                return ok("Applied");
            }
            return internalServerError(); // return error
        }
        return notFound();
    }

    /**
     * Link a preset to a camera and preset values of this camera.
     * @param id preset id
     * @param cameraId camera id
     * @return the updated preset
     */
    public Result link(Long id, Long cameraId) {
        Preset preset = Preset.find.byId(id); // find preset
        if (preset != null) {
            Camera camera = Camera.find.byId(cameraId);
            if (camera != null) {
                // find the camera values

                Integer[] values = camera.getCameraValues();
                preset.camera = camera;
                preset.pan = values[0];
                preset.tilt = values[1];
                preset.zoom = values[2];
                preset.focus = values[3];
                preset.iris = values[4];
                //savePresetThumbnail(preset);
                checkCompatibility(preset.script);
                preset.save();

                return ok(Json.toJson(preset));
            }
            return notFound("Camera not found");
        }
        return notFound("Preset not found");
    }

    /**
     * Check if camera linking does not mean two actions one the same camera are adjacent.
     * @param script script to check.
     * @return true if link is compatible, false if some disputes arise.
     */
    public boolean checkCompatibility(Script script) {
        if (script == null) {
            return true;
        }
        Action prev = null;
        boolean isCompatible = true;
        List<Action> actions = script.actions;
        for (Action current : actions) {
            if (prev != null) {
                if ((current.preset.getCameraId() != 0)
                        && current.preset.getCameraId().equals(prev.preset.getCameraId())) {
                    current.setFlag(
                            Action.FlagType.INCOMPATIBLE,
                            "Action not compatible with action \"" + prev.description + "\""
                    );
                    isCompatible = false;
                } else if (current.flagType == Action.FlagType.INCOMPATIBLE) {
                    current.flagged = false;
                }
            }
            prev = current;
        }
        return isCompatible;
    }

    /**
     * Saves the current image a camera has to the preset.
     * @param preset preset to be saved to.
     * @return true if saving succeeded, false otherwise.
     */
    /*public boolean savePresetThumbnail(Preset preset) {
        try {
            Camera camera = preset.camera;
            if (camera == null) {
                return false;
            }
            BufferedImage i = new SnapshotCommand().get(camera, SnapshotCommand.RES_1280);
            if (i == null) {
                return false;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(i, "jpg", baos);
            preset.image = baos.toByteArray();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/

    /**
     * Unlink a preset, remove the linked camera.
     * @param id preset id
     * @return the updated preset
     */
    public Result unlink(Long id) {
        Preset preset = Preset.find.byId(id); // find preset
        if (preset != null) {
            preset.unlink();
            return ok(Json.toJson(preset));
        }
        return notFound("Preset not found");
    }

    /**
     * Get thumbnail for this preset. takes at least some seconds to complete.
     * We should cache this in the future
     * @param id the preset
     * @return the image after sometime
     */
    public CompletionStage<Result> thumbnail(Long id) {
        models.Preset preset = models.Preset.find.byId(id); // find preset
        if (preset != null) {
            return preset.getThumbnail();
        }
        return null;
    }

}
