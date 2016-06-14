package util.socket;

import com.fasterxml.jackson.databind.JsonNode;
import models.ActiveScript;
import models.Script;

import java.util.Collections;
import java.util.Date;

/**
 * Starts a script.
 */
public class StartActor extends SocketActor {

    @Override
    public boolean canAct(JsonNode jsonNode) {
        return jsonNode.has("start");
    }

    @Override
    public void act(JsonNode jsonNode) {
        Long scriptId = jsonNode.findPath("start").asLong();
        Script toStart = Script.find.byId(scriptId); // find the script
        if (toStart != null) {
            Collections.sort(toStart.actions);
            ActiveScript as = new ActiveScript(); // create an activescript object
            as.actionIndex = 0;
            as.runningTime = new Date().getTime();
            as.script = toStart;
            as.save();
        }
    }

}
