package rpg.dungeon.Entity.Player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import rpg.dungeon.Engine.Drawable.Drawable;
import rpg.dungeon.Engine.GUI.ColorChangeBar;
import rpg.dungeon.Engine.utils.Timer;
import rpg.dungeon.Entity.Player.Inventory.Armor.InventoryArmor;
import rpg.dungeon.Entity.Player.Inventory.InventoryPotions;
import rpg.dungeon.Entity.Player.Inventory.Item.ItemArmor;

/**
 * Created by philipp on 19.09.15.
 */
public class PlayerGUI extends Drawable{
    public ActionsHolder actionsHolder;
    //public PotionsHolder potionsHolder;
    public ColorChangeBar helth,mana;
    public Table bars;
    public Timer manaregentimer = new Timer();
    public Player player;

    public PlayerGUI(Stage stage,Skin skin,Player player){
        this.actionsHolder = new ActionsHolder(skin,player.style.actionsHolder);
        stage.addActor(this.actionsHolder);
        //this.potionsHolder = new PotionsHolder(skin,"default");
        //stage.addActor(this.potionsHolder);
        //this.potionsHolder.setPosition(10, 10);
        bars = new Table(skin);
        String[] helthcolor = new String[4];helthcolor[0] = "grun";helthcolor[1] = "gelb";helthcolor[2] = "orange";helthcolor[3] = "rot";
        helth = new ColorChangeBar(skin,helthcolor,0,100,100);
        helthcolor = null;
        bars.add(helth).size(helth.getWidth(),helth.getHeight()).padBottom(5);
        bars.row();
        String[] manacolor = new String[3];manacolor[0] = "lila";manacolor[1] = "blau";manacolor[2] = "cyan";
        mana = new ColorChangeBar(skin,manacolor,0,100,100);
        manacolor = null;
        bars.add(mana).size(mana.getWidth(),mana.getHeight());
        stage.addActor(bars);
        this.player = player;
        manaregentimer.start();
    }

    public void resize(float width,float height){
        bars.setPosition(bars.getMinWidth()/2+10,height-bars.getMinHeight()/2f-10);
        actionsHolder.setPosition(width-actionsHolder.getWidth()-10,10);
    }

    @Override
    public void update(float delta) {
        if(mana.getValue()<mana.max) {
            manaregentimer.update(delta);
            if (manaregentimer.getTime() > 0.1f) {
                mana.setValue(mana.getValue()+2);
                manaregentimer.reset();
                manaregentimer.start();
            }
        }
        actionsHolder.update(player.dispensers);
    }

    @Override
    public void render(Batch batch) {

    }

    @Override
    public void dispose() {

    }
}
