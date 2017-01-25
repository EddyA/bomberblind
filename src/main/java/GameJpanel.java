import static images.ImagesLoader.IMAGE_SIZE;
import static spriteList.ctrl.AddingMethods.addBird;
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
import exceptions.CannotPlaceEnemyOnMapException;
import exceptions.InvalidConfigurationException;
import exceptions.InvalidPropertiesException;
import map.Map;
import map.zelda.ZeldaMap;
import map.zelda.ZeldaMapProperties;
import map.zelda.ZeldaMapSetting;
import sprite.SpriteType;
import sprite.nomad.BlueBomber;
import sprite.nomad.Bomber;
import sprite.nomad.WhiteBird;
import spriteList.SpriteList;
import spriteList.SpritesProperties;
import spriteList.SpritesSetting;
import utils.Direction;
import utils.Tuple2;

public class GameJpanel extends JPanel implements Runnable, KeyListener {

    private Map map;
    private Bomber bomber;
    private SpriteList spriteList;
    private List<Long> pressedKeyList;

    // this members allow printing map from a certain point.
    private int xMapStartPosOnScreen;
    private int yMapStartPosOnScreen;

    public GameJpanel(int widthScreen, int heightScreen) throws IOException, InvalidPropertiesException,
            InvalidConfigurationException, CannotCreateMapElementException, CannotPlaceEnemyOnMapException {

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
        bomber = new BlueBomber(bbManInitialPosition.getFirst(), bbManInitialPosition.getSecond());
        addBomber(spriteList, bomber);

        // create the 3 first birds :)
        addBird(spriteList, 
                new WhiteBird(0, bbManInitialPosition.getSecond() + 200, Direction.EAST, -6));
        addBird(spriteList,
                new WhiteBird(-50, bbManInitialPosition.getSecond() + 225, Direction.EAST, -8));
        addBird(spriteList,
                new WhiteBird(0, bbManInitialPosition.getSecond() + 250, Direction.EAST, -12));

        // create a list to handle pressed keys.
        pressedKeyList = new ArrayList<>();
        pressedKeyList.add(0L); // add the "wait" action.

        setFocusable(true);
        addKeyListener(this);

        Thread T = new Thread(this);
        T.start();
    }

    /**
     * Update the ZeldaMap start position on screen function of the Bomber map position.
     */
    private void updateMapStartPosOnScreen() {
        if (bomber.getxMap() < getWidth() / 2) {
            xMapStartPosOnScreen = 0;
        } else if (bomber.getxMap() > (map.getMapWidth() * IMAGE_SIZE) - (getWidth() / 2)) {
            xMapStartPosOnScreen = (map.getMapWidth() * IMAGE_SIZE) - getWidth();
        } else {
            xMapStartPosOnScreen = bomber.getxMap() - (getWidth() / 2);
        }
        if (bomber.getyMap() < getHeight() / 2) {
            yMapStartPosOnScreen = 0;
        } else if (bomber.getyMap() > (map.getMapHeight() * IMAGE_SIZE) - (getHeight() / 2)) {
            yMapStartPosOnScreen = (map.getMapHeight() * IMAGE_SIZE) - getHeight();
        } else {
            yMapStartPosOnScreen = bomber.getyMap() - (getHeight() / 2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        try {
            map.paintBuffer(g2d, xMapStartPosOnScreen, yMapStartPosOnScreen);
            spriteList.paintBuffer(g2d, xMapStartPosOnScreen, yMapStartPosOnScreen);
        } catch (Exception e) {
            System.err.println("GameJPanel.paintComponent(): " + e);
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

    @Override
    public void run() {
        while (true) {
            try {
                int pressedKey = pressedKeyList.get(pressedKeyList.size() - 1).intValue();
                if (pressedKey == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                updateMapStartPosOnScreen();
                spriteList.update(pressedKey);

                // update the list order to handle sprites superposition.
                spriteList.sort((o1, o2) -> {
                    if (o1.getSpriteType() != o2.getSpriteType() &&
                            (o1.getSpriteType() == SpriteType.TYPE_FLYING_NOMAD ||
                                    o2.getSpriteType() == SpriteType.TYPE_FLYING_NOMAD)) {
                        return o1.getSpriteType() == SpriteType.TYPE_FLYING_NOMAD ? 1 : -1;
                    } else if (o1.getyMap() != o2.getyMap()) {
                        return o1.getyMap() > o2.getyMap() ? 1 : -1;
                    } else {
                        return 0;
                    }
                });
                repaint();
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.err.println("Unexpected exception: " + Arrays.toString(e.getStackTrace()));
            }
        }
    }
}