package rpg.dungeon.Engine.Renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import rpg.dungeon.Engine.Drawable.Drawable;
import rpg.dungeon.Engine.Drawable.DrawableMap;
import rpg.dungeon.Engine.Loading.MapLoader;

/**
 * Created by philipp on 25.02.15.
 */
public class RendererMap extends RendererWorld {
    private static MapLoader mapLoader = new MapLoader();
    public TiledMap map;
    public DrawableMap drawableMap;

    public RendererMap(float speed, float fov, boolean vertical, String atlas, String skin, Color color, boolean debug,String MapFile) {
        super(speed, fov, vertical, atlas, skin, color, debug);
        map = mapLoader.load(MapFile,world);
        drawableMap = new DrawableMap(map);
    }

    @Override
    public void begin(){
        super.begin();
        drawableMap.renderLayer(batch,0);
    }

    @Override
    public void end(){
        drawableMap.renderLayer(batch,2);
        super.end();
    }
}
