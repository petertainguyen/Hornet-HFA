package org.csc133.a5.views;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.GridLayout;
import org.csc133.a5.GameWorld;

public class GlassCockPit extends Container {
    private GameWorld gw;
    private Label heading;
    private Label speed;
    private Label fuel;
    private Label numberOfFiresBurning;
    private Label totalFireSize;
    private Label percentDestroyed;
    private Label currentFinancialLoss;

    public GlassCockPit() {
        gw = GameWorld.getInstance();

        this.getAllStyles().setBgTransparency(255);
        this.setLayout(new GridLayout(2,7));

        this.add("HEADING");
        this.add("SPEED");
        this.add("FUEL");
        this.add("#'S OF FIRES");
        this.add("FIRE SIZE");
        this.add("% DESTROYED");
        this.add("FINANCIAL LOSS");

        heading = new Label("0");
        speed = new Label("0");
        fuel = new Label("25000");
        numberOfFiresBurning = new Label("0");
        totalFireSize = new Label("0");
        percentDestroyed = new Label("0");
        currentFinancialLoss = new Label("0");

        this.add(heading);
        this.add(speed);
        this.add(fuel);
        this.add(numberOfFiresBurning);
        this.add(totalFireSize);
        this.add(percentDestroyed);
        this.add(currentFinancialLoss);
    }

    public void update(){
        heading.setText(gw.getHeading());
        speed.setText(gw.getSpeed());
        fuel.setText(gw.getFuel());
        numberOfFiresBurning.setText(gw.getNumberOfFiresBurning());
        totalFireSize.setText(gw.getTotalFireSize());
        percentDestroyed.setText(gw.getPercentDestroyed());
        currentFinancialLoss.setText(gw.getCurrentFinancialLoss());
    }
}

