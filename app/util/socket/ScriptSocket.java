package util.socket;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import models.ActiveScript;
import models.Script;
import play.libs.Json;
import play.mvc.WebSocket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * WebSocket (ws) for streaming script data.
 */
public final class ScriptSocket {

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
    private ScriptSocket() { }

    /**
     * Join a websocket.
     * @param in incoming stream
     * @param out outgoing stream
     */
    public void join(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {
        subscribers.add(out);

        in.onMessage(jsonNode -> {
            // do nothing yet
            // just return the active script
            System.out.println("This socket: " + jsonNode.toString());

            processInput(jsonNode);
        });

        List<ActiveScript> as = ActiveScript.find.all();
        if (!as.isEmpty()) {
            write(as.get(0));
        } else {
            write(Json.toJson(new ArrayList<>()));
        }
    }

    /**
     * Processes possible inputs from a client.
     * @param jsonNode the input data decoded in a JSON object
     */
    private void processInput(JsonNode jsonNode) {
        if (jsonNode.has("start")) {
            // start the script
            Long scriptId = jsonNode.findPath("start").asLong();
            Script toStart = Script.find.byId(scriptId);
            if (toStart != null) {
                ActiveScript as = new ActiveScript();
                as.actionIndex = 0;
                as.runningTime = new Date().getTime();
                as.script = models.Script.find.byId(toStart.id);
                as.save();
            }
        } else if (jsonNode.has("stop")) {
            List<ActiveScript> aslist = ActiveScript.find.all();
            if (!aslist.isEmpty()) { // we have an active script
                ActiveScript as = aslist.get(0);
                as.delete();
            }
        }

        List<ActiveScript> aslist = ActiveScript.find.all();
        if (!aslist.isEmpty()) { // we have an active script
            ActiveScript as = aslist.get(0);
            as.actionIndex = jsonNode.findPath("actionIndex").asInt();
            //as.save();
            write(as);
        } else {
            write(Json.toJson(new ArrayList<>()));
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