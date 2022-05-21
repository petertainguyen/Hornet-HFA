package org.csc133.a5.gameobjects.parts;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a5.gameobjects.Movable;

public class Arc extends Movable {
    private final int startAngle;
    private final int arcAngle;

    private Arc(){
        this.startAngle = 0;
        this.arcAngle = 0;
    }

    @Override
    public void updateLocalTransform() {

    }

    public Arc(int color, int width, int height,
               int startAngle, int arcAngle) {
        this(color,width,height,
                0,0,0,0,0,
                startAngle,arcAngle);
    }

    public Arc(int color,
               int width, int height,
               float translateX, float translateY,
               float scaleX, float scaleY,
               float degreesRotation,
               int startAngle, int arcAngle){

        setColor(color);
        setDimension(new Dimension(width,height));
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;

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
        transform.rotate(-startAngle,0,0);
        transform.scale(-myScale.getScaleX(),-myScale.getScaleY());

        g.setTransform(transform);
    }

    @Override
    public void localDraw(Graphics g, Point containerOrigin,
                          Point screenOrigin) {
        g.setColor(getColor());

        localTransform(g);

        g.drawArc(-getWidth()/2,-getHeight()/2,getWidth(),getHeight(),
                startAngle,arcAngle);

        undoLocalTransform(g);
    }
}
