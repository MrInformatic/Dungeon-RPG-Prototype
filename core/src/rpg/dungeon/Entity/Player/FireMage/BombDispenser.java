package rpg.dungeon.Entity.Player.FireMage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import rpg.dungeon.Engine.Drawable.DrawableParticel;
import rpg.dungeon.Engine.Drawable.DrawableParticelPool;
import rpg.dungeon.Entity.Opponents.Opponent;
import rpg.dungeon.Entity.Player.Player;
import rpg.dungeon.Entity.Player.Dispenser;

/**
 * Created by philipp on 07.02.15.
 */
public class BombDispenser extends Dispenser{
    public boolean left = false;

    public BombDispenser(Player player){
        super(player);
        mana = 35;
        cooldown = 0.5f;
    }

    @Override
    public void dispense(Player player,World world) {
        if(candispense(player)){
            if(left){
                Vector2 vleft = player.getLeftHandPosition();
                add(new Bomb(vleft.x,vleft.y,player.getdir(),world,player));
                left = false;
            }else {
                Vector2 vright = player.getRightHandPosition();
                add(new Bomb(vright.x,vright.y,player.getdir(),world,player));
                left = true;
            }
        }
    }

    public class Bomb extends DrawableParticel implements ContactListener {
        public Body body;
        public boolean destroy = false;
        public boolean dead = false;
        public Vector2 dir;
        public Player player;
        public BombSensor sensor;

        public Bomb(float x, float y, Vector2 dir, World world, Player player) {
            super(x, y, "Particel/Bomb", "Particel/");
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(x / 24f, y / 24f);
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.bullet = true;

            CircleShape circle = new CircleShape();
            circle.setRadius(4f / 24f);

            FixtureDef fixturedef = new FixtureDef();
            fixturedef.shape = circle;
            fixturedef.restitution = 0f;
            fixturedef.density = 0f;
            fixturedef.friction = 0f;
            fixturedef.isSensor = true;

            this.dir = dir.cpy();

            effect.getEmitters().get(0).getAngle().setHigh(this.dir.angle(), this.dir.angle());
            effect.getEmitters().get(0).getAngle().setLow(this.dir.angle(), this.dir.angle());

            body = world.createBody(bodyDef);
            body.createFixture(fixturedef);
            body.setFixedRotation(true);
            body.setLinearVelocity(this.dir.scl(4f));
            body.setUserData(this);

            this.player = player;

            circle.dispose();
        }

        public void render(Batch batch) {
            super.render(batch);

        }

        @Override
        public void update(float delta) {
            super.update(delta);
            if (body != null) {
                effect.setPosition(body.getPosition().x * 24f, body.getPosition().y * 24f);
            }
            if (timer.getTime() > 1f) {
                if (body != null) {
                    sensor = new BombSensor(body.getWorld(),body.getPosition());
                    body.getWorld().destroyBody(body);
                    body = null;
                }
            }
            if (timer.getTime() > 1.5f) {
                end = true;
                dispose();
            }
        }

        @Override
        public void dispose(){
            super.dispose();
            sensor.dispose();
        }

        @Override
        public void beginContact(Contact contact) {
            if (contact.getFixtureA().getBody().getUserData() != player && contact.getFixtureB().getBody().getUserData() != player && !(contact.getFixtureA().getBody().getUserData() instanceof BombSensor) && !(contact.getFixtureB().getBody().getUserData() instanceof BombSensor)&& !(contact.getFixtureA().getBody().getUserData() instanceof SplashDispenser.Splash) && !(contact.getFixtureB().getBody().getUserData() instanceof SplashDispenser.Splash)) {
                if (!dead) {
                    effect.getEmitters().get(0).setPosition(-1000, -1000);
                    effect.getEmitters().get(1).update(1 - timer.getTime());
                    timer.setTime(1);
                    dead = true;
                }
            }
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

        public class BombSensor implements ContactListener
        {

            public Body sensorbody;

            public BombSensor(World world,Vector2 pos){
                BodyDef bodyDef = new BodyDef();
                bodyDef.position.set(pos);
                bodyDef.type = BodyDef.BodyType.DynamicBody;
                bodyDef.bullet = true;

                CircleShape circle = new CircleShape();
                circle.setRadius(1f);

                FixtureDef fixturedef = new FixtureDef();
                fixturedef.shape = circle;
                fixturedef.restitution = 0f;
                fixturedef.density = 0f;
                fixturedef.friction = 0f;
                fixturedef.isSensor = true;

                sensorbody = world.createBody(bodyDef);
                sensorbody.createFixture(fixturedef);
                sensorbody.setFixedRotation(true);
                sensorbody.setUserData(this);

                circle.dispose();
            }

            public void dispose(){
                sensorbody.getWorld().destroyBody(sensorbody);
                sensorbody = null;
            }

            @Override
            public void beginContact(Contact contact) {
                if(Opponent.class.isInstance(contact.getFixtureA().getBody().getUserData())&&contact.getFixtureB().getBody()==sensorbody){
                    ((Opponent)contact.getFixtureA().getBody().getUserData()).live.value -= Math.max(1-(sensorbody.getPosition().dst(contact.getFixtureA().getBody().getPosition())-((Opponent)contact.getFixtureA().getBody().getUserData()).settings.radius/24f),0)*50;
                }
                if(Opponent.class.isInstance(contact.getFixtureB().getBody().getUserData())&&contact.getFixtureA().getBody()==sensorbody){
                    ((Opponent)contact.getFixtureB().getBody().getUserData()).live.value -= Math.max(1-(sensorbody.getPosition().dst(contact.getFixtureB().getBody().getPosition())-((Opponent)contact.getFixtureB().getBody().getUserData()).settings.radius/24f),0)*50;
                }
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
        }
    }
}