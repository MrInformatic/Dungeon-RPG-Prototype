package rpg.dungeon.Engine.Renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import rpg.dungeon.Engine.Renderer.utils.StandartViewport;

/**
 * Created by philipp on 24.02.15.
 */
public class RendererGUI extends Renderer {
    public Stage stage;
    public TextureAtlas atlas;
    public Skin skin;
    public boolean PostProcessGui = false;

    public RendererGUI(float speed,float fov,boolean vertical,String atlas,String skin){
        super(speed,fov,vertical);
        viewport = new StandartViewport(fov,vertical);
        stage = new Stage(viewport);
        try {
            this.atlas = new TextureAtlas(atlas);
        }catch (Exception e){}
        try {
            this.skin = new Skin(Gdx.files.internal(skin), this.atlas);
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void resize(int width,int height){
        viewport.resize(width, height);
        super.resize(width,height);
    }

    @Override
    public void render() {
        super.render();
        if(!PostProcessGui)
           rendergui();
    }

    @Override
    public void update() {
        super.update();
        stage.act(Gdx.graphics.getDeltaTime());
    }

    @Override
    protected void end(){
        if(PostProcessGui)
            rendergui();
        super.end();
    }

    @Override
    public void dispose(){
        super.dispose();
        stage.dispose();
        atlas.dispose();
        skin.dispose();
    }

    protected void rendergui(){
        stage.draw();
    }
}
