package controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.mvc.Http;
import play.mvc.LegacyWebSocket;
import play.mvc.Result;
import play.test.Helpers;

import java.util.Date;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;

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
        scriptController.startScript((long) 1);

        Result result = new ScriptController().getActiveScript();
        assertEquals(Http.Status.OK, result.status());
        assertEquals("application/json", result.contentType().get());
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

        assertTrue(new ScriptController().socket() instanceof LegacyWebSocket);
    }

    /**
     * Tests creation of a script. TODO: Requires application HTTP stack for test
     */
    @Test
    public void testCreate() {
        //Result result = new ScriptController().create();
        //assertEquals(OK, result.status());
    }


}
