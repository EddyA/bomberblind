import exceptions.CannotCreateMapElementException;
import exceptions.CannotFindPathFromEntranceToExitException;
import exceptions.InvalidConfigurationException;
import exceptions.InvalidPropertiesException;
import map.Map;
import map.zelda.ZeldaMap;
import map.zelda.ZeldaMapProperties;
import map.zelda.ZeldaMapSetting;
import sprite.SpriteType;
import sprite.nomad.BlueBomber;
import sprite.nomad.Bomber;
import sprite.settled.BonusType;
import sprite.settled.Sparkle;
import spritelist.SpriteList;
import spritelist.SpritesProperties;
import spritelist.SpritesSetting;
import utils.Timer;
import utils.TopBar;
import utils.Tuple2;
import utils.text.SkinnedTextWithBG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static images.ImagesLoader.IMAGE_SIZE;
import static map.ctrl.NomadMethods.isNomadCrossingExit;
import static spritelist.ctrl.AddingMethods.addBomber;
import static spritelist.ctrl.AddingMethods.addSparkle;
import static spritelist.ctrl.GenerationMethods.placeAGroupOfBird;
import static utils.Direction.DIRECTION_EAST;

public class GameJPanel extends JPanel implements Runnable, KeyListener {

    private static final int MAX_NB_MAP_GENERATION = 20; // max number of try to generate the map.

    private final Map map;
    private final Bomber bomber;
    private final SpriteList spriteList;
    private final List<Long> pressedKeyList;

    private final Timer timer = new Timer();
    private GameStatus gameStatus;

    // this members allow printing map from a certain point.
    private int xMapStartPosOnScreen;
    private int yMapStartPosOnScreen;

    public GameJPanel(int screenWidth, int screenHeight) throws IOException, InvalidPropertiesException,
            InvalidConfigurationException, CannotCreateMapElementException, CannotFindPathFromEntranceToExitException {

        // create the map.
        map = new ZeldaMap(
                new ZeldaMapSetting(
                        new ZeldaMapProperties("/zelda.map.properties").loadProperties().checkProperties()),
                screenWidth,
                screenHeight);
        int nbTry = 0;
        boolean isMapGenerated = false;
        while (!isMapGenerated) {
            try {
                map.generateMap();
                isMapGenerated = true;
            } catch (CannotFindPathFromEntranceToExitException e) {
                if (nbTry++ >= MAX_NB_MAP_GENERATION) {
                    throw new CannotFindPathFromEntranceToExitException("not able to generate a viable map (i.e. with a "
                            + "passable path between the entrance and the exit) despite a certain number of generations ("
                            + MAX_NB_MAP_GENERATION + "): the proportion of immutable patterns/obstacles "
                            + "must be to high, please check the relative map.properties.");
                } else {
                    map.resetMap();
                }
            }
        }

        // create the list of sprites.
        spriteList = new SpriteList(
                new SpritesSetting(
                        new SpritesProperties("/zelda.sprites.properties").loadProperties().checkProperties()),
                map,
                screenWidth,
                screenHeight);
        spriteList.generateSprites();

        // create the main bomber and add it to the list of sprites.
        Tuple2<Integer, Integer> bbManInitialPosition = map.computeInitialBbManPosition();
        bomber = new BlueBomber(bbManInitialPosition.getFirst(), bbManInitialPosition.getSecond());
        addBomber(spriteList, bomber);

        // create an exit sign to help the player finding the exit.
        Tuple2<Integer, Integer> exitSignPosition = map.computeExitSignPosition();
        addSparkle(spriteList, new Sparkle(exitSignPosition.getFirst(), (exitSignPosition.getSecond())));

        // create the 3 first birds :)
        placeAGroupOfBird(spriteList, 3, -100, bbManInitialPosition.getSecond() + 225, DIRECTION_EAST, -8);

        // create a list to handle pressed keys.
        pressedKeyList = new ArrayList<>();
        pressedKeyList.add(0L); // add the "wait" action.

        setFocusable(true);
        addKeyListener(this);

        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Update the ZeldaMap start position on screen function of the Bomber map position.
     */
    private void updateMapStartPosOnScreen() {
        if (bomber.getXMap() < getWidth() / 2) {
            xMapStartPosOnScreen = 0;
        } else if (bomber.getXMap() > (map.getMapWidth() * IMAGE_SIZE) - (getWidth() / 2)) {
            xMapStartPosOnScreen = (map.getMapWidth() * IMAGE_SIZE) - getWidth();
        } else {
            xMapStartPosOnScreen = bomber.getXMap() - (getWidth() / 2);
        }
        if (bomber.getYMap() < getHeight() / 2) {
            yMapStartPosOnScreen = 0;
        } else if (bomber.getYMap() > (map.getMapHeight() * IMAGE_SIZE) - (getHeight() / 2)) {
            yMapStartPosOnScreen = (map.getMapHeight() * IMAGE_SIZE) - getHeight();
        } else {
            yMapStartPosOnScreen = bomber.getYMap() - (getHeight() / 2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        try {
            map.paintBuffer(g2d, xMapStartPosOnScreen, yMapStartPosOnScreen);
            spriteList.paintBuffer(g2d, xMapStartPosOnScreen, yMapStartPosOnScreen);
            TopBar.paintBuffer(g2d, map.getScreenWidth(), bomber, timer.getElapsedTime());

            if (gameStatus == GameStatus.STATUS_GAME_NEW) {
                SkinnedTextWithBG.paintBuffer(g2d, map.getScreenWidth(), map.getScreenHeight(), SkinnedTextWithBG.TEXT_NEW);
            } else if (gameStatus == GameStatus.STATUS_GAME_OVER) {
                SkinnedTextWithBG.paintBuffer(g2d, map.getScreenWidth(), map.getScreenHeight(), SkinnedTextWithBG.TEXT_GAME_OVER);
            } else if (gameStatus == GameStatus.STATUS_GAME_WIN) {
                SkinnedTextWithBG.paintBuffer(g2d, map.getScreenWidth(), map.getScreenHeight(), SkinnedTextWithBG.TEXT_WIN);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
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
        gameStatus = GameStatus.STATUS_GAME_NEW;
        while (true) {
            try {
                int pressedKey = pressedKeyList.get(pressedKeyList.size() - 1).intValue();
                if (pressedKey == KeyEvent.VK_ESCAPE) { // quit the game.
                    System.exit(0);

                } else if (gameStatus == GameStatus.STATUS_GAME_NEW) {
                    if (pressedKey == KeyEvent.VK_ENTER) {
                        gameStatus = GameStatus.STATUS_GAME_PLAY;
                        bomber.setInvincible();
                        timer.start();
                    }

                } else if (gameStatus == GameStatus.STATUS_GAME_PLAY) {
                    if (bomber.getBonus(BonusType.TYPE_BONUS_HEART) == 0) { // the bomber is dead.
                        timer.stop();
                        gameStatus = GameStatus.STATUS_GAME_OVER;

                    } else if (pressedKey == KeyEvent.VK_Q && // the bomber try to exit.
                            isNomadCrossingExit(map.getMapPointMatrix(),
                                    map.getMapWidth(),
                                    map.getMapHeight(),
                                    bomber.getXMap(),
                                    bomber.getYMap())) {
                        timer.stop();
                        gameStatus = GameStatus.STATUS_GAME_WIN;

                    } else {
                        spriteList.update(pressedKey);
                    }
                }
                updateMapStartPosOnScreen();

                // update the list order to handle sprites superposition.
                spriteList.sort((o1, o2) -> {
                    if (o1.getSpriteType() != o2.getSpriteType() &&
                            (o1.getSpriteType() == SpriteType.TYPE_SPRITE_FLYING_NOMAD ||
                                    o2.getSpriteType() == SpriteType.TYPE_SPRITE_FLYING_NOMAD)) {
                        return o1.getSpriteType() == SpriteType.TYPE_SPRITE_FLYING_NOMAD ? 1 : -1;
                    } else if (o1.getYMap() != o2.getYMap()) {
                        return o1.getYMap() > o2.getYMap() ? 1 : -1;
                    } else {
                        return 0;
                    }
                });
                repaint();
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace(System.out);
            }
        }
    }
}
