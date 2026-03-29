package rpg.dungeon.Entity.Player.Inventory.Item;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by philipp on 19.09.15.
 */
public class Item {
    public ItemStyle itemStyle;
    public String name;

    public Item(Skin skin,String name){
        this.name = name;
        itemStyle = skin.get(name,ItemStyle.class);
    }

    public static class ItemStyle{
        public ItemStyle(){

        }

        public Drawable drawable;
        public String description;
    }
}
