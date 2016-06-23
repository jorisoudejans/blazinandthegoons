package util.socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.ActiveScript;
import models.Camera;
import models.Preset;
import play.api.libs.json.Json;

import java.util.Collections;
import java.util.List;

/**
 * Saves running script state and communicates to all.
 */
public class StateActor extends SocketActor {

    private static final int IDLETIME = 1000;

    @Override
    public boolean canAct(JsonNode jsonNode) {
        return jsonNode.has("id");
    }

    @Override
    public void act(JsonNode jsonNode) {
        List<ActiveScript> aslist = ActiveScript.find.all();
        if (!aslist.isEmpty()) { // we have an active script
            ActiveScript as = new ObjectMapper().convertValue(jsonNode, ActiveScript.class);
            if (as.actionIndex < 0)
                as.actionIndex = 0;
            as.update();

            if (as.actionIndex > 0) {
                Camera prevCam = as.script.actions.get(as.actionIndex - 1).preset.camera;
                if (prevCam != null) {
                    prevCam.deactTime = System.currentTimeMillis();
                    prevCam.save();
                }
            }

            try {
                setupPreset(as);
            } catch (Exception e) {
                System.out.println("E " + e.getLocalizedMessage());
            }
        }
    }

    /**
     * This method will make sure the cameras will be set to the correct preset in time.
     * @param as The active script
     */
    private void setupPreset(ActiveScript as) {
        if (as.script.actions.size() <= as.actionIndex + 1) {
            return;
        }
        Preset next = as.script.actions.get(as.actionIndex + 1).preset;
        System.out.println("Applying: " + next.name + " " + next.pan);
        if (next != null && next.camera != null) {
            /*if (Math.abs(next.camera.deactTime - System.currentTimeMillis()) < IDLETIME) {
                try {
                    Thread.sleep(IDLETIME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
            next.apply();
        }
    }

}
