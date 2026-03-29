package rpg.dungeon.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import rpg.dungeon.Entity.Player.Player;

/**
 * Created by philipp on 31.01.15.
 */
public class InputManager implements ControllerListener,InputProcessor {
    public Player player;
    private boolean potions = false;
    
    public InputManager(Player player){
        super();
        Gdx.input.setInputProcessor(this);
        Controllers.addListener(this);
        this.player = player;
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        if(!player.inventory.inventory) {
            if(!potions) {
                if (buttonCode == 0) {
                    player.dispense(0);
                }
                if (buttonCode == 1) {
                    player.dispense(1);
                }
                if (buttonCode == 2) {
                    player.dispense(2);
                }
                if (buttonCode == 3) {
                    player.dispense(3);
                }
            }else{

            }
            if (buttonCode == 4) {
                player.stop = true;
            }
            if (buttonCode == 5) {
                player.sneak = true;
            }
            if(buttonCode==6) {
                player.inventory.openinventory();
                potions = false;
            }
            if(buttonCode==7){
                potions = true;
            }
        }else {
            if(buttonCode==6){
                player.inventory.closeinventory();
            }
            if(buttonCode==3){
                player.inventory.changeinventory();
            }
        }if(buttonCode==9){
                //Pause
        }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if(buttonCode==0){
            player.enddispense(0);
        }if(buttonCode==1){
            player.enddispense(1);
        }if(buttonCode==2){
            player.enddispense(2);
        }if(buttonCode==3){
            player.enddispense(3);
        }if(buttonCode==4){
            player.stop = false;
        }if(buttonCode==5){
            player.sneak = false;
        }if(buttonCode==7){
            potions = false;
        }
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        if(player.inventory.inventory) {
            if(axisCode == 1){
                if (Math.abs(value) > Settings.deadzone) {
                    player.inventory.armor.setDescriptionScrollSpeed(value);
                }else{
                    player.inventory.armor.setDescriptionScrollSpeed(0);
                }
            }
            if(axisCode == 2){
                if (Math.abs(value) > Settings.deadzone) {
                    player.inventory.armor.setitemsHolderScrollSpeed(value);
                }else{
                    player.inventory.armor.setitemsHolderScrollSpeed(0);
                }
            }
        }else{
            if (axisCode == 0) {
                if (Math.abs(value) > Settings.deadzone) {
                    player.setSpeedX(value);
                } else {
                    player.setSpeedX(0);
                }
            } else if (axisCode == 1) {
                if (Math.abs(value) > Settings.deadzone) {
                    player.setSpeedY(-value);
                } else {
                    player.setSpeedY(0);
                }
            }
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.UP){
            player.setSpeedY(1);
        }else if(keycode == Input.Keys.DOWN) {
            player.setSpeedY(-1);
        }
        if(keycode == Input.Keys.LEFT){
            player.setSpeedX(-1);
        }else if(keycode == Input.Keys.RIGHT){
            player.setSpeedX(1);
        }
        if(keycode==Input.Keys.SPACE){
            player.dispense(0);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(!Gdx.input.isKeyPressed(Input.Keys.DOWN)&&!Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.setSpeedY(0);
        }if(!Gdx.input.isKeyPressed(Input.Keys.LEFT)&&!Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.setSpeedX(0);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
