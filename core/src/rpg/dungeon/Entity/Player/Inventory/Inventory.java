package rpg.dungeon.Entity.Player.Inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import rpg.dungeon.Entity.Player.Player;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;


/**
 * Created by philipp on 15.09.15.
 */
public class Inventory extends WidgetGroup{
    public InventoryStyle style;
    public float screenwidth;
    public float screenheight;
    public Player player;

    public Inventory(Skin skin,String name,Player player){
        super();
        style = skin.get(name,InventoryStyle.class);
        this.setSize(style.background.getMinWidth(),style.background.getMinHeight());
        this.player = player;
    }

    @Override
    public void draw(Batch batch,float alpha) {
        if (style.darker != null) {
            style.darker.draw(batch, 0, 0, screenwidth, screenheight);
        }
        style.background.draw(batch, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        super.draw(batch, alpha);
    }

    public void resize(float width,float height){
        screenwidth = width;
        screenheight = height;
    }

    public static class InventoryStyle{
        public InventoryStyle(){

        }

        public Drawable background;
        public Drawable darker;
    }
}
