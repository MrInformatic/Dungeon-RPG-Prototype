package rpg.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import rpg.dungeon.Engine.Drawable.DrawableSprite;
import rpg.dungeon.Engine.GUI.Bar;
import rpg.dungeon.Engine.Renderer.RendererMap;
import rpg.dungeon.Entity.Opponents.Bat;
import rpg.dungeon.Entity.Opponents.Opponent;
import rpg.dungeon.Entity.Player.Player;
import rpg.dungeon.Manager.ContactManager;
import rpg.dungeon.Manager.InputManager;
import rpg.dungeon.Shader.LightShader;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by philipp on 25.02.15.
 */
public class MyRenderer extends RendererMap {
    public Torches torches;
    public Player player;
    public InputManager inputManager;
    public ContactManager contactManager;
    public ShapeRenderer shapeRenderer;

    public MyRenderer(float speed, float fov, boolean vertical, String atlas, String skin, Color color, boolean debug, String MapFile) {
        super(speed, fov, vertical, atlas, skin, color, debug, MapFile);
        PostProcessGui = true;

        LightShader.init(map);

        torches = new Torches(map);
        player = new Player(map,world,this.skin,this.stage,"FireMage");

        camera.target = player.pos;

        drawables.add(player);
        drawables.add(torches);

        Opponent.registerallopponents(map,world,this,player,this.skin);

        drawableMap.shaderProgram = LightShader.lightShader;

        inputManager = new InputManager(player);
        contactManager = new ContactManager(world);

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void update(){
        super.update();
    }

    @Override
    public void render(){
        super.render();
        if(debug) {
            shapeRenderer.setProjectionMatrix(camera.combined.cpy().scl(24));
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (int i = 0; i < Opponent.allopponents.get("Bat").length; i++) {
                if(!Opponent.allopponents.get("Bat")[i].death) {
                    shapeRenderer.line(player.pos.cpy().scl(1f / 24f), Opponent.allopponents.get("Bat")[i].pos.cpy().scl(1f / 24f));
                }
            }
            shapeRenderer.end();
        }
    }

    @Override
    public void dispose(){
        shapeRenderer.dispose();
    }

    @Override
    public void resize(int width,int height){
        super.resize(width,height);
        player.resize(viewport.getWorldWidth(),viewport.getWorldHeight());
    }
}
