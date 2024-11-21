package org.example;

import java.awt.*;

public class HitBox {
    private final Rectangle box;

    public HitBox(int x, int y, int width, int height) {
        this.box = new Rectangle(x, y, width, height);
    }

    public boolean intersects(HitBox other) {
        return box.intersects(other.box);
    }

    public void setPosition(int x, int y) {
        box.setLocation(x, y);
    }

    public Rectangle getBox() {
        return box;
    }
}
