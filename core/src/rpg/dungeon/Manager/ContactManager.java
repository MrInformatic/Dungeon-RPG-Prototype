package rpg.dungeon.Manager;

import com.badlogic.gdx.physics.box2d.*;
import rpg.dungeon.Entity.Opponents.Bat;

/**
 * Created by philipp on 16.02.15.
 */
public class ContactManager implements ContactListener{
    public ContactManager(World world) {
        super();
        world.setContactListener(this);
    }

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getBody().getUserData() != null) {
            if (scaninterfaces(contact.getFixtureA().getBody().getUserData().getClass(), ContactListener.class)) {
                ((ContactListener) contact.getFixtureA().getBody().getUserData()).beginContact(contact);
            }
        }
        if (contact.getFixtureB().getBody().getUserData() != null) {
            if (scaninterfaces(contact.getFixtureB().getBody().getUserData().getClass(), ContactListener.class)) {
                ((ContactListener) contact.getFixtureB().getBody().getUserData()).beginContact(contact);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        if (contact.getFixtureA().getBody().getUserData() != null) {
            if (scaninterfaces(contact.getFixtureA().getBody().getUserData().getClass(), ContactListener.class)) {
                ((ContactListener) contact.getFixtureA().getBody().getUserData()).endContact(contact);
            }
        }
        if (contact.getFixtureB().getBody().getUserData() != null) {
            if (scaninterfaces(contact.getFixtureB().getBody().getUserData().getClass(), ContactListener.class)) {
                ((ContactListener) contact.getFixtureB().getBody().getUserData()).endContact(contact);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if (contact.getFixtureA().getBody().getUserData() != null) {
            if (scaninterfaces(contact.getFixtureA().getBody().getUserData().getClass(), ContactListener.class)) {
                ((ContactListener) contact.getFixtureA().getBody().getUserData()).preSolve(contact,oldManifold);
            }
        }
        if (contact.getFixtureB().getBody().getUserData() != null) {
            if (scaninterfaces(contact.getFixtureB().getBody().getUserData().getClass(), ContactListener.class)) {
                ((ContactListener) contact.getFixtureB().getBody().getUserData()).preSolve(contact,oldManifold);
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        if (contact.getFixtureA().getBody().getUserData() != null) {
            if (scaninterfaces(contact.getFixtureA().getBody().getUserData().getClass(), ContactListener.class)) {
                ((ContactListener) contact.getFixtureA().getBody().getUserData()).postSolve(contact,impulse);
            }
        }
        if (contact.getFixtureB().getBody().getUserData() != null) {
            if (scaninterfaces(contact.getFixtureB().getBody().getUserData().getClass(), ContactListener.class)) {
                ((ContactListener) contact.getFixtureB().getBody().getUserData()).postSolve(contact,impulse);
            }
        }
    }

    private boolean scaninterfaces(Class c,Class i){
        Class cl = c;
        while(cl.getSuperclass()!=null){
            for(int n=0;n<cl.getInterfaces().length;n++){
                if(cl.getInterfaces()[n].equals(i)){
                    return true;
                }
            }
            cl = cl.getSuperclass();
        }
        return false;
    }
}
