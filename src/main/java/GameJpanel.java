import static images.ImagesLoader.IMAGE_SIZE;
import static spriteList.ctrl.AddingMethods.addBomb;
import static spriteList.ctrl.AddingMethods.addBomber;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

import exceptions.CannotCreateMapElementException;
import exceptions.CannotPlaceSpriteOnMapException;
import exceptions.InvalidConfigurationException;
import exceptions.InvalidPropertiesException;
import map.ctrl.NomadMethods;
import map.zelda.ZeldaMap;
import map.zelda.ZeldaMapProperties;
import map.zelda.ZeldaMapSetting;
import sprite.nomad.BlueBomber;
import sprite.nomad.abstracts.Bomber;
import spriteList.SpriteList;
import spriteList.SpritesProperties;
import spriteList.SpritesSetting;
import utils.Direction;
import utils.Tools;
import utils.Tuple2;

class GameJpanel extends JPanel implements Runnable, KeyListener {

    private map.abstracts.Map map;
    private BlueBomber mainBomber;
    private SpriteList spriteList;
    private List<Long> pressedKeyList;

    // this members allow printing map from a certain point.
    private int xMapStartPosOnScreen;
    private int yMapStartPosOnScreen;

    GameJpanel(int widthScreen, int heightScreen) throws IOException, InvalidPropertiesException,
            InvalidConfigurationException, CannotCreateMapElementException, CannotPlaceSpriteOnMapException {

        // create the map.
        map = new ZeldaMap(
                new ZeldaMapSetting(
                        new ZeldaMapProperties("/zelda.map.properties").loadProperties().checkProperties()),
                widthScreen,
                heightScreen);
        map.generateMap();

        // create the list of sprites.
        spriteList = new SpriteList(
                new SpritesSetting(
                        new SpritesProperties("/zelda.sprites.properties").loadProperties().checkProperties()),
                map,
                widthScreen,
                heightScreen);
        spriteList.generateSprites();

        // create the main bomber and add it to the list of sprites.
        Tuple2<Integer, Integer> bbManInitialPosition = map.computeInitialBbManPosition();
        mainBomber = new BlueBomber(bbManInitialPosition.getFirst(), bbManInitialPosition.getSecond());
        addBomber(spriteList, mainBomber);

        // create a list to handle pressed keys.
        pressedKeyList = new ArrayList<>();
        pressedKeyList.add(0L); // add the "wait" action.

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
        } else if (mainBomber.getXMap() > (map.getMapWidth() * IMAGE_SIZE) - (getWidth() / 2)) {
            xMapStartPosOnScreen = (map.getMapWidth() * IMAGE_SIZE) - getWidth();
        } else {
            xMapStartPosOnScreen = mainBomber.getXMap() - (getWidth() / 2);
        }
        if (mainBomber.getYMap() < getHeight() / 2) {
            yMapStartPosOnScreen = 0;
        } else if (mainBomber.getYMap() > (map.getMapHeight() * IMAGE_SIZE) - (getHeight() / 2)) {
            yMapStartPosOnScreen = (map.getMapHeight() * IMAGE_SIZE) - getHeight();
        } else {
            yMapStartPosOnScreen = mainBomber.getYMap() - (getHeight() / 2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        try {
            map.paintBuffer(g2d, xMapStartPosOnScreen, yMapStartPosOnScreen);
            spriteList.paintBuffer(g2d, xMapStartPosOnScreen, yMapStartPosOnScreen);
        } catch (Exception e) {
            System.err.println("GameJPanel.paintComponent(): " + e.getMessage());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Long keyCode = (long) e.getKeyCode();
        Long lastKeyCode = pressedKeyList.get(pressedKeyList.size() - 1);
        if (!keyCode.equals(lastKeyCode)) {
            pressedKeyList.add((long) e.getKeyCode());
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
                if (mainBomber.getCurAction() != Bomber.Action.STATUS_DYING && !mainBomber.isFinished()) {
                    switch (pressedKeyList.get(pressedKeyList.size() - 1).intValue()) {
                        case KeyEvent.VK_ESCAPE: {
                            System.exit(1);
                            break;
                        }
                        case 0: {
                        mainBomber.setCurAction(Bomber.Action.STATUS_WAITING);
                            break;
                        }
                        case KeyEvent.VK_UP: {
                        mainBomber.setCurAction(Bomber.Action.STATUS_WALKING);
                        mainBomber.setCurDirection(Direction.NORTH);
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
                        mainBomber.setCurAction(Bomber.Action.STATUS_WALKING);
                        mainBomber.setCurDirection(Direction.SOUTH);
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
                        mainBomber.setCurAction(Bomber.Action.STATUS_WALKING);
                        mainBomber.setCurDirection(Direction.WEST);
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
                        mainBomber.setCurAction(Bomber.Action.STATUS_WALKING);
                        mainBomber.setCurDirection(Direction.EAST);
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
                        addBomb(spriteList, map.getMapPointMatrix(), Tools.getCharRowIdx(mainBomber.getYMap()),
                                Tools.getCharColIdx(mainBomber.getXMap()), 3);
                            break;
                        }
                        case KeyEvent.VK_W: {
                        mainBomber.setCurAction(Bomber.Action.STATUS_WON);
                            break;
                        }
                    }
                    updateMapStartPosOnScreen();
                }
                spriteList.update();

                // update the list order to handle the sprites superposition.
                spriteList.sort((o1, o2) -> {
                    if (o1.getYMap() < o2.getYMap()) {
                        return -1;
                    } else if (o1.getYMap() > o2.getYMap()) {
                        return 1;
                    } else {
                        return 0;
                    }
                });
                repaint();
                Thread.sleep(4);
            } catch (InterruptedException e) {
                System.err.println("Unexpected exception: " + Arrays.toString(e.getStackTrace()));
            }
        }
    }
}