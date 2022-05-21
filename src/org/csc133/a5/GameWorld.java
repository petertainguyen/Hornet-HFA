package org.csc133.a5;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point2D;
import org.csc133.a5.gameobjects.*;
import org.csc133.a5.views.ControlCluster;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld {
    private River river;
    private Helicopter helicopter;
    private Helipad helipad;
    private ControlCluster controlCluster;
    private GameWorld gw;
    private NonPlayerHelicopter nonPlayerHelicopter;

    private static GameWorld instance;

    private CopyOnWriteArrayList<GameObject> gameObjects;
    private CopyOnWriteArrayList<Building> buildings;
    private CopyOnWriteArrayList<Fire> fires;
    private Dimension worldSize;
    private Fire fire;
    private Building building;
    private int fireUnits;
    private Random num = new Random();
    private int initSizeOfFire;
    private int areaOfFire;
    private int totalAreaOfFire;
    private int undamagedBuildingPercentage;
    private int totalDestroyed;
    private int tempFinancial;

    private GameWorld() {
    }

    public static GameWorld getInstance() {
        if (instance == null)
            instance = new GameWorld();
        return instance;
    }

    public void init() {
        river = new River(new Dimension(worldSize.getWidth(),
                worldSize.getHeight() / 6),
                new Point2D(worldSize.getWidth() / 2,
                        worldSize.getHeight() * 2 / 3));
        helipad = new Helipad(worldSize);
        helicopter = new Helicopter(worldSize);
        buildings = new CopyOnWriteArrayList<>();
        fires = new CopyOnWriteArrayList<>();
        controlCluster = new ControlCluster();
        nonPlayerHelicopter = new NonPlayerHelicopter(worldSize);

        fireUnits = 1000;
        initSizeOfFire = 0;
        totalAreaOfFire = 0;
        undamagedBuildingPercentage = 100;
        totalDestroyed = 0;
        tempFinancial = num.nextInt(2700) + 300;

        buildings.add(new Building
                (new Dimension(worldSize.getWidth() / 2
                        + worldSize.getWidth() / 4,
                        worldSize.getHeight() / 13),
                        new Point2D(worldSize.getWidth() / 2,
                                worldSize.getHeight() / 2
                                        + worldSize.getHeight() / 3),
                        (num.nextInt(900) + 100)));

        buildings.add(new Building
                (new Dimension(worldSize.getWidth() / 9,
                        worldSize.getHeight() / 2 -
                                worldSize.getHeight() / 12),
                        new Point2D(worldSize.getWidth() / 12,
                                worldSize.getHeight() / 4),
                        (num.nextInt(900) + 100)));

        buildings.add(new Building
                (new Dimension(worldSize.getWidth() / 9,
                        worldSize.getHeight() / 3),
                        new Point2D(worldSize.getWidth() / 3
                                + worldSize.getWidth() / 2,
                                worldSize.getHeight() / 2
                                        - worldSize.getHeight() / 4),
                        (num.nextInt(900) + 100)));

        // while (fireUnits <= 1000) {

        for(int i = 0; i < 2; i++) {
            for (Building building : buildings) {
                initSizeOfFire = num.nextInt(5) + 1;
                Fire tempFire = new Fire(initSizeOfFire);

                if (fireUnits > 0) {
                    building.setFireInBuilding(tempFire);
                    fires.add(tempFire);

                    areaOfFire = (int) (Math.pow(tempFire.getDimension().getWidth()
                            / 2, 2) * Math.PI);
                    totalAreaOfFire += areaOfFire;
                    fireUnits -= areaOfFire;
                }
            }
        }

        gameObjects = new CopyOnWriteArrayList<>();
        gameObjects.add(river);
        gameObjects.add(helipad);

        for (Building building : buildings) {
            gameObjects.add(building);
        }

        for (Fire fire : fires) {
            gameObjects.add(fire);
        }

        gameObjects.add(helicopter);
        gameObjects.add(nonPlayerHelicopter);
    }

    public int getSize(){
        return initSizeOfFire;
    }

    public void quit() {
        Display.getInstance().exitApplication();
    }

    public void drinkWater() {
        if ((helicopter.getMyTranslation().getTranslateY()
                - helicopter.getSize()
                < river.getMyTranslation().getTranslateY()
                + river.getDimension().getHeight() / 2)
                && helicopter.getMyTranslation().getTranslateY()
                > river.getMyTranslation().getTranslateY() / 2) {

            helicopter.fillWaterTank();
        }

    }

    public void fightFire() {
        for (Fire fire : fires) {
            if (helicopter.getMyTranslation().getTranslateX()
                    - helicopter.getSize()
                    < fire.getMyTranslation().getTranslateX() + fire.getSize()
                    && helicopter.getMyTranslation().getTranslateY()
                    - helicopter.getSize()
                    < fire.getMyTranslation().getTranslateY() + fire.getSize()
                    && helicopter.getMyTranslation().getTranslateX()
                    > fire.getMyTranslation().getTranslateX()
                    && helicopter.getMyTranslation().getTranslateY()
                    + helicopter.getSize()
                    > fire.getMyTranslation().getTranslateY()) {

                if (helicopter.getWaterTank() > 0) {
                    helicopter.depletesWater();
                    fire.shrink();
                }
            }
        }
    }

    public void left(){
        helicopter.rotateLeft();
    }

    public void right(){
        helicopter.rotateRight();
    }

    public void speedUp(){
        helicopter.speedIncrease();
    }

    public void speedDown(){
        helicopter.speedDown();
    }

    public void startOrStopEngine() {helicopter.startOrStopEngine();}

    public void tick(){
        // repaint();

        // growth rate timer for fire
        int random = num.nextInt(5);
        if (random == 3)
            spawnFire();

        //refillFuelTank();

        if (helicopter.getSpinSpeed() == 0)
            gameWinCondition();

        gameLossCondition();

        helicopter.updateLocalTransform();

        gameOverBuildingDestroyed();

        if (helicopter.getSpinSpeed() == 0) {
            controlCluster.changeEngineButtonToStart();
        } else
            controlCluster.changeEngineButtonToStop();

        //System.err.println(nonPlayerHelicopter.getMyTranslation());
        //System.err.println(helicopter.getMyTranslation());
    }

    public void spawnFire(){
        for(Fire fire : fires) {
            fire.grow();

            if (fire.getSize() < 1) {
                fires.remove(fire);
            }
        }
    }

    public void gameWinCondition(){
        Dialog gameWin = new Dialog();

        if (fires.size() == 0 && helicopter.getFuelTank() != 0 &&
                (helicopter.getMyTranslation().getTranslateX()
                        + helicopter.getSize()
                        < helipad.getMyTranslation().getTranslateX()
                        + helipad.getDimension().getWidth()
                        - helipad.getDimension().getWidth() / 2
                        && helicopter.getMyTranslation().getTranslateY()
                        - helicopter.getSize()
                        < helipad.getMyTranslation().getTranslateY()
                        + helipad.getDimension().getHeight()
                        && helicopter.getMyTranslation().getTranslateX()
                        > helipad.getMyTranslation().getTranslateX()
                        && helicopter.getMyTranslation().getTranslateY()
                        > helipad.getMyTranslation().getTranslateY()
                        - helipad.getDimension().getWidth() / 2)) {
            gameWin.show("YOU WON", "You put out all the fire!"
                            + "\nYour current fuel amount is: "
                            + helicopter.getFuelTank()
                            + "\n Your current undamaged building %: "
                            + undamagedBuildingPercentage,
                    new Command("Play again?") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            init();
                        }
                    }, new Command ("Some of time") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            quit();
                        }
                    } );
        }
    }

    public void refillFuelTank(){
        if ((helicopter.getMyTranslation().getTranslateX()
                + helicopter.getSize()
                < helipad.getMyTranslation().getTranslateX()
                + helipad.getDimension().getWidth()
                && helicopter.getMyTranslation().getTranslateY()
                + helicopter.getSize()
                < helipad.getMyTranslation().getTranslateY()
                + helipad.getDimension().getHeight()
                && helicopter.getMyTranslation().getTranslateX()
                > helipad.getMyTranslation().getTranslateX()
                && helicopter.getMyTranslation().getTranslateY()
                > helipad.getMyTranslation().getTranslateY())) {

            helicopter.refillFuelTank();
        }
    }

    public void gameLossCondition(){
        Dialog gameLoss = new Dialog();

        if (helicopter.getFuelTank() <= 0) {
            gameLoss.show("Game Over", "You ran out of fuel.",
                    new Command("Play again?") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            init();
                        }
                    }, new Command ("Some of time") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            quit();
                        }
                    } );
        }
    }

    public void gameOverBuildingDestroyed(){
        Dialog buildingDestroyed = new Dialog();

        // if (undamagedBuilding <= 0) {

        if (totalDestroyed >= 100) {

            buildingDestroyed.show("Game Over",
                    "All of your buildings are destroyed. " +
                            "\nBetter luck next time!",
                    new Command("Play again?") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            init();
                        }
                    }, new Command ("Some of time") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            quit();
                        }
                    } );
        }
    }

    public CopyOnWriteArrayList<GameObject> getGameObjectCollection() {
        return gameObjects;
    }

    public String getHeading() {
        return String.valueOf(helicopter.getStartingAngle());
    }

    public String getSpeed() {
        return String.valueOf(helicopter.getSpeed());
    }

    public String getFuel() {
        return String.valueOf(helicopter.getFuelTank());
    }

    public String getNumberOfFiresBurning() {
        return String.valueOf(fires.size());
    }

    public String getTotalFireSize() {
        // 1000 max units of fire
        int sizeFires = 0;

        for (Fire fire : fires) {
            sizeFires += Math.pow(fire.getDimension().getWidth()/2, 2)
                    * Math.PI;
        }

        return String.valueOf(sizeFires);
    }

    public CopyOnWriteArrayList<Fire> getFires() {
        return fires;
    }

    public String getPercentDestroyed() {
        int sizeFires = 0;
        int sizeBuildings = 0;

        for (Fire fire : fires) {
            sizeFires += Math.pow(fire.getDimension().getWidth()/2, 2)
                    * Math.PI;
        }

        for(Building building : buildings) {
            sizeBuildings += building.getDimension().getWidth()
                * building.getDimension().getHeight();
        }

        totalDestroyed = sizeFires * 40 / sizeBuildings;

        undamagedBuildingPercentage -= totalDestroyed;

        return String.valueOf(totalDestroyed + "%");
    }

    public Dimension getWorldSize() {
        return worldSize;
    }

    public String getCurrentFinancialLoss() {
        int temp = 0;

        for (Fire fire : fires) {
            temp += tempFinancial * totalDestroyed / 100;
            //tempFinancial -= Math.pow(fire.getDimension().getWidth()/2, 2)
            // * Math.PI;
        }
        //sum of each building's (value * percent destroyed/100)

        return String.valueOf(temp);
    }

    public void setDimension(Dimension worldSize) {
        this.worldSize = worldSize;
    }

    public void accelerate() {
        helicopter.accelerate();
    }
}
