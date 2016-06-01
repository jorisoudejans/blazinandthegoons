package util.camera.commands;

import org.junit.Before;

/**
 * Pan tilt command.
 */
public class FocusCommandTest extends CameraCommandTest {

    @Before
    public void setUp() throws Exception {
        command = new FocusCommand(10);
    }

}
