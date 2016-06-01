package util.camera.commands;

import org.junit.Before;
import org.junit.Test;
import util.camera.LiveCamera;

import static org.junit.Assert.assertNull;

/**
 * Pan tilt command.
 */
public class SnapshotCommandTest extends CameraCommandTest {

    @Before
    public void setUp() throws Exception {
        command = new SnapshotCommand();
    }

    @Test
    public void testGet() {
        assertNull(((SnapshotCommand)command).get(new LiveCamera("0.0.0.0"), SnapshotCommand.RES_1280));
    }
}
