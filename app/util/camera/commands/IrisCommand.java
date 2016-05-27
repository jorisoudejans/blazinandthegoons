package util.camera.commands;

/**
 * Change camera's pan tilt values.
 */
public class IrisCommand extends CameraCommand {

    private int iris;

    /**
     * Create a new command.
     * @param iris the zoom value
     */
    public IrisCommand(int iris) {
        this.iris = iris;
    }

    @Override
    protected String getCommand() {
        return "AXI";
    }

    @Override
    protected String getParameters() {
        return addZerosHex(Integer.toHexString(iris).toUpperCase(), 3);
    }


}
