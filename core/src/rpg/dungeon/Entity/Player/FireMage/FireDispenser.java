package rpg.dungeon.Entity.Player.FireMage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import rpg.dungeon.Engine.Drawable.DrawableParticel;
import rpg.dungeon.Engine.Drawable.DrawableParticelPool;
import rpg.dungeon.Entity.Opponents.Opponent;
import rpg.dungeon.Entity.Player.Dispenser;
import rpg.dungeon.Entity.Player.Player;

/**
 * Created by philipp on 16.03.15.
 */
public class FireDispenser extends Dispenser {
    public boolean left = false;

    public FireDispenser(Player player){
        super(player);
        mana = 5;
        cooldown = 0.1f;
    }

    @Override
    public void dispense(Player player, World world) {
        if(candispense(player)){
            add(new Fire(player.pos.x,player.pos.y,world,player));
        }
    }

    public class Fire extends DrawableParticel implements ContactListener{
        public Body body;
        public Player player;

        public Fire(float x, float y,World world,Player player) {
            super(x, y, "Particel/Fire", "Particel/");

            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(x / 24f, y / 24f);
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.bullet = true;

            CircleShape circle = new CircleShape();
            circle.setRadius(2f / 24f);

            FixtureDef fixturedef = new FixtureDef();
            fixturedef.shape = circle;
            fixturedef.restitution = 0f;
            fixturedef.density = 0f;
            fixturedef.friction = 0f;
            fixturedef.isSensor = true;

            body = world.createBody(bodyDef);
            body.createFixture(fixturedef);
            body.setFixedRotation(true);
            body.setUserData(this);

            circle.dispose();

            this.player = player;
        }

        @Override
        public void update(float delta) {
            super.update(delta);
            if(timer.getTime() > 3f){
                if(body!=null) {
                    body.getWorld().destroyBody(body);
                    body = null;
                }
            }
            if (timer.getTime() > 4f) {
                end = true;
                dispose();
            }
        }

        @Override
        public void beginContact(Contact contact) {
            if (contact.getFixtureA().getBody().getUserData() != player && contact.getFixtureB().getBody().getUserData() != player&& !(contact.getFixtureA().getBody().getUserData() instanceof SplashDispenser.Splash) && !(contact.getFixtureB().getBody().getUserData() instanceof SplashDispenser.Splash)) {
                effect.getEmitters().get(0).setPosition(-1000, -1000);
                if(Opponent.class.isInstance(contact.getFixtureA().getBody().getUserData())&&contact.getFixtureB().getBody()==body){
                    ((Opponent)contact.getFixtureA().getBody().getUserData()).live.value -= timer.getTime()/3f*10f;
                }
                if(Opponent.class.isInstance(contact.getFixtureB().getBody().getUserData())&&contact.getFixtureA().getBody()==body){
                    ((Opponent)contact.getFixtureB().getBody().getUserData()).live.value -= timer.getTime()/3f*10f;
                }
                timer.setTime(3);
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
