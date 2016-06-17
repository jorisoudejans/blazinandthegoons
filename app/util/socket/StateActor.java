package util.socket;

import com.fasterxml.jackson.databind.JsonNode;
import models.ActiveScript;

import java.util.Collections;
import java.util.List;

/**
 * Saves running script state and communicates to all.
 */
public class StateActor extends SocketActor {

    @Override
    public boolean canAct(JsonNode jsonNode) {
        return true;
    }

    @Override
    public void act(JsonNode jsonNode) {
        List<ActiveScript> aslist = ActiveScript.find.all();
        if (!aslist.isEmpty()) { // we have an active script
            ActiveScript as = aslist.get(0);
            as.actionIndex = jsonNode.findPath("actionIndex").asInt();
            as.save(); // save it
        }
    }

}
