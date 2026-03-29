package rpg.dungeon.Engine.Renderer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by philipp on 29.01.15.
 */
public class SlidingCamera extends OrthographicCamera {
    public Vector2 pos = new Vector2();
    public Vector2 target = Vector2.Zero;
    public float speed;

    public SlidingCamera(float width,float height,float speed)
    {
        super(width,height);
        this.speed = speed;
    }

    @Override
    public void update(){
        super.update();
        try {
            Vector3 position = this.position;
            this.pos.x += (target.x - this.pos.x) * speed * Gdx.graphics.getDeltaTime();
            this.pos.y += (target.y - this.pos.y) * speed * Gdx.graphics.getDeltaTime();
            position.x = this.pos.x;
            position.y = this.pos.y;
        }catch (Exception e){}
    }
}
