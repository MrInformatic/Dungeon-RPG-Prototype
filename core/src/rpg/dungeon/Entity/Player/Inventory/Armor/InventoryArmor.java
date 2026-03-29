package rpg.dungeon.Entity.Player.Inventory.Armor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import rpg.dungeon.Engine.GUI.BetterScrollPane;
import rpg.dungeon.Engine.GUI.DifferenceBar;
import rpg.dungeon.Engine.GUI.ScrollingText;
import rpg.dungeon.Entity.Player.Inventory.Item.ItemArmor;
import rpg.dungeon.Entity.Player.Inventory.Item.ItemsHolder;
import rpg.dungeon.Entity.Player.Inventory.Inventory;
import rpg.dungeon.Entity.Player.Inventory.Item.ItemsHolderPane;
import rpg.dungeon.Entity.Player.Player;

/**
 * Created by philipp on 16.09.15.
 */
public class InventoryArmor extends Inventory {
    public DifferenceBar[] differenceBars;
    public ScrollingText description;
    public ItemsHolderPane itemsHolderpane;

    public InventoryArmor(Skin skin, String name,Player player) {
        super(skin, name, player);
        differenceBars = new DifferenceBar[4];
        for (int i = 0; i < differenceBars.length; i++) {
            differenceBars[i] = new DifferenceBar(skin, "armor", 0, 1000, 0, 0);
            addActor(differenceBars[i]);
        }
        differenceBars[0].setPosition(62,94);
        differenceBars[1].setPosition(62,74);
        differenceBars[2].setPosition(62,54);
        differenceBars[3].setPosition(62,34);
        itemsHolderpane = new ItemsHolderPane(skin,"armoritems","armor");
        itemsHolderpane.setPosition(166,31);
        itemsHolderpane.setSize(123,178);
        itemsHolderpane.setitems(new ItemArmor[10]);
        addActor(itemsHolderpane);
        description = new ScrollingText(skin,"description","description");
        description.setLabelWidth(94);
        description.setSize(92,97);
        description.setPosition(61, 112);
        description.setLabelText("Test Test Test Test Test Test Test Test Test Test Test Test. Test Test Test Test Test Test Test Test Test Test Test Test. Test Test Test Test Test Test Test Test Test Test Test Test. Test Test Test Test Test Test Test Test Test Test Test Test. Test Test Test Test Test Test Test Test Test Test Test Test.");
        addActor(description);
    }

    @Override
    public void draw(Batch batch,float alpha){
        super.draw(batch,alpha);
    }

    public void setDescriptionScrollSpeed(float value){
        description.setVelocityY(value);
    }

    public void setitemsHolderScrollSpeed(float value){
        itemsHolderpane.setBetterVelocityY(value);
    }
}
