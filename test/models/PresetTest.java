package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.test.Helpers;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Test presets.
 */
public class PresetTest {
    private static Application app;

    /**
     * Start fake app.
     */
    @Before
    public void startApp() {
        app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);
    }

    /**
     * Test creation of a preset.
     */
    @Test
    public void testCreate() {
        Preset pr = Preset.createDummyPreset("preset1", "test", null);
        List<Preset> out = Preset.find.where().ilike("name", "preset1").findList();
        assertEquals(out.size(), 1);
        assertEquals(out.get(0), pr);
    }

    @Test
    public void testIsLinked() {
        Camera camera = mock(Camera.class);
        int i = 65;
        Preset pr = Preset.createDummyPreset("preset1", "test", null);
        pr.link(camera, new PresetLinkData(1, 1, 1, 1, 1));
        assertTrue(pr.isLinked());
        assertEquals(camera, pr.camera);
    }

    /**
     * Stop fake app.
     */
    @After
    public void stopApp() {
        Helpers.stop(app);
    }

}
