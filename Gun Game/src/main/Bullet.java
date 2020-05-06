package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Bullet extends gameObject{
    private final Handler handler;
    public int X = 0, Y = 0;
    private int start;
    private float checkX = 0, checkY = 0;
    public Bullet(int x, int y, ID id, Handler handler, int Health, int damage, int score) {
        super(x, y, id, Health, damage, score);
        for(int i = 0; i < handler.object.size(); i ++) {
            gameObject tempObject = handler.object.get(i);
            if (tempObject.getid() == ID.Player) {
                X = tempObject.getX();
                Y = tempObject.getY();
                System.out.println(tempObject.getid());
            }
        }

        Point p = MouseInfo.getPointerInfo().getLocation();
        float mouseX = p.x - (X+130);
        float mouseY = p.y - (Y+73);
        this.handler = handler;
        float con = (float) (20 / Math.sqrt(Math.pow(((mouseX - x) / 30), 2) + Math.pow((mouseY - y) / 30, 2)));
        ang = Math.atan2(mouseY, mouseX);
        Random r= new Random();
        if(score == 2){
            velX = Math.round(mouseX/30* con)+r.nextInt(7)*(r.nextBoolean() ? -1 : 1);
            velY = Math.round(mouseY/30* con)+r.nextInt(7)*(r.nextBoolean() ? -1 : 1);
        } else{
        velX = Math.round(mouseX/30* con);
        velY = Math.round(mouseY/30* con);
        }
        if(velY ==0) velY = 1;
        if(velX ==0) velX = 1;

        if((float) Math.abs(velY)/Math.abs(velX) > 1){
            checkX = 1;
            if(velY < 0) {
                checkY = (float) -(velY / velX);
            } else{
                checkY = (float) (velY / velX);
            }
            start = Math.round(Math.abs(velX));
        }
        if((float) Math.abs(velX)/Math.abs(velY) > 1){
            checkY = 1;
            if(velY < 0) {
                checkX = (float) -(velX / velY);
            } else{
                checkX = (float) (velX / velY);
            }
            start = Math.round(Math.abs(velY));
        }
        else{
            if(velY < 0) {
                checkX = (float) -(1);
            }
            start = Math.round(Math.abs(velX));
        }
    }
    public double ang;
    public void tick() {
        if (x == 0 && y == 0) {
            x = X;
            y = Y;
        }

        x += velX;
        y += velY;
        for (int i = 0; i < handler.object.size(); i++) {
            gameObject tempObject = handler.object.get(i);
            if (tempObject.getid() == ID.Enemy1 || tempObject.getid() == ID.Enemy3 || tempObject.getid() == ID.Enemy2 || tempObject.getid() == ID.SuperBoss ) {
                for(int j = 0; j < start; j ++) {
                    if (x + checkX*j > tempObject.getX() - 15 && y + checkY*j > tempObject.getY() - 15 && y + checkY*j <
                            tempObject.getY() + 52 && x + checkX*j < tempObject.getX() + 58 && tempObject.getdamage() == 0) {
                        int N = tempObject.getHealth();
                        tempObject.setHealth(N - 1);
                        tempObject.setdamage(30);

                    }
                }
                if (tempObject.getHealth() < 1) {
                    handler.object.remove(tempObject);
                    for(int j = 0; j < handler.object.size(); j++){
                        gameObject tempObject1 = handler.object.get(j);
                        if(tempObject1.getid() == ID.Player){
                            tempObject1.setScore(tempObject1.getScore()+1);
                        }
                    }
                }
            }
        }
    }


    public void render(Graphics g) {

        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        BufferedImage icon = LoadImage();
        at.rotate(ang, 18, 18);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(icon, at, null);
    }
    private BufferedImage LoadImage() {

        BufferedImage img = null;


        try{
            img = ImageIO.read(new File(".\\src\\GunGameSprites\\Bullet.png"));
        } catch (IOException ignored){
        }
        return img;
    }
}
