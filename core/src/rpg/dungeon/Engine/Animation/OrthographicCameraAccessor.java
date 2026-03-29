package rpg.dungeon.Engine.Animation;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by philipp on 24.01.15.
 */
public class OrthographicCameraAccessor extends StandartAccessor {
    public static void addAccessor(Class c)
    {
        Tween.registerAccessor(c, new OrthographicCameraAccessor());
    }

    public static void PlayAnimation(Tween tween){
        tween.start(AnimationManager.manager);
    }

    @Override
    public int getValues(Object target, int tweenType, float[] returnValues) {
        OrthographicCamera a = (OrthographicCamera)target;
        if(tweenType == 1){
            returnValues[0]=a.position.x;
            returnValues[1]=a.position.y;
            return 2;
        }else if(tweenType == 2){
            returnValues[0]=a.position.x;
            return 1;
        }else if(tweenType == 3){
            returnValues[0]=a.position.y;
            return 1;
        }else if(tweenType == 4){
            returnValues[0]=a.zoom;
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public void setValues(Object target, int tweenType, float[] newValues) {
        OrthographicCamera a = (OrthographicCamera)target;
        if(tweenType == 1){
            a.position.x = newValues[0];
            a.position.y = newValues[1];
        }else if(tweenType == 2){
            a.position.x = newValues[0];
        }else if(tweenType == 3){
            a.position.y = newValues[0];
        }else if(tweenType == 4){
            a.zoom = newValues[0];
        }
    }

    @Override
    public int getTweenType(String name) {
        if(name.endsWith("Position")){return 1;}
        else if(name.endsWith("PositionX")){return 2;}
        else if(name.endsWith("PositionY")){return 3;}
        else if(name.endsWith("Zoom")){return 4;}
        return -1;
    }

    public static class TweenType{
        public static final int Position = 1;
        public static final int PositionX = 2;
        public static final int PositionY = 3;
        public static final int Zoom = 4;
    }
}
