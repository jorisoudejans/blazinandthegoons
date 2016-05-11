package controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.mvc.Http;
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
    public void testIndexResponse() {
        Result result = new Script().index();
        assertEquals(Http.Status.OK, result.status());
        assertEquals("application/json", result.contentType().get());
        assertTrue(contentAsString(result).contains("[]"));
    }

    /**
     * Test script index.
     */
    @Test
    public void testIndex() {
        models.Script s1 = new models.Script();
        s1.name = "Script One";
        s1.creationDate = new Date();
        s1.save();

        models.Script s2 = new models.Script();
        s2.name = "Script Two";
        s2.creationDate = new Date();
        s2.save();

        Result result = new Script().index();
        assertTrue(contentAsString(result).contains("Script One"));
        assertTrue(contentAsString(result).contains("Script Two"));

    }

    /**
     * Test get script.
     */
    @Test
    public void testGet() {
        models.Script s1 = new models.Script();
        s1.name = "Script One";
        s1.creationDate = new Date();
        s1.save();

        Result result = new Script().get(s1.id);
        assertEquals(Http.Status.OK, result.status());
        assertEquals("application/json", result.contentType().get());
        assertTrue(contentAsString(result).contains("Script One"));

    }

    /**
     * Test get script status.
     */
    @Test
    public void testGetStatus() {
        models.Script s1 = new models.Script();
        s1.name = "Script One";
        s1.creationDate = new Date();
        s1.save();

        Result result = new Script().status(s1.id);
        assertEquals(Http.Status.OK, result.status());
        assertEquals("application/json", result.contentType().get());
        assertTrue(contentAsString(result).contains("Script One"));

    }

    /**
     * Tests creation of a script. TODO: Requires application HTTP stack for test
     */
    @Test
    public void testCreate() {
        //Result result = new Script().create();
        //assertEquals(OK, result.status());
    }


}
