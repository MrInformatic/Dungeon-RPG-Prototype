package rpg.dungeon.Engine.GUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by philipp on 24.09.15.
 */
public class ScrollingText extends ScrollPane{
    public float scrollspeed;
    public float scroll;

    public ScrollingText(Skin skin,String name,String labelname) {
        super(new Label("",skin,labelname),skin,name);
        ((Label)getWidget()).setWrap(true);
        getWidget().setHeight(((Label)getWidget()).getPrefHeight());
        setSmoothScrolling(true);
    }

    public void setLabelWidth(int value){
        getWidget().setWidth(value);
    }

    public void setLabelText(String text){
        ((Label)getWidget()).setText(text);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        scroll = Math.max(Math.min(scroll+scrollspeed*delta*100,getMaxY()),0);
        setScrollY(scroll);
    }

    @Override
    public void setVelocityY(float value){
        scrollspeed = value;
    }

    @Override
    protected void drawChildren(Batch batch,float alpha){
        super.drawChildren(batch,alpha);
        getStyle().background.draw(batch,0,0,getWidth(),getHeight());
    }
}
