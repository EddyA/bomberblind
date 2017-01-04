import images.ImagesLoader;
import utils.ScreenMode;
import utils.Tuple2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class App extends JFrame {

    private int screenWidth;
    private final static int DEFAULT_SCREEN_WIDTH = 1024;
    private int screenHeight;
    private final static int DEFAULT_SCREEN_HEIGHT = 768;

    private App(GraphicsDevice graphicsDevice) {
        super(graphicsDevice.getDefaultConfiguration());

        // compute the fullscreen resolution according to the screen format (4/3, 16/9, 16/10, etc.).
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Optional<Tuple2<Integer, Integer>> fullscreenResolution = ScreenMode
                .computeFullscreenResolution(screenSize.getWidth() / screenSize.getHeight());
        if (fullscreenResolution.isPresent()) {
            screenWidth = fullscreenResolution.get().getFirst();
            screenHeight = fullscreenResolution.get().getSecond();
        }

        try {
            System.out.println("- load images ... ");
            ImagesLoader.fillImagesMatrix();

            System.out.println("- set screen mode ...");
            this.setTitle("Bomberblind © Eddy ALBERT");
            this.setIconImage(ImageIO.read(App.class.getResource("/images/icon.gif")));
            if (!fullscreenResolution.isPresent() ||  // is the screen format supported by the software?
                    !ScreenMode.setFullscreenMode(graphicsDevice, // is the screen format supported by the hardware?
                            this,
                            screenWidth,
                            screenHeight)) {
                // if fullscreen is not supported, set the window mode.
                screenWidth = DEFAULT_SCREEN_WIDTH;
                screenHeight = DEFAULT_SCREEN_HEIGHT;
                ScreenMode.setWindowMode(this, screenWidth, screenHeight);
            }
            System.out.println("- create and set JPanel ...");
            GameJpanel gameJpanel = new GameJpanel(screenWidth, screenHeight);
            this.setContentPane(gameJpanel);

            System.out.println("- run.");
            this.setVisible(true);
            gameJpanel.requestFocusInWindow();
        } catch (Exception e) {
            System.err.println("App: " + e);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        new App(graphicsEnvironment.getDefaultScreenDevice());
    }
}