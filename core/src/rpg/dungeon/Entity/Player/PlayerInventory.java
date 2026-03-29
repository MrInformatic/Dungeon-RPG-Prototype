package rpg.dungeon.Entity.Player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import rpg.dungeon.Engine.Drawable.Drawable;
import rpg.dungeon.Entity.Player.Inventory.Armor.InventoryArmor;
import rpg.dungeon.Entity.Player.Inventory.InventoryPotions;
import rpg.dungeon.Entity.Player.Inventory.Item.ItemArmor;

import java.util.ArrayList;

/**
 * Created by philipp on 19.09.15.
 */
public class PlayerInventory extends Drawable{
    public InventoryArmor armor;
    public InventoryPotions potions;
    public ItemArmor helm,main,trousers,shoes;
    public ItemArmor[] itemsArmor;
    public ArrayList<ItemArmor> items;
    public boolean inventory = false;
    public Player player;

    public PlayerInventory(Stage stage,Skin skin,Player player){
        armor = new InventoryArmor(skin,"Armor",player);
        stage.addActor(armor);
        armor.setVisible(false);
        potions = new InventoryPotions(skin,"Potions",player);
        stage.addActor(potions);
        potions.setVisible(false);
        this.player = player;
    }

    public void openinventory(){
        armor.setVisible(true);
        inventory = true;
        player.enddispense(0);
        player.enddispense(1);
        player.enddispense(2);
        player.enddispense(3);
        player.sneak = false;
        player.stop = false;
        player.setSpeedX(0);
        player.setSpeedY(0);
    }

    public void closeinventory(){
        armor.setVisible(false);
        potions.setVisible(false);
        inventory = false;
    }

    public void changeinventory(){
        if(armor.isVisible()){
            armor.setVisible(false);
            potions.setVisible(true);
        }else if(potions.isVisible()){
            potions.setVisible(false);
            armor.setVisible(true);
        }
    }

    public void resize(float width,float height){
        armor.setPosition((width-armor.getWidth())/2f,(height-armor.getHeight())/2f);
        armor.resize(width,height);
        potions.setPosition((width-potions.getWidth())/2f,(height-potions.getHeight())/2f);
        potions.resize(width,height);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Batch batch) {

    }

    @Override
    public void dispose() {

    }
}
