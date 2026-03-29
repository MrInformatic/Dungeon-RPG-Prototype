package rpg.dungeon.Engine.GUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by philipp on 16.09.15.
 */
public class ColorChangeBar extends Bar {
    public BarStyle[] barStyles;
    public TextureRegion[] textureRegions;

    public ColorChangeBar(Skin skin, String[] names, int min, int max, int value) {
        super(skin, names[0], min, max, value);
        barStyles = new BarStyle[names.length];
        textureRegions = new TextureRegion[names.length];
        for (int i=0;i<names.length;i++){
            barStyles[i] = skin.get(names[i],BarStyle.class);
            textureRegions[i] = new TextureRegion(((TextureRegionDrawable)barStyles[i].Bar).getRegion());
        }
    }

    @Override
    public void setValue(int value){
        int i = Math.min(Math.max((barStyles.length-1)-(int)Math.min(Math.floor(((float)value/(float)(max-min))*(barStyles.length)),barStyles.length-1),0),barStyles.length-1);
        barStyle = barStyles[i];
        bar = textureRegions[i];
        super.setValue(value);
    }

    @Override
    public void draw(Batch batch,float alpha){
        super.draw(batch,alpha);
    }
}
