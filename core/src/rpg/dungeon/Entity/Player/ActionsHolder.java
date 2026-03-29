package rpg.dungeon.Entity.Player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import rpg.dungeon.Engine.GUI.Bar;

import java.util.ArrayList;

/**
 * Created by philipp on 29.03.15.
 */
public class ActionsHolder extends WidgetGroup {
    public ActionsHolderStyle actionsHolderStyle;
    public Bar[] actions;

    public ActionsHolder(Skin skin,String name) {
        super();
        actionsHolderStyle = skin.get(name,ActionsHolderStyle.class);
        setSize(actionsHolderStyle.Background.getMinWidth(),actionsHolderStyle.Background.getMinHeight());
        actions = new Bar[actionsHolderStyle.actionsnames.length];
        for(int i=0;i<actions.length;i++){
            actions[i] = new Bar(skin,actionsHolderStyle.actionsnames[i],0,100,0);
            addActor(actions[i]);
        }
        actions[0].setPosition(5, 30);
        actions[1].setPosition(30, 55);
        actions[2].setPosition(30, 5);
        actions[3].setPosition(55, 30);
    }

    public void update(Dispenser[] dispensers){
        for(int i=0;i<4;i++){
            actions[i].setValue(dispensers[i].getcooldown());
        }
    }

    @Override
    public void draw(Batch batch,float alpha){
        actionsHolderStyle.Background.draw(batch,getX(),getY(),getWidth(),getHeight());
        super.draw(batch,alpha);
    }

    public static class ActionsHolderStyle{
        public ActionsHolderStyle(){
        }

        public Drawable Background;
        public String[] actionsnames;
    }
}
