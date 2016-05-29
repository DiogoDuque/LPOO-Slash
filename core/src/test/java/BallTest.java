import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.gameobjects.Ball;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Diogo on 29-05-2016.
 */
public class BallTest {


    @Test
    public void test1()
    {
        World world=new World(new Vector2(0, 0), false);
        int x=50,y=50;
        Ball ball = new Ball(x,y,world);
        assertEquals(x,ball.getX());
        world=null;
    }

}