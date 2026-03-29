package rpg.dungeon.Engine.Drawable;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import rpg.dungeon.Engine.Shader.DefaultShader;

import java.util.ArrayList;

/**
 * Created by philipp on 24.02.15.
 */
public abstract class Drawable {
    public ArrayList<Drawable> childs = new ArrayList<Drawable>();
    public ShaderProgram shaderProgram = DefaultShader.defaultshader;

    public Drawable(){
        super();
    }

    public abstract void update(float delta);

    public abstract void render(Batch batch);

    public abstract void dispose();

    public void renderchilds(Batch batch){
        for(int i=0;i<childs.size();i++){
            childs.get(i).render(batch);
        }
        batch.setShader(shaderProgram);
    }

    public void updatechilds(float delta){
        for(int i=0;i<childs.size();i++){
            childs.get(i).update(delta);
        }
    }

    public void disposechilds(){
        for(int i=0;i<childs.size();i++){
            childs.get(i).dispose();
        }
    }
}
