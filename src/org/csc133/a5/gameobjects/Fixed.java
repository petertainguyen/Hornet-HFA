package org.csc133.a5.gameobjects;

import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point2D;

public abstract class Fixed extends GameObject{
    public Fixed() {
        super();
    }

    public Point2D getLocation(){
        return location;
    }
    public Dimension getDimension(){
        return dimension;
    }
}
