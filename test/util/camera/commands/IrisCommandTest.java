package util.camera.commands;

import org.junit.Before;

/**
 * Pan tilt command.
 */
public class IrisCommandTest extends CameraCommandTest {

    @Before
    public void setUp() throws Exception {
        command = new IrisCommand(10);
    }

}
