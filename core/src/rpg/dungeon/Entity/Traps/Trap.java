package rpg.dungeon.Entity.Traps;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import rpg.dungeon.Entity.Entity;

/**
 * Created by philipp on 01.09.15.
 */
public abstract class Trap extends Entity {
    public TrapSettings settings;

    public Trap(String name,Rectangle rectangle,World world,int animwidth,int animheight,Color color,Skin skin,int dir){
        super();
        settings = skin.get("name",TrapSettings.class);
        PolygonShape box = new PolygonShape();
        box.setAsBox(rectangle.getWidth(),rectangle.getHeight(),rectangle.getCenter(Vector2.Zero),0f);
        init(name,box,rectangle,world,"Trap/"+name+".png",animwidth,animheight,color,settings);
        if(dir == Direction.up){
            speed = new Vector2(0,1);
        }else if(dir == Direction.down){
            speed = new Vector2(0,-1);
        }else if(dir == Direction.left){
            speed = new Vector2(-1,0);
        }else if(dir == Direction.right){
            speed = new Vector2(1,0);
        }
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

    public static class TrapSettings extends EntitySettings{
        public TrapSettings(){
            this.runspeed = 0;
        }
    }

    public static class Direction{
        public static final int up = 0;
        public static final int down = 0;
        public static final int left = 0;
        public static final int right = 0;
    }
}
