package main;

import java.awt.*;

public class DoomsDay extends gameObject{
    private long time;
    private Handler handler;
    public DoomsDay(int x, int y, ID id, int Health, int damage, int score, long time, Handler handler) {
        super(x, y, id, Health, damage, score);
        this.handler = handler;
        this.time = time;
    }

    @Override
    public void tick() {
        int Remove = 0;
        if(score == 1 && System.currentTimeMillis() - time > 30000){
            for(int i = 0; i < handler.object.size()+Remove; i ++){
                System.out.println(handler.object.size());
                gameObject tempObject = handler.object.get(i-Remove);
                if(tempObject.getid() != ID.Player && tempObject.getid() != ID.SuperBoss && tempObject.getid() != ID.DoomsDay){
                    handler.removeObject(tempObject);
                    Remove ++;
                }else{
                    tempObject.setdamage(5);
                }
            }
            time = System.currentTimeMillis();
            damage = 1;
        }else if(System.currentTimeMillis() - time < 30000){
            for(int i = 0; i < handler.object.size(); i ++) {
                gameObject tempObject = handler.object.get(i);
                if(tempObject.getid() == ID.Player){
                    tempObject.setdamage(5);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {

    }
}
