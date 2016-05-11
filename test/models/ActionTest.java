package models;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.test.Helpers;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test actions.
 */
public class ActionTest {
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
     * Test creation of action.
     */
    @Test
    public void testCreate() {
        Action act = Action.createAction("decript", 0, 0, new Preset(), new Script());
        List<Action> out = Action.find.where().ilike("description", "decript").findList();
        assertEquals(out.size(), 1);
        assertEquals(out.get(0), act);
    }

    /**
     * Stop fake app.
     */
    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }

}
