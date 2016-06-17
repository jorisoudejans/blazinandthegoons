package util.camera.commands;

import models.Camera;

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
    protected String getGetCommand() {
        return "GI";
    }

    @Override
    protected String getParameters() {
        return toHex(iris, 3);
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
