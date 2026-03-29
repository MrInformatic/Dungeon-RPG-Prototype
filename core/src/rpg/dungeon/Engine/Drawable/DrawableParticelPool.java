package rpg.dungeon.Engine.Drawable;

import com.badlogic.gdx.graphics.g2d.Batch;
import rpg.dungeon.Engine.Shader.DefaultShader;

/**
 * Created by philipp on 07.02.15.
 */
public class DrawableParticelPool extends Drawable {
    public void add(DrawableParticel particel){
        childs.add(particel);
    }

    @Override
    public void update(float delta) {
        updatechilds(delta);
        for (int i = 0; i < childs.size(); i++) {
            try {
                if (((DrawableParticel)childs.get(i)).end) {
                    childs.remove(i);
                }
            } catch (Exception e) { }
        }
    }

    @Override
    public void render(Batch batch) {
        renderchilds(batch);
    }

    @Override
    public void dispose(){
        disposechilds();
    }
}
