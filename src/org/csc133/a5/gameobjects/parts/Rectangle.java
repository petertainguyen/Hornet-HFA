package org.csc133.a5.gameobjects.parts;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a5.gameobjects.Movable;

public class Rectangle extends Movable {

    private Rectangle(){
    }

    @Override
    public void updateLocalTransform() {

    }

    public Rectangle(int color,
                     int width, int height,
                     float translateX, float translateY,
                     float scaleX, float scaleY,
                     float degreesRotation){

        setColor(color);
        setDimension(new Dimension(width,height));

        translate(translateX,translateY);
        scale(scaleX,scaleY);
        rotate(degreesRotation);
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

        transform.translate(-myTranslation.getTranslateX(),-myTranslation.getTranslateY());
        transform.scale(-myScale.getScaleX(),-myScale.getScaleY());

        g.setTransform(transform);
    }

    @Override
    public void localDraw(Graphics g, Point containerOrigin, Point screenOrigin) {
        g.setColor(getColor());

        containerTranslate(g,containerOrigin);
        cn1ForwardPrimitiveTranslate(g,getDimension());

        localTransform(g);

        g.drawRect(0,0, getWidth(),getHeight());

        //g.drawRect(-getWidth()/2,-getHeight()/2,getWidth(),getHeight());

        undoLocalTransform(g);
    }
}

