package models;

import org.junit.*;
import play.Application;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Test cameras.
 */
public class CameraTest {
    private Application app;

    /**
     * Start fake app.
     */
    @Before
    public void startApp() {
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
        c.location = null;
        c.presets = new ArrayList<>();
        c.save();

        // 1 + 2, 2 pre - seeded
        assertEquals(1 + 2, Camera.find.all().size());
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
    @After
    public void stopApp() {
        Helpers.stop(app);
    }

}
