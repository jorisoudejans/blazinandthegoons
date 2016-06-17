package util.camera.commands;

import models.Camera;

/**
 * Change camera's pan tilt values.
 */
public class PanTiltCommand extends CameraCommand {

    private int tilt;
    private int pan;

    /**
     * Create a new command.
     * @param tilt tilt value
     * @param pan pan value
     */
    public PanTiltCommand(int tilt, int pan) {
        this.tilt = tilt;
        this.pan = pan;
    }

    @Override
    protected String getCommand() {
        return "APC";
    }

    @Override
    protected String getParameters() {
        return toHex(pan, 4) +
                toHex(tilt, 4);
    }

    @Override
    public Integer[] get(Camera camera) {
        String result = super.getValues(camera);
        if (result != null) {
            String panS = result.substring(0, 4);
            String tiltS = result.substring(4, 8);
            return new Integer[]{ Integer.parseInt(panS, 16), Integer.parseInt(tiltS, 16) };
        }
        return null;
    }

}
