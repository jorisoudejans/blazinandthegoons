package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }
    public Result running() { return ok(running.render("")); }
    public Result manage() { return ok(manage.render("")); }

}
