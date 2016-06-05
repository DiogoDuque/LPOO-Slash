package com.lpoo.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.slashhelpers.Utilities;

import java.util.Random;

/**
 * Created by Diogo Duque on 05/06/2016.
 */
public class Redirecter {

    /**
     * The redirecter position.
     */
    private Vector2 position;
    /**
     * If GameWorld.score is bigger than or equal to scoreLimit, a redirecter will appear in the GameArea.
     */
    public final static int scoreLimit = 5;
    public final static float radius = 2*Ball.getRadius();

    /**
     * Constructor.
     * @param gameAreaPoints vertices of the polygon that gives shape to the GameArea
     */
    public Redirecter(Vector2[] gameAreaPoints) {
        int xMin, xMax, yMin, yMax;
        Random rand = new Random();
        xMin=(int)(Math.max(gameAreaPoints[0].x,gameAreaPoints[1].x)+2*radius);
        xMax=(int)(Math.min(gameAreaPoints[2].x, gameAreaPoints[3].x)-2*radius);
        yMin=(int)(Math.max(gameAreaPoints[0].y, gameAreaPoints[3].y)+2*radius);
        yMax=(int)(Math.min(gameAreaPoints[1].y, gameAreaPoints[2].y)-2*radius);
        this.position = new Vector2(xMin+(xMax-xMin)*rand.nextFloat(),yMin+(yMax-yMin)*rand.nextFloat());
    }

    public void attemptMoveBall(Ball ball)
    {
        double distance = Utilities.distance(ball.getBody().getPosition(),position);
        if(distance>radius+Ball.getRadius())
            return;

        ball.setRandomMovement();
    }

    public Vector2 getPosition() {
        return position;
    }
}
