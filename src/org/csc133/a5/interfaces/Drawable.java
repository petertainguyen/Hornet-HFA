package org.csc133.a5.interfaces;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public interface Drawable {
    void localDraw(Graphics g, Point containerOrigin, Point screenOrigin);
}
