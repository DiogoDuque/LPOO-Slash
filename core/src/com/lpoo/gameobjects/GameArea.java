package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created with 4 vertices with this structure:
 * +-------------------------
 * |          5 |
 * |    (Pt1)   |   (Pt4)
 * |  5         |        95
 * |------------+-----------      Centro(50.5,50.5)
 * |            |
 * |    (Pt2)   |   (Pt3)
 * |         95 |
 * In order to create something that "ressembles" a box and to avoid more complex calculations,
 * the vertices must be created in different quadrants.
 */
public class GameArea {

    private Vector2[] points;

    public GameArea(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4, World world)
    {
        points = new Vector2[4];
        points[0]=p1;
        points[1]=p2;
        points[2]=p3;
        points[3]=p4;
        System.out.println("GameArea::GameArea() - GameArea has vertices: p1="+points[0]+", p2="+points[1]+", p3="+points[2]+", p4="+points[3]);

        for(int i=0; i<4; i++) {
            Vector2 a,b;
            a=points[i];
            b=points[i==3 ? 0 : i+1];

            double distancePTP = Math.sqrt(Math.pow((a.x-b.x),2)+Math.pow((a.y-b.y),2));
            Vector2 midPoint = new Vector2((a.x+b.x)/2, (a.y+b.y)/2);
            double angle = Math.atan((a.y-b.y)/(a.x-b.x));

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(midPoint.x, midPoint.y);
            Body groundBody = world.createBody(bodyDef);
            PolygonShape groundBox = new PolygonShape();
            groundBox.setAsBox((float)distancePTP/2, 1);
            groundBody.setTransform(midPoint,(float)angle);
            groundBody.createFixture(groundBox, 0);
        }
    }

    public Vector2[] getPoints() {return points;}

}
