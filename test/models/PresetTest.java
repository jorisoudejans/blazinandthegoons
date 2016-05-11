package models;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.test.Helpers;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test presets.
 */
public class PresetTest {
    private static Application app;

    /**
     * Start fake app.
     */
    @BeforeClass
    public static void startApp() {
        app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);
    }

    /**
     * Test creation of a preset.
     */
    @Test
    public void testCreate() {
        Preset pr = Preset.createPreset("preset1", 0, 0, 0, 0, 0);
        List<Preset> out = Preset.find.where().ilike("name", "preset1").findList();
        assertEquals(out.size(), 1);
        assertEquals(out.get(0), pr);
    }

    /**
     * Stop fake app.
     */
    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }

}
