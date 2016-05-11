package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Controller for the Action class. This controller is used to handle
 * the json requests from the client.
 */
public class Action extends Controller {

    /**
     * Get a list of all Action objects in the database.
     * @return The list of all Action objects.
     */
    public Result index() {
        List<models.Action> actionList = models.Action.find.all();
        return ok(Json.toJson(actionList));
    }

    /**
     * Looks in the database to find the Action object with id.
     * @param id    The id of the Action object to be found.
     * @return  ok with the found object.
     */
    public Result get(Long id) {
        models.Action action = models.Action.find.byId(id);
        return ok(Json.toJson(action));
    }


    /**
     * Method that uses the json body to create and save an Action object.
     * @return  ok with the created object.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        JsonNode json = request().body().asJson();
        String des = json.findPath("description").textValue();
        int timestamp = json.findPath("timestamp").intValue();
        int duration = json.findPath("estTime").intValue();
        models.Preset preset = models.Preset.find.byId(json.findPath("preset").longValue());
        models.Script script = models.Script.find.byId(json.findPath("script").longValue());

        models.Action action = models.Action.createAction(des, timestamp, duration, preset, script);

        return ok(Json.toJson(action));
    }

}
