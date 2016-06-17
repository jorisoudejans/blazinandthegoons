package util.camera.commands;

import models.Camera;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Abstract implementation of a command to send to a camera.
 */
public abstract class CameraCommand {

    /**
     * The command code to execute.
     * @return string with code
     */
    protected abstract String getCommand();

    /**
     * Parameters to include in the call.
     * @return stringified parameters
     */
    protected abstract String getParameters();

    /**
     * Get the values for this command
     * @param camera the camera
     * @return values
     */
    public abstract Object get(Camera camera);

    /**
     * The command to retrieve the values
     * @return the get values
     */
    protected String getGetCommand() {
        return getCommand();
    }

    /**
     * Executes this command on the camera.
     * @param camera Camera to apply it to
     * @return whether the command caused any errors
     */
    public boolean execute(Camera camera) {
        String url = "http://" + camera.getIp() + "/cgi-bin/aw_ptz?cmd=%23"
                + this.getCommand()
                + this.getParameters()
                + "&res=1";
        try {
            BufferedReader br = getHttp(url);
            String msg = br.readLine();
            return msg.toUpperCase().startsWith(this.getCommand());
        } catch (IOException e) {
            System.out.print(
                    "An error occurred while executing command on camera "
                            + camera.getIp() + ": "
                            + e.getMessage());
        }
        return false;
    }

    /**
     * Get current values of this command
     * @param camera Camera to apply it to
     * @return the values
     */
    protected String getValues(Camera camera) {
        String url = "http://" + camera.getIp() + "/cgi-bin/aw_ptz?cmd=%23"
                + this.getGetCommand()
                + "&res=1";
        try {
            BufferedReader br = getHttp(url);
            String msg = br.readLine();
            if (msg.toUpperCase().startsWith(this.getCommand())) {
                return msg.toUpperCase().substring(this.getCommand().length());
            }
            return null;
        } catch (IOException e) {
            System.out.print(
                    "An error occurred while executing command on camera "
                            + camera.getIp() + ": "
                            + e.getMessage());
        }
        return null;
    }

    /**
     * Method for getting the sending and getting the http response.
     * @param urlString The url of the HTTP GET
     * @return BufferedReader of the response
     * @throws IOException exception that can occur for invalid url
     */
    private BufferedReader getHttp(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );
    }

    /**
     * Convert an int to its hex representation for the camera.
     * @param value the value
     * @param minLength the minimum hex length (to pad with zeros)
     * @return hex string
     */
    protected String toHex(int value, int minLength) {
        return pad(Integer.toHexString(value).toUpperCase(), minLength);
    }

    /**
     * If a message requires a length of eg. 4, but the actual hex is
     * only 2 characters long, we need to add the additional zeros ourselves.
     * @param msg   The hex message
     * @param minLength    The required length
     * @return  The message with the required length
     */
    private String pad(String msg, int minLength) {
        if (msg.length() >= minLength) {
            return msg;
        }
        return pad("0" + msg, minLength);
    }

}
