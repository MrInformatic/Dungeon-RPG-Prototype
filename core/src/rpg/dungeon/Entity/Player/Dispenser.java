package rpg.dungeon.Entity.Player;

import com.badlogic.gdx.physics.box2d.World;
import rpg.dungeon.Engine.Drawable.DrawableParticel;
import rpg.dungeon.Engine.Drawable.DrawableParticelPool;
import rpg.dungeon.Engine.utils.Timer;

/**
 * Created by philipp on 26.03.15.
 */
public abstract class Dispenser extends DrawableParticelPool {
    public int mana = 5;
    public float cooldown = 1;
    public Timer cooldowntimer = new Timer();

    public Dispenser(Player player){
        super();
        cooldowntimer.start();
        player.childs.add(this);
    }

    public abstract void dispense(Player player,World world);

    public boolean candispense(Player player){
        if(cooldowntimer.getTime()>=cooldown){
            if(player.gui.mana.getValue()>=mana) {
                cooldowntimer.reset();
                cooldowntimer.start();
                player.gui.mana.setValue(player.gui.mana.getValue()-mana);
                return true;
            }
        }
        return false;
    }

    public int getcooldown(){
        return Math.max(0,Math.min(100,100-Math.round(cooldowntimer.getTime()/cooldown*100)));
    }

    @Override
    public void update(float delta){
        super.update(delta);
        cooldowntimer.update(delta);
    }
}
