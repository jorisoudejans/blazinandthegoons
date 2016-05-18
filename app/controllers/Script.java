package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Action;
import models.ActiveScript;
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
public class Script extends Controller {

    /**
     * Get all scripts in the database.
     * @return the scripts
     */
    public Result getAll() {
        List<models.Script> scriptList = models.Script.find.all();
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
        models.Script script = new models.Script();
        script.name = name;
        script.creationDate = new Date(); // now
        script.save();
        return ok(Json.toJson(script));
    }

    /**
     * Update a script.
     * @param id the script to update
     * @return the created script
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result update(Long id) {
        models.Script script = models.Script.find.byId(id); // find the script
        if (script != null) {
            JsonNode json = request().body().asJson(); // get the JSON payload
            script = Json.fromJson(json, models.Script.class);
            script.update();
            return ok(Json.toJson(script)); // report back the updated script
        }
        return notFound();
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
        return notFound("Script " + id);
    }

    /**
     * Remove action from a script.
     * @param scriptId the script which the action belongs to
     * @param actionId the action id in the script
     * @return script with added action
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result removeAction(Long scriptId, Long actionId) {
        models.Script script = models.Script.find.byId(scriptId); // retrieve the script
        if (script != null) {
            Action action = Action.find.byId(actionId); // retrieve the action
            if (action != null) {
                action.delete(); // retrieve the action
                return ok(Json.toJson(script));
            }
            return notFound("Action " + actionId);
        }
        return notFound("Script " + scriptId);
    }

    /**
     * Get a running script.
     * @return data of running script
     */
    public Result getActiveScript() {
        models.ActiveScript as = models.ActiveScript.find.all().get(0);
        if (as != null) {
            return ok(Json.toJson(as));
        }
        return notFound("No active script");
    }

    /**
     * Run a script. Creates an extension of a Script, i.e. an ActiveScript.
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
        return notFound("Script " + id);
    }

    /**
     * Update current action index. Sets the action currently being executed.
     * @param id script id
     * @return updated script
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateActiveScript(Long id) {
        models.Script script = models.Script.find.byId(id);
        if (script != null && script.activeScript != null) {
            JsonNode json = request().body().asJson();
            script.activeScript.actionIndex = json.findPath("actionIndex").intValue();
            script.save();
            return ok(Json.toJson(script.activeScript));
        }
        return notFound("Script " + id);
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
