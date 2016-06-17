package util.camera.commands;

import models.Camera;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Retrieves a snapshot of a camera.
 */
public class SnapshotCommand extends CameraCommand {

    @Override
    protected String getCommand() {
        return "";
    }

    @Override
    protected String getParameters() {
        return "";
    }

    @Override
    public Object get(Camera camera) {
        return null; // can't retrieve anything
    }

    public static final int RES_480 = 480;
    public static final int RES_720 = 720;
    public static final int RES_1280 = 1280;

    /**
     * Get the snapshot image.
     * @param camera Device to get the image from
     * @param res wanted resolution const, see: RES_*
     * @return the image or null on failure
     */
    public BufferedImage get(Camera camera, int res) {
        String urlString = "http://" + camera.getIp() + "/cgi-bin/camera?resolution=" + res;
        try {
            URL url = new URL(urlString);
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
