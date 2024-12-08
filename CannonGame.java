/**
 *
 * A simple Angry Birds-like game, aim is to hit the targets by avoiding obstacles
 *
 * By pressing the keyboard buttons (up, down, left and right), user can move the gun
 * By pressing the "space" button, shoot the bullet
 * By pressing the "r" button, make the rectangle bigger
 *
 * Kemal Onal
 * 20.03.2024
 */

import java.awt.Font;
import java.awt.event.KeyEvent;

public class KemalOnal {
    private static final int WIDTH = 1600;
    private static final int HEIGHT = 800;
    private static final double GRAVITY = 9.80665;
    private static final double X0 = 120;
    private static final double Y0 = 120;
    private static double bulletVelocity = 180;
    private static double bulletAngle = 45.0;
    private static final double[][] obstacleArray = {
            {1200, 0, 60, 220},
            {1000, 0, 60, 160},
            {600, 0, 60, 80},
            {600, 180, 60, 160},
            {220, 0, 120, 180}
    };

    private static final double[][] targetArray = {
            {1160, 0, 30, 30},
            {730, 0, 30, 30},
            {150, 0, 20, 20},
            {1480, 0, 60, 60},
            {340, 80, 60, 30},
            {1500, 600, 60, 60}
    };
    
    public static void main(String[] args) {

        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        StdDraw.setXscale(0, 1600);
        StdDraw.setYscale(0, 800);
        StdDraw.enableDoubleBuffering();

        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        for (double[] obstacle : obstacleArray) {
            StdDraw.filledRectangle(obstacle[0] + (obstacle[2] / 2), obstacle[1] + (obstacle[3] / 2), obstacle[2] / 2, obstacle[3] / 2);
        }

        StdDraw.setPenColor(StdDraw.ORANGE);
        for (double[] target : targetArray) {
            StdDraw.filledRectangle(target[0] + (target[2] / 2), target[1] + (target[3] / 2), target[2] / 2, target[3] / 2);
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(X0/2, Y0/2, 60, 60);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(X0 -50 , Y0 +50 , "Angle: " + bulletAngle + ", Velocity: " + bulletVelocity);

        while (true) {
            StdDraw.pause(25);

            double x1 = X0 + Math.cos(Math.toRadians(bulletAngle)) * (bulletVelocity/3);
            double y1 = Y0 + Math.sin(Math.toRadians(bulletAngle)) * (bulletVelocity/3);

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(X0, Y0, x1, y1);

            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont( new Font("Serif", Font.PLAIN, 20) );
            StdDraw.textLeft(25, 80, "a = "+bulletAngle);
            StdDraw.textLeft(25, 50, "v = "+bulletVelocity);

            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                double x = X0;
                double y = Y0;
                double t = 0;
                double xOld = 0;
                double xNew = 0;
                double yOld = 0;
                double yNew = 0;
                double Vx0 = (Math.cos(Math.toRadians(bulletAngle)) * bulletVelocity) / 1.72;
                double Vy0 = (Math.sin(Math.toRadians(bulletAngle)) * bulletVelocity) / 1.72;
                boolean hitTarget = false;
                boolean hitObstacle = false;

                while(x < WIDTH && y > 0){
                    xOld = x;
                    yOld = y;

                    x = X0 + (Vx0 * t) ;
                    y = Y0 + (Vy0 * t) - (0.5 * GRAVITY * t * t) ;

                    xNew = x;
                    yNew = y;

                    for(double[] obstacle : obstacleArray){
                        if(x >= obstacle[0] && x <= obstacle[0] + obstacle[2] && y >= obstacle[1] && y <= obstacle[1] + obstacle[3]){
                            hitObstacle = true;
                            break;
                        }
                    }

                    for(double[] target : targetArray){
                        if(x >= target[0] && x <= target[0] + target[2] && y >= target[1] && y <= target[1] + target[3]){
                            hitTarget = true;
                            break;
                        }
                    }
                    StdDraw.pause(10);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.setPenRadius(0.001);
                    StdDraw.line(xOld,yOld,xNew,yNew);
                    StdDraw.filledCircle(x,y ,2 );
                    StdDraw.show();

                    if(hitObstacle || hitTarget){
                        break;
                    }

                    t += 0.1 ;
                }

                String outcomeMessage = "";
                if(hitTarget){
                    outcomeMessage = "Hit the Target!";
                }else if(hitObstacle){
                    outcomeMessage = "Hit an Obstacle!";
                }else if(y <= 0){
                    outcomeMessage = "Touched the Ground!";
                }else if(x >= WIDTH){
                    outcomeMessage = "Exceeded Maximum X Interval!";
                }

                StdDraw.textLeft(10 , HEIGHT -20 , outcomeMessage );
            }

            if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                StdDraw.pause(100);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setPenRadius(0.011);
                StdDraw.line(X0, Y0, x1, y1);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setFont( new Font("Serif", Font.PLAIN, 20) );
                StdDraw.textLeft(25, 80, "a = "+bulletAngle);
                bulletAngle = Math.min(89.0, bulletAngle + 1.0);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                StdDraw.pause(100);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setPenRadius(0.011);
                StdDraw.line(X0, Y0, x1, y1);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setFont( new Font("Serif", Font.PLAIN, 20) );
                StdDraw.textLeft(25, 80, "a = "+bulletAngle);
                bulletAngle = Math.max(1.0, bulletAngle - 1.0);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
                StdDraw.pause(50);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setPenRadius(0.011);
                StdDraw.line(X0, Y0, x1, y1);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setFont( new Font("Serif", Font.PLAIN, 20) );
                StdDraw.textLeft(25, 50, "v = "+bulletVelocity);
                bulletVelocity = Math.min(300.0, bulletVelocity + 1.0);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
                StdDraw.pause(50);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setPenRadius(0.011);
                StdDraw.line(X0, Y0, x1, y1);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setFont( new Font("Serif", Font.PLAIN, 20) );
                StdDraw.textLeft(25, 50, "v = "+bulletVelocity);
                bulletVelocity = Math.max(100.0, bulletVelocity - 1.0);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_R)) {
                bulletVelocity = 180;
                bulletAngle = 45.0;

                StdDraw.clear();

                StdDraw.setPenColor(StdDraw.DARK_GRAY);
                for (double[] obstacle : obstacleArray) {
                    StdDraw.filledRectangle(obstacle[0] + (obstacle[2] / 2), obstacle[1] + (obstacle[3] / 2), obstacle[2] / 2, obstacle[3] / 2);
                }

                StdDraw.setPenColor(StdDraw.ORANGE);
                for (double[] target : targetArray) {
                    StdDraw.filledRectangle(target[0] + (target[2] / 2), target[1] + (target[3] / 2), target[2] / 2, target[3] / 2);
                }

                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledRectangle(X0/2, Y0/2, 60, 60);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(X0 -50 , Y0 +50 , "Angle: " + bulletAngle + ", Velocity: " + bulletVelocity);

            }

            StdDraw.show();

        }
    }
}