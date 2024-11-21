package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int DELAY = 25;

    private SnakeHead snakeHead;
    private SnakeBody snakeBody;
    private Apple apple;
    private boolean running = false;
    private int applesEaten = 0;
    private Timer timer;

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        Point startPosition = new Point(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
        snakeHead = new SnakeHead(startPosition.x, startPosition.y, UNIT_SIZE);
        snakeBody = new SnakeBody(6, UNIT_SIZE, startPosition, 0.25);
        apple = new Apple(SCREEN_WIDTH, SCREEN_HEIGHT, UNIT_SIZE);
        running = true;
        applesEaten = 0;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            apple.draw(g);
            snakeBody.draw(g);
            snakeHead.draw(g);
            drawScore(g);
        } else {
            gameOver(g);
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, SCREEN_HEIGHT / 2 + 50);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            snakeHead.move();
            snakeBody.move(snakeHead.getPosition());
    
            // Apple과 SnakeHead의 히트박스 충돌 여부 확인
            if (snakeHead.getHitBox().intersects(apple.getHitBox())) {
                snakeBody.grow();
                applesEaten++;
                apple.spawn();
            }
    
            // 게임오버 조건: 머리와 몸통 충돌 혹은 머리가 벽에 충돌
            if (checkGameOver()) {
                running = false;
            }
        }
        repaint();
    }
    
    private boolean checkGameOver() {
        // 1. 머리와 몸통 충돌
        if (snakeBody.checkCollision(snakeHead.getHitBox())) {
            return true;
        }
    
        // 2. 머리가 벽에 충돌
        Point headPosition = snakeHead.getPosition();
        if (headPosition.x < 0 || headPosition.x >= SCREEN_WIDTH ||
            headPosition.y < 0 || headPosition.y >= SCREEN_HEIGHT) {
            return true;
        }
    
        return false; // 아무 충돌도 발생하지 않음
    }
    
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                //case KeyEvent.VK_LEFT:
                //    snakeHead.setDirection('L');
                //    break;
                //case KeyEvent.VK_RIGHT: 
                //    snakeHead.setDirection('R');
                //    break;
                case KeyEvent.VK_UP -> snakeHead.setDirection('U');
                case KeyEvent.VK_DOWN -> snakeHead.setDirection('D');
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            snakeHead.setDirection('N');
        }
    }
}
