import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import images.ImagesLoader;

public class App extends JFrame {

    // define the screen height in term of number of elements of "ImagesLoader.IMAGE_SIZE" px.
    @SuppressWarnings("FieldCanBeLocal") private final int SCREEN_HEIGHT_NB_ELTS = 24;

    private App(GraphicsDevice graphicsDevice) {
        super(graphicsDevice.getDefaultConfiguration());

        // compute screen size.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenRatio = screenSize.getWidth() / screenSize.getHeight();
        int heightScreen = SCREEN_HEIGHT_NB_ELTS * ImagesLoader.IMAGE_SIZE;
        int widthScreen = (int) (SCREEN_HEIGHT_NB_ELTS * screenRatio + 1) * ImagesLoader.IMAGE_SIZE;

        try {
            System.out.println("- load images ... ");
            ImagesLoader.fillImagesMatrix();

            System.out.println("- create and set JPanel ... ");
            GameJpanel gameJpanel = new GameJpanel(widthScreen, heightScreen);
            setContentPane(gameJpanel);

            System.out.println("- set screen mode and run.");

            DisplayMode displayModes[] = graphicsDevice.getDisplayModes();

            DisplayMode displayMode = new DisplayMode(800, 600, 8, 60);



            graphicsDevice.setDisplayMode(displayModes[0]);
            if (graphicsDevice.isFullScreenSupported()) {
                setUndecorated(true);
                setAlwaysOnTop(true);
                graphicsDevice.setFullScreenWindow(this);
            } else {
                setSize(widthScreen, heightScreen);
            }

            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setResizable(false);
            setVisible(true);
        } catch (Exception e) {
            System.err.println("App: " + e);
        }
    }

    public static void main(String[] args) {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        new App(graphicsEnvironment.getDefaultScreenDevice());
    }
}
