package util.camera.commands;

import org.junit.Before;

/**
 * Pan tilt command.
 */
public class ZoomCommandTest extends CameraCommandTest {

    @Before
    public void setUp() throws Exception {
        command = new ZoomCommand(10);
    }

}
