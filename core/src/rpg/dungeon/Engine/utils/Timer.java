package rpg.dungeon.Engine.utils;

/**
 * Created by philipp on 24.01.15.
 */
public class Timer {
    private float time;
    private boolean test;

    public void start(){
        test = true;
    }

    public void stop(){
        test = false;
    }

    public float getTime(){
        return time;
    }

    public void setTime(float value){
        time = value;
    }

    public void reset() {
        time = 0;
        test = false;
    }

    public void update(float delta)
    {
        if(test)
        {
            time += delta;
        }
    }
}
