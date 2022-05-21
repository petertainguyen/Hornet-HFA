package org.csc133.a5.interfaces;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

public interface Selectable {
    public void setSelected(boolean yesNO);

    public boolean isSelected();

    public boolean contains(Point2D point);
}
