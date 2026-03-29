package rpg.dungeon.Shader;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by philipp on 12.02.15.
 */
public class Light{
    public float strength;
    public float intensity;
    public Vector2 position;

    public Light(float intensity,float str,float x,float y){
        this(intensity,str,new Vector2(x,y));
    }

    public Light(float intensity,float str,Vector2 pos){
        this.strength = str;
        this.position = pos;
        this.intensity = intensity;
    }
}
