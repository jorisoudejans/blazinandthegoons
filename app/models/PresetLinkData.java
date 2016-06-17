package models;

/**
 * Represents linking data for linking presets.
 */
public class PresetLinkData {
    private int pan;
    private int tilt;
    private int zoom;
    private int focus;
    private int iris;

    /**
     * Create a new link
     * @param pan camera value
     * @param tilt camera value
     * @param zoom camera value
     * @param focus camera value
     * @param iris camera value
     */
    public PresetLinkData(int pan, int tilt, int zoom, int focus, int iris) {
        this.pan = pan;
        this.tilt = tilt;
        this.zoom = zoom;
        this.focus = focus;
        this.iris = iris;
    }

    /**
     * Get pan preset value
     * @return preset value
     */
    public int getPan() {
        return pan;
    }

    /**
     * Get tilt preset value
     * @return preset value
     */
    public int getTilt() {
        return tilt;
    }

    /**
     * Get zoom preset value
     * @return preset value
     */
    public int getZoom() {
        return zoom;
    }

    /**
     * Get focus preset value
     * @return preset value
     */
    public int getFocus() {
        return focus;
    }

    /**
     * Get iris preset value
     * @return preset value
     */
    public int getIris() {
        return iris;
    }
}