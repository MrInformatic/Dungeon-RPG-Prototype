package rpg.dungeon.Entity.Player.FireMage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import rpg.dungeon.Engine.Drawable.DrawableParticel;
import rpg.dungeon.Engine.utils.Timer;
import rpg.dungeon.Entity.Opponents.Opponent;
import rpg.dungeon.Entity.Player.Dispenser;
import rpg.dungeon.Entity.Player.Player;

import java.util.ArrayList;

/**
 * Created by philipp on 20.02.15.
 */
public class SplashDispenser extends Dispenser {
    public SplashDispenser(Player player) {
        super(player);
        mana = 100;
        cooldown = 25;
    }

    @Override
    public void dispense(Player player, World world) {
        if(candispense(player)) {
            add(new Splash(player.pos.x, player.pos.y,world,player));
        }
    }

    public class Splash extends DrawableParticel implements ContactListener{
        public Body body;
        public Player player;
        public ArrayList<Opponent> opponents = new ArrayList<Opponent>();
        public Timer damagetimer;

        public Splash(float x, float y,World world,Player player) {
            super(x, y, "Particel/Splash", "Particel/");
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(x / 24f, (y+4) / 24f);
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.bullet = true;

            CircleShape circle = new CircleShape();
            circle.setRadius(1.8f);

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

            damagetimer = new Timer();
            damagetimer.start();
        }

        @Override
        public void render(Batch batch) {
            super.render(batch);
        }

        @Override
        public void update(float delta) {
            super.update(delta);
            damagetimer.update(delta);
            if(timer.getTime() > 10){
                if(body!=null) {
                    body.getWorld().destroyBody(body);
                    body = null;
                }
            }
            if (timer.getTime() > 12) {
                end = true;
                dispose();
            }
            if(damagetimer.getTime() > 0.5f){
                for (int i=0;i<opponents.size();i++){
                    opponents.get(i).live.value-=10;
                    damagetimer.reset();
                    damagetimer.start();
                }
            }
        }

        @Override
        public void dispose(){
            super.dispose();
            opponents.clear();
        }

        @Override
        public void beginContact(Contact contact) {
            if (contact.getFixtureA().getBody().getUserData() != player && contact.getFixtureB().getBody().getUserData() != player) {
                if(Opponent.class.isInstance(contact.getFixtureA().getBody().getUserData())&&contact.getFixtureB().getBody()==body){
                    addifnew((Opponent)contact.getFixtureA().getBody().getUserData());
                }
                if(Opponent.class.isInstance(contact.getFixtureB().getBody().getUserData())&&contact.getFixtureA().getBody()==body){
                    addifnew((Opponent)contact.getFixtureB().getBody().getUserData());
                }
            }
        }

        private void addifnew(Opponent opponent){
            boolean found = false;
            for(int i=0;i<opponents.size();i++){
                if(opponents.get(i)==opponent){
                    found = true;
                }
            }
            if(!found){
                opponents.add(opponent);
            }
        }

        @Override
        public void endContact(Contact contact) {
            if (contact.getFixtureA().getBody().getUserData() != player && contact.getFixtureB().getBody().getUserData() != player) {
                if(Opponent.class.isInstance(contact.getFixtureA().getBody().getUserData())&&contact.getFixtureB().getBody()==body){
                    removeiffound((Opponent) contact.getFixtureA().getBody().getUserData());
                }
                if(Opponent.class.isInstance(contact.getFixtureB().getBody().getUserData())&&contact.getFixtureA().getBody()==body){
                    removeiffound((Opponent) contact.getFixtureB().getBody().getUserData());
                }
            }
        }

        private void removeiffound(Opponent opponent){
            for(int i=0;i<opponents.size();i++){
                if(opponents.get(i)==opponent){
                    opponents.remove(i);
                    break;
                }
            }
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }
}
