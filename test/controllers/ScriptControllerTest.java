
import controllers.Script;
import org.junit.*;
import play.Application;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.Date;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

/**
 * Runs some integration tests on the API
 *
 * Created by hidde on 5/3/16.
 */
public class ScriptControllerTest {

    private static Application app;

    @Before
    public void startApp() {
        app = fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);
    }

    @After
    public void stopApp() {
        Helpers.stop(app);
    }

    /**
     * Tests basic request of scripts
     */
    @Test
    public void testIndexResponse() {
        Result result = new Script().index();
        assertEquals(OK, result.status());
        assertEquals("application/json", result.contentType().get());
        assertTrue(contentAsString(result).contains("[]"));
    }

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

    @Test
    public void testGet() {
        models.Script s1 = new models.Script();
        s1.name = "Script One";
        s1.creationDate = new Date();
        s1.save();

        Result result = new Script().get(s1.id);
        assertEquals(OK, result.status());
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
