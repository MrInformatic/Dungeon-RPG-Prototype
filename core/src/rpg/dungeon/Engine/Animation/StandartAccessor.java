package rpg.dungeon.Engine.Animation;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by philipp on 06.01.15.
 */
public abstract class StandartAccessor implements TweenAccessor {
    public abstract int getTweenType(String name);
}
