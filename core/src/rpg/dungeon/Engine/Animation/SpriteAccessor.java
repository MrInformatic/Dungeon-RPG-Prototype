package rpg.dungeon.Engine.Animation;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by philipp on 24.01.15.
 */
public class SpriteAccessor extends StandartAccessor {
    public static void addAccessor(Class c)
    {
        Tween.registerAccessor(c, new SpriteAccessor());
    }

    public static void PlayAnimation(Tween tween){
        tween.start(AnimationManager.manager);
    }

    @Override
    public int getValues(Object target, int tweenType, float[] returnValues) {
        Sprite a = (Sprite)target;
        if(tweenType == 0){
            returnValues[0]=a.getColor().r;
            returnValues[1]=a.getColor().g;
            returnValues[2]=a.getColor().b;
            returnValues[3]=a.getColor().a;
            return 4;
        }else if(tweenType == 1){
            returnValues[0]=a.getColor().r;
            returnValues[1]=a.getColor().g;
            returnValues[2]=a.getColor().b;
            return 3;
        }else if(tweenType == 2){
            returnValues[0]=a.getColor().r;
            return 1;
        }else if(tweenType == 3){
            returnValues[0]=a.getColor().g;
            return 1;
        }else if(tweenType == 4){
            returnValues[0]=a.getColor().b;
            return 1;
        }else if(tweenType == 5){
            returnValues[0]=a.getColor().a;
            return 1;
        }else if(tweenType == 6){
            returnValues[0]=a.getOriginX();
            returnValues[1]=a.getOriginY();
            return 2;
        }else if(tweenType == 7){
            returnValues[0]=a.getOriginX();
            return 1;
        }else if(tweenType == 8){
            returnValues[0]=a.getOriginY();
            return 1;
        }else if(tweenType == 9){
            returnValues[0]=a.getWidth();
            returnValues[1]=a.getHeight();
            return 2;
        }else if(tweenType == 10){
            returnValues[0]=a.getWidth();
            return 1;
        }else if(tweenType == 11){
            returnValues[0]=a.getHeight();
            return 1;
        }else if(tweenType == 12){
            returnValues[0]=a.getScaleX();
            returnValues[1]=a.getScaleY();
            return 2;
        }else if(tweenType == 13){
            returnValues[0]=a.getScaleX();
            return 1;
        }else if(tweenType == 14){
            returnValues[0]=a.getScaleY();
            return 1;
        }else if(tweenType == 15){
            returnValues[0]=a.getX();
            returnValues[1]=a.getY();
            return 2;
        }else if(tweenType == 16){
            returnValues[0]=a.getX();
            return 1;
        }else if(tweenType == 17){
            returnValues[0]=a.getY();
            return 1;
        }else if(tweenType == 18){
            returnValues[0]=a.getRotation();
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public void setValues(Object target, int tweenType, float[] newValues) {
        Sprite a = (Sprite)target;
        if(tweenType == 0){
            a.setColor(newValues[0],newValues[1],newValues[2],newValues[3]);
        }else if(tweenType == 1){
            a.setColor(newValues[0],newValues[1],newValues[2],a.getColor().a);
        }else if(tweenType == 2){
            a.setColor(newValues[0],a.getColor().g,a.getColor().b,a.getColor().a);
        }else if(tweenType == 3){
            a.setColor(a.getColor().r,newValues[0],a.getColor().b,a.getColor().a);
        }else if(tweenType == 4){
            a.setColor(a.getColor().r,a.getColor().g,newValues[0],a.getColor().a);
        }else if(tweenType == 5){
            a.setColor(a.getColor().r,a.getColor().g,a.getColor().b,newValues[0]);
        }else if(tweenType == 6){
            a.setOrigin(newValues[0],newValues[1]);
        }else if(tweenType == 7){
            a.setOrigin(newValues[0],a.getOriginY());
        }else if(tweenType == 8){
            a.setOrigin(a.getOriginX(),newValues[0]);
        }else if(tweenType == 9){
            a.setSize(newValues[0],newValues[1]);
        }else if(tweenType == 10){
            a.setSize(newValues[0],a.getHeight());
        }else if(tweenType == 11){
            a.setSize(a.getWidth(),newValues[0]);
        }else if(tweenType == 12){
            a.setScale(newValues[0],newValues[1]);
        }else if(tweenType == 13){
            a.setScale(newValues[0],a.getScaleY());
        }else if(tweenType == 14){
            a.setScale(a.getScaleX(),newValues[0]);
        }else if(tweenType == 15){
            a.setPosition(newValues[0],newValues[1]);
        }else if(tweenType == 16){
            a.setX(newValues[0]);
        }else if(tweenType == 17){
            a.setY(newValues[0]);
        }else if(tweenType == 18){
            a.setRotation(newValues[0]);
        }
    }

    @Override
    public int getTweenType(String name) {
        if(name.endsWith("ColorRGBA")){return 0;}
        else if(name.endsWith("ColorRGB")){return 1;}
        else if(name.endsWith("ColorR")){return 2;}
        else if(name.endsWith("ColorG")){return 3;}
        else if(name.endsWith("ColorB")){return 4;}
        else if(name.endsWith("ColorA")){return 5;}
        else if(name.endsWith("Orgin")){return 6;}
        else if(name.endsWith("OrginX")){return 7;}
        else if(name.endsWith("OrginY")){return 8;}
        else if(name.endsWith("Size")){return 9;}
        else if(name.endsWith("Width")){return 10;}
        else if(name.endsWith("Height")){return 11;}
        else if(name.endsWith("Scale")){return 12;}
        else if(name.endsWith("ScaleX")){return 13;}
        else if(name.endsWith("ScaleY")){return 14;}
        else if(name.endsWith("Position")){return 15;}
        else if(name.endsWith("PositionX")){return 16;}
        else if(name.endsWith("PositionY")){return 17;}
        else if(name.endsWith("Rotation")){return 18;}
        return -1;
    }

    public static class TweenType{
        public static final int ColorRGBA = 0;
        public static final int ColorRGB = 1;
        public static final int ColorR = 2;
        public static final int ColorG = 3;
        public static final int ColorB = 4;
        public static final int ColorA = 5;
        public static final int Origin = 6;
        public static final int OriginX = 7;
        public static final int OriginY = 8;
        public static final int Size = 9;
        public static final int Width = 10;
        public static final int Height = 11;
        public static final int Scale = 12;
        public static final int ScaleX = 13;
        public static final int ScaleY = 14;
        public static final int Position = 15;
        public static final int PositionX = 16;
        public static final int PositionY = 17;
        public static final int Rotation = 18;
    }
}
