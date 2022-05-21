package org.csc133.a5.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import org.csc133.a5.GameWorld;
import org.csc133.a5.commands.*;

public class ControlCluster extends Container {
    private GameWorld gw;

    private Container leftBox = new Container(new GridLayout
            (1,3));
    private Container rightBox = new Container(new GridLayout
            (1,3));
    private Container middleBox = new Container(new GridLayout
            (1,2));

    private Button startEngine;

    public ControlCluster() {
        gw = GameWorld.getInstance();

        this.setLayout(new BorderLayout());
        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setBgColor(ColorUtil.LTGRAY);

        Button left = new Button("Left");
        left.setCommand(new Left(gw));
        leftBox.add(left);

        Button right = new Button("Right");
        right.setCommand(new Right(gw));
        leftBox.add(right);

        Button fight = new Button("Fight");
        fight.setCommand(new Fight(gw));
        leftBox.add(fight);

        startEngine = new Button("Start Engine");
        startEngine.setCommand(new StartOrStopEngine(gw));
        middleBox.add(startEngine);

        Button exit = new Button("Exit");
        exit.setCommand(new Exit(gw));
        middleBox.add(exit);

        Button drink = new Button("Drink");
        drink.setCommand(new Drink(gw));
        rightBox.add(drink);

        Button brake = new Button("Brake");
        brake.setCommand(new Brake(gw));
        rightBox.add(brake);

        Button accel = new Button("Accel");
        accel.setCommand(new Accel(gw));
        rightBox.add(accel);

        this.add(BorderLayout.WEST, leftBox);
        middleBox.getAllStyles().setBgTransparency(255);
        middleBox.getAllStyles().setBgColor(ColorUtil.WHITE);
        this.add(BorderLayout.CENTER, middleBox);
        this.add(BorderLayout.EAST, rightBox);
    }

    public void changeEngineButtonToStart() {
        startEngine.setText("Start Engine");
    }

    public void changeEngineButtonToStop() {
        startEngine.setText("Stop Engine");
    }
}
