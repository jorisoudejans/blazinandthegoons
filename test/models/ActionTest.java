package models;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.test.Helpers;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Shane on 4-5-2016.
 */
public class ActionTest {
    public static Application app;

    @BeforeClass
    public static void startApp() {
        app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);
    }

    @Test
    public void testCreate() {
        Action act = Action.createAction("decript", 10, 5, new Preset(), new Script());
        List<Action> out = Action.find.where().ilike("description", "decript").findList();
        assertEquals(out.size(), 1);
        assertEquals(out.get(0), act);
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }

}
