package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Action;
import models.ActiveScript;
import models.Camera;
import models.Preset;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.LegacyWebSocket;
import play.mvc.Result;
import play.mvc.WebSocket;
import util.socket.ScriptSocket;

import java.util.Date;
import java.util.List;

/**
 * Controls a script.
 */
public class ScriptController extends Controller {

    /**
     * Get all scripts in the database.
     * @return the scripts
     */
    public Result getAll() {
        List<models.Script> scriptList = models.Script.find.all();
        for (models.Script scr : scriptList) {
            System.out.println();
        }
        return ok(Json.toJson(scriptList));
    }

    /**
     * Get script by id.
     * @param id script id
     * @return script
     */
    public Result get(Long id) {
        models.Script script = models.Script.find.byId(id);
        return ok(Json.toJson(script));
    }

    /**
     * Create a new script.
     * @return the created script
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        JsonNode json = request().body().asJson();
        String name = json.findPath("name").textValue();
        models.Script script = Json.fromJson(json, models.Script.class);
        script.name = name;
        script.save();
        System.out.println(script);
        return ok(Json.toJson(script));
    }

    /**
     * Update a script.
     * @param id the script to update
     * @return the created script
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result save(Long id) {
        JsonNode json = request().body().asJson(); // get the JSON payload
        models.Script script = Json.fromJson(json, models.Script.class); // find the script
        if (script.id == -1) {
            models.Script actScript = new models.Script();
            actScript.name = script.name;
            actScript.save();
        } else {
            script.update();
        }
        return ok(Json.toJson(script)); // report back the updated script
    }

    /**
     * Add action to a script.
     * @param id script id
     * @return script with added action
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result addAction(Long id) {
        models.Script script = models.Script.find.byId(id);
        if (script != null) {
            JsonNode json = request().body().asJson();
            String description = json.findPath("description").textValue();
            Action action = new Action();
            action.description = description;
            script.actions.add(action);
            script.save();
            return ok(Json.toJson(script));
        }
        return notFound("ScriptController " + id);
    }

    /**
     * Remove action from a script.
     * @param scriptId the script which the action belongs to
     * @param actionId the action id in the script
     * @return script with added action
     */
    public Result removeAction(Long scriptId, Long actionId) {
        models.Script script = models.Script.find.byId(scriptId); // retrieve the script
        if (script != null) {
            Action action = Action.find.byId(actionId); // retrieve the action
            if (action != null) {
                //TODO: If the action is not in the action-list
                // of the script it will still delete it in the db
                script.actions.remove(action);
                script.save();
                action.delete(); // delete the action
                return ok(Json.toJson(script));
            }
            return notFound("ActionController " + actionId);
        }
        return notFound("ScriptController " + scriptId);
    }

    /**
     * Get a running script.
     * @return data of running script
     */
    public Result getActiveScript() {
        if (models.ActiveScript.find.all().size() != 0) {
            models.ActiveScript as = models.ActiveScript.find.all().get(0);
             return ok(Json.toJson(as));
        }
        return notFound("No active script");
    }

    /**
     * Run a script. Creates an extension of a ScriptController, i.e. an ActiveScript.
     * @param id script id
     * @return activeScript
     */
    public Result startScript(Long id) {
        models.Script s = models.Script.find.byId(id);
        if (s != null) {
            List<ActiveScript> allScripts = ActiveScript.find.all();
            for (ActiveScript as : allScripts) { // remove all scripts
                as.delete();
            }

            ActiveScript as = new ActiveScript();
            as.actionIndex = 0;
            as.runningTime = new Date().getTime();
            as.script = s;
            as.save();
            return ok(Json.toJson(as));
        }
        return notFound("ScriptController " + id);
    }

    /**
     * Get a new websocket instance.
     * @return websocket for scripts
     */
    public LegacyWebSocket<JsonNode> socket() {

        return new LegacyWebSocket<JsonNode>() {
            @Override
            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {
                ScriptSocket.getActive().join(in, out);
            }
        };
    }

}
