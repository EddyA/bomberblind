import static java.awt.DisplayMode.BIT_DEPTH_MULTI;
import static java.awt.DisplayMode.REFRESH_RATE_UNKNOWN;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import images.ImagesLoader;
import utils.FullscreenResolution;
import utils.Tuple2;

public class App extends JFrame {

    private final static int DEFAULT_SCREEN_WIDTH = 1024;
    private final static int DEFAULT_SCREEN_HEIGHT = 768;

    private App(GraphicsDevice graphicsDevice) {
        super(graphicsDevice.getDefaultConfiguration());

        // compute the screen resolution according to the screen format (4/3, 16/9, 16/10, etc.).
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Optional<Tuple2<Integer, Integer>> fullscreenResolution = FullscreenResolution
                .compute(screenSize.getWidth() / screenSize.getHeight());
        int screenWidth = DEFAULT_SCREEN_WIDTH;
        int screenHeight = DEFAULT_SCREEN_HEIGHT;
        if (fullscreenResolution.isPresent()) {
            screenWidth = fullscreenResolution.get().getFirst();
            screenHeight = fullscreenResolution.get().getFirst();
        }

        try {
            System.out.println("- load images ... ");
            ImagesLoader.fillImagesMatrix();

            System.out.println("- set screen mode ...");
            boolean fullscreenNotAvailable = false;
            if (fullscreenResolution.isPresent()) { // is the screen format supported by the software?
                if (graphicsDevice.isFullScreenSupported()) { // is the fullscreen supported by the hardware?
                    setUndecorated(true);
                    setAlwaysOnTop(true);
                    graphicsDevice.setFullScreenWindow(this);
                    try {
                        graphicsDevice.setDisplayMode(
                                new DisplayMode(screenWidth, screenHeight, BIT_DEPTH_MULTI, REFRESH_RATE_UNKNOWN));
                    } catch (IllegalArgumentException e) {
                        fullscreenNotAvailable = true; // if the screen resolution is not supported by hardware.
                    }
                } else {
                    fullscreenNotAvailable = true; // if the full screen mode is not supported by hardware.
                }
            } else {
                fullscreenNotAvailable = true; // if the full screen mode is not supported by software.
            }

            if (fullscreenNotAvailable) {
                dispose(); // re-init the JFrame.
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
                setLocationRelativeTo(null); // align to center.
                setIconImage(ImageIO.read(App.class.getResource("/images/icon.gif")));
                setTitle("Bomberblind");
                setUndecorated(false);
                setAlwaysOnTop(false);
                setResizable(false);
            }

            System.out.println("- create and set JPanel ...");
            GameJpanel gameJpanel = new GameJpanel(1280, 1024);
            setContentPane(gameJpanel);

            System.out.println("- run.");
            setVisible(true);
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
