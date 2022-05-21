package org.csc133.a5.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class FireCollection extends GameObjectCollection<Fire>{
    public FireCollection(){
        super();
        this.color = ColorUtil.MAGENTA;
    }

    @Override
    public void updateLocalTransform() {

    }

    @Override
    public void localDraw(Graphics g, Point containerOrigin,
                          Point screenOrigin) {
        for (Fire fire : getGameObjects())
            fire.localDraw(g, containerOrigin, screenOrigin);
    }
}
