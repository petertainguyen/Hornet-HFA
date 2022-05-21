package org.csc133.a5.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

public class Helipad extends Fixed{
    public Point2D getLocation(){
        return super.getLocation();
    }

    public Dimension getDimension(){
        return super.getDimension();
    }

    @Override
    public void updateLocalTransform() {

    }

    public Helipad(Dimension worldSize) {
        super();
        this.worldSize = worldSize;
        this.color = ColorUtil.GRAY;
        this.dimension = new Dimension(250,250);

        myTranslation = Transform.makeIdentity();

        myTranslation.translate(worldSize.getWidth() / 2,200);
    }

    @Override
    public void localDraw(Graphics g, Point containerOrigin,
                          Point screenOrigin) {
        g.setColor(this.color);

        int x = -dimension.getWidth() / 2;
        int y = -dimension.getHeight() / 2;
        int w = dimension.getWidth();
        int h = dimension.getHeight();

        //cn1ForwardPrimitiveTranslate(g, dimension);

        g.drawArc(x,y,w,h,0,360);
        g.drawRect(x,y,w,h);
    }
}

