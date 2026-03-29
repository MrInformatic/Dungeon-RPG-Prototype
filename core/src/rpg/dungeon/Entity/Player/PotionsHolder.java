package rpg.dungeon.Entity.Player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import rpg.dungeon.Engine.GUI.Bar;

import java.util.ArrayList;

/**
 * Created by philipp on 15.09.15.
 */
public class PotionsHolder extends WidgetGroup {
    public PotionsHolderStyle actionsHolderStyle;
    public Image[] actions;
    //26,3
    public PotionsHolder(Skin skin,String name) {
        super();
        actionsHolderStyle = skin.get(name,PotionsHolderStyle.class);
        setSize(actionsHolderStyle.Background.getMinWidth(),actionsHolderStyle.Background.getMinHeight());
        actions = new Image[4];
        for(int i=0;i<actions.length;i++){
            actions[i] = new Image();
            addActor(actions[i]);
        }
        actions[0].setPosition(5, 30);
        actions[1].setPosition(30, 55);
        actions[2].setPosition(30, 5);
        actions[3].setPosition(55, 30);
    }

    @Override
    public void draw(Batch batch,float alpha){
        actionsHolderStyle.Background.draw(batch,getX(),getY(),getWidth(),getHeight());
        super.draw(batch,alpha);
    }

    public static class PotionsHolderStyle{
        public PotionsHolderStyle(){
        }

        public Drawable Background;
    }
}
