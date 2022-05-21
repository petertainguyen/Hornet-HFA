package org.csc133.a5.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a5.gameobjects.parts.Arc;
import org.csc133.a5.gameobjects.parts.FilledRectangle;
import org.csc133.a5.gameobjects.parts.Line;
import org.csc133.a5.gameobjects.parts.Rectangle;
import org.csc133.a5.interfaces.Steerable;


import java.util.ArrayList;

import static com.codename1.ui.CN.*;

public class Helicopter extends Movable implements Steerable {
    private FlightControl flightControl;

    private int fuelTank;
    private int waterTank;
    private int spinSpeed;

    final static int BUBBLE_RADIUS = 60;
    final static int ENGINE_BLOCK_WIDTH = (int)(BUBBLE_RADIUS * 1.8);
    final static int ENGINE_BLOCK_HEIGHT = ENGINE_BLOCK_WIDTH / 3;
    final static int BLADE_LENGTH = BUBBLE_RADIUS * 5;
    final static int BLADE_WIDTH = 25;

    private ArrayList<GameObject> heloParts;
    private HeloBlade heloBlade;
    private Helipad helipad;

    public Helicopter(Dimension worldSize) {
        super();

        waterTank = 0;
        fuelTank = 100000;
        spinSpeed = 0;

        helipad = new Helipad(this.worldSize = worldSize);

        super.setSpeed(0);
        super.setStartingAngle(0);

        this.worldSize = worldSize;
        this.color = ColorUtil.YELLOW;
        this.dimension = new Dimension(BUBBLE_RADIUS * 3,
                BUBBLE_RADIUS * 3);

        myTranslation.translate(worldSize.getWidth()/2, 200);

        heloParts = new ArrayList<>();
        heloParts.add(new HeloBladeShaft());
        heloParts.add(new HeloEngineBlock());
        heloParts.add(new HeloLeftLegStand());
        heloParts.add(new HeloRightLegStand());
        heloParts.add(new HeloTail());
        heloParts.add(new HeloTailBeam());

        for(int i = 0; i < 4; i++) {
            heloParts.add(new HeloLegConnector(i));
        }

        heloParts.add(new HeloTailEnd());
        heloParts.add(new HeloTailBlade());
        heloParts.add(new HeloTailPatternLine1());
        heloParts.add(new HeloTailPatternLine2());
        heloParts.add(new HeloBubble());

        heloBlade = new HeloBlade();
        heloParts.add(heloBlade);

        heloState = new Off();
    }

    @Override
    public void updateLocalTransform() {
        heloState.updateLocalTransforms();
    }

    //``````````````````````````````````````````````````````````````````````````
    private static class HeloBubble extends Arc {
        public HeloBubble() {
            super(ColorUtil.YELLOW,
                    (int)2.5 * BUBBLE_RADIUS,
                    (int)2.5 * BUBBLE_RADIUS,
                    -190,
                    45,
                    1,1,
                    -120,
                    -45,270);
        }
    }

    //``````````````````````````````````````````````````````````````````````````
    private static class HeloEngineBlock extends Rectangle {
        public HeloEngineBlock() {
            super(ColorUtil.YELLOW,
                    ENGINE_BLOCK_WIDTH,
                    ENGINE_BLOCK_HEIGHT,
                    -ENGINE_BLOCK_WIDTH,
                    (float)(-ENGINE_BLOCK_WIDTH * 3.525),
                    1,1,0);
        }
    }

    //``````````````````````````````````````````````````````````````````````````
    private static class HeloBlade extends Rectangle{
        public HeloBlade() {
            super(ColorUtil.WHITE,
                    BLADE_LENGTH, BLADE_WIDTH,
                    (float)(-ENGINE_BLOCK_HEIGHT * 7.75),
                    -ENGINE_BLOCK_HEIGHT * 17,
                    1,1,
                    42);
        }

        public void updateLocalTransforms(double rotationSpeed) {
            this.rotate(rotationSpeed);
        }
    }

    //``````````````````````````````````````````````````````````````````````````
    private static class HeloBladeShaft extends Arc {
        public HeloBladeShaft() {
            super(ColorUtil.LTGRAY,
                    2 * Helicopter.BLADE_WIDTH / 3,
                    2 * Helicopter.BLADE_WIDTH / 3,
                    0,0,
                    1,1,
                    0,
                    0,360);

            //A3
            /*
            super(ColorUtil.LTGRAY,
                    2 * Helicopter.BLADE_WIDTH / 3,
                    2 * Helicopter.BLADE_WIDTH / 3,
                    0,0,
                    1,1,
                    0,
                    0,360);
             */
        }
    }

    //`````````````````````````````````````````````````````````````````````````
    private static class HeloLeftLegStand extends Rectangle {
        public HeloLeftLegStand() {
            super(ColorUtil.YELLOW,
                    ENGINE_BLOCK_HEIGHT / 2,
                    (int)(ENGINE_BLOCK_WIDTH + .5 * ENGINE_BLOCK_WIDTH),
                    (float)(-BUBBLE_RADIUS * 2.4 + 80),
                    -Helicopter.ENGINE_BLOCK_HEIGHT * 4
                            -Helicopter.ENGINE_BLOCK_HEIGHT / 2,
                    1,1,
                    0);
        }
    }

    //``````````````````````````````````````````````````````````````````````````
    private static class HeloRightLegStand extends Rectangle {
        public HeloRightLegStand() {
            super(ColorUtil.YELLOW,
                    ENGINE_BLOCK_HEIGHT / 2,
                    (int)(ENGINE_BLOCK_WIDTH + .5 * ENGINE_BLOCK_WIDTH),
                    (float)(-BUBBLE_RADIUS * 2.7), // left.right
                    (float)(-Helicopter.ENGINE_BLOCK_HEIGHT * 11.8),
                    1,1,
                    0);
        }
    }

    //`````````````````````````````````````````````````````````````````````````
    private static class HeloTail extends Rectangle {
        public HeloTail() {
            super(ColorUtil.YELLOW,
                    (int)(ENGINE_BLOCK_WIDTH / 3.5),
                    (ENGINE_BLOCK_HEIGHT * 4),
                    (int)(ENGINE_BLOCK_HEIGHT / 2 + ENGINE_BLOCK_HEIGHT * .2),
                    -Helicopter.ENGINE_BLOCK_HEIGHT + 2,
                    1,1,
                    0);
        }
    }

    //`````````````````````````````````````````````````````````````````````````
    private static class HeloTailBeam extends FilledRectangle {
        public HeloTailBeam() {
            super(ColorUtil.GRAY,
                    ENGINE_BLOCK_WIDTH / 8,
                    ENGINE_BLOCK_HEIGHT * 4,
                    (float)(-ENGINE_BLOCK_HEIGHT * 1.3125),
                    (float)(-ENGINE_BLOCK_WIDTH * 3.1),
                    1,1,
                    0);
        }
    }

    //`````````````````````````````````````````````````````````````````````````
    private static class HeloLegConnector extends Movable{
        public HeloLegConnector(int connectorNum) {
            setColor(ColorUtil.GRAY);
            setDimension(new Dimension((int)(ENGINE_BLOCK_HEIGHT * 1.1),
                    ENGINE_BLOCK_HEIGHT / 3));

            if (connectorNum == 0) {
                translate(-BUBBLE_RADIUS * .85,
                        -ENGINE_BLOCK_WIDTH * .55);
                scale(1, 1);
                rotate(0);
            } else if (connectorNum ==1) {
                translate(-BUBBLE_RADIUS * 2.2,
                        (float)(-ENGINE_BLOCK_HEIGHT * 4.85));
                scale(1, 1);
                rotate(0);
            } else if (connectorNum == 2) {
                translate(-BUBBLE_RADIUS * .85,
                        (float)(-ENGINE_BLOCK_WIDTH * 1.25));
                scale(1, 1);
                rotate(0);
            } else {
                translate(-BUBBLE_RADIUS * 2.2,
                        (float)(-ENGINE_BLOCK_WIDTH * .925));
                scale(1, 1);
                rotate(0);
            }
        }

        public void localTransform(Graphics g) {
            Transform transform = Transform.makeIdentity();
            g.getTransform(transform);

            transform.concatenate(myTranslation);
            transform.concatenate(myRotation);
            transform.concatenate(myScale);

            g.setTransform(transform);
        }

        public void undoLocalTransform(Graphics g) {
            Transform transform = Transform.makeIdentity();

            g.getTransform(transform);

            transform.translate(-myTranslation.getTranslateX(),
                    -myTranslation.getTranslateY());
            transform.scale(-myScale.getScaleX(),-myScale.getScaleY());

            g.setTransform(transform);
        }

        @Override
        public void localDraw(Graphics g, Point containerOrigin,
                              Point screenOrigin) {
            g.setColor(getColor());

            localTransform(g);

            g.fillRect(0,0,
                    getWidth(),getHeight());

            undoLocalTransform(g);
        }

        @Override
        public void updateLocalTransform() {
        }
    }

    //``````````````````````````````````````````````````````````````````````````
    private static class HeloTailPatternLine1 extends Line {
        public HeloTailPatternLine1(){
            super(ColorUtil.YELLOW,
                    (int)(ENGINE_BLOCK_WIDTH / 5.5),
                    (int)(ENGINE_BLOCK_HEIGHT * 2.7),
                    ENGINE_BLOCK_HEIGHT / 2 + 11,
                    (float)(ENGINE_BLOCK_HEIGHT * .1) - 11,
                    1,1,
                    0);
        }
    }

    //``````````````````````````````````````````````````````````````````````````
    private static class HeloTailPatternLine2 extends Line {
        public HeloTailPatternLine2(){
            super(ColorUtil.YELLOW,
                    (int)(ENGINE_BLOCK_WIDTH / 2.575),
                    (int)(ENGINE_BLOCK_HEIGHT * 2.5),
                    ENGINE_BLOCK_HEIGHT /4 + 6,
                    (float)(-Helicopter.ENGINE_BLOCK_HEIGHT * 11.4),
                    1,1,
                    120);
        }
    }

    //``````````````````````````````````````````````````````````````````````````
    private static class HeloTailEnd extends FilledRectangle {
        public HeloTailEnd(){
            super(ColorUtil.GRAY,
                    (int)(ENGINE_BLOCK_HEIGHT * 1.25),
                    (int)(BLADE_WIDTH * .7),
                    BLADE_WIDTH,
                    (float)(ENGINE_BLOCK_HEIGHT * 3.08),
                    1,1,
                    (float)0);
        }
    }

    //``````````````````````````````````````````````````````````````````````````
    private static class HeloTailBlade extends Rectangle {
        public HeloTailBlade(){
            super(ColorUtil.LTGRAY,
                    BLADE_WIDTH / 3,
                    BLADE_LENGTH / 6,
                    (float)(-BLADE_WIDTH * 3.25),
                    (float)(-BLADE_LENGTH * 1.76),
                    1,1,
                    (float)0);
        }
    }

    private Helicopter getHelo() {
        return this;
    }

    //``````````````````````````````````````````````````````````````````````````
    //Helicopter State Pattern
    //
    HeloState heloState;

    private void changeState(HeloState heloState) {
        this.heloState = heloState;

        //System.err.println(this.heloState.getClass().getCanonicalName());
    }

    private HeloState getState() {
        return heloState;
    }

    private Boolean isOnTheHelipad() {
        if (getMyTranslation().getTranslateX()
                + getSize()
                < helipad.getMyTranslation().getTranslateX()
                + helipad.getDimension().getWidth()
                - helipad.getDimension().getWidth() / 2
                && getMyTranslation().getTranslateY()
                - getSize()
                < helipad.getMyTranslation().getTranslateY()
                + helipad.getDimension().getHeight()
                && getMyTranslation().getTranslateX()
                > helipad.getMyTranslation().getTranslateX()
                && getMyTranslation().getTranslateY()
                > helipad.getMyTranslation().getTranslateY()
                - helipad.getDimension().getWidth() / 2) {
            return true;
        }   else {
            return false;
        }
    }


    //``````````````````````````````````````````````````````````````````````````
    private abstract class HeloState {
        Helicopter getHelo() {
            return Helicopter.this;
        }

        public void accelerate(){}

        public void startOrStopEngine(){
        }

        public boolean hasLandedAt(){
            return false;
        }

        public void updateLocalTransforms() {
        }
    }

    //``````````````````````````````````````````````````````````````````````````
    private class Off extends HeloState {

        @Override
        public void startOrStopEngine() {
            getHelo().changeState(new Starting());

            //System.err.println("Off State;");
        }

        @Override
        public boolean hasLandedAt() {
            return true;
        }

        public void updateLocalTransforms() {
            //System.err.println("Off State Update");
            //System.err.println(this.getClass().getCanonicalName());
        }
    }

    //``````````````````````````````````````````````````````````````````````````
    private class Starting extends HeloState {
        @Override
        public void startOrStopEngine() {
            getHelo().changeState(new Stopping());

            //System.err.println("Start State;");
        }

        public void updateLocalTransforms() {
            if (spinSpeed <= 20)
                spinSpeed += 1;
            heloBlade.updateLocalTransforms(spinSpeed);

            if (spinSpeed == 20)
                getHelo().changeState(new Ready());

            useFuel();

            //System.err.println("Starting State Update");
        }
    }

    //``````````````````````````````````````````````````````````````````````````
    private class Stopping extends HeloState {
        @Override
        public void startOrStopEngine() {
            getHelo().changeState(new Starting());

            //System.err.println("Stopping State;");
        }

        public void updateLocalTransforms() {
            if (spinSpeed > 0)
                spinSpeed -= 1;
            heloBlade.updateLocalTransforms(spinSpeed);

            if (spinSpeed == 0)
                getHelo().changeState(new Off());

            //System.err.println("Stopping State Update");
        }
    }

    //``````````````````````````````````````````````````````````````````````````
    private class Ready extends HeloState {
        @Override
        public void startOrStopEngine() {
            getHelo().changeState(new Stopping());

            //System.err.println("Ready State;");
        }

        public void accelerate(){
            speedIncrease();
        }

        public void updateLocalTransforms() {
            heloBlade.updateLocalTransforms(spinSpeed);
            useFuel();
            move();

            //System.err.println("Ready0 State Update");
        }
    }

    public int getSize() {
        return this.dimension.getWidth();
    }

    public int getWaterTank(){
        return waterTank;
    }

    public int getFuelTank() {
        return fuelTank;
    }

    public int depletesWater() {
        return waterTank -= 100;
    }

    public void refillFuelTank() {
        if (fuelTank <= 24999) {
            fuelTank += 10;
        }
    }

    public void rotateRight() {
        startingAngle = startingAngle + 15;

        if (startingAngle > 360) {
            startingAngle -= 360;
        }

        myRotation.rotate((float)Math.toRadians(-15.f),0,-1479);
    }

    public void rotateLeft() {
        startingAngle = startingAngle - 15;

        if (startingAngle < 0) {
            startingAngle += 360;
        }

        myRotation.rotate((float)Math.toRadians(15.f),0,-1479);
    }

    public void useFuel() {
        fuelTank = fuelTank - ((int)Math.pow(speed,2) + 5) ;
    }

    public void fillWaterTank(){
        if (waterTank < 1000 && speed < 2) {
            waterTank += 100;
        }
    }

    public int getStartingAngle(){
        return super.startingAngle;
    }

    public int getSpeed() {
        return super.speed;
    }

    public void speedDown() {
        super.speedDown();
    }

    public void speedIncrease() {
        super.speedIncrease();
    }

    public void move() {
        super.move();
    }

    public void setFlightControl(FlightControl flightControl) {
        this.flightControl = flightControl;
    }

    public double headingAngle(){
        return super.headingAngle();
    }

    @Override
    public void localDraw(Graphics g, Point containerOrigin,
                          Point screenOrigin) {
        g.setColor(color);

        int x = -dimension.getWidth() / 2;
        int y = -dimension.getHeight() / 2;

        g.setFont(Font.createSystemFont(FACE_SYSTEM,STYLE_PLAIN,SIZE_SMALL));
        g.drawString("F : " + fuelTank,
                (int)(x + ENGINE_BLOCK_WIDTH * 2.3),
                y + ENGINE_BLOCK_HEIGHT * 3);
        g.drawString("W : " + waterTank,
                (int)(x + ENGINE_BLOCK_WIDTH * 2.3),
                y + ENGINE_BLOCK_HEIGHT * 3 + 40);

        for(GameObject go : heloParts) {
            go.localDraw(g,  containerOrigin, screenOrigin);
        }
    }

    public void startOrStopEngine() {
        heloState.startOrStopEngine();
    }

    public void accelerate() {
        heloState.accelerate();
    }

    public int getSpinSpeed() {return spinSpeed;}

    public void getInstance() {
        getInstance();
    }

    public ArrayList<GameObject> getHeloParts(){
        return heloParts;
    }
}
