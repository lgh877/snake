package org.example;

import java.awt.*;
import java.util.Random;

public class Apple {
    private Point position;
    private final int unitSize;
    private final HitBox hitBox;
    private final int screenWidth, screenHeight;
    private final Random random;

    public Apple(int screenWidth, int screenHeight, int unitSize) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.unitSize = unitSize;
        this.random = new Random();

        position = new Point(0, 0);
        hitBox = new HitBox(0, 0, unitSize*2, unitSize*2);
        spawn();
    }

    public void spawn() {
        position.x = random.nextInt(screenWidth / unitSize) * unitSize;
        position.y = random.nextInt(screenHeight / unitSize) * unitSize;
        hitBox.setPosition(position.x-unitSize, position.y-unitSize);
    }

    public HitBox getHitBox() {
        return hitBox;
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(position.x, position.y, unitSize, unitSize);
    }
}
