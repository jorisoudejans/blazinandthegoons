package controllers;

import models.Camera;
import models.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.LegacyWebSocket;
import play.mvc.Result;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static play.test.Helpers.*;

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


    @Test
    public void testAddLocation() {
        Http.RequestBuilder builder = fakeRequest("POST", "/api/locations/create");
        builder.header("Content-Type", "application/json");

        Map<String, String> maps = new HashMap<>();
        maps.put("name", "New location");

        builder.bodyJson(Json.toJson(maps));

        Result r = route(LocationControllerTest.app, builder);
        assertTrue(contentAsString(r).contains("New location"));
    }

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

        Http.RequestBuilder builder = fakeRequest("DELETE", "/api/locations/" + l.id + "/" + c.id + "/remove");
        builder.header("Content-Type", "application/json");

        Result r = route(LocationControllerTest.app, builder);
        assertEquals(200, r.status());
    }

}
