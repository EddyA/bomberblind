import exceptions.CannotCreateRMapElementException;
import exceptions.OutOfRMapBoundsException;
import map.CharacterMethods;
import map.RMap;
import sprites.nomad.BbManBlue;
import sprites.nomad.abstracts.BbMan;

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
    private BbManBlue bbMan;
    private SpriteList spriteList;
    private List<Long> pressedKeyList;

    private int xBbManPosOnScreen;
    private int yBbManPosOnScreen;
    private int xMapStartPosOnScreen;
    private int yMapStartPosOnScreen;

    GameJpanel(int widthScreen, int heightScreen) throws CannotCreateRMapElementException {
        rMap = new RMap(widthScreen, heightScreen);
        rMap.createPatterns();
        rMap.generateMap();

        // compute the position of the BbMan on the map.
        int xBbManOnMap = rMap.getSpCastleT1().getColIdx() * IMAGE_SIZE +
                (rMap.getCastleT1().getWidth() * IMAGE_SIZE / 2);
        int yBbManOnMap = rMap.getSpCastleT1().getRowIdx() * IMAGE_SIZE +
                (rMap.getCastleT1().getHeight() * IMAGE_SIZE) + (IMAGE_SIZE / 2);

        // create the BbMan.
        bbMan = new BbManBlue(xBbManOnMap, yBbManOnMap);

        // create a list of sprites.
        spriteList = new SpriteList(rMap, widthScreen, heightScreen);

        // create a list to handle pressed key.
        pressedKeyList = new ArrayList<>();
        pressedKeyList.add(0L);

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
        if (bbMan.getXMap() < getWidth() / 2) { // left side.
            xBbManPosOnScreen = bbMan.getXMap();
        } else if (bbMan.getXMap() > (MAP_WIDTH * IMAGE_SIZE) - (getWidth() / 2)) { // right side.
            xBbManPosOnScreen = getWidth() - ((MAP_WIDTH * IMAGE_SIZE) - bbMan.getXMap());
        } else { // standard case.
            xBbManPosOnScreen = getWidth() / 2;
        }
        if (bbMan.getYMap() < getHeight() / 2) { // upper side.
            yBbManPosOnScreen = bbMan.getYMap();
        } else if (bbMan.getYMap() > (MAP_HEIGHT * IMAGE_SIZE) - (getHeight() / 2)) { // lower case.
            yBbManPosOnScreen = getHeight() - ((MAP_HEIGHT * IMAGE_SIZE) - bbMan.getYMap());
        } else { // standard case.
            yBbManPosOnScreen = getHeight() / 2;
        }
    }

    /**
     * Update the RMap start position on screen function of the BbMan map position.
     */
    private void updateMapStartPosOnScreen() {
        if (bbMan.getXMap() < getWidth() / 2) {
            xMapStartPosOnScreen = 0;
        } else if (bbMan.getXMap() > (MAP_WIDTH * IMAGE_SIZE) - (getWidth() / 2)) {
            xMapStartPosOnScreen = (MAP_WIDTH * IMAGE_SIZE) - getWidth();
        } else {
            xMapStartPosOnScreen = bbMan.getXMap() - (getWidth() / 2);
        }
        if (bbMan.getYMap() < getHeight() / 2) {
            yMapStartPosOnScreen = 0;
        } else if (bbMan.getYMap() > (MAP_HEIGHT * IMAGE_SIZE) - (getHeight() / 2)) {
            yMapStartPosOnScreen = (MAP_HEIGHT * IMAGE_SIZE) - getHeight();
        } else {
            yMapStartPosOnScreen = bbMan.getYMap() - (getHeight() / 2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        try {
            rMap.paintBuffer(g2d, xMapStartPosOnScreen, yMapStartPosOnScreen);
            spriteList.paintBuffer(g2d, xMapStartPosOnScreen, yMapStartPosOnScreen);
            bbMan.paintBuffer(g2d, xBbManPosOnScreen, yBbManPosOnScreen);
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
     * Shift BbMan of a pixel to help him finding the way (if possible).
     *
     * @param pressedKey the pressed key
     */
    private void shiftBbManIfPossible(int pressedKey) {
        int bbManRowIdx = bbMan.getYMap() / IMAGE_SIZE;
        int bbManColIdx = bbMan.getXMap() / IMAGE_SIZE;
        int bbManRowShift = bbMan.getYMap() % IMAGE_SIZE;
        int bbManColShift = bbMan.getXMap() % IMAGE_SIZE;

        switch (pressedKey) {
            case KeyEvent.VK_UP: {
                if (rMap.getRMapPoint(bbManRowIdx - 1, bbManColIdx).isPathway() &&
                        !rMap.getRMapPoint(bbManRowIdx - 1, bbManColIdx).isBombing()) { // the upper case is a pathway && !bombing.
                    if (bbManColShift < IMAGE_SIZE / 2) { // bbMan on left side of its case.
                        bbMan.setXMap(bbMan.getXMap() + 1);
                    } else if (bbManColShift > IMAGE_SIZE / 2) { // bbMan on right side of its case.
                        bbMan.setXMap(bbMan.getXMap() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                if (rMap.getRMapPoint(bbManRowIdx + 1, bbManColIdx).isPathway() &&
                        !rMap.getRMapPoint(bbManRowIdx + 1, bbManColIdx).isBombing()) { // the lower case is a pathway && !bombing.
                    if (bbManColShift < IMAGE_SIZE / 2) { // bbMan on left side of its case.
                        bbMan.setXMap(bbMan.getXMap() + 1);
                    } else if (bbManColShift > IMAGE_SIZE / 2) { // bbMan on right side of its case.
                        bbMan.setXMap(bbMan.getXMap() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_LEFT: {
                if (rMap.getRMapPoint(bbManRowIdx, bbManColIdx - 1).isPathway() &&
                        !rMap.getRMapPoint(bbManRowIdx, bbManColIdx - 1).isBombing()) { // the left case is a pathway && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bbMan on upper side of its case.
                        bbMan.setYMap(bbMan.getYMap() + 1);
                    }
                }
                if (rMap.getRMapPoint(bbManRowIdx - 1, bbManColIdx - 1).isPathway() &&
                        !rMap.getRMapPoint(bbManRowIdx - 1, bbManColIdx - 1).isBombing()) { // the upper/left case is a pathway &&
                    // !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bbMan on upper side of its case.
                        bbMan.setYMap(bbMan.getYMap() - 1);
                    }
                }
                break;
            }
            case KeyEvent.VK_RIGHT: {
                if (rMap.getRMapPoint(bbManRowIdx, bbManColIdx + 1).isPathway() &&
                        !rMap.getRMapPoint(bbManRowIdx, bbManColIdx + 1).isBombing()) { // the right case is a pathway && !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bbMan on upper side of its case.
                        bbMan.setYMap(bbMan.getYMap() + 1);
                    }
                }
                if (rMap.getRMapPoint(bbManRowIdx - 1, bbManColIdx + 1).isPathway() &&
                        !rMap.getRMapPoint(bbManRowIdx - 1, bbManColIdx + 1).isBombing()) { // the upper/right case is a pathway &&
                    // !bombing.
                    if (bbManRowShift < IMAGE_SIZE / 2) { // bbMan on upper side of its case.
                        bbMan.setYMap(bbMan.getYMap() - 1);
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
                if (bbMan.getStatus() == BbMan.status.STATUS_DEAD) {
                    if (bbMan.isFinished()) {
                        bbMan.initStatement();
                    }
                } else {
                    switch (pressedKeyList.get(pressedKeyList.size() - 1).intValue()) {
                        case KeyEvent.VK_ESCAPE: {
                            System.exit(1);
                            break;
                        }
                        case 0: {
                            bbMan.setStatus(BbMan.status.STATUS_WAIT);
                            break;
                        }
                        case KeyEvent.VK_UP: {
                            bbMan.setStatus(BbMan.status.STATUS_WALK_BACK);
                            if (!CharacterMethods.isCharacterCrossingMapLimit(rMap, bbMan.getXMap(), bbMan.getYMap() - 1)) {
                                if (!CharacterMethods.isCharacterCrossingObstacle(rMap, bbMan.getXMap(), bbMan.getYMap() - 1) &&
                                        !CharacterMethods.isCharacterCrossingBomb(rMap, bbMan.getXMap(), bbMan.getYMap() - 1,
                                                KeyEvent.VK_UP)) {
                                    bbMan.setYMap(bbMan.getYMap() - 1);
                                } else {
                                    shiftBbManIfPossible(KeyEvent.VK_UP);
                                }
                            }
                            break;
                        }
                        case KeyEvent.VK_DOWN: {
                            bbMan.setStatus(BbMan.status.STATUS_WALK_FRONT);
                            if (!CharacterMethods.isCharacterCrossingMapLimit(rMap, bbMan.getXMap(), bbMan.getYMap() + 1)) {
                                if (!CharacterMethods.isCharacterCrossingObstacle(rMap, bbMan.getXMap(), bbMan.getYMap() + 1) &&
                                        !CharacterMethods.isCharacterCrossingBomb(rMap, bbMan.getXMap(), bbMan.getYMap() + 1,
                                                KeyEvent.VK_DOWN)) {
                                    bbMan.setYMap(bbMan.getYMap() + 1);
                                } else {
                                    shiftBbManIfPossible(KeyEvent.VK_DOWN);
                                }
                            }
                            break;
                        }
                        case KeyEvent.VK_LEFT: {
                            bbMan.setStatus(BbMan.status.STATUS_WALK_LEFT);
                            if (!CharacterMethods.isCharacterCrossingMapLimit(rMap, bbMan.getXMap() - 1, bbMan.getYMap())) {
                                if (!CharacterMethods.isCharacterCrossingObstacle(rMap, bbMan.getXMap() - 1, bbMan.getYMap()) &&
                                        !CharacterMethods.isCharacterCrossingBomb(rMap, bbMan.getXMap() - 1, bbMan.getYMap(),
                                                KeyEvent.VK_LEFT)) {
                                    bbMan.setXMap(bbMan.getXMap() - 1);
                                } else {
                                    shiftBbManIfPossible(KeyEvent.VK_LEFT);
                                }
                            }
                            break;
                        }
                        case KeyEvent.VK_RIGHT: {
                            bbMan.setStatus(BbMan.status.STATUS_WALK_RIGHT);
                            if (!CharacterMethods.isCharacterCrossingMapLimit(rMap, bbMan.getXMap() + 1, bbMan.getYMap())) {
                                if (!CharacterMethods.isCharacterCrossingObstacle(rMap, bbMan.getXMap() + 1, bbMan.getYMap()) &&
                                        !CharacterMethods.isCharacterCrossingBomb(rMap, bbMan.getXMap() + 1, bbMan.getYMap(),
                                                KeyEvent.VK_RIGHT)) {
                                    bbMan.setXMap(bbMan.getXMap() + 1);
                                } else {
                                    shiftBbManIfPossible(KeyEvent.VK_RIGHT);
                                }
                            }
                            break;
                        }
                        case KeyEvent.VK_B: {
                            spriteList.addBomb(bbMan.getYMap() / IMAGE_SIZE, bbMan.getXMap() / IMAGE_SIZE, 5);
                            break;
                        }
                        case KeyEvent.VK_W: {
                            bbMan.setStatus(BbMan.status.STATUS_WIN);
                            break;
                        }
                    }
                    updateMapStartPosOnScreen();
                    updateBbManPosOnScreen();
                    if (!bbMan.isInvincible() &&
                            CharacterMethods.isCharacterBurning(rMap, bbMan.getXMap(), bbMan.getYMap())) {
                        bbMan.setStatus(BbMan.status.STATUS_DEAD);
                    }
                }
                spriteList.clean();
                repaint();
                Thread.sleep(4);
            } catch (InterruptedException | OutOfRMapBoundsException e) {
                System.err.println("Unexpected exception: " + e.getMessage());
            }
        }
    }
}