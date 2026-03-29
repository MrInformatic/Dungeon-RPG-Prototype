package rpg.dungeon.Entity.Opponents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import rpg.dungeon.Engine.Drawable.DrawableBar;
import rpg.dungeon.Engine.GUI.Bar;
import rpg.dungeon.Engine.Renderer.Renderer;
import rpg.dungeon.Engine.utils.Timer;
import rpg.dungeon.Entity.Entity;
import rpg.dungeon.Entity.Player.FireMage.BombDispenser;
import rpg.dungeon.Entity.Player.FireMage.FireDispenser;
import rpg.dungeon.Entity.Player.FireMage.FlameDispenser;
import rpg.dungeon.Entity.Player.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by philipp on 02.04.15.
 */
public abstract class Opponent extends Entity{
    public static Class[] opponentsclass = new Class[]{
            Bat.class};
    public Player target;
    public Class[] ignoring = new Class[]{Entity.class, FlameDispenser.Flame.class, BombDispenser.Bomb.class, BombDispenser.Bomb.BombSensor.class, FireDispenser.Fire.class};
    public boolean cansee = false;
    public static HashMap<String,Opponent[]> allopponents = new HashMap<String, Opponent[]>();
    public DrawableBar live;
    public boolean death = false;
    public boolean contact = false;
    public Player contactplayer;
    public Timer cooldowntimer;
    public OpponentSettings settings;

    public static void registerallopponents(Map map,World world,Renderer renderer,Player target,Skin skin){
        for(int i=0;i<opponentsclass.length;i++){
            allopponents.put(opponentsclass[i].getSimpleName(),getallOpponents(opponentsclass[i], map, world, target,skin));
        }
        Iterator<Opponent[]> opponents = allopponents.values().iterator();
        while (opponents.hasNext()){
            Opponent[] opponent = opponents.next();
            for(int n=0;n<opponent.length;n++){
                renderer.drawables.add(opponent[n]);
            }
        }
    }

    @Override
    public void update(float delta) {
        if(live.value>0) {
            super.update(delta);
            if (this.pos.dst(target.pos) < settings.lookradius) {
                RayCastCallback result = new RayCastCallback() {
                    @Override
                    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                        if (target.equals(fixture.getBody().getUserData())) {
                            cansee = true;
                            return 0;
                        } else {
                            for (int i = 0; i < ignoring.length; i++) {
                                if (ignoring[i].isInstance(fixture.getBody().getUserData())) {
                                    return -1;
                                }
                            }
                        }
                        cansee = false;
                        return 0;
                    }
                };
                body.getWorld().rayCast(result, this.pos.cpy().scl(1f / 24f), target.pos.cpy().scl(1f / 24f));
            }
            updateKI();
            live.bar.setPosition(sprite.getX() + (sprite.getWidth() - live.bar.getWidth()) / 2f, sprite.getY() + sprite.getHeight());
            cooldowntimer.update(delta);
            if(settings.distance){
                if(cansee){
                    if(cooldowntimer.getTime()>=settings.cooldown){
                        punchPlayer(contactplayer);
                        cooldowntimer.reset();
                        cooldowntimer.start();
                    }
                }
            }else {
                if (contact) {
                    if(cooldowntimer.getTime()>=settings.cooldown) {
                        punchPlayer(contactplayer);
                        cooldowntimer.reset();
                        cooldowntimer.start();
                    }
                }
            }
        } else {
            if(!death) {
                Death();
            }
        }
    }

    public void Death(){
        death = true;
        body.getWorld().destroyBody(body);
    }

    @Override
    public void render(Batch batch) {
        if(live.value>0) {
            super.render(batch);
        }
    }

    public abstract void updateKI();

    public Opponent(Rectangle rectangle,World world,String name,int animwidth,int animheight,Color color,Player target,Skin skin){
        super();
        settings = skin.get(name,OpponentSettings.class);
        CircleShape circle = new CircleShape();
        circle.setRadius(settings.radius/24f-0.1f);
        this.target = target;
        init(name,circle,rectangle,world,"Monster/"+name+".png",animwidth,animheight,color,settings);
        this.animation = StandartAnimations.idledown;
        this.live = new DrawableBar(skin,"Opponent",0,settings.maxlive,settings.maxlive);
        this.childs.add(this.live);
        cooldowntimer = new Timer();
        cooldowntimer.start();
    }

    public static Opponent[] getallOpponents(Class c,Map map,World world,Player target,Skin skin){
        ArrayList<Opponent> opponents = new ArrayList<Opponent>();
        MapObject[] opponentsr = getMapObjects(map,c.getSimpleName());
        for(int i=0;i<opponentsr.length;i++){
            if(opponentsr[i] instanceof RectangleMapObject){
                try {
                    opponents.add((Opponent) c.getConstructor(World.class,Rectangle.class,Player.class,Skin.class).newInstance(world, ((RectangleMapObject) opponentsr[i]).getRectangle(),target,skin));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return opponents.toArray(new Opponent[0]);
    }

    public abstract void punchPlayer(Player player);

    @Override
    public void beginContact(Contact contact) {
        if(Player.class.isInstance(contact.getFixtureA().getUserData())){
            contactplayer = (Player) contact.getFixtureA().getUserData();
            this.contact = true;
        }
        else if(Player.class.isInstance(contact.getFixtureB().getUserData())){
            contactplayer = (Player) contact.getFixtureB().getUserData();
            this.contact = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        if(Player.class.isInstance(contact.getFixtureA().getUserData())){
            contactplayer = null;
            this.contact = false;
        }
        else if(Player.class.isInstance(contact.getFixtureB().getUserData())){
            contactplayer = null;
            this.contact = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public static class OpponentSettings extends EntitySettings{
        public float radius;
        public float cooldown;
        public boolean distance;
        public float lookradius;
        public int maxlive;
    }
}
