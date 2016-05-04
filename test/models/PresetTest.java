package models;

import models.Preset;
import org.junit.*;
import play.test.Helpers;
import play.Application;

import java.util.List;
import static  org.junit.Assert.*;

/**
 * Created by Shane on 4-5-2016.
 */
public class PresetTest {
    public static Application app;

    @BeforeClass
    public static void startApp() {
        app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);
    }

    @Test
    public void testCreate() {
        Preset pr = Preset.createPreset("preset1", 3, 50, 50, 50, 50);
        List<Preset> out = Preset.find.where().ilike("name", "preset1").findList();
        assertEquals(out.size(), 1);
        assertEquals(out.get(0), pr);
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }

}
