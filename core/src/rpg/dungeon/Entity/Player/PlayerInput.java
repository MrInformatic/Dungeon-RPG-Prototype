package rpg.dungeon.Entity.Player;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by philipp on 31.01.15.
 */
public interface PlayerInput
{
    public void setSpeedX(float value);
    public void setSpeedY(float value);
    public void dispense(int value);
    public void enddispense(int value);
}
