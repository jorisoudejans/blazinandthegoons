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
        // MOCK SCRIPT AND ACTION DATA
        List<Action> actionlist = new ArrayList<Action>();

        Action a1 = Action.createAction("Go to Trombone", 5, 3, Preset.createPreset("Nice view", 0, 0.f, 0.f, 0.f, 0.f), null);
        Action a2 = Action.createAction("Go to Dirigent", 4, 2, Preset.createPreset("Dirigent focus", 3, 0.f, 0.f, 0.f, 0.f), null);
        Action a3 = Action.createAction("Go to Violin", 3, 1, Preset.createPreset("Violin snare closeup", 2, 0.f, 0.f, 0.f, 0.f), null);
        Action a4 = Action.createAction("Go to Contrabas", 2, 0, Preset.createPreset("Contrabas player focus", 5, 0.f, 0.f, 0.f, 0.f), null);
        /*Action a1 = new Action();
        a1.description = "Go to Trombone";
        a1.preset = Preset.createPreset("Nice view", 0, 0.f, 0.f, 0.f, 0.f);
        a1.save();
        Action a2 = new Action();
        a2.description = "Go to Dirigent";
        a2.preset = Preset.createPreset("Dirigent focus", 3, 0.f, 0.f, 0.f, 0.f);
        a2.save();
        Action a3 = new Action();
        a3.description = "Go to Violin";
        a3.preset = Preset.createPreset("Violin snare closeup", 2, 0.f, 0.f, 0.f, 0.f);
        a3.save();
        Action a4 = new Action();
        a4.description = "Go to Contrabas";
        a4.preset = Preset.createPreset("Contrabas player focus", 5, 0.f, 0.f, 0.f, 0.f);
        a4.save();*/
        a1.save();
        a2.save();
        a3.save();
        a4.save();

        actionlist.addAll(Arrays.asList(a1,a2,a3,a4));

        Script s = new Script();
        s.name = "Mock script";
        s.actions = actionlist;
        a1.script = s;
        a2.script = s;
        a3.script = s;
        a4.script = s;

        for(models.Action ac : models.Action.find.all())
            System.out.println(Json.toJson(ac));

        for(models.Script sc : models.Script.find.all())
            System.out.println(Json.toJson(sc));

        s.save();

        return ok(index.render("Your new application is ready."));
    }

}
