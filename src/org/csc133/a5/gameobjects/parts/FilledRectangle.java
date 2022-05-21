package org.csc133.a5.gameobjects.parts;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a5.gameobjects.Movable;

public class FilledRectangle extends Movable {

    private FilledRectangle(){
    }

    @Override
    public void updateLocalTransform() {

    }

    public FilledRectangle(int color,
                           int width, int height,
                           float translateX, float translateY,
                           float scaleX, float scaleY,
                           float degreesRotation){

        setColor(color);
        setDimension(new Dimension(width,height));

        myTranslation.translate(translateX,translateY);
        myScale.scale(scaleX,scaleY);
        myRotation.rotate(degreesRotation,0,0);
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
}