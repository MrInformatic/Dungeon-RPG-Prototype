package rpg.dungeon.Entity.Player.FireMage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import rpg.dungeon.Engine.Drawable.DrawableParticel;
import rpg.dungeon.Entity.Opponents.Opponent;
import rpg.dungeon.Entity.Player.Dispenser;
import rpg.dungeon.Entity.Player.Player;

/**
 * Created by philipp on 17.02.15.
 */
public class FlameDispenser extends Dispenser {
    public boolean left = false;

    public FlameDispenser(Player player){
        super(player);
        mana = 5;
        cooldown = 0.1f;
    }

    @Override
    public void dispense(Player player, World world) {
        if(candispense(player)){
            if(left){
                Vector2 vleft = player.getLeftHandPosition();
                add(new Flame(vleft.x,vleft.y,player.getdir(),world,player));
                left = false;
            }else {
                Vector2 vright = player.getRightHandPosition();
                add(new Flame(vright.x,vright.y,player.getdir(),world,player));
                left = true;
            }
        }
    }

    public class Flame extends DrawableParticel implements ContactListener {
        public Body body;
        public Vector2 dir;
        public boolean destroy = false;
        public Player player;

        public Flame(float x, float y, Vector2 d, World world, Player player) {
            super(x, y, "Particel/Flame", "Particel/");

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

            this.dir = d.cpy();

            effect.getEmitters().get(0).getAngle().setHigh(this.dir.angle(), this.dir.angle());
            effect.getEmitters().get(0).getAngle().setLow(this.dir.angle(), this.dir.angle());

            body = world.createBody(bodyDef);
            body.createFixture(fixturedef);
            body.setFixedRotation(true);
            body.setLinearVelocity(this.dir.scl(6));
            body.setUserData(this);

            this.player = player;

            circle.dispose();
        }

        @Override
        public void render(Batch batch) {
            super.render(batch);
        }

        @Override
        public void update(float delta) {
            super.update(delta);
            if (body != null) {
                effect.setPosition(body.getPosition().x * 24f, body.getPosition().y * 24f);
            }
            if (timer.getTime() > 0.75f) {
                if (body != null) {
                    body.getWorld().destroyBody(body);
                    body = null;
                }
            }
            if (timer.getTime() > 2) {
                end = true;
                dispose();
            }
        }

        @Override
        public void beginContact(Contact contact) {
            if (contact.getFixtureA().getBody().getUserData() != player && contact.getFixtureB().getBody().getUserData() != player&& !(contact.getFixtureA().getBody().getUserData() instanceof SplashDispenser.Splash) && !(contact.getFixtureB().getBody().getUserData() instanceof SplashDispenser.Splash)) {
                effect.getEmitters().get(0).setPosition(-1000, -1000);
                if(Opponent.class.isInstance(contact.getFixtureA().getBody().getUserData())&&contact.getFixtureB().getBody()==body){
                    ((Opponent)contact.getFixtureA().getBody().getUserData()).live.value -= 10-timer.getTime()*10;
                }
                if(Opponent.class.isInstance(contact.getFixtureB().getBody().getUserData())&&contact.getFixtureA().getBody()==body){
                    ((Opponent)contact.getFixtureB().getBody().getUserData()).live.value -= 10-timer.getTime()*10;
                }
                timer.setTime(1);
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
