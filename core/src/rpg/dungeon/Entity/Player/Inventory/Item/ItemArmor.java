package rpg.dungeon.Entity.Player.Inventory.Item;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by philipp on 19.09.15.
 */
public class ItemArmor extends Item{
    public ItemArmorStyle itemArmorStyle;

    public ItemArmor(String name,Skin skin) {
        super(skin,name);
        itemArmorStyle = skin.get(name,ItemArmorStyle.class);
    }

    public int getLive(){
        return itemArmorStyle.live;
    }

    public int getProtection(){
        return itemArmorStyle.protection;
    }

    public int getMana(){
        return itemArmorStyle.mana;
    }

    public int getManaregen(){
        return itemArmorStyle.manaregen;
    }

    public int getType(){
        return itemArmorStyle.type;
    }

    public String getDescription(){
        return itemArmorStyle.description;
    }

    public static class ItemArmorStyle extends ItemStyle{
        public ItemArmorStyle(){

        }

        public int live;
        public int protection;
        public int mana;
        public int manaregen;
        public int type;
    }

    public static class ItemArmorTypes{
        public static final int helm = 0;
        public static final int main = 0;
        public static final int trousers = 0;
        public static final int shoes = 0;
    }
}
