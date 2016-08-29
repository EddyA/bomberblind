import static images.ImagesLoader.IMAGE_SIZE;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import exceptions.CannotCreateMapElementException;
import exceptions.InvalidMapConfigurationException;
import map.ctrl.NomadMethods;
import map.zelda.ZeldaMap;
import map.zelda.ZeldaMapProperties;
import map.zelda.ZeldaMapSetting;
import sprites.nomad.BomberBlue;
import sprites.nomad.abstracts.Bomber;
import utils.Tuple2;

public class GameJpanel extends JPanel implements Runnable, KeyListener {

    private final int MAP_WIDTH = 80;
    private final int MAP_HEIGHT = 32;

    private map.abstracts.Map map;
    private BomberBlue mainBomber;
    private NomadList nomadList;
    private SettledList settledList;
    private List<Long> pressedKeyList;

    private int xBbManPosOnScreen;
    private int yBbManPosOnScreen;
    private int xMapStartPosOnScreen;
    private int yMapStartPosOnScreen;

    GameJpanel(int widthScreen, int heightScreen)
            throws IOException, InvalidMapConfigurationException, CannotCreateMapElementException {

        // get the map properties.
        ZeldaMapProperties mapProperties = new ZeldaMapProperties("/zelda.map.properties")
                .loadProperties()
                .checkProperties();

        // create the map.
        map = new ZeldaMap(new ZeldaMapSetting(mapProperties), widthScreen, heightScreen);
        map.generateMap();

        // create a list of sprites.
        nomadList = new NomadList(map, widthScreen, heightScreen);
        settledList = new SettledList(map, widthScreen, heightScreen);

        // create the main bomber and add it to the list of nomad.
        Tuple2<Integer, Integer> bbManInitialPosition = map.computeInitialBbManPosition();
        mainBomber = new BomberBlue(bbManInitialPosition.getFirst(), bbManInitialPosition.getSecond());
        nomadList.addMainBomber(mainBomber);

        // ToDo: Just a test ...
        nomadList.addCloakedSkeleton(bbManInitialPosition.getFirst() - IMAGE_SIZE, bbManInitialPosition.getSecond());
        nomadList.addCloakedSkeleton(bbManInitialPosition.getFirst() - IMAGE_SIZE * 2, bbManInitialPosition.getSecond());
        nomadList.addCloakedSkeleton(bbManInitialPosition.getFirst() + IMAGE_SIZE, bbManInitialPosition.getSecond());
        nomadList.addCloakedSkeleton(bbManInitialPosition.getFirst() + IMAGE_SIZE * 2, bbManInitialPosition.getSecond());

        // create a list to handle pressed key.
        pressedKeyList = new ArrayList<>();
        pressedKeyList.add(0L); // add the "wait" status.

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(this);

        Thread T = new Thread(this);
        T.start();
    }

    /**
     * Update the ZeldaMap start position on screen function of the Bomber map position.
     */
    private void updateMapStartPosOnScreen() {
        if (mainBomber.getXMap() < getWidth() / 2) {
            xMapStartPosOnScreen = 0;
        } else if (mainBomber.getXMap() > (MAP_WIDTH * IMAGE_SIZE) - (getWidth() / 2)) {
            xMapStartPosOnScreen = (MAP_WIDTH * IMAGE_SIZE) - getWidth();
        } else {
            xMapStartPosOnScreen = mainBomber.getXMap() - (getWidth() / 2);
        }
        if (mainBomber.getYMap() < getHeight() / 2) {
            yMapStartPosOnScreen = 0;
        } else if (mainBomber.getYMap() > (MAP_HEIGHT * IMAGE_SIZE) - (getHeight() / 2)) {
            yMapStartPosOnScreen = (MAP_HEIGHT * IMAGE_SIZE) - getHeight();
        } else {
            yMapStartPosOnScreen = mainBomber.getYMap() - (getHeight() / 2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        try {
            map.paintBuffer(g2d, xMapStartPosOnScreen, yMapStartPosOnScreen);
            settledList.paintBuffer(g2d, xMapStartPosOnScreen, yMapStartPosOnScreen);
            nomadList.paintBuffer(g2d, xMapStartPosOnScreen, yMapStartPosOnScreen);
        } catch (Exception e) {
            System.err.println("GameJPanel.paintComponent(): " + e.getMessage());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Long keyCode = Long.valueOf(e.getKeyCode());
        Long lastKeyCode = pressedKeyList.get(pressedKeyList.size() - 1);
        if (!keyCode.equals(lastKeyCode)) {
            pressedKeyList.add(Long.valueOf(e.getKeyCode()));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeyList.remove(Long.valueOf(e.getKeyCode()));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Shift Bomber of a pixel to help him finding the way (if possible).
     *
     * @param pressedKey the pressed key
     */
    private void shiftBbManIfPossible(int pressedKey) {
        int bbManRowIdx = mainBomber.getYMap() / IMAGE_SIZE;
        int bbManColIdx = mainBomber.getXMap() / IMAGE_SIZE;
        int bbManRowShift = mainBomber.getYMap() % IMAGE_SIZE;
        int bbManColShift = mainBomber.getXMap() % IMAGE_SIZE;

        switch (pressedKey) {
            case KeyEvent.VK_UP: {
                if (map.getMapPointMatrix()[bbManRowIdx - 1][bbManColIdx].isPathway() && // the upper case is a pathway
                        !map.getMapPointMatrix()[bbManRowIdx - 1][bbManColIdx].isBombing()) {  // && !bombing.
                    if (bbManColShift < IMAGE_SIZE / 2) { // mainBomber on left side of its case.
                        mainBomber.setXMap(mainBomber.getXMap() + 1);
                    } else if (bbManColShift > IMAGE_SIZE / 2) { // mainBomber on right side of its case.
                        mainBomber.setXMap(mainBomber.getXMap() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                if (map.getMapPointMatrix()[bbManRowIdx + 1][bbManColIdx].isPathway() && // the lower case is a pathway
                        !map.getMapPointMatrix()[bbManRowIdx + 1][bbManColIdx].isBombing()) { // && !bombing.
                    if (bbManColShift < IMAGE_SIZE / 2) { // mainBomber on left side of its case.
                        mainBomber.setXMap(mainBomber.getXMap() + 1);
                    } else if (bbManColShift > IMAGE_SIZE / 2) { // mainBomber on right side of its case.
                        mainBomber.setXMap(mainBomber.getXMap() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_LEFT: {
                if (map.getMapPointMatrix()[bbManRowIdx][bbManColIdx - 1].isPathway() && // the left case is a pathway
                        !map.getMapPointMatrix()[bbManRowIdx][bbManColIdx - 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // mainBomber on upper side of its case.
                        mainBomber.setYMap(mainBomber.getYMap() + 1);
                    }
                }
                if (map.getMapPointMatrix()[bbManRowIdx - 1][bbManColIdx - 1].isPathway() && // the upper/left case is a pathway
                        !map.getMapPointMatrix()[bbManRowIdx - 1][bbManColIdx - 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // mainBomber on upper side of its case.
                        mainBomber.setYMap(mainBomber.getYMap() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_RIGHT: {
                if (map.getMapPointMatrix()[bbManRowIdx][bbManColIdx + 1].isPathway() && // the right case is a pathway
                        !map.getMapPointMatrix()[bbManRowIdx][bbManColIdx + 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // mainBomber on upper side of its case.
                        mainBomber.setYMap(mainBomber.getYMap() + 1);
                    }
                }
                if (map.getMapPointMatrix()[bbManRowIdx - 1][bbManColIdx + 1].isPathway() && // the upper/right case is a pathway
                        !map.getMapPointMatrix()[bbManRowIdx - 1][bbManColIdx + 1].isBombing()) { // && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // mainBomber on upper side of its case.
                        mainBomber.setYMap(mainBomber.getYMap() - 1);
                    }
                }
                break;
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (mainBomber.getStatus() != Bomber.status.STATUS_DEAD) {
                    switch (pressedKeyList.get(pressedKeyList.size() - 1).intValue()) {
                        case KeyEvent.VK_ESCAPE: {
                            System.exit(1);
                            break;
                        }
                        case 0: {
                            mainBomber.setStatus(Bomber.status.STATUS_WAIT);
                            break;
                        }
                        case KeyEvent.VK_UP: {
                            mainBomber.setStatus(Bomber.status.STATUS_WALK_BACK);
                        if (!NomadMethods.isNomadCrossingMapLimit(map.getMapWidth(), map.getMapHeight(),
                                    mainBomber.getXMap(), mainBomber.getYMap() - 1)) {
                            if (!NomadMethods.isNomadCrossingObstacle(map.getMapPointMatrix(), mainBomber.getXMap(),
                                    mainBomber.getYMap() - 1) &&
                                    !NomadMethods.isNomadCrossingBomb(map.getMapPointMatrix(), mainBomber.getXMap(),
                                            mainBomber.getYMap() - 1, KeyEvent.VK_UP)) {
                                    mainBomber.setYMap(mainBomber.getYMap() - 1);
                                } else {
                                    shiftBbManIfPossible(KeyEvent.VK_UP);
                                }
                            }
                            break;
                        }
                        case KeyEvent.VK_DOWN: {
                            mainBomber.setStatus(Bomber.status.STATUS_WALK_FRONT);
                        if (!NomadMethods.isNomadCrossingMapLimit(map.getMapWidth(), map.getMapHeight(),
                                    mainBomber.getXMap(), mainBomber.getYMap() + 1)) {
                            if (!NomadMethods.isNomadCrossingObstacle(map.getMapPointMatrix(), mainBomber.getXMap(),
                                    mainBomber.getYMap() + 1) &&
                                    !NomadMethods.isNomadCrossingBomb(map.getMapPointMatrix(), mainBomber.getXMap(),
                                            mainBomber.getYMap() + 1, KeyEvent.VK_DOWN)) {
                                    mainBomber.setYMap(mainBomber.getYMap() + 1);
                                } else {
                                    shiftBbManIfPossible(KeyEvent.VK_DOWN);
                                }
                            }
                            break;
                        }
                        case KeyEvent.VK_LEFT: {
                            mainBomber.setStatus(Bomber.status.STATUS_WALK_LEFT);
                        if (!NomadMethods.isNomadCrossingMapLimit(map.getMapWidth(), map.getMapHeight(),
                                    mainBomber.getXMap() - 1, mainBomber.getYMap())) {
                            if (!NomadMethods.isNomadCrossingObstacle(map.getMapPointMatrix(), mainBomber.getXMap() - 1,
                                    mainBomber.getYMap()) &&
                                    !NomadMethods.isNomadCrossingBomb(map.getMapPointMatrix(), mainBomber.getXMap() - 1,
                                            mainBomber.getYMap(), KeyEvent.VK_LEFT)) {
                                    mainBomber.setXMap(mainBomber.getXMap() - 1);
                                } else {
                                    shiftBbManIfPossible(KeyEvent.VK_LEFT);
                                }
                            }
                            break;
                        }
                        case KeyEvent.VK_RIGHT: {
                            mainBomber.setStatus(Bomber.status.STATUS_WALK_RIGHT);
                        if (!NomadMethods.isNomadCrossingMapLimit(map.getMapWidth(), map.getMapHeight(),
                                    mainBomber.getXMap() + 1, mainBomber.getYMap())) {
                            if (!NomadMethods.isNomadCrossingObstacle(map.getMapPointMatrix(), mainBomber.getXMap() + 1,
                                    mainBomber.getYMap()) &&
                                    !NomadMethods.isNomadCrossingBomb(map.getMapPointMatrix(), mainBomber.getXMap() + 1,
                                            mainBomber.getYMap(), KeyEvent.VK_RIGHT)) {
                                    mainBomber.setXMap(mainBomber.getXMap() + 1);
                                } else {
                                    shiftBbManIfPossible(KeyEvent.VK_RIGHT);
                                }
                            }
                            break;
                        }
                        case KeyEvent.VK_B: {
                            settledList.addBomb(mainBomber.getYMap() / IMAGE_SIZE, mainBomber.getXMap() / IMAGE_SIZE, 5);
                            break;
                        }
                        case KeyEvent.VK_W: {
                            mainBomber.setStatus(Bomber.status.STATUS_WIN);
                            break;
                        }
                    }
                    updateMapStartPosOnScreen();
                }
                settledList.updateStatusAndClean();
                nomadList.updateStatusAndClean();
                repaint();
                Thread.sleep(4);
            } catch (InterruptedException e) {
                System.err.println("Unexpected exception: " + e.getMessage());
            }
        }
    }
}