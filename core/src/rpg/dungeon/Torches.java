package rpg.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import rpg.dungeon.Engine.Drawable.Drawable;
import rpg.dungeon.Engine.Shader.DefaultShader;
import rpg.dungeon.Engine.utils.Timer;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by philipp on 14.02.15.
 */
public class Torches extends Drawable{
    public ArrayList<Sprite> torches = new ArrayList<Sprite>();
    public Animation<TextureRegion> burn;
    public Timer timer = new Timer();

    public Torches(TiledMap map) {
        super();

        timer = new Timer();
        timer.start();

        Texture torch = new Texture("Tileset/Torch.png");
        TextureRegion[][] buffer = TextureRegion.split(torch, 4, 8);
        burn = new Animation<>(0.1f, buffer[0]);
        burn.setPlayMode(Animation.PlayMode.LOOP);

        Iterator<MapObject> mapObjects = map.getLayers().get(1).getObjects().iterator();
        while (mapObjects.hasNext()){
            MapObject a = mapObjects.next();
            if(a instanceof RectangleMapObject) {
                RectangleMapObject b = (RectangleMapObject)a;
                if (b.getName().equals("Light")) {
                    add(b.getRectangle());
                }
            }
        }
    }

    private void add(com.badlogic.gdx.math.Rectangle rectangle) {
        torches.add(new Sprite(burn.getKeyFrame(0)));
        torches.get(torches.size()-1).setSize(4, 8);
        torches.get(torches.size()-1).setPosition(rectangle.x-2, rectangle.y - 4);
        torches.get(torches.size()-1).setOriginCenter();
    }

    @Override
    public void update(float delta) {
        updatechilds(delta);
        timer.update(delta);
        for(int i=0;i<torches.size();i++) {
            torches.get(i).setRegion(burn.getKeyFrame(timer.getTime()));
        }
    }

    @Override
    public void render(Batch batch) {
        renderchilds(batch);
        for(int i=0;i<torches.size();i++){
            torches.get(i).draw(batch);
        }
    }

    @Override
    public void dispose() {
        disposechilds();
    }
}
