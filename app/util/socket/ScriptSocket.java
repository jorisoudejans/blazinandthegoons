package util.socket;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import models.ActiveScript;
import play.libs.Json;
import play.mvc.WebSocket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * WebSocket (ws) for streaming script data.
 */
public class ScriptSocket {

    private static ScriptSocket singleton;

    private List<WebSocket.Out<JsonNode>> subscribers;

    /**
     * Singleton.
     * @return singleton instance
     */
    public static ScriptSocket getActive() {
        if (singleton == null) {
            singleton = new ScriptSocket();
            singleton.subscribers = new ArrayList<>();
        }
        return singleton;
    }

    /**
     * Actor prop for this class.
     * @param out the reference to bind to
     * @return new prop for streaming script sockets
     */
    public static Props props(ActorRef out) {
        return Props.create(ScriptSocket.class, out);
    }

    /**
     * creates a new scriptsocket.
     */
    public ScriptSocket() { }

    /**
     * Join a websocket.
     * @param in incoming stream
     * @param out outgoing stream
     */
    public void join(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {
        makeAScript();

        subscribers.add(out);

        in.onMessage(jsonNode -> {
            // do nothing yet
            // just return the active script
            System.out.println("This socket: " + jsonNode.toString());
            System.out.println("Whats oing on " + jsonNode.findPath("actionIndex").asInt());

            ActiveScript as = ActiveScript.find.all().get(0);

            if (as != null) {
                as.actionIndex = jsonNode.findPath("actionIndex").asInt();
                //as.save();
            }

            write(as);
        });



        ActiveScript as = ActiveScript.find.all().get(0);
        write(as);
    }

    private void makeAScript() {
        if (ActiveScript.find.all().isEmpty()) {
            ActiveScript as = new ActiveScript();
            as.actionIndex = 0;
            as.runningTime = new Date().getTime();
            as.script = models.Script.find.byId(1L);
            as.save();
        }
    }

    private void write(ActiveScript script) {
        JsonNode v = Json.toJson(script);
        write(v);
    }

    private void write(JsonNode n) {
        for (WebSocket.Out<JsonNode> j : subscribers) {
            j.write(n);
        }
    }

}