package util.camera;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * Api which will control the real cameras with http commands.
 */
public class CameraApi {

    /**
     * This method will return the absolute pan and tilt position respectively.
     * @param cam The camera from which the values will be read.
     * @throws Exception When the response is not the pan/tilt or getHttp throws one.
     *          Buffered reader can also throw an exception
     * @return  array with pan at index 0 and tilt at index 1
     */
    public static int[] getPanTilt(LiveCamera cam) throws  Exception {
        String url = "http://" + cam.getIp() + "/cgi-bin/aw_ptz?cmd=%23APC&res=1";
        BufferedReader br = getHttp(url);
        String msg = br.readLine();
        String tag = msg.substring(0, 3);
        if (tag.equals("aPC")) {
            int pan = Integer.parseInt(msg.substring(3, 7));
            int tilt = Integer.parseInt(msg.substring(7, 11), 16);
            return new int[]{pan, tilt};
        } else {
            //TODO: Catch possible error message
            throw new Exception("error");
        }
    }

    /**
     * This method will set the absolute pan and tilt position.
     * @param cam The camera which will be changed.
     * @param pan The pan position
     * @param tilt the tilt position
     * @throws Exception When the response is not the pan/tilt ok.
     *          Buffered reader can also throw an exception
     * @return  array with pan at index 0 and tilt at index 1,
     *             this should return the same values as the input
     */
    public static int[] setPanTilt(LiveCamera cam, int pan, int tilt) throws  Exception {

        String url = "http://" + cam.getIp() + "/cgi-bin/aw_ptz?cmd=%23APC"
                + Integer.toHexString(pan) + Integer.toHexString(tilt) + "&res=1";
        BufferedReader br = getHttp(url);
        String msg = br.readLine();
        String tag = msg.substring(0, 3);
        if (tag.equals("aPC")) {
            int panReturn = Integer.parseInt(msg.substring(3, 7));
            int tiltReturn = Integer.parseInt(msg.substring(7, 11), 16);
            return new int[]{panReturn, tiltReturn};
        } else {
            //TODO: Catch possible error message
            throw new Exception("error");
        }
    }

    /**
     * This method will return the zoom position of the camera.
     * @param cam The camera from which the values will be read.
     * @throws Exception When the response is not the pan/tilt or getHttp throws one.
     *          Buffered reader can also throw an exception
     * @return  the zoom position
     */
    public static int getZoom(LiveCamera cam) throws Exception {
        String url = "http://" + cam.getIp() + "/cgi-bin/aw_ptz?cmd=%23GZ&res=1";
        BufferedReader br = getHttp(url);
        String msg = br.readLine();
        String tag = msg.substring(0, 2);
        if (tag.equals("gz")) {
            return Integer.parseInt(msg.substring(2, 5), 16);
        } else {
            //TODO: Catch possible error message
            throw new Exception("error");
        }
    }

    /**
     * This method will return the zoom position of the camera.
     * @param cam The camera from which the values will be read.
     * @throws Exception When the response is not the pan/tilt or getHttp throws one.
     *          Buffered reader can also throw an exception
     * @return  the zoom position
     */
    public static int getFocus(LiveCamera cam) throws Exception {
        String url = "http://" + cam.getIp() + "/cgi-bin/aw_ptz?cmd=%23GF&res=1";
        BufferedReader br = getHttp(url);
        String msg = br.readLine();
        String tag = msg.substring(0, 2);
        if (tag.equals("gf")) {
            return Integer.parseInt(msg.substring(2, 5), 16);
        } else {
            //TODO: Catch possible error message
            throw new Exception("error");
        }
    }



    /**
     * Method for getting the sending and getting the http response.
     * @param urlString The url of the HTTP GET
     * @return BufferedReader of the response
     */
    private static BufferedReader getHttp(String urlString) throws Exception {
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            return new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        throw new Exception("couldn't connect");
    }
}
