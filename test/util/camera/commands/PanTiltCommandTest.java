package util.camera.commands;

import org.junit.Before;

/**
 * Pan tilt command.
 */
public class PanTiltCommandTest extends CameraCommandTest {

    @Before
    public void setUp() throws Exception {
        command = new PanTiltCommand(10, 10);
    }

}
