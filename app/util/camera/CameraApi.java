package util.camera;


import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
                + Integer.toHexString(pan).toUpperCase()
                + Integer.toHexString(tilt).toUpperCase() + "&res=1";
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
     * This method will set the zoom position of the camera.
     * @param cam The camera which will be changed.
     * @param zoom The zoom value
     * @throws Exception When the response is not the pan/tilt or getHttp throws one.
     *          Buffered reader can also throw an exception
     * @return  the zoom position the camera returns
     *              This should return the same value as the input
     */
    public static int setZoom(LiveCamera cam, int zoom) throws Exception {
        String url = "http://" + cam.getIp() + "/cgi-bin/aw_ptz?cmd=%23GZ"
                + Integer.toHexString(zoom).toUpperCase() + "&res=1";
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
     * This method will return the focus position of the camera.
     * @param cam The camera from which the values will be read.
     * @throws Exception When the response is not the pan/tilt or getHttp throws one.
     *          Buffered reader can also throw an exception
     * @return  the focus value
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
     * This method will set the focus of the camera.
     * @param cam The camera which will be changed.
     * @param focus The focus value
     * @throws Exception When the response is not the pan/tilt or getHttp throws one.
     *          Buffered reader can also throw an exception
     * @return  the focus value the camera returns,
     *             this value should be the same as the input
     */
    public static int setFocus(LiveCamera cam, int focus) throws Exception {
        String url = "http://" + cam.getIp() + "/cgi-bin/aw_ptz?cmd=%23GF"
                + Integer.toHexString(focus).toUpperCase() + "&res=1";
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
     * This method will return the iris position of the camera.
     * @param cam   The camera of which will be read
     * @return  The iris position
     * @throws Exception When response is not the iris position or getHttp throws one.
     *          Buffered reader can also throw an exception
     */
    public static int getIris(LiveCamera cam) throws Exception {
        String url = "http://" + cam.getIp() + "/cgi-bin/aw_ptz?cmd=%23GI&res=1";
        BufferedReader br = getHttp(url);
        String msg = br.readLine();
        String tag = msg.substring(0, 2);
        if (tag.equals("gi")) {
            //There is also data about auto-iris being on or off
            return Integer.parseInt(msg.substring(2, 5), 16);
        } else {
            //TODO: Catch possible error message
            throw new Exception("error");
        }
    }

    /**
     * This method will set the iris position of the camera.
     * @param cam   The camera of which will be read
     * @param iris The iris position the camera will be set to
     * @return  The iris position the camera returns,
     *              this should be the same as the input.
     * @throws Exception When response is not the iris position or getHttp throws one.
     *          Buffered reader can also throw an exception
     */
    public static int setIris(LiveCamera cam, int iris) throws Exception {
        String url = "http://" + cam.getIp() + "/cgi-bin/aw_ptz?cmd=%23AXI"
                + Integer.toHexString(iris).toUpperCase() + "&res=1";
        BufferedReader br = getHttp(url);
        String msg = br.readLine();
        String tag = msg.substring(0, 3);
        if (tag.equals("axi")) {
            //There is also data about auto-iris being on or off
            return Integer.parseInt(msg.substring(3, 6), 16);
        } else {
            //TODO: Catch possible error message
            throw new Exception("error");
        }
    }

    /**
     * Requests a snapshot from the camera at the current time.
     * @param cam   Camera which takes the snapshot
     * @return     The image of the snapshot
     * @throws Exception    Exceptions could be thrown when retrieving jpeg
     */
    public static BufferedImage getJpegSnapshot(LiveCamera cam)throws Exception {
        String urlString = "http://" + cam.getIp() + "/cgi-bin/camera?resolution=1280";
        URL url = new URL(urlString);
        return ImageIO.read(url);
    }


    /**
     * Method for getting the sending and getting the http response.
     * @param urlString The url of the HTTP GET
     * @return BufferedReader of the response
     */
    private static BufferedReader getHttp(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return new BufferedReader(new InputStreamReader(conn.getInputStream()));
    }
}
