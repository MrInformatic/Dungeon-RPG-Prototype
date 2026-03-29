package rpg.dungeon.Entity.Player.Inventory.Item;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import rpg.dungeon.Entity.Player.Inventory.Item.ItemArmor;

/**
 * Created by philipp on 24.09.15.
 */
public class ItemsHolder extends Widget {
    public ItemsHolderStyle style;
    private Item[] items;

    public ItemsHolder(Skin skin, String name){
        style = skin.get(name,ItemsHolderStyle.class);
    }

    public void setitems(Item[] value){
        items = value;
        this.setHeight(items.length*style.itemsHolder.getMinHeight()+items.length+1);
    }

    public Item[] getItems() {
        return items;
    }

    @Override
    public void draw(Batch batch,float alpha){
        super.draw(batch,alpha);
        for (int i=0;i<items.length;i++){
            style.itemsHolder.draw(batch,getX()+1,getY()+getHeight()-1-style.itemsHolder.getMinHeight()*(i+1)-i,style.itemsHolder.getMinWidth(),style.itemsHolder.getMinHeight());
        }
    }

    @Override
    public float getPrefWidth(){
        return getWidth();
    }

    @Override
    public float getPrefHeight(){
        return getHeight();
    }

    public static class ItemsHolderStyle{
        public Drawable itemsHolder;
    }
}
