package rpg.dungeon.Engine.Animation;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.TweenUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import rpg.dungeon.Engine.Animation.OrthographicCameraAccessor;
import rpg.dungeon.Engine.Animation.StandartAccessor;
import rpg.dungeon.Engine.Animation.TweenStyle;
import rpg.dungeon.Engine.Renderer.utils.SlidingCamera;

/**
 * Created by philipp on 03.01.15.
 */
public class AnimationManager {
    public static TweenManager manager = new TweenManager();

    public static void init(){
        OrthographicCameraAccessor.addAccessor(SlidingCamera.class);
    }

    public static void update(float delta){
        manager.update(delta);
    }

    public static TweenStyle loadTween(Skin skin,String name)
    {
        try {
            Tween.setCombinedAttributesLimit(4);
            return new TweenStyle(skin.get(name, TweenStyle.TweenStyleHelper.class));
        }catch (Exception e){return null;}
    }

    public static void playTween(Object target,TweenStyle style) {
        try {
            Tween.to(target, ((StandartAccessor) Tween.getRegisteredAccessor(target.getClass())).getTweenType(style.type), style.length).target(style.args).ease(TweenUtils.parseEasing(style.easy)).start(manager);
        }catch(Exception e){e.printStackTrace();}
    }

    public static TweenStyle[] loadTweens(Skin skin,String[] names)
    {
        try {
            TweenStyle[] result = new TweenStyle[names.length];
            for(int i=0;i<names.length;i++)
            {
                result[i] = loadTween(skin,names[i]);
            }
            return result;
        }catch (Exception e){return null;}
    }

    public static void playTweens(Object target,TweenStyle[] styles) {
        try {
            for(int i=0;i<styles.length;i++)
            {
                playTween(target,styles[i]);
            }
        }catch(Exception e){}
    }
}
