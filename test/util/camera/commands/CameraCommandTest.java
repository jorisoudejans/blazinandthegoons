package util.camera.commands;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the camera commands
 */
public class CameraCommandTest {

    @Test
    public void testAddZerosHex() {
        assertEquals("000F95", CameraCommand.addZerosHex("F95", 6));
    }

}
