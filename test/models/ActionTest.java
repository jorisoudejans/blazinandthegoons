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
        Action act = new Action();
        act.index = 0;
        act.description = "Description";
        act.timestamp = 0;
        act.duration = 0;
        act.preset = new Preset();
        act.script = new Script();
        act.save();

        List<Action> out = Action.find.where().ilike("description", "Description").findList();
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
