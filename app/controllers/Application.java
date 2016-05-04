package controllers;

import models.Action;
import models.Script;
import java.util.*;
import views.html.*;
import play.*;
import play.mvc.*;

public class Application extends Controller {

    public Result index() {
        // MOCK SCRIPT AND ACTION DATA
        List<Action> actionlist = new ArrayList<Action>();

        Action a1 = new Action();
        a1.description = "Go to Trombone";
        Action a2 = new Action();
        a2.description = "Go to Dirigent";
        Action a3 = new Action();
        a3.description = "Go to Violin";
        Action a4 = new Action();
        a4.description = "Go to Contrabas";

        actionlist.addAll(Arrays.asList(a1,a2,a3,a4));

        Script s = new Script();
        s.name = "Mock script";
        s.actions = actionlist;
        a1.script = s;
        a2.script = s;
        a3.script = s;
        a4.script = s;

        s.save();

        return ok(index.render("Your new application is ready."));
    }

}
