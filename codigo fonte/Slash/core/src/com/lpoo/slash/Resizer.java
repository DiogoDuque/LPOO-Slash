package com.lpoo.slash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Diogo on 05-06-2016.
 * Abstract class to be implemented on all classes that also implement Screen, so all resizing
 * can be done through here.
 */
public abstract class Resizer {
    private float screenResizeFactor;
    public float width, height;

    public Resizer(float screenResizeFactor)
    {
        this.screenResizeFactor=screenResizeFactor;
        width=Slash.screenDimensions.x*screenResizeFactor;
        height=Slash.screenDimensions.y*screenResizeFactor;
    }

    public Vector2 convertDimensions(Vector2 dims)
    {
        dims.x = dims.x*width/ Gdx.app.getGraphics().getWidth();
        dims.y = dims.y*height/Gdx.app.getGraphics().getHeight();
        return dims;
    }
}
