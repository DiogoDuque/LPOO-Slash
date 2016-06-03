package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.gameworld.GameWorld;
import com.lpoo.slashhelpers.Function;
import com.lpoo.slashhelpers.Utilities;

import java.util.Arrays;
import java.util.List;

import static com.lpoo.gameworld.GameWorld.isBetween;

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
    public static final Vector2 center = new Vector2(125,100); //centro da zona onde é suposto a gameArea estar
    private Body[] bodies;
    private GameWorld gameWorld;
    public GameArea(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4, World world, GameWorld gameWorld)
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
      // Vector2 a1 = new Vector2(10,10);
      // Vector2 b2 = new Vector2(4,4);
       //Vector2 b3 = new Vector2(5,5);
       //List<Vector2> x = Arrays.asList();
       //x = Utilities.getCircleLineIntersectionPoint(a1, b2 ,a1,10);
       //System.out.println("a = "+b3+" b= "+b2+" c "+a1+" pontos intersectados 1 "+x.get(0)+" pontos intersectados 2 "+x.get(1));

        resize();

        gameWorld.checkBounds(points);


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

    public Vector2[] getPoints() {return points;}

    /**
     *
     * @param toDelete corner to delete after gameArea has been fully slashed
     */
    public void setToDelete(Vector2 toDelete) {this.toDelete=toDelete;}

    public Vector2 getToDelete() {return toDelete;}

    public void dispose()
    {
        for(int i=0; i<4; i++)
        {
            bodies[i].getWorld().destroyBody(bodies[i]);
        }

    }

    public float polygonArea()
    {   int numPoints = 4;
        float area = 0;         // Accumulates area in the loop
        int j = numPoints-1;  // The last vertex is the 'previous' one to the first

        for (int i=0; i<numPoints; i++)
        { area = area +  (points[j].x+points[i].x) * (points[j].y-points[i].y);
            j = i;  //j is previous vertex to i
        }
        return area/2;
    }

    public boolean resize(){
        // if(polygonArea()>10000)
        //      return;
        //   while(polygonArea()<15000){
        //Vector2 p1 = new Vector2(0,0);
        List<Vector2> x = Arrays.asList(null,null);
        int k= 0;
        if (polygonArea()<10000){
            int i = 0;
            while(polygonArea()<12000) {

                Function y = new Function(points[i % 4], points[(i + 2) % 4]);
                Function y1 = new Function(points[(i + 1) % 4], points[(i + 3) % 4]);
                Vector2 center = y.intersect(y1);
                //   }

                // Vector2 y2 = new Function(,points[0]+100);

                x = Utilities.getCircleLineIntersectionPoint(points[(i + 2) % 4], center, points[i % 4], 10);
                System.out.println("resize" + x.get(1));

                if (isBetween(center, x.get(k),points[i % 4])) {
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
