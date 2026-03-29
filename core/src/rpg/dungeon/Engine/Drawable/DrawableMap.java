package rpg.dungeon.Engine.Drawable;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import rpg.dungeon.Engine.Drawable.Drawable;

import java.util.Iterator;

/**
 * Created by philipp on 13.02.15.
 */
public class DrawableMap extends Drawable{
    public TiledMap map;
    public ShaderProgram shaderProgram;

    public DrawableMap(TiledMap map) {
        this.map = map;
    }

    public void renderLayer(Batch batch,int layer) {
        renderchilds(batch);
        if (shaderProgram != null) {
            batch.setShader(shaderProgram);
        }
        MapLayer maplayer = (MapLayer) map.getLayers().get(layer);
        if (maplayer instanceof TiledMapTileLayer) {
            TiledMapTileLayer l = (TiledMapTileLayer) maplayer;
            for (int x = 0; x < l.getWidth(); x++) {
                for (int y = 0; y < l.getHeight(); y++) {
                    TiledMapTile t = l.getCell(x,y).getTile();
                    batch.draw(t.getTextureRegion(), l.getTileWidth() * x, l.getTileHeight() * y);
                }
            }
        }
        if (shaderProgram != null) {
            batch.setShader(null);
        }
    }

    @Override
    public void update(float delta) {
        updatechilds(delta);
    }

    @Override
    public void render(Batch batch) {
        renderchilds(batch);
        Iterator<MapLayer> layers = map.getLayers().iterator();
        while (layers.hasNext()){
            MapLayer maplayer = layers.next();
            if (maplayer instanceof TiledMapTileLayer) {
                TiledMapTileLayer l = (TiledMapTileLayer) maplayer;
                for (int x = 0; x < l.getWidth(); x++) {
                    for (int y = 0; y < l.getHeight(); y++) {
                        TiledMapTile t = l.getCell(x, y).getTile();
                        batch.draw(t.getTextureRegion(), l.getTileWidth() * x, l.getTileHeight() * y);
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        disposechilds();
        map.dispose();
    }
}
