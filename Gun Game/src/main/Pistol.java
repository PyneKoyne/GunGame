package main;

import java.awt.*;

public class Pistol extends gameObject{
    private long time;
    private Handler handler;
    public Pistol(int x, int y, ID id, int Health, int damage, int score, long time, Handler handler) {
        super(x, y, id, Health, damage, score);
        this.handler = handler;
        this.time = time;
    }

    @Override
    public void tick() {
        if(score == 1 && System.currentTimeMillis() - time > 1000){
            handler.addObject(new Bullet(0, 0, ID.Bullet, handler, 0, 0, 0));
            time += System.currentTimeMillis() - time;
        }
    }

    @Override
    public void render(Graphics g) {

    }
}
