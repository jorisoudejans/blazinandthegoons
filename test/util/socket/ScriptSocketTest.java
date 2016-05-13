package util.socket;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import play.Application;
import play.mvc.WebSocket;
import play.test.Helpers;

import java.util.function.Consumer;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static play.test.Helpers.fakeApplication;

/**
 * Class to test ScriptSocket class
 * Created by hidde on 5/12/16.
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

/*
    public void test_outputBoth() {
        scriptSocket.join(socket_in_p1, socket_out_p1);
        scriptSocket.join(socket_in_p2, socket_out_p2);



    }
*/
}
