package util.camera.commands;

import models.Camera;

/**
 * Change camera's pan tilt values.
 */
public class ZoomCommand extends CameraCommand {

    private int zoom;

    /**
     * Create a new command.
     * @param zoom the zoom value
     */
    public ZoomCommand(int zoom) {
        this.zoom = zoom;
    }

    @Override
    protected String getCommand() {
        return "AXZ";
    }

    @Override
    protected String getGetCommand() {
        return "GZ";
    }

    @Override
    protected String getParameters() {
        return toHex(zoom, 3);
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
