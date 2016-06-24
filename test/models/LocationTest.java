package models;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.test.Helpers;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Test locations.
 */
public class LocationTest {
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
     * Test creation of a location.
     */
    @Test
    public void testCreate() {
        Location l = new Location();
        l.name = "First location";
        l.save();

        // 1 + 1 due to database seeding
        assertEquals(3, Location.find.all().size());
    }

    /**
     * Test creation of a location with a camera.
     */
    @Test
    public void testCreateLocation() {
        Camera c = new Camera();
        c.name = "Camera 1";
        c.ip = "0.0.0.0";
        c.save();

        Location l = new Location();
        l.name = "Second location";
        l.cameras = Collections.singletonList(c);
        l.save();

        assertEquals(l.name, Location.find.byId(l.id).name);
    }

    /**
     * Stop fake app.
     */
    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }

}
