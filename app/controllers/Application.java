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
        // MOCK SCRIPT AND ACTION DATA
        //List<Action> actionlist = new ArrayList<Action>();

        if(models.Preset.find.byId(1L) == null) {
            Preset.createPreset("Nice view", 0, 0.f, 0.f, 0.f, 0.f);
            Preset.createPreset("Dirigent focus", 3, 0.f, 0.f, 0.f, 0.f);
            Preset.createPreset("Violin snare closeup", 2, 0.f, 0.f, 0.f, 0.f);
            Preset.createPreset("Contrabas player", 5, 0.f, 0.f, 0.f, 0.f);
        }

        Script s = new Script();
        s.name = "Mock script";
        //s.actions = actionlist;
        s.save();

        Action a1 = Action.createAction("Go to Trombone", 5, 3, models.Preset.find.byId(1L), s);
        Action a2 = Action.createAction("Go to Dirigent", 4, 2, models.Preset.find.byId(2L), s);
        Action a3 = Action.createAction("Go to Violin", 3, 1, models.Preset.find.byId(3L), s);
        Action a4 = Action.createAction("Go to Contrabas", 2, 0, models.Preset.find.byId(4L), s);
        //actionlist.addAll(Arrays.asList(a1,a2,a3,a4));

        for (int i = 0; i < 20; i++) { // create some more actions
            Action a = Action.createAction("Action "+i, i, i*2, models.Preset.find.byId(1L), s);
        }


        System.out.println("All ACTIONS");
        for(models.Action ac : models.Action.find.all())
            System.out.println(Json.toJson(ac));
        System.out.println();

        System.out.println("All SCRIPTS");
        for(models.Script sc : models.Script.find.all())
            System.out.println(Json.toJson(sc));
        System.out.println();

        System.out.println("All PRESETS");
        for(models.Preset pr : models.Preset.find.all())
            System.out.println(Json.toJson(pr));
        System.out.println();

        return ok(index.render("Your new application is ready."));
    }

}
