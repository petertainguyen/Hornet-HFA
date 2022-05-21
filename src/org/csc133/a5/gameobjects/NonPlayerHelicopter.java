package org.csc133.a5.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a5.GameWorld;

import java.util.ArrayList;

import static com.codename1.ui.CN.*;

public class NonPlayerHelicopter extends Helicopter{
    private GameWorld gw;
    private Helicopter helicopter;
    private FlightControl flightControl;

    private int fuelTank;
    private int waterTank;
    private int spinSpeed;

    private double movementIncrement = 0;

    private ArrayList<GameObject> heloParts;

    public NonPlayerHelicopter(Dimension worldSize) {
        super(worldSize);

        helicopter = new Helicopter(worldSize);

        waterTank = 0;
        fuelTank = 100000;
        spinSpeed = 0;

        super.setSpeed(0);
        super.setStartingAngle(0);

        this.worldSize = worldSize;
        this.color = ColorUtil.YELLOW;
        this.dimension = new Dimension(BUBBLE_RADIUS * 3,
                BUBBLE_RADIUS * 3);

        this.translate(0,0);

        heloParts = helicopter.getHeloParts();

        setFlightControl(flightControl);
    }

    @Override
    public void updateLocalTransform() {

        Point2D currentPoint = new Point2D(myTranslation.getTranslateX(),
                myTranslation.getTranslateY());

        //flightControl.moveAlongAPath(currentPoint);

        /*
        int curve = 1;

        if (curve == 1) {
            Point2D currentPoint = new Point2D(myTranslation.getTranslateX(),
                    myTranslation.getTranslateY());

            Point2D newPoint = flightControl.getPrimary().evaluateCurve(
                    movementIncrement,
                    flightControl.getPrimary().getPadToRiverControlPoints());

            double translateX = newPoint.getX() - currentPoint.getX();
            double translateY = newPoint.getY() - (newPoint.getY() * 2)
                    - currentPoint.getY();

            this.translate(translateX, translateY);

            if (movementIncrement <= 1)
                movementIncrement = movementIncrement + 0.005;
        }

        if (myTranslation.getTranslateX() == 1025
                && myTranslation.getTranslateY() == 825) {
            if (waterTank < 1000)
                waterTank += 100;

            if (waterTank == 1000 && curve < 2)
                curve += 1;
        }

        if (curve == 2) {
            Point2D currentPoint = new Point2D(myTranslation.getTranslateX(),
                    myTranslation.getTranslateY());

            Point2D newPoint = flightControl.getPrimary().evaluateCurve(
                    movementIncrement,
                    flightControl.getPrimary().getRiverToFireControlPoints());

            double translateX = newPoint.getX() - currentPoint.getX();
            double translateY = newPoint.getY() - (newPoint.getY() * 2)
                    - currentPoint.getY();

            this.translate(translateX, translateY);

            if (movementIncrement <= 1)
                movementIncrement = movementIncrement + 0.01;
        }

        if (myTranslation.getTranslateX() == 1625
                && myTranslation.getTranslateY() == 325) {
            if (waterTank > 0)
                waterTank -= 100;
            if (curve < 3 && waterTank == 0)
                curve += 1;
        }

        if (curve == 3) {
            Point2D currentPoint = new Point2D(myTranslation.getTranslateX(),
                    myTranslation.getTranslateY());

            Point2D newPoint = flightControl.getPrimary().evaluateCurve(
                    movementIncrement,
                    flightControl.getPrimary().getFireToRiverControlPoints());

            double translateX = newPoint.getX() - currentPoint.getX();
            double translateY = newPoint.getY() - (newPoint.getY() * 2)
                    - currentPoint.getY();

            this.translate(translateX, translateY);

            if (movementIncrement <= 1)
                movementIncrement = movementIncrement + 0.01;

            if (curve > 1)
                curve -= 1;
        }

        if(spinSpeed <= 20)
            spinSpeed += 1;

        //myRotation.rotate((float)Math.toRadians(flightPath.get.f),0,-1479);
        heloParts.get(15).myRotation.rotate(
                (float)Math.toRadians(spinSpeed),0,0);

        fuelTank -= 10;
        */
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
}
