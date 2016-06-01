package models;

import com.google.inject.Inject;
import play.api.db.evolutions.DynamicEvolutions;
import play.db.ebean.EbeanConfig;
import play.libs.Json;

/**
 * Seeds the database.
 */
public class DatabaseSeeder {

    /**
     * Requires ebean.
     * @param config ebean config
     * @param evolutions evolutions object
     */
    @Inject
    public DatabaseSeeder(EbeanConfig config, DynamicEvolutions evolutions) {

        System.out.println("Seeding database: " + config + " " + evolutions);

        if (Preset.find.byId(1L) == null) { // create presets if not present
            Preset.createPreset("Nice view", 0, 10000, 10000, 0, 0);
            Preset.createPreset("Dirigent focus", 3, 15000, 10000, 0, 0);
            Preset.createPreset("Violin snare closeup", 2, 8000, 6000, 0, 0);
            Preset.createPreset("Contrabas player", 5, 500, 800, 0, 0);
        }

        System.out.println("Seeding database");

        Script s = new Script(); // create new script
        s.name = "Mock script";
        //s.actions = actionlist;
        s.save();

        // create actions
        Action a1 = Action.createAction("Go to Trombone", 5, 3, models.Preset.find.byId(1L), s);
        Action a2 = Action.createAction("Go to Dirigent", 4, 2, models.Preset.find.byId(2L), s);
        Action a3 = Action.createAction("Go to Violin", 3, 1, models.Preset.find.byId(3L), s);
        Action a4 = Action.createAction("Go to Contrabas", 2, 0, models.Preset.find.byId(4L), s);
        //actionlist.addAll(Arrays.asList(a1,a2,a3,a4));

        for (int i = 0; i < 20; i++) { // create some more actions
            Action a = Action.createAction("Action " + i, i, i * 2, models.Preset.find.byId(1L), s);
        }
        //printall(); // for debug
    }

    /**
     * Prints all data that we just created.
     */
    private void printall() {

        System.out.println("All ACTIONS");
        for (Action ac : models.Action.find.all())
            System.out.println(Json.toJson(ac));
        System.out.println();

        System.out.println("All SCRIPTS");
        for (Script sc : models.Script.find.all())
            System.out.println(Json.toJson(sc));
        System.out.println();

        System.out.println("All PRESETS");
        for (Preset pr : models.Preset.find.all())
            System.out.println(Json.toJson(pr));
        System.out.println();
    }

}
