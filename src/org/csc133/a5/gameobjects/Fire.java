package org.csc133.a5.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a5.interfaces.Selectable;

import java.util.Random;

import static com.codename1.ui.CN.*;

public class Fire extends Fixed implements Selectable {
    private Random num = new Random();
    private int currentSizeOfFire;
    private int growth;
    private Boolean startCheck;
    private int maxSizeOfFire;
    private Transform myTranslation;
    private boolean selected;

    public Fire(int fireSize) {
        super();

        int currentSizeOfFire = fireSize;
        startCheck = false;

        this.color = ColorUtil.MAGENTA;
        this.dimension = new Dimension(fireSize,fireSize);

        myTranslation = Transform.makeIdentity();

        selected = false;
    }

    public Point2D getLocation(){
        return super.getLocation();
    }

    public void grow(){
        growth = (num.nextInt(1) + 1) * 2;

        if (startCheck == true) {
            currentSizeOfFire += growth;

            myTranslation.translate(myTranslation.getTranslateX()
                            - growth / 2,
                    myTranslation.getTranslateY() - growth / 2);
            dimension.setHeight(currentSizeOfFire);
            dimension.setWidth(currentSizeOfFire);
        }
    }

    public void start() {
        startCheck = true;
    }

    public void shrink(){
        currentSizeOfFire -= 100;
        myTranslation.translate(myTranslation.getTranslateX() + 50,
                myTranslation.getTranslateY() + 50);
    }

    public int getSize() {
        return currentSizeOfFire;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    /*
    //``````````````````````````````````````````````````````````````````````````
    // Fire State Pattern
    FireState fireState;

    private void changeState(FireState fireState) {
        this.fireState = fireState;
    }

    private FireState getFireState(){return fireState;}

    private abstract class FireState {
        Fire getFire() {return Fire.this;}

        public void stateOfFire(){
        }

        public boolean hasBeenPutOut() {return false;}

        public void updateLocalTransforms(){
        }
    }

    private class UnStarted extends FireState {
        @Override
        public void stateOfFire() {
            getFire().changeState(new Burning());
        }

        public void updateLocalTransforms(){
        }
    }

    private class Burning extends FireState{
        @Override
        public void stateOfFire(){
            getFire().changeState(new Extinguished());
        }

        public void updateLocalTransform(){
        }
    }

    private class Extinguished extends FireState{
        @Override
        public void stateOfFire(){
            getFire().changeState(new UnStarted());
        }

        public void updateLocalTransforms(){
        }
    }

    public void stateOfFire() {fireState.stateOfFire();}
    */

    @Override
    public void updateLocalTransform() {
        //fireState.updateLocalTransforms();
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    private double distanceBetween(Point2D a, Point2D b) {
        double distanceX = a.getX() - b.getX();
        double distanceY = a.getY() - b.getY();
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    @Override
    public boolean contains(Point2D point) {
        return distanceBetween(new Point2D(
                getMyTranslation().getTranslateX(),
                getMyTranslation().getTranslateY()), point) <= getWidth() / 2;
    }

    @Override
    public void localDraw(Graphics g, Point containerOrigin,
                          Point screenOrigin) {
        g.setColor(getColor());

        int x = -dimension.getWidth()/2;
        int y = -dimension.getHeight()/2;
        int w = dimension.getWidth();
        int h = dimension.getHeight();

        if (currentSizeOfFire > 0) {

            g.fillArc(x, y, w, h, 0, 360);

            g.setFont(Font.createSystemFont(FACE_MONOSPACE, STYLE_PLAIN,
                    SIZE_SMALL));
            g.drawString("" + currentSizeOfFire,
                    (int) myTranslation.getTranslateX()
                            + dimension.getWidth()
                            + containerOrigin.getX(),
                    (int) myTranslation.getTranslateY()
                            + dimension.getHeight()
                            + containerOrigin.getY());

            //System.err.println("Fire X: " + x);
            //System.err.println("Fire Y: " + y);
        }
    }
}