package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Script extends Controller {

    public static Result index() {
        return ok(script.render("This is the script controller."));
    }

}
