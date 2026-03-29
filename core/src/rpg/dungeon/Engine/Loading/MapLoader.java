package rpg.dungeon.Engine.Loading;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.XmlReader;

import java.util.Iterator;

/**
 * Created by philipp on 29.01.15.
 */
public class MapLoader extends TmxMapLoader
{
    private Rectangle[][] collider;

    public MapLoader(){
        super();
    }

    public TiledMap load (String fileName,World world) {
        collider = new Rectangle[18][];
        TiledMap map = super.load(fileName);
        init(world, map);
        Iterator TileSets = map.getTileSets().iterator();
        while(TileSets.hasNext()){
            Iterator TileSet = ((TiledMapTileSet)TileSets.next()).iterator();
            while (TileSet.hasNext()){
                TiledMapTile tile = (TiledMapTile) TileSet.next();
                tile.getTextureRegion().getTexture().setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Nearest);
            }
        }
        return map;
    }

    private void init(World world,TiledMap map)
    {
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
        TiledMapTileLayer layercopy = new TiledMapTileLayer(layer.getWidth(),layer.getHeight(),Math.round(layer.getTileWidth()),Math.round(layer.getTileHeight()));
        int pos = map.getTileSets().getTileSet(0).size();
        for(int x=0;x<layercopy.getWidth();x++)
        {
            for(int y=0;y<layercopy.getHeight();y++)
            {
                layercopy.setCell(x,y,new TiledMapTileLayer.Cell());
                layercopy.getCell(x,y).setTile(map.getTileSets().getTile(pos+layer.getCell(x,y).getTile().getId()));
            }
        }
        map.getLayers().add(layercopy);
        float w = layer.getTileWidth();
        float h = layer.getTileHeight();
        for(int x=0;x<layer.getWidth();x++)
        {
            for(int y=0;y<layer.getHeight();y++)
            {
                Rectangle[] rectangles = collider[layer.getCell(x,y).getTile().getId()-1];
                for(int i=0;i<rectangles.length;i++)
                {
                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set((w*x+(rectangles[i].getX())+rectangles[i].getWidth()/2f)/24f,(h*y+(24-rectangles[i].getY())-rectangles[i].getHeight()/2f)/24f);

                    PolygonShape box = new PolygonShape();
                    box.setAsBox(rectangles[i].getWidth()/2f/24f,rectangles[i].getHeight()/2f/24f);

                    Body body = world.createBody(bodyDef);
                    body.createFixture(box,0.0f);

                    box.dispose();
                }
            }
        }
    }

    @Override
    protected void loadTileSet (XmlReader.Element element, FileHandle tmxFile, ImageResolver imageResolver) {
        super.loadTileSet(element,tmxFile,imageResolver);
        if(element.getAttribute("name").equals("Ground")) {
            for (int i = 0; i < element.getChildCount(); i++) {
                if (element.getChild(i).getName().equals("tile")) {
                    if (element.getChild(i).getChildCount() > 0) {
                        XmlReader.Element e1 = element.getChild(i).getChild(0);
                        Rectangle[] rectangles = new Rectangle[e1.getChildCount()];
                        for (int n = 0; n < e1.getChildCount(); n++) {
                            XmlReader.Element e2 = e1.getChild(n);
                            rectangles[n] = new Rectangle(e2.getFloatAttribute("x"), e2.getFloatAttribute("y"), e2.getFloatAttribute("width"), e2.getFloatAttribute("height"));
                        }
                        collider[element.getChild(i).getIntAttribute("id")] = rectangles;
                    } else {
                        collider[element.getChild(i).getIntAttribute("id")] = new Rectangle[0];
                    }
                }
            }
        }
    }
}
