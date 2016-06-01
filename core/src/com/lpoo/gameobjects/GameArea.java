package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.slashhelpers.Utilities;

/**
 * Created with 4 vertices with this structure:
 * +-------------------------+--------
 * |            |0           |
 * |    (Pt1)   |   (Pt4)    |
 * |0           |         250|
 * |------------+------------+
 * |            |(125,100)   |
 * |    (Pt2)   |   (Pt3)    |
 * |            |200         |
 * In order to create something that "ressembles" a box and to avoid more complex calculations,
 * the vertices must be created in different quadrants.
 */
public class GameArea {

    private Vector2[] points;
    private Vector2 toDelete; //vertice que sera apagado e substituido por outro depois do corte
    private Vector2 center = new Vector2(125,100); //centro da zona onde é suposto a gameArea estar
    private Body[] bodies;

    public GameArea(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4, World world)
    {
        toDelete=null;
        bodies = new Body[4];
        points = new Vector2[4];
        points[0]=p1;
        points[1]=p2;
        points[2]=p3;
        points[3]=p4;
        //checkAndCorrect();
        System.out.println("GameArea::GameArea() - GameArea has vertices: p1="+points[0]+", p2="+points[1]+", p3="+points[2]+", p4="+points[3]);

        for(int i=0; i<4; i++) {
            Vector2 a,b;
            a=points[i];
            b=points[i==3 ? 0 : i+1];

            double distancePTP = Utilities.distance(a,b);
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
            bodies[i]=groundBody;
        }
    }

    /**
     * Makes small corrections the gameArea shape:
     * - Move if it is off-centered
     * - Resize if it is too small TODO
     * - Slightly move a point so that "y=m*x+b" functions can be created with no problems
     */
    private void checkAndCorrect()
    {
        int minDistance=20; //min distance to not require resizing
        //move+resize
        for(int i=0; i<4; i++)
        {
            Vector2 p1=points[i], p2=points[(i+1)%4];
            double distance = Math.sqrt(Math.pow(p1.x-p2.x,2)+Math.pow(p1.y-p2.y,2));
            Vector2 direction = new Vector2(p1.x-p2.x,p1.y-p2.y);;
            double ang = Math.atan(direction.y/direction.x);
            if(distance<minDistance)
                switch(i)
                {
                    case 0:
                        if(p1.y>center.y) { //move p1
                            p1.x += direction.x*(float)Math.cos(ang)*(minDistance/distance);
                            p1.y += direction.y*(float)Math.sin(ang)*(minDistance/distance);
                        } else if (p2.y<center.y) { //move p2
                            p2.x -= direction.x*(float)Math.cos(ang)*(minDistance/distance);
                            p2.y -= direction.y*(float)Math.sin(ang)*(minDistance/distance);
                        } else { //move both
                            p1.x += direction.x*(float)Math.cos(ang)*(minDistance/(2*distance));
                            p1.y += direction.y*(float)Math.sin(ang)*(minDistance/(2*distance));
                            p2.x -= direction.x*(float)Math.cos(ang)*(minDistance/(2*distance));
                            p2.y -= direction.y*(float)Math.sin(ang)*(minDistance/(2*distance));
                        }
                        break;
                    case 1:
                        if(p1.x>center.x) { //move p1
                            p1.x += direction.x*(float)Math.cos(ang)*(minDistance/distance);
                            p1.y += direction.y*(float)Math.sin(ang)*(minDistance/distance);
                        } else if (p2.x<center.x) { //move p2
                            p2.x -= direction.x*(float)Math.cos(ang)*(minDistance/distance);
                            p2.y -= direction.y*(float)Math.sin(ang)*(minDistance/distance);
                        } else { //move both
                            p1.x += direction.x * (float) Math.cos(ang) * (minDistance / (2 * distance));
                            p1.y += direction.y * (float) Math.sin(ang) * (minDistance / (2 * distance));
                            p2.x -= direction.x * (float) Math.cos(ang) * (minDistance / (2 * distance));
                            p2.y -= direction.y * (float) Math.sin(ang) * (minDistance / (2 * distance));
                        }
                    case 2:
                        if(p1.y<center.y) { //move p1
                            p1.x += direction.x*(float)Math.cos(ang)*(minDistance/distance);
                            p1.y += direction.y*(float)Math.sin(ang)*(minDistance/distance);
                        } else if (p2.y>center.y) { //move p2
                            p2.x -= direction.x*(float)Math.cos(ang)*(minDistance/distance);
                            p2.y -= direction.y*(float)Math.sin(ang)*(minDistance/distance);
                        } else { //move both
                            p1.x += direction.x * (float) Math.cos(ang) * (minDistance / (2 * distance));
                            p1.y += direction.y * (float) Math.sin(ang) * (minDistance / (2 * distance));
                            p2.x -= direction.x * (float) Math.cos(ang) * (minDistance / (2 * distance));
                            p2.y -= direction.y * (float) Math.sin(ang) * (minDistance / (2 * distance));
                        }
                    case 3:
                        if(p1.x<center.x) { //move p1
                            p1.x += direction.x*(float)Math.cos(ang)*(minDistance/distance);
                            p1.y += direction.y*(float)Math.sin(ang)*(minDistance/distance);
                        } else if (p2.x>center.x) { //move p2
                            p2.x -= direction.x*(float)Math.cos(ang)*(minDistance/distance);
                            p2.y -= direction.y*(float)Math.sin(ang)*(minDistance/distance);
                        } else { //move both
                            p1.x += direction.x * (float) Math.cos(ang) * (minDistance / (2 * distance));
                            p1.y += direction.y * (float) Math.sin(ang) * (minDistance / (2 * distance));
                            p2.x -= direction.x * (float) Math.cos(ang) * (minDistance / (2 * distance));
                            p2.y -= direction.y * (float) Math.sin(ang) * (minDistance / (2 * distance));
                        }
                }
        }

        //check if points.x are all different
        if(points[0].x==points[1].x)
            points[0].x++;
        if(points[2].x==points[3].x)
            points[2].x--;
    }

    public Vector2[] getPoints() {return points;}

    public void setToDelete(Vector2 toDelete) {this.toDelete=toDelete;}

    public Vector2 getToDelete() {return toDelete;}

    public void dispose()
    {
        for(int i=0; i<4; i++)
        {
            bodies[i].getWorld().destroyBody(bodies[i]);
        }

    }
}
