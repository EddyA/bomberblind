import images.ImagesLoader;

import java.awt.*;

public class Bomb implements Runnable {

    /**
     * enum the different available status of the bomb.
     */
    public enum STATUS {
        STATUS_ALIVE,
        STATUS_EXPLOSE
    }

    private STATUS status; // bomber status.

    private Point pointOnMap; // position on the map.
    private Point pointOnScreen; // position on the screen.
    private Image curImage; // bomber image.

    private int bombTimer; // time before a bomb should explose.
    private int flameSize; // flame size.

    private Thread t; // thread to manage the bomber animation.
    private int frameTimer; // time between each frame.

    public Bomb(int x, int y, int bombTimer, int flameSize) {

        this.status = STATUS.STATUS_ALIVE;
        this.pointOnMap = new Point(x, y);
        this.pointOnScreen = new Point(x, y);
        this.bombTimer = bombTimer;
        this.flameSize = flameSize;
        this.frameTimer = 100;
        t = new Thread(this);
        t.start();
    }

    /**
     * Set the bomb status.
     *
     * @param status new status of the bomb
     */
    public void setStatus(STATUS status) {
        this.status = status;
    }

    /**
     * Set the bomb location on the map.
     *
     * @param x abscissa of bomb on the map
     * @param y ordinate of bomb on the map
     */
    public void setPointOnMap(double x, double y) {
        pointOnMap.setLocation(x, y);
    }

    /**
     * Set the bomb location on the screen.
     *
     * @param x abscissa of bomb on the screen
     * @param y ordinate of bomb on the screen
     */
    public void setPointOnScreen(double x, double y) {
        pointOnScreen.setLocation(x, y);
    }

    /**
     * Paint the bomb on the map.
     *
     * @param g the graphics context
     */
    public void paintBuffer(Graphics g) {
        g.drawImage(curImage, (int) this.pointOnScreen.getX(),
                (int) this.pointOnScreen.getY() + ImagesLoader.IMAGE_SIZE, null);
    }

    /**
     * run the thread allowing updating the bomber image according to its status.
     */
    public void run() {
        int frameIdx = 0;
        boolean flag = true;
        while (flag) {
            switch (this.status) {
                case STATUS_ALIVE: {
                    if (frameIdx == ImagesLoader.NB_BOMB_FRAME) {
                        frameIdx = 0;
                    }
                    curImage = ImagesLoader.imagesMatrix[ImagesLoader.bombMatrixRowIdx][frameIdx];
                    try {
                        Thread.sleep(this.frameTimer);
                    } catch (InterruptedException ie) {
                        System.err.print("thread of a bomb has been interrupted.\n" + ie.getMessage());
                    }
                    frameIdx++;
                    break;
                }
                case STATUS_EXPLOSE: {
                    flag = false;
                    break;
                }
            }
        }
    }
}
