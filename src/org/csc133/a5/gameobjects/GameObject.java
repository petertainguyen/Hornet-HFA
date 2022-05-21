package org.csc133.a5.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a5.interfaces.Drawable;

public abstract class GameObject implements Drawable {
    private FlightPath flightPath;
    protected int color;
    Point2D location;
    protected Dimension dimension;
    Dimension worldSize;
    protected Transform myTranslation, myRotation, myScale;
    private Transform gOrigXform;
    private int angle;

    public GameObject() {
        angle = 0;
        myTranslation = Transform.makeIdentity();
        myRotation = Transform.makeRotation(angle, 0,0);
        myScale = Transform.makeIdentity();
    }

    protected void localTransforms(Transform gxForm) {
        gxForm.translate(myTranslation.getTranslateX(),
                myTranslation.getTranslateY());
        gxForm.concatenate(myRotation);
        gxForm.scale(myScale.getScaleX(), myScale.getScaleY());
    }

    protected Transform preLTTransform(Graphics g, Point originScreen) {
        Transform gXform = Transform.makeIdentity();

        g.getTransform(gXform);
        gOrigXform = gXform.copy();

        gXform.translate(originScreen.getX(), originScreen.getY());

        return gXform;
    }

    protected void postLTTransforms(Graphics g, Point originScreen,
                                    Transform gXform) {
        gXform.translate(-originScreen.getX(), -originScreen.getY());
        g.setTransform(gXform);
    }

    protected void restoreOriginalTransforms(Graphics g) {
        g.setTransform(gOrigXform);
    }

    protected void containerTranslate(Graphics g, Point containerOrigin) {
        Transform gxForm = Transform.makeIdentity();
        g.getTransform(gxForm);
        gxForm.translate(containerOrigin.getX(),containerOrigin.getY());
        g.setTransform(gxForm);
    }

    protected void cn1ForwardPrimitiveTranslate(Graphics g,
                                                Dimension pDimension) {
        Transform gxForm = Transform.makeIdentity();
        g.getTransform(gxForm);
        gxForm.translate(pDimension.getWidth()/2,
                pDimension.getHeight()/2);
        g.setTransform(gxForm);
    }

    protected void cn1ReversePrimitiveTranslate(Graphics g,
                                                Dimension pDimension) {
        Transform gxForm = Transform.makeIdentity();
        g.getTransform(gxForm);
        gxForm.translate(pDimension.getWidth()/2,
                pDimension.getHeight()/2);
        g.setTransform(gxForm);
    }

    public void rotate(double degrees) {
        myRotation.rotate((float)Math.toRadians(degrees), 0, 0);
    }

    public void scale(double scaleX, double scaleY) {
        myScale.scale((float)scaleX, (float)scaleY);
    }

    public void translate(double translateX, double translateY) {
        myTranslation.translate((float)translateX, (float)translateY);
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Dimension getWorldSize() {
        return worldSize;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public int getWidth() {
        return this.dimension.getWidth();
    }

    public int getHeight() {
        return this.dimension.getHeight();
    }

    public Transform getMyTranslation() {
        return myTranslation;
    }

    protected void transformText(Graphics g) {
        Transform localTransform = Transform.makeIdentity();
        Transform inverseRotation = Transform.makeIdentity();

        try {
            myRotation.getInverse(inverseRotation);
        } catch (Transform.NotInvertibleException e) {
        }

        localTransform.translate(localTransform.getTranslateX(),
                localTransform.getTranslateY());
        localTransform.concatenate(inverseRotation);
        localTransform.scale(1, -1);
        localTransform.translate(-localTransform.getTranslateX(),
                localTransform.getTranslateY());

        localTransform.translate(0,getHeight());
        g.setTransform(localTransform);
    }

    public abstract void updateLocalTransform();

    public void draw(Graphics g, Point containerOrigin, Point screenOrigin) {
        g.setColor(color);

        Transform localTransform = Transform.makeIdentity();

        localTransform.scale(1,-1);

        localTransform.concatenate(myTranslation);
        localTransform.concatenate(myRotation);
        localTransform.concatenate(myScale);

        localTransform.scale(1,-1);

        localTransform.translate(0,screenOrigin.getY());
        localTransform.translate(containerOrigin.getX(),
                containerOrigin.getY());

        g.setTransform(localTransform);

        this.localDraw(g, containerOrigin, screenOrigin);
        g.resetAffine();
    }
}
