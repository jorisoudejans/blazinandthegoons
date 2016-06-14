package controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;

/**
 * Test the preset controller.
 */
public class PresetControllerTest {

    private static Application app;

    /**
     * Start fake app.
     */
    @Before
    public void startApp() {
        app = fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);
    }

    /**
     * Stop fake app.
     */
    @After
    public void stopApp() {
        Helpers.stop(app);
    }

    /**
     * Tests basic request of presets.
     */
    @Test
    public void testGetAllResponse() {
        Result result = new PresetController().getAll();
        assertEquals(Http.Status.OK, result.status());
        assertEquals("application/json", result.contentType().get());
        assertTrue(contentAsString(result).contains("[{"));
    }

    /**
     * Test get all presets.
     */
    @Test
    public void testGetAll() {
        models.Preset p1 = new models.Preset();
        p1.name = "PresetController One";
        p1.camera = null;
        p1.save();

        models.Preset p2 = new models.Preset();
        p2.name = "PresetController Two";
        p2.camera = null;
        p2.save();

        Result result = new PresetController().getAll();
        assertTrue(contentAsString(result).contains("PresetController One"));
        assertTrue(contentAsString(result).contains("PresetController Two"));

    }

    /**
     * Test get action.
     */
    @Test
    public void testGet() {
        models.Preset p1 = new models.Preset();
        p1.name = "PresetController One";
        p1.camera = null;
        p1.save();

        Result result = new PresetController().get(p1.id);
        assertEquals(Http.Status.OK, result.status());
        assertEquals("application/json", result.contentType().get());
        assertTrue(contentAsString(result).contains("PresetController One"));
    }

}
