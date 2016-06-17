package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Camera;
import models.Script;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

/**
 * Test the preset controller.
 */
public class PresetControllerTest {

    private static Application app;

    private Script script;

    /**
     * Start fake app.
     */
    @Before
    public void startApp() {
        app = fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);

        script = new Script();
        script.name = "My script";
        script.save();
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

    /**
     * Tests linking of preset.
     */
    @Test
    public void testLink() {
        models.Preset p1 = new models.Preset();
        p1.name = "PresetController One";
        p1.save();

        Camera camera = Camera.make("Camera One", "0.0.0.0");
        camera.save();

        Http.RequestBuilder builder = fakeRequest("POST", "/api/presets/" + p1.id + "/link");
        builder.header("Content-Type", "application/json");

        Map<String, Object> maps = new HashMap<>();
        maps.put("cameraId", camera.id);
        maps.put("pan", 1);
        maps.put("tilt", 2);
        maps.put("zoom", 3);
        maps.put("focus", 4);
        maps.put("iris", 5);

        builder.bodyJson(Json.toJson(maps));

        Result r = route(PresetControllerTest.app, builder);
        JsonNode json = Json.parse(contentAsString(r));

        assertEquals(4, json.findPath("focus").asInt());
    }

    /**
     * Tests linking of preset.
     */
    @Test
    public void testCreate() {
        Http.RequestBuilder builder = fakeRequest("POST", "/api/scripts/" + script.id + "/presets/create");
        builder.header("Content-Type", "application/json");

        Map<String, Object> maps = new HashMap<>();
        maps.put("name", "Preset name");

        builder.bodyJson(Json.toJson(maps));

        Result r = route(PresetControllerTest.app, builder);
        JsonNode json = Json.parse(contentAsString(r));

        assertEquals("Preset name", json.findPath("name").asText());
    }

    /**
     * Tests linking of preset.
     */
    @Test
    public void testCreateNotFound() {
        Http.RequestBuilder builder = fakeRequest("POST", "/api/scripts/" + 100 + "/presets/create");
        builder.header("Content-Type", "application/json");

        Map<String, Object> maps = new HashMap<>();
        maps.put("name", "Preset name");

        builder.bodyJson(Json.toJson(maps));

        Result r = route(PresetControllerTest.app, builder);
        assertEquals(404, r.status());
    }

    /**
     * Tests linking of preset.
     */
    @Test
    public void testApplyPresetNotFound() {
        models.Preset p1 = new models.Preset();
        p1.name = "PresetController One";
        p1.save();

        Camera camera = Camera.make("Camera One", "0.0.0.0");
        camera.save();

        Http.RequestBuilder builder = fakeRequest("GET", "/api/presets/" + 100 + "/activate");
        builder.header("Content-Type", "application/json");

        Result r = route(PresetControllerTest.app, builder);
        assertEquals(404, r.status()); // We can only look at the status reported back by the server
    }

}
