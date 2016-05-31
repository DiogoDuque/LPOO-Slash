package com.lpoo.slashhelpers;

import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xpath.internal.functions.Function2Args;

import org.junit.Test;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


import static org.junit.Assert.*;

/**
 * Created by Utilizador on 31/05/2016.
 */
public class FunctionTest {

    private float m;
    private float b;

    @Test
    public void testGetM() throws Exception {
        Vector2 p1 = new Vector2(0,0);
        Vector2 p2 = new Vector2(5,5);
        Function f = new Function(p1,p2);
        m = 1;
        b = 0;
        assertEquals(m,f.getM(), 0.0001);
    }

    @Test
    public void testGetB() throws Exception {
        Vector2 p1 = new Vector2(0,0);
        Vector2 p2 = new Vector2(5,5);
        Function f = new Function(p1,p2);
        m = 1;
        b = 0;
        assertEquals(b,f.getB(), 0.0001);
    }

    @Test
    public void testGetY() throws Exception {
        Vector2 p1 = new Vector2(0,0);
        Vector2 p2 = new Vector2(5,5);
        Function f = new Function(p1,p2);
        m = 1;
        b = 0;
        assertEquals(3,f.getY(3), 0.0001);

    }

    @Test
    public void testGetX() throws Exception {
        Vector2 p1 = new Vector2(0,0);
        Vector2 p2 = new Vector2(5,5);
        Function f = new Function(p1,p2);
        m = 1;
        b = 0;
        assertEquals(3,f.getX(3), 0.0001);

    }

    @Test
    public void testIntersect() throws Exception {
        Vector2 p1 = new Vector2(0,0);
        Vector2 p2 = new Vector2(5,5);
        Function f = new Function(p1,p2);

        Vector2 p3 = new Vector2(0,0);
        Vector2 p4 = new Vector2(5,-5);
        Function f1 = new Function(p3,p4);
        assertEquals(p1.x,f.intersect(f1).x, 0.0001);
        assertEquals(p1.y,f.intersect(f1).y, 0.0001);
    }
}