package rpg.dungeon.Engine.GUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

/**
 * Created by philipp on 25.09.15.
 */
public class BetterScrollPane extends ScrollPane {
    public float scrollspeed;
    public float scroll;

    public BetterScrollPane(Widget widget,Skin skin,String name) {
        super(widget,skin,name);
        setSmoothScrolling(true);
    }

    public BetterScrollPane(Widget widget,ScrollPaneStyle style){
        super(widget,style);
        setSmoothScrolling(true);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        scroll = Math.max(Math.min(scroll+scrollspeed*delta*100,getMaxY()),0);
        setScrollY(scroll);
    }

    public void setBetterVelocityY(float value){
        scrollspeed = value;
    }

    @Override
    protected void drawChildren(Batch batch,float alpha){
        super.drawChildren(batch,alpha);
        getStyle().background.draw(batch,0,0,getWidth(),getHeight());
    }
}
