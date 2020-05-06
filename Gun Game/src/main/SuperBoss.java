package main;

import javafx.scene.transform.Scale;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SuperBoss extends gameObject{

    private Handler handler;
    private double Speed = 3, Size = 1.5;
    public SuperBoss(int x, int y, ID id, Handler handler, int Health, int damage, int score) {
        super(x, y, id, Health, damage, score);
        this.handler = handler;
    }
    public int X = 0;
    public int Y = 0;
    public int minusHealth = 0;
    public float con;
    public void tick() {
        System.out.println("wt");
        for(int i = 0; i < handler.object.size(); i ++) {
            gameObject tempObject = handler.object.get(i);
            if (tempObject.getid() == ID.Player) {
                X = tempObject.getX();
                Y = tempObject.getY();
                int N = tempObject.getHealth();
                tempObject.setHealth(N-minusHealth);
                minusHealth = 0;
            }
        }
        if (x + velX > X-30 && y+velY > Y-30 && y + velY < Y+67 && x + velX < X+78){
            minusHealth ++;
        }
        con = (float) (Speed/Math.sqrt(Math.pow(((X-x)/40), 2)+Math.pow((Y-y)/40, 2)));
        for(int i = 0; i < handler.object.size(); i ++){
            gameObject tempObject = handler.object.get(i);
            if(tempObject.getid() == ID.SuperBoss){
                tempObject.setVelY(Math.round((Y-y)/40*con));
                tempObject.setVelX(Math.round((X-x)/40*con));
            }
            if(tempObject.getid() == ID.Enemy1){
                if(x + 18*Size > tempObject.getX() && y + 18*Size > tempObject.getY() && x + 18*Size < tempObject.getX()+18 && y + 18*Size < tempObject.getY() + 18){
                    handler.removeObject(tempObject);
                    Speed += 0.1;
                    Health += 0.5;
                    Size += 0.05;
                }
            }
            if(tempObject.getid() == ID.Enemy2){
                if(x + 18*Size > tempObject.getX() && y + 18*Size > tempObject.getY() && x + 18*Size < tempObject.getX()+18 && y + 18*Size < tempObject.getY() + 18){
                    handler.removeObject(tempObject);
                    Speed += 0.05;
                    Health += 2;
                    Size += 0.1;
                }
            }
            if(tempObject.getid() == ID.Enemy3){
                if(x + 18*Size > tempObject.getX()-10*Size && y + 18*Size > tempObject.getY()-10*Size && x + 18*Size < tempObject.getX()+57*Size && y + 18*Size < tempObject.getY() + 46*Size){
                    handler.removeObject(tempObject);
                    Speed += 0.08;
                    Health += 1;
                    Size += 0.08;
                }
            }
        }
        if(score > 0){
            velX = 0;
            velY = 0;
            score --;
        }
        x += velX;
        y += velY;
        if(Health < 0){
            for(int i = 0; i < handler.object.size(); i ++){
                gameObject tempObject = handler.object.get(i);
                if(tempObject.getid() == ID.Player){
                    tempObject.setScore(100000);
                }
            }
        }
    }
    public double ang;
    public void render(Graphics g) {
        BufferedImage icon;
        Random r = new Random();
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        ang = Math.atan2(Y-y, X-x);
        at.rotate(ang, 18, 18);
        if(damage > 0) {
            icon = LoadImage(".\\src\\GunGameSprites\\SuperBossDamage.png");
            damage -= 1;
        } else{
            icon = LoadImage(".\\src\\GunGameSprites\\SuperBoss.png");
        }
        Image newImage = icon.getScaledInstance((int) Math.round(46*Size), (int)Math.round(37*Size), Image.SCALE_DEFAULT);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(newImage, at, null);
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
