package org.csc133.a5.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

import java.util.Random;

import static com.codename1.ui.CN.*;

public class Building extends Fixed{
    private Random num = new Random();
    private int initialValue;
    private int damagePercentage;
    private Fire fires;
    private Point2D fireLocation;
    private Point2D buildingLocation;
    private int areaOfBuilding;
    private int totalAreaOfAllBuildings;
    private int totalValueOfAllBuildings;
    private int valueOfBuilding;

    public Building(Dimension buildingSize, Point2D location,
                    int initialValue) {
        super();
        this.color = ColorUtil.rgb(255,0,0);

        valueOfBuilding = initialValue;
        totalAreaOfAllBuildings = 0;
        totalValueOfAllBuildings = 0;

        myTranslation.translate((int)location.getX(), (int)location.getY());
        this.dimension = buildingSize;
    }

    public void setFireInBuilding(Fire fire){
        int x =((int)(myTranslation.getTranslateX()
                - this.dimension.getWidth() / 2)
                + num.nextInt(dimension.getWidth()) - fire.getSize());

        int y =((int)(myTranslation.getTranslateY()
                - this.dimension.getHeight() / 2)
                + num.nextInt(dimension.getHeight()) - fire.getSize());

        fire.translate(x,y);
        fire.start();
    }

    @Override
    public void localDraw(Graphics g, Point containerOrigin,
                          Point screenOrigin) {
        g.setColor(color);

        int x = -dimension.getWidth() / 2;
        int y = -dimension.getHeight() / 2;
        int w = dimension.getWidth();
        int h = dimension.getHeight();

        g.drawRect(x,y,w,h);

        g.setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_PLAIN, SIZE_SMALL));
        g.drawString("V : " + valueOfBuilding,
                (int)myTranslation.getTranslateX()
                        + dimension.getWidth() / 2 + 50,
                (int)myTranslation.getTranslateY()
                        + dimension.getHeight() / 2 + 50);
        g.drawString("D : " + damagePercentage + "%",
                (int)myTranslation.getTranslateX()
                        + dimension.getWidth() / 2 + 50,
                (int)myTranslation.getTranslateY()
                        + dimension.getHeight() / 2 + 50);
    }

    @Override
    public void updateLocalTransform() {

    }
}

