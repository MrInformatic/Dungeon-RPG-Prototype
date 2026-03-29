package rpg.dungeon.Engine.Renderer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by philipp on 24.01.15.
 */
public class StandartViewport extends ScreenViewport {
    public float s;
    public boolean vert;

    public StandartViewport(float size,boolean vertical){
        super(new OrthographicCamera(size,size));
        s = size;
        vert = vertical;
    }

    public void resize(float width,float height){
        if(vert)
        {
            this.setUnitsPerPixel(1f/((float)Math.round(height/s)));
        }else{
            this.setUnitsPerPixel(1f/((float)Math.round(width/s)));
        }
        this.update((int)width,(int)height,true);
    }
}
