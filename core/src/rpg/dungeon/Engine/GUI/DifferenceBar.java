package rpg.dungeon.Engine.GUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by philipp on 16.09.15.
 */
public class DifferenceBar extends Bar {
    public DifferenceBarStyle differenceBarStyle;
    public int value1,value2;
    public int moreregionx = 0;
    public int moreregiony = 0;
    public int lessregionx = 0;
    public int lessregiony = 0;
    public TextureRegion more;
    public TextureRegion less;

    public DifferenceBar(Skin skin, String name, int min, int max, int value1,int value2) {
        super(skin.get(name,DifferenceBarStyle.class), min, max, 0);
        differenceBarStyle = skin.get(name,DifferenceBarStyle.class);
        barStyle = differenceBarStyle;
        this.value1 = value1;
        this.value2 = value2;
        if(value1<value2){
            setValue(value1);
        }else{
            setValue(value2);
        }
        more = new TextureRegion(((TextureRegionDrawable)differenceBarStyle.more).getRegion());
        less = new TextureRegion(((TextureRegionDrawable)differenceBarStyle.less).getRegion());
        moreregionx = more.getRegionX();
        moreregiony = more.getRegionY();
        lessregionx = less.getRegionX();
        lessregiony = less.getRegionY();
        if(barStyle.vert){
            if(value1<value2){
                //more
            }else if(value1>value2){
                //less
            }
        }else{
            if(value1<value2){
                more.setRegionX(moreregionx+barStyle.start+bar.getRegionWidth());
                more.setRegionWidth(Math.round((float)(barStyle.end - barStyle.start) / ((float)(max - min)/(float)(Math.min(Math.max(value2-value1,min),max) - min))));
            }else if(value1>value2){
                less.setRegionX(lessregionx+barStyle.start+bar.getRegionWidth());
                less.setRegionWidth(Math.round((float)(barStyle.end - barStyle.start) / ((float)(max - min)/(float)(Math.min(Math.max(value1-value2,min),max) - min))));
            }
        }
    }

    public void setValue(int value,int witch) {
        if(witch==1){
            value1 = value;
        }else if(witch==2){
            value2 = value;
        }
        if(value1<value2){
            setValue(value1);
        }else{
            setValue(value2);
        }
        TextureRegion bar = ((TextureRegionDrawable)barStyle.Bar).getRegion();
        if(barStyle.vert){
            if(value1<value2){
                //more
            }else if(value1>value2){
                //less
            }
        }else{
            if(value1<value2){
                more.setRegionX(moreregionx+barStyle.start+bar.getRegionWidth());
                more.setRegionWidth(Math.round((float)(barStyle.end - barStyle.start) / ((float)(max - min)/(float)(Math.min(Math.max(value2-value1,min),max) - min))));
            }else if(value1>value2){
                less.setRegionX(lessregionx+barStyle.start+bar.getRegionWidth());
                less.setRegionWidth(Math.round((float)(barStyle.end - barStyle.start) / ((float)(max - min)/(float)(Math.min(Math.max(value1-value2,min),max) - min))));
            }
        }
    }

    public void draw(Batch batch,float alpha){
        super.draw(batch,alpha);
        if(barStyle.vert){
            if(value1<value2){
                //more
            }else if(value1>value2){
                //less
            }
        }else{
            if(value1<value2){
                differenceBarStyle.more.draw(batch,getX()+barStyle.start+bar.getRegionWidth(),getY(),more.getRegionWidth(),more.getRegionHeight());
                batch.draw(more,getX()+barStyle.start+bar.getRegionWidth(),getY(),more.getRegionWidth(),more.getRegionHeight());
            }else if(value1>value2){
                differenceBarStyle.less.draw(batch,getX()+barStyle.start+bar.getRegionWidth(),getY(),less.getRegionWidth(),less.getRegionHeight());
                batch.draw(less,getX()+barStyle.start+bar.getRegionWidth(),getY(),less.getRegionWidth(),less.getRegionHeight());
            }
        }
    }

    public static class DifferenceBarStyle extends BarStyle{
        public DifferenceBarStyle(){
            super();
        }

        public Drawable more;
        public Drawable less;
    }
}
