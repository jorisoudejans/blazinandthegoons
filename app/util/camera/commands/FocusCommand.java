package util.camera.commands;

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
        return "GF";
    }

    @Override
    protected String getParameters() {
        return addZerosHex(Integer.toHexString(focus).toUpperCase(), 3);
    }


}
