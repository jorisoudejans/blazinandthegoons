package util.camera.commands;

import models.Camera;
import org.junit.Test;

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
    public void testGetGetCommand() {
        assertNotNull(getCommand().getGetCommand());
    }

    @Test
    public void testExecute() {
        assertFalse(getCommand().execute(Camera.make("Boilerplate", "null"))); // should fail because of IP
    }

    @Test
    public void testGet() {
        assertNull(getCommand().get(Camera.make("Boilerplate", "null"))); // should fail because of IP
    }
}
