package rpg.dungeon.Engine.Drawable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by philipp on 25.02.15.
 */
public class DrawableSprite extends Drawable{
    public Sprite sprite;

    public DrawableSprite(){
        sprite = new Sprite();
    }

    @Override
    public void update(float delta) {
        updatechilds(delta);
    }

    @Override
    public void render(Batch batch) {
        renderchilds(batch);
        sprite.draw(batch);
    }

    @Override
    public void dispose() {
        disposechilds();
    }
}
