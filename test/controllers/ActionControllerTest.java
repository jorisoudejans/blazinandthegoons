package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

/**
 * Test the action controller.
 */
public class ActionControllerTest {

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
     * Tests basic request of action.
     */
    @Test
    public void testGetAllResponse() {
        Result result = new ActionController().getAll();
        assertEquals(Http.Status.OK, result.status());
        assertEquals("application/json", result.contentType().get());
        assertTrue(contentAsString(result).contains("[{"));
    }

    /**
     * Test get all actions.
     */
    @Test
    public void testGetAll() {
        models.Action a1 = new models.Action();
        a1.description = "ActionController One";
        a1.timestamp = (int) new Date().getTime();
        a1.save();

        models.Action a2 = new models.Action();
        a2.description = "ActionController Two";
        a2.timestamp = (int) new Date().getTime();
        a2.save();

        Result result = new ActionController().getAll();
        assertTrue(contentAsString(result).contains("ActionController One"));
        assertTrue(contentAsString(result).contains("ActionController Two"));

    }

    /**
     * Test get action.
     */
    @Test
    public void testGet() {
        models.Action a1 = new models.Action();
        a1.description = "ActionController One";
        a1.timestamp = (int) new Date().getTime();
        a1.save();

        Result result = new ActionController().get(a1.id);
        assertEquals(Http.Status.OK, result.status());
        assertEquals("application/json", result.contentType().get());
        assertTrue(contentAsString(result).contains("ActionController One"));
    }

}
