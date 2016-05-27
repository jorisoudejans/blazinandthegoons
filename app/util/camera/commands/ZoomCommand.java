package util.camera.commands;

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
        return "GZ";
    }

    @Override
    protected String getParameters() {
        return addZerosHex(Integer.toHexString(zoom).toUpperCase(), 3);
    }


}
