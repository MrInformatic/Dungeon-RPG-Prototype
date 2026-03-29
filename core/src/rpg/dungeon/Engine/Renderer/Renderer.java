package rpg.dungeon.Engine.Renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import rpg.dungeon.Engine.Drawable.Drawable;
import rpg.dungeon.Engine.Renderer.utils.SlidingCamera;
import rpg.dungeon.Engine.Renderer.utils.StandartViewport;

import java.util.ArrayList;

/**
 * Created by philipp on 24.02.15.
 */
public class Renderer {
    public ArrayList<Drawable> drawables = new ArrayList<Drawable>();
    public SpriteBatch batch;
    public SlidingCamera camera;
    public StandartViewport viewport;

    public Renderer(float speed,float fov,boolean vertical){
        super();
        batch = new SpriteBatch();
        camera = new SlidingCamera(0,0,speed);
        viewport = new StandartViewport(fov,vertical);
    }

    public void update() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        for(int i=0;i<drawables.size();i++){
            drawables.get(i).update(Gdx.graphics.getDeltaTime());
        }
    }

    public void render() {
        begin();
        for(int i=0;i<drawables.size();i++){
            drawables.get(i).render(batch);
        }
        end();
    }

    protected void begin(){
        batch.begin();
    }

    protected void end(){
        batch.end();
    }

    public void resize(int width,int height){
        viewport.resize(width, height);
        camera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
    }

    public void dispose() {
        batch.dispose();
        for(int i=0;i<drawables.size();i++){
            drawables.get(i).dispose();
        }
    }
}
