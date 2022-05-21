package org.csc133.a5.gameobjects;

import com.codename1.ui.geom.Dimension;
import org.csc133.a5.GameWorld;

public class PlayerHelicopter extends Helicopter{
    private GameWorld gw;

    public PlayerHelicopter(Dimension worldSize) {
        super(worldSize);
        //gw = GameWorld.getInstance();
    }
}
