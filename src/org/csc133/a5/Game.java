package org.csc133.a5;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;
import org.csc133.a5.gameobjects.Helicopter;
import org.csc133.a5.views.ControlCluster;
import org.csc133.a5.views.GlassCockPit;
import org.csc133.a5.views.MapView;

public class Game extends Form implements Runnable{
    private GameWorld gw;
    private Helicopter heli;
    private MapView mapView;
    private GlassCockPit glassCockPit;
    public ControlCluster controlCluster;

    public static int getDispW(){
        return Display.getInstance().getDisplayWidth();
    }

    public static int getDispH(){
        return Display.getInstance().getDisplayHeight();
    }

    public static int getSmallDim() { return Math.min(getDispW(),getDispH()); }
    public static int getLargeDim() { return Math.max(getDispW(),getDispH()); }

    public Game() {
        gw = GameWorld.getInstance();
        Dimension placeholder = new Dimension(0,0);
        heli = new Helicopter(placeholder);

        setTitle("Game World");

        mapView = new MapView();
        glassCockPit = new GlassCockPit();
        controlCluster = new ControlCluster();

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH, glassCockPit);
        this.add(BorderLayout.CENTER, mapView);
        this.add(BorderLayout.SOUTH, controlCluster);

        addKeyListener('Q', (evt) -> gw.quit());
        addKeyListener('d', (evt) -> gw.drinkWater());
        addKeyListener('f', (evt) -> gw.fightFire());
        addKeyListener(-93, (evt) -> gw.left());
        addKeyListener(-94, (evt) -> gw.right());
        //addKeyListener(-91, (evt) -> gw.speedUp());
        addKeyListener(-91, (evt) -> gw.accelerate());
        addKeyListener(-92, (evt) -> gw.speedDown());
        addKeyListener('s', (evt) -> gw.startOrStopEngine());

        UITimer timer = new UITimer(this);
        timer.schedule(100,true, this);

        this.getAllStyles().setBgColor(ColorUtil.BLACK);
        this.show();

        gw.init();
    }

    @Override
    public void run() {
        gw.tick();
        glassCockPit.update();
        mapView.updateLocalTransforms();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}

