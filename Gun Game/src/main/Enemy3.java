package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Enemy3 extends gameObject{

    private Handler handler;

    public Enemy3(int x, int y, ID id, Handler handler, int Health, int damage, int score) {
        super(x, y, id, Health, damage, score);
        this.handler = handler;
    }
    public int X = 0;
    public int Y = 0;
    public int minusHealth = 0;
    public float con;
    public void tick() {


        for(int i = 0; i < handler.object.size(); i ++) {
            gameObject tempObject = handler.object.get(i);
            if (tempObject.getid() == ID.Player) {
                X = tempObject.getX();
                Y = tempObject.getY();
                int N = tempObject.getHealth();
                tempObject.setHealth(N-minusHealth);
                minusHealth = 0;
            }
            if (tempObject.getid() == ID.SuperBoss) {
                X = tempObject.getX();
                Y = tempObject.getY();
                break;
            }
        }
        if (x + velX > X-30 && y+velY > Y-30 && y + velY < Y+67 && x + velX < X+78){
            minusHealth ++;
        }
        for(int i = 0; i < handler.object.size(); i ++){
            gameObject tempObject = handler.object.get(i);
            if(tempObject.getid() == ID.Enemy3){
                tempObject.setVelY(Math.round((Y-y)/30));
                tempObject.setVelX(Math.round((X-x)/30));
            }
        }
        if(score > 0){
            velX = 0;
            velY = 0;
            score --;
            minusHealth = 0;
        }
        x += velX;
        y += velY;
    }
    public double ang;
    public void render(Graphics g) {
        BufferedImage icon;
        Random r = new Random();
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        ang = Math.atan2(Y-y, X-x);
        at.rotate(ang, 18, 18);
        if(damage > 0) {
            icon = LoadImage(".\\src\\GunGameSprites\\Enemy3_damage.png");
            damage -= 1;
        } else{
            icon = LoadImage(".\\src\\GunGameSprites\\Enemy3.png");
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(icon, at, null);
    }

    private BufferedImage LoadImage(String FileName) {

        BufferedImage img = null;


        try{
            img = ImageIO.read(new File(FileName));
        } catch (IOException e){

        }
        return img;
    }
}
