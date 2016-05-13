package util.camera;

/**
 * This class will represent a live camera, a camera that will be able
 * to be controlled by the api.
 */
public class LiveCamera {

    private String ip;

    /**
     * constructor for LiveCamera.
     * @param ip    ip-address of the camera
     */
    public LiveCamera(String ip) {
        this.ip = ip;
    }

    /**
     * Getter for the ip-address.
     * @return the ip-address
     */
    public String getIp() {
        return this.ip;
    }
}
