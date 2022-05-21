package org.csc133.a5.gameobjects;

import com.codename1.ui.geom.Point2D;

public class FlightControl {
    private Traversal primary;
    private Traversal correction;

    public FlightControl(NonPlayerHelicopter nph) {
        primary = new Traversal(nph);
        correction = new Traversal(nph);
        primary.activate();
        correction.deactivate();
    }

    public void moveAlongAPath(Point2D c) {
        primary.moveAlongAPath(c);
    }

    public Traversal getPrimary() {
        return primary;
    }

    public Traversal getCorrection() {
        return correction;
    }
}
