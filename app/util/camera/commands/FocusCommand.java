package util.camera.commands;

import models.Camera;

/**
 * Change camera's pan tilt values.
 */
public class FocusCommand extends CameraCommand {

    private int focus;

    /**
     * Create a new command.
     * @param focus the focus value
     */
    public FocusCommand(int focus) {
        this.focus = focus;
    }

    @Override
    protected String getCommand() {
        return "AXF";
    }

    @Override
    protected String getGetCommand() {
        return "GF";
    }

    @Override
    protected String getParameters() {
        return toHex(focus, 3);
    }

    @Override
    public Integer get(Camera camera) {
        String result = super.getValues(camera);
        if (result != null) {
            String panS = result.substring(0, 3);
            return Integer.parseInt(panS, 16);
        }
        return null;
    }

}
