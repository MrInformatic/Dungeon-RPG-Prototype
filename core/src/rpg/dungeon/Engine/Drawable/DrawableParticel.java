package rpg.dungeon.Engine.Drawable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import rpg.dungeon.Engine.utils.Timer;

/**
 * Created by philipp on 07.02.15.
 */
public class DrawableParticel extends Drawable{
    public Timer timer = new Timer();
    public ParticleEffect effect;
    public boolean end = false;
    public Vector2 pos;

    public DrawableParticel(float x,float y,String effect,String image){
        this.effect = new ParticleEffect();
        this.effect.load(Gdx.files.internal(effect),Gdx.files.internal(image));
        pos = new Vector2(x,y);
        this.effect.setPosition(x,y);
        this.effect.start();
        timer.start();
    }

    @Override
    public void render(Batch batch){
        renderchilds(batch);
        effect.draw(batch);
    }

    @Override
    public void dispose(){
        disposechilds();
        effect.dispose();
    }

    @Override
    public void update(float delta) {
        updatechilds(delta);
        timer.update(delta);
        effect.update(delta);
    }
}
