package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.lpoo.gameworld.GameWorld;
import com.lpoo.slashhelpers.Function;
import com.lpoo.slashhelpers.Utilities;

import java.util.Arrays;
import java.util.List;


/**
 * The GameArea is the "box" that contains all the balls within, and which slasher will attempt to cut.
 *
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

    /**
     * The four points that make this gameArea.
     */
    private Vector2[] points;
    /**
     * gameArea point that will be deleted due to a slash.
     */
    private Vector2 toDelete;
    /**
     * center of the zone where the gameArea is supposed to be within.
     */
    public static final Vector2 center = new Vector2(125,100);
    /**
     * Bodies of the 4 edges of the gameArea.
     */
    private Body[] bodies;
    /**
     * GameWorld containing all the gameobjects.
     */
    private GameWorld gameWorld;

    public GameArea(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4, GameWorld gameWorld)
    {
        toDelete=null;
        bodies = new Body[4];
        points = new Vector2[4];
        points[0]=p1;
        points[1]=p2;
        points[2]=p3;
        points[3]=p4;
        this.gameWorld=gameWorld;
        //checkAndCorrect();

        float area =polygonArea();
        System.out.println("GameArea::GameArea() - GameArea has vertices: p1="+points[0]+", p2="+points[1]+", p3="+points[2]+", p4="+points[3]+"area= "+area);

        resize();

        Utilities.checkBounds(points);


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
            Body groundBody = gameWorld.getWorld().createBody(bodyDef);
            PolygonShape groundBox = new PolygonShape();
            groundBox.setAsBox((float)distancePTP/2, 1);
            groundBody.setTransform(midPoint,(float)angle);
            groundBody.createFixture(groundBox, 0);
            bodies[i]=groundBody;
        }
    }

    /**
     *
     * @return points.
     */
    public Vector2[] getPoints() {return points;}

    /**
     *
     * @param toDelete corner to delete after gameArea has been fully slashed
     */
    public void setToDelete(Vector2 toDelete) {this.toDelete=toDelete;}

    /**
     *
     * @return toDelete
     */
    public Vector2 getToDelete() {
        return toDelete;
    }

    /**
     * Destroys all the bodies that were part of this gameArea.
     */
    public void dispose()
    {
        for(int i=0; i<4; i++)
        {
            bodies[i].getWorld().destroyBody(bodies[i]);
        }

    }

    /**
     *
     * @return area of the polygon made by the gameArea's 4 points.
     */
    private float polygonArea()
    {   int numPoints = 4;
        float area = 0;         // Accumulates area in the loop
        int j = numPoints-1;  // The last vertex is the 'previous' one to the first

        for (int i=0; i<numPoints; i++)
        { area = area +  (points[j].x+points[i].x) * (points[j].y-points[i].y);
            j = i;  //j is previous vertex to i
        }
        return area/2;
    }

    /**
     * Resizes the gameArea in case its dimensions are too small.
     * @return true if dimensions were resized, false if resizing was not needed.
     */
    private boolean resize()
    {
        List<Vector2> x = Arrays.asList(null,null);
        int k= 0;
        if (polygonArea()<10000){
            int i = 0;
            Vector2 center = new Vector2(0,0) ;
            while(polygonArea()<18000) {

                Function y = new Function(points[i % 4], points[(i + 2) % 4]);
                Function y1 = new Function(points[(i + 1) % 4], points[(i + 3) % 4]);
                Vector2 center1 = y.intersect(y1);
                //   }p
                System.out.println("GameWorld::getcircle pointA:"+ points[i % 4] + " p0" + points[(i+1) % 4] + " p1" + points[(i+1) % 4] + " p2 " + points[(i+2) % 4]+ " p3"+  points[(i+3) % 4]);
                if(center1 != null){
                    center = center1;
                }

                x = Utilities.getCircleLineIntersectionPoint(points[(i + 2) % 4], center, points[i % 4], 10);
                System.out.println("resize" + x.get(1));

                if (Utilities.isBetween(center, x.get(k),points[i % 4])) {
                    k = 1;
                }
                if (gameWorld.getSlasher().getPosition() == points[i % 4]) {
                    gameWorld.setSlasher(new Slasher(x.get(k), gameWorld));
                }
                points[i%4] = x.get(k);


                i++;
            }

            return true;
        }

        return false;
    }

}