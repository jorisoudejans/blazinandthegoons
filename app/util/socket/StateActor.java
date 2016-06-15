package util.socket;

import com.fasterxml.jackson.databind.JsonNode;
import models.ActiveScript;
import models.Camera;
import models.Preset;

import java.util.Collections;
import java.util.List;

/**
 * Saves running script state and communicates to all.
 */
public class StateActor extends SocketActor {

    private static final int IDLETIME = 1000;

    @Override
    public boolean canAct(JsonNode jsonNode) {
        return true;
    }

    @Override
    public void act(JsonNode jsonNode) {
        List<ActiveScript> aslist = ActiveScript.find.all();
        if (!aslist.isEmpty()) { // we have an active script
            Collections.sort(aslist.get(0).script.actions);
            ActiveScript as = aslist.get(0);
            as.actionIndex = jsonNode.findPath("actionIndex").asInt();
            as.save(); // save it

            Camera prevCam = as.script.actions.get(as.actionIndex - 1).preset.camera;
            prevCam.deactTime = System.currentTimeMillis();
            prevCam.save();

            setupPreset(as);
        }
    }

    /**
     * This method will make sure the cameras will be set to the correct preset in time.
     * @param as The active script
     */
    public void setupPreset(ActiveScript as) {
        Preset next = as.script.actions.get(as.actionIndex + 1).preset;
        if (Math.abs(next.camera.deactTime - System.currentTimeMillis()) < IDLETIME) {
            try {
                Thread.sleep(IDLETIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        next.apply();
    }

}
