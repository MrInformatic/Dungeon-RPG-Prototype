package rpg.dungeon.Entity.Player.Inventory.Item;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import rpg.dungeon.Engine.GUI.BetterScrollPane;
import rpg.dungeon.Entity.Opponents.Bat;

/**
 * Created by philipp on 02.10.15.
 */
public class ItemsHolderPane extends BetterScrollPane {
    private int selected;
    public float selectorposition;
    public float selectorspeeed;
    public float selectedposition;
    public float pulsealpha = 1;
    public boolean pulsedirection;
    public ItemsHolderPaneStyle style;

    public ItemsHolderPane(Skin skin, String name, String holdername) {
        super(new ItemsHolder(skin,holdername),skin.get(name,ItemsHolderPaneStyle.class));
        getWidget().setPosition(0,0);
        style = skin.get(name,ItemsHolderPaneStyle.class);
        setStyle(style);
    }

    @Override
    public void act(float delta){
        if(pulsealpha<=0){
            pulsedirection = true;
        }
        if(pulsealpha>=1) {
            pulsedirection = false;
        }
        if(pulsedirection){
            pulsealpha += delta;
        }
        else{
            pulsealpha -= delta;
        }
        selectedposition += delta * selectorspeeed * 5f;
        selectedposition = Math.max(Math.min(selectedposition,((ItemsHolder)getWidget()).getItems().length),0);
        int selectedpositionround = Math.round(selectedposition);
        if(selected != selectedpositionround){
            setSelectedItem(selectedpositionround);
        }
    }

    @Override
    protected void drawChildren(Batch batch,float alpha){
        super.drawChildren(batch,alpha);
        batch.setColor(1,1,1,Math.min(Math.max(pulsealpha,0),1));
        style.selector.draw(batch,0,getHeight()-style.selector.getMinHeight()-selectorposition,style.selector.getMinWidth(),style.selector.getMinHeight());
        batch.setColor(1,1,1,1);
        style.background.draw(batch,0,0,getWidth(),getHeight());
    }

    @Override
    public void setBetterVelocityY(float value){
        selectorspeeed = value;
    }

    public void setSelectedItem(int value){
        selected = value;
        selectorposition = style.selectorsteps*selected;
        pulsealpha = 1;
        pulsedirection = false;
    }

    public int getSelectedItem(){
        return selected;
    }

    public void setitems(Item[] items){
        ((ItemsHolder)getWidget()).setitems(items);
    }

    public static class ItemsHolderPaneStyle extends ScrollPaneStyle{
        public ItemsHolderPaneStyle(){
            super();
        }

        public Drawable selector;
        public float selectorsteps;
    }
}
