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
        Action act = Action.createAction(0,"decript", 0, 0, new Preset(), new Script());
        List<Action> out = Action.find.where().ilike("description", "decript").findList();
        assertEquals(out.size(), 1);
        assertEquals(out.get(0), act);
    }

    @Test
    public void testSetFlag() {
        Action act = Action.createAction(0,"decript", 0, 0, new Preset(), new Script());
        act.setFlag(Action.FlagType.OBSTRUCTED, "Dirigent moved to obtruct the position");

        assertEquals(true, act.flagged);
        assertEquals(Action.FlagType.OBSTRUCTED, act.flagType);
    }

    /**
     * Stop fake app.
     */
    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }

}
