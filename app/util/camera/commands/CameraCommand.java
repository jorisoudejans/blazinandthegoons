package util.camera.commands;

import util.camera.LiveCamera;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletionStage;

/**
 * Abstract implementation of a command to send to a camera.
 */
public abstract class CameraCommand {

    /**
     * The command code to execute
     * @return string with code
     */
    protected abstract String getCommand();

    /**
     * Parameters to include in the call.
     * @return stringified parameters
     */
    protected abstract String getParameters();

    /**
     * Executes this command on the camera.
     * @param camera Camera to apply it to
     * @return whether the command caused any errors
     */
    public boolean execute(LiveCamera camera) {
        String url = "http://" + camera.getIp() + "/cgi-bin/aw_ptz?cmd=%23" + this.getCommand() + this.getParameters() + "&res=1";
        System.out.println("Doing url: " + url);
        try {
            BufferedReader br = getHttp(url);
            String msg = br.readLine();
            return msg.toUpperCase().startsWith(this.getCommand());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method for getting the sending and getting the http response.
     * @param urlString The url of the HTTP GET
     * @return BufferedReader of the response
     */
    private BufferedReader getHttp(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return new BufferedReader(new InputStreamReader(conn.getInputStream()));
    }

    /**
     * If a message requires a length of eg. 4, but the actual hex is
     * only 2 characters long, we need to add the additional zeros ourselves.
     * @param msg   The hex message
     * @param length    The required length
     * @return  The message with the required length
     */
    public static String addZerosHex(String msg, int length) {
        if (msg.length() >= length) {
            return msg;
        }
        return addZerosHex("0" + msg, length);
    }

}
