package util.socket;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Can perform an action based on a Socket action.
 */
public abstract class SocketActor {

    /**
     * Whether this actor can act based on the data.
     * @param jsonNode incoming data
     * @return whether we can act
     */
    public boolean canAct(JsonNode jsonNode) {
        return false;
    }

    /**
     * Act on a socket action.
     * @param jsonNode incoming data
     */
    public abstract void act(JsonNode jsonNode);

}
