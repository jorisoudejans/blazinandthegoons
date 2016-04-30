package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.*;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import java.util.Date;
import java.util.List;

import models.Action;

public class Script extends Controller {

    public Result index() {
        List<models.Script> scriptList = models.Script.find.all();
        return ok(Json.toJson(scriptList));
    }

    public Result get(Long id) {
        models.Script script = models.Script.find.byId(id);
        return ok(Json.toJson(script));
    }

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
        return notFound("Script "+id);
    }

}
