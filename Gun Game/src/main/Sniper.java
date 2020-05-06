package main;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.*;

public class Sniper extends gameObject{
    private long timer1;
    private Handler handler;
    public Sniper(int x, int y, ID id, int Health, int damage, int score, long time, Handler handler) {
        super(x, y, id, Health, damage, score);
        this.timer1 = time;
        this.handler = handler;
        for(int i = 0; i < handler.object.size(); i ++){
            gameObject tempObject = handler.object.get(i);
            if(tempObject.getid() == ID.Rifle){
                handler.removeObject(tempObject);
            }
        }
    }

    @Override
    public void tick() {
        if(System.currentTimeMillis() - timer1 > 2000 && score == 1){
            for(int i = 0; i < handler.object.size(); i ++){
                gameObject tempObject = handler.object.get(i);
                if(tempObject.getid() == ID.Player && tempObject.getVelX() == 0 && tempObject.getVelY() == 0) {
                    handler.addObject(new SniperRound(0, 0, ID.SniperRound, handler, 0, 0, 0));
                }else if(tempObject.getid() == ID.Player){
                    handler.addObject(new SniperRound(0, 0, ID.SniperRound, handler, 0, 0, 2));
                }
            }
            timer1 += System.currentTimeMillis() - timer1;
        }
    }

    @Override
    public void render(Graphics g) {

    }
}
