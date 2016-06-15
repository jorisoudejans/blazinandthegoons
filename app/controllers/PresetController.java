package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Camera;
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

            models.Preset preset = models.Preset.createDummyPreset(name, script);

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
