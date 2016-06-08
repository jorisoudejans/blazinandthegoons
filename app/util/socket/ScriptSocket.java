package util.socket;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import models.ActiveScript;
import models.Script;
import play.libs.Json;
import play.mvc.WebSocket;

import java.net.Socket;
import java.util.*;

/**
 * WebSocket (ws) for streaming script data.
 */
public final class ScriptSocket {

    private static ScriptSocket singleton;

    private List<WebSocket.Out<JsonNode>> subscribers;

    private List<SocketActor> actors;

    /**
     * Singleton.
     * @return singleton instance
     */
    public static ScriptSocket getActive() {
        if (singleton == null) {
            singleton = new ScriptSocket();
            singleton.subscribers = new ArrayList<>();
            singleton.actors = Arrays.asList(new StartActor(), new StopActor(), new StateActor()); // add the actors
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

        in.onMessage(jsonNode -> processInput(in, jsonNode));

        List<ActiveScript> as = ActiveScript.find.all();
        if (!as.isEmpty()) {
            Collections.sort(as.get(0).script.actions);
            write(as.get(0));
        } else {
            write(Json.toJson(new ArrayList<>()));
        }
    }

    /**
     * Processes inputs from any client.
     * @param inputSocket the socket that sent this data
     * @param jsonNode the input data decoded in a JSON object
     */
    void processInput(WebSocket.In<JsonNode> inputSocket, JsonNode jsonNode) {
        for (SocketActor socketActor : actors) {
            if (socketActor.canAct(jsonNode)) {
                socketActor.act(jsonNode);
            }
        }

        // Update new state to all clients
        List<ActiveScript> aslist = ActiveScript.find.all();
        if (!aslist.isEmpty()) { // we have an active script
            Collections.sort(aslist.get(0).script.actions);
            ActiveScript as = aslist.get(0);
            write(as);
        } else {
            write(Json.toJson(new ArrayList<>()));
        }
    }

    private void write(ActiveScript script) {
        Collections.sort(script.script.actions);
        JsonNode v = Json.toJson(script);
        write(v);
    }

    private void write(JsonNode n) {
        for (WebSocket.Out<JsonNode> j : subscribers) {
            j.write(n);
        }
    }

}