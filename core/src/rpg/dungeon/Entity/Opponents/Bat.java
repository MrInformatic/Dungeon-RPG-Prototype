package rpg.dungeon.Entity.Opponents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import rpg.dungeon.Entity.Entity;
import rpg.dungeon.Entity.Player.Player;

import java.util.ArrayList;

/**
 * Created by philipp on 03.04.15.
 */
public class Bat extends Opponent{
    public Bat(World world,Rectangle rectangle,Player target,Skin skin) {
        super(rectangle, world, "Bat", 24, 24, Color.WHITE, target,skin);
    }

    @Override
    public void updateKI() {
        if(cansee){
            speed = target.pos.cpy().sub(this.pos).nor();
        }else{
            speed = Vector2.Zero;
        }
    }

    @Override
    public void punchPlayer(Player player) {
        player.gui.helth.setValue(player.gui.helth.getValue()-2);
    }
}