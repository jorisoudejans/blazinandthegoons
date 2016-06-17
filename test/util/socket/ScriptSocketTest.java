package util.socket;

import com.fasterxml.jackson.databind.JsonNode;
import models.ActiveScript;
import models.Script;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import play.Application;
import play.libs.Json;
import play.mvc.WebSocket;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static play.test.Helpers.fakeApplication;

/**
 * Class to test ScriptSocket class.
 */
public class ScriptSocketTest {

    protected ScriptSocket scriptSocket;

    @Mock
    protected WebSocket.In<JsonNode> socket_in_p1;
    @Mock
    protected WebSocket.In<JsonNode> socket_in_p2;
    @Mock
    protected WebSocket.Out<JsonNode> socket_out_p1;
    @Mock
    protected WebSocket.Out<JsonNode> socket_out_p2;

    private static Application app;

    /**
     * Start fake app.
     */
    @Before
    public void startApp() {
        app = fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);

        MockitoAnnotations.initMocks(this);

        scriptSocket = ScriptSocket.getActive();
        doAnswer(invocationOnMock -> null).when(socket_in_p1).onMessage(any(Consumer.class));
    }

    /**
     * Stop fake app.
     */
    @After
    public void stopApp() {
        Helpers.stop(app);
    }

    /**
     * Tests adding a single socket and getting output.
     */
    @Test
    public void test_addSingle() {
        scriptSocket.join(socket_in_p1, socket_out_p1); // add the mocks

        verify(socket_out_p1).write(any(JsonNode.class)); // verify write was called
    }

    /**
     * Tests adding a single socket and writing to the socket with no active script.
     */
    @Test
    public void test_singleWriteNoActiveScript() {
        scriptSocket.join(socket_in_p1, socket_out_p1); // add the mocks
        JsonNode node = mock(JsonNode.class);
        scriptSocket.processInput(socket_in_p1, node);

        verify(socket_out_p1, times(2)).write(Json.toJson(new ArrayList<>())); // retrieve empty array for no active script
    }

    /**
     * Tests adding a single socket and writing to the socket with active script.
     */
    @Test
    public void test_singleWrite() {
        scriptSocket.join(socket_in_p1, socket_out_p1); // add the mocks
        ActiveScript activeScript = activateScript();
        activeScript.actionIndex++; // IMPORTANT: don't save!
        scriptSocket.processInput(socket_in_p1, Json.toJson(activeScript));

        verify(socket_out_p1, times(1)).write(Json.toJson(activeScript)); //
    }

    /**
     * Tests adding two sockets and writing on one script with an activescript
     */
    @Test
    public void test_doubleWrite() {
        scriptSocket.join(socket_in_p1, socket_out_p1);
        scriptSocket.join(socket_in_p2, socket_out_p2);

        ActiveScript activeScript = activateScript();
        activeScript.actionIndex++; // IMPORTANT: don't save!
        scriptSocket.processInput(socket_in_p1, Json.toJson(activeScript));

        verify(socket_out_p1, times(1)).write(Json.toJson(activeScript));
        verify(socket_out_p2, times(1)).write(Json.toJson(activeScript));
    }

    /**
     * Tests adding two sockets while activeScript is activated for one
     */
    @Test
    public void test_joinActiveScript() {
        scriptSocket.join(socket_in_p1, socket_out_p1);

        ActiveScript activeScript = activateScript();

        scriptSocket.join(socket_in_p2, socket_out_p2);

        verify(socket_out_p1, times(1)).write(Json.toJson(new ArrayList<>()));
        verify(socket_out_p2, times(0)).write(Json.toJson(new ArrayList<>())); // 2 never sees empty list

        verify(socket_out_p1, times(1)).write(Json.toJson(activeScript));
        verify(socket_out_p2, times(1)).write(Json.toJson(activeScript));
    }

    /**
     * Tests adding two sockets and writing both sockets back forth
     */
    @Test
    public void test_writeOneTwo_synchronous() {
        scriptSocket.join(socket_in_p1, socket_out_p1);
        scriptSocket.join(socket_in_p2, socket_out_p2);

        ActiveScript activeScript = activateScript();
        activeScript.actionIndex++; // IMPORTANT: don't save!
        scriptSocket.processInput(socket_in_p1, Json.toJson(activeScript));
        activeScript.actionIndex--;
        scriptSocket.processInput(socket_in_p2, Json.toJson(activeScript));

        verify(socket_out_p1, times(1)).write(Json.toJson(activeScript));
        verify(socket_out_p2, times(1)).write(Json.toJson(activeScript));

        // and the old version
        activeScript.actionIndex++;
        verify(socket_out_p1, times(1)).write(Json.toJson(activeScript));
        verify(socket_out_p2, times(1)).write(Json.toJson(activeScript));
    }

    /**
     * Tests activating script.
     */
    @Test
    public void test_singleActivateScript() {
        scriptSocket.join(socket_in_p1, socket_out_p1); // add the mocks

        Map<String, Object> maps = new HashMap<>();
        maps.put("start", 1);

        JsonNode node = Json.toJson(maps);
        scriptSocket.processInput(socket_in_p1, node);

        verify(socket_out_p1, times(2)).write(any(JsonNode.class)); // retrieve empty array for no active script
    }

    /**
     * Tests activating script.
     */
    @Test
    public void test_singleStopScript() {
        scriptSocket.join(socket_in_p1, socket_out_p1); // add the mocks

        ActiveScript activeScript = activateScript();

        Map<String, Object> maps = new HashMap<>();
        maps.put("stop", activeScript.id);

        JsonNode node = Json.toJson(maps);
        scriptSocket.processInput(socket_in_p1, node);

        verify(socket_out_p1, times(2)).write(Json.toJson(new ArrayList<>())); // retrieve empty array for no active script
    }

    private ActiveScript activateScript() {
        Script s = Script.find.byId(1L);
        ActiveScript as = new ActiveScript();
        as.actionIndex = 0;
        as.runningTime = new Date().getTime();
        as.script = s;
        as.save();
        return as;
    }


}
