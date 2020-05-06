package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private boolean uP = false;
    private boolean dP = false;
    private boolean lP = false;
    private boolean rP = false;
    private Handler handler;

    public KeyInput(Handler handler){
        this.handler = handler;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        for(int i = 0; i < handler.object.size(); i ++) {
            gameObject tempObject = handler.object.get(i);
            if (tempObject.getid() == ID.Player) {
                //key events for player 1

                if(key == KeyEvent.VK_W ){
                    uP = true;
                    tempObject.setVelY(-4);
                }
                if(key == KeyEvent.VK_D ){
                    rP = true;
                    tempObject.setVelX(4);
                }
                if(key == KeyEvent.VK_S ){
                    dP = true;
                    tempObject.setVelY(4);
                }
                if(key == KeyEvent.VK_A ){
                    lP = true;
                    tempObject.setVelX(-4);
                }

            }


        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        for(int i = 0; i < handler.object.size(); i ++) {
            gameObject tempObject = handler.object.get(i);
            if (tempObject.getid() == ID.Player) {
                //Key Release Events
                if (key == KeyEvent.VK_W) {
                    uP = false;
                    if (!dP) {
                        tempObject.setVelY(0);
                    }
                }
                if (key == KeyEvent.VK_D) {
                    rP = false;
                    if (!lP) {
                        tempObject.setVelX(0);
                    }
                }
                if (key == KeyEvent.VK_S) {
                    dP = false;
                    if (!uP) {
                        tempObject.setVelY(0);
                    }
                }
                if (key == KeyEvent.VK_A) {
                    lP = false;
                    if (!rP) {
                        tempObject.setVelX(0);
                    }
                }
            }
        }

    }
}
