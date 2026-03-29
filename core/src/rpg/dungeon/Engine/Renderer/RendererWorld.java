package rpg.dungeon.Engine.Renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import rpg.dungeon.Engine.Renderer.RendererPostProcessing;

/**
 * Created by philipp on 25.02.15.
 */
public class RendererWorld extends RendererPostProcessing{
    public World world;
    public Box2DDebugRenderer debugRenderer;
    public boolean debug;
    private float oldtime,time = 0;

    public RendererWorld(float speed,float fov,boolean vertical,String atlas, String skin,Color color, boolean debug) {
        super(speed,fov,vertical,atlas, skin,color);
        this.debug = debug;
        if(this.debug) {
            debugRenderer = new Box2DDebugRenderer();
        }
        world = new World(new Vector2(0, 0), true);
    }

    @Override
    public void update(){
        super.update();
        world.step(Gdx.graphics.getRawDeltaTime(), 24, 24);
    }

    @Override
    public void render(){
        super.render();
        if(this.debug) {
            debugRenderer.render(world,camera.combined.cpy().scl(24));
        }
    }

    @Override
    public void dispose(){
        super.dispose();
        world.dispose();
        if(this.debug) {
            debugRenderer.dispose();
        }
    }
}
