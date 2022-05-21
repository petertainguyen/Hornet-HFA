package org.csc133.a5.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

public class Traversal extends FlightPath{
    boolean active;
    double t;
    NonPlayerHelicopter nph;

    public Traversal(NonPlayerHelicopter helicopter) {
        this.nph = helicopter;
        active = false;
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void setTail(Point2D lastControlPoint) {
        super.setTail(lastControlPoint);
    }

    public void moveAlongAPath(Point2D c) {
        Point2D point = evaluateCurve(t, getAllControlPoints());

        double translateX = point.getX() - c.getX();
        double translateY = point.getY() - c.getY();
        double theta = 360 - Math.toDegrees(Math.atan2(translateY,translateX));

        nph.translate(translateX,translateY);

        if (t <= 1) {
            t = t + nph.getSpeed() * 0.001;
            nph.rotate(nph.getSpeed() - theta);
            nph.setStartingAngle((int)theta);
        } else
            t = 0;
    }

    @Override
    public void localDraw(Graphics g, Point containerOrigin,
                          Point screenOrigin) {
        if(active)
            super.localDraw(g, containerOrigin, screenOrigin);
    }
}
