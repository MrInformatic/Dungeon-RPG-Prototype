package rpg.dungeon.Engine.GUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by philipp on 20.03.15.
 */
public class Bar extends Widget{
    public BarStyle barStyle;
    public int min,max;
    private int value;
    private int barregionx = 0,barregiony = 0;
    public TextureRegion bar;

    public Bar(Skin skin,String name,int min,int max,int value){
        super();
        barStyle = skin.get(name,BarStyle.class);
        setSize(barStyle.Background.getMinWidth(),barStyle.Background.getMinHeight());
        this.max = max;
        this.min = min;
        this.value = value;
        bar = new TextureRegion(((TextureRegionDrawable)barStyle.Bar).getRegion());
        barregionx = bar.getRegionX();
        barregiony = bar.getRegionY();
    }

    public Bar(BarStyle style,int min,int max,int value){
        super();
        barStyle = style;
        setSize(barStyle.Background.getMinWidth(),barStyle.Background.getMinHeight());
        this.max = max;
        this.min = min;
        this.value = value;
        bar = new TextureRegion(((TextureRegionDrawable)barStyle.Bar).getRegion());
        barregionx = bar.getRegionX();
        barregiony = bar.getRegionY();
    }



    public void setValue(int value) {
        this.value = value;
        if(barStyle.vert){
            int h = Math.round((float)barStyle.start + (float)(barStyle.end - barStyle.start) / ((float)(max - min)/(float)(Math.min(Math.max(value,min),max) - min)));
            bar.setRegionY(barregiony+barStyle.end-h);
            bar.setRegionHeight(h);
        }else {
            bar.setRegionWidth(Math.round((float)barStyle.start + (float)(barStyle.end - barStyle.start) / ((float)(max - min)/(float)(Math.min(Math.max(value,min),max) - min))));
        }
    }

    public int getValue(){
        return value;
    }

    @Override
    public void draw(Batch batch,float alpha){
        super.draw(batch,alpha);
        barStyle.Background.draw(batch, getX(), getY(), getWidth(), getHeight());
        batch.draw(bar,getX(),getY(),bar.getRegionWidth(),bar.getRegionHeight());
    }

    public static class BarStyle{
        public BarStyle(){
        }

        public Drawable Background;
        public Drawable Bar;
        public boolean vert = false;
        public int start = 0;
        public int end = 100;
    }
}
