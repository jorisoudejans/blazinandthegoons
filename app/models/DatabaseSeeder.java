package models;

import com.google.inject.Inject;
import play.api.db.evolutions.DynamicEvolutions;
import play.db.ebean.EbeanConfig;
import play.libs.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

        Location location = new Location();
        location.name = "Mola";
        location.save();

        Camera camera = Camera.make("Camera One", "192.166.10.101");
        camera.location = location;
        camera.presets = new ArrayList<>();
        camera.save();

        Camera camera2 = Camera.make("Camera Two", "192.166.10.101");
        camera2.location = location;
        camera2.presets = new ArrayList<>();
        camera2.save();

        location.cameras = Arrays.asList(camera, camera2);
        location.save();

        Script s = new Script(); // create new script
        s.name = "Mock script";
        s.location = location;
        s.save();

        Preset.createDummyPreset("Nice view", s); // unlinked
        Preset.createDummyPreset("Dirigent focus", s); // unlinked
        Preset p1 = Preset.createDummyPreset("Violin snare closeup", s); // linked
        Preset p2 = Preset.createDummyPreset("Contrabas player", s); // linked
        p1.camera = camera;
        p2.camera = camera2;
        p1.realPresetId = 1;
        p2.realPresetId = 2;
        camera.presets.add(p1);
        camera2.presets.add(p2);
        camera.save();
        camera2.save();

        System.out.println("Seeding database");

        // create actions
        Action a1 = Action.createAction(0, "Open on dirigent", 5, 8, models.Preset.find.byId(1L), s);
        Action a2 = Action.createAction(1, "Clarinets", 2, 8, models.Preset.find.byId(4L), s);
        Action a3 = Action.createAction(2, "Static on strings", 4, 12, models.Preset.find.byId(2L), s);
        Action a4 = Action.createAction(3, "Static on violins", 3, 12, models.Preset.find.byId(3L), s);
        Action a5 = Action.createAction(4, "Dirigent still", 2, 18, models.Preset.find.byId(4L), s);
        Action a6 = Action.createAction(5, "Flutes close-up", 2, 6, models.Preset.find.byId(1L), s);
        Action a7 = Action.createAction(6, "Harp", 2, 5, models.Preset.find.byId(3L), s);
        //actionlist.addAll(Arrays.asList(a1,a2,a3,a4));

        for (int i = 0; i < 20; i++) { // create some more actions
            Action a = Action.createAction(6 + i, "Action " + i, i, i * 2, models.Preset.find.byId(1L), s);
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
