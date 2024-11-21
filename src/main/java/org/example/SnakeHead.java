package org.example;

import java.awt.*;

import javax.swing.ImageIcon;

public class SnakeHead {
    private Point position;
    private final int unitSize;
    private char direction = 'R';
    private final HitBox hitBox;
    private Image headImage;
    private double rotationYaw;
    private double currentRotYaw;


    public SnakeHead(int x, int y, int unitSize) {
        this.position = new Point(x, y); // 초기 머리 위치
        this.unitSize = unitSize;
        this.hitBox = new HitBox(x, y, unitSize, unitSize);
        headImage = new ImageIcon("src/main/resources/snake_head.png").getImage();
        rotationYaw = 90;
    }

    public void move() {
        currentRotYaw = rotationYaw;
        switch (direction) {
            case 'U' -> rotationYaw+=10;
            case 'D' -> rotationYaw-=10;
            //case 'L' -> position.translate(-unitSize/5, 0);
            //case 'R' -> position.translate(unitSize/5, 0);
        }
        position.translate((int)(Math.sin(Math.toRadians(rotationYaw))*unitSize/5), (int)(Math.cos(Math.toRadians(rotationYaw))*unitSize/5));
        hitBox.setPosition(position.x, position.y);
    }

    public void setDirection(char direction) {
        this.direction = direction;
        /*if ((this.direction == 'L' && direction != 'R') ||
            (this.direction == 'R' && direction != 'L') ||
            (this.direction == 'U' && direction != 'D') ||
            (this.direction == 'D' && direction != 'U') ||
            (this.direction == 'N')) {
            this.direction = direction;
        }*/
    }

    public Point getPosition() {
        return position;
    }

    public HitBox getHitBox() {
        return hitBox;
    }


    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(rotationYaw-currentRotYaw, position.x + unitSize / 2, position.y + unitSize / 2);
        g2d.drawImage(headImage, position.x, position.y, null);
        g2d.rotate(-rotationYaw+currentRotYaw, position.x + unitSize / 2, position.y + unitSize / 2);

        //g.setColor(Color.green);
        //g.fillRect(position.x, position.y, unitSize, unitSize);
    }
}
