package org.example;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class SnakeBody {
    private final ArrayList<Point> bodySegments;
    private final ArrayList<HitBox> hitBoxes;
    private final int unitSize;
    private final double elasticity; // 탄력성 상수
    private Image bodyImage;


    public SnakeBody(int initialSize, int unitSize, Point initialPosition, double elasticity) {
        this.unitSize = unitSize;
        this.elasticity = elasticity;
        this.bodySegments = new ArrayList<>();
        this.hitBoxes = new ArrayList<>();
    
        bodyImage = new ImageIcon("src/main/resources/snake_body.png").getImage();

        for (int i = 0; i < initialSize; i++) {
            Point segment = new Point(initialPosition.x - (i + 1) * unitSize, initialPosition.y); // 머리 뒤로 배치
            bodySegments.add(segment);
            hitBoxes.add(new HitBox(segment.x, segment.y, 0, 0));
        }
    }

    public void move(Point headPosition) {
        for (int i = bodySegments.size() - 1; i > 0; i--) {
            Point current = bodySegments.get(i);
            Point prev = bodySegments.get(i - 1);

            // Inverted Kinetics 적용 (선형 보간 + 탄력성)
            double deltaX = prev.x - current.x;
            double deltaY = prev.y - current.y;
            current.translate((int) (deltaX * elasticity), (int) (deltaY * elasticity));
            hitBoxes.get(i).setPosition(current.x, current.y);
        }

        // 첫 번째 세그먼트는 머리 위치를 따라감
        bodySegments.get(0).setLocation(headPosition);
        hitBoxes.get(0).setPosition(headPosition.x, headPosition.y);
    }

    public boolean checkCollision(HitBox headHitBox) {
        for (HitBox box : hitBoxes) {
            if (box != headHitBox && headHitBox.intersects(box)) {
                return true;
            }
        }
        return false;
    }

    public void grow() {
        Point lastSegment = bodySegments.get(bodySegments.size() - 1);
        bodySegments.add(new Point(lastSegment.x, lastSegment.y));
        hitBoxes.add(new HitBox(lastSegment.x, lastSegment.y, unitSize, unitSize));
    }

    public void draw(Graphics g) {
        //g.setColor(new Color(45, 180, 0));
        for (int i=1;i<bodySegments.size();i++) {
            g.drawImage(bodyImage, bodySegments.get(i).x, bodySegments.get(i).y, null);
            //g.fillRect(segment.x, segment.y, unitSize, unitSize);
        }
    }
}
