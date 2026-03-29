package rpg.dungeon.Entity.Player.DarkMage;

import rpg.dungeon.Engine.Drawable.DrawableParticel;

/**
 * Created by philipp on 17.03.15.
 */
public class MagicPoison extends DrawableParticel {
    public MagicPoison(float x, float y) {
        super(x, y,"Particel/MagicPoison", "Particel/");
    }

    @Override
    public void update(float delta){
        super.update(delta);
        if(timer.getTime()>4f){
            end = true;
            dispose();
        }
    }
}
