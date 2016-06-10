package util.socket;

import com.fasterxml.jackson.databind.JsonNode;
import models.ActiveScript;

import java.util.Collections;
import java.util.List;

/**
 * Stops a script.
 */
public class StopActor extends SocketActor {

    @Override
    public boolean canAct(JsonNode jsonNode) {
        return jsonNode.has("stop");
    }

    @Override
    public void act(JsonNode jsonNode) {
        List<ActiveScript> aslist = ActiveScript.find.all();
        if (!aslist.isEmpty()) { // we have an active script
            Collections.sort(aslist.get(0).script.actions);
            ActiveScript as = aslist.get(0);
            as.delete();
        }
    }

}
