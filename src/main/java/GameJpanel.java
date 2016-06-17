import bbman.BbMan;
import bomb.Flame;
import exceptions.MapException;
import images.ImagesLoader;
import map.RMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import static images.ImagesLoader.IMAGE_SIZE;

public class GameJpanel extends JPanel implements Runnable, KeyListener {

    private final int MAP_WIDTH = 80;
    private final int MAP_HEIGHT = 32;

    private RMap rMap;
    private BbMan bbMan;
    private List<Long> listPressedKey;

    private int xBbManPosOnScreen;
    private int yBbManPosOnScreen;
    private int xMapStartPosOnScreen;
    private int yMapStartPosOnScreen;

    GameJpanel(int widthScreen, int heightScreen) throws MapException {
        rMap = new RMap(MAP_WIDTH, MAP_HEIGHT, widthScreen, heightScreen);

        // compute the position of the BbMan on the map.
        int xBbManOnMap = rMap.spCastleT1.getColIdx() * IMAGE_SIZE +
                (rMap.castleT1.getWidth() * IMAGE_SIZE / 2);
        int yBbManOnMap = rMap.spCastleT1.getRowIdx() * IMAGE_SIZE +
                (rMap.castleT1.getHeight() * IMAGE_SIZE) + (IMAGE_SIZE / 2); // to not stick the castle.
        bbMan = new BbMan(xBbManOnMap, yBbManOnMap, ImagesLoader.bbManSprites1);

        // create a list to handle pressed key.
        listPressedKey = new ArrayList<>();
        listPressedKey.add(0L);

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(this);

        Thread T = new Thread(this);
        T.start();
    }

    /**
     * Update the BbMap position on screen function of the BbMan map position.
     */
    private void updateBbManPosOnScreen() {
        if (bbMan.getPointOnMap().getX() < getWidth() / 2) { // left side.
            xBbManPosOnScreen = (int) bbMan.getPointOnMap().getX();
        } else if (bbMan.getPointOnMap().getX() > (MAP_WIDTH * IMAGE_SIZE) - (getWidth() / 2)) { // right side.
            xBbManPosOnScreen = getWidth() - ((MAP_WIDTH * IMAGE_SIZE) -
                    (int) bbMan.getPointOnMap().getX());
        } else { // standard case.
            xBbManPosOnScreen = getWidth() / 2;
        }
        if (bbMan.getPointOnMap().getY() < getHeight() / 2) { // upper side.
            yBbManPosOnScreen = (int) bbMan.getPointOnMap().getY();
        } else if (bbMan.getPointOnMap().getY() > (MAP_HEIGHT * IMAGE_SIZE) - (getHeight() / 2)) { // lower case.
            yBbManPosOnScreen = getHeight() - ((MAP_HEIGHT * IMAGE_SIZE) -
                    (int) bbMan.getPointOnMap().getY());
        } else { // standard case.
            yBbManPosOnScreen = getHeight() / 2;
        }
    }

    /**
     * Update the RMap start position on screen function of the BbMan map position.
     */
    private void updateMapStartPosOnScreen() {
        if (bbMan.getPointOnMap().getX() < getWidth() / 2) {
            xMapStartPosOnScreen = 0;
        } else if (bbMan.getPointOnMap().getX() > (MAP_WIDTH * IMAGE_SIZE) - (getWidth() / 2)) {
            xMapStartPosOnScreen = (MAP_WIDTH * IMAGE_SIZE) - getWidth();
        } else {
            xMapStartPosOnScreen = (int) bbMan.getPointOnMap().getX() - (getWidth() / 2);
        }
        if (bbMan.getPointOnMap().getY() < getHeight() / 2) {
            yMapStartPosOnScreen = 0;
        } else if (bbMan.getPointOnMap().getY() > (MAP_HEIGHT * IMAGE_SIZE) - (getHeight() / 2)) {
            yMapStartPosOnScreen = (MAP_HEIGHT * IMAGE_SIZE) - getHeight();
        } else {
            yMapStartPosOnScreen = (int) bbMan.getPointOnMap().getY() - (getHeight() / 2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        try {
            rMap.paintBuffer(g2d, new Point(xMapStartPosOnScreen, yMapStartPosOnScreen));
            bbMan.paintBuffer(g2d, new Point(xBbManPosOnScreen, yBbManPosOnScreen));
        } catch (Exception e) {
            System.err.println("exception painting RMap: " + e.getMessage());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Long keyCode = Long.valueOf(e.getKeyCode());
        Long lastKeyCode = listPressedKey.get(listPressedKey.size() - 1);
        if (!keyCode.equals(lastKeyCode)) {
            listPressedKey.add(Long.valueOf(e.getKeyCode()));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        listPressedKey.remove(Long.valueOf(e.getKeyCode()));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Is the BbMan crossing a map limit?
     *
     * @param xBbMan BbMan abscissa
     * @param yBbMan BbMan ordinate
     * @return true if the BbMan is crossing a map limit, false if not
     */
    private boolean isCrossingMapLimit(int xBbMan, int yBbMan) {
        boolean isCrossing = false;
        if (xBbMan - (IMAGE_SIZE / 2) < 0 ||
                xBbMan + (IMAGE_SIZE / 2) > rMap.mapWidth * IMAGE_SIZE) {
            isCrossing = true;
        }
        if (yBbMan < 0 ||
                yBbMan > rMap.mapHeight * IMAGE_SIZE) {
            isCrossing = true;
        }
        return isCrossing;
    }

    /**
     * Is the BbMan crossing an obstacle?
     *
     * @param xBbMan BbMan abscissa
     * @param yBbMan BbMan ordinate
     * @return true if BbMan is crossing an obstacle, false if not
     */
    private boolean isCrossingObstacle(int xBbMan, int yBbMan) {
        boolean isCrossing = false;
        if (!rMap.myMap[(yBbMan - (IMAGE_SIZE / 2)) / IMAGE_SIZE][(xBbMan - IMAGE_SIZE / 2) / IMAGE_SIZE].isPathway() ||
                !rMap.myMap[(yBbMan - (IMAGE_SIZE / 2)) / IMAGE_SIZE][(xBbMan + IMAGE_SIZE / 2 - 1) / IMAGE_SIZE].isPathway() ||
                !rMap.myMap[yBbMan / IMAGE_SIZE][(xBbMan - IMAGE_SIZE / 2) / IMAGE_SIZE].isPathway() ||
                !rMap.myMap[yBbMan / IMAGE_SIZE][(xBbMan + IMAGE_SIZE / 2 - 1) / IMAGE_SIZE].isPathway()) {
            isCrossing = true;
        }
        return isCrossing;
    }

    /**
     * Shift BbMan of a pixel to help him finding the way (if possible).
     *
     * @param pressedKey the pressed key
     */
    private void shiftBbManIfPossible(int pressedKey) {
        int bbManRowIdx = (int) bbMan.getPointOnMap().getY() / IMAGE_SIZE;
        int bbManColIdx = (int) bbMan.getPointOnMap().getX() / IMAGE_SIZE;
        int bbManRowShift = (int) bbMan.getPointOnMap().getY() % IMAGE_SIZE;
        int bbManColShift = (int) bbMan.getPointOnMap().getX() % IMAGE_SIZE;

        switch (pressedKey) {
            case KeyEvent.VK_UP: {
                if (rMap.myMap[bbManRowIdx - 1][bbManColIdx].isPathway()) { // the upper case is a pathway.
                    if (bbManColShift < IMAGE_SIZE / 2) { // bbMan on left side of its case.
                        bbMan.setPointOnMap((int) bbMan.getPointOnMap().getX() + 1, (int) bbMan.getPointOnMap().getY());
                    } else if (bbManColShift > IMAGE_SIZE / 2) { // bbMan on right side of its case.
                        bbMan.setPointOnMap((int) bbMan.getPointOnMap().getX() - 1, (int) bbMan.getPointOnMap().getY());
                    }
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                if (rMap.myMap[bbManRowIdx + 1][bbManColIdx].isPathway()) { // the lower case is a pathway.
                    if (bbManColShift < IMAGE_SIZE / 2) { // bbMan on left side of its case.
                        bbMan.setPointOnMap((int) bbMan.getPointOnMap().getX() + 1, (int) bbMan.getPointOnMap().getY());
                    } else if (bbManColShift > IMAGE_SIZE / 2) { // bbMan on right side of its case.
                        bbMan.setPointOnMap((int) bbMan.getPointOnMap().getX() - 1, (int) bbMan.getPointOnMap().getY());
                    }
                }
                break;
            }
            case KeyEvent.VK_LEFT: {
                if (rMap.myMap[bbManRowIdx][bbManColIdx - 1].isPathway()) { // the left case is a pathway.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bbMan on upper side of its case.
                        bbMan.setPointOnMap((int) bbMan.getPointOnMap().getX(), (int) bbMan.getPointOnMap().getY() + 1);
                    }
                }
                if (rMap.myMap[bbManRowIdx - 1][bbManColIdx - 1].isPathway()) { // the upper/left case is a pathway.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bbMan on upper side of its case.
                        bbMan.setPointOnMap((int) bbMan.getPointOnMap().getX(), (int) bbMan.getPointOnMap().getY() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_RIGHT: {
                if (rMap.myMap[bbManRowIdx][bbManColIdx + 1].isPathway()) { // the right case is a pathway.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bbMan on upper side of its case.
                        bbMan.setPointOnMap((int) bbMan.getPointOnMap().getX(), (int) bbMan.getPointOnMap().getY() + 1);
                    }
                }
                if (rMap.myMap[bbManRowIdx - 1][bbManColIdx + 1].isPathway()) { // the upper/right case is a pathway.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bbMan on upper side of its case.
                        bbMan.setPointOnMap((int) bbMan.getPointOnMap().getX(), (int) bbMan.getPointOnMap().getY() - 1);
                    }
                }
                break;
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            int xBbMan = (int) bbMan.getPointOnMap().getX();
            int yBbMan = (int) bbMan.getPointOnMap().getY();

            switch (listPressedKey.get(listPressedKey.size() - 1).intValue()) {
                case KeyEvent.VK_ESCAPE: {
                    System.exit(1);
                    break;
                }
                case 0: {
                    bbMan.setStatus(BbMan.STATUS.STATUS_WAIT);
                    break;
                }
                case KeyEvent.VK_UP: {
                    bbMan.setStatus(BbMan.STATUS.STATUS_WALK_BACK);
                    if (!isCrossingMapLimit(xBbMan, yBbMan - 1)) {
                        if (!isCrossingObstacle(xBbMan, yBbMan - 1)) {
                            bbMan.setPointOnMap(xBbMan, yBbMan - 1);
                        } else {
                            shiftBbManIfPossible(KeyEvent.VK_UP);
                        }
                    }
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    bbMan.setStatus(BbMan.STATUS.STATUS_WALK_FRONT);
                    if (!isCrossingMapLimit(xBbMan, yBbMan + 1)) {
                        if (!isCrossingObstacle(xBbMan, yBbMan + 1)) {
                            bbMan.setPointOnMap(xBbMan, yBbMan + 1);
                        } else {
                            shiftBbManIfPossible(KeyEvent.VK_DOWN);
                        }
                    }
                    break;
                }
                case KeyEvent.VK_LEFT: {
                    bbMan.setStatus(BbMan.STATUS.STATUS_WALK_LEFT);
                    if (!isCrossingMapLimit(xBbMan - 1, yBbMan)) {
                        if (!isCrossingObstacle(xBbMan - 1, yBbMan)) {
                            bbMan.setPointOnMap(xBbMan - 1, yBbMan);
                        } else {
                            shiftBbManIfPossible(KeyEvent.VK_LEFT);
                        }
                    }
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    bbMan.setStatus(BbMan.STATUS.STATUS_WALK_RIGHT);
                    if (!isCrossingMapLimit(xBbMan + 1, yBbMan)) {
                        if (!isCrossingObstacle(xBbMan + 1, yBbMan)) {
                            bbMan.setPointOnMap(xBbMan + 1, yBbMan);
                        } else {
                            shiftBbManIfPossible(KeyEvent.VK_RIGHT);
                        }
                    }
                    break;
                }
                case KeyEvent.VK_W: {
                    bbMan.setStatus(BbMan.STATUS.STATUS_WIN);
                    break;
                }
                case KeyEvent.VK_D: {
                    bbMan.setStatus(BbMan.STATUS.STATUS_DEATH);
                    break;
                }
                case KeyEvent.VK_F: {
                    rMap.flamesList.add(new Flame(10, 10));
                    break;
                }
            }
            updateBbManPosOnScreen();
            updateMapStartPosOnScreen();
            repaint();
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }
    }
}