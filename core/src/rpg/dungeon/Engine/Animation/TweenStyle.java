package rpg.dungeon.Engine.Animation;

/**
 * Created by philipp on 06.01.15.
 */
public class TweenStyle {
    public String type;
    public float length;
    public float[] args;
    public String easy;

    public TweenStyle(TweenStyleHelper helper)
    {
        this.type = helper.type;
        this.length = helper.length;
        this.args = helper.args;
        this.easy = helper.easy;
    }

    public static class TweenStyleHelper {
        public String type;
        public float length;
        public float[] args;
        public String easy;
    }
}
