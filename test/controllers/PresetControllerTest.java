package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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
        p1.name = "Preset One";
        p1.save();

        Camera camera = Camera.make("Camera One", "0.0.0.0");
        camera.save();

        Http.RequestBuilder builder = fakeRequest("GET", "/api/presets/" + p1.id + "/link/" + camera.id);
        builder.header("Content-Type", "application/json");

        Result r = route(PresetControllerTest.app, builder);
        assertEquals(200, r.status());
    }

    /**
     * Test unlinking a preset.
     */
    @Test
    public void testUnlink() {
        models.Preset p1 = new models.Preset();
        p1.name = "Preset One";
        p1.save();

        Camera camera = Camera.make("Camera One", "0.0.0.0");
        camera.save();

        Http.RequestBuilder builderLink = fakeRequest("GET", "/api/presets/" + p1.id + "/link/" + camera.id);
        builderLink.header("Content-Type", "application/json");

        Http.RequestBuilder builderUnlink = fakeRequest("GET", "/api/presets/" + p1.id + "/unlink");
        builderUnlink.header("Content-Type", "application/json");

        p1 = Preset.find.byId(p1.id);

        assert p1 != null;

        assertEquals(0L, (long) p1.getCameraId());
    }

    /**
     * Test checking compatibility check on failure.
     */
    @Test
    public void testCompatibilityCheckFail() {
        models.Preset p1 = new models.Preset();
        p1.name = "Preset One";
        p1.save();
        models.Preset p2 = new models.Preset();
        p2.name = "Preset Two";
        p2.save();

        Camera camera = Camera.make("Camera One", "0.0.0.0");
        camera.save();

        Action action1 = new Action();
        action1.preset = p1;
        action1.save();
        Action action2 = new Action();
        action2.preset = p2;
        action2.save();
        Script script = new Script();
        script.save();
        script.actions.add(action1);
        script.actions.add(action2);
        script.save();

        p1.camera = camera;
        p1.save();
        p2.camera = camera;
        p2.save();
        assertFalse(new PresetController().checkCompatibility(script));
    }

    /**
     * Test checking compatibility check on success.
     */
    @Test
    public void testCompatibilityCheckSucceed() {
        models.Preset p1 = new models.Preset();
        p1.name = "Preset One";
        p1.save();
        models.Preset p2 = new models.Preset();
        p2.name = "Preset Two";
        p2.save();

        Camera camera1 = Camera.make("Camera One", "0.0.0.0");
        camera1.save();
        Camera camera2 = Camera.make("Camera Two", "0.0.0.0");
        camera2.save();

        Action action1 = new Action();
        action1.preset = p1;
        action1.save();
        Action action2 = new Action();
        action2.preset = p2;
        action2.save();
        Script script = new Script();
        script.save();
        script.actions.add(action1);
        script.actions.add(action2);
        script.save();

        p1.camera = camera1;
        p1.save();
        p2.camera = camera2;
        p2.save();
        Assert.assertTrue(new PresetController().checkCompatibility(script));
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
