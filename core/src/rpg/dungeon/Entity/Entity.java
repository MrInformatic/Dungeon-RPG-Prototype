package rpg.dungeon.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import rpg.dungeon.Engine.Drawable.DrawableSprite;
import rpg.dungeon.Engine.utils.Timer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by philipp on 17.02.15.
 */
public abstract class Entity extends DrawableSprite implements ContactListener{
    public Animation<TextureRegion>[] animations;
    public Timer timer = new Timer();
    public Vector2 speed = new Vector2(0,0);
    public Body body;
    public int animation;
    public EntitySettings settings;
    public Vector2 pos = new Vector2();
    public static HashMap<String,TextureRegion[][]> allanimations = new HashMap<String,TextureRegion[][]>();

    public static void loadTextur(String name,String file,int width,int height){
        if(!allanimations.containsKey(name)){
            Texture texture = new Texture(file);
            allanimations.put(name,TextureRegion.split(texture,width,height));
        }
    }

    @SuppressWarnings("unchecked")
    public static Animation<TextureRegion>[] getAnimations(String name){
        TextureRegion[][] buffer = allanimations.get(name);
        Animation<TextureRegion>[] result = new Animation[buffer.length];
        for(int i=0;i<buffer.length;i++){
            result[i] = new Animation<>(1,new Array<TextureRegion>(buffer[i]),Animation.PlayMode.LOOP);
        }
        return result;
    }


    public Entity() {
        super();
    }

    public void init(String name,Shape shape,Rectangle rectangle,World world,String file,int animwidth,int animheight,Color color,EntitySettings settings){
        sprite.setSize(rectangle.width, rectangle.height);
        sprite.setPosition(rectangle.x, rectangle.y);
        sprite.setOriginCenter();
        sprite.setColor(color);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX()/24f+0.5f,sprite.getY()/24f+0.5f);

        FixtureDef fixturedef = new FixtureDef();
        fixturedef.shape = shape;
        fixturedef.restitution = 0f;
        fixturedef.density = 0f;
        fixturedef.friction = 1f;

        body = world.createBody(bodyDef);
        body.createFixture(fixturedef).setUserData(this);
        body.setFixedRotation(true);
        body.setUserData(this);

        shape.dispose();

        this.settings = settings;
        timer.start();

        loadTextur(name,file,animwidth,animheight);
        animations = getAnimations(name);
        sprite.setRegion(animations[0].getKeyFrames()[0]);
    }

    public void setAnimationspeed(float speed){
        settings.animationspeed = speed;
    }


    public void updateanim() {
        if (Math.abs(speed.x) > Math.abs(speed.y)) {
            if (speed.x < 0) {
                animation = StandartAnimations.goleft;
            } else if (speed.x > 0) {
                animation = StandartAnimations.goright;
            }
        } else if (Math.abs(speed.x) < Math.abs(speed.y)) {
            if (speed.y < 0) {
                animation = StandartAnimations.godown;
            } else if (speed.y > 0) {
                animation = StandartAnimations.goup;
            }
        }
        if(speed.equals(Vector2.Zero)){
            if(animation == StandartAnimations.goup){
                animation = StandartAnimations.idleup;
            }else if(animation == StandartAnimations.godown){
                animation = StandartAnimations.idledown;
            }else if(animation == StandartAnimations.goleft){
                animation = StandartAnimations.idleleft;
            }else if(animation == StandartAnimations.goright){
                animation = StandartAnimations.idleright;
            }
        }
    }

    @Override
    public void update(float delta){
        super.update(delta);
        timer.update(delta/settings.animationspeed);
        updateanim();
        body.setLinearVelocity(speed.cpy().scl(settings.runspeed));
        Vector2 tmp = body.getPosition().cpy().scl(24);
        sprite.setPosition(tmp.x - 12,tmp.y - 12);
        pos.x = tmp.x;
        pos.y = tmp.y;
        sprite.setRegion(animations[animation].getKeyFrame(timer.getTime()));
    }

    @Override
    public void render(Batch batch){
        super.render(batch);
    }

    @Override
    public void dispose(){
        super.dispose();
    }

    public static MapObject[] getMapObjects(Map map,String name){
        ArrayList<MapObject> result = new ArrayList<MapObject>();
        Iterator<MapObject> mapObjects = map.getLayers().get(1).getObjects().iterator();
        while (mapObjects.hasNext()){
            MapObject a = mapObjects.next();
            if (a.getName().equals(name)) {
                result.add(a);
            }
        }
        return result.toArray(new MapObject[0]);
    }

    public static class EntitySettings{
        public float runspeed;
        public float animationspeed;

        public EntitySettings() {

        }

        public EntitySettings(float runspeed,float animationspeed){
            this.runspeed = runspeed;
            this.animationspeed = animationspeed;
        }
    }

    public static class StandartAnimations{
        public static final int idleup = 0;
        public static final int idledown = 1;
        public static final int idleleft = 3;
        public static final int idleright = 2;
        public static final int goup = 4;
        public static final int godown = 5;
        public static final int goleft = 7;
        public static final int goright = 6;
    }
}
