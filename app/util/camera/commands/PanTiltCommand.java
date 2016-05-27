package util.camera.commands;

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
        return addZerosHex(Integer.toHexString(pan).toUpperCase(), 4) +
                addZerosHex(Integer.toHexString(tilt).toUpperCase(), 4);
    }


}
