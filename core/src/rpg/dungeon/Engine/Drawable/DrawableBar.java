package rpg.dungeon.Engine.Drawable;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.*;

/**
 * Created by philipp on 24.08.15.
 */
public class DrawableBar extends Drawable {
    public BarStyle barStyle;
    public int min, max, value;
    public int barregionx = 0, barregiony = 0;
    public Rectangle bar;

    public DrawableBar(Skin skin, String name, int min, int max, int value) {
        super();
        barStyle = skin.get(name, BarStyle.class);
        bar = new Rectangle();
        bar.setSize(barStyle.Background.getMinWidth(), barStyle.Background.getMinHeight());
        this.max = max;
        this.min = min;
        this.value = value;
        TextureRegion bar = ((TextureRegionDrawable) barStyle.Bar).getRegion();
        barregionx = bar.getRegionX();
        barregiony = bar.getRegionY();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Batch batch) {
        barStyle.Background.draw(batch, this.bar.getX(), this.bar.getY(), this.bar.getWidth(), this.bar.getHeight());
        TextureRegion bar = ((TextureRegionDrawable) barStyle.Bar).getRegion();
        if (barStyle.vert) {
            int h = Math.round((float) barStyle.start + (float) (barStyle.end - barStyle.start) / ((float) (max - min) / (float) (Math.min(Math.max(value, min), max) - min)));
            bar.setRegionY(barregiony + barStyle.end - h);
            bar.setRegionHeight(h);
        } else {
            bar.setRegionWidth(Math.round((float) barStyle.start + (float) (barStyle.end - barStyle.start) / ((float) (max - min) / (float) (Math.min(Math.max(value, min), max) - min))));
        }
        barStyle.Bar.draw(batch, this.bar.getX(), this.bar.getY(), bar.getRegionWidth(), bar.getRegionHeight());
    }

    @Override
    public void dispose() {
    }

    public static class BarStyle {
        public BarStyle() {
        }

        public com.badlogic.gdx.scenes.scene2d.utils.Drawable Background;
        public com.badlogic.gdx.scenes.scene2d.utils.Drawable Bar;
        public boolean vert = false;
        public int start = 0;
        public int end = 100;
    }

}
