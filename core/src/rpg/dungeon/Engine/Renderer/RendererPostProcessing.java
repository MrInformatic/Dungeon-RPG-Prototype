package rpg.dungeon.Engine.Renderer;

import com.badlogic.gdx.graphics.Color;
import rpg.dungeon.Engine.Renderer.utils.PostProcessing;

/**
 * Created by philipp on 24.02.15.
 */
public class RendererPostProcessing extends RendererGUI {
    public PostProcessing postProcessing;

    public RendererPostProcessing(float speed,float fov,boolean vertical,String atlas,String skin,Color color){
        super(speed,fov,vertical,atlas,skin);
        postProcessing = new PostProcessing(color);
    }

    @Override
    public void update(){
        super.update();
    }

    @Override
    protected void begin(){
        postProcessing.begin();
        super.begin();
    }

    @Override
    protected void end(){
        super.end();
        postProcessing.end();
    }

    @Override
    public void resize(int width,int height){
        super.resize(width,height);
        postProcessing.resize(viewport.getUnitsPerPixel());
    }

    @Override
    public void dispose(){
        super.dispose();
        postProcessing.dispose();
    }
}
