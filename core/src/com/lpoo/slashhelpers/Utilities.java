package com.lpoo.slashhelpers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.gameobjects.Ball;
import com.lpoo.gameobjects.GameArea;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Diogo on 01-06-2016.
 * Contains several utilities for various purposes.
 * Design Pattern: Facade
 */
public final class Utilities {

    /**
     *
     * @param pt1 one point
     * @param pt2 another point
     * @return Euclidian distance between the two points.
     */
    public static double distance(Vector2 pt1, Vector2 pt2)
    {
        return Math.sqrt(Math.pow(pt1.x-pt2.x,2)+Math.pow(pt1.y-pt2.y,2));
    }

    public static List<Vector2> getCircleLineIntersectionPoint(Vector2 pointA, Vector2 pointB, Vector2 center, float radius) {
        float baX = pointB.x - pointA.x;
        float baY = pointB.y - pointA.y;
        float caX = center.x - pointA.x;
        float caY = center.y - pointA.y;

        float a = baX * baX + baY * baY;
        float bBy2 = baX * caX + baY * caY;
        float c = caX * caX + caY * caY - radius * radius;

        float pBy2 = bBy2 / a;
        float q = c / a;

        float disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }
        // if disc == 0 ... dealt with later
        float tmpSqrt = (float) Math.sqrt(disc);
        float abScalingFactor1 = -pBy2 + tmpSqrt;
        float abScalingFactor2 = -pBy2 - tmpSqrt;

        Vector2 p1 = new Vector2(pointA.x - baX * abScalingFactor1, pointA.y
                - baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        Vector2 p2 = new Vector2(pointA.x - baX * abScalingFactor2, pointA.y
                - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }

    /**
     * Changes the current screen on Game to the newScreen.
     * @param game Game on which to set a new Screen
     * @param newScreen new Screen
     */
    public static void changeScreen(Game game, Screen newScreen)
    {
        Screen oldScreen = game.getScreen();

        game.setScreen(newScreen);
        if(oldScreen != null)
            oldScreen.dispose();
    }

    /**
     * Changes the points (if needed) so that each of them is in his quadrant and not too close to the sides of the board.
     * The method does not need a return value because the Vector2[] is edited, so the reference in the calling method is also changed.
     * @param points points that will be used to create a gameArea.
     */
    public static void checkBounds(Vector2[] points)
    {

        for(int i = 0 ; i <points.length;i++){

            //checks 'x' border of screen
            if(points[i].x <10)
                points[i].x = 10;
            else if(points[i].x >240)
                points[i].x = 240;

            //checks 'y' border of screen
            if(points[i].y <10)
                points[i].y = 10;
            else if(points[i].y >190)
                points[i].y = 190;

            //checks quadrants
            switch(i)
            {
                case 0: //1st quadrant - up left
                    if(points[i].x> GameArea.center.x)
                        points[i].x=GameArea.center.x;
                    if(points[i].y>GameArea.center.y)
                        points[i].y=GameArea.center.y;
                    break;

                case 1: //2nd quadrant - down left
                    if(points[i].x>GameArea.center.x)
                        points[i].x=GameArea.center.x;
                    if(points[i].y<GameArea.center.y)
                        points[i].y=GameArea.center.y;
                    break;

                case 2: //3rd quadrant - down right
                    if(points[i].x<GameArea.center.x)
                        points[i].x=GameArea.center.x;
                    if(points[i].y<GameArea.center.y)
                        points[i].y=GameArea.center.y;
                    break;

                case 3: //4th quadrant - up right
                    if(points[i].x<GameArea.center.x)
                        points[i].x=GameArea.center.x;
                    if(points[i].y>GameArea.center.y)
                        points[i].y=GameArea.center.y;
                    break;
            }


        }
        for (int i = 0;i<points.length;i++){
            if(points[i%4].x == points[(i+1)%4].x){
                points[i].x++;
            }
            if(points[i%4].y == points[(i+1)%4].y){
                points[i].y++;
            }
        }
    }

    public static boolean isBetween ( Vector2 center, Vector2 p, Vector2 ballPoint)
    {

        System.out.println("GameWorld::isBetween() center:"+ center + " p" + p + "ball" + ballPoint);
        if(p == null)
            return false;

        if ((p.x >= center.x )&& (p.x <= ballPoint.x)){
            if ((p.y >= center.y) && (p.y <= ballPoint.y))
                return true;
            else if ((p.y <= center.y) &&( p.y >= ballPoint.y))
                return true;
            else return false;
        }
        if ((p.x <= center.x) && (p.x >= ballPoint.x)){
            if ((p.y >= center.y) && (p.y <= ballPoint.y))
                return true;
            else if ((p.y <= center.y) &&( p.y >= ballPoint.y))
                return true;
            else return false;
        }
        return false;
    }

    public static boolean pointInPolygon( Vector2[] pointsTriangle, Ball ball) {

        Vector2 center = new Vector2();
        boolean b = true;
        center.x = (pointsTriangle[0].x + pointsTriangle[1].x + pointsTriangle[2].x)/3;
        center.y = (pointsTriangle[0].y + pointsTriangle[1].y + pointsTriangle[2].y)/3;
        Vector2 ballPoint = new Vector2(ball.getBody().getPosition().x,ball.getBody().getPosition().y);
        Function f = new Function(center, ballPoint);
        for(int i = 0;i<pointsTriangle.length;i++){
            Function edge = new Function(pointsTriangle[i], pointsTriangle[((i+1)%3)]);
            Vector2 p = f.intersect(edge);

            if(Utilities.isBetween(center, p, ballPoint))
                b = false;
        }
        return b;
    }
}
