package controllers;

import models.Action;
import models.Script;
import models.Preset;
import java.util.*;
import views.html.*;
import play.*;
import play.mvc.*;
import play.libs.Json;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("First index"));
    }

    public Result directorView() {

        return ok(index.render("Your new application is ready."));
    }

}
