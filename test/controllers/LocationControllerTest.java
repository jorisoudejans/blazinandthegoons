package controllers;

import models.Camera;
import models.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.mvc.Http;
import play.mvc.LegacyWebSocket;
import play.mvc.Result;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;

/**
 * Runs some integration tests on the location API.
 */
public class LocationControllerTest {

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
     * Test location getAll.
     */
    @Test
    public void testGetAllBasic() {
        Location l = new Location();
        l.name = "Hello123";
        l.save();

        Result result = new LocationController().getAll();
        assertTrue(contentAsString(result).contains("Hello123"));
    }


    /**
     * Test location add camera.
     */
    /*@Test
    public void testGetAllAddCamera() {
        Location l = new Location();
        l.name = "Hello123";
        l.cameras = new ArrayList<>();
        l.save();

        Camera c = new Camera();
        c.name = "Camera 1";
        c.ip = "null";
        c.save();

        l.cameras.add(c);
        l.save();

        Result result = new LocationController().getAll();
        assertTrue(contentAsString(result).contains("null"));
    }*/

    /**
     * Test location add camera.
     */
    @Test
    public void testGetAllRemoveCamera() {
        Location l = new Location();
        l.name = "Hello123";
        l.save();

        Camera c = new Camera();
        c.name = "Camera 1";
        c.ip = "null";
        c.location = l;
        c.save();

        c.delete();

        Result result = new LocationController().getAll();
        assertFalse(contentAsString(result).contains("null"));
    }

}
