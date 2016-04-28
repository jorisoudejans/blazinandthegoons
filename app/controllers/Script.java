package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Script extends Controller {

    public Result index() {
        return ok(script.render("This is the script controller."));
    }


    public void findBugsExample() {
        boolean value = false;
        if (value = true) {
            //do Something
        } else {
            //else Do Something
        }

    }

}
