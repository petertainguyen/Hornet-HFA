package org.csc133.a5.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

public class River extends Fixed{
    public River(Dimension riverSize, Point2D location) {
        super();
        this.color = ColorUtil.BLUE;
        this.dimension = riverSize;

        myTranslation.translate((int)location.getX(), (int)location.getY());
    }

    @Override
    public void localDraw(Graphics g, Point containerOrigin,
                          Point screenOrigin) {
        g.setColor(this.color);

        int x = -dimension.getWidth()/2;
        int y = -dimension.getHeight()/2;
        int w = dimension.getWidth();
        int h = dimension.getHeight();

        g.drawRect(x,y,w,h);
    }

    public Point2D getLocation(){
        return super.getLocation();
    }

    public Dimension getDimension(){
        return super.getDimension();
    }

    @Override
    public void updateLocalTransform() {

    }

}

