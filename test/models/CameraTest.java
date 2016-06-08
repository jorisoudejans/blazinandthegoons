package models;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.test.Helpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test cameras.
 */
public class CameraTest {
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
     * Test creation of a camera.
     */
    @Test
    public void testCreate() {
        Camera c = new Camera();
        c.name = "Camera 1";
        c.ip = "0.0.0.0";
        c.save();

        assertEquals(1, Camera.find.all().size());
    }

    /**
     * Test creation of a camera with a location.
     */
    @Test
    public void testCreateLocation() {
        Camera c = new Camera();
        c.name = "Camera 1";
        c.ip = "0.0.0.0";
        c.save();

        Location l = new Location();
        l.name = "First location";
        l.cameras = Collections.singletonList(c);
        l.save();

        c.location = l;
        c.save();

        assertEquals(1, Location.find.byId(l.id).cameras.size());
    }

    /**
     * Stop fake app.
     */
    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }

}
