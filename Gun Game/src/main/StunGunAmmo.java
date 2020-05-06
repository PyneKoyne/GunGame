package main;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Random;

public class StunGunAmmo extends gameObject{
    private Handler handler;
    public int X = 0, Y = 0;
    private float con = 0;
    private int score = 0, start = 0;
    private float checkX = 0, checkY = 0;
    private int total = 0;
    public StunGunAmmo(int x, int y, ID id, Handler handler, int Health, int damage, int score) {
        super(x, y, id, Health, damage, score);
        for(int i = 0; i < handler.object.size(); i ++) {
            gameObject tempObject = handler.object.get(i);
            if (tempObject.getid() == ID.Player) {
                X = tempObject.getX();
                Y = tempObject.getY();
            }
        }

        Point p = MouseInfo.getPointerInfo().getLocation();
        float mouseX = p.x - (X+130);
        float mouseY = p.y - (Y+73);
        this.handler = handler;
        con = (float) (40/Math.sqrt(Math.pow(((mouseX-x)/3), 2)+Math.pow((mouseY-y)/3, 2)));
        ang = Math.atan2(mouseY, mouseX);
        Random r= new Random();
        velX = Math.round(mouseX/3*con);
        velY = Math.round(mouseY/3*con);
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
            if (tempObject.getid() == ID.Enemy1 || tempObject.getid() == ID.Enemy3 || tempObject.getid() == ID.Enemy2 || tempObject.getid() == ID.SuperBoss) {
                for(int j = 0; j < start; j ++) {
                    if (x + checkX*j > tempObject.getX() - 15 && y + checkY*j > tempObject.getY() - 15 && y + checkY*j <
                            tempObject.getY() + 52 && x + checkX*j < tempObject.getX() + 58) {
                        tempObject.setScore(200);
                    }
                }
            }
        }
    }


    public void render(Graphics g) {

        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        BufferedImage icon = null;
        if(total == 0) {
            icon = LoadImage(".\\src\\GunGameSprites\\Taser-1.png");
            total ++;
        }else if(total ==1){
            icon = LoadImage(".\\src\\GunGameSprites\\Taser-2.png");
            total ++;
        }else if(total == 2){
            icon = LoadImage(".\\src\\GunGameSprites\\Taser-3.png");
            total ++;
        }else if(total == 3){
            icon = LoadImage(".\\src\\GunGameSprites\\Taser-4.png");
            total ++;
        }else if(total == 4){
            icon = LoadImage(".\\src\\GunGameSprites\\Taser-5.png");
            total = 0;
        }
        at.rotate(ang, 18, 18);
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
