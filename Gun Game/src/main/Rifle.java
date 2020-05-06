package main;

import java.awt.*;

public class Rifle extends gameObject{
    private long timer1;
    private Handler handler;
    private int total3 = 0;
    public Rifle(int x, int y, ID id, int Health, int damage, int score, long time, Handler handler) {
        super(x, y, id, Health, damage, score);
        timer1 = time;
        this.handler = handler;
        for(int i = 0; i < handler.object.size(); i ++){
            gameObject tempObject = handler.object.get(i);
            if(tempObject.getid() == ID.Pistol){
                handler.removeObject(tempObject);
            }
        }
    }

    @Override
    public void tick() {
        if(System.currentTimeMillis() - timer1 > 1100 && score == 1){
            if(total3 == 0){
                handler.addObject(new Bullet(0,0, ID.Bullet, handler, 0, 0, 0));
            }
            if(total3 > 0) {
                handler.addObject(new Bullet(0, 0, ID.Bullet, handler, 0, 0, 2));
            }
            timer1 += 150;
            total3 ++;
        }
        if (total3 > 2){
            total3 = 0;
            timer1 += System.currentTimeMillis() - timer1;
        }
            if(System.currentTimeMillis() - timer1 > 1400){
        timer1 += System.currentTimeMillis() - timer1;
        total3 = 0;
        }
    }

    @Override
    public void render(Graphics g) {

    }
}
