package rpg.dungeon;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import rpg.dungeon.Engine.Renderer.RendererMap;
import rpg.dungeon.Engine.Renderer.RendererWorld;

import java.util.Random;

/**
 * Created by philipp on 29.01.15.
 */
public class MainGame implements Screen{
    //private static Manager manager;

    //private static Player player;
    public static Random r = new Random();

    public MyRenderer renderer;

    @Override
    public void show() {
        renderer = new MyRenderer(5,240,true,"GUI/GUI.pack","GUI/GUI.json", Color.BLACK,false,"Map/Test.tmx");

        //player = new Player(manager.mapManager.map,manager.physikManager.world);

        //manager = new Manager(player.pos);
    }

    @Override
    public void render(float delta) {
        renderer.update();
        renderer.render();
        //manager.update(delta);
        //player.update();

        //manager.begin(delta);
        //player.draw(manager.screenManager.batch);
        //manager.end();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width,height);
        //manager.resize(width,height);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        //manager.dispose();
        //player.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
