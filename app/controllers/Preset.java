package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Api controller for handling the json calls from the client.
 */
public class Preset extends Controller {

    /**
     * Get a list of all Preset objects in the database.
     * @return The list of all Preset objects.
     */
    public Result index() {
        List<models.Preset> presetList = models.Preset.find.all();
        return ok(Json.toJson(presetList));
    }

    /**
     * Looks in the database to find the Preset object with id.
     * @param id    The id of the Preset object to be found.
     * @return  ok with the found object.
     */
    public Result get(Long id) {
        models.Preset preset = models.Preset.find.byId(id);
        return ok(Json.toJson(preset));
    }

    /**
     * Method that uses the json body to create and save a Preset object.
     * @return  ok with the created object.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        JsonNode json = request().body().asJson();
        String name = json.findPath("name").textValue();
        int camera = json.findPath("camera").intValue();
        int pan = json.findPath("pan").intValue();
        int tilt = json.findPath("pan").intValue();
        int zoom = json.findPath("pan").intValue();
        int focus = json.findPath("pan").intValue();

        models.Preset preset = models.Preset.createPreset(name, camera, pan, tilt, zoom, focus);

        return ok(Json.toJson(preset));
    }

}
