package rpg.dungeon.Entity.Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import rpg.dungeon.Engine.GUI.ColorChangeBar;
import rpg.dungeon.Entity.Entity;
import rpg.dungeon.Engine.Drawable.DrawableParticelPool;
import rpg.dungeon.Engine.utils.Timer;
import rpg.dungeon.Entity.Player.Inventory.Armor.InventoryArmor;
import rpg.dungeon.Entity.Player.Inventory.InventoryPotions;
import rpg.dungeon.Entity.Player.Inventory.Item.ItemArmor;
import rpg.dungeon.Shader.LightShader;

import java.util.ArrayList;

/**
 * Created by philipp on 29.01.15.
 */
public class Player extends Entity implements PlayerInput {
    public Dispenser[] dispensers;
    public int dispensevalue = -1;
    public Vector2 dispensedir = new Vector2();
    public DrawableParticelPool pool;
    public boolean stop = false;
    public boolean sneak = false;
    public PlayerGUI gui;
    public PlayerInventory inventory;
    public PlayerStyle style;

    public Player(TiledMap map,World world,Skin skin,Stage stage,String name) {
        super();
        shaderProgram = LightShader.lightShader;
        style = skin.get(name,PlayerStyle.class);
        CircleShape circle = new CircleShape();
        circle.setRadius(14f/24f-0.1f);
        Rectangle rectangle = ((RectangleMapObject)(getMapObjects(map,"Player")[0])).getRectangle();
        init("Player",circle,rectangle,world,"Player/Player.png",24,24,style.color,new Entity.EntitySettings(2f,0.1f));
        pool = new DrawableParticelPool();
        childs.add(pool);
        gui = new PlayerGUI(stage,skin,this);
        inventory = new PlayerInventory(stage,skin,this);
        childs.add(gui);
        childs.add(inventory);
        dispensers = new Dispenser[style.dispensersnames.length];
        for(int i=0;i<style.dispensersnames.length;i++){
            try {
                dispensers[i] = ((Dispenser)Class.forName(style.dispenserspakage+"."+style.dispensersnames[i]).getConstructor(Player.class).newInstance(this));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setSpeedX(float value) {
        speed.x = value;
        dispensedir.x = value;
    }

    @Override
    public void setSpeedY(float value) {
        speed.y = value;
        dispensedir.y = value;
    }

    public Vector2 getdir(){
        Vector2 d = dispensedir.nor();
        if(dispensedir.equals(Vector2.Zero)) {
            if (animation == StandartAnimations.idleup) {
                d = new Vector2(0, 1);
            } else if (animation == StandartAnimations.idledown) {
                d = new Vector2(0, -1);
            } else if (animation == StandartAnimations.idleleft) {
                d = new Vector2(-1, 0);
            } else if (animation == StandartAnimations.idleright) {
                d = new Vector2(1, 0);
            }
        }
        return d;
    }

    @Override
    public void dispense(int value){
        dispensevalue = value;
    }

    @Override
    public void enddispense(int value){
        dispensevalue = -1;
    }

    @Override
    public void update(float delta){
        super.update(delta);
        if(!stop) {
            if(sneak){
                settings.runspeed = 1;
                setAnimationspeed(0.2f / speed.len());
            }else {
                settings.runspeed = 2;
                setAnimationspeed(0.1f / speed.len());
            }
        }else{
            settings.runspeed = 0;
            setAnimationspeed(Float.MAX_VALUE);
            body.setLinearVelocity(Vector2.Zero);
        }
        if(dispensevalue>-1){
            try {
                dispensers[dispensevalue].dispense(this,body.getWorld());
            }catch (Exception e){}
        }
    }

    @Override
    public void dispose(){
        super.dispose();
        pool.dispose();
    }

    public void resize(float width,float height){
        inventory.resize(width,height);
        gui.resize(width,height);
    }

    public Vector2 getLeftHandPosition(){
        if(animation == StandartAnimations.goup || animation == StandartAnimations.godown || animation == StandartAnimations.idleup || animation == StandartAnimations.idledown){
            return new Vector2(sprite.getX()+2,sprite.getY()+12);
        }else if(animation == StandartAnimations.goleft || animation == StandartAnimations.goright || animation == StandartAnimations.idleleft || animation == StandartAnimations.idleright){
            return new Vector2(sprite.getX()+12,sprite.getY()+6);
        }
        return null;
    }

    public Vector2 getRightHandPosition(){
        if(animation == StandartAnimations.goup || animation == StandartAnimations.godown || animation == StandartAnimations.idleup || animation == StandartAnimations.idledown){
            return new Vector2(sprite.getX()+22,sprite.getY()+12);
        }else if(animation == StandartAnimations.goleft || animation == StandartAnimations.goright || animation == StandartAnimations.idleleft || animation == StandartAnimations.idleright){
            return new Vector2(sprite.getX()+12,sprite.getY()+16);
        }
        return null;
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public static class PlayerStyle{
        public PlayerStyle(){

        }

        public String actionsHolder;
        public String[] dispensersnames;
        public String dispenserspakage;
        public BitmapFont font;
        public Color color;
    }
}
