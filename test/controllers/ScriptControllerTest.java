package controllers;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.api.test.FakeRequest$;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.LegacyWebSocket;
import play.mvc.Result;
import play.test.Helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

/**
 * Runs some integration tests on the API.
 */
public class ScriptControllerTest {

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
     * Tests basic request of scripts.
     */
    @Test
    public void testGetAllResponse() {
        Result result = new ScriptController().getAll();
        assertEquals(Http.Status.OK, result.status());
        assertEquals("application/json", result.contentType().get());
        assertTrue(contentAsString(result).contains("[{"));
    }

    /**
     * Test script getAll.
     */
    @Test
    public void testGetAll() {
        models.Script s1 = new models.Script();
        s1.name = "ScriptController One";
        s1.creationDate = new Date();
        s1.save();

        models.Script s2 = new models.Script();
        s2.name = "ScriptController Two";
        s2.creationDate = new Date();
        s2.save();

        Result result = new ScriptController().getAll();
        assertTrue(contentAsString(result).contains("ScriptController One"));
        assertTrue(contentAsString(result).contains("ScriptController Two"));

    }

    /**
     * Test get script.
     */
    @Test
    public void testGet() {
        models.Script s1 = new models.Script();
        s1.name = "ScriptController One";
        s1.creationDate = new Date();
        s1.save();

        Result result = new ScriptController().get(s1.id);
        assertEquals(Http.Status.OK, result.status());
        assertEquals("application/json", result.contentType().get());
        assertTrue(contentAsString(result).contains("ScriptController One"));
    }

    /**
     * Test get script status. Should return a websocket
     */
    @Test
    public void testGetActiveScript() {
        models.Script s1 = new models.Script();
        s1.name = "ScriptController One";
        s1.creationDate = new Date();
        s1.save();

        ScriptController scriptController = new ScriptController();

        Result r1 = scriptController.getActiveScript();
        assertEquals(Http.Status.NOT_FOUND, r1.status());

        scriptController.startScript((long) 1);

        Result r2 = scriptController.getActiveScript();
        assertEquals(Http.Status.OK, r2.status());
        assertEquals("application/json", r2.contentType().get());
    }

    @Test
    public void testStartScript() {
        Result r = new ScriptController().startScript((long) -1234);
        assertEquals(Http.Status.NOT_FOUND, r.status());
    }

    /**
     * Test get script status. Should return a websocket
     */
    @Test
    public void testGetWebSocketScript() {
        models.Script s1 = new models.Script();
        s1.name = "ScriptController One";
        s1.creationDate = new Date();
        s1.save();

        ScriptController scriptController = new ScriptController();
        scriptController.startScript((long) 1);

        assertTrue(new ScriptController().socket() != null);
    }

    /**
     * Tests creation of a script.
     */
    @Test
    public void testCreate() {
        Http.RequestBuilder builder = fakeRequest("POST", "/api/scripts/create");
        builder.header("Content-Type", "application/json");

        Map<String, String> maps = new HashMap<>();
        maps.put("name", "New script");

        builder.bodyJson(Json.toJson(maps));

        Result r = route(ScriptControllerTest.app, builder);
        System.out.println("Result: " + contentAsString(r));
        assertTrue(contentAsString(r).contains("New script"));
    }

    /**
     * Tests update of a script.
     */
    @Test
    public void testUpdateNew() {
        Http.RequestBuilder builder = fakeRequest("POST", "/api/scripts/-1");
        builder.header("Content-Type", "application/json");

        Map<String, Object> maps = new HashMap<>();
        maps.put("id", -1);
        maps.put("name", "New script");

        builder.bodyJson(Json.toJson(maps));

        Result r = route(ScriptControllerTest.app, builder);
        System.out.println("Result: " + contentAsString(r));
        assertTrue(contentAsString(r).contains("New script"));
    }

    /**
     * Tests update of a script.
     */
    @Test
    public void testUpdateScriptNotNew() {
        models.Script s1 = new models.Script();
        s1.name = "ScriptController One";
        s1.creationDate = new Date();
        s1.save();

        Http.RequestBuilder builder = fakeRequest("POST", "/api/scripts/" + s1.id);
        builder.header("Content-Type", "application/json");

        Map<String, Object> maps = new HashMap<>();
        maps.put("id", s1.id);
        maps.put("name", "New script");

        builder.bodyJson(Json.toJson(maps));

        Result r = route(ScriptControllerTest.app, builder);
        System.out.println("Result: " + contentAsString(r));
        assertTrue(contentAsString(r).contains("New script"));
    }

    /**
     * Tests adding an action to a script.
     */
    @Test
    public void testAddAction1() {
        models.Script s1 = new models.Script();
        s1.name = "ScriptController One";
        s1.creationDate = new Date();
        s1.save();

        Http.RequestBuilder builder = fakeRequest("POST", "/api/scripts/" + s1.id + "/actions/create");
        builder.header("Content-Type", "application/json");

        Map<String, Object> maps = new HashMap<>();
        maps.put("description", "great desc");

        builder.bodyJson(Json.toJson(maps));
        Result r = route(ScriptControllerTest.app, builder);
        System.out.println("addAction Result: " + contentAsString(r));
        assertTrue(contentAsString(r).contains("great desc"));
    }

    /**
     * Test right response of adding an action to a non-existing script.
     */
    @Test
    public void testAddAction2() {
        Http.RequestBuilder builder = fakeRequest("POST", "/api/scripts/546547/actions/create");
        builder.header("Content-Type", "application/json");

        Map<String, Object> maps = new HashMap<>();
        maps.put("description", "great desc");

        builder.bodyJson(Json.toJson(maps));
        Result r = route(ScriptControllerTest.app, builder);
        assertEquals(Http.Status.NOT_FOUND, r.status());
    }

    /**
     * Tests removing an action from a script.
     */
    @Test
    public void testRemoveAction1() {
        models.Action a1 = new models.Action();
        a1.description = "simple desc";

        models.Script s1 = new models.Script();
        s1.name = "ScriptController One";
        s1.creationDate = new Date();
        s1.actions.add(a1);
        s1.save();

        Result r = new ScriptController().removeAction(s1.id, a1.id);

        System.out.println("removeAction Result: " + contentAsString(r));

        assertTrue(!contentAsString(r).contains("simple desc"));
        assertTrue(contentAsString(r).contains("\"actions\":[]"));
    }

    /**
     * Test right response of removing action with non-existing script or action.
     */
    @Test
    public void testRemoveAction2() {
        models.Script s1 = new models.Script();
        s1.name = "ScriptController One";
        s1.creationDate = new Date();
        s1.save();

        Result r = new ScriptController().removeAction(s1.id, (long) -300);
        assertEquals(Http.Status.NOT_FOUND, r.status());

        r = new ScriptController().removeAction((long) -300, (long) -300);
        assertEquals(Http.Status.NOT_FOUND, r.status());
    }

    @Test
    public void testUpdateActiveScript() {
        models.Script s1 = new models.Script();
        s1.name = "ScriptController One";
        s1.creationDate = new Date();
        s1.save();

        ScriptController scriptController = new ScriptController();

        Result r1 = scriptController.updateActiveScript(s1.id);
        assertEquals(Http.Status.NOT_FOUND, r1.status());

        scriptController.startScript(s1.id);

        Http.RequestBuilder builder = fakeRequest("GET", "/api/scripts/" + s1.id + "/update");
        builder.header("Content-Type", "application/json");

        Map<String, Object> maps = new HashMap<>();
        maps.put("actionIndex", 8);

        builder.bodyJson(Json.toJson(maps));
        Result r = route(ScriptControllerTest.app, builder);

        System.out.println("UPdate Result: " + contentAsString(r));
        assertEquals(Http.Status.OK, r.status());
        assertTrue(contentAsString(r).contains("\"actionIndex\":8"));
    }


}
