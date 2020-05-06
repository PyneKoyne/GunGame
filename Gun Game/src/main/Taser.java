package main;

import java.awt.*;

public class Taser extends gameObject{
    private long time;
    private Handler handler;
    private long Charge = 3000;
    private long time1 = System.currentTimeMillis();
    private int X = 0, Y = 0;
    public Taser(int x, int y, ID id, int Health, int damage, int score, long time, Handler handler) {
        super(x, y, id, Health, damage, score);
        this.handler = handler;
        this.time = time;
    }

    @Override
    public void tick() {
        for(int i = 0; i < handler.object.size(); i ++){
            gameObject tempObject = handler.object.get(i);
            if(tempObject.getid() == ID.Player){
                X = tempObject.getX();
                Y = tempObject.getY();
            }
        }
        Point p = MouseInfo.getPointerInfo().getLocation();
        float mouseX = p.x - 155 - X;
        float mouseY = p.y - 75 - Y;
        float hyp = (float) Math.sqrt(Math.pow(mouseX, 2) + Math.pow(mouseY, 2))/60;
        float minusX = 0, minusY = 0;
        x = X +13+ Math.round(mouseX/hyp);
        y = Y +9+ Math.round(mouseY/hyp);
        if(score == 0){
            time1 = System.currentTimeMillis();
        }
        if(Charge > 0 && score == 1){
            Charge += time1 - System.currentTimeMillis();
            time = System.currentTimeMillis();
            time1 = System.currentTimeMillis();
            for(int i = 0; i < handler.object.size(); i ++){
                gameObject tempObject = handler.object.get(i);
                if(tempObject.getid() == ID.Player){
                    tempObject.setdamage(100);
                }
                if (tempObject.getid() == ID.Enemy1 || tempObject.getid() == ID.Enemy3 || tempObject.getid() == ID.Enemy2 ) {
                    if (x > tempObject.getX() - 15 && y > tempObject.getY() - 15 && y < tempObject.getY() + 52 && x < tempObject.getX() + 58) {
                        int N = tempObject.getHealth();
                        tempObject.setHealth(N-1);
                        tempObject.setdamage(1);
                        tempObject.setVelX(0);
                        tempObject.setVelY(0);
                    }
                    if (tempObject.getHealth() < 0) {
                        handler.object.remove(tempObject);
                        for(int j = 0; j < handler.object.size(); j++){
                            gameObject tempObject1 = handler.object.get(j);
                            if(tempObject1.getid() == ID.Player){
                                tempObject1.setScore(tempObject1.getScore()+1);
                            }
                        }
                    }

                } if( tempObject.getid() == ID.SuperBoss){
                    if (x > tempObject.getX() - 15 && y > tempObject.getY() - 15 && y < tempObject.getY() + 52 && x < tempObject.getX() + 58) {
                        if(tempObject.getdamage() == 0){
                            int N = tempObject.getHealth();
                            tempObject.setHealth(N-1);
                            tempObject.setdamage(10);
                        }
                        tempObject.setVelX(0);
                        tempObject.setVelY(0);
                    }
                }
            }
        }else{
            for(int i = 0; i < handler.object.size(); i ++){
                gameObject tempObject = handler.object.get(i);
                if(tempObject.getid() == ID.Player){
                    tempObject.setdamage(3);
                }
            }
        }
        if(System.currentTimeMillis() - time > 3000){
            time += System.currentTimeMillis() - time;
            Charge = 3000;
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        g.drawString("Battery Charge", 15, 695);
        g.setColor(Color.BLACK);
        g.fillRect(7, 702, 206, 31);
        g.setColor(Color.green);
        g.fillRect(10, 705, (int) (Charge/15), 25);
    }
}
