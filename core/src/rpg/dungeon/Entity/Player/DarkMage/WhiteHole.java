package rpg.dungeon.Entity.Player.DarkMage;

import rpg.dungeon.Engine.Drawable.DrawableParticel;

/**
 * Created by philipp on 17.03.15.
 */
public class WhiteHole extends DrawableParticel {
    public WhiteHole(float x, float y) {
        super(x, y,"Particel/WhiteHole", "Particel/");
    }

    @Override
    public void update(float delta){
        super.update(delta);
        if(timer.getTime()>6f){
            end = true;
            dispose();
        }
    }
}
