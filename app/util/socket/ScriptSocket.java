package util.socket;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * WebSocket (ws) for streaming script data.
 */
public class ScriptSocket extends UntypedActor {

    /**
     * Actor prop for this class.
     * @param out the reference to bind to
     * @return new prop for streaming script sockets
     */
    public static Props props(ActorRef out) {
        return Props.create(ScriptSocket.class, out);
    }

    private final ActorRef out;

    /**
     * creates a new scriptsocket and binds the ref.
     * @param out actor reference
     */
    public ScriptSocket(ActorRef out) {
        this.out = out;
    }

    /**
     * When a message is received on the socket.
     * @param message the message
     * @throws Exception when an error occurs
     */
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            out.tell("I received your message: " + message, self()); // just a simple loopback
        }
    }
}