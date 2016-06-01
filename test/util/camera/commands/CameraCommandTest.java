package util.camera.commands;

import org.junit.Test;
import util.camera.LiveCamera;

import static org.junit.Assert.*;

/**
 * Tests the camera commands.
 */
public abstract class CameraCommandTest {

    protected CameraCommand command;

    public CameraCommand getCommand() {
        return command;
    }

    @Test
    public void testAddZerosHex() {
        assertEquals("000F95", getCommand().toHex(3989, 6));
    }

    @Test
    public void testGetCommandCode() {
        assertNotNull(getCommand().getCommand());
    }

    @Test
    public void testGetParameters() {
        assertNotNull(getCommand().getParameters());
    }

    @Test
    public void testExecute() {
        assertFalse(getCommand().execute(new LiveCamera("0.0.0.0"))); // should fail because of IP
    }
}
