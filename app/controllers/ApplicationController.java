package controllers;

import models.Action;
import models.Script;
import models.Preset;
import java.util.*;
import views.html.*;
import play.*;
import play.mvc.*;
import play.libs.Json;

public class ApplicationController extends Controller {

    public Result index() {
        return ok(overview.render("Home"));
    }

    public Result directorView() {

        return ok(director.render("Director View"));
    }

    public Result overviewView() {
        return ok(overview.render("ScriptController selection"));
    }

    public Result editView(Long id) {
        return ok(edit.render("Edit script", id));
    }
    public Result editNewView() {
        return ok(edit.render("Edit script", (long) -1));
    }

}
