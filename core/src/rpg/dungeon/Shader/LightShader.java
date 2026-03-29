package rpg.dungeon.Shader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import rpg.dungeon.Engine.Shader.DefaultShader;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by philipp on 12.02.15.
 */
public class LightShader extends ShaderProgram{
    public static ShaderProgram lightShader;
    public Light[] lights;
    public Color color;
    public static int lightCount;

    public static void init(TiledMap map){
        lightShader = new LightShader(Gdx.files.internal("shaders/lightvert.glsl"),Gdx.files.internal("shaders/light.glsl"),map,new Color(1,1,0.8f,1));
        //lightShader = DefaultShader.defaultshader;
    }

    public LightShader(FileHandle vertexShader, FileHandle fragmentShader,TiledMap map,Color color) {
        super("#define MaxLights "+getLightCount(map)+" \n"+vertexShader.readString(),"#define MaxLights "+getLightCount(map)+" \n"+fragmentShader.readString());
        Iterator objects = map.getLayers().get(1).getObjects().iterator();
        ArrayList<Light> lights = new ArrayList<Light>();
        while(objects.hasNext()){
            MapObject object = (MapObject)objects.next();
            if(object instanceof RectangleMapObject){
                if(object.getName().equals("Light")){
                    lights.add(new Light(0.5f, Float.parseFloat((String) object.getProperties().get("Strength")), ((RectangleMapObject) object).getRectangle().getX(), ((RectangleMapObject) object).getRectangle().getY()));
                }
            }
        }
        this.color = color;
        this.lights = lights.toArray(new Light[0]);
    }

    public static int getLightCount(TiledMap map){
        int result = 0;
        Iterator objects = map.getLayers().get(1).getObjects().iterator();
        while(objects.hasNext()){
            MapObject object = (MapObject)objects.next();
            if(object instanceof RectangleMapObject){
                if(object.getName().equals("Light")){
                    result++;
                }
            }
        }
        lightCount = result;
        return result;
    }

    @Override
    public void bind(){
        super.bind();
        float[] strength = new float[lights.length];
        float[] intensity = new float[lights.length];
        float[] position = new float[lights.length * 2];

        for (int i = 0; i < lights.length; i++) {
            strength[i] = lights[i].strength;
            intensity[i] = lights[i].intensity;
            position[i * 2] = lights[i].position.x;
            position[i * 2 + 1] = lights[i].position.y;
        }
        this.setUniform1fv("strength[0]", strength, 0, strength.length);
        this.setUniform1fv("intensity[0]", intensity, 0, intensity.length);
        this.setUniform2fv("Lights[0]", position, 0, position.length);
        this.setUniformf("u_color", color);
    }
}
