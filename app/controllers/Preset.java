package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Action;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;
import java.util.List;

public class Preset extends Controller {

    public Result index() {
        List<models.Preset> presetList = models.Preset.find.all();
        return ok(Json.toJson(presetList));
    }

    public Result get(Long id) {
        models.Preset preset = models.Preset.find.byId(id);
        return ok(Json.toJson(preset));
    }

}
