package org.csc133.a5.views;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a5.GameWorld;
import org.csc133.a5.gameobjects.*;

import java.util.concurrent.CopyOnWriteArrayList;


public class MapView extends Container {
    private GameWorld gw;
    private Helicopter helicopter;
    private NonPlayerHelicopter nonPlayerHelicopter;
    private FlightControl flightControl;

    public MapView() {
        gw = GameWorld.getInstance();

        //flightPath = new FlightPath();
        flightControl = new FlightControl(nonPlayerHelicopter);

        //nonPlayerHelicopter = new NonPlayerHelicopter();
    }

    @Override
    public void laidOut(){
        gw.setDimension(new Dimension(this.getWidth(), this.getHeight()));
    }

    public void displayTransform(Graphics g) {
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(getAbsoluteX(),getAbsoluteY());

        gXform.translate(0,getHeight());
        gXform.scale(1f,-1f);

        gXform.translate(-getAbsoluteX(),-getAbsoluteY());
        g.setTransform(gXform);
    }

    private Transform buildWorldToNDXForm(float windowWidth,
                                          float windowHeight,
                                          float windowLeft,
                                          float windowBottom) {
        Transform tmpXform = Transform.makeIdentity();
        tmpXform.scale((1/windowWidth), (1/windowHeight));
        tmpXform.translate(-windowLeft,windowBottom);
        return tmpXform;
    }

    private Transform buildNDToDisplayXform(float displayWidth,
                                            float displayHeight) {
        Transform tmpXform = Transform.makeIdentity();
        tmpXform.translate(0,displayHeight);
        tmpXform.scale(displayWidth,-displayHeight);
        return tmpXform;
    }

    private void setupVTM(Graphics g){
        Transform worldToND, ndToDisplay, theVTM;
        float windowLeft, windowRight, windowBottom,windowTop;

        windowLeft = windowBottom = 0;
        windowRight = this.getWidth();
        windowTop = this.getHeight();

        float windowHeight = windowTop - windowBottom;
        float windowWidth = windowRight - windowLeft;

        worldToND = buildWorldToNDXForm(windowWidth,windowHeight,
                windowLeft,windowBottom);
        ndToDisplay = buildNDToDisplayXform(this.getWidth(),this.getHeight());
        theVTM = ndToDisplay.copy();
        theVTM.concatenate(worldToND);

        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(0,-getY());
        gXform.translate(getAbsoluteX(),getAbsoluteY());
        gXform.concatenate(theVTM);
        gXform.translate(-getAbsoluteX(),-getAbsoluteY());
        g.setTransform(gXform);
    }

    private Transform getInverseVTM() {
        Transform inverseVTM = Transform.makeIdentity();

        try {
            getInverseVTM().getInverse(inverseVTM);
        } catch (Transform.NotInvertibleException e){
            e.printStackTrace();
        }

        return inverseVTM;
    }

    private Point2D transformPoint2D(Transform t, Point2D p) {
        float[] in = new float[2];
        float[] out = new float[2];
        in[0] = (float)p.getX();
        in[1] = (float)p.getY();
        t.transformPoint(in, out);
        return new Point2D(out[0],out[1]);
    }

    @Override
    public void pointerPressed(int x, int y){
        x = x - getAbsoluteX();
        y = Math.abs((int)(y - getAbsoluteY() * 6.345));

        Point2D selectedPoint = new Point2D(x,y);

        for (Fire fires : gw.getFires()) {
            if (fires.contains(selectedPoint) && !fires.isSelected()){
                fires.setSelected(true);

                flightControl.getPrimary().setTail(new Point2D(
                        fires.getMyTranslation().getTranslateX(),
                        -(int)(fires.getMyTranslation().getTranslateY())));
            } else {
                fires.setSelected(false);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        setupVTM(g);

        Point containerOrigin = new Point(this.getX(),this.getY());
        Point screenOrigin = new Point(this.getWidth(),this.getHeight());

        for(GameObject go: gw.getGameObjectCollection())
            go.draw(g,containerOrigin,screenOrigin);

        flightControl.getPrimary().draw(g,containerOrigin,screenOrigin);
        flightControl.getCorrection().draw(g,containerOrigin,screenOrigin);

        g.resetAffine();
    }

    public void updateLocalTransforms() {
        for(GameObject go: gw.getGameObjectCollection())
            go.updateLocalTransform();
    }
}
